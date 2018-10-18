<%@page import="app.commons.util.Converter"%>
<%@ page import="app.commons.util.MyRequest" %>
<%@ page import="net.slimmer.model.Food" %>
<%@ page import="net.slimmer.model.Dining" %>


<%
    Dining dining = MyRequest.getDining(request);
    List<Food> foodList = dining.getFoodList();
%>

<%@include file="header.jsp" %>

<div class="basic-content">


    <h1><%= dining.getName() %> - <%--  MyCalendar do: getDateAndTimeString --%></h1>

    <%@include file="messages.jsp" %>
    
    <table class="summary-table">
        
        <tr>
                <th class="td-300px">Elintarvike</th> 
                <th class="td-50px">Paino</th>
                <th class="td-60px">Hinta</th>
                <th class="td-60px">Kcal</th>

                <th class="td-50px">Prot.</th>
                <th class="td-50px">Hiil.</th>
                <th class="td-50px">Rasva</th>
                <th class="td-50px">Natrium</th>
                <th class="td-50px">Kuitu</th>
                
                
        </tr>
<%
        for(int i=0; i < foodList.size(); i++) {
            
            Food food = foodList.get(i);
%>        
            <tr class="bg-hover">
                <td><a class="list-link" href="food.jtml?act=summary&id=<%= food.getId() %>"><%= i+1 %>. <%= food.getName() %></a></td>
                <td><%= food.getGramsString() %>g</td>
                <td><%= food.getServingPriceString() %>&euro;</td>
                <td><%= food.getEnergyByGrams() %> kcal</td>

                <td><%= food.getProteinByGramsString() %>g</td>
                <td><%= food.getCarbsByGramsString() %>g</td>
                <td><%= food.getFatByGramsString() %>g</td>
                <td><%= food.getNatriumByGramsString() %>g</td>
                <td><%= food.getFiberByGramsString() %>g</td>
                
            </tr>
<%
       }
%>        
        <tr><td colspan="9"><hr></td></tr>
        <tr class="bg-hover">
            <td>Yhteensä:</td> 
            <td><%= dining.getFoodGramsTotalString() %>g</td> 
            <td><%= dining.getPriceString() %>&euro;</td>
            <td><%= dining.getEnergy() %> kcal</td>

            <td><%= dining.getProteinString() %>g</td>
            <td><%= dining.getCarbsString() %>g</td>
            <td><%= dining.getFatString() %>g</td>
            <td><%= dining.getNatriumString() %>g</td>
            <td><%= dining.getFiberString() %>g</td>
            
        </tr>
<%
        if(MyRequest.getAct(request).equals("delete")) { //act was delete, show different buttons
%>        
        <tr>
            <td colspan="9">
                <input type="button" value="Näytä päivän ruokailut" class="large-button" 
                                   onclick="redir('dining.jtml?act=daysummary&day=<%= dining.getDate() %>')">                
            </td> 
            <td></td>
        </tr>

<%
        }
        else {
%>
        <tr>
            <td colspan="9">
                <input type="button" value="Lisää ruokailu" class="large-button" 
                                   onclick="redir('dining.jtml?act=form&day=<%= dining.getDate() %>')">                
                <input type="button" value="Muuta ruokailun tietoja" class="large-button" 
                                   onclick="redir('dining.jtml?act=form&id=<%= dining.getId() %>')">            
                <input type="button" value="Poista ruokailu" class="large-button" 
                                   onclick="redir('dining.jtml?act=delete&id=<%= dining.getId() %>')">            
                <input type="button" value="Kopioi ruokailu" class="large-button" 
                                   onclick="redir('dining.jtml?act=copy&id=<%= dining.getId() %>')">
            </td> 
            <td></td>
        </tr>
<%
        }
%>        
    </table>
        
    
</div>

<%@include file="footer.jsp" %>
