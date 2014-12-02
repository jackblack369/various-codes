Exemple 3.2 : InputTest.java

import java.util.*;

public class InputTest
{  
   public static void main(String[] args)
   {  
      Scanner in = new Scanner(System.in);

      // récupérer la première entrée
      System.out.print("What is your name? ");
      String name = in.nextLine();
 
       // récupérer la seconde entrée 
      System.out.print("How old are you? ");
      int age = in nextInt();
 
      // afficher la sortie à la console
      System.out.println("Hello, " + name +
         ". Next year, you'll be " +  (age + 1));
   }
}
