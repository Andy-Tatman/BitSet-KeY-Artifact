package java.nio;

/**
 * @generated
 */
public abstract class LongBuffer extends java.nio.Buffer implements java.lang.Comparable {
   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public abstract java.nio.LongBuffer slice();

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public abstract long get(int param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public java.nio.LongBuffer get(long[] param0);
}