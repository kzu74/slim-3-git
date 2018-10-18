<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%@ page import="net.slimmer.model.Exercise" %>
<%@ page import="net.slimmer.model.Sport" %>
<%@ page import="app.commons.util.MyRequest" %>

<%
    Map<String, List<Exercise>> exMap = MyRequest.getExerciseMap(request);
%>

<script>
$(document.ready)( {
    
    function hovIn() {
    
    var areaID = $(this).attr('id');
    //alert('['+areaID+']');
    if (areaID == 'CUST_1') {
        $('#myDiv').show();
    }
}

    function hovOut() {
    $('#myDiv').hide();
}

    $('.icon-div').hover(hovIn, hovOut);

}) 
</script>

<%@include file="header.jsp" %>

<div class="basic-content">

    <h1>Treenit</h1>
    
<%
    for(String date : exMap.keySet()) {
%>

    <div class="ex-all-wrapper">

        <div class="clear-date-header"><%= date %></div>
<%
        List<Exercise> exList = exMap.get(date);
        
        for(Exercise ex : exList) {
            
        Sport sport = ex.getSport();
        
        System.out.println("SPORT ====================== " + sport);
%>    

            <div class="ex-wrapper">

                <div class="icon-div" id="ex<%= ex.getId() %>">
                    <a href="">
                        <img src="images/icons/<%= sport.getAbbreviation() %>.png" title="<%= sport.getName() %>"></a>
                </div>

                <div class="left-side">

                    <div class="name-header"><a href="exercise.jtml?act=summary&id=<%= ex.getId() %>" class="ex-name-link"><%= ex.getName() %> klo 
                            <%= ex.getStartTimeString() %> - <%= ex.getEndTimeString() %></a></div>
                    <div class="data-div">
                        Teho (1-10) = <%= ex.getPower() %> &nbsp;&nbsp; Minuutit = <%= ex.getMinutes() %> min
                         &nbsp;&nbsp; Kulutus = <%= ex.getCalories() %> kcal
                    </div>
                </div>
                <div class="right-side">
                    <a href="exercise.jtml?act=summary&id=<%= ex.getId() %>" class="ex-comments-link">
                    <%= ex.getComments() %>
                    </a>
                </div>

            </div>
<%
        } // exList
%>
    </div>
<%    
    } // exMap
%>
        
    
</div>

<%@include file="footer.jsp" %>
