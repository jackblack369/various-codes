Exemple 9.12 : Employee.java

public class Employee
{
   public Employee(String n, double s)
   {
      name = n;
      salary = s;
   }

   public native void raiseSalary(double byPercent);

   public void print()
   {
      System.out.println(name + " " + salary);
   }

   private String name;
   private double salary;

   static
   {
      System.loadLibrary("Employee");
   }
}
