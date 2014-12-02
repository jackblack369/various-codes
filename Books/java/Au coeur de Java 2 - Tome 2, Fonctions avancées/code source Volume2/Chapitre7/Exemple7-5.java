Exemple 7.5 : WordCheckPermission.java

import java.security.*;
import java.util.*;

/**
   Une permission qui recherche les mots interdits.
*/
public class WordCheckPermission extends Permission
{
   /**
      Construit une permission de vérification des mots.
      @param target une liste de mots séparés par des virgules
      @param anAction "insérer" ou "éviter"
   */
   public WordCheckPermission(String target, String anAction)
   {
      super(target);
      action = anAction;
   }

   public String getActions() { return action; }

   public boolean equals(Object other)
   {
      if (other == null) return false;
      if (!getClass().equals(other.getClass())) return false;
      WordCheckPermission b = (WordCheckPermission) other;
      if (!action.equals(b.action)) return false;
      if (action.equals("insérer"))
         return getName().equals(b.getName());
      else if (action.equals("éviter"))
         return badWordSet().equals(b.badWordSet());
      else return false;
   }

   public int hashCode()
   {
      return getName().hashCode() + action.hashCode();
   }

   public boolean implies(Permission other)
   {
      if (!(other instanceof WordCheckPermission)) return false;
      WordCheckPermission b = (WordCheckPermission) other;
      if (action.equals("insert"))
      {
         return b.action.equals("insérer") &&
            getName().indexOf(b.getName()) >= 0;
      }
      else if (action.equals("éviter"))
      {
         if (b.action.equals("éviter"))
            return b.badWordSet().containsAll(badWordSet());
         else if (b.action.equals("insérer"))
         {
            for (String badWord : badWordSet())
               if (b.getName().indexOf(badWord) >= 0)
                  return false;
            return true;
         }
         else return false;
      }
      else return false;
   }

   /**
      Obtient les mots interdits décrits par cette règle de permission.
      @return un ensemble de mots interdits
   */
   public Set<String> badWordSet()
   {
      Set<String> set = new HashSet<String>();
      set.addAll(Arrays.asList(getName().split(",")));
      return set;
   }

   private String action;
}

