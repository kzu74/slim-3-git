<%@page import="app.commons.util.MyCalendar"%>
<%@ page import="java.util.List" %>
<%@ page import="app.commons.util.MyRequest"%>
<%@ page import="app.commons.jsp.HelperJSP "%>
<%@ page import="net.slimmer.model.Serving" %>
<%@ page import="net.slimmer.model.Food" %>


<%

    Serving serving = MyRequest.getServing(request);
    
    if(serving == null) { // case insert
        serving = new Serving();
    }
    
    List<Food> foodList = serving.getFoodList();
    
    List<Food> allFoodsList = MyRequest.getAllFoodsList(request);
    
    String h1 = "Tallenna annos";
    
    if(serving.getId() > 0) {
        h1 = "Muuta annoksen tietoja"; // + serving.getName();
    }

    HelperJSP helperJSP = new HelperJSP();
%>

<%@include file="header.jsp" %>

<div class="basic-content">

    <h1><%= h1 %></h1>
    
    <form method="post" action="serving.jtml">
        
        <input type="hidden" name="id" value="<%= serving.getId() %>">
        <input type="hidden" name="act" value="save">
        
        <%-- jäin tähän --%>
        <table>
            <tr>
                <td class="td-120px">*Nimi:</td> 
                <td><input type="text" name="name" class="input-text-300px" value="<%= serving.getName() %>"></td>
            </tr>
            
<%
            String foodName = "";
            
            for(int i = 0; i < 10; i++) {
                
                System.out.println("i =============== " + i);
                
                Food food = serving.getFood(i);
                
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

                    int idx = 0;
                    
                    int foodGrams = 0;
                    
                    for(Food allFood : allFoodsList) {
                        
                        Food servingFood = serving.getFood(idx);
                        
                        if(servingFood != null && servingFood.getId() == allFood.getId() 
                                && !helperJSP.htmlSelectAlreadySet(i, servingFood.getId())) {
                            
                            foodGrams = servingFood.getGrams();
                            selected = "selected";
                            helperJSP.flagHtmlSelect(i, servingFood.getId()); // jäin tähän!!! 1.9.2013 klo 16:12
                        }
                        else {
                            selected = "";
                        }
                        idx++;
%>                        
                        <option name="" value="<%= allFood.getId() %>" <%= selected %>><%= allFood.getName() %></option> 
<%
                    } // for allFoodList
%>              
                    </select>
                    
                    &nbsp; Grammat: <input type="text" name="foodgrams<%= i+1 %>" 
                                           class="input-text-four-letters" value="<%= foodGrams %>">
               </td>
                  <!--  <input type="text" name="food<%= i+1 %>" class="input-text-300px" value="<%= foodName %>"></td> -->
            </tr>
<%
           } // for int i 0-9 html select
%>
            <tr>
                
                <td colspan="2">
                    <input type="submit" name="save" value="Tallenna" class="large-button">
                    <input type="button" name="list" value="Näytä annokset" class="large-button"
                           onclick="redir('serving.jtml?act=list')">
                
                </td> 
            </tr>
        </table>
    </form>
        
    
</div>

<%@include file="footer.jsp" %>
