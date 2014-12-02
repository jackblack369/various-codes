Exemple 5.8 : ObjectInspectorTest.java

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

/**
   Ce programme montre comment utiliser un modèle d'arbre personnalisé.
   Il affiche les champs d'un objet.
*/
public class ObjectInspectorTest
{
   public static void main(String[] args)
   {
      JFrame frame = new ObjectInspectorFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);      
   }
}

/**
   Ce bloc contient l'arbre d'objets. 
*/
class ObjectInspectorFrame extends JFrame
{
   public ObjectInspectorFrame()
   {
      setTitle("ObjectInspectorTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // nous examinons ce cadre

      Variable v = new Variable(getClass(), "this", this);
      ObjectTreeModel model = new ObjectTreeModel();
      model.setRoot(v);

      // construit et affiche l'arbre 

         tree = new JTree(model);         
         add(new JScrollPane(tree), 
            BorderLayout.CENTER);
   }

   private JTree tree;
   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT = 300;
}

/**
   Ce modèle d'arbre décrit la structure d'arbre d'un objet Java.   
   Les enfants sont les objets stockés dans des variables d'instance.
*/
class ObjectTreeModel implements TreeModel 
{
   /**
      Construit un arbre vide.
   */
   public ObjectTreeModel() 
   {
      root = null;
   }
   
   /**
      Définit la racine sur une variable donnée.
      @param v la variable décrite par cet arbre
   */
   public void setRoot(Variable v)
   {
      Variable oldRoot = v;
      root = v;
      fireTreeStructureChanged(oldRoot);
   }

   public Object getRoot() 
   {
      return root;
   }

   public int getChildCount(Object parent) 
   {
      return ((Variable)parent).getFields().size();
   }

   public Object getChild(Object parent, int index) 
   {
      ArrayList<Field> fields = ((Variable)parent).getFields();
      Field f = (Field)fields.get(index);
      Object parentValue = ((Variable)parent).getValue();
      try
      {
         return new Variable(f.getType(), f.getName(), 
            f.get(parentValue));
      }
      catch(IllegalAccessException e)
      {
         return null;
      }
   }

   public int getIndexOfChild(Object parent, Object child)
   {
      int n = getChildCount(parent);
      for (int i = 0; i < n; i++)
         if (getChild(parent, i).equals(child))
            return i;
      return -1;
   }

   public boolean isLeaf(Object node) 
   {
      return getChildCount(node) == 0;
   }

   public void valueForPathChanged(TreePath path,
      Object newValue) 
   {}

   public void addTreeModelListener(TreeModelListener l)
   {
      listenerList.add(TreeModelListener.class, l);
   }

   public void removeTreeModelListener(TreeModelListener l)
   {
     listenerList.remove(TreeModelListener.class, l);
   }

   protected void fireTreeStructureChanged(Object oldRoot) 
   {
      TreeModelEvent event 
         = new TreeModelEvent(this, new Object[] {oldRoot});
    EventListener[] listeners = listenerList.getListeners(
         TreeModelListener.class);
      for (int i = 0; i < listeners.length; i++)
         ((TreeModelListener)listeners[i]).treeStructureChanged(
            event);
   }

   private Variable root;
   private EventListenerList listenerList 
      = new EventListenerList();
}

/**
   Une variable avec un type, un nom et une valeur.
*/
class Variable
{
   /**
      Construit une variable.
      @param aType le type
      @param aName le nom
      @param aValue la valeur
   */
  public Variable(Class aType, String aName, Object aValue)
   {
      type = aType;
      name = aName;
      value = aValue;
      fields = new ArrayList<Field>();
      
      // trouve tous les champs si nous avons un type de classe 
      // sauf que nous n'ouvrons pas les chaînes ni les valeurs nulles

      if (!type.isPrimitive() && !type.isArray() &&
         !type.equals(String.class) && value != null) 
      {
         // obtient des champs de la classe et de toutes les superclasses
         for (Class c = value.getClass(); c != null; 
            c = c.getSuperclass())
         {
            Field[] fs = c.getDeclaredFields();
            AccessibleObject.setAccessible(fs, true);
            
            // obtient tous les champs non-statiques
            for (Field f : fs)
               if ((f.getModifiers() & Modifier.STATIC) == 0)
                  fields.add(f);
         }
      }
   }
  
   /**
      Obtient la valeur de cette variable.
      @return la valeur
   */
   public Object getValue() { return value; }
   
   /**
      Obtient tous les champs non-statiques de cette variable.
      @return une liste de tableau des variables décrivant les champs
   */
   public ArrayList<Field> getFields() { return fields; }
   
   public String toString()
   {
      String r = type + " " + name;
      if (type.isPrimitive())
         r += "=" + value;
      else if (type.equals(String.class))
         r += "=" + value;
      else if (value == null)
         r += "=null";
      return r;
   }

   private Class type;
   private String name;
   private Object value;
   private ArrayList<Field> fields;
}
