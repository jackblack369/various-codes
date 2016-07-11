package com.headfirstjava;
//rmiregistry lookup
import java.rmi.*;
/**
 *
 * @author Christophe
 */
public class MyRemoteClient {
    public static void main(String[] args) {
        new MyRemoteClient().go();
    }
    
    public void go() {
        try {
            Remote service = (Remote) Naming.lookup("rmi://127.0.0.1/Remote hello");
            String s = service.sayHello();
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
