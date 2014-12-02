Exemple 9.9 : SliderTest.java

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class SliderTest
{
   public static void main(String[] args)
   {  
      SliderTestFrame frame = new SliderTestFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec une série de curseurs et une zone de texte pour
      afficher les valeurs du curseur.
*/
class SliderTestFrame extends JFrame
{  
   public SliderTestFrame()
   {  
      setTitle("SliderTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      sliderPanel = new JPanel();
      sliderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

       // écouteur commun à tous les curseurs
      listener = new 
         ChangeListener()
         {  
            public void stateChanged(ChangeEvent event)
            {  
               // mettre à jour le champ de texte lorsque la
               // valeur du curseur change
               JSlider source = (JSlider) event.getSource();
               textField.setText("" + source.getValue());
            }
         };

      // ajouter un curseur normal

      JSlider slider = new JSlider();
      addSlider(slider, "Normal");

      // ajouter un curseur avec des repères grands et petits 

      slider = new JSlider();
      slider.setPaintTicks(true);
      slider.setMajorTickSpacing(20);
      slider.setMinorTickSpacing(5);
      addSlider(slider, "Ticks");

      // ajouter un curseur qui s'aligne sur les repères 

      slider = new JSlider();
      slider.setPaintTicks(true);
      slider.setSnapToTicks(true);
      slider.setMajorTickSpacing(20);
      slider.setMinorTickSpacing(5);
      addSlider(slider, "Snap to ticks");

      // ajouter un curseur sans couloir

      slider = new JSlider();
      slider.setPaintTicks(true);
      slider.setMajorTickSpacing(20);
      slider.setMinorTickSpacing(5);
      slider.setPaintTrack(false);
      addSlider(slider, "No track");

      // ajouter un curseur inversé

      slider = new JSlider();
      slider.setPaintTicks(true);
      slider.setMajorTickSpacing(20);
      slider.setMinorTickSpacing(5);
      slider.setInverted(true);
      addSlider(slider, "Inverted");

      // ajouter un curseur avec des libellés numériques 

      slider = new JSlider();
      slider.setPaintTicks(true);
      slider.setPaintLabels(true);
      slider.setMajorTickSpacing(20);
      slider.setMinorTickSpacing(5);
      addSlider(slider, "Labels");

      // ajouter un curseur avec libellés alphabétiques 

      slider = new JSlider();
      slider.setPaintLabels(true);
      slider.setPaintTicks(true);
      slider.setMajorTickSpacing(20);
      slider.setMinorTickSpacing(5);

      Dictionary<Integer, Component> labelTable = 
         new Hashtable<Integer, Component>();
      labelTable.put(0, new JLabel("A"));
      labelTable.put(20, new JLabel("B"));
      labelTable.put(40, new JLabel("C"));
      labelTable.put(60, new JLabel("D"));
      labelTable.put(80, new JLabel("E"));
      labelTable.put(100, new JLabel("F"));

      slider.setLabelTable(labelTable);
      addSlider(slider, "Custom labels");

      // ajouter un curseur avec des icônes comme libellés

      slider = new JSlider();
      slider.setPaintTicks(true);
      slider.setPaintLabels(true);
      slider.setSnapToTicks(true);
      slider.setMajorTickSpacing(20);
      slider.setMinorTickSpacing(20);

      labelTable = new Hashtable<Integer, Component>();

      // ajoutés les images de cartes

      labelTable.put(0,
         new JLabel(new ImageIcon("nine.gif")));
      labelTable.put(20,
         new JLabel(new ImageIcon("ten.gif")));
      labelTable.put(40,
         new JLabel(new ImageIcon("jack.gif")));
      labelTable.put(60,
         new JLabel(new ImageIcon("queen.gif")));
      labelTable.put(80,
         new JLabel(new ImageIcon("king.gif")));
      labelTable.put(100,
         new JLabel(new ImageIcon("ace.gif")));

      slider.setLabelTable(labelTable);
      addSlider(slider, "Icon labels");

      // ajouter le champ de texte affichant la valeur du curseur

      textField = new JTextField();
      add(sliderPanel, BorderLayout.CENTER);
      add(textField, BorderLayout.SOUTH);
   }

    /**
      Ajoute un curseur au panneau curseur et attache l'écouteur 
      @param s Le curseur
      @param description La description du curseur
   */
   public void addSlider(JSlider s, String description)
   {        
      s.addChangeListener(listener);
      JPanel panel = new JPanel();
      panel.add(s);
      panel.add(new JLabel(description));
      sliderPanel.add(panel);
   }

   public static final int DEFAULT_WIDTH = 350;
   public static final int DEFAULT_HEIGHT = 450;  

   private JPanel sliderPanel;
   private JTextField textField;
   private ChangeListener listener;
}
