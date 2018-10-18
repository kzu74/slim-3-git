package app.commons.util;

import javax.servlet.http.HttpServletRequest;
import net.slimmer.dao.UserDAO;
import net.slimmer.model.User;

public class MySession {

    public static void setUser(HttpServletRequest req, User user) {
        req.getSession().setAttribute("user", user);
    }
    
    public static User getUser(HttpServletRequest req) {
        
        Object obj = req.getSession().getAttribute("user");
        if(obj != null) {
            
            return (User)obj;
        }
        return null;
    }
    
    public static int getUserId(HttpServletRequest req) {
        
        Object obj = req.getSession().getAttribute("user");
        if(obj != null) {
            
            User u= (User)obj;
            return u.getId();
        }
        return -1;
    }
    
    public static void setIntendedURL(HttpServletRequest req, String url) {
        req.getSession().setAttribute("intendedURL", url);
    }
    
    public static String getIntendedURL(HttpServletRequest req) {
        
        Object obj = req.getSession().getAttribute("intendedURL");
        if(obj != null) {
            
            return (String)obj;
        }
        return null;
    }
    
    
    
    
}
