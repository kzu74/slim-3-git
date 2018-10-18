/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.slimmer.dao;

import app.commons.util.Converter;
import app.commons.util.MyCalendar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.slimmer.model.DiningStats;
import net.slimmer.model.Food;

/**
 *
 * @author Kaitsu
 */
public class DiningStatsDAO extends DAO {
    
    public DiningStatsDAO(Connection c) {
        super(c);
    }
    
    
    public List<DiningStats> listDayStats() throws Exception {
        
        List<DiningStats> theList = new ArrayList<DiningStats>();
        
        String sql = "SELECT SUM(energy) as energy, DATE_FORMAT(diningTime, '%d.%m.%Y') as date, " +
                        "DATE(diningTime) as mysqldate, COUNT(*) as meals FROM dining GROUP BY DATE(diningTime)";
        
        String sql2 = "";
        
        ResultSet rset = null;
        ResultSet rset2 = null;
        PreparedStatement stmt = null;
        
        try { 

            stmt = conn.prepareStatement(sql);
                        
            rset = stmt.executeQuery();
            
            while(rset.next()) { 
            
                DiningStats ds = new DiningStats();

                ds.setDate(rset.getString("date"));
                String mysqlDate = rset.getString("mysqlDate");
                ds.setEnergy(rset.getInt("energy"));
                ds.setDiningsCount(rset.getInt("meals"));
                ds.setCalendarDate(Converter.toCalendar(ds.getDate()));
                
                sql2 = "SELECT SUM( dining_food.foodGrams ) AS vegeGrams " + // day's vegetables
                        "FROM dining, dining_food, food " +
                        "WHERE food.type = 'vegetable' " +
                        "AND DATE(diningTime) = '" + mysqlDate + "' " +
                        "AND dining.id = dining_food.diningId " +
                        "AND food.id = dining_food.foodId " +
                        "GROUP BY DATE(dining.diningTime)";
                
                stmt = conn.prepareStatement(sql2);
                
                rset2 = stmt.executeQuery();
                
                if(rset2.next()) {
                    //int g = Integer.parseInt(rset2.getString("vegeGrams"));
                    ds.setVegetableGrams(rset2.getInt("vegeGrams"));
                }
                        
                
                theList.add(ds);
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
        
        
        return theList;
        
        
    }
    
    
    
    /**
     * Get day's vegetable grams, day == date.
     */
    public int getVegetableGrams(Calendar date)  {
        
        int grams = 0;
        String mysqlDate = Converter.toMySQLDateString(date);
        if(mysqlDate == null) {
            mysqlDate = Converter.toMySQLDateString(MyCalendar.getInstance());
        }
        
        ResultSet rset = null;
        PreparedStatement stmt = null;
        String sql = "SELECT SUM( dining_food.foodGrams ) AS vegeGrams " + // day's vegetables
                "FROM dining, dining_food, food " +
                "WHERE food.type = 'vegetable' " +
                "AND DATE(diningTime) = '" + mysqlDate + "' " +
                "AND dining.id = dining_food.diningId " +
                "AND food.id = dining_food.foodId " +
                "GROUP BY DAY(dining.diningTime)";        
        
        try { 
            stmt = conn.prepareStatement(sql);
                        
            rset = stmt.executeQuery();
            
            if(rset.next()) { 
                grams = rset.getInt(1);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }
        return grams;
        
        
    }
    
    
    /**
     * Returns a map containing daily dining stats groupped by week number (map's key).
     */
    public Map<Integer, List<DiningStats>> listWeeklyDayStatsOrig() throws Exception {
        
        Map<Integer, List<DiningStats>> weeklyMap = new LinkedHashMap<Integer, List<DiningStats>>(); // <week of year, list dining stats>
        
        List<DiningStats> theList = new ArrayList<DiningStats>();
        
        String sql = "SELECT SUM(energy) as energy, DATE_FORMAT(diningTime, '%d.%m.%Y') as date, " +
                        "DATE(diningTime) as mysqldate, COUNT(*) as meals FROM dining GROUP BY DATE(diningTime)";
        
        String sql2 = "";
        
        ResultSet rset = null;
        ResultSet rset2 = null;
        PreparedStatement stmt = null;
        
        try { 

            stmt = conn.prepareStatement(sql);
                        
            rset = stmt.executeQuery();
            
            //int index = 0;
            
            while(rset.next()) { 
            
                DiningStats ds = new DiningStats();

                ds.setDate(rset.getString("date"));
                String mysqlDate = rset.getString("mysqlDate");
                ds.setEnergy(rset.getInt("energy"));
                ds.setDiningsCount(rset.getInt("meals"));
                ds.setCalendarDate(Converter.toCalendar(ds.getDate()));
                
                sql2 = "SELECT SUM( dining_food.foodGrams ) AS vegeGrams " + // day's vegetables
                        "FROM dining, dining_food, food " +
                        "WHERE food.type = 'vegetable' " +
                        "AND DATE(diningTime) = '" + mysqlDate + "' " +
                        "AND dining.id = dining_food.diningId " +
                        "AND food.id = dining_food.foodId " +
                        "GROUP BY DAY(dining.diningTime)";
                
                stmt = conn.prepareStatement(sql2);
                
                rset2 = stmt.executeQuery();
                
                if(rset2.next()) {
                    //int g = Integer.parseInt(rset2.getString("vegeGrams"));
                    ds.setVegetableGrams(rset2.getInt("vegeGrams"));
                }
                        
                
                theList.add(ds);
                

                int weekdayNumber = MyCalendar.getWeekDayNumber(ds.getCalendarDate());
                int weekOfYear = ds.getCalendarDate().get(Calendar.WEEK_OF_YEAR);
                
                
                if(theList.size() > 1) { // more than one in the list
                    //t.out("----------------- tultiin index > 0......................");
                    DiningStats prevDs = theList.get(theList.size() - 2);
                    int prevWeekOfYear = prevDs.getCalendarDate().get(Calendar.WEEK_OF_YEAR);
                    
                    if(prevWeekOfYear != weekOfYear) {
                        t.out("----------------- tultiin prev != current......................");
                        theList.remove(theList.size() - 1);
                        List<DiningStats> weeklyList = new ArrayList<DiningStats>(theList);
                        weeklyMap.put(prevWeekOfYear, weeklyList);
                        theList.clear();
                        theList.add(ds);
                        
                    }
                }
                if((weekdayNumber + 7) % 7 == 0) { // case sunday
                    
                    //t.out(sql);
                    
                    List<DiningStats> weeklyList = new ArrayList<DiningStats>(theList);
                    weeklyMap.put(weekOfYear, weeklyList);
                    theList.clear();
                }
                else if(rset.isLast()) {
                    List<DiningStats> weeklyList = new ArrayList<DiningStats>(theList);
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
            if (rset2 != null) try { rset2.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }
        
        
        return weeklyMap;
        
        
    }
    
    public Map<String, List<DiningStats>> listWeeklyDayStats() throws Exception {
        
        Map<String, List<DiningStats>> weeklyMap = new LinkedHashMap<String, List<DiningStats>>(); // <week of year, list dining stats>
        
        List<DiningStats> theList = new ArrayList<DiningStats>();
        
        String sql = "SELECT SUM(energy) as energy, DATE_FORMAT(diningTime, '%d.%m.%Y') as date, " +
                     "DATE(diningTime) as mysqldate, COUNT(*) as meals FROM dining GROUP BY DATE(diningTime) " + 
                     "ORDER BY diningTime ASC";
                
        ResultSet rset = null;
        ResultSet rset2 = null;
        PreparedStatement stmt = null;
        
        try { 

            stmt = conn.prepareStatement(sql);
                        
            rset = stmt.executeQuery();
            
            while(rset.next()) { 
            
                DiningStats ds = new DiningStats();

                ds.setDate(rset.getString("date"));
                String mysqlDate = rset.getString("mysqlDate");
                ds.setEnergy(rset.getInt("energy"));
                ds.setDiningsCount(rset.getInt("meals"));
                ds.setCalendarDate(Converter.toCalendar(ds.getDate()));
                
                sql = "select food.unitPrice, dining_food.currentUnitPrice, dining_food.foodGrams, dining.id, food.type " +
                      "from dining inner join dining_food ON dining.id = dining_food.diningId " +
                      "inner join food ON dining_food.foodId = food.id " +
                      "where DATE(diningTime) = '" + mysqlDate + "'";
                
                stmt = conn.prepareStatement(sql);
                
                rset2 = stmt.executeQuery();
                
                int index = 0;
                float dayPrice = 0.0f;
                int vegeGrams = 0;
                
                while(rset2.next()) {
                    
                    float unitPrice = rset2.getFloat("unitPrice");
                    float cUnitPrice = rset2.getFloat("currentUnitPrice");
                    int foodGrams = rset2.getInt("foodGrams");
                    String foodType = rset2.getString("type");
                    
                    index++;
                    
                    if(cUnitPrice != -1) { // not original price
                        unitPrice = cUnitPrice;
                    }
                    
                    float foodServingPrice = Food.getServingPrice(foodGrams, unitPrice);
                    
                    dayPrice += foodServingPrice;
                              
                    if(foodType != null && foodType.equals("vegetable")) {
                        vegeGrams += foodGrams;
                    }
                    
                }
                
                ds.setDiningsCount(index);
                ds.setPrice(dayPrice);
                ds.setVegetableGrams(vegeGrams);
                
                theList.add(ds);
                
                Calendar dsCalDate = ds.getCalendarDate();
                int weekdayNumber = MyCalendar.getWeekDayNumber(dsCalDate);
                int weekOfYear = dsCalDate.get(Calendar.WEEK_OF_YEAR);
                
                if(theList.size() > 1) { // more than one in the list

                    DiningStats prevDs = theList.get(theList.size() - 2);
                    int prevWeekOfYear = prevDs.getCalendarDate().get(Calendar.WEEK_OF_YEAR);
                    
                    if(prevWeekOfYear != weekOfYear) { // week has changed, let's put 'previous' week's data to map
                        theList.remove(theList.size() - 1); // remove following week's day stats
                        List<DiningStats> weeklyList = new ArrayList<DiningStats>(theList);
                        //weeklyMap.put(prevWeekOfYear, weeklyList);
                        String weekString = MyCalendar.getWeekString(prevDs.getCalendarDate());
                        String mapKey = "Viikko " + prevWeekOfYear + " - " + weekString;
                        weeklyMap.put(mapKey, weeklyList);
                        theList.clear();
                        theList.add(ds);
                        
                    }
                }
                if((weekdayNumber + 7) % 7 == 0) { // case sunday
                    
                    
                    List<DiningStats> weeklyList = new ArrayList<DiningStats>(theList);
                    DiningStats dsFirst = weeklyList.get(0);
                    String weekString = MyCalendar.getWeekString(dsFirst.getCalendarDate());
                    String mapKey = "Viikko " + weekOfYear + " - " + weekString;
                    
                    weeklyMap.put(mapKey, weeklyList);
                    
                    theList.clear();
                }
                else if(rset.isLast()) {
                    List<DiningStats> weeklyList = new ArrayList<DiningStats>(theList);
                    
                    DiningStats dsFirst = weeklyList.get(0);
                    String weekString = MyCalendar.getWeekString(dsFirst.getCalendarDate());
                    String mapKey = "Viikko " + weekOfYear + " - " + weekString;
                    
                    weeklyMap.put(mapKey, weeklyList);
                    theList.clear(); // is this useful / needed?
                    
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
        
        
        return weeklyMap;
        
        
    }
    
    /**
     * Returns a map containing daily dining stats groupped by week number (map's key).
     */
    public Map<Integer, List<DiningStats>> listWeeklyDayStatsOld2() throws Exception {
        
        Map<Integer, List<DiningStats>> weeklyMap = new LinkedHashMap<Integer, List<DiningStats>>(); // <week of year, list dining stats>
        
        List<DiningStats> theList = new ArrayList<DiningStats>();
        
        String sql = "SELECT SUM(energy) as energy, DATE_FORMAT(diningTime, '%d.%m.%Y') as date, " +
                     "DATE(diningTime) as mysqldate, COUNT(*) as meals FROM dining GROUP BY DATE(diningTime) " + 
                     "ORDER BY diningTime ASC";
                
        ResultSet rset = null;
        ResultSet rset2 = null;
        PreparedStatement stmt = null;
        
        try { 

            stmt = conn.prepareStatement(sql);
                        
            rset = stmt.executeQuery();
            
            while(rset.next()) { 
            
                DiningStats ds = new DiningStats();

                ds.setDate(rset.getString("date"));
                String mysqlDate = rset.getString("mysqlDate");
                ds.setEnergy(rset.getInt("energy"));
                ds.setDiningsCount(rset.getInt("meals"));
                ds.setCalendarDate(Converter.toCalendar(ds.getDate()));
                
                sql = "select food.unitPrice, dining_food.currentUnitPrice, dining_food.foodGrams, dining.id, food.type " +
                      "from dining inner join dining_food ON dining.id = dining_food.diningId " +
                      "inner join food ON dining_food.foodId = food.id " +
                      "where DATE(diningTime) = '" + mysqlDate + "'";
                
                stmt = conn.prepareStatement(sql);
                
                rset2 = stmt.executeQuery();
                
                int index = 0;
                float dayPrice = 0.0f;
                int vegeGrams = 0;
                
                while(rset2.next()) {
                    
                    float unitPrice = rset2.getFloat("unitPrice");
                    float cUnitPrice = rset2.getFloat("currentUnitPrice");
                    int foodGrams = rset2.getInt("foodGrams");
                    String foodType = rset2.getString("type");
                    
                    index++;
                    
                    if(cUnitPrice != -1) { // not original price
                        unitPrice = cUnitPrice;
                    }
                    
                    float foodServingPrice = Food.getServingPrice(foodGrams, unitPrice);
                    
                    dayPrice += foodServingPrice;
                              
                    if(foodType != null && foodType.equals("vegetable")) {
                        vegeGrams += foodGrams;
                    }
                    
                }
                
                ds.setDiningsCount(index);
                ds.setPrice(dayPrice);
                ds.setVegetableGrams(vegeGrams);
                
                theList.add(ds);
                
                int weekdayNumber = MyCalendar.getWeekDayNumber(ds.getCalendarDate());
                int weekOfYear = ds.getCalendarDate().get(Calendar.WEEK_OF_YEAR);
                
                
                if(theList.size() > 1) { // more than one in the list
                    //t.out("----------------- tultiin index > 0......................");
                    DiningStats prevDs = theList.get(theList.size() - 2);
                    int prevWeekOfYear = prevDs.getCalendarDate().get(Calendar.WEEK_OF_YEAR);
                    
                    if(prevWeekOfYear != weekOfYear) {
                        t.out("TULTIIN prev-next ja prevWeek == " + prevWeekOfYear + " ja " + weekOfYear);
                        theList.remove(theList.size() - 1);
                        List<DiningStats> weeklyList = new ArrayList<DiningStats>(theList);
                        weeklyMap.put(prevWeekOfYear, weeklyList);
                        theList.clear();
                        theList.add(ds);
                        
                    }
                }
                if((weekdayNumber + 7) % 7 == 0) { // case sunday
                    
                        t.out("TULTIIN SUNDAY == ja viikko " + weekOfYear);
                    
                    List<DiningStats> weeklyList = new ArrayList<DiningStats>(theList);
                    weeklyMap.put(weekOfYear, weeklyList);
                    theList.clear();
                }
                else if(rset.isLast()) {
                    t.out("TULTIIN isLAst() ja viikko == " + weekOfYear);
                    List<DiningStats> weeklyList = new ArrayList<DiningStats>(theList);
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
            if (rset2 != null) try { rset2.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }
        
        
        return weeklyMap;
        
        
    }
    
    
    
    
}
