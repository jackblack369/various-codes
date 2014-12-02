Exemple 5.5 : ReflectionTest.java

import java.util.*;
import java.lang.reflect.*;

public class ReflectionTest
{  
   public static void main(String[] args)
   {  
      /* lire le nom de classe dans les args de ligne de commande
         ou l'entrée de l'utilisateur */
      String name;
      if (args.length > 0) 
         name = args[0];
      else 
      {
         Scanner in = new Scanner(System.in);
         System.out.println("Enter class Name ("e.g. java.util.Date): ");
         name = in.next();
      }

      try
      {  
         /* afficher le nom de classe et de superclasse 
            (if != Object) */
         Class cl = Class.forName(name);
         Class supercl = cl.getSuperclass();
         System.out.print("class " + name);
         if (supercl != null && supercl != Object.class)
            System.out.print(" extends " + supercl.getName());

         System.out.print("\n{\n");
         printConstructors(cl);
         System.out.println();
         printMethods(cl);
         System.out.println();
         printFields(cl);
         System.out.println("}");
      }
      catch(ClassNotFoundException e) { e.printStackTrace(); }
      System.exit(0);
   }

    /**
      affiche tous les constructeurs d'une classe
      @param cl Une classe
    */
   public static void printConstructors(Class cl)
   {  
      Constructor[] constructors = cl.getDeclaredConstructors();

      for (Constructor c : constructors)
      {  
         String name = c.getName();
         System.out.print("   " + Modifier.toString(c.getModifiers()));
         System.out.print(" " + name + "(");

         // afficher les types des paramètres
         Class[] paramTypes = c.getParameterTypes();
         for (int j = 0; j < paramTypes.length; j++)
         {  
            if (j > 0) System.out.print(", ");
            System.out.print(paramTypes[j].getName());
         }
         System.out.println(");");
      }
   }

    /**
      imprime toutes les méthodes d'une classe
      @param cl Une classe
    */
   public static void printMethods(Class cl)
   {  
      Method[] methods = cl.getDeclaredMethods();

      for (Method m : methods) 
         Class retType = m.getReturnType();
         String name = m.getName();

          /* affiche les modificateurs, le type renvoyé
            et le nom de la méthode */
         System.out.print("    " + Modifier.toString(m.getModifiers()));
         System.out.print("   " + retType.getName() + " " + name
            + "(");

         // imprime les types des paramètres 
         Class[] paramTypes = m.getParameterTypes();
         for (int j = 0; j < paramTypes.length; j++)
         {  
            if (j > 0) System.out.print(", ");
            System.out.print(paramTypes[j].getName());
         }
         System.out.println(");");
      }
   }

    /**
      Affiche tous les champs d'une classe
      @param cl Une classe
    */
   public static void printFields(Class cl)
   {  
      Field[] fields = cl.getDeclaredFields();

      for (Field f : fields)
         Class type = f.getType();
         String name = f.getName();
         System.out.print("    " + Modifier.toString(f.getModifiers()));
         System.out.println("   " + type.getName() + " " + name
            + ";");
      }
   }
}
