/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.slimmer.dao;

import app.commons.util.Converter;
import app.commons.util.MyCalendar;
import app.commons.util.Trace;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.slimmer.model.Exercise;
import net.slimmer.model.ExerciseStats;
import net.slimmer.model.Sport;

/**
 *
 * @author Kaitsu
 */
public class ExerciseDAO extends DAO {

    Trace t = new Trace();
    
    public static final String loadSQL = "select * from exercise where id = ?";
    
    private static String insertSQL = 
    "insert into exercise (name, comments, power, startTime, endTime, calories, sportId, userId) values (?,?,?,?,?,?,?,?)";

    private static String updateSQL = 
    "update exercise set name=?, comments=?, power=?, startTime=?, endTime=?, calories=?, sportId=?, userId=? where id = ?";
    
    private static String deleteSQL = "delete from exercise where id = ?";
    
    //private static String listAllSQL = "select * from exercise order by startTime ASC";
    
    
    // TODO: add userId
    private static String listAllWithSportSQL = 
    "select exercise.id, exercise.name as name, comments, power, startTime, endTime, calories, sportId, " +
    "sport.id as sid, sport.name as sname, minCalories, maxCalories, abbreviation, userId from exercise " +
            "INNER JOIN sport ON exercise.sportId = sport.id where userId = ? ORDER BY DATE(startTime) DESC, startTime ASC";
    
    private int userId = 0;

    public ExerciseDAO(Connection c) {
        super(c);
    }
    
    
    /**
     * Binds insertSQL or updateSQL to user data. If users id > 0, binds one
     * more "?" to user.id.
     */
    public static void bind(PreparedStatement stmt, Exercise exercise) throws Exception {

        stmt.setString(1, exercise.getName());
        stmt.setString(2, exercise.getComments());
        stmt.setInt(3, exercise.getPower());
        stmt.setTimestamp(4, exercise.getStartTime());
        stmt.setTimestamp(5, exercise.getEndTime());
        stmt.setInt(6, exercise.getCalories());
        stmt.setInt(7, exercise.getSportId());
        stmt.setInt(8, exercise.getUserId());
        
        if (exercise.getId() > 0) {
            stmt.setInt(9, exercise.getId());
        }

    }
    
    public static void bind(ResultSet rset, Exercise exercise) throws Exception {

        exercise.setId(rset.getInt("id"));
        exercise.setName(rset.getString("name"));
        exercise.setPower(rset.getInt("power"));
        exercise.setComments(rset.getString("comments"));
        exercise.setStartTime(rset.getTimestamp("startTime"));
        exercise.setEndTime(rset.getTimestamp("endTime"));
        exercise.setCalories(rset.getInt("calories"));
        exercise.setSportId(rset.getInt("sportId"));
        exercise.setUserId(rset.getInt("userId"));
        
        
    }
    
    public static void bindSport(ResultSet rset, Sport sport) throws Exception {
        sport.setId(rset.getInt("sid"));
        sport.setName(rset.getString("sname"));
        sport.setMinCalories(rset.getInt("minCalories"));
        sport.setMaxCalories(rset.getInt("maxCalories"));
        sport.setAbbreviation(rset.getString("abbreviation"));
        
    }
    
    
    
    public Exercise load(int id) throws Exception {
        
        Exercise exercise = null;
        ResultSet rset = null;
        ResultSet rset2 = null;
        PreparedStatement stmt = null;
        
        try { 

            stmt = conn.prepareStatement(loadSQL);
            
            stmt.setInt(1, id);
            
            rset = stmt.executeQuery();
            
            if(rset.next()) {
            
                exercise = new Exercise();
                bind(rset, exercise);
                
                String sql = "select * from sport where id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, exercise.getSportId());
                rset2 = stmt.executeQuery();
                if(rset2.next()) {
                    Sport s = new Sport();
                    SportDAO.bind(rset2, s);
                    exercise.setSport(s);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (rset2 != null) try { rset2.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return exercise;
    }

    public Exercise insert(Exercise exercise) throws Exception {

        PreparedStatement stmt = null;
        ResultSet rset = null;

        try {

            stmt = conn.prepareStatement(insertSQL);

            bind(stmt, exercise);

            int rowCount = stmt.executeUpdate();

            if (rowCount == 1) { // success 
                
                String sql = "select MAX(id) from exercise";
                rset = stmt.executeQuery(sql);
                
                if (rset.next()) {
                    
                    int id = rset.getInt(1);
                    exercise.setId(id);
                }
            } 
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return exercise;

    }
    
    public boolean update(Exercise exercise) throws Exception {

        boolean success = true;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        
        
        try { 

            stmt = conn.prepareStatement(updateSQL);

            bind(stmt, exercise);
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount != 1) { // success 
                success = false;
            }
        }
        catch(Exception e) {
            success = false;
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return success;
        
    }
    
    public Exercise delete(int id) throws Exception {

        Exercise exercise = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        
        try { 
            
            exercise = load(id);

            stmt = conn.prepareStatement(deleteSQL);

            stmt.setInt(1, id);
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount != 1) { // success 
                exercise = null;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return exercise;
        
    }
    
    public List<Exercise> listByPartOfName(String part) throws Exception {
        
        List<Exercise> list = new ArrayList<Exercise>();
        ResultSet rset = null;
        PreparedStatement stmt = null;
        String sql = "select * from exercise where name LIKE'%" + part + "%'";
        
        try { 

            stmt = conn.prepareStatement(sql);
                        
            rset = stmt.executeQuery();
            
            while(rset.next()) { // there was reserved time between c1 and c2
            
                Exercise exercise = new Exercise();
                bind(rset, exercise);
                list.add(exercise);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return list;
    }
    
    
    public List<Exercise> listAll() throws Exception {
        
        List<Exercise> list = new ArrayList<Exercise>();
        ResultSet rset = null;
        
        PreparedStatement stmt = null;
        
        try { 

            stmt = conn.prepareStatement(listAllWithSportSQL);
            stmt.setInt(1, userId);
            
            t.out("SQL == " + listAllWithSportSQL);        
            
            rset = stmt.executeQuery();
            
            while(rset.next()) { // there was reserved time between c1 and c2
            
                Exercise exercise = new Exercise();
                Sport sport = new Sport();
                
                bind(rset, exercise);
                bindSport(rset, sport);
                
                exercise.setSport(sport);
                                
                list.add(exercise);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return list;
    }
    
    /**
     * String orderBy field name and ASC / DESC, eg. 'startTime DESC'. 
     
    public List<Exercise> listAll(String orderBy) throws Exception {
        
        List<Exercise> list = new ArrayList<Exercise>();
        ResultSet rset = null;
        String sql = listAllWithSportSQL + " order by " + orderBy;
        PreparedStatement stmt = null;
        
        t.out("SQL == " + sql);
        
        try { 

            stmt = conn.prepareStatement(sql);
                        
            rset = stmt.executeQuery();
            
            while(rset.next()) { // there was reserved time between c1 and c2
            
                Exercise exercise = new Exercise();
                Sport sport = new Sport();
                
                bind(rset, exercise);
                bindSport(rset, sport);
                
                exercise.setSport(sport);
                
                list.add(exercise);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return list;
    }
    
    */
    
    /**
     * First map's Integer = weekNumber, second map's Integer exercise id.
     */
    
    public Map<Integer, List<ExerciseStats>> listWeeklyDayStats() throws Exception {
        
        Map<Integer, List<ExerciseStats>> weeklyMap = new LinkedHashMap<Integer, List<ExerciseStats>>(); // <week of year, list exercise stats>
        
        List<ExerciseStats> theList = new ArrayList<ExerciseStats>();
        
        // ois ehkä helpompi jos tallentais kulutuksen erilliseen kenttään exercise-tauluun...
        
        // jäin tähän 31.12.2013
        // ehkäpä hover boxi ajaxilla, joka sitten näyttää tarkemmat tiedot
        // kyllä säkin osaat vähän piirtää ja ota rohkeasti mallia hienoista sivuista!
        String sql = 
                "SELECT DATE_FORMAT(exercise.startTime, '%d.%m.%Y') as date, " +
                "startTime, " + 
                "COUNT(*) as exCount, " +
                "SUM(MINUTE(TIMEDIFF(exercise.endTime, exercise.startTime ))) AS minutes, " +
                "SUM(calories) as calories " + 
                "FROM exercise ORDER BY DAY(exercise.startTime) DESC, startTime ASC"; 
        
        ResultSet rset = null;
        PreparedStatement stmt = null;
        
        try { 

            stmt = conn.prepareStatement(sql);
                        
            rset = stmt.executeQuery();
                        
            while(rset.next()) { 
            
                ExerciseStats es = new ExerciseStats();

                es.setDate(rset.getString("date"));
                es.setCalendarDate(Converter.toCalendar(es.getDate()));
                es.setExerciseCount(rset.getInt("exCount"));
                es.setMinutes(rset.getInt("minutes"));
                es.setCalories(rset.getInt("calories"));

                theList.add(es);
                
                int weekdayNumber = MyCalendar.getWeekDayNumber(es.getCalendarDate());
                int weekOfYear = es.getCalendarDate().get(Calendar.WEEK_OF_YEAR);
                
                if(theList.size() > 1) { // 2 or more in the list

                    ExerciseStats prevEs = theList.get(theList.size() - 2);
                    int prevWeekOfYear = prevEs.getCalendarDate().get(Calendar.WEEK_OF_YEAR);
                    
                    if(prevWeekOfYear != weekOfYear) { // week is changed
                        theList.remove(theList.size() - 1);
                        List<ExerciseStats> weeklyList = new ArrayList<ExerciseStats>(theList);
                        weeklyMap.put(prevWeekOfYear, weeklyList);
                        
                        theList.clear();
                        theList.add(es);
                        
                    }
                }
                if((weekdayNumber + 7) % 7 == 0) { // case sunday
                    
                    List<ExerciseStats> weeklyList = new ArrayList<ExerciseStats>(theList);
                    weeklyMap.put(weekOfYear, weeklyList);
                    theList.clear();
                }
                else if(rset.isLast()) {
                    List<ExerciseStats> weeklyList = new ArrayList<ExerciseStats>(theList);
                    weeklyMap.put(weekOfYear, weeklyList);
                    theList.clear(); // is this useful / needed?
                    
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }
        
        return weeklyMap;
    }
    
    
    /**
     * Return a map containing key String = ex.getDate() and that day's exercises in the list as value.
     */
    public Map<String, List<Exercise>> listDailyExercises() throws Exception {
        
        Map<String, List<Exercise>> dailyMap = new LinkedHashMap<String, List<Exercise>>();
        
        List<Exercise> exList = listAll();
        
        Exercise prevEx = null;
        Exercise currEx = null;
        List<Exercise> tempExList = new ArrayList<Exercise>();
        
        for(int i=1; i < exList.size(); i++) { // starting from second item
            
            if(exList.size() > 1) { // two or more items in the list
                
                prevEx = exList.get(i - 1);
                currEx = exList.get(i);
                
                tempExList.add(prevEx);
                
                Calendar c1 = Converter.toCalendar(prevEx.getStartTime());
                Calendar c2 = Converter.toCalendar(currEx.getStartTime());

                if((c1.get(Calendar.DAY_OF_YEAR) != c2.get(Calendar.DAY_OF_YEAR)) 
                        || i == exList.size() -1) { // next day or last item in the list

                    List<Exercise> mapList = new ArrayList<Exercise>(tempExList); // copy of tempExList
                    dailyMap.put(prevEx.getDateWeekdayPrefix(), mapList);
                    tempExList.clear();
                }
            }
        }
        
        return dailyMap;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    
    
    
    
}
