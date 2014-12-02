Exemple 3.7 : LDAPTest.java

import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.naming.*;
import javax.naming.directory.*;
import javax.swing.*;

/**
   Ce programme présente un accès à une base de données
   hiérarchique via LDAP.
*/
public class LDAPTest
{  
   public static void main(String[] args)
   {  
      JFrame frame = new LDAPFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Le cadre qui contient le panneau de données et 
   les boutons de navigation.
*/
class LDAPFrame extends JFrame
{
   public LDAPFrame()
   {  
      setTitle("LDAPTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      JPanel northPanel = new JPanel();
      northPanel.setLayout(new java.awt.GridLayout(1, 2, 3, 1));
      northPanel.add(new JLabel("uid", SwingConstants.RIGHT));
      uidField = new JTextField();
      northPanel.add(uidField);
      add(northPanel, BorderLayout.NORTH);

      JPanel buttonPanel = new JPanel();
      add(buttonPanel, BorderLayout.SOUTH);

      findButton = new JButton("Rechercher");
      findButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               findEntry();
            }
         });
      buttonPanel.add(findButton);

      saveButton = new JButton("Enregistrer");
      saveButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               saveEntry();
            }
         });
      buttonPanel.add(saveButton);      

      deleteButton = new JButton("Supprimer");
      deleteButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               deleteEntry();
            }
         });
      buttonPanel.add(deleteButton);

      addWindowListener(new
         WindowAdapter()
         {  
            public void windowClosing(WindowEvent event)
            {  
               try
               {                   
                  if (context != null) context.close();
               }
               catch (NamingException e) 
               {
                  e.printStackTrace();
               }
            }
        });
   }

   /**
      Retrouve l'entrée de l'uid dans le champ de texte.
   */
   public void findEntry()
   {
      try
      {                   
         if (scrollPane != null) remove(scrollPane);
         String dn = "uid=" + uidField.getText() + 
                  ",ou=people,dc=mycompany,dc=com";
         if (context == null) context = getContext();         
         attrs = context.getAttributes(dn);
         dataPanel = new DataPanel(attrs);
         scrollPane = new JScrollPane(dataPanel);
         add(scrollPane, BorderLayout.CENTER);
         validate();
         uid = uidField.getText();
      }
      catch (NamingException e) 
      {
         JOptionPane.showMessageDialog(this, e);
      }
      catch (IOException e) 
      {
         JOptionPane.showMessageDialog(this, e);
      }      
   }

   /**
      Enregistre les changements apportés par l'utilisateur.
   */
   public void saveEntry()
   {
      try
      {
         if (dataPanel == null) return;
         if (context == null) context = getContext();
         if (uidField.getText().equals(uid)) // actualiser l'entrée
         {
            String dn = "uid=" + uidField.getText() + 
               ",ou=people,dc=mycompany,dc=com";
            Attributes editedAttrs = dataPanel.getEditedAttributes();
            NamingEnumeration<? extends Attribute> attrEnum = 
               attrs.getAll(); 
            while (attrEnum.hasMore()) 
            {
               Attribute attr = attrEnum.next();
               String id = attr.getID();
               Object value = attr.get();
               Attribute editedAttr = editedAttrs.get(id);
               if (editedAttr != null && 
                     !attr.get().equals(editedAttr.get()))
                  context.modifyAttributes(dn, 
                          DirContext.REPLACE_ATTRIBUTE,
                     new BasicAttributes(id, editedAttr.get()));
            }
         }
         else // créer une nouvelle entrée
         {
            String dn = "uid=" + uidField.getText() + 
                   ",ou=people,dc=mycompany,dc=com";
            attrs = dataPanel.getEditedAttributes();
            Attribute objclass = new BasicAttribute("objectClass");
            objclass.add("uidObject");
            objclass.add("person");
            attrs.put(objclass);
            attrs.put("uid", uidField.getText());
            context.createSubcontext(dn, attrs);
         }

         findEntry();
      }
      catch (NamingException e) 
      {
         JOptionPane.showMessageDialog(LDAPFrame.this, e);
         e.printStackTrace();
      }
      catch (IOException e) 
      {
         JOptionPane.showMessageDialog(LDAPFrame.this, e);
         e.printStackTrace();
      }      
   }

   /**
      Efface l'entrée de l'uid du champ de texte.
   */
   public void deleteEntry()
   {
      try
      {
         String dn = "uid=" + uidField.getText() + 
              ",ou=people,dc=mycompany,dc=com";
         if (context == null) context = getContext();
         context.destroySubcontext(dn);
         uidField.setText("");
         remove(scrollPane);
         scrollPane = null;
         repaint();
      }
      catch (NamingException e) 
      {
         JOptionPane.showMessageDialog(LDAPFrame.this, e);
         e.printStackTrace();
      }
      catch (IOException e) 
      {
         JOptionPane.showMessageDialog(LDAPFrame.this, e);
         e.printStackTrace();
      }      
   }

   /**
      Récupère un contexte des propriétés spécifiées 
      dans le fichier ldapserver.properties
      @return Le contexte de répertoires
   */
   public static DirContext getContext()
      throws NamingException, IOException
   {  
      Properties props = new Properties();
      FileInputStream in = new FileInputStream("ldapserver.properties");
      props.load(in);
      in.close();

      String url = props.getProperty("ldap.url");
      String username = props.getProperty("ldap.username");
      String password = props.getProperty("ldap.password");

      Hashtable<String, String> env = new Hashtable<String, String>();
      env.put(Context.SECURITY_PRINCIPAL, username);
      env.put(Context.SECURITY_CREDENTIALS, password);
      DirContext initial = new InitialDirContext(env);
      DirContext context = (DirContext) initial.lookup(url);

      return context;
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  

   private JButton findButton;
   private JButton saveButton;
   private JButton deleteButton;

   private JTextField uidField;
   private DataPanel dataPanel;
   private Component scrollPane;

   private DirContext context;
   private String uid;
   private Attributes attrs;
}

/**
   Ce panneau affiche le contenu d'un jeu de résultats.
*/
class DataPanel extends JPanel
{
   /**
      Construit le panneau de données.
      @param attributes Les attributs de l'entrée
   */
   public DataPanel(Attributes attrs) throws NamingException
   {
      setLayout(new java.awt.GridLayout(0, 2, 3, 1));

      NamingEnumeration<? extends Attribute> attrEnum = attrs.getAll(); 
      while (attrEnum.hasMore()) 
      {
         Attribute attr = attrEnum.next();
         String id = attr.getID();

         NamingEnumeration<?> valueEnum = attr.getAll(); 
         while (valueEnum.hasMore()) 
         {
            Object value = valueEnum.next();
            if (id.equals("userPassword"))
               value = new String((byte[]) value);            

            JLabel idLabel = new JLabel(id, SwingConstants.RIGHT);
            JTextField valueField = new JTextField("" + value);
            if (id.equals("objectClass"))
               valueField.setEditable(false);
            if (!id.equals("uid"))
            {
               add(idLabel);
               add(valueField);
            }
         }
      }
   }

   public Attributes getEditedAttributes()
   {
      Attributes attrs = new BasicAttributes();
      for (int i = 0; i < getComponentCount(); i += 2)
      {
         JLabel idLabel = (JLabel) getComponent(i);
         JTextField valueField = (JTextField) getComponent(i + 1);
         String id = idLabel.getText();
         String value = valueField.getText();
         if (id.equals("userPassword"))
            attrs.put("userPassword", value.getBytes());            
         else if (!id.equals("") && !id.equals("objectClass"))
            attrs.put(id, value);
      }
      return attrs;
   }
}
