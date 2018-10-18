<%@ page import="app.commons.util.Converter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="net.slimmer.model.Dining" %>
<%@ page import="net.slimmer.model.DiningStats" %>
<%@ page import="app.commons.util.MyRequest" %>
<%@ page import="app.commons.util.MyCalendar" %>
<%@ page import="java.util.Calendar" %>

<%
    //List<DiningStats> dsList = MyRequest.getDiningStatsList(request);
    Map<String, List<DiningStats>> weeklyMap = MyRequest.getDiningStatsMap(request);
    //DiningStats dsFirst = dsList.get(0);
    //Calendar cFirst = dsFirst.getCalendarDate();
%>


<%@include file="header.jsp" %>

<div class="basic-content">

    <h1>Ruokailut viikoittain</h1>
    
<%
    for(String mapKey : weeklyMap.keySet()) {
%>    
    
    <div class="clear-both-10px">
        <span class="table-header">
           <%= mapKey %>
        </span>
    </div>

        <table class="list-table">
            <tr>
                <td class="bold">Pvm</td>
                <td class="bold">Hinta</td>
                <td class="bold">Energia</td>
                <td class="bold">Kasvikset (g)</td>
<%--                <td class="bold">Ateriat (lkm)</td> --%>
            </tr>
<%
        String trBg = "";
        int i = 2;
        String dateTime = "";
    
        List<DiningStats> dsList = weeklyMap.get(mapKey);
        for(DiningStats ds : dsList) {
            
            Calendar c = ds.getCalendarDate();
            
            if(i % 2 == 0) { trBg = "trColoured"; } else { trBg = ""; }
%>            
            <tr class="<%= trBg %>">
                <td class="td-300px">
                    <a class="list-link" href="dining.jtml?act=daysummary&day=<%= ds.getDate() %>"><%= ds.getDatePrefix() %></a>
                </td> 
                <td class="td-80px">
                    <%= ds.getPriceString() %>&euro;
                </td>
                <td class="td-80px">
                    <%= ds.getEnergy() %> kcal
                </td>
                <td class="td-80px">
                    <%= ds.getVegetableGrams() %>g
                </td>
<%--                
                <td class="td-100px">
                    <%= ds.getDiningsCount() %>
                </td>
--%>
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
<%
   } // map
%>        
    
</div>

<%@include file="footer.jsp" %>
