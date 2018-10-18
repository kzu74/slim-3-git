<%@ page import="net.slimmer.model.Food" %>


<%
    Food f = new Food();
    Object o = request.getAttribute("food");
    if(o != null) {
        f = (Food)o;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <h2>Ruoka id = <%= f.getId() %></h2>
    </body>
</html>
