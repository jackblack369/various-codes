Exemple 5.6 : ClassTree.java

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

/**
   Ce programme montre l'affichage de cellule en présentant
   un arbre de classes et leurs superclasses.
*/
public class ClassTree
{
   public static void main(String[] args)
   {
      JFrame frame = new ClassTreeFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Ce bloc affiche l'arbre de classe ainsi qu'un champ de texte et
   ajoute un bouton pour ajouter des classes à l'arbre.
*/
class ClassTreeFrame extends JFrame
{
   public ClassTreeFrame()
   {
      setTitle("ClassTree");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // la racine de l'arbre est Object
      root = new DefaultMutableTreeNode(java.lang.Object.class);
      model = new DefaultTreeModel(root);
      tree = new JTree(model);
      
      // ajoute cette classe pour placer des données dans l'arbre 
      addClass(getClass());

      // définit les icônes des nœuds 
      ClassNameTreeCellRenderer renderer 
         = new ClassNameTreeCellRenderer();
      renderer.setClosedIcon(new ImageIcon("red-ball.gif"));
      renderer.setOpenIcon(new ImageIcon("yellow-ball.gif"));
      renderer.setLeafIcon(new ImageIcon("blue-ball.gif"));
      tree.setCellRenderer(renderer);      

      add(new JScrollPane(tree), BorderLayout.CENTER);

      addTextField();
   }

   /**
      Ajoute le champ de texte et le bouton "Ajouter" pour ajouter 
      une nouvelle classe.
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

      // les nouveaux noms de classes sont saisis dans ce champ de texte
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
      @param obj l'objet à retrouver
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
      
      // si la classe se trouve déjà dans l'arbre, renvoie ce nœud
      DefaultMutableTreeNode node = findUserObject(c);
      if (node != null) return node;
   
      // la classe n'est pas présente, ajoute d'abord le parent de la 
      // classe de manière récursive

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
   
   private DefaultMutableTreeNode root;
   private DefaultTreeModel model;
   private JTree tree;
   private JTextField textField;
   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT = 300;
}

/**
   Cette classe affiche un nom de classe en normal ou italique.
   Les classes abstraites sont en italique.
*/
class ClassNameTreeCellRenderer extends DefaultTreeCellRenderer
{
   public Component getTreeCellRendererComponent(JTree tree,
      Object value, boolean selected, boolean expanded,
      boolean leaf, int row, boolean hasFocus)
   {
      super.getTreeCellRendererComponent(tree, value,
         selected, expanded, leaf, row, hasFocus);
      // récupère l'objet utilisateur 
      DefaultMutableTreeNode node 
         = (DefaultMutableTreeNode)value;
      Class c = (Class)node.getUserObject();

      // la première fois, dérive une police italique à partir de la 
      // police normale
      if (plainFont == null)
      {
         plainFont = getFont();
         // l'afficheur de cellule d'arbre est parfois appelé avec une 
         // étiquette sans police
         if (plainFont != null)
            italicFont = plainFont.deriveFont(Font.ITALIC);
      }

      // transforme la police en italique si la classe est abstraite, 
      // reste normale dans le cas contraire
      if ((c.getModifiers() & Modifier.ABSTRACT) == 0)
         setFont(plainFont);
      else
         setFont(italicFont);
      return this;
   }

   private Font plainFont = null;
   private Font italicFont = null;
}
