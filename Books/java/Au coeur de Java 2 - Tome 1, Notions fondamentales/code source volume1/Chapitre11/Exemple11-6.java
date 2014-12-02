Exemple 11.6 : EventTracer.java

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.lang.reflect.*;

public class EventTracer
{
   public EventTracer()
   {
      // le gestionnaire pour tous les proxies d'événement
      handler = new
         InvocationHandler()
         {
            public Object invoke(Object proxy,
               Method method, Object[] args)
            {
               System.out.println(method + ":" + args[0]);
               return null;
            }
         };
   }

   /**
      Ajoute des traceurs d'événements pour tous les événements 
      que ce composant et ses enfants peuvent écouter
      @param c Un composant
   */
   public void add(Component c)
   {
      try
      {
         // récupérer tous les événements que ce composant peut écouter
         BeanInfo info = Introspector.getBeanInfo(c.getClass());

         EventSetDescriptor[] eventSets
            = info.getEventSetDescriptors();
         for (EventSetDescriptor eventSet : eventSets)
            addListener(c, eventSet);
      }
      catch (IntrospectionException e) {}
      // ok pour ne pas ajouter d'écouteurs si l'exception est lancée

      if (c instanceof Container)
      {
         // récupérer tous les enfants et appeler add 
         // de manière récurrente
         for (Component comp : ((Container) c).getComponents())
            add(comp);
      }
   }

   /**
      Ajouter un écouteur au jeu d'événements donné
      @param c Un composant
      @param eventSet Un descripteur d'une interface d'écouteur
   */
   public void addListener(Component c, EventSetDescriptor eventSet)
   {
      // créer l'objet proxy pour ce type d'écouteur 
      // et acheminer tous les appels au gestionnaire
      Object proxy = Proxy.newProxyInstance(null,
         new Class[] { eventSet.getListenerType() }, handler);

      // ajouter le proxy au composant sous forme d'écouteur 
      Method addListenerMethod = eventSet.getAddListenerMethod();
      try
      {
         addListenerMethod.invoke(c, proxy);
      }
      catch(InvocationTargetException e) {}
      catch(IllegalAccessException e) {}
      // ok pour ne pas ajouter d'écouteur si l'exception est lancée
   }

   private InvocationHandler handler;
}
