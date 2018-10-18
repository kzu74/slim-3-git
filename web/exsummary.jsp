<%@ page import="app.commons.util.MyCalendar"%>
<%@ page import="app.commons.util.MyRequest"%>
<%@ page import="app.commons.util.MySession"%>
<%@ page import="net.slimmer.model.Exercise" %>
<%@ page import="net.slimmer.model.Sport" %>
<%@ page import="net.slimmer.model.User" %>

<%
    Exercise exercise = MyRequest.getExercise(request);
    Sport sport = exercise.getSport();
    //User user = MySession.getUser(request);

%>

<%@include file="header.jsp" %>

<div class="basic-content">

    <h1><%= exercise.getName() %></h1>
    
    <%@include file="messages.jsp" %>
    
    <table class="summary-table">
        <tr class="bg-hover">
            <td class="td-120px">Nimi:</td> 
            <td><%= exercise.getName() %></td>
        </tr>
        <tr class="bg-hover">
            <td class="td-120-up-px">Kommentit:</td> 
            <td class="td-desc"><%= exercise.getComments() %></td>
        </tr>
        <tr class="bg-hover">
            <td class="td-120px">Laji:</td> 
            <td><%= sport.getName() %> (<%= sport.getMinCalories() %> - <%= sport.getMaxCalories() %>) kcal / tunti / 70kg henkilö</td>
        </tr>
        <tr class="bg-hover">
            <td class="td-120px">Teho (1-10):</td> 
            <td><%= exercise.getPower() %></td>
        </tr>
        <tr class="bg-hover">
            <td class="td-120px">Pvm:</td> 
            <td><%= exercise.getDateWeekdayPrefix() %></td>
        </tr>
        <tr class="bg-hover">
            <td class="td-120px">Klo:</td> 
            <td><%= exercise.getStartTimeString() %> - <%= exercise.getEndTimeString() %></td>
        </tr>
        <tr class="bg-hover">
            <td class="td-120px">Minuutit:</td> 
            <td><%= MyCalendar.getMinutesBetween(exercise.getStartTime(), exercise.getEndTime()) %> min</td>
        </tr>
        <tr class="bg-hover">
            <td class="td-120px">Kulutus:</td> 
            <td><%= exercise.getCalories() %> kcal</td>
        </tr>
        <tr>
            <td class="td-500px" colspan="2">
                <input type="button" value="Näytä treenit" class="large-button" 
                       onclick="redir('exercise.jtml?act=list')">
                <input type="button" value="Muokkaa" class="large-button" 
                                       onclick="redir('exercise.jtml?act=edit&id=<%= exercise.getId() %>')">
            </td> 
        </tr>
    </table>
        
    
</div>

<%@include file="footer.jsp" %>
