Exemple 9.22 : Win32RegKey.c

#include "Win32RegKey.h"
#include "Win32RegKeyNameEnumeration.h"
#include <string.h>
#include <stdlib.h>
#include <windows.h>

JNIEXPORT jobject JNICALL Java_Win32RegKey_getValue
  (JNIEnv* env, jobject this_obj, jobject name)
{
   const char* cname;
   jstring path;
   const char* cpath;
   HKEY hkey;
   DWORD type;
   DWORD size;
   jclass this_class;
   jfieldID id_root;
   jfieldID id_path;
   HKEY root;
   jobject ret;
   char* cret;

   /* extraire la classe */
   this_class = (*env)->GetObjectClass(env, this_obj);

   /* extraire les ID de champ */
   id_root = (*env)->GetFieldID(env, this_class, "root", "I");
   id_path = (*env)->GetFieldID(env, this_class, "path", 
      "Ljava/lang/String;");

   /* extraire les champs */
   root = (HKEY) (*env)->GetIntField(env, this_obj, id_root);
   path = (jstring)(*env)->GetObjectField(env, this_obj, 
      id_path);
   cpath = (*env)->GetStringUTFChars(env, path, NULL);

   /* ouvrir la clé de base de registres */
   if (RegOpenKeyEx(root, cpath, 0, KEY_READ, &hkey) 
      != ERROR_SUCCESS)
   {
      (*env)->ThrowNew(env,
         (*env)->FindClass(env, "Win32RegKeyException"),
         "Echec de l'ouverture de clé");
      (*env)->ReleaseStringUTFChars(env, path, cpath);
      return NULL;
   }

   (*env)->ReleaseStringUTFChars(env, path, cpath);
   cname = (*env)->GetStringUTFChars(env, name, NULL);

   /* trouver le type et la taille de la valeur */
   if (RegQueryValueEx(hkey, cname, NULL, &type, NULL, &size) != 
      ERROR_SUCCESS)
   {
     (*env)->ThrowNew(env,
         (*env)->FindClass(env, "Win32RegKeyException"),
         "Echec de la recherche de valeur de clé");
      RegCloseKey(hkey);
      (*env)->ReleaseStringUTFChars(env, name, cname);
      return NULL;
   }

   /* allouer la mémoire pour contenir la valeur */
   cret = (char*)malloc(size);

   /* lire la valeur */
   if (RegQueryValueEx(hkey, cname, NULL, &type, cret, &size) != 
      ERROR_SUCCESS)
   {
     (*env)->ThrowNew(env,
         (*env)->FindClass(env, "Win32RegKeyException"),
         "Echec de la recherche de valeur de clé");
      free(cret);
      RegCloseKey(hkey);
      (*env)->ReleaseStringUTFChars(env, name, cname);
      return NULL;
   }

   /* selon le type, stocker la valeur dans 
    une chaîne, un entier ou un tableau d'octets */
   if (type == REG_SZ)
   {
      ret = (*env)->NewStringUTF(env, cret);
   }
   else if (type == REG_DWORD)
   {
     jclass class_Integer = (*env)->FindClass(env,
         "java/lang/Integer");
      /* extraire l'ID de méthode du constructeur */
      jmethodID id_Integer = (*env)->GetMethodID(env, 
         class_Integer, "<init>", "(I)V");
      int value = *(int*)cret;
      /* invoquer le constructeur */
      ret = (*env)->NewObject(env, class_Integer, id_Integer, 
         value);
   }
   else if (type == REG_BINARY)
   {
      ret = (*env)->NewByteArray(env, size);
      (*env)->SetByteArrayRegion(env, (jarray)ret, 0, size, 
         cret);
   }
   else
   {
     (*env)->ThrowNew(env,
         (*env)->FindClass(env, "Win32RegKeyException"),
         "Type de valeur non géré");
      ret = NULL;
   }

   free(cret);
   RegCloseKey(hkey);
   (*env)->ReleaseStringUTFChars(env, name, cname);

   return ret;
}

JNIEXPORT void JNICALL Java_Win32RegKey_setValue
  (JNIEnv* env, jobject this_obj, jstring name, jobject value)
{
   const char* cname;
   jstring path;
   const char* cpath;
   HKEY hkey;
   DWORD type;
   DWORD size;
   jclass this_class;
   jclass class_value;
   jclass class_Integer;
   jfieldID id_root;
   jfieldID id_path;
   HKEY root;
   const char* cvalue;
   int ivalue;

   /* extraire la classe */
   this_class = (*env)->GetObjectClass(env, this_obj);

   /* extraire les ID de champ */
   id_root = (*env)->GetFieldID(env, this_class, "root", "I");
   id_path = (*env)->GetFieldID(env, this_class, "path", 
      "Ljava/lang/String;");

   /* extraire les champs */
   root = (HKEY)(*env)->GetIntField(env, this_obj, id_root);
   path = (jstring)(*env)->GetObjectField(env, this_obj, 
      id_path);
   cpath = (*env)->GetStringUTFChars(env, path, NULL);

   /* ouvrir la clé de base de registres */
   if (RegOpenKeyEx(root, cpath, 0, KEY_WRITE, &hkey) 
      != ERROR_SUCCESS)
   {
     (*env)->ThrowNew(env,
         (*env)->FindClass(env, "Win32RegKeyException"),
         "Echec de l'ouverture de clé");
      (*env)->ReleaseStringUTFChars(env, path, cpath);
      return;
   }

   (*env)->ReleaseStringUTFChars(env, path, cpath);
   cname = (*env)->GetStringUTFChars(env, name, NULL);

   class_value = (*env)->GetObjectClass(env, value);
   class_Integer = (*env)->FindClass(env, "java/lang/Integer");
   /* déterminer le type de l'objet value */
   if ((*env)->IsAssignableFrom(env, class_value, 
      (*env)->FindClass(env, "java/lang/String")))
   {
      /* c'est une chaîne--obtenir un pointeur sur les caractères */
      cvalue = (*env)->GetStringUTFChars(env, (jstring) value, 
         NULL);
      type = REG_SZ;
      size = (*env)->GetStringLength(env, (jstring) value) +  1;
   }
   else if ((*env)->IsAssignableFrom(env, class_value, 
      class_Integer))
   {
      /* c'est un entier--appeler intValue pour obtenir la valeur */
      jmethodID id_intValue = (*env)->GetMethodID(env, 
         class_Integer, "intValue", "()I");
      ivalue = (*env)->CallIntMethod(env, value, id_intValue);
      type = REG_DWORD;
      cvalue = (char*)&ivalue;
      size = 4;
   }
   else if ((*env)->IsAssignableFrom(env, class_value, 
      (*env)->FindClass(env, "[B")))
   {
      /* c'est un tableau d'octets--obtenir un pointeur sur les octets */
      type = REG_BINARY;
      cvalue = (char*)(*env)->GetByteArrayElements(env, 
         (jarray) value, NULL);
      size = (*env)->GetArrayLength(env, (jarray) value);
   }
   else
   {
      /* nous ne savons pas comment gérer ce type */
      (*env)->ThrowNew(env,
         (*env)->FindClass(env, "Win32RegKeyException"),
         "Type de valeur non géré");
      RegCloseKey(hkey);
      (*env)->ReleaseStringUTFChars(env, name, cname);
      return;
   }

   /* définir la valeur */
   if (RegSetValueEx(hkey, cname, 0, type, cvalue, size) 
      != ERROR_SUCCESS)
   {  (*env)->ThrowNew(env,
         (*env)->FindClass(env, "Win32RegKeyException"),
         "Echec de la valeur du jeu");
   }

   RegCloseKey(hkey);
   (*env)->ReleaseStringUTFChars(env, name, cname);

   /* si la valeur était une chaîne ou un tableau d'octets, 
      libérer le pointeur */
   if (type == REG_SZ)
   {
      (*env)->ReleaseStringUTFChars(env, (jstring) value, 
         cvalue);
   }
   else if (type == REG_BINARY)
   {
      (*env)->ReleaseByteArrayElements(env, (jarray) value, 
         (jbyte*) cvalue, 0);
   }
}

/* fonction d'aide au démarrage de l'énumération des noms */
static int startNameEnumeration(JNIEnv* env, jobject this_obj,
   jclass this_class)
{
   jfieldID id_index;
   jfieldID id_count;
   jfieldID id_root;
   jfieldID id_path;
   jfieldID id_hkey;
   jfieldID id_maxsize;

   HKEY root;
   jstring path;
   const char* cpath;
   HKEY hkey;
   DWORD maxsize = 0;
   DWORD count = 0;

   /* extraire les ID de champ */
   id_root = (*env)->GetFieldID(env, this_class, "root", "I");
   id_path = (*env)->GetFieldID(env, this_class, "path",
      "Ljava/lang/String;");
   id_hkey = (*env)->GetFieldID(env, this_class, "hkey", "I");
   id_maxsize = (*env)->GetFieldID(env, this_class, "maxsize",
      "I");
   id_index = (*env)->GetFieldID(env, this_class, "index", 
      "I");
   id_count = (*env)->GetFieldID(env, this_class, "count", 
      "I");

   /* extraire les valeurs de champs */
   root = (HKEY)(*env)->GetIntField(env, this_obj, id_root);
   path = (jstring)(*env)->GetObjectField(env, this_obj, 
      id_path);
   cpath = (*env)->GetStringUTFChars(env, path, NULL);

   /* ouvrir la clé de base de registres */
   if (RegOpenKeyEx(root, cpath, 0, KEY_READ, &hkey) 
      != ERROR_SUCCESS)
   {
      (*env)->ThrowNew(env,
        (*env)->FindClass(env, "Win32RegKeyException"),
         "Echec de l'ouverture de clé");
      (*env)->ReleaseStringUTFChars(env, path, cpath);
      return -1;
   }
   (*env)->ReleaseStringUTFChars(env, path, cpath);

   /* demander compte et longueur max des noms */
   if (RegQueryInfoKey(hkey, NULL, NULL, NULL, NULL, 
      NULL, NULL, &count, &maxsize, NULL, NULL, NULL) 
      != ERROR_SUCCESS) 
   {
      (*env)->ThrowNew(env,
        (*env)->FindClass(env, "Win32RegKeyException"),
         "Echec de la recherche d'infos de clé");
      RegCloseKey(hkey);
      return -1;
   }

   /* définir les valeurs de champ */
   (*env)->SetIntField(env, this_obj, id_hkey, (DWORD)hkey);
   (*env)->SetIntField(env, this_obj, id_maxsize, maxsize + 1);
   (*env)->SetIntField(env, this_obj, id_index, 0);
   (*env)->SetIntField(env, this_obj, id_count, count);
   return count;
}

JNIEXPORT jboolean JNICALL 
Java_Win32RegKeyNameEnumeration_hasMoreElements
   (JNIEnv* env, jobject this_obj)
{  jclass this_class;
   jfieldID id_index;
   jfieldID id_count;
   int index;
   int count;
   /* extraire la classe */
   this_class = (*env)->GetObjectClass(env, this_obj);

   /* extraire les ID de champ */
   id_index = (*env)->GetFieldID(env, this_class, "index", 
      "I");
   id_count = (*env)->GetFieldID(env, this_class, "count", 
      "I");

   index = (*env)->GetIntField(env, this_obj, id_index);
   if (index == -1) /* première fois */
   {
      count = startNameEnumeration(env, this_obj, this_class);
      index = 0;
   }
   else
      count = (*env)->GetIntField(env, this_obj, id_count);
   return index < count;
}

JNIEXPORT jobject JNICALL 
   Java_Win32RegKeyNameEnumeration_nextElement
   (JNIEnv* env, jobject this_obj)
{
   jclass this_class;
   jfieldID id_index;
   jfieldID id_hkey;
   jfieldID id_count;
   jfieldID id_maxsize;

   HKEY hkey;
   int index;
   int count;
   DWORD maxsize;

   char* cret;
   jstring ret;

  /* extraire la classe */
   this_class = (*env)->GetObjectClass(env, this_obj);

   /* extraire les ID de champ */
   id_index = (*env)->GetFieldID(env, this_class, "index", 
      "I");
   id_count = (*env)->GetFieldID(env, this_class, "count", 
      "I");
   id_hkey = (*env)->GetFieldID(env, this_class, "hkey", "I");
   id_maxsize = (*env)->GetFieldID(env, this_class, "maxsize",
      "I");

   index = (*env)->GetIntField(env, this_obj, id_index);
   if (index == -1) /* première fois */
   {
      count = startNameEnumeration(env, this_obj, this_class);
      index = 0;
   }
   else
      count = (*env)->GetIntField(env, this_obj, id_count);

   if (index >= count) /* déjà la fin */
   {
     (*env)->ThrowNew(env,
         (*env)->FindClass(env, 
         "java/util/NoSuchElementException"),
         "Passé la fin de l'énumération");
      return NULL;
   }

   maxsize = (*env)->GetIntField(env, this_obj, id_maxsize);
   hkey = (HKEY)(*env)->GetIntField(env, this_obj, id_hkey);
   cret = (char*)malloc(maxsize);

   /* rechercher le nom suivant */
   if (RegEnumValue(hkey, index, cret, &maxsize, NULL, NULL,
      NULL, NULL) != ERROR_SUCCESS)
   {
     (*env)->ThrowNew(env,
         (*env)->FindClass(env, "Win32RegKeyException"),
         "Echec énum valeur");
      free(cret);
      RegCloseKey(hkey);
      (*env)->SetIntField(env, this_obj, id_index, count);
      return NULL;
   }

   ret = (*env)->NewStringUTF(env, cret);
   free(cret);

   /* incrémenter index */
   index++;
   (*env)->SetIntField(env, this_obj, id_index, index);

   if (index == count) /* à la fin */
   {
     RegCloseKey(hkey);
   }

   return ret;
}

