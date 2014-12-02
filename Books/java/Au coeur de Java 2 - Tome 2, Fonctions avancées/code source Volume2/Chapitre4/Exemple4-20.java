Exemple 4.20 : SysPropServer.java

import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;

class SysPropImpl extends _SysProPOA
{
   public String getProperty(String key)
   {
       return System.getProperty(key);
   }
}

public class SysPropServer
{
   public static void main(String args[])
   {
      try
      {
         System.out.println(
            "Création et initialisation de l'ORB...");

         ORB orb = ORB.init(args, null);

         System.out.println(
            "Enregistrement de l'implémentation du serveur avec l'ORB...");

         POA rootpoa
            = (POA) orb.resolve_initial_references("RootPOA");
         rootpoa.the_POAManager().activate();

         SysPropImpl impl = new SysPropImpl();
         org.omg.CORBA.Object ref
            = rootpoa.servant_to_string(ref));
         System.out.println(orb.object_to_string(ref));

         org.omg.CORBA.Object namingContextObj =
             orb.resolve_initial_references("NameService");
         NamingContext namingContext
            = NamingContextHelper.narrow(namingContextObj);

         NameComponent[] path =
            {
                new NameComponent("SysProp", "Object")
            };

         System.out.println(
            "Liaison de l'implémentation du serveur au service de définition de noms...");

         namingContext.rebind(path, impl);

         System.out.println(
            "Attente des invocations des clients...");
         orb.run();

      }
      catch (Exception e)
      {
          e.printStackTrace(System.out);
      }
   }
}

