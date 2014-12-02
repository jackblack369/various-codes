Exemple 9.9 : Printf2.java

class Printf2
{
   public static native String sprint(String format, double x);

   static
   {
      System.loadLibrary("Printf2");
   }
}
