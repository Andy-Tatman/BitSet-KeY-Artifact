package java.io;

/**
 * @generated
 */
public class ObjectInputStream extends java.io.InputStream implements java.io.ObjectInput, java.io.ObjectStreamConstants {
   /**
    * @generated
    */
   /*@ public behavior
     @ requires true;
     @ ensures true;
     @ assignable \everything;
     @*/
   public java.io.ObjectInputStream.GetField readFields() throws java.io.IOException, java.lang.ClassNotFoundException;

   /**
    * @generated
    */
   public static abstract class GetField extends java.lang.Object {
      /**
       * @generated
       */
      /*@ public behavior
        @ requires true;
        @ ensures true;
        @ assignable \everything;
        @*/
      public abstract java.lang.Object get(java.lang.String param0, java.lang.Object param1) throws java.io.IOException;
   }
}