package com.example;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.example.Dog;
/**
 *
 * @author Christophe
 */
public class ListenerTest extends HttpServlet{
    
    public  void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("test context set by listener<br>");
        out.println("<br>");
        Dog dog = (Dog) getServletContext().getAttribute("dog");
        out.println("Dog's breed is: " + dog.getBreed());
    }
}
