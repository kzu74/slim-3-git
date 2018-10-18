<%@page import="app.commons.util.Converter"%>
<%@ page import="java.util.List" %>
<%@ page import="net.slimmer.model.Dining" %>
<%@ page import="app.commons.util.MyRequest" %>

<%
    List<Dining> diningList = MyRequest.getDiningList(request);
%>


<%@include file="header.jsp" %>

<div class="basic-content">

    <h1>Ruokailut</h1>
    
    <form method="get" action="DiningServlet">
        <%-- jäin tähän --%>
        <table>
            <!--
            <tr>
                <th width="230" align="left">Nimi</th> 
                <th></th>
            </tr>
            -->
<%
        String trBg = "";
        int i = 1;
        String dateTime = "";
    
        for(Dining dining : diningList) {
            
            dateTime = Converter.toDateTimeString(dining.getDiningTime());
            
            if(i % 2 == 0) { trBg = "trColoured"; } else { trBg = ""; }
%>            
            <tr class="<%= trBg %>">
                <td class="td-300px">
                    <a class="list-link" href="dining.jtml?act=summary&id=<%= dining.getId() %>"><%= dining.getName() %></a>
                </td> 
                <td class="td-80px">
                    <%= dining.getEnergy() %> kcal
                </td>
                <td class="td-100px">
                    <%= dateTime %>
                </td>
         
                <%--
                <td class="td-button">
                    <a href="dining.jtml?act=edit&id=<%= dining.getId() %>" class="button1">Muokkaa</a>&nbsp;
                    <a href="dining.jtml?act=delete&id=<%= dining.getId() %>" class="button1">Poista</a>
                
                </td>
                --%>
            </tr>
<%
            i++;
       }
%>             
        </table>
    </form>
        
    
</div>

<%@include file="footer.jsp" %>
