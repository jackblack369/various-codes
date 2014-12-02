Exemple 7.11 : JAASTest.java

import java.awt.*;
import java.awt.event.*;
import javax.security.auth.*;
import javax.security.auth.login.*;
import javax.swing.*;
 
/**
   Ce programme authentifie un utilisateur via un identifiant 
   personnalisé, puis exécute SysPropAction
   avec les droits de l'utilisateur.
*/
public class JAASTest
{
   public static void main(final String[] args)
   {
      System.setSecurityManager(new SecurityManager());
      JFrame frame = new JAASFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);      
   }
}
 
/**
   Ce cadre possède des champs de texte réservés au nom d'utilisateur et 
   au mot de passe ainsi qu'un champ pour le nom de la propriété système 
   demandée, enfin un champ pour montrer la valeur de la propriété.
*/
class JAASFrame extends JFrame
{
   public JAASFrame()
   {
      setTitle("JAASTest");
 
      username = new JTextField(20);
      password = new JPasswordField(20);
      propertyName = new JTextField(20);
      propertyValue = new JTextField(20);
      propertyValue.setEditable(false);
 
      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(0, 2));
      panel.add(new JLabel("nom d'utilisateur :"));
      panel.add(username);
      panel.add(new JLabel("mot de passe :"));
      panel.add(password);
      panel.add(propertyName);
      panel.add(propertyValue);     
      add(panel, BorderLayout.CENTER);
 
      JButton getValueButton = new JButton("Récupérer la valeur");
      getValueButton.addActionListener(new
         ActionListener()
         {  
            public void actionPerformed(ActionEvent event) { 
               getValue(); }
         });
      JPanel buttonPanel = new JPanel();
      buttonPanel.add(getValueButton);
      add(buttonPanel, BorderLayout.SOUTH);
      pack();
   }
 
   public void getValue()
   {
      try 
      {
         LoginContext context = new LoginContext("Login1", 
            new SimpleCallbackHandler(username.getText(), 
            password.getPassword()));
         context.login();
         Subject subject = context.getSubject();
         propertyValue.setText(
            "" + Subject.doAsPrivileged(subject, 
            new SysPropAction(propertyName.getText()), null));
         context.logout();
      } 
      catch (LoginException e ) 
      {
         JOptionPane.showMessageDialog(this, e);
      }      
   }
 
   private JTextField username;
   private JPasswordField password;
   private JTextField propertyName;
   private JTextField propertyValue;
}
