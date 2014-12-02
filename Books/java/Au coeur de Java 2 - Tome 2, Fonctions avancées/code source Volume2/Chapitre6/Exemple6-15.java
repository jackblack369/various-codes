Exemple 6.15 : DamageReporter.java

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.beans.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
 
/**
   Ce programme montre l'utilisation d'un encodeur et d'un décodeur XML.
   Le code de la GUI et du dessin est collecté dans cette classe. Les 
   seuls éléments intéressants sont les écouteurs d'action pour openItem 
   et saveItem. Vous verrez les personnalisations d'encodeur dans 
   la classe DamageReport.
*/
public class DamageReporter extends JFrame
{
   public static void main(String[] args)
   {
      JFrame frame = new DamageReporter();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
 
   public DamageReporter()
   {
      setTitle("DamageReporter");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
 
      chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));
 
      report = new DamageReport();
      report.setCarType(DamageReport.CarType.SEDAN);
 
      // Configurer la barre de menus
      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
 
      JMenu menu = new JMenu("Fichier");
      menuBar.add(menu);
 
      JMenuItem openItem = new JMenuItem("Ouvrir");
      menu.add(openItem);
      openItem.addActionListener(new 
         ActionListener()
         {
            public void actionPerformed(ActionEvent evt)
            {
               // Afficher la boîte de dialogue de choix du fichier
               int r = chooser.showOpenDialog(null);
 
               // si le fichier est sélectionné, ouvrir
               if(r == JFileChooser.APPROVE_OPTION)
               {
                  try
                  {
                     File file = chooser.getSelectedFile();
                     XMLDecoder decoder = new XMLDecoder(
                        new FileInputStream(file));
                     report = (DamageReport) decoder.readObject();
                     decoder.close();
                     repaint();
                  }
                  catch (IOException e)
                  {
                     JOptionPane.showMessageDialog(null, e);
                  }
               }
            }
         });
 
      JMenuItem saveItem = new JMenuItem("Enregistrer");
      menu.add(saveItem);
      saveItem.addActionListener(new 
         ActionListener()
         {
            public void actionPerformed(ActionEvent evt)
            {
               report.setRentalRecord(rentalRecord.getText());
               chooser.setSelectedFile(new File(
                  rentalRecord.getText() + ".xml"));
 
               // Afficher la boîte de dialogue de choix du fichier
               int r = chooser.showSaveDialog(null);
 
               // si le fichier est sélectionné, sauvegarder
               if(r == JFileChooser.APPROVE_OPTION)
               {                  
                  try
                  {
                     File file = chooser.getSelectedFile();
                     XMLEncoder encoder = new XMLEncoder(
                        new FileOutputStream(file));
                     report.configureEncoder(encoder);
                     encoder.writeObject(report);
                     encoder.close();
                  }
                  catch (IOException e)
                  {
                     JOptionPane.showMessageDialog(null, e);
                  }
               }
            }
         });
 
      JMenuItem exitItem = new JMenuItem("Quitter");
      menu.add(exitItem);
      exitItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               System.exit(0);
            }
         });
 
      // liste déroulante pour le type de voiture
      rentalRecord = new JTextField();
      carType = new JComboBox();
      carType.addItem(DamageReport.CarType.SEDAN);
      carType.addItem(DamageReport.CarType.WAGON);
      carType.addItem(DamageReport.CarType.SUV);
 
      carType.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               DamageReport.CarType item = (DamageReport.CarType) 
                  carType.getSelectedItem();
               report.setCarType(item);
               repaint();
            }
         });
 
      // panneau pour afficher la voiture
      carPanel = new 
         JPanel() 
         {
            public void paintComponent(Graphics g)
            {
               super.paintComponent(g);
               Graphics2D g2 = (Graphics2D) g;
               g2.draw((Shape) shapes.get(report.getCarType()));
               report.drawDamage(g2);
            }
         };
      carPanel.addMouseListener(new
         MouseAdapter()
         {
            public void mousePressed(MouseEvent event)
            {
               report.click(new Point2D.Double(event.getX(), 
                  event.getY()));
               repaint();
            }
         });
      carPanel.setBackground(new Color(0.9f, 0.9f, 0.45f));
 
      // Boutons radio pour les actions de clic
      addButton = new JRadioButton("Ajouter");
      removeButton = new JRadioButton("Supprimer");
      ButtonGroup group = new ButtonGroup();
      JPanel buttonPanel = new JPanel();
      group.add(addButton);
      buttonPanel.add(addButton);
      group.add(removeButton);
      buttonPanel.add(removeButton);      
      addButton.setSelected(!report.getRemoveMode());
      removeButton.setSelected(report.getRemoveMode());
      addButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               report.setRemoveMode(false);
            }
         });
      removeButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               report.setRemoveMode(true);
            }
         });
 
      // Composants de mise en page
      JPanel gridPanel = new JPanel();
      gridPanel.setLayout(new GridLayout(0, 2));
      gridPanel.add(new JLabel("Rapport de location"));
      gridPanel.add(rentalRecord);
      gridPanel.add(new JLabel("Type de voiture"));
      gridPanel.add(carType);      
      gridPanel.add(new JLabel("Opération"));
      gridPanel.add(buttonPanel);      
 
      add(gridPanel, BorderLayout.NORTH);
      add(carPanel, BorderLayout.CENTER);
   }
 
   private JTextField rentalRecord;
   private JComboBox carType;
   private JPanel carPanel;
   private JRadioButton addButton;
   private JRadioButton removeButton;
   private DamageReport report;
   private JFileChooser chooser;
 
   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT = 400;
 
   private static Map<DamageReport.CarType, Shape> shapes 
      = new EnumMap<DamageReport.CarType, 
      Shape>(DamageReport.CarType.class);
 
   static 
   {
      int width = 200;
      int height = 100;
      int x = 50;
      int y = 50;
      Rectangle2D.Double body = new Rectangle2D.Double(
         x, y + width / 6, width - 1, width / 6);
      Ellipse2D.Double frontTire = new Ellipse2D.Double(
         x + width / 6, y + width / 3, 
         width / 6, width / 6);
      Ellipse2D.Double rearTire = new Ellipse2D.Double(
         x + width * 2 / 3, y + width / 3,
         width / 6, width / 6);
 
      Point2D.Double p1 = new Point2D.Double(
         x + width / 6, y + width / 6);
      Point2D.Double p2 = new Point2D.Double(x + width / 3, y);
      Point2D.Double p3 = new Point2D.Double(x + width * 2 / 3, y);
      Point2D.Double p4 = new Point2D.Double(
         x + width * 5 / 6, y + width / 6);
 
      Line2D.Double frontWindshield = new Line2D.Double(p1, p2);
      Line2D.Double roofTop = new Line2D.Double(p2, p3);
      Line2D.Double rearWindshield = new Line2D.Double(p3, p4);
 
      GeneralPath sedanPath = new GeneralPath();
      sedanPath.append(frontTire, false);
      sedanPath.append(rearTire, false);
      sedanPath.append(body, false);
      sedanPath.append(frontWindshield, false);
      sedanPath.append(roofTop, false);
      sedanPath.append(rearWindshield, false);      
      shapes.put(DamageReport.CarType.SEDAN, sedanPath);
 
      Point2D.Double p5 = new Point2D.Double(x + width * 11 / 12, y);
      Point2D.Double p6 = new Point2D.Double(x + width, y + width / 6);
      roofTop = new Line2D.Double(p2, p5);
      rearWindshield = new Line2D.Double(p5, p6);
 
      GeneralPath wagonPath = new GeneralPath();
      wagonPath.append(frontTire, false);
      wagonPath.append(rearTire, false);
      wagonPath.append(body, false);
      wagonPath.append(frontWindshield, false);
      wagonPath.append(roofTop, false);
      wagonPath.append(rearWindshield, false);      
      shapes.put(DamageReport.CarType.WAGON, wagonPath);
 
      Point2D.Double p7 = new Point2D.Double(
         x + width / 3, y - width / 6);
      Point2D.Double p8 = new Point2D.Double(
         x + width * 11 / 12, y - width / 6);
      frontWindshield = new Line2D.Double(p1, p7);
      roofTop = new Line2D.Double(p7, p8);
      rearWindshield = new Line2D.Double(p8, p6);
 
      GeneralPath suvPath = new GeneralPath();
      suvPath.append(frontTire, false);
      suvPath.append(rearTire, false);
      suvPath.append(body, false);
      suvPath.append(frontWindshield, false);
      suvPath.append(roofTop, false);
      suvPath.append(rearWindshield, false);      
      shapes.put(DamageReport.CarType.SUV, suvPath);
   }
}
