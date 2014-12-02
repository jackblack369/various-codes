Exemple 9.13 : Employee.c

#include "Employee.h"

#include <stdio.h>

JNIEXPORT void JNICALL Java_Employee_raiseSalary
  (JNIEnv* env, jobject this_obj, jdouble byPercent)
{
   /* extraire la classe */
   jclass class_Employee = (*env)->GetObjectClass(env, 
      this_obj);

   /* extraire l'ID de champ */
   jfieldID id_salary = (*env)->GetFieldID(env, 
      class_Employee, "salary", "D");

   /* extraire la valeur de champ */
   jdouble salary = (*env)->GetDoubleField(env, this_obj,
      id_salary);

   salary *= 1 + byPercent / 100;

   /* définir la valeur de champ */
   (*env)->SetDoubleField(env, this_obj, id_salary, salary);
}
