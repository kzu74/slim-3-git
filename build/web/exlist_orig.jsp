<%@ page import="java.util.List" %>
<%@ page import="net.slimmer.model.Exercise" %>
<%@ page import="app.commons.util.MyRequest" %>

<%
    List<Exercise> exerciseList = MyRequest.getExerciseList(request);
%>


<%@include file="header.jsp" %>

<div class="basic-content">

    <h1>Treenit</h1>
    
    <form method="get" action="ExerciseServlet">
        <table>
            <td><b>Kuvaus</b></td>
            <td class="td-120px"><b>Pvm</b></td>
            <td class="td-100px"><b>Klo</b></td>
            <td class="td-70px"><b>Minuutit</b></td>
            <td class="td-90px"><b>Teho (1-10)</b></td>
            <td class="td-80px"><b>Kulutus</b></td>
<%
        String trBg = "";
        int i = 2;
    
        for(Exercise ex : exerciseList) {
            
            String sTime = ex.getStartTimeString();
            String eTime = ex.getEndTimeString();
            
            
            if(i % 2 == 0) { trBg = "trColoured"; } else { trBg = ""; }
%>            
            <tr class="<%= trBg %>">
                <td>
                    <a class="list-link" href="exercise.jtml?act=summary&id=<%= ex.getId() %>"><%= ex.getName() %></a>
                </td>
                <td>
                    <%= ex.getDateWeekdayPrefix() %>
                    <%--
                    <a href="exercise.jtml?act=delete&id=<%= exercise.getId() %>" class="button1">Poista</a>
                    <a href="exercise.jtml?act=edit&id=<%= exercise.getId() %>" class="button1">Muokkaa</a>
                    --%>
                </td> 
                <td><%= sTime %> - <%= eTime %></td>
                <td><%= ex.getMinutes() %>min</td>
                <td><%= ex.getPower() %></td>
                <td><%= ex.getCalories() %> kcal</td>
                
<!--
                <td class="td-button">
                
                </td>
-->
            </tr>
<%
            i++;
       }
%>             
        </table>
    </form>
        
    
</div>

<%@include file="footer.jsp" %>
