Exemple 5.5 : TreeEditTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;

/**
   Ce programme montre la modification d'arbre.
*/
public class TreeEditTest
{
   public static void main(String[] args)
   {
      JFrame frame = new TreeEditFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un bloc avec un arbre et des boutons pour modifier l'arbre.
*/
class TreeEditFrame extends JFrame
{
   public TreeEditFrame()
   {
      setTitle("TreeEditTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
         
      // construit l'arbre 

      TreeNode root = makeSampleTree();   
      model = new DefaultTreeModel(root);
      tree = new JTree(model);
      tree.setEditable(true);
      
      // ajoute un panneau déroulant contenant un arbre 

      JScrollPane scrollPane = new JScrollPane(tree);
      add(scrollPane, BorderLayout.CENTER);
      
      makeButtons();
   }
   
   public TreeNode makeSampleTree()
   {
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
      city = new DefaultMutableTreeNode("San Diego");
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
      return root;
   }
   
   /**
      Crée des boutons pour ajouter un frère, un enfant et 
      effacer un nœud.
   */
   public void makeButtons()
   {
      JPanel panel = new JPanel();
      JButton addSiblingButton = new JButton("Ajouter un frère");
      addSiblingButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               DefaultMutableTreeNode selectedNode 
                  = (DefaultMutableTreeNode)
                  tree.getLastSelectedPathComponent();

               if (selectedNode == null) return; 

         DefaultMutableTreeNode parent
            = (DefaultMutableTreeNode)
            selectedNode.getParent();

            if (parent == null) return;

            DefaultMutableTreeNode newNode
             = new DefaultMutableTreeNode("Nouveau");
               
               int selectedIndex = parent.getIndex(selectedNode);
               model.insertNodeInto(newNode, parent, 
                   selectedIndex + 1);

      // Affiche maintenant le nouveau nœud 

      TreeNode[] nodes = model.getPathToRoot(newNode);
      TreePath path = new TreePath(nodes);
      tree.scrollPathToVisible(path);
   }
         });
      panel.add(addSiblingButton);

      JButton addChildButton = new JButton("Ajouter un enfant");
      addChildButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               DefaultMutableTreeNode selectedNode
                  = (DefaultMutableTreeNode)
                  tree.getLastSelectedPathComponent();

               if (selectedNode == null) return;

               DefaultMutableTreeNode newNode
                  = new DefaultMutableTreeNode("Nouveau");
               model.insertNodeInto(newNode, selectedNode,
                  selectedNode.getChildCount());               

               // affiche maintenant le nouveau noeud
               
               TreeNode[] nodes = model.getPathToRoot(newNode);
               TreePath path = new TreePath(nodes);
               tree.scrollPathToVisible(path);
             }
});
panel.add(addChildButton);

      JButton deleteButton = new JButton("Supprimer");
      deleteButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               DefaultMutableTreeNode selectedNode
                  = (DefaultMutableTreeNode)
                  tree.getLastSelectedPathComponent();

               if (selectedNode != null && 
                  selectedNode.getParent() != null)
                  model.removeNodeFromParent(selectedNode);
            }
         });
      panel.add(deleteButton);
      add(panel, BorderLayout.SOUTH);
   }

   private DefaultTreeModel model;
   private JTree tree;
   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT = 200;
}
