Exemple 10.4 : GridBagPane.java

import java.awt.*;
import java.beans.*;
import java.io.*;
import java.lang.reflect.*;
import javax.swing.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import org.w3c.dom.ls.*;
import org.w3c.dom.bootstrap.*;

/**
   Ce panneau utilise un fichier XML pour décrire ses composants et
   leurs positions dans la disposition de grille.
*/
public class GridBagPane extends JPanel
{
   /**
      Construit un volet de grille.
      @param filename le nom du fichier XML qui décrit les
      composants du volet et leurs positions
   */
   public GridBagPane(String filename)
   {
      setLayout(new GridBagLayout());
      constraints = new GridBagConstraints();

      try
      {
         DocumentBuilderFactory factory 
            = DocumentBuilderFactory.newInstance();
         factory.setValidating(true);

         if (filename.contains("-schema"))
         {
            factory.setNamespaceAware(true);
            final String JAXP_SCHEMA_LANGUAGE =
               "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
            final String W3C_XML_SCHEMA =
               "http://www.w3.org/2001/XMLSchema"; 
            factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA); 
         }

         factory.setIgnoringElementContentWhitespace(true);

         DocumentBuilder builder = factory.newDocumentBuilder();
         Document doc = builder.parse(new File(filename));

         if (filename.contains("-schema")) 
            // contournement au bogue #4867706
         {
            int count = removeWhitespace(doc.getDocumentElement());
            System.out.println(count + " whitespace nodes removed.");
         }

         parseGridbag(doc.getDocumentElement());         
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   /**
      Supprime l'espace blanc du contenu d'élément 
      @param e L'élément racine
      @return Le nombre de noeuds d'espace blanc supprimés.
   */
   private int removeWhitespace(Element e)
   {
      NodeList children = e.getChildNodes();
      if (children.getLength() <= 1) return 0;
      int count = 0;
      for (int i = children.getLength() - 1; i >= 0; i--)
      {
         Node child = children.item(i);
         if (child instanceof Text && (
            (Text) child).getData().trim().length() == 0)
         {
            e.removeChild(child);
            count++;
         }
         else if (child instanceof Element)
            count += removeWhitespace((Element) child);
      }
      return count;
   }

   /**
      Extrait un composant avec un nom donné.
      @param name un nom de composant
      @return le composant avec le nom donné ou null lorsqu'aucun
      composant dans ce volet de grille ne possède le nom donné
   */
   public Component get(String name)
   {
      Component[] components = getComponents();
      for (int i = 0; i < components.length; i++)
      {
         if (components[i].getName().equals(name))
            return components[i];
      }
      return null;
   }

   /**
      Analyse un élément gridbag.
      @param e un élément gridbag
   */
   private void parseGridbag(Element e)
   {
      NodeList rows = e.getChildNodes();
      for (int i = 0; i < rows.getLength(); i++)
      {
         Element row = (Element) rows.item(i);
         NodeList cells = row.getChildNodes();
         for (int j = 0; j < cells.getLength(); j++)
         {
            Element cell = (Element) cells.item(j);
            parseCell(cell, i, j);
         }
      }
   }

   /**
      Analyse un élément de cellule.
      @param e un élément de cellule
      @param r la ligne de la cellule
      @param c la colonne de la cellule
   */
   private void parseCell(Element e, int r, int c)
   {
      // obtenir les attributs

      String value = e.getAttribute("gridx");
      if (value.length() == 0) // utiliser les paramètres par défaut
      {
         if (c == 0) constraints.gridx = 0;
         else constraints.gridy += constraints.gridwidth;
      }
      else 
         constraints.gridx = Integer.parseInt(value);

      value = e.getAttribute("gridy");
      if (value.length() == 0) // utiliser les paramètres par défaut
         constraints.gridy = r;
      else 
         constraints.gridx = Integer.parseInt(value);

      constraints.gridwidth
         = Integer.parseInt(e.getAttribute("gridwidth"));
      constraints.gridheight
         = Integer.parseInt(e.getAttribute("gridheight"));
      constraints.weightx
         = Integer.parseInt(e.getAttribute("weightx"));
      constraints.weighty
         = Integer.parseInt(e.getAttribute("weighty"));
      constraints.ipadx
         = Integer.parseInt(e.getAttribute("ipadx"));
      constraints.ipady
         = Integer.parseInt(e.getAttribute("ipady"));

      // utiliser une réflexion pour obtenir des valeurs d'entier de 
      // champs statiques
      Class cl = GridBagConstraints.class;

      try
      {  
         String name = e.getAttribute("fill");
         Field f = cl.getField(name);
         constraints.fill = f.getInt(cl);

         name = e.getAttribute("anchor");
         f = cl.getField(name);
         constraints.anchor = f.getInt(cl);
      }
      catch (Exception ex) // les méthodes de réflexion 
                           // peuvent lancer diverses exceptions
      {  
         ex.printStackTrace();
      }
      
      Component comp = (Component) parseBean(
         (Element) e.getFirstChild());
      add(comp, constraints);
   }

   /**
      Analyse un élément bean.
      @param e un élément bean
   */   
   private Object parseBean(Element e)
   {
      try
      {
         NodeList children = e.getChildNodes();
         Element classElement = (Element) children.item(0);
         String className 
            = ((Text) classElement.getFirstChild()).getData();
         
         Class cl = Class.forName(className);         
         
         Object obj = cl.newInstance();
         
         if (obj instanceof Component)
            ((Component) obj).setName(e.getAttribute("id"));
         
         for (int i = 1; i < children.getLength(); i++)
         {
            Node propertyElement = children.item(i);
            Element nameElement 
               = (Element) propertyElement.getFirstChild();
            String propertyName 
               = ((Text) nameElement.getFirstChild()).getData();
            
            Element valueElement 
               = (Element) propertyElement.getLastChild();
            Object value = parseValue(valueElement);
            BeanInfo beanInfo = Introspector.getBeanInfo(cl);
            PropertyDescriptor[] descriptors 
               = beanInfo.getPropertyDescriptors();
            boolean done = false;
            for (int j = 0; !done && j < descriptors.length; j++)
            {
               if (descriptors[j].getName().equals(propertyName))
               {
                  descriptors[j].getWriteMethod().invoke(obj, value);
                  done = true;
               }
            }
            
         }
         return obj;
      }
      catch (Exception ex) 
         // les méthodes de réflexion peuvent déclencher diverses 
         // exceptions
      {
         ex.printStackTrace();
         return null;
      }
   }

   /**
      Analyse un élément de valeur.
      @param e un élément de valeur
   */
   private Object parseValue(Element e)
   {
      Element child = (Element) e.getFirstChild();
      if (child.getTagName().equals("bean"))
         return parseBean(child);
      String text = ((Text) child.getFirstChild()).getData();
      if (child.getTagName().equals("int"))
         return new Integer(text);         
      else if (child.getTagName().equals("boolean"))
         return new Boolean(text);         
      else if (child.getTagName().equals("string"))
         return text;
      else 
         return null;
   }

   private GridBagConstraints constraints;
}
