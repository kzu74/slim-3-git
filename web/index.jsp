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
        T��ll� voit pit�� kirjaa siit� mit� olet sy�nyt milloinkin. 
        N�et kuinka paljon kaloreita on tullut min�kin p�iv�n�. Alat huomata mist� tulee 
        helposti paljon kaloreita ja voit my�s kirjata kommentteja
        mit� tapahtui juuri ennen p��dyit ostamaan puoli kiloa karkkia :-)
            </td></tr>
    </table>


</div>
<%@include file="footer.jsp" %>
    