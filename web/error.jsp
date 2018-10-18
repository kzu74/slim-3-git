<%-- 
    Document   : error
    Created on : 21.4.2016, 11:38:05
    Author     : kaitsu
--%>

<%@page import="app.commons.util.Converter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exception page</title>
    </head>
    <body>
        <h1>Virhe tapahtui..........</h1>

        <%
            Exception e = null;
            Object o = request.getAttribute("ERROR");
            if (o != null) {
                e = (Exception) o;

            }
            if (e != null) {
        %>
        <p>
            <%= e.getMessage() %>
        </p>
        <p>
            <%= Converter.exceptionToStackTraceString(e) %>
        </p>
        
        
        <%
            }
            else {
%>      

        Exception oli NULL!

<%
            }
%>        



    </p>
</body>
</html>
