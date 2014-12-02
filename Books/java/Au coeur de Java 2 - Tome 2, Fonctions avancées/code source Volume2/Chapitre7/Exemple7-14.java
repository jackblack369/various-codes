Exemple 7.14 : SimpleCallbackHandler.java

import javax.security.auth.callback.*;

/**
   Ce gestionnaire de rappel simple présente le nom de l'utilisateur
   et son mot de passe.
*/
public class SimpleCallbackHandler implements CallbackHandler
{
   /**
      Construit le gestionnaire de rappel.
      @param username Le nom de l'utilisateur 
      @param password Un tableau de caractères contenant le mot de passe
   */
   public SimpleCallbackHandler(String username, char[] password)
   {
      this.username = username;
      this.password = password;
   }

   public void handle(Callback[] callbacks)
   {
      for (Callback callback : callbacks)
      {
         if (callback instanceof NameCallback)
         {
            ((NameCallback) callback).setName(username);
         }
         else if (callback instanceof PasswordCallback)
         {
            ((PasswordCallback) callback).setPassword(password);
         }
      }
   }

   private String username;
   private char[] password;
}
