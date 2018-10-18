/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.slimmer.servlet;

import app.commons.util.Trace;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseServlet extends HttpServlet {
    
    public static final String JDBC_CONNECTION = "JDBC_CONNECTION";
    
    Trace t = new Trace();

    public void forward(HttpServletRequest req, HttpServletResponse res, String page) throws ServletException, IOException {
        RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/" + page);
        rd.forward(req, res);
    }
    
    public void redirect(HttpServletRequest req, HttpServletResponse res, String page) throws IOException {
        String ctx = req.getServletContext().getContextPath();
        res.sendRedirect(ctx + "/" + page);
    }
    
    public void addToMessageList(HttpServletRequest req, String message) {
        //addError(message);
        getMessageList(req).add(message);
    }

    public List<String> getMessageList(HttpServletRequest req) {
        List<String> list = null;
        Object o = req.getAttribute("messageList");
        if(o != null) {
            list = (List<String>)o;
        }
        else {
            list = new ArrayList<String>();
            req.setAttribute("messageList", list);
        }
        return list;

    }
    
    public void addToMessageList(HttpSession ses, String message) {
        
        t.out("ollaan addToM.................. msg == " + message);
        getMessageList(ses).add(message);
    }
    
    public List<String> getMessageList(HttpSession ses) {

        List<String> list = null;
        Object o = ses.getAttribute("messageList");
        if(o != null) {
            list = (List<String>)o;
        }
        else {
            list = new ArrayList<String>();
            ses.setAttribute("messageList", list);
        }
        return list;

    }
    
    public void setMessageList(HttpServletRequest req, List<String> list) {
        req.setAttribute("messageList", list);
    }
    public void setMessageList(HttpSession ses, List<String> list) {
        ses.setAttribute("messageList", list);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            // TODO output your page here. You may use following sample code. 
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BaseServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BaseServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    public Connection getConnection(HttpServletRequest req) {
        
        Connection c = null;
        
        Object obj = req.getAttribute(JDBC_CONNECTION);
        
        if(obj != null) {
            c = (Connection)obj;
        }
        
        return c;
        
    }
}
