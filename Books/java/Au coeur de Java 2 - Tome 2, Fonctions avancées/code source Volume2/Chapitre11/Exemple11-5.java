Exemple 11.5 : BeanInfoAnnotationFactory.java

import com.sun.mirror.apt.*;
import com.sun.mirror.declaration.*;
import com.sun.mirror.type.*;
import com.sun.mirror.util.*;

import java.beans.*;
import java.io.*;
import java.util.*;

/**
   Cette classe sert à exécuter un outil de traitement des 
   annotations qui crée un fichier BeanInfo.
 */
public class BeanInfoAnnotationFactory implements 
   AnnotationProcessorFactory 
{
   public Collection<String> supportedAnnotationTypes() 
   {
      return Arrays.asList("Property");
   }

   public Collection<String> supportedOptions() 
   {
      return Arrays.asList(new String[0]);
   }

   public AnnotationProcessor getProcessorFor(
      Set<AnnotationTypeDeclaration> atds,
      AnnotationProcessorEnvironment env) 
   {
      return new BeanInfoAnnotationProcessor(env);
   }

   /**
      Cette classe est l'outil de traitement qui analyse
      les annotations @Property.
   */
   private static class BeanInfoAnnotationProcessor 
      implements AnnotationProcessor 
   {
      BeanInfoAnnotationProcessor(
         AnnotationProcessorEnvironment env) 
      {
         this.env = env;
      }

      public void process()
      {
         for (TypeDeclaration t : env.getSpecifiedTypeDeclarations())
         {
            if (t.getModifiers().contains(Modifier.PUBLIC))
            {
               System.out.println(t);
               Map<String, Property> props = 
                  new TreeMap<String, Property>();
               for (MethodDeclaration m : t.getMethods())
               {
                  Property p = m.getAnnotation(Property.class);
                  if (p != null)
                  {
                     String mname = m.getSimpleName();
                     String[] prefixes = { "get", "set", "is" };
                     boolean found = false;
                     for (int i = 0; !found && i < prefixes.length; i++)
                        if (mname.startsWith(prefixes[i]))
                        {
                           found = true;
                           int start = prefixes[i].length();
                           String name = Introspector.decapitalize(
                              mname.substring(start));
                           props.put(name, p);
                        }

                     if (!found)
                        env.getMessager().printError(m.getPosition(), 
                           "@Property doit être appliqué à une méthode 
                           getXxx, setXxx ou isXxx");
                  }
               }

               try
               {
                  if (props.size() > 0)
                     writeBeanInfoFile(t.getQualifiedName(), props);
               }
               catch (IOException e)
               {
                  e.printStackTrace();
               }
            }
         }
      }

      /**
         Ecrit le fichier source de la classe BeanInfo.
         @param beanClassName Le nom de la classe de bean 
         @param props Une carte des noms de propriété
         et leurs annotations
      */
      private void writeBeanInfoFile(
            String beanClassName, Map<String, Property> props) 
         throws IOException
      {
         PrintWriter out = env.getFiler().createSourceFile(
            beanClassName + "BeanInfo");
         int i = beanClassName.lastIndexOf(".");
         if (i > 0)
         {
            out.print("package ");
            out.println(beanClassName.substring(0, i)); 
         }
         out.print("classe publique ");
         out.print(beanClassName.substring(i + 1));
         out.println("BeanInfo extends java.beans.SimpleBeanInfo");
         out.println("{");
         out.println("   public java.beans.PropertyDescriptor[] 
            getPropertyDescriptors()");
         out.println("   {");
         out.println("      try");
         out.println("      {");
         for (Map.Entry<String, Property> e : props.entrySet())
         {
            out.print("         java.beans.PropertyDescriptor ");
            out.print(e.getKey());
            out.println("Descripteur");
            out.print("            = new 
               java.beans.PropertyDescriptor(\"");
            out.print(e.getKey());
            out.print("\", ");
            out.print(beanClassName);
            out.println(".class);");            
            String ed = e.getValue().editor().toString();
            if (!ed.equals(""))
            {
               out.print("         ");
               out.print(e.getKey());
               out.print("Descriptor.setPropertyEditorClass(");
               out.print(ed);
               out.println(".class);");
            }
         }
         out.println("         return new 
            java.beans.PropertyDescriptor[]");
         out.print("         {");
         boolean first = true;
         for (String p : props.keySet())
         {
            if (first) first = false; else out.print(",");
            out.println();
            out.print("            ");
            out.print(p);
            out.print("Descripteur");
         }
         out.println();
         out.println("         };");
         out.println("      }");
         out.println("      catch (
            java.beans.IntrospectionException e)");
         out.println("      {");
         out.println("         e.printStackTrace();");
         out.println("         return null;");
         out.println("      }");
         out.println("   }");
         out.println("}");
         out.close();
      }


      private AnnotationProcessorEnvironment env;
    }
}
