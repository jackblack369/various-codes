Exemple 9.15 : Printf3.java

import java.io.*;

class Printf3
{
   public static native void fprint(PrintWriter out, 
      String format, double x);

   static
   {
      System.loadLibrary("Printf3");
   }
}
