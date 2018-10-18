<%@page import="app.commons.util.Converter"%>
<%@ page import="java.util.List" %>
<%@ page import="net.slimmer.model.Dining" %>
<%@ page import="net.slimmer.model.DiningStats" %>
<%@ page import="app.commons.util.MyRequest" %>
<%@ page import="app.commons.util.MyCalendar" %>
<%@ page import="java.util.Calendar" %>

<%
    List<DiningStats> dsList = MyRequest.getDiningStatsList(request);
    DiningStats dsFirst = dsList.get(0);
    Calendar cFirst = dsFirst.getCalendarDate();
%>


<%@include file="header.jsp" %>

<div class="basic-content">

    <h1>Ruokailut</h1>
    
    <div class="clear-both-10px">
        <span class="table-header">
           Päivittäisen energian keskiarvo = <%= DiningStats.getAverageEnergy(dsList) %> kcal
        </span>
    </div>

            <%--
    <div class="clear-both">
        <span class="table-header">
            &nbsp;Viikko <%= cFirst.get(Calendar.WEEK_OF_YEAR) %> - 
            Energia keskiarvo <%= DiningStats.getWeeklyAverageEnergies(dsList).get(0) %> kcal
        </span>
    </div>
    --%>
    <form method="get" action="DiningServlet">
        <%-- jäin tähän --%>
        <table class="list-table">
            <tr>
                <td class="bold">Pvm</td>
                <td class="bold">Energia</td>
                <td class="bold">Kasvikset (g)</td>
                <td class="bold">Ateriat (lkm)</td>
            </tr>
<%
        String trBg = "";
        int i = 2;
        String dateTime = "";
    
        for(DiningStats ds : dsList) {
            
            Calendar c = ds.getCalendarDate();
            
            if(i % 2 == 0) { trBg = "trColoured"; } else { trBg = ""; }
%>            
            <tr class="<%= trBg %>">
                <td class="td-300px">
                    <a class="list-link" href="dining.jtml?act=daysummary&day=<%= ds.getDate() %>"><%= ds.getDatePrefix() %></a>
                </td> 
                <td class="td-80px">
                    <%= ds.getEnergy() %> kcal
                </td>
                <td class="td-80px">
                    <%= ds.getVegetableGrams() %>g
                </td>
                <td class="td-100px">
                    <%= ds.getDiningsCount() %>
                </td>
         
                <%--
                <td class="td-button">
                    <a href="dining.jtml?act=edit&id=<%= dining.getId() %>" class="button1">Muokkaa</a>&nbsp;
                    <a href="dining.jtml?act=delete&id=<%= dining.getId() %>" class="button1">Poista</a>
                
                </td>
                --%>
            </tr>
<%
            i++;
       }
%>             
        </table>
    </form>
        
    
</div>

<%@include file="footer.jsp" %>
