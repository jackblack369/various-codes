Exemple 10.7 : XPathTest.java

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.xml.namespace.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import org.xml.sax.*;
 
/**
   Ce programme évalue les expressions XPath.
*/
public class XPathTest
{ 
   public static void main(String[] args)
   {  
      JFrame frame = new XPathFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}
 
/**
   Ce cadre présente un document XML, un panneau pour taper une
   expression XPath et un champ de texte pour afficher le résultat.
*/
class XPathFrame extends JFrame
{  
   public XPathFrame()
   {  
      setTitle("XPathTest");
 
      JMenu fileMenu = new JMenu("Fichier");
      JMenuItem openItem = new JMenuItem("Ouvrir");
      openItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event) 
               { openFile(); }
         });
      fileMenu.add(openItem);
 
      JMenuItem exitItem = new JMenuItem("Quitter");
      exitItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event) 
               { System.exit(0); }
         });
      fileMenu.add(exitItem);
 
      JMenuBar menuBar = new JMenuBar();
      menuBar.add(fileMenu);
      setJMenuBar(menuBar);
 
      ActionListener listener = new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event) 
               { evaluate(); }
         };
      expression = new JTextField(20);
      expression.addActionListener(listener);
      JButton evaluateButton = new JButton("Evaluer");
      evaluateButton.addActionListener(listener);
 
      typeCombo = new JComboBox(new Object[] 
         { "STRING", "NODE", "NODESET", "NUMBER", "BOOLEAN" });
      typeCombo.setSelectedItem("STRING");
 
      JPanel panel = new JPanel();
      panel.add(expression);
      panel.add(typeCombo);
      panel.add(evaluateButton);
      docText = new JTextArea(10, 40);
      result = new JTextField(); 
      result.setBorder(new TitledBorder("Résultat"));
 
      add(panel, BorderLayout.NORTH);
      add(new JScrollPane(docText), BorderLayout.CENTER);
      add(result, BorderLayout.SOUTH);
 
      try
      {
         DocumentBuilderFactory factory = 
            DocumentBuilderFactory.newInstance();
         builder = factory.newDocumentBuilder();
      }
      catch (ParserConfigurationException e)
      {
         JOptionPane.showMessageDialog(this, e);
      }
 
      XPathFactory xpfactory = XPathFactory.newInstance();
      path = xpfactory.newXPath();
      pack();
   }
 
   /**
      Ouvre un fichier et charge le document.
   */
   public void openFile()
   {  
      JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));
 
      chooser.setFileFilter(new
         javax.swing.filechooser.FileFilter()
         {  
            public boolean accept(File f)
            {  
               return f.isDirectory() || 
                  f.getName().toLowerCase().endsWith(".xml");
            }
            public String getDescription() { return "Fichiers XML"; }
         });
      int r = chooser.showOpenDialog(this);
      if (r != JFileChooser.APPROVE_OPTION) return;
      File f = chooser.getSelectedFile();
      try
      {
         byte[] bytes = new byte[(int) f.length()];
         new FileInputStream(f).read(bytes);
         docText.setText(new String(bytes));
         doc = builder.parse(f);
      }
      catch (IOException e)
      {
         JOptionPane.showMessageDialog(this, e);
      }
      catch (SAXException e)
      {
         JOptionPane.showMessageDialog(this, e);
      }
   }
 
   public void evaluate()
   {
 
      try
      {
         String typeName = (String) typeCombo.getSelectedItem();
         QName returnType = (QName) 
            XPathConstants.class.getField(typeName).get(null);
         Object evalResult = path.evaluate(expression.getText(), 
            doc, returnType);
         if (typeName.equals("NODESET"))
         {
            NodeList list = (NodeList) evalResult;
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            for (int i = 0; i < list.getLength(); i++)
            {
               if (i > 0) builder.append(", ");
               builder.append("" + list.item(i));
            }
            builder.append("}");
            result.setText("" + builder);
         }
         else
            result.setText("" + evalResult);
      }
      catch (XPathExpressionException e)
      {
         result.setText("" + e);
      }
      catch (Exception e) // exception de réflexion
      {
         e.printStackTrace();
      }
   }
 
   private DocumentBuilder builder;
   private Document doc;
   private XPath path;
   private JTextField expression;
   private JTextField result;
   private JTextArea docText;
   private JComboBox typeCombo;
}
