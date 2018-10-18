<%@ page import="app.commons.util.MyRequest"%>
<%@ page import="net.slimmer.model.Food" %>
<%@ page import="app.commons.util.MyRequest" %>
<%@ page import="java.util.*" %>

<%
    Food food = MyRequest.getFood(request);
    
    if(food == null) { // case insert
        food = new Food();
    }
    
    String h1 = "Tallenna elintarvike";
    
    if(food.getId() > 0) {
        h1 = "Muokkaa: " + food.getName();
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

    <h1><%= h1 %></h1>
    
    <%@include file="messages.jsp" %>
    
    <form method="post" action="food.jtml">
        <input type="hidden" name="id" value="<%= food.getId() %>">
        <input type="hidden" name="act" value="save">
        
        <%-- jäin tähän --%>
        <table class="form-table">
            <tr>
                <td width="130">*Nimi:</td> 
                <td><input type="text" name="name" class="input-text-300px" value="<%= food.getName() %>"></td>
            </tr>
            <tr>
                <td width="130">Energia (kcal/100g):</td> 
                <td><input type="text" name="energy" class="input-text-40px" value="<%= food.getEnergy() %>"></td>
            </tr>
            <tr>
                <td width="130">Proteiini (100g):</td> 
                <td><input type="text" name="protein" class="input-text-40px" value="<%= food.getProteinString() %>"></td>
            </tr>
            <tr>
                <td width="130">Hiilihydraatti (100g):</td> 
                <td><input type="text" name="carbs" class="input-text-40px" value="<%= food.getCarbsString() %>"></td>
            </tr>
            <tr>
                <td width="130">Rasva (100g):</td> 
                <td><input type="text" name="fat" class="input-text-40px" value="<%= food.getFatString() %>"></td>
            </tr>
            <tr>
                <td width="130">Kuidut (100g):</td> 
                <td><input type="text" name="fiber" class="input-text-40px" value="<%= food.getFiberString() %>"></td>
            </tr>
            <tr>
                <td width="130">Natrium (100g):</td> 
                <td><input type="text" name="natrium" class="input-text-40px" value="<%= food.getNatriumString() %>"></td>
            </tr>
            <tr>
                <td width="130">Hinta (1kg):</td> 
                <td><input type="text" name="price" class="input-text-40px" value="<%= food.getUnitPriceString() %>"></td>
            </tr>
            <tr>
                <td width="130">Tyyppi:</td> 
                <td>
<%
                Map<String, String> foodTypeMap = Food.getFoodTypeMap();
%>                    
                <select name="type">
<%
                    String selected = "";
                    
                    for(Map.Entry entry : foodTypeMap.entrySet()) {
                        String type = (String)entry.getKey();
                        String typeLabel = (String)entry.getValue();
                        
                        if(food.getType().equals(type)) selected = "selected"; else selected = "";
%>                        
                            <option value="<%= type %>" <%= selected %>><%= typeLabel %></option>
<%
                   }
%>                        
                    </select>
                </td>
            </tr>
            <tr>
                <td width="130">Laita julkiseksi:</td> 
                <td><input type="checkbox" name="isPublic" value="yes" checked>Kyllä</td>
            </tr>
            
            
            <tr>
                
                <td width="130" colspan="2">
                    <input type="submit" name="save" value="Tallenna" class="large-button">
                    <input type="button" name="list" value="Näytä elintarvikkeet" class="large-button"
                           onclick="redir('food.jtml?act=list')">
                
                </td> 
            </tr>
        </table>
    </form>
        
    
</div>

<%@include file="footer.jsp" %>
