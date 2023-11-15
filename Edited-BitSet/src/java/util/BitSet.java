/*
 * Copyright (c) 1995, 2020, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package java.util;

import java.io.*;
// For methods we have disabled.
/*
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
*/

/**
 * This class implements a vector of bits that grows as needed. Each
 * component of the bit set has a {@code boolean} value. The
 * bits of a {@code BitSet} are indexed by nonnegative integers.
 * Individual indexed bits can be examined, set, or cleared. One
 * {@code BitSet} may be used to modify the contents of another
 * {@code BitSet} through logical AND, logical inclusive OR, and
 * logical exclusive OR operations.
 *
 * <p>By default, all bits in the set initially have the value
 * {@code false}.
 *
 * <p>Every bit set has a current size, which is the number of bits
 * of space currently in use by the bit set. Note that the size is
 * related to the implementation of a bit set, so it may change with
 * implementation. The length of a bit set relates to logical length
 * of a bit set and is defined independently of implementation.
 *
 * <p>Unless otherwise noted, passing a null parameter to any of the
 * methods in a {@code BitSet} will result in a
 * {@code NullPointerException}.
 *
 * <p>A {@code BitSet} is not safe for multithreaded use without
 * external synchronization.
 *
 * @author  Arthur van Hoff
 * @author  Michael McCloskey
 * @author  Martin Buchholz
 * @since   1.0
 */
public class BitSet implements Cloneable, java.io.Serializable {


    /*
     * BitSets are packed into arrays of "words."  Currently a word is
     * a long, which consists of 64 bits, requiring 6 address bits.
     * The choice of word size is determined purely by performance concerns.
     */
    private static final int ADDRESS_BITS_PER_WORD = 6;
    private static final int BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD;
    private static final int BIT_INDEX_MASK = BITS_PER_WORD - 1;

    /* Used to shift left or right for a partial word mask */
    private static final long WORD_MASK = 0xffffffffffffffffL;

    /**
     * @serialField bits long[]
     *
     * The bits in this BitSet.  The ith bit is stored in bits[i/64] at
     * bit position i % 64 (where bit position 0 refers to the least
     * significant bit and 63 refers to the most significant bit).
     */
/*    @java.io.Serial
    private static final ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField("bits", long[].class),
    };
*/

    /**
     * The internal field corresponding to the serialField "bits".
     */
    private long[] words;

    /**
     * The number of words in the logical size of this BitSet.
     */
    private transient int wordsInUse = 0;

    /**
     * Whether the size of "words" is user-specified.  If so, we assume
     * the user knows what he's doing and try harder to preserve it.
     */
    private transient boolean sizeIsSticky = false;

    /*@ invariant 
      @  words != null &
      @   // The first three are from checkInvariants:
      @  (wordsInUse == 0 || words[wordsInUse - 1] != 0) &&
      @  (wordsInUse >= 0 && wordsInUse <= words.length) &&
      @  (wordsInUse == words.length || words[wordsInUse] == 0) &&
      @  // Our addition to the invariant:
      @  (wordsInUse < words.length ==> 
      @      (\forall \bigint i; wordsInUse <= i < words.length; words[i] == 0) ) &&
      @  // wordsInUse is bounded by the last word BitSet can set a bit in:
      @  (wordsInUse <= (Integer.MAX_VALUE/BITS_PER_WORD + 1) ) && // +1 is to round up.
      @  // words.length is bounded by 2*wordsInUse's bound (See ensureCapacity.)
      @  (words.length <= 2*(Integer.MAX_VALUE/BITS_PER_WORD + 1) ) &&
      @  // For the various taclets we have added, we require the assumption that
      @  // each array element of words is inLong. However, we were not able to
      @  // automatically show this in KeY itself.
      @  (\forall \bigint i; 0 <= i < words.length; \dl_inLong(words[i]) )
      @   ; 
      @*/

    /* use serialVersionUID from JDK 1.0.2 for interoperability */
/*    @java.io.Serial
    private static final long serialVersionUID = 7997698588986878753L;
*/

    /**
     * Given a bit index, return word index containing it.
     */
    /*@ normal_behaviour
      @ requires bitIndex >= -1;
      @ ensures \old(bitIndex) >= 0 ==> \result == (\old(bitIndex) / 64); 
      @ ensures \old(bitIndex) == -1 ==> \result == -1; 
      @*/
    private static /*@ strictly_pure @*/ int wordIndex(int bitIndex) {
        return bitIndex >> ADDRESS_BITS_PER_WORD;
    }

    /**
     * Every public method must preserve these invariants.
     */
    /*@ normal_behaviour
      @ ensures true;
      @ assignable \strictly_nothing; @*/
    private void checkInvariants() {
        assert(wordsInUse == 0 || words[wordsInUse - 1] != 0);
        assert(wordsInUse >= 0 && wordsInUse <= words.length);
        assert(wordsInUse == words.length || words[wordsInUse] == 0);
    }

    /**
     * Sets the field wordsInUse to the logical size in words of the bit set.
     * WARNING:This method assumes that the number of words actually in use is
     * less than or equal to the current value of wordsInUse!
     */
    /*@ 
      @ normal_behaviour
      @ requires words != null;
      @ requires wordsInUse >= 0 && wordsInUse <= words.length;
      @ requires wordsInUse < words.length ==> 
      @      (\forall \bigint i; wordsInUse <= i < words.length; words[i] == 0);
      @ requires (wordsInUse <= (Integer.MAX_VALUE/BITS_PER_WORD + 1) );
      @ requires (words.length <= 2*(Integer.MAX_VALUE/BITS_PER_WORD +1) );
      @ requires (\forall \bigint i; 0 <= i < words.length; \dl_inLong(words[i]));
      @ ensures \invariant_for(this);
      @ ensures wordsInUse <= \old(wordsInUse);
      @ assignable this.wordsInUse;
      @ helper
      @*/
    private void recalculateWordsInUse() {
        // Traverse the bitset until a used word is found
        int i;
        /*@
          @ maintaining (\forall \bigint j; i < j < words.length; words[j] == 0);
          @ maintaining i < wordsInUse && i >= -1;
          @ decreasing i+1; // +1: At the end of the loop (if !break), i=-1.
          @ assignable \strictly_nothing;
          @*/
        for (i = wordsInUse-1; i >= 0; i--)
            if (words[i] != 0)
                break;

        wordsInUse = i+1; // The new logical size
    }

    /**
     * Creates a new bit set. All bits are initially {@code false}.
     */
    /*@ public normal_behaviour 
      @ requires true;
      @ ensures wordsToSeq() == \seq_empty;
      @ assignable \nothing;
      @*/
    /*@ private normal_behaviour 
      @ requires true;
      @ ensures words.length == 1; 
      @ ensures wordsToSeq() == \seq_empty;
      @ assignable \nothing;
      @*/
    public BitSet() {
        initWords(BITS_PER_WORD);
        sizeIsSticky = false;
    }

    /**
     * Creates a bit set whose initial size is large enough to explicitly
     * represent bits with indices in the range {@code 0} through
     * {@code nbits-1}. All bits are initially {@code false}.
     *
     * @param  nbits the initial size of the bit set
     * @throws NegativeArraySizeException if the specified initial size
     *         is negative
     */
    /*@ public normal_behaviour 
      @ requires nbits >= 0;
      @ ensures wordsToSeq() == \seq_empty;
      @ assignable \nothing;
      @*/
    /*@ private normal_behaviour 
      @ requires nbits >= 0;
      @ ensures nbits == 0 ==> words.length == 0;
      @ ensures nbits > 0 ==> words.length == ((nbits-1) / 64) + 1; 
      @ ensures wordsToSeq() == \seq_empty;
      @ assignable \nothing;
      @*/
    public BitSet(int nbits) {
        // nbits can't be negative; size 0 is OK
        if (nbits < 0)
            throw new NegativeArraySizeException("nbits < 0: " + nbits);

        initWords(nbits);
        sizeIsSticky = true;
    }

    private void initWords(int nbits) {
        words = new long[wordIndex(nbits-1) + 1];
    }

    /**
     * Creates a bit set using words as the internal representation.
     * The last word (if there is one) must be non-zero.
     */
    private BitSet(long[] words) {
        this.words = words;
        this.wordsInUse = words.length;
        checkInvariants();
    }

    /**
     * Returns a new bit set containing all the bits in the given long array.
     *
     * <p>More precisely,
     * <br>{@code BitSet.valueOf(longs).get(n) == ((longs[n/64] & (1L<<(n%64))) != 0)}
     * <br>for all {@code n < 64 * longs.length}.
     *
     * <p>This method is equivalent to
     * {@code BitSet.valueOf(LongBuffer.wrap(longs))}.
     *
     * @param longs a long array containing a little-endian representation
     *        of a sequence of bits to be used as the initial bits of the
     *        new bit set
     * @return a {@code BitSet} containing all the bits in the long array
     * @since 1.7
     */
    public static BitSet valueOf(long[] longs) {
        int n;
        for (n = longs.length; n > 0 && longs[n - 1] == 0; n--)
            ;
        return new BitSet(Arrays.copyOf(longs, n));
    }

    
    /**
     * Returns a new long array containing all the bits in this bit set.
     *
     * <p>More precisely, if
     * <br>{@code long[] longs = s.toLongArray();}
     * <br>then {@code longs.length == (s.length()+63)/64} and
     * <br>{@code s.get(n) == ((longs[n/64] & (1L<<(n%64))) != 0)}
     * <br>for all {@code n < 64 * longs.length}.
     *
     * @return a long array containing a little-endian representation
     *         of all the bits in this bit set
     * @since 1.7
     */
    public long[] toLongArray() {
        return Arrays.copyOf(words, wordsInUse);
    }


    // Our method for converting the actual representation to the logical representation.
    /*@ private model strictly_pure \seq wordsToSeq() {
      @  return (\seq_def \bigint i; 0; (\bigint)wordsInUse*(\bigint)BITS_PER_WORD; 
      @             (words[i / BITS_PER_WORD] >>> (int)(i % BITS_PER_WORD)) & 1  // >>> is not defined for bigint.
      @         );
      @ }
      @*/
    

    /**
     * Ensures that the BitSet can hold enough words.
     * @param wordsRequired the minimum acceptable number of words.
     */
    /*@
      @   normal_behaviour
      @     requires
      @         wordsRequired >= 0 & wordsRequired <= (Integer.MAX_VALUE/BITS_PER_WORD + 1); 
      @     ensures words.length >= wordsRequired;
      @     ensures wordsToSeq() == \old(wordsToSeq());
      @     ensures \old(words).length <= words.length; 
      @     ensures (\forall \bigint i; 0 <= i < \old(words).length;
      @                 \old(words[i]) == words[i]);
      @     ensures \old(words.length) < words.length ==> (\forall \bigint i; 
      @        \old(words.length) <= i < words.length; words[i] == 0);
      @     assignable words, sizeIsSticky;
      @*/
    private void ensureCapacity(int wordsRequired) {
        if (words.length < wordsRequired) {
            // Allocate larger of doubled size or required size
            int request = Math.max(2 * words.length, wordsRequired);
            words = Arrays.copyOf(words, request);
            sizeIsSticky = false;
        }
    }

    /**
     * Ensures that the BitSet can accommodate a given wordIndex,
     * temporarily violating the invariants.  The caller must
     * restore the invariants before returning to the user,
     * possibly using recalculateWordsInUse().
     * @param wordIndex the index to be accommodated.
     */
    /*@ normal_behaviour
      @ requires wordIndex >= 0 & wordIndex <= Integer.MAX_VALUE/BITS_PER_WORD;
      @ requires \invariant_for(this);
      @
      @ ensures wordIndex < \old(wordsInUse) ==> 
      @     words == \old(words) & wordsInUse == \old(wordsInUse);
      @ ensures wordIndex >= \old(wordsInUse) ==> wordsInUse == wordIndex+1; 
      @ ensures wordIndex < words.length; // Implies: wordsInUse <= words.length (invariant)
      @ // Parts required to restore the invariant:
      @ ensures (\forall \bigint i; 0 <= i < \old(wordsInUse); words[i] == \old(words[i]));
      @ ensures (\forall \bigint i; \old(wordsInUse) <= i < words.length; words[i] == 0);
      @ ensures words != null & words.length >= \old(words).length;
      @ ensures wordsInUse <= (Integer.MAX_VALUE/BITS_PER_WORD + 1);
      @ ensures words.length <= 2*(Integer.MAX_VALUE/BITS_PER_WORD + 1);
      @ ensures (\forall \bigint i; 0 <= i < words.length; \dl_inLong(words[i]) );
      @ assignable words, wordsInUse, sizeIsSticky;
      @ helper
      @*/
    private void expandTo(int wordIndex) {
        int wordsRequired = wordIndex+1;
        if (wordsInUse < wordsRequired) {
            ensureCapacity(wordsRequired);
            wordsInUse = wordsRequired;
        }
    }

    /**
     * Checks that fromIndex ... toIndex is a valid range of bit indices.
     */
    /*@ normal_behaviour
      @ requires fromIndex >= 0 && toIndex >= 0 && fromIndex <= toIndex; 
      @ ensures true;
      @ assignable \strictly_nothing;
      @*/
    private static void checkRange(int fromIndex, int toIndex) {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("fromIndex < 0: " + fromIndex);
        if (toIndex < 0)
            throw new IndexOutOfBoundsException("toIndex < 0: " + toIndex);
        if (fromIndex > toIndex)
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex +
                                                " > toIndex: " + toIndex);
    }

    /**
     * Sets the bit at the specified index to {@code true}.
     *
     * @param  bitIndex a bit index
     * @throws IndexOutOfBoundsException if the specified index is negative
     * @since  1.0
     */
    /*@
      @ normal_behaviour
      @ requires bitIndex >= 0; 
      @ ensures wordsToSeq()[bitIndex] == 1;
      @ ensures (\forall \bigint j; 0 <= j < \old(wordsToSeq()).length & j != bitIndex; 
      @          wordsToSeq()[j] == \old(wordsToSeq())[j] );
      @ ensures \old(wordsToSeq()).length < wordsToSeq().length ==>
      @          (\forall \bigint k; \old(wordsToSeq()).length <= k < wordsToSeq().length & k != bitIndex; 
      @            wordsToSeq()[k] == 0
      @          );
      @*/
    public void set(int bitIndex) {
        if (bitIndex < 0)
            throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);

        int wordIndex = wordIndex(bitIndex);
        expandTo(wordIndex); 

        words[wordIndex] |= (1L << bitIndex); // Restores invariants

        checkInvariants();
    }

    /**
     * Sets all of the bits in this BitSet to {@code false}.
     *
     * @since 1.4
     */
    /*@
      @ normal_behaviour
      @ requires true;
      @ ensures (\forall \bigint i; 0 <= i < wordsToSeq().length; wordsToSeq()[i] == 0);
      @*/
    public void clear() {
        /*@ 
          @ maintaining wordsInUse <= words.length;
          @ maintaining (\forall \bigint i; wordsInUse <= i < words.length; words[i] == 0);
          @ maintaining wordsInUse >= 0;
          @ decreasing wordsInUse;
          @ assignable words[*], wordsInUse;
          @*/
        while (wordsInUse > 0)
            words[--wordsInUse] = 0;
    }

    /**
     * Returns a new {@code BitSet} composed of bits from this {@code BitSet}
     * from {@code fromIndex} (inclusive) to {@code toIndex} (exclusive).
     *
     * @param  fromIndex index of the first bit to include
     * @param  toIndex index after the last bit to include
     * @return a new {@code BitSet} from a range of this {@code BitSet}
     * @throws IndexOutOfBoundsException if {@code fromIndex} is negative,
     *         or {@code toIndex} is negative, or {@code fromIndex} is
     *         larger than {@code toIndex}
     * @since  1.4
     */
    /*@ normal_behaviour
      @ requires fromIndex >= 0 && fromIndex <= toIndex; 
      @ ensures \result != this && \invariant_for(\result);
      @ ensures (\forall \bigint i; 0 <= i < \result.wordsToSeq().length; 
      @         (fromIndex + i < wordsToSeq().length ? wordsToSeq()[fromIndex + i] 
      @                  : 0) == \result.wordsToSeq()[i]);
      @ ensures (\result.wordsToSeq().length < (toIndex-fromIndex)) ==>
      @           (\forall \bigint i; \result.wordsToSeq().length <= i < (toIndex-fromIndex); 
      @             (fromIndex + i < wordsToSeq().length ? wordsToSeq()[fromIndex + i] 
      @                  : 0) == 0);
      @ assignable \nothing;
      @*/
    public BitSet get(int fromIndex, int toIndex) {
        checkRange(fromIndex, toIndex);

        checkInvariants();

        int len = length();

        /* Our suggested bug fix: */
        if (len < 0)
            len = Integer.MAX_VALUE;

        // If no set bits in range return empty bitset
        if (len <= fromIndex || fromIndex == toIndex)
            return new BitSet(0);

        // An optimization
        if (toIndex > len)
            toIndex = len;
        

        BitSet result = new BitSet(toIndex - fromIndex); 
        int targetWords = wordIndex(toIndex - fromIndex - 1) + 1;
        int sourceIndex = wordIndex(fromIndex);
        boolean wordAligned = ((fromIndex & BIT_INDEX_MASK) == 0);
        

        // Process all words but the last word
        /*@ 
          @ // Adjusting wordsToSeq for result: 
          @ maintaining ( \forall \bigint j; 0 <= j < ((\bigint)i*(\bigint)BITS_PER_WORD); 
          @         ( (result.words[j / BITS_PER_WORD] >>> (int)(j % BITS_PER_WORD)) & 1 )
          @      == (fromIndex + i < wordsToSeq().length ? wordsToSeq()[fromIndex + i] 
          @               : 0) ); 
          @   // >>> is not defined for bigint.
          @ maintaining i >= 0 & i <= targetWords - 1;
          @ maintaining sourceIndex < wordsInUse;
          @ maintaining (i < targetWords-1) ==> sourceIndex+1 < wordsInUse;
          @ maintaining sourceIndex >= fromIndex / 64 && sourceIndex <= toIndex / 64;
          @ maintaining (\forall \bigint j; 0 <= j < result.words.length; \dl_inLong(result.words[j]) );
          @ assignable result.words[*];
          @ decreasing targetWords - i;
          @*/
        for (int i = 0; i < targetWords - 1; i++, sourceIndex++)
            result.words[i] = wordAligned ? words[sourceIndex] :
                (words[sourceIndex] >>> fromIndex) |
                (words[sourceIndex+1] << -fromIndex);
        
        // Process the last word
        long lastWordMask = WORD_MASK >>> -toIndex;
        result.words[targetWords - 1] =
            ((toIndex-1) & BIT_INDEX_MASK) < (fromIndex & BIT_INDEX_MASK)
            ? /* straddles source words */
            ((words[sourceIndex] >>> fromIndex) |
             (words[sourceIndex+1] & lastWordMask) << -fromIndex)
            :
            ((words[sourceIndex] & lastWordMask) >>> fromIndex);

        // Set wordsInUse correctly
        result.wordsInUse = targetWords;
        result.recalculateWordsInUse();
        result.checkInvariants();

        return result;
    }

    /**
     * Returns the "logical size" of this {@code BitSet}: the index of
     * the highest set bit in the {@code BitSet} plus one. Returns zero
     * if the {@code BitSet} contains no set bits.
     *
     * @return the logical size of this {@code BitSet}
     * @since  1.2
     */
    /*@ normal_behaviour
      @ requires true;
      @ ensures \result >= 0 || \result == Integer.MIN_VALUE;
      @ ensures \result == 0 ==>
      @     wordsToSeq().length == 0;
      @ ensures \result != 0 ==> wordsToSeq()[\result-1] == 1 &
      @         (\forall \bigint i; \result-1 < i < wordsToSeq().length; wordsToSeq()[i] == 0);
      // Result is in the last word of the LOGICAL size of words[]. (words[wIU-1] != 0) 
      @ ensures \result != 0 ==> (\result-1 < wordsToSeq().length && \result-1 >= wordsToSeq().length - BITS_PER_WORD); 
      @*/
    public  /*@ strictly_pure @*/ int length() {
        if (wordsInUse == 0)
            return 0;

        return BITS_PER_WORD * (wordsInUse - 1) +
            (BITS_PER_WORD - Long.numberOfLeadingZeros(words[wordsInUse - 1]));
    }

    /**
     * Returns the number of bits of space actually in use by this
     * {@code BitSet} to represent bit values.
     * The maximum element in the set is the size - 1st element.
     *
     * @return the number of bits currently in this bit set
     */
    public int size() {
        return words.length * BITS_PER_WORD;
    }

    /**
     * Compares this object against the specified object.
     * The result is {@code true} if and only if the argument is
     * not {@code null} and is a {@code BitSet} object that has
     * exactly the same set of bits set to {@code true} as this bit
     * set. That is, for every nonnegative {@code int} index {@code k},
     * <pre>((BitSet)obj).get(k) == this.get(k)</pre>
     * must be true. The current sizes of the two bit sets are not compared.
     *
     * @param  obj the object to compare with
     * @return {@code true} if the objects are the same;
     *         {@code false} otherwise
     * @see    #size()
     */
    /*
    public boolean equals(Object obj) {
        if (!(obj instanceof BitSet set))
            return false;
        if (this == obj)
            return true;

        checkInvariants();
        set.checkInvariants();

        if (wordsInUse != set.wordsInUse)
            return false;

        // Check words in use by both BitSets
        for (int i = 0; i < wordsInUse; i++)
            if (words[i] != set.words[i])
                return false;

        return true;
    }
    */

}
