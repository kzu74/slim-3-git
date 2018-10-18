<%@ page import="java.util.List" %>
<%@ page import="net.slimmer.model.Serving" %>
<%@ page import="app.commons.util.MyRequest" %>

<%
    List<Serving> servingList = MyRequest.getServingList(request);
%>


<%@include file="header.jsp" %>

<div class="basic-content">

    <h1>Annokset</h1>
    
    <form method="get" action="ServingServlet">
        <%-- jäin tähän --%>
        <table class="summary-table" cellspacing="0" cellpadding="0">
            <!--
            <tr>
                <th width="230" align="left">Nimi</th> 
                <th></th>
            </tr>
            -->
<%
        String trBg = "";
        int i = 1;
    
        for(Serving serving : servingList) {
            
            if(i % 2 == 0) { trBg = "trColoured"; } else { trBg = ""; }
%>            
        <tr>                
            <td class="tooltip-td">
                <a class="tooltip" href="serving.jtml?act=summary&id=<%= serving.getId() %>"><%= serving.getName() %></a>
                <span>
                    <img src="css/images/popup-arrow.gif" class="callout">
                    <a class="button-edit" href="serving.jtml?act=summary&id=<%= serving.getId()%>">Näytä</a>
                    <a class="button-edit" href="serving.jtml?act=edit&id=<%= serving.getId()%>">Muokkaa</a>
                    <a class="button-edit" href="serving.jtml?act=copy&id=<%= serving.getId()%>">Kopioi</a>
                    <a class="button-edit" href="serving.jtml?act=delete&id=<%= serving.getId()%>">Poista</a>

                </span>
                
                </td> 
                
            </tr>
<%
            i++;
       }
%>             
        </table>
    </form>
        
    
</div>

<%@include file="footer.jsp" %>
