package net.slimmer.servlet;

import app.commons.util.MyRequest;
import app.commons.util.MySession;
import app.commons.util.Param;
import app.commons.util.Trace;
import app.commons.util.UserMessage;
import java.sql.Connection;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import net.slimmer.dao.MyDataSource;
import net.slimmer.dao.UserDAO;
import net.slimmer.model.User;

/**
 *
 * @author Kaitsu
 */
public class AppFilter implements Filter {    

    
    Trace t = new Trace(getClass());
    
    public void init(FilterConfig filterConfig) {

    }

    public void destroy() {
        //TODO: clean up connPool here
    }
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {

        Connection conn = null;
        HttpServletRequest req = (HttpServletRequest)request;

        User user = MySession.getUser(req);
        String act = Param.getString(req, "act", "-");

        try {
            conn = MyDataSource.getInstance().getConnection();
            conn.setAutoCommit(false);
            MyRequest.setConnection(req, conn); // to request attribute
            t.out("APPFILTER TRY CONN == " + conn);
            
            if(user != null) { // authentic user
                chain.doFilter(request, response);
                conn.commit();
            }   
            else if(user == null && act.equals("login")) { // user is in login form
                t.out("u == null && agt=login");
                UserDAO uDAO = new UserDAO(conn);
                String email = Param.getString(req, "email");
                String pass = Param.getString(req, "pass");                
                User u = uDAO.login(email, pass);
                UserMessage um = new UserMessage();
  
                if(u != null) { // go to intended url if user had to login between
                    t.out("u == " + u.getName());
                    
                    MySession.setUser(req, u);
                    String intendedURL = MySession.getIntendedURL(req);
                    if(intendedURL != null) { 
                        
                        req.getRequestDispatcher(intendedURL).forward(request, response);
                    }
                    else {
                        req.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                }
                else { // login failed
                    MyRequest.addToMessageList(req, um.getLoginFailed());
                    req.getRequestDispatcher("loginform.jsp").forward(request, response);
                }
            }
            else if(user == null && !act.equals("login")) { // session went old
                String url = MyRequest.getRelativeURL(req);
                if(!act.equals("logout")) {
                    MySession.setIntendedURL(req, url);
                }
                req.getRequestDispatcher("loginform.jsp").forward(request, response);
            }
            else {
                req.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }
        catch (Exception e) {
            try {
                if(conn != null) {
                    conn.rollback();
                }
                t.out("APPFILTER ROLLBACK CONN == " + conn);
                e.printStackTrace();
            } catch(Exception ex) {ex.printStackTrace();}
        }
        finally {
            try {
                if(conn != null) {
                    conn.close();
                }
                t.out("APPFILTER FINALLY AFTER CLOSE CONN == " + conn);
            } catch(Exception ex) {ex.printStackTrace();}
        }
    }    
}