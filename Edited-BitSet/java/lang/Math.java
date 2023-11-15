package java.lang;

/**
 * @generated
 */
public final class Math extends java.lang.Object {
   /*@
     @ public normal_behaviour
     @  ensures a >= b ==> \result == a;
     @  ensures (a < b) ==> \result == b;
     @  assignable \strictly_nothing;
     @  // no_state  // for future use
     @*/
   public static int max(int a, int b);

   /*@
     @ public normal_behaviour
     @  ensures a <= b ==> \result == a; 
     @  ensures (a > b) ==> \result == b;
     @  assignable \strictly_nothing;
     @  // no_state  // for future use
     @*/
   public static int min(int a, int b);
}