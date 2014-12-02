Exemple 5.4 : SimpleTree.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;

/**
   Ce programme présente un Exemple d'arbre.
*/
public class SimpleTree
{
   public static void main(String[] args)
   {
      JFrame frame = new SimpleTreeFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Ce bloc contient un Exemple d'arbre qui affiche un
   modèle d'arbre construit manuellement.
*/
class SimpleTreeFrame extends JFrame
{
   public SimpleTreeFrame()
   {
      setTitle("SimpleTree");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // définition des données du modèle d'arbre

      DefaultMutableTreeNode root 
         = new DefaultMutableTreeNode("Monde");
      DefaultMutableTreeNode country 
         = new DefaultMutableTreeNode("USA");
      root.add(country);
      DefaultMutableTreeNode state 
         = new DefaultMutableTreeNode("Californie");
      country.add(state);
      DefaultMutableTreeNode city 
         = new DefaultMutableTreeNode("San José");
      state.add(city);
      city = new DefaultMutableTreeNode("Cupertino");
      state.add(city);
      state = new DefaultMutableTreeNode("Michigan");
      country.add(state);
      city = new DefaultMutableTreeNode("Ann Arbor");
      state.add(city);
      country = new DefaultMutableTreeNode("Allemagne");
      root.add(country);
      state = new DefaultMutableTreeNode("Schleswig-Holstein");
      country.add(state);
      city = new DefaultMutableTreeNode("Kiel");
      state.add(city);

      // construit l'arbre et le place dans un panneau déroulant
         
      JTree tree = new JTree(root);
      Container contentPane = getContentPane();
      contentPane.add(new JScrollPane(tree));
   }
   private static final int DEFAULT_WIDTH = 300;
   private static final int DEFAULT_HEIGHT = 200;
}
