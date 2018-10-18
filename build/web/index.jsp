<%@page import="app.commons.util.MySession" %>

<%
    String fn2 = "";
    String welcome = "Tervetuloa!";
    User u = MySession.getUser(request);
    if(u != null)  {
        welcome = "Tervetuloa, " + u.getFirstName() + "!";
    }

%>

<%@include file="header.jsp" %>
<div class="basic-content">

    <h1><%= welcome %></h1>
    <table>
        <tr><td class="front-font">
        Täällä voit pitää kirjaa siitä mitä olet syönyt milloinkin. 
        Näet kuinka paljon kaloreita on tullut minäkin päivänä. Alat huomata mistä tulee 
        helposti paljon kaloreita ja voit myös kirjata kommentteja
        mitä tapahtui juuri ennen päädyit ostamaan puoli kiloa karkkia :-)
            </td></tr>
    </table>


</div>
<%@include file="footer.jsp" %>
    