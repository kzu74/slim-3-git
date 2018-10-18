<%-- 
    Document   : captcha
    Created on : 1.1.2014, 16:16:54
    Author     : Kaitsu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Captcha test</h1>
        Kuva
    <img src="simpleImg" />
    Kuva
    <form action="captchaSubmit.jsp" method="post">
        <input name="answer" />
        <input type="submit" value="Lähetä" />
    </form>        
    </body>
</html>
