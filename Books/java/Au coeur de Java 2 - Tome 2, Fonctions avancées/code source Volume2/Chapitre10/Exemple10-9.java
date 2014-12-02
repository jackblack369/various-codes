Exemple 10.9 : XMLWriteTest.java

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

/**
   Ce programme montre comment écrire un fichier XML. Il enregistre
   un fichier décrivant un dessin moderne au format SVG.
*/
public class XMLWriteTest
{  
   public static void main(String[] args)
   {  
      XMLWriteFrame frame = new XMLWriteFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un bloc contenant un panneau permettant d'afficher un dessin moderne.
*/
class XMLWriteFrame extends JFrame
{
   public XMLWriteFrame()
   {
      setTitle("XMLWriteTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      chooser = new JFileChooser();

      // ajouter le panneau au bloc

      panel = new RectanglePanel();
      add(panel);

      // définir la barre de menu

      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);

      JMenu menu = new JMenu("Fichier");
      menuBar.add(menu);

      JMenuItem newItem = new JMenuItem("Nouveau");
      menu.add(newItem);
      newItem.addActionListener(new 
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
               { panel.newDrawing(); }
         });

      JMenuItem saveItem = new JMenuItem("Enregistrer");
      menu.add(saveItem);
      saveItem.addActionListener(new 
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               try
               {
                  saveDocument();
               }
               catch (TransformerException e)
               {
                  JOptionPane.showMessageDialog(
                     XMLWriteFrame.this, e.toString());
               }
               catch (IOException e)
               {
                  JOptionPane.showMessageDialog(
                     XMLWriteFrame.this, e.toString());
               }
            }
         });

      JMenuItem exitItem = new JMenuItem("Quitter");
      menu.add(exitItem);
      exitItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
               { System.exit(0); }
         });

   }

   /**
      Enregistre le dessin au format SVG.
   */
   public void saveDocument() 
      throws TransformerException, IOException
   {
      if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
         return;
      File f = chooser.getSelectedFile();
      Document doc = panel.buildDocument();         
      Transformer t = TransformerFactory
         .newInstance().newTransformer();

      t.setOutputProperty(OutputKeys.DOCTYPE-SYSTEM, 
"http://www.w3.org/TR/2000/CR-SVG-20000802/DTD/svg-20000802.dtd");
      t.setOutputProperty(OutputKeys.DOCTYPE-PUBLIC, 
         "-//W3C//DTD SVG 20000802//EN");

      t.transform(new DOMSource(doc), 
         new StreamResult(new FileOutputStream(f)));      
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  

   private RectanglePanel panel;
   private JFileChooser chooser;
}

/**
   Un panneau qui affiche un ensemble de rectangles colorés.
*/
class RectanglePanel extends JPanel
{ 
   public RectanglePanel()
   {  
      rects = new ArrayList<Rectangle2D>();
      colors = new ArrayList<Color>();
      generator = new Random();

      DocumentBuilderFactory factory 
         = DocumentBuilderFactory.newInstance();
      try
      {
         builder = factory.newDocumentBuilder();
      }
      catch (ParserConfigurationException e)
      {
         e.printStackTrace();
      }
   }

   /**
      Crée un nouveau dessin aléatoire.
   */
   public void newDrawing()
   {
      int n = 10 + generator.nextInt(20);
      rects.clear();
      colors.clear();
      for (int i = 1; i <= n; i++)
      {
         int x = generator.nextInt(getWidth());
         int y = generator.nextInt(getHeight());
         int width = generator.nextInt(getWidth() - x);
         int height = generator.nextInt(getHeight() - y);
         rects.add(new Rectangle(x, y, width, height));
         int r = generator.nextInt(256);
         int g = generator.nextInt(256);
         int b = generator.nextInt(256);
         colors.add(new Color(r, g, b));
      }
      repaint();
   }

   public void paintComponent(Graphics g)
   {  
      if (rects.size() == 0) newDrawing();
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;      

      // dessine tous les rectangles
      for (int i = 0; i < rects.size(); i++)
      {
         g2.setPaint(colors.get(i));
         g2.fill(rects.get(i));      
      }
   }

   /**
      Crée un document SVG du dessin en cours.
      @return l'arbre DOM du document SVG
   */
   public Document buildDocument()
   {

      Document doc = builder.newDocument();
      Element svgElement = doc.createElement("svg");
      doc.appendChild(svgElement);
      svgElement.setAttribute("width", "" + getWidth());
      svgElement.setAttribute("height", "" + getHeight());
      
      for (int i = 0; i < rects.size(); i++)
      {
         Color c = colors.get(i);
         Rectangle2D r = rects.get(i);
         Element rectElement = doc.createElement("rect");
         rectElement.setAttribute("x", "" + r.getX());
         rectElement.setAttribute("y", "" + r.getY());
         rectElement.setAttribute("width", "" + r.getWidth());
         rectElement.setAttribute("height", "" + r.getHeight());
         rectElement.setAttribute("fill", colorToString(c));
         svgElement.appendChild(rectElement);
      }

      return doc;
   }

   /**
      Transforme une couleur en valeur hexa.
      @param c une couleur
      @return une chaîne de la forme #rrggbb
   */
   private static String colorToString(Color c)
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append(Integer.toHexString(
         c.getRGB() & 0xFFFFFF));
      while(buffer.length() < 6) buffer.insert(0, '0');
      buffer.insert(0, '#');
      return buffer.toString();
   }

   private ArrayList<Rectangle2D> rects;
   private ArrayList<Color> colors;
   private Random generator;
   private DocumentBuilder builder;
}
