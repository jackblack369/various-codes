Exemple 10.10 : AppletFrame.java

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class AppletFrame extends JFrame
   implements AppletStub, AppletContext
{  
   public AppletFrame(Applet anApplet)
   {  
      applet = anApplet;
      add(applet);
      applet.setStub(this);
   }

   public void setVisible(true)
   {
      if (b)
      {
         applet.init();
         super.setVisible(true);
         applet.start();
      }
      else  
      {
         applet.stop();
         super.setVisible(false);
         applet.destroy();
      }

   // Méthodes AppletStub
   public boolean isActive() { return true; }
   public URL getDocumentBase() { return null; }
   public URL getCodeBase() { return null; }
   public String getParameter(String name) { return ""; }
   public AppletContext getAppletContext() { return this; }
   public void appletResize(int width, int height) {}

   // Méthodes AppletContext 
   public AudioClip getAudioClip(URL url) { return null; }
   public Image getImage(URL url) { return null; }
   public Applet getApplet(String name) { return null; }
   public Enumeration getApplets() { return null; }
   public void showDocument(URL url) {}
   public void showDocument(URL url, String target) {}
   public void showStatus(String status) {}
   public void setStream(String key, InputStream stream) {}
   public InputStream getStream(String key) { return null; }
   public Iterator getStreamKeys() { return null; }

   private Applet applet;
}
