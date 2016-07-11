package com.example;
import javax.servlet.*;
import com.example.Dog;
/**
 *
 * @author Christophe
 */
public class MyServletContextListener implements ServletContextListener{
    public void contextInitialized (ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        String dogBreed = sc.getInitParameter("breed");
        Dog d = new Dog(dogBreed);
        sc.setAttribute("dog",d);
    }
    
    public void contextDestroyed(ServletContextEvent event){
        // nothing to do here
    }
}
