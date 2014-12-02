Exemple 6.10 : ChartBean2BeanInfo.java

package com.horstmann.corejava;

import java.awt.*;
import java.beans.*;

/**
   L'info du bean pour le Chartbean, spécifiant les éditeurs de 
   propriétés.
*/
public class ChartBeanBeanInfo extends SimpleBeanInfo
{
   public PropertyDescriptor[] getPropertyDescriptors()
   {
      try
      {
         PropertyDescriptor titlePositionDescriptor
            = new PropertyDescriptor("titlePosition", ChartBean.class);
         titlePositionDescriptor.setPropertyEditorClass(
            TitlePositionEditor.class);
         PropertyDescriptor inverseDescriptor
            = new PropertyDescriptor("inverse", ChartBean.class);
         inverseDescriptor.setPropertyEditorClass(InverseEditor.class);
         PropertyDescriptor valuesDescriptor 
            = new PropertyDescriptor("values", ChartBean.class);
         valuesDescriptor.setPropertyEditorClass(
            DoubleArrayEditor.class);

         return new PropertyDescriptor[]
         {  
            new PropertyDescriptor("title", ChartBean.class),
            titlePositionDescriptor, 
            valuesDescriptor,
            new PropertyDescriptor("graphColor", ChartBean.class),
            inverseDescriptor
         };
      }
      catch (IntrospectionException e)
      {  
         e.printStackTrace();
         return null;
      }
   }
}

