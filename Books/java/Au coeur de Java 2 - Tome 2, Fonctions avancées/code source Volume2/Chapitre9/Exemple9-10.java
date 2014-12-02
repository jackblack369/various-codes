Exemple 9.10 : Printf2.c

#include "Printf2.h"
#include <string.h>
#include <stdlib.h>
#include <float.h>

/**
 @param formate une chaîne contenant un spécificateur de format printf
 (comme "%8.2f"). Les sous-chaînes "%%" sont sautées.
 @return renvoie un pointeur sur le spécificateur de format (sautant "%")
 ou NULL s'il n'y avait pas un spécificateur unique de format
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
      /* n'est pas un format en virgule flottante */
   return p;
}

JNIEXPORT jstring JNICALL Java_Printf2_sprint
  (JNIEnv* env, jclass cl, jstring format, jdouble x)
{
   const char* cformat;
   char* fmt;
   jstring ret;

   cformat = (*env)->GetStringUTFChars(env, format, NULL);
   fmt = find_format(cformat);
   if (fmt == NULL)
      ret = format;
   else
   {
      char* cret;
      int width = atoi(fmt);
      if (width == 0) width = DBL_DIG + 10;
      cret = (char*)malloc(strlen(cformat) +  width);
      sprintf(cret, cformat, x);
      ret = (*env)->NewStringUTF(env, cret); 
      free(cret);
   }
   (*env)->ReleaseStringUTFChars(env, format, cformat);
   return ret;
}
