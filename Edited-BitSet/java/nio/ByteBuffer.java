package java.nio;

/**
 * @generated
 */
public abstract class ByteBuffer extends java.nio.Buffer implements java.lang.Comparable {
   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public static java.nio.ByteBuffer wrap(byte[] param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public final java.nio.ByteBuffer order(java.nio.ByteOrder param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public abstract java.nio.ByteBuffer slice();

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public abstract byte get(int param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public abstract long getLong();

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public abstract byte get();

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public abstract java.nio.ByteBuffer putLong(long param0);

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public abstract java.nio.ByteBuffer put(byte param0);
}