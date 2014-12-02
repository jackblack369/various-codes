Exemple 9.17 : Printf4.c

#include "Printf4.h"
#include <string.h>
#include <stdlib.h>
#include <float.h>

/**
  @param formate une chaîne contenant un spécificateur de format printf
  (comme "%8.2f"). Les sous-chaînes "%%" sont sautées.
  @return renvoie un pointeur sur le spécificateur de format (sautant   
  "%")ou NULL s'il n'y avait pas un spécificateur unique de format
 */
char* find_format(const char format[])
{
   char* p;
   char* q;

   p = strchr(format, '%');
   while (p != NULL && *(p + 1) == '%') /* sauter %% */
      p = strchr(p + 2, '%');
   if (p == NULL) return NULL;
   /* vérifier maintenant que % est unique */
   p++;
   q = strchr(p, '%');
   while (q != NULL && *(q + 1) == '%') /* sauter %% */
      q = strchr(q + 2, '%');
   if (q != NULL) return NULL; /* % non unique */
   q = p + strspn(p, " -0+#"); /* sauter les indicateurs */
   q += strspn(q, "0123456789"); /* sauter la largeur de champ */
   if (*q == '.') { q++; q += strspn(q, "0123456789"); }
      /* sauter la précision */
   if (strchr("eEfFgG", *q) == NULL) return NULL;
      /* n'est pas un format virgule flottante */
   return p;
}

JNIEXPORT void JNICALL Java_Printf4_fprint
   (JNIEnv* env, jclass cl, jobject out, jstring format, 
   jdouble x)
{
   const char* cformat;
   char* fmt;
   jclass class_PrintWriter;
   jmethodID id_print;
   char* cstr;
   int width;
   int i;

   if (format == NULL)
   {
     (*env)->ThrowNew(env,
         (*env)->FindClass(env, 
         "java/lang/NullPointerException"),
         "Printf4.fprint: format null");
      return;
   }

   cformat = (*env)->GetStringUTFChars(env, format, NULL);
   fmt = find_format(cformat);

   if (fmt == NULL)
   {
     (*env)->ThrowNew(env,
         (*env)->FindClass(env, 
         "java/lang/IllegalArgumentException"),
         "Printf4.fprint: format non valide");
      return;
   }

   width = atoi(fmt);
   if (width == 0) width = DBL_DIG + 10;
   cstr = (char*)malloc(strlen(cformat) +  width);

   if (cstr == NULL)
   {
     (*env)->ThrowNew(env,
         (*env)->FindClass(env, "java/lang/OutOfMemoryError"),
         "Printf4.fprint: échec malloc");
      return;
   }

   sprintf(cstr, cformat, x);
   
   (*env)->ReleaseStringUTFChars(env, format, cformat);

   /* appeler maintenant ps.print(str) */

   /* extraire la classe */
   class_PrintWriter = (*env)->GetObjectClass(env, out);

   /* extraire l'ID de méthode */
   id_print = (*env)->GetMethodID(env, class_PrintWriter,
      "print", "(C)V");

   /* appeler la méthode */
   for (i = 0; cstr[i] != 0 && !(*env)->ExceptionOccurred(env);
            i++)
      (*env)->CallVoidMethod(env, out, id_print, cstr[i]);

   free(cstr);
}
