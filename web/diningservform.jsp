<%@page import="app.commons.util.Converter"%>
<%@ page import="app.commons.util.MyCalendar"%>
<%@ page import="java.util.List" %>
<%@ page import="app.commons.util.MyRequest"%>
<%@ page import="app.commons.jsp.HelperJSP "%>
<%@ page import="net.slimmer.model.Dining" %>
<%@ page import="net.slimmer.model.Food" %>
<%@ page import="net.slimmer.model.Serving" %>



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
    
    List<Serving> servingList = MyRequest.getServingList(request);
    
    
   // int i = MyRequest.getCurrentFoodIndex(request);
    
    System.out.println("ALL FOODS LIST LENGTH == " + allFoodsList.size());
    
    String h1 = "Tallenna ruokailu";
    
    if(dining.getId() > 0) {
        h1 = "Muuta ruokailun tietoja"; // + dining.getName();
    }

    HelperJSP helperJSP = new HelperJSP();
%>

<%@include file="header.jsp" %>

<script>
    $(document).ready(function(){
        
        fokus();
        
        $('.form-table tr.colorTR').click(function () {
            $(this).find('td input:radio').prop('checked', true);
            $('.form-table tr.colorTR').removeClass("trActive");
            $(this).addClass("trActive");
        });    
        
        $('.form-table tr.colorTR').mouseover(function () {
            $(this).addClass("trHover");
        });

        $('.form-table tr.colorTR').mouseout(function () {
            $(this).removeClass("trHover");
        });        
    });
</script>


<div class="basic-content">

    <h1><%= h1 %></h1>
    
    <%@include file="messages.jsp" %>
        
    <form method="post" action="dining.jtml" >
        
        <input type="hidden" name="id" value="<%= dining.getId() %>">
        <input type="hidden" name="act" value="saveServ">
        
        <%-- jäin tähän --%>
        <table class="form-table">
            <tr>
                <td class="td-120px">*Nimi:</td> 
                <td class="td-400px">
                    <input type="text" name="name" class="input-text-300px" value="<%= dining.getName() %>" id="fokus">
                </td>
            </tr>
            <tr class="colorTR">
                <td>
                    <input type="radio" name="foodOption" value="serving" id="servingRadio" checked>
                    <label for="wServingRadio" class="radioLabel">Punnittu annos:</label>
                </td>
                <td>
                    <select name="servingId">
                        <option name="choose" value="none">Valitse annos...</option>
<%
                        for(Serving serving : servingList) {
%>               
                            <option name="" value="<%= serving.getId() %>"><%= serving.getName() %></option> 
<%
                       } // servingList
%>
                    
                    </select>
                    <!--&nbsp; -->
                </td>
            </tr>
            
            <tr class="colorTR">
                <td>
                    <input type="radio" name="foodOption" value="servingGrams" id="servingRadioGrams">
                    <label for="servingRadioGrams" class="radioLabel">Annos (g):</label>
                </td>
                <td>
                    <select name="servingGramsId">
                        <option name="choose" value="none">Valitse annos...</option>
<%
                        for(Serving serving : servingList) {
%>               
                            <option name="" value="<%= serving.getId() %>"><%= serving.getName() %></option> 
<%
                       } // servingList
%>
                    
                    </select>
                    &nbsp;
                    Grammat: <input type="text" name="servingGrams" 
                                           class="input-text-40px" value="0">
                </td>
            </tr>

            <tr class="colorTR">
                <td>
                    <input type="radio" name="foodOption" value="food" id="foodRadio">
                    <label for="foodRadio" class="radioLabel">Ruoka</label>
                    </td> 
                    <td colspan="2">
                    <select name="foodId">
                        
                        <option name="choose" value="none">Valitse ruoka...</option>
<%
                    for(Food allFood : allFoodsList) {
%>                        
                        <option name="" value="<%= allFood.getId() %>"><%= allFood.getName() %></option> 
<%
                    } // for allFoodList
%>              
                    </select>
                    &nbsp;
                    Grammat: <input type="text" name="foodGrams" 
                                           class="input-text-40px" value="0">
                    
            </tr>
            
            
            <tr>
                <td>Pvm (pp.kk.vvvv):</td> 
                <td>
                    <input type="text" name="diningDate" class="input-text-80px" value="<%= date %>">&nbsp;
                    Klo (tt:mm):
                    <input type="text" name="diningTime" class="input-text-40px" value="<%= time %>">&nbsp;
                
                </td>
            </tr>
            <tr>
                <td colspan="3">
<%
        if(dining.getId() > 0) {
%>                    
        <table class="innerTable">
            <tr>
                <td class="td-50px"><b>Valitse</b></td>
                <td class="td-400px"><b>Elintarvike</b></td> 
                <td class="td-120px"><b>Grammat</b></td>
                <td class="td-120px"><b>Kalorit</b></td>
                <td class="td-120px"><b>Proteiini</b></td>
                <td class="td-120px"><b>Hiilarit</b></td>
                <td class="td-120px"><b>Rasva</b></td>
            </tr>                        
<%
                int dFoodIndex = 0;
                for(Food dFood : diningFoodList) {
                    dFoodIndex++;
%>                    
            <tr>
                <td><input type="checkbox" name="food<%= dFoodIndex %>" value="<%= dFood.getId() %>"></td>
                <td><%= dFoodIndex %>. <%= dFood.getName() %></td>
                <td><%= dFood.getGrams() %>g</td>
                <td><%= dFood.getEnergyByGrams() %> kcal</td>
                <td><%= dFood.getProteinByGramsString() %> </td>                
                <td><%= dFood.getCarbsByGramsString() %> </td>                
                <td><%= dFood.getFatByGramsString() %> </td>                
                
            </tr>
<%
               } // diningFoodList
%>            
            <tr><td colspan="7"><hr></td></tr>
            <tr>
                <td colspan="2">Yhteensä:</td> 
                <td><%= dining.getFoodGramsTotal() %>g</td> 
                <td><%= dining.getEnergy() %> kcal</td>
                <td><%= dining.getProteinString() %>g</td>
                <td><%= dining.getCarbsString() %>g</td>
                <td><%= dining.getFatString() %>g</td>
            </tr>
        </table>
<%
       } // if(dining.getId() > 0)
%>            
                </td>
            </tr>
            <tr>
                
                <td colspan="6">
                    <input type="submit" name="save" value="Lisää ja tallenna" class="large-button" id="submitButton">
                    <% if(dining.getId() > 0) { %>
                    <input type="submit" name="del" value="Poista valitut" class="large-button" id="submitButton">
                    <% } %>
                    <input type="button" name="list" value="Näytä päivän ruokailut" class="large-button"
                           onclick="redir('dining.jtml?act=daysummary')">
                
                </td> 
            </tr>
        </table>
    </form>
        
    
</div>

<%@include file="footer.jsp" %>
