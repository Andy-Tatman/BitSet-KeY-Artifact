package java.io;

/**
 * @generated
 */
public class ObjectOutputStream extends java.io.OutputStream implements java.io.ObjectOutput, java.io.ObjectStreamConstants {
   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public java.io.ObjectOutputStream.PutField putFields() throws java.io.IOException;

   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public void writeFields() throws java.io.IOException;

   /**
    * @generated
    */
   public static abstract class PutField extends java.lang.Object {
      /**
       * @generated
       */
      /*@ public behavior
        @ requires true;
        @ ensures true;
        @ assignable \everything;
        @*/
      public abstract void put(java.lang.String param0, java.lang.Object param1);
   }
}