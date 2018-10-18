<%@page import="java.util.List" %>

<%

    Object msgObj = request.getAttribute("messageList");
    List<String> mlist = null;
    if(msgObj != null) {
        mlist = (List<String>)msgObj;
    }

    if(mlist != null) {
  
        for(String msg : mlist) {
%>            
            <p class="message"><%= msg %></p>
<%            
        }
    }
    
%>                 

<%

    Object msgObjSes = session.getAttribute("messageList");
    System.out.println("messageList sesssiossa = " + msgObjSes);
    List<String> mlistSes = null;
    if(msgObjSes != null) {
        mlistSes = (List<String>)msgObjSes;
    }

    if(mlistSes != null) {
  
        for(String msg : mlistSes) {
%>            
        <!--    <p class="message"><%= msg %></p> -->
<%            
        }
    }
    
%>                 