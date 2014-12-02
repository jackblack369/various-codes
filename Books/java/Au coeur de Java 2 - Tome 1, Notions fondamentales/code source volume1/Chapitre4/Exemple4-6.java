Exemple 4.6 : PackageTest.java

import com.horstmann.corejava.*;
   // La classe Employee est définie dans ce package

import static java.lang.System.*;

public class PackageTest
{  
   public static void main(String[] args)
   {  
      // du fait de l'instruction import, nous n'utilisons pas
      // com.horstmann.corejava.Employee ici
      Employee harry = new Employee("Harry Hacker", 50000,
         1989, 10, 1);

      // augmenter le salaire de 5%
      harry.raiseSalary(5);

      // afficher les informations concernant harry
      // utiliser java.lang.System.out ici
      out.println("name=" + harry.getName()
         + ",salary=" + harry.getSalary());
   }
}
