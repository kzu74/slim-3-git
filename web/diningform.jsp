<%@page import="app.commons.util.Converter"%>
<%@ page import="app.commons.util.MyCalendar"%>
<%@ page import="java.util.List" %>
<%@ page import="app.commons.util.MyRequest"%>
<%@ page import="app.commons.jsp.HelperJSP "%>
<%@ page import="net.slimmer.model.Dining" %>
<%@ page import="net.slimmer.model.Food" %>


<%

    Dining dining = MyRequest.getDining(request);
    String date = "";
    String time = "";
    
    if(dining == null) { // case insert
        dining = new Dining();
        date = MyCalendar.getTodayDate();
        time = MyCalendar.getNowTime();
    }
    else {
        date = Converter.toDateString(dining.getDiningTime());
        time = Converter.toTimeString(dining.getDiningTime());
    }
    
    List<Food> diningFoodList = dining.getFoodList();
    
    List<Food> allFoodsList = MyRequest.getAllFoodsList(request);
    
    System.out.println("ALL FOODS LIST LENGTH == " + allFoodsList.size());
    
    String h1 = "Tallenna ruokailu";
    
    if(dining.getId() > 0) {
        h1 = "Muuta ruokailun tietoja"; // + dining.getName();
    }

    HelperJSP helperJSP = new HelperJSP();
%>

<%@include file="header.jsp" %>

<div class="basic-content">

    <h1><%= h1 %></h1>
    
    <form method="post" action="dining.jtml" >
        
        <input type="hidden" name="id" value="<%= dining.getId() %>">
        <input type="hidden" name="act" value="save">
        
        <%-- jäin tähän --%>
        <table>
            <tr>
                <td class="td-120px">*Nimi:</td> 
                <td><input type="text" name="name" class="input-text-300px" value="<%= dining.getName() %>" id="fokus"></td>
            </tr>
            
<%
            String foodName = "";
            
            for(int i = 0; i < 10; i++) {
                
                
                Food food = dining.getFood(i);
                
                if(food != null) {
                    foodName = food.getName();
                }
                else {
                    foodName = "";
                }
%>            
            <tr>
                <td>Ruoka <%= i + 1 %>:</td> 
                <td>
                    <select name="food<%= i+1 %>">
                        
                        <option name="choose" value="none">Valitse ruoka...</option>
<%
                    String selected = "";
                    int foodGrams = 0;
                    
                    for(Food allFood : allFoodsList) {
                        
                        for(Food diningFood : diningFoodList) {
                        
                            if(diningFood.getId() == allFood.getId() 
                                    && !helperJSP.htmlSelectAlreadySet(i, diningFood.getId())) {
                                selected = "selected";
                                helperJSP.flagHtmlSelect(i, diningFood.getId());
                                foodGrams = diningFood.getGrams();
                                break;
                            }
                            else {
                                selected = "";
                            }
                        }
%>                        
                        <option name="" value="<%= allFood.getId() %>" <%= selected %>><%= allFood.getName() %></option> 
<%
                    } // for allFoodList
%>              
                    </select>
                    
                    &nbsp; Grammat: <input type="text" name="foodgrams<%= i+1 %>" 
                                           class="input-text-40px" value="<%= foodGrams %>">
               </td>
                  <!--  <input type="text" name="food<%= i+1 %>" class="input-text-300px" value="<%= foodName %>"></td> -->
            </tr>
<%
           } // for int i 0-9 html select
%>
            <tr>
                <td>Pvm (pp.kk.vvvv):</td> 
                <td>
                    <input type="text" name="diningDate" class="input-text-80px" value="<%= date %>">&nbsp;
                    Klo (tt:mm):
                    <input type="text" name="diningTime" class="input-text-40px" value="<%= time %>">&nbsp;
                
                </td>
            </tr>
            
            
            <tr>
                
                <td colspan="2">
                    <input type="submit" name="save" value="Tallenna" class="large-button">
                    <input type="button" name="list" value="Näytä ruokailut" class="large-button"
                           onclick="redir('dining.jtml?act=list')">
                
                </td> 
            </tr>
        </table>
    </form>
        
    
</div>

<%@include file="footer.jsp" %>
