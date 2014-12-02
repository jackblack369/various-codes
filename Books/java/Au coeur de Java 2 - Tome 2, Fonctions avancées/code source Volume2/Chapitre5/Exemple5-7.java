Exemple 5.7 : ClassBrowserTree.java

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

/**
   Ce programme présente des événements de sélection d'arbres.
*/
public class ClassBrowserTest
{
   public static void main(String[] args)
   {
      JFrame frame = new ClassBrowserTestFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un bloc avec un arbre de classe, une zone de texte présentant les 
   propriétés de la classe sélectionnée et un champ de texte pour ajouter
   de nouvelles classes.
*/
class ClassBrowserTestFrame extends JFrame
{
   public ClassBrowserTestFrame()
   {
      setTitle("ClassBrowserTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // la racine de l'arbre est Object
      root = new DefaultMutableTreeNode(java.lang.Object.class);
      model = new DefaultTreeModel(root);
      tree = new JTree(model);
      
      // ajoute cette classe pour remplir l'arbre avec quelques données
      addClass(getClass());

      // définit le mode de sélection
      tree.addTreeSelectionListener(new
         TreeSelectionListener()
         {
            public void valueChanged(TreeSelectionEvent event)
            {  
               // l'utilisateur a sélectionné un noeud différent
               // --mise à jour de la description
               TreePath path = tree.getSelectionPath();
               if (path == null) return;
               DefaultMutableTreeNode selectedNode
                  = (DefaultMutableTreeNode)
                  path.getLastPathComponent();
               Class c = (Class)selectedNode.getUserObject();
               String description = getFieldDescription(c);
               textArea.setText(description);
            }
         });
      int mode = TreeSelectionModel.SINGLE_TREE_SELECTION;
      tree.getSelectionModel().setSelectionMode(mode);
      
      // cette zone de texte contient la description de la classe 
      textArea = new JTextArea();

      // ajoute l'arbre et la zone de texte au panneau
      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(1, 2));
      panel.add(new JScrollPane(tree));
      panel.add(new JScrollPane(textArea));

      add(panel, BorderLayout.CENTER);

      addTextField();
   }

   /**
      Ajoute le champ de texte et le bouton "Ajouter" pour ajouter une
      nouvelle classe.
   */
   public void addTextField()
   {
      JPanel panel = new JPanel();

      ActionListener addListener = new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               // ajoute la classe dont le nom se trouve dans le champ de 
               // texte 
               try
               {
                  String text = textField.getText();
                  addClass(Class.forName(text));
                  // efface le champ de texte pour indiquer le succès de 
                  // l'opération 
                 textField.setText("");
               }
               catch (ClassNotFoundException e)
               {  
                  JOptionPane.showMessageDialog(null, 
                     "Classe introuvable");
               }
            }
         };
 
      // les noms des nouvelles classes sont saisis dans ce champ de 
      // texte
      textField = new JTextField(20);
      textField.addActionListener(addListener);
      panel.add(textField);

      JButton addButton = new JButton("Ajouter");
      addButton.addActionListener(addListener);
      panel.add(addButton);

      add(panel, BorderLayout.SOUTH);
   }

   /**
      Retrouve un objet dans l'arbre.
      @param obj l'objet à trouver
      @return le noeud contenant l'objet ou null
      si l'objet ne figure pas dans l'arbre
   */
   public DefaultMutableTreeNode findUserObject(Object obj)
   {
      // trouve le nœud contenant un objet utilisateur 
      Enumeration e = root.breadthFirstEnumeration();
      while (e.hasMoreElements())
      {
         DefaultMutableTreeNode node 
            = (DefaultMutableTreeNode)e.nextElement();
         if (node.getUserObject().equals(obj))
            return node;
      }
      return null;
   }
   
   /**
      Ajoute une nouvelle classe et toute classe parent qui ne fait pas
      encore partie de l'arbre.
      @param c la classe à ajouter
      @return le noeud nouvellement ajouté
   */
   public DefaultMutableTreeNode addClass(Class c)
   {
      // ajoute une nouvelle classe dans l'arbre 
   
      // saute les types qui ne sont pas des classes 
      if (c.isInterface() || c.isPrimitive()) return null;
      
      // Si la classe est déjà dans l'arbre, renvoie ce nœud 
      DefaultMutableTreeNode node = findUserObject(c);
      if (node != null) return node;
   
      // la classe n'est pas présente, commence par ajouter le parent de 
      // la classe récursivement

      Class s = c.getSuperclass();

      DefaultMutableTreeNode parent;
      if (s == null) 
         parent = root;
      else
         parent = addClass(s);
    
      // ajoute la classe comme enfant du parent
      DefaultMutableTreeNode newNode 
        = new DefaultMutableTreeNode(c);
      model.insertNodeInto(newNode, parent, 
         parent.getChildCount());
      
      // rend le nœud visible 
      TreePath path = new TreePath(model.getPathToRoot(newNode));
      tree.makeVisible(path);
      
      return newNode;
   }
   
   /**
      Renvoie une description des champs d'une classe.
      @param la classe à décrire
      @return une chaîne contenant tous les types et noms de champs
   */
   public static String getFieldDescription(Class c)
   {
      // utilise une réflexion pour trouver les types et les noms des 
      // champs
      StringBuilder r = new StringBuilder();
      Field[] fields = c.getDeclaredFields();
      for (int i = 0; i < fields.length; i++)
      {
         Field f = fields[i];
         if ((f.getModifiers() & Modifier.STATIC) != 0)
                       r.append("static ");
                   r.append(f.getType().getName());
                   r.append(" ");
                   r.append(f.getName());
                   r.append("\n");
                 }
                 return r.toString();
   }
   
   private DefaultMutableTreeNode root;
   private DefaultTreeModel model;
   private JTree tree;
   private JTextField textField;
   private JTextArea textArea;
   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT = 300;
}
