Exemple 7.19 : CertificateSigner.java

import java.io.*;
import java.security.*;
import java.security.cert.*;
import java.util.*;

import sun.security.x509.X509CertInfo;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X500Name;
import sun.security.x509.CertificateIssuerName;

/**
   Ce programme signe un certificat, à l'aide de la clé privée
   d'un autre certificat d'un magasin de clés.
*/
public class CertificateSigner
{
   public static void main(String[] args)
   {
      String ksname = null;  // nom du magasin de clés 
      String alias = null;   // alias de clé privée 
      String inname = null;  // nom du fichier en entrée
      String outname = null; // nom du fichier en sortie
      for (int i = 0; i < args.length; i += 2)
      {
         if (args[i].equals("-keystore"))
            ksname = args[i + 1];
         else if (args[i].equals("-alias"))
            alias = args[i + 1];
         else if (args[i].equals("-infile"))
            inname = args[i + 1];
         else if (args[i].equals("-outfile"))
            outname = args[i + 1];
         else usage();
      }

      if (ksname == null || alias == null ||
         inname == null || outname == null) usage();

      try
      {
        PushbackReader console = new PushbackReader(new
            InputStreamReader(System.in));

         KeyStore store = KeyStore.getInstance("JKS", "SUN");
         InputStream in = new FileInputStream(ksname);
         System.out.print("Mot de passe du magasin de clés : ");
         System.out.flush();
         char[] password = readPassword(console);
         store.load(in, password);
         Arrays.fill(password, ' ');
         in.close();

         System.out.print("Mot de passe de la clé pour " + alias);
         System.out.flush();
         char[] keyPassword = readPassword(console);
         PrivateKey issuerPrivateKey
            = (PrivateKey) store.getKey(alias, keyPassword);
         Arrays.fill(keyPassword, ' ');

         if (issuerPrivateKey == null)
            error("Clé privée introuvable");

         in = new FileInputStream(inname);

         CertificateFactory factory
            = CertificateFactory.getInstance("X.509");

         X509Certificate inCert
            = (X509Certificate) factory.generateCertificate(in);
         in.close();
         byte[] inCertBytes = inCert.getTBSCertificate();

         X509Certificate issuerCert
            = (X509Certificate) store.getCertificate(alias);
         Principal issuer = issuerCert.getSubjectDN();
         String issuerSigAlg = issuerCert.getSigAlgName();

         FileOutputStream out = new FileOutputStream(outname);

         X509CertInfo info = new X509CertInfo(inCertBytes);
         info.set(X509CertInfo.ISSUER,
            new CertificateIssuerName((X500Name) issuer));

         X509CertImpl outCert = new X509CertImpl(info);
         outCert.sign(issuerPrivateKey, issuerSigAlg);
         outCert.derEncode(out);

         out.close();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   /**
      Lit un mot de passe.
      @param in le lecteur à partir duquel lire le mot de passe
      @return un tableau de caractères contenant le mot de passe
   */
   public static char[] readPassword(PushbackReader in)
      throws IOException
   {
      final int MAX_PASSWORD_LENGTH = 100;
      int length = 0;
      char[] buffer = new char[MAX_PASSWORD_LENGTH];

      while (true)
      {
         int ch = in.read();
         if (ch == '\r' || ch == '\n' || ch == -1
            || length == MAX_PASSWORD_LENGTH)
         {
            if (ch == '\r') // fin de ligne DOS "\r\n" 
            {
               ch = in.read();
               if (ch != '\n' && ch != -1) in.unread(ch);
            }
            char[] password = new char[length];
            System.arraycopy(buffer, 0, password, 0, length);
            Arrays.fill(buffer, ' ');
            return password;
         }
         else
         {
            buffer[length] = (char) ch;
            length++;
         }
      }
   }

   /**
      Imprime un message d'erreur et se ferme.
      @param message
   */
   public static void error(String message)
   {
      System.out.println(message);
      System.exit(1);
   }

   /**
      Imprime un message d'utilisation et se ferme.
   */
   public static void usage()
   {
      System.out.println("Usage: java CertificateSigner"
         + " -keystore keyStore -alias issuerKeyAlias"
         + " -infile inputFile -outfile outputFile");
      System.exit(1);
   }
}

