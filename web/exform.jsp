<%@ page import="app.commons.util.MyRequest"%>
<%@ page import="net.slimmer.model.Exercise" %>
<%@ page import="net.slimmer.model.Sport" %>
<%@ page import="app.commons.util.MyRequest" %>
<%@ page import="app.commons.util.Trace" %>
<%@ page import="java.util.*" %>

<%
// null pointer tulee... jäin tähän 27.12.2013

    Trace t = new Trace();
    
    Exercise ex = MyRequest.getExercise(request);
    t.out("-----------------------------------------");
    t.out("ex == " + ex);
    
    List<Sport> sportList = MyRequest.getSportList(request);
    
    Sport selSport = null;
    
    if(ex != null) {
        selSport = ex.getSport();
    }
    
    t.out("sportList == " + sportList);
    t.out("-----------------------------------------");
    
    
    
    if(ex == null) { // case insert
        ex = new Exercise();
    }
    
    String h1 = "Tallenna treeni";
    
    if(ex.getId() > 0) {
        h1 = "Muokkaa: " + ex.getName();
    }

%>

<%@include file="header.jsp" %>

<div class="basic-content">

    <h1><%= h1 %></h1>
    
    <form method="post" action="exercise.jtml">
        <input type="hidden" name="id" value="<%= ex.getId() %>">
        <input type="hidden" name="act" value="save">
        
        <%-- jäin tähän --%>
        <table class="form-table">
            <tr>
                <td width="130">*Nimi:</td> 
                <td><input type="text" name="name" class="input-text-300px" value="<%= ex.getName() %>"></td>
            </tr>
            <tr>
                <td width="130">Kommentit:</td> 
                <td><textarea name="comments" cols="45" rows="5"><%= ex.getComments() %></textarea></td>
            </tr>

            <tr>
                <td width="130">Laji:</td> 
                <td>
                <select name="sportId">
<%
                    String selected = "";
                    
                    for(Sport sport : sportList) {
                        String name = sport.getName();
                        int sportId = sport.getId();
                        
                        if(selSport != null && selSport.getId() == sport.getId()) selected = "selected"; else selected = "";
%>                        
                            <option value="<%= sportId %>" <%= selected %>><%= name %></option>
<%
                   }
%>                        
                    </select>
                </td>
            </tr>
            
            <tr>
                <td width="130">Teho:</td> 
                <td>
                <select name="power">
<%
                    selected = "";
                    
                    for(int i = 1; i <= 10; i++) {
                        
                        if(ex != null && ex.getPower() == i) selected = "selected"; else selected = "";
%>                        
                            <option value="<%= i %>" <%= selected %>><%= i %></option>
<%
                   }
%>                        
                    </select>
                </td>
            </tr>
            <tr>
                <td width="130">Päivä (pp.kk.vvvv):</td> 
                <td><input type="text" name="date" class="input-text-80px" value="<%= ex.getDate() %>"></td>
            </tr>
            
            <tr>
                <td width="130">Klo (tt:mm):</td> 
                <td><input type="text" name="startTime" class="input-text-40px" value="<%= ex.getStartTimeString() %>">
                - <input type="text" name="endTime" class="input-text-40px" value="<%= ex.getEndTimeString() %>">
                </td>
            </tr>
            
            <tr>
                
                <td width="130" colspan="2">
                    <input type="submit" name="save" value="Tallenna" class="large-button">
                    <input type="button" name="list" value="Näytä treenit" class="large-button"
                           onclick="redir('exercise.jtml?act=list')">
                
                </td> 
            </tr>
        </table>
    </form>
        
    
</div>

<%@include file="footer.jsp" %>
