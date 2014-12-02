Exemple 7.13 : SimpleLoginModule.java

import java.io.*;
import java.lang.reflect.*;
import java.security.*;
import java.util.*;
import javax.security.auth.*;
import javax.security.auth.login.*;
import javax.security.auth.callback.*;
import javax.security.auth.spi.*;

import javax.swing.*;

/**
   Ce module d'identification authentifie les utilisateurs en lisant
   les noms d'utilisateur, les mots de passe et les rôles
   à partir d'un fichier texte. 
*/
public class SimpleLoginModule implements LoginModule
{
   public void initialize(Subject subject, 
      CallbackHandler callbackHandler, 
      Map<String, ?> sharedState, Map<String, ?> options)
   {
      this.subject = subject;
      this.callbackHandler = callbackHandler;
      this.sharedState = sharedState;
      this.options = options;
   }

   public boolean login() throws LoginException 
   {
      if (callbackHandler == null)
         throw new LoginException("pas de gestionnaire");

      NameCallback nameCall = new NameCallback("nom d'utilisateur : ");
      PasswordCallback passCall = new PasswordCallback(
         "mot de passe : ", false);
      try
      {
         callbackHandler.handle(new Callback[] { nameCall, passCall });         
      }
      catch (UnsupportedCallbackException e)
      {
         LoginException e2 = new LoginException("Rappel non accepté");
         e2.initCause(e);
         throw e2;
      }
      catch (IOException e)
      {
         LoginException e2 = new LoginException("
            Exception d'E/S dans le rappel");
         e2.initCause(e);
         throw e2;
      }

      return checkLogin(nameCall.getName(), passCall.getPassword());
   }

   /**
      Vérifie si les informations d'authentification sont valides.
      Si oui, le subject acquiert les principals pour le
      nom d'utilisateur et le rôle. 
      @param username Le nom d'utilisateur 
      @param password Un tableau de caractères contenant le mot de passe 
      @return true si les informations d'authentification sont valides
   */
   private boolean checkLogin(String username, char[] password) 
      throws LoginException
   {
      try
      {
         Scanner in = new Scanner(new FileReader("" + 
            options.get("pwfile")));
         while (in.hasNextLine())
         {
            String[] inputs = in.nextLine().split("\\|");
            if (inputs[0].equals(username) && 
               Arrays.equals(inputs[1].toCharArray(), password))
            {
               String role = inputs[2];
               Set<Principal> principals = subject.getPrincipals();
               principals.add(new SimplePrincipal("username", username));
               principals.add(new SimplePrincipal("role", role));
               return true;
            }
         }
         in.close();
         return false;
      }
      catch (IOException e)
      {
         LoginException e2 = new LoginException(
            "Impossible d'ouvrir le fichier de mot de passe");
         e2.initCause(e);
         throw e2;
      }
   }

   public boolean logout() { return true; } 
   public boolean abort() { return true; } 
   public boolean commit() { return true; } 

   private Subject subject;
   private CallbackHandler callbackHandler;
   private Map<String, ?> sharedState;
   private Map<String, ?> options;
}
