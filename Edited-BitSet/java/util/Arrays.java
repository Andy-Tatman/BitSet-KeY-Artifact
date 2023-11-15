package java.util;


// Source: https://git.key-project.org/key-public/key/-/blob/stable/key.core/src/main/resources/de/uka/ilkd/key/java/JavaRedux/java/util/Arrays.java

/**
 * @generated
 */
public class Arrays extends java.lang.Object {
   /**
    * @generated
    */
   /*@ public normal_behavior
     @ requires 0 <= newLength;
     @ ensures \fresh(\result) && \result.length == newLength;
     @ ensures (\forall \bigint i; 0 <= i && i < newLength;
     @ 	\result[i] == (i < original.length ? original[i] : 0)
     @ );
     @ assignable \nothing;
     @*/
   public static long[] copyOf(long[] original, int newLength);
}