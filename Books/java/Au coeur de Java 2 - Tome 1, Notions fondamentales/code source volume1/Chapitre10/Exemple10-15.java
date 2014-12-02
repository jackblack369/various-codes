Exemple 10.15 : SystemInfo.java

import java.applet.*;
import java.io.*;
import java.util.*;

/**
   Ce programme affiche toutes les propriétés système.
*/
public class SystemInfo
{  
   public static void main(String args[])
   {   
      try
      {
         Properties sysprops = System.getProperties();
         sysprops.store(System.out, "System Properties");
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }
}
