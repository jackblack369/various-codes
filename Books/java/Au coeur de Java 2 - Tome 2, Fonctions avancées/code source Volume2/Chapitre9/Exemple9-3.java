Exemple 9.3 : HelloNative.java

class HelloNative
{
   public static native void greeting(); 

   static
   {
      System.loadLibrary("HelloNative");
   }
}
