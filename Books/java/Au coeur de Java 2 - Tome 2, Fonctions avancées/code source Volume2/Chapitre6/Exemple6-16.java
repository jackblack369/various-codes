Exemple 6.16 : DamageReport.java

import java.awt.*;
import java.awt.geom.*;
import java.beans.*;
import java.util.*;
 
/**
   Cette classe décrit un rapport de dommages à un véhicule qui sera 
   sauvegardé et chargé avec un mécanisme de persistance à long terme.
*/
public class DamageReport
{
   public enum CarType { SEDAN, WAGON, SUV }
 
   // Cette propriété est sauvegardée automatiquement
   public void setRentalRecord(String newValue)
   {
      rentalRecord = newValue;
   }
 
   public String getRentalRecord()
   {
      return rentalRecord;
   }
 
   // Cette propriété est sauvegardée automatiquement
   public void setCarType(CarType newValue)
   {
      carType = newValue;
   }
 
   public CarType getCarType()
   {
      return carType;
   }
 
   // Cette propriété est définie comme transitoire
   public void setRemoveMode(boolean newValue)
   {
      removeMode = newValue;
   }
 
   public boolean getRemoveMode()
   {
      return removeMode;
   }
 
   public void click(Point2D p)
   {
      if (removeMode) 
      {
         for (Point2D center : points)
         {
            Ellipse2D circle = new Ellipse2D.Double(
               center.getX() - MARK_SIZE, center.getY() - MARK_SIZE, 
               2 * MARK_SIZE, 2 * MARK_SIZE);
            if (circle.contains(p))
            {
               points.remove(center);
               return;
            }
         }
      }
      else points.add(p);
   }
 
   public void drawDamage(Graphics2D g2)
   {
      g2.setPaint(Color.RED);
      for (Point2D center : points)
      {
         Ellipse2D circle = new Ellipse2D.Double(
            center.getX() - MARK_SIZE, center.getY() - MARK_SIZE, 
            2 * MARK_SIZE, 2 * MARK_SIZE);
         g2.draw(circle);
      }
   }   
 
   public void configureEncoder(XMLEncoder encoder)
   {
      // Cette étape est nécessaire pour sauvegarder les objets 
      // Point2D.Double
      encoder.setPersistenceDelegate(
         Point2D.Double.class,
         new DefaultPersistenceDelegate(new String[]{ "x", "y" }) );
 
      // Cette étape est nécessaire pour sauvegarder le type énuméré 
      // CarType
      encoder.setPersistenceDelegate(CarType.class, new EnumDelegate());
 
      // Cette étape est nécessaire car la liste des points sous forme de 
      // tableau n'est pas (et ne doit pas être) exposée 
      // comme une propriété
      encoder.setPersistenceDelegate(
         DamageReport.class, new
            DefaultPersistenceDelegate()
            {
               protected void initialize(Class type, 
                     Object oldInstance, Object newInstance, 
                  Encoder out) 
               {
                  super.initialize(type, oldInstance, newInstance, out);
                  DamageReport r = (DamageReport) oldInstance;
 
                  for (Point2D p : r. points)
                     out.writeStatement(
                        new Statement(oldInstance,"click", 
                        new Object[]{ p }) );
               }
            });      
 
   }
 
   // Cette étape est nécessaire pour rendre transitoire la propriété 
   // removeMode
   static 
   {
      try 
      {
         BeanInfo info = Introspector.getBeanInfo(DamageReport.class);         
         for (PropertyDescriptor desc : info.getPropertyDescriptors()) 
            if (desc.getName().equals("removeMode"))
               desc.setValue("transient", Boolean.TRUE);
      } 
      catch (IntrospectionException e) 
      { 
         e.printStackTrace(); 
      }
   }
 
   private String rentalRecord;
   private CarType carType;
   private boolean removeMode;
   private ArrayList<Point2D> points = new ArrayList<Point2D>();
 
   private static final int MARK_SIZE = 5;
}
