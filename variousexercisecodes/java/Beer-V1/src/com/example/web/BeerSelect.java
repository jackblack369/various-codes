package com.example.web;
import com.example.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Christophe
 */
public class BeerSelect extends HttpServlet {
    public void doPost(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws IOException, ServletException {
        
        String c = request.getParameter("color");
        BeerExpert be = new BeerExpert();
        List<String> result = be.getBrands(c);
        
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        out.print("Beer Selection Advice<br>");
        
//        Iterator<String> it = result.iterator();
//        
//        while (it.hasNext()) {
//            out.print("<br>try: " + it.next());
//        }
        request.setAttribute("styles", result);
        RequestDispatcher view = request.getRequestDispatcher("result.jsp");
        view.forward(request, response);
    }
}
