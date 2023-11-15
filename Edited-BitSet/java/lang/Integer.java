package java.lang;

/**
 * @generated
 */
public final class Integer extends java.lang.Number implements java.lang.Comparable {
   /**
    * @generated
    */
   public static final int MAX_VALUE= 2147483647;
   public static final int MIN_VALUE= -2147483648;

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static java.lang.String toString(int param0, int param1);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static java.lang.String toHexString(int param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static java.lang.String toOctalString(int param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static java.lang.String toBinaryString(int param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static java.lang.String toString(int param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static int parseInt(java.lang.String param0, int param1) throws java.lang.NumberFormatException;

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static int parseInt(java.lang.String param0) throws java.lang.NumberFormatException;

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static java.lang.Integer valueOf(java.lang.String param0, int param1) throws java.lang.NumberFormatException;

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static java.lang.Integer valueOf(java.lang.String param0) throws java.lang.NumberFormatException;

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static java.lang.Integer valueOf(int param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public Integer(int param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public Integer(java.lang.String param0) throws java.lang.NumberFormatException;

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public byte byteValue();

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public short shortValue();

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public int intValue();

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public long longValue();

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static java.lang.Integer getInteger(java.lang.String param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static java.lang.Integer getInteger(java.lang.String param0, int param1);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static java.lang.Integer getInteger(java.lang.String param0, java.lang.Integer param1);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static java.lang.Integer decode(java.lang.String param0) throws java.lang.NumberFormatException;

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public int compareTo(java.lang.Integer param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static int highestOneBit(int param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static int lowestOneBit(int param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static int numberOfLeadingZeros(int param0);


   /*@ public behavior
     @ requires true;
     @ ensures true;
     @*/
  //  /*@ public behavior
  //    @ requires true;
  //    @ ensures \result >= 0 && \result <= 32 && (\forall int j; 0 <= j < \result; ((i >> j) & 1) == 0 );
  //    @*/
   public static /*@ strictly_pure @*/ int numberOfTrailingZeros(int i);
  //  {
  //       // HD, Count trailing 0's
  //       i = ~i & (i - 1);
  //       if (i <= 0) return i & 32;
  //       int n = 1;
  //       if (i > 1 << 16) { n += 16; i >>>= 16; }
  //       if (i > 1 <<  8) { n +=  8; i >>>=  8; }
  //       if (i > 1 <<  4) { n +=  4; i >>>=  4; }
  //       if (i > 1 <<  2) { n +=  2; i >>>=  2; }
  //       return n + (i >>> 1);
  //  }

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static int bitCount(int param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static int rotateLeft(int param0, int param1);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static int rotateRight(int param0, int param1);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static int reverse(int param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static int signum(int param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static int reverseBytes(int param0);
}