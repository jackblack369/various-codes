import java.rmi.*;
/**
 *
 * @author Christophe
 */
// Must extend java.rmi.Remote
public interface MyRemote extends Remote {
    //all methods must delclare a RemoteException
    public String sayHello() throws RemoteException;
}
