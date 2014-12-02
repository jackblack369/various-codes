Exemple 6.12 : PersistentFrameTest.java

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import javax.swing.*;
 
/**
   Ce programme montre l'utilisation d'un encodeur et d'un décodeur XML 
   pour enregistrer et restaurer un cadre.
*/
public class PersistentFrameTest
{
   public static void main(String[] args)
   {
      chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));      
      PersistentFrameTest test = new PersistentFrameTest();
      test.init();
   }

