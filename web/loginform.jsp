<%@ page import="app.commons.util.MyRequest"%>
<%@ page import="net.slimmer.model.Food" %>
<%@ page import="app.commons.util.MyRequest" %>
<%@ page import="java.util.*" %>

<%

%>

<%@include file="header.jsp" %>

<div class="basic-content">

    <h1>Kirjaudu sisään</h1>
    
    <%@include file="messages.jsp" %>
    
    <form method="post" action="login.jtml">
        <input type="hidden" name="id" value="">
        <input type="hidden" name="act" value="login">
        
        <table class="form-table">
            <tr>
                <td width="100">Sähköposti:</td> 
                <td><input type="text" name="email" class="input-text-300px" value=""></td>
            </tr>
            <tr>
                <td width="100">Salasana:</td> 
                <td><input type="password" name="pass" class="input-text-300px"></td>
            </tr>
            
            <tr>
                
                <td width="130" colspan="2">
                    <input type="submit" name="save" value="Kirjaudu" class="large-button">
                </td> 
            </tr>
        </table>
    </form>
        
    
</div>

<%@include file="footer.jsp" %>
