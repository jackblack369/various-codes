import java.rmi.*;
import java.rmi.server.*;
/**
 *
 * @author Christophe
 */
// UnicastRemoteObject the easiest to make remote objects
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {
    public String sayHello(){
        return "server says ,'hey'";
    }
    
    public MyRemoteImpl() throws  RemoteException {
        //super constructor throws a RemoteException
    };
    
    public static void main(String[] args) {
        try {
            // make the remote object then bind it to rmiregistry
            MyRemote service = new MyRemoteImpl();
            Naming.bind("Remote hello", service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
