/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.slimmer.servlet;

import app.commons.util.MySession;
import app.commons.util.Param;
import app.commons.util.Trace;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.slimmer.dao.UserDAO;
import net.slimmer.model.User;

public class LoginServlet extends BaseServlet {

    UserDAO userDAO = null;
    Trace t = new Trace();
    
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        userDAO = new UserDAO(getConnection(req));
        
        String act = Param.getString(req, "act", "-");
        
        if(act.equals("loginform")) {
            forward(req, res, "loginform.jsp");
        }
        else if(act.equals("login")) {
            login(req, res);
        }
        else if(act.equals("register")) {
            
        }
        else if(act.equals("logout")) {
            logout(req, res);
        }
        else {
            
        }
        
        //String firstName = Param.getString(req, "firstName", "Vieras");
        String email = Param.getString(req, "email", "-");
        String password = Param.getString(req, "password", "-");
        int weight = Param.getInt(req, "weight", -1);
        // jäin tähän 1.1.2014 klo 18:42
        //Captcha c = req.getSession().getAttribute(email)
    
    }
    
    public void login(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        
        String email = Param.getString(req, "email");
        String pass = Param.getString(req, "pass");
        
        t.out("email == " + email);
        t.out("pass == " + pass);
        
        User user = userDAO.login(email, pass);
        
        t.out("User == " + user);
        
        if(user != null) {
            MySession.setUser(req, user);
            //user.setValidSession(true);
            forward(req, res, "index.jsp");
        }
        else {
            addToMessageList(req, "- Tarkista tunnukset.");
            forward(req, res, "loginform.jsp");
        }
    }
    
    public void logout(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
            MySession.setUser(req, null);
            t.out("laitettu user nulliksi.....................................");
            req.getSession().invalidate(); // ei unbindaa useria
            t.out("req.getSes.inv().................................................");
            forward(req, res, "loginform.jsp");
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
}
