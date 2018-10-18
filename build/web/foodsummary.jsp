<%@ page import="net.slimmer.model.Food" %>

<%
    Food food = null;

    Object fobj = request.getAttribute("food");
    
    if(fobj != null) {
        food = (Food)fobj;
    }
%>

<%@include file="header.jsp" %>

<script>
    $(document).ready(function() {
<% 
        if(food.getId() == 0) { 
%>
            $("#name").focus(); // cursor to text field name when creating new food
<%      
        }
        else {
%>
            scrollToH1();
<%        
        }
%>
    });
</script>


<div class="basic-content">

    <h1><%= food.getName() %></h1>
    
    <%@include file="messages.jsp" %>
    
    <table class="summary-table">
        <tr class="bg-hover">
            <td class="td-150px">Nimi:</td> 
            <td><%= food.getName() %></td>
        </tr>
        <tr class="bg-hover">
            <td class="td-150px">Energia (kcal/100g):</td> 
            <td><%= food.getEnergy() %> kcal</td>
        </tr>
        <tr class="bg-hover">
            <td class="td-150px">Proteiini (100g):</td> 
            <td><%= food.getProteinString() %>g</td>
        </tr>
        <tr class="bg-hover">
            <td class="td-150px">Hiilihydraatti (100g):</td> 
            <td><%= food.getCarbsString() %>g</td>
        </tr>
        <tr class="bg-hover">
            <td class="td-150px">Rasva (100g):</td> 
            <td><%= food.getFatString() %>g</td>
        </tr>
        <tr class="bg-hover">
            <td class="td-150px">Kuitu (100g):</td> 
            <td><%= food.getFiberString() %>g</td>
        </tr>
        <tr class="bg-hover">
            <td class="td-150px">Natrium (100g):</td> 
            <td><%= food.getNatriumString() %>g</td>
        </tr>
        <tr class="bg-hover">
            <td class="td-150px">Hinta (1kg):</td> 
            <td><%= food.getUnitPriceString() %>&euro;</td>
        </tr>
        <tr>
            <td class="td-500px" colspan="2">
                <input type="button" value="Näytä elintarvikkeet" class="large-button" 
                       onclick="redir('food.jtml?act=list')">
                <input type="button" value="Muokkaa" class="large-button" 
                                       onclick="redir('food.jtml?act=edit&id=<%= food.getId() %>')">
                <!-- jäin tähän -->
            </td> 
        </tr>
    </table>
        
    
</div>

<%@include file="footer.jsp" %>
