Exemple 11.3 : ActionListenerInstaller.java

import java.awt.event.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
 
public class ActionListenerInstaller
{
   /**
      Traite toutes les annotations ActionListenerFor 
      dans l'objet donné.
      @param obj Un objet dont les méthodes peuvent avoir des 
      annotations ActionListenerFor 
   */
   public static void processAnnotations(Object obj)
   {
      try
      {
         Class cl = obj.getClass();
         for (Method m : cl.getDeclaredMethods())
         {
            ActionListenerFor a = m.getAnnotation(
               ActionListenerFor.class);
            if (a != null) 
            {
               Field f = cl.getDeclaredField(a.source());
               f.setAccessible(true);
               addListener(f.get(obj), obj, m);
            }
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
 
   /**
      Ajoute un écouteur d'action qui appelle une méthode donnée.
      @param source La source de l'événement auquel un écouteur
      d'action est ajouté
      @param param Le paramètre implicite de la méthode que 
      l'écouteur appelle
      @param m La méthode appelée par l'écouteur
   */
   public static void addListener(Object source, 
      final Object param, final Method m)
      throws NoSuchMethodException, IllegalAccessException, 
      InvocationTargetException
   {
      InvocationHandler handler = new
         InvocationHandler()
         {
            public Object invoke(Object proxy, Method mm, Object[] args) 
            throws Throwable
            {
               return m.invoke(param);                    
            }
         };
 
      Object listener = Proxy.newProxyInstance(null,
         new Class[] { java.awt.event.ActionListener.class },
         handler);
      Method adder = source.getClass().getMethod("addActionListener", 
         ActionListener.class);
      adder.invoke(source, listener);
   }
}
