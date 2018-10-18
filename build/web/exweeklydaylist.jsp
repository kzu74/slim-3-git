<%@ page import="app.commons.util.Converter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="net.slimmer.model.Exercise" %>
<%@ page import="net.slimmer.model.ExerciseStats" %>
<%@ page import="app.commons.util.MyRequest" %>
<%@ page import="app.commons.util.MyCalendar" %>
<%@ page import="java.util.Calendar" %>

<%
    //List<DiningStats> dsList = MyRequest.getDiningStatsList(request);
    Map<Integer, List<ExerciseStats>> weeklyMap = MyRequest.getExerciseStatsMap(request);
    //DiningStats dsFirst = dsList.get(0);
    //Calendar cFirst = dsFirst.getCalendarDate();
%>


<%@include file="header.jsp" %>

<div class="basic-content">

    <h1>Treenit viikoittain</h1>
    
<%
    for(Integer weekNumber : weeklyMap.keySet()) {
%>    
    
    <div class="clear-both-10px">
        <span class="table-header">
           Viikko <%= weekNumber %>
        </span>
    </div>
<%-- jäin tähän 1.1.2014 klo 12:10  --%>
        <table class="list-table">
            <tr>
                <td class="bold">Pvm</td>
                <td class="bold">Treenit</td>
                <td class="bold">Minuutit</td>
                <td class="bold">Kulutus</td>
            </tr>
<%
        String trBg = "";
        int i = 2;
        String dateTime = "";
    
        List<ExerciseStats> esList = weeklyMap.get(weekNumber);
        for(ExerciseStats es : esList) {
            
            Calendar c = es.getCalendarDate();
            
            if(i % 2 == 0) { trBg = "trColoured"; } else { trBg = ""; }
%>            
            <tr class="<%= trBg %>">
                <td class="td-300px">
                    <a class="list-link" href="dining.jtml?act=daysummary&day=<%= es.getDate() %>"><%= es.getDatePrefix() %></a>
                </td> 
                <td class="td-80px">
                    <%= es.getExerciseCount() %>
                </td>
                <td class="td-80px">
                    <%= es.getMinutes() %>
                </td>
                <td class="td-100px">
                    <%= es.getCalories() %> kcal
                </td>
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
