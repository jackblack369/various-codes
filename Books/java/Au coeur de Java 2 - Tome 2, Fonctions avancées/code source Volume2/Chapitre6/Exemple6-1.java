Exemple 6.1 : ImageViewerBean.java

package com.horstmann.corejava;

import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

/**
   Un bean pour l'affichage d'une image.
*/
public class ImageViewerBean extends JPanel
{

   public ImageViewerBean()
   {
      setBorder(BorderFactory.createEtchedBorder());
   }

   /**
      Définit la propriété fileName.
      @param fileName le nom du fichier image
   */ 
   public void setFileName(String fileName)
   {
      try
      {
         file = new File(fileName);
         setIcon(new ImageIcon(ImageIO.read(file)));
      }
      catch (IOException e)
      {
         file = null;
         setIcon(null);
      }
  }

/**
   Récupère la propriété fileName.
   @return le nom du fichier image
*/
public String getFileName()
{
   if (file == null) return null;
   else return file.getPath();
}

   public Dimension getPreferredSize()
   {
      return new Dimension(XPREFSIZE, YPREFSIZE);
   }

   private File file = null; 
   private static final int XPREFSIZE = 200;
   private static final int YPREFSIZE = 200;
}
