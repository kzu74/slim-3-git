<%@ page import="app.commons.util.MyRequest" %>
<%@ page import="net.slimmer.model.Food" %>
<%@ page import="net.slimmer.model.Dining" %>

<%
    List<Dining> diningList = MyRequest.getDiningList(request);
    String day = MyRequest.getDayString(request);
    String prefixDay = MyRequest.getPrefixDayString(request);
    String prevDay = MyRequest.getPrevDayString(request);
    String nextDay = MyRequest.getNextDayString(request);
%>

<%@include file="header.jsp" %>

<div class="basic-content">

    <div class="clear-both">
    </div>

    <h1>Päivän ruokailut - <%= prefixDay%></h1>

    <%@include file="messages.jsp" %>

    <table class="summary-table">
        <tr>
            <td class="td-300px"><b>Ruokailu</b></td> 
            <td class="td-60px"><b>Klo</b></td>
            <td class="td-60px"><b>Hinta</b></td>
            <td class="td-80px"><b>Kalorit</b></td>
            <td class="td-60px"><b>Proteiini</b></td>
            <td class="td-60px"><b>Hiilarit</b></td>
            <td class="td-60px"><b>Rasva</b></td>
            <td class="td-60px"><b>Kasvikset</b></td>
            <td class="td-60px"><b>Natrium</b></td>
            <td class="td-60px"><b>Kuitu</b></td>

        </tr>
        <%    int i = 0;
            for (Dining dining : diningList) {
                i++;
        %>        
        <tr class="bg-hover">
            <td class="tooltip-td">
                <a class="tooltip" href="dining.jtml?act=summary&id=<%= dining.getId()%>">
                    <%= i%>. <%= dining.getName()%>
                </a>
                <span>
                    <img src="css/images/popup-arrow.gif" class="callout">
                    <a class="button-edit" href="dining.jtml?act=summary&id=<%= dining.getId()%>">Näytä</a>
                    <a class="button-edit" href="dining.jtml?act=form&id=<%= dining.getId()%>">Muokkaa</a>
                    <a class="button-edit" href="dining.jtml?act=copy&id=<%= dining.getId()%>">Kopioi</a>
                    <a class="button-edit" href="dining.jtml?act=delete&id=<%= dining.getId()%>">Poista</a>

                </span>
            </td>
            <td><%= dining.getTime()%></td>
            <td><%= dining.getPriceString()%>&euro;</td>

            <td><%= dining.getEnergy()%> kcal</td>
            <td><%= dining.getProteinString()%>g</td>
            <td><%= dining.getCarbsString()%>g</td>
            <td><%= dining.getFatString()%>g</td>
            <td><%= dining.getVegetableGrams()%>g</td>
            <td><%= dining.getNatriumString()%>g</td>
            <td><%= dining.getFiberString()%>g</td>
        </tr>
        <%
            }
        %>        
        <tr><td colspan="10"><hr></td></tr>
        <tr class="bg-hover">
            <td>Yhteensä:</td> 
            <td></td>
            <td><%= Dining.getPriceSumString(diningList)%>&euro;</td> 
            <td><%= Dining.getEnergySum(diningList)%> kcal</td> 
            <td><%= Dining.getProteinSumString(diningList)%>g</td>
            <td><%= Dining.getCarbsSumString(diningList)%>g</td>

            <td><%= Dining.getFatSumString(diningList)%>g</td>
            <td><%= Dining.getVegetablesSum(diningList)%>g</td>
            <td><%= Dining.getNatriumSumString(diningList)%>g</td>
            <td><%= Dining.getFiberSumString(diningList)%>g</td>

        </tr>
        <tr>
            <td colspan="10"><input type="button" value="Lisää ruokailu" class="large-button" 
                                    onclick="redir('dining.jtml?act=form&day=<%= day%>')"> 

                <input type="button" value="<=  Edellinen päivä" class="input-button" 
                       onclick="redir('dining.jtml?act=daysummary&day=<%= prevDay%>')">
                <input type="button" value="Seuraava päivä  =>" class="input-button" 
                       onclick="redir('dining.jtml?act=daysummary&day=<%= nextDay%>')">

            </td> 
        </tr>
    </table>


</div>

<%@include file="footer.jsp" %>
