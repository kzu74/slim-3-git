<%@ page import="app.commons.util.MyRequest" %>
<%@ page import="net.slimmer.model.Food" %>
<%@ page import="net.slimmer.model.Serving" %>


<%
    Serving serving = MyRequest.getServing(request);
    List<Food> foodList = serving.getFoodList();
    
%>

<%@include file="header.jsp" %>

<div class="basic-content">

    <h1><%= serving.getName() %></h1>
    
    <%@include file="messages.jsp" %>
    
    <table class="summary-table">
        <tr class="bg-hover">
            <td class="td-300px"><b>Ruoka</b></td> 
            <td class="td-120px"><b>Grammat</b></td>
            <td class="td-120px"><b>Kalorit</b></td>
            <td class="td-120px"><b>Proteiini</b></td>
            <td class="td-120px"><b>Hiilarit</b></td>
            <td class="td-120px"><b>Rasva</b></td>
        </tr>
<%
        int i = 0;
        for(Food food : foodList) {
            i++;
%>        
            <tr class="bg-hover">
                <td><%= i %>. <%= food.getName() %></td>
                <td><%= food.getGrams() %>g</td>
                <td><%= food.getEnergyByGrams() %> kcal</td>
                <td><%= food.getProteinByGramsString() %>g</td>
                <td><%= food.getCarbsByGramsString() %>g</td>
                <td><%= food.getFatByGramsString() %>g</td>
                
            </tr>
<%
       }
%>        
        <tr class="bg-hover"><td colspan="6"><hr></td></tr>
        <tr class="bg-hover">
            <td>Yhteensä:</td> 
            <td><%= serving.getFoodGramsTotal() %>g</td> 
            <td><%= serving.getEnergy() %> kcal</td>
            <td><%= serving.getProteinString() %> kcal</td>
            <td><%= serving.getCarbsString() %> kcal</td>
            <td><%= serving.getFatString() %> kcal</td>
        </tr>
        <tr>
            
            <td colspan="6">
                <input type="button" value="Lisää annos" class="large-button" 
                                   onclick="redir('serving.jtml?act=edit')">
                <input type="button" value="Muuta annoksen tietoja" class="large-button" 
                                   onclick="redir('serving.jtml?act=edit&id=<%= serving.getId() %>')">
                <input type="button" value="Poista annos" class="large-button" 
                                   onclick="redir('serving.jtml?act=delete?id=<%= serving.getId() %>')">
                <input type="button" value="Näytä annokset" class="large-button" 
                                   onclick="redir('serving.jtml?act=list')">
            </td> 
        </tr>
    </table>
        
    
</div>

<%@include file="footer.jsp" %>
