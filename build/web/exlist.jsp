<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%@ page import="net.slimmer.model.Exercise" %>
<%@ page import="net.slimmer.model.Sport" %>
<%@ page import="app.commons.util.MyRequest" %>

<%
    Map<String, List<Exercise>> exMap = MyRequest.getExerciseMap(request);
%>


<%@include file="header.jsp" %>

<script>
    
$(document).ready(function() {
    
    var box;
    
    $(".ex-wrapper").mouseenter(function(e){
        
        //$('.ex-hover-box').hide();
    
        box = $(this).next(); // ex-hover-box
        
        $(this).css({"background": "#fbf5d2"});
        
        var toppis = $(document).scrollTop() + 80;

        var leftis = 620;
                
        box.css({ "top": toppis, "left": leftis }) 
        
        clearTimeout(box.data('timeoutId'));
        
        box.fadeIn(200);
        
    }).mouseleave(function(){
        
        $(".ex-wrapper").css({"background": "#fff"});
    
        var timeoutId = setTimeout(function() {
            
            $(".ex-wrapper").css({"background": "#fff"});
            box.hide();
        }, 
        30);
        
        box.data('timeoutId', timeoutId); 
        
    });

    $(".ex-hover-box").mouseenter(function(){
        
        clearTimeout($(this).data('timeoutId'));
        
    }).mouseleave(function(){
        
        $(".ex-wrapper").css({"background": "#fff"});
        
        var timeoutId = setTimeout(function(){
            $(".ex-hover-box").hide();
        }, 30);
        
        $(".ex-hover-box").data('timeoutId', timeoutId); 
    
    });
    
    /**
     * Ongelma: kun klikkaa muokkaa buttonia, niin tapahtuu alla oleva.
     */
    $(document).mousedown(function(e) {
        
        var target = e.target; // jäin tähän 2.7.2014 10:31
        
        if(!$(target).attr('class').is('large-button')) {
            $(".ex-hover-box").hide();
        }
        
//        alert("targetti class = " + $(target).attr('class'));
        
        
        
    });
    
    
});
    
</script>
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

        <div class="ex-wrapper" id="<%= date %>">

            <div class="icon-div">
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
                    
        <div class="ex-hover-box" id="ex-<%= ex.getId() %>">
            
            <div class="hover-header-area">
                <div class="icon-div">
                    <a href=""><img src="images/icons/<%= sport.getAbbreviation() %>.png" title="<%= sport.getName() %>"></a>
                </div>
                <div class="hover-header-right-area">
                    <div class="hover-header">
                        <%= ex.getName() %> klo 
                            <%= ex.getStartTimeString() %> - <%= ex.getEndTimeString() %>
                    </div>
                    <div class="hover-power">
                        Teho (1-10) = <%= ex.getPower() %> &nbsp;&nbsp; Minuutit = <%= ex.getMinutes() %> min
                         &nbsp;&nbsp; Kulutus = <%= ex.getCalories() %> kcal
                    </div>
                </div>
            </div>

            <div class="hover-content-area">
                <div class="info-text">
                    <%= ex.getComments() %>
                </div>
            </div>
            <div class="hover-button-area">
                <input type="button" value="Muokkaa" class="large-button"
                       onclick="redir('exercise.jtml?act=edit&id=<%= ex.getId() %>')">
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
