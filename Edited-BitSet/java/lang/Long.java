package java.lang;

/**
 * @generated
 */
public final class Long extends java.lang.Number implements java.lang.Comparable {
   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static int numberOfTrailingZeros(long param0);

   /**
    * @generated
    */
  /*@ public behavior
    @ requires true;
    @ ensures true;
    @*/
  //  /*@ normal_behavior
  //    @ requires true;
  //    @ ensures \result >= 0 && \result <= 64 && (\forall int j; 0 <= j < \result; ((i >>> j) & 1) == 0 );
  //    @*/
   public static /*@ strictly_pure @*/ int numberOfLeadingZeros(long i);
  //  {
  //       int x = (int)i;
  //       return x == 0 ? 32 + Integer.numberOfTrailingZeros((int)(i >>> 32))
  //               : Integer.numberOfTrailingZeros(x);
  //  }

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static int bitCount(long param0);
}