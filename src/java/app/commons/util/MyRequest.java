package app.commons.util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.slimmer.model.DiningStats;
import net.slimmer.model.Dining;
import net.slimmer.model.Exercise;
import net.slimmer.model.ExerciseStats;
import net.slimmer.model.Food;
import net.slimmer.model.Serving;
import net.slimmer.model.Sport;
import net.slimmer.servlet.BaseServlet;

public class MyRequest {

    static Trace t = new Trace(MyRequest.class);
    
    public static String getAct(HttpServletRequest req) {
        
        Object obj = req.getParameter("act");
        if(obj != null) {
            return (String)obj;
        }
        return "";
    }
    
    public static void setConnection(HttpServletRequest req, Connection conn) {
        req.setAttribute(BaseServlet.JDBC_CONNECTION, conn);
    }
    
    public static Connection getConnection(HttpServletRequest req) {
        Connection conn = null;
        Object o = req.getAttribute(BaseServlet.JDBC_CONNECTION);
        if(o != null) {
            conn = (Connection)o;
        }
        return conn;
                
    }
    
    public static List<String> getMessageList(HttpServletRequest req) {
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
    
    public static void addToMessageList(HttpServletRequest req, String message) {
        getMessageList(req).add(message);
    }

    public static String getString(HttpServletRequest req, String param) {
        
        t.out("param ===== " + param);
        
        Object obj = req.getParameter(param);
        
        if(obj != null) {
            String s = (String)obj;
//            t.out("param getString() == " + param + " ja value == " + s);
            return s;
        }
        else {
//            new Trace().out("parama == nuull");
        }
        return "";
    }
    
    public static void setFood(HttpServletRequest req, Food food) {
        req.setAttribute("food", food);
    }
    public static Food getFood(HttpServletRequest req) {
        
        Object obj = req.getAttribute("food");
        if(obj != null) {
            return (Food)obj;
        }
        return null;
    }

    public static void setExercise(HttpServletRequest req, Exercise ex) {
        req.setAttribute("exercise", ex);
    }
    public static Exercise getExercise(HttpServletRequest req) {
        
        Object obj = req.getAttribute("exercise");
        if(obj != null) {
            return (Exercise)obj;
        }
        return null;
    }

    public static void setDining(HttpServletRequest req, Dining dining) {
        req.setAttribute("dining", dining);
    }
    public static Dining getDining(HttpServletRequest req) {
        
        Object obj = req.getAttribute("dining");
        if(obj != null) {
            return (Dining)obj;
        }
        return null;
    }

    public static void setServing(HttpServletRequest req, Serving serving) {
        req.setAttribute("serving", serving);
    }
    public static Serving getServing(HttpServletRequest req) {
        
        Object obj = req.getAttribute("serving");
        if(obj != null) {
            return (Serving)obj;
        }
        return null;
    }
    
    
    public static void setFoodList(HttpServletRequest req, List<Food> list) {
        req.setAttribute("foodlist", list);
    }
    public static List<Food> getFoodList(HttpServletRequest req) {
        
        Object obj = req.getAttribute("foodlist");
        if(obj != null) {
            return (List<Food>)obj;
        }
        return null;
    }
    
    public static void setAllFoodsList(HttpServletRequest req, List<Food> list) {
        req.setAttribute("allfoodslist", list);
    }
    public static List<Food> getAllFoodsList(HttpServletRequest req) {
        
        Object obj = req.getAttribute("allfoodslist");
        if(obj != null) {
            return (List<Food>)obj;
        }
        return null;
    }

    public static void setDiningList(HttpServletRequest req, List<Dining> list) {
        req.setAttribute("dininglist", list);
    }
    public static List<Dining> getDiningList(HttpServletRequest req) {
        
        Object obj = req.getAttribute("dininglist");
        if(obj != null) {
            return (List<Dining>)obj;
        }
        return null;
    }
    public static void setExerciseList(HttpServletRequest req, List<Exercise> list) {
        req.setAttribute("exerciselist", list);
    }
    public static List<Exercise> getExerciseList(HttpServletRequest req) {
        
        Object obj = req.getAttribute("exerciselist");
        if(obj != null) {
            return (List<Exercise>)obj;
        }
        return null;
    }

    public static void setSportList(HttpServletRequest req, List<Sport> list) {
        req.setAttribute("sportlist", list);
    }
    public static List<Sport> getSportList(HttpServletRequest req) {
        
        Object obj = req.getAttribute("sportlist");
        if(obj != null) {
            return (List<Sport>)obj;
        }
        return null;
    }
    
    public static void setServingList(HttpServletRequest req, List<Serving> list) {
        req.setAttribute("servinglist", list);
    }
    public static List<Serving> getServingList(HttpServletRequest req) {
        
        Object obj = req.getAttribute("servinglist");
        if(obj != null) {
            return (List<Serving>)obj;
        }
        return null;
    }

    public static void setDayString(HttpServletRequest req, String day) {
        req.setAttribute("day", day);
    }
    public static String getDayString(HttpServletRequest req) {
        
        Object obj = req.getAttribute("day");
        if(obj != null) {
            return (String)obj;
        }
        return null;
    }
    
    public static void setPrefixDayString(HttpServletRequest req, String pday) {
        req.setAttribute("prefixday", pday);
    }
    public static String getPrefixDayString(HttpServletRequest req) {
        
        Object obj = req.getAttribute("prefixday");
        if(obj != null) {
            return (String)obj;
        }
        return null;
    }
    
    public static void setPrevDayString(HttpServletRequest req, String day) {
        req.setAttribute("prevday", day);
    }
    public static String getPrevDayString(HttpServletRequest req) {
        
        Object obj = req.getAttribute("prevday");
        if(obj != null) {
            return (String)obj;
        }
        return null;
    }
    
    public static void setNextDayString(HttpServletRequest req, String day) {
        req.setAttribute("nextday", day);
    }
    public static String getNextDayString(HttpServletRequest req) {
        
        Object obj = req.getAttribute("nextday");
        if(obj != null) {
            return (String)obj;
        }
        return null;
    }
    
    public static void setDiningStatsList(HttpServletRequest req, List<DiningStats> dsList) {
        req.setAttribute("diningstatslist", dsList);
    }
    public static List<DiningStats> getDiningStatsList(HttpServletRequest req) {
        
        Object obj = req.getAttribute("diningstatslist");
        if(obj != null) {
            return (List<DiningStats>)obj;
        }
        return null;
    }
    
    public static void setDiningStatsMap(HttpServletRequest req, Map map) {
        req.setAttribute("diningStatsMap", map);
    }
    public static Map getDiningStatsMap(HttpServletRequest req) {
        
        Object obj = req.getAttribute("diningStatsMap");
        if(obj != null) {
            return (Map<Integer, List<DiningStats>>)obj;
        }
        return null;
    }
    
    public static void setExerciseStatsMap(HttpServletRequest req, Map map) {
        req.setAttribute("exerciseStatsMap", map);
    }
    public static Map getExerciseStatsMap(HttpServletRequest req) {
        
        Object obj = req.getAttribute("exerciseStatsMap");
        if(obj != null) {
            return (Map<Integer, List<ExerciseStats>>)obj;
        }
        return null;
    }
    
    public static void setExerciseMap(HttpServletRequest req, Map map) {
        req.setAttribute("exerciseMap", map);
    }
    public static Map getExerciseMap(HttpServletRequest req) {
        
        Object obj = req.getAttribute("exerciseMap");
        if(obj != null) {
            return (Map<Integer, List<Exercise>>)obj;
        }
        return null;
    }
    
    public static void setCurrentFoodIndex(HttpServletRequest req, int i) {
        req.setAttribute("currentFoodIndex", new Integer(i));
    }
    public static Integer getCurrentFoodIndex(HttpServletRequest req) {
        
        Object obj = req.getAttribute("currentFoodIndex");
        if(obj != null) {
            return new Integer(0);
        }
        return (Integer)obj;
    }
    
    public static String getRelativeURL(HttpServletRequest req) {
        String reqURI = req.getRequestURI();
        String queryString = req.getQueryString();
        int i = reqURI.lastIndexOf("/") + 1;
        String relativeURL = reqURI.substring(i) + "?" + queryString;
        return relativeURL;
    }
    
    
    
    
    
}
