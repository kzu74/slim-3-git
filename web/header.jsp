<%@ page import="app.commons.util.MySession" %>
<%@ page import="net.slimmer.model.User" %>

<%
    User user = MySession.getUser(request);
    
    String loginText = "Kirjaudu sisään";
    String loginHref = "login.jtml?act=loginform";
    String fn = "";
    
    if(user != null) {
        loginText = "Kirjaudu ulos";
        loginHref = "login.jtml?act=logout";
        fn = user.getFirstName();
    }

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" type="image/x-icon" href="images/favicon.png" />        
        <link type="text/css" rel="stylesheet" href="css/style.css" />
        <link type="text/css" rel="stylesheet" href="css/main-menu.css" />
        <link type="text/css" rel="stylesheet" href="css/button.css" />
        <link type="text/css" rel="stylesheet" href="css/form.css" />
        <link type="text/css" rel="stylesheet" href="css/table.css" />
        <link type="text/css" rel="stylesheet" href="css/report.css" />
        <link type="text/css" rel="stylesheet" href="css/ex-hover-box.css" />
        
        <script type='text/javascript' src='js/funks.js'></script>
        <script type='text/javascript' src="js/jquery-1.10.2.js"></script>

        <script src="js/jquery-1.7.js" type="text/javascript"></script>
        <script src="js/jquery-ui.min.js" type="text/javascript"></script>
        <link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />

        <%--
        <script src="http://code.jquery.com/jquery-1.7.js" type="text/javascript"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
        <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
        <!--  http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/images/ui-bg_flat_75_ffffff_40x100.png -->
        
        <!-- http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/images/images/ui-bg_glass_75_dadada_1x400.png  -->
        --%>
        <STYLE TYPE="text/css" media="all"> 
            .ui-autocomplete {
                position: absolute;
                cursor: default;
                min-height: 25px;
                max-height: 200px;
                overflow-y: scroll;
                overflow-x: hidden;
            }
            
body .ui-autocomplete {
  /* font-family to all */
}

body .ui-autocomplete .ui-menu-item .ui-corner-all {
   /* all <a> */
}

body .ui-autocomplete .ui-menu-item .ui-state-focus {
   /* selected <a> */
   background: red;
}            
        </STYLE>
        

        

        <title>Digidieetti - ilmainen ruokapäiväkirja</title>
    </head>
    
    <body class="bg-bea">
        <%--
    <div id="background">
        <img src="bea1.jpeg" alt="" />
    </div>
        --%>
        <div align="center">
            <div class="wrapper">  
                <div class="header"></div>
                <div class="main-menu">
                    <ul>
                        <li class="li-left">&nbsp;</li>
                        <li><a href="index.jsp">Etusivu</a></li>
                        <li><a href="food.jtml?act=list">Elintarvikkeet</a>
                            <ul>
                                <li><a href="food.jtml?act=list">Elintarvikkeet</a></li>
                                <li><a href="food.jtml?act=edit">Tallenna uusi elintarvike</a></li>
                            </ul>
                        </li>
                        <li><a href="serving.jtml?act=list">Ruoka-annokset</a>
                            <ul>
                                <li><a href="serving.jtml?act=list">Näytä kaikki</a></li>
                                <li><a href="serving.jtml?act=listbydays">Näytä 10 päivän annokset</a></li>
                                <li><a href="serving.jtml?act=edit">Tallenna uusi annos</a></li>
                            </ul>
                        </li>
                        <li><a href="dining.jtml?act=daylist">Ruokailu</a>
                            <ul>
                                <%--<li><a href="dining.jtml?act=daylist">Näytä kaikki ruokailut</a></li>--%>
                                <li><a href="dining.jtml?act=weeklydaylist">Näytä ruokailut</a></li>
                                <li><a href="dining.jtml?act=daysummary">Näytä päivän ruokailut </a></li>
                                <%--<li><a href="dining.jtml?act=edit">Tallenna ruokailu</a></li>--%>
                                <li><a href="dining.jtml?act=form">Tallenna ruokailu</a></li>
                            </ul>
                        </li>
                        <%--
                        <li><a href="exercise.jtml?act=list">Liikunta</a>
                            <ul>
                                <li><a href="exercise.jtml?act=list">Näytä treenit</a></li>
                                <!--<li><a href="exercise.jtml?act=weeklydaylist">Näytä treenit viikoittain</a></li>-->
                                <li><a href="exercise.jtml?act=edit">Tallenna treeni</a></li>
                            </ul>
                        </li>
                        --%>
<%
                    if(user == null) {
%>                        
                        <li><a href="login.jtml?act=loginform">Kirjaudu</a>
                            <ul>
                                <li><a href="login.jtml?act=loginform">Kirjaudu sisään</a></li>
                                <li><a href="">Rekisteröidy käyttäjäksi</a></li>
                                <li><a href="login.jtml?act=login&email=kaitsu.email@gmail.com&pass=kaitsu">Kirjaudu Kaitsuna</a></li>
                                <li><a href="login.jtml?act=login&email=lissu.lappu@gmail.com&pass=lissu">Kirjaudu Lissuna</a></li>
                            </ul>
                        </li>
<%
                    }
                    else {
%>
                        <li><a href=""><%= fn %></a>
                            <ul>
                                <li><a href="">Omat tiedot</a></li>
                                <li><a href="login.jtml?act=logout">Kirjaudu ulos</a></li>
                            </ul>
                        </li>
                        <!--
                        <li><a href="">Taustakuva</a>
                            <ul>
                                <li><a href="">Näytä taustakuva</a></li>
                                <li><a class="bgx">Vaihda taustakuva</a></li>
                            </ul>
                        </li>
                            -->
<%
                       }
%>                        
                        
                    </ul>
                </div><!-- main-menu -->
                
<script>
    $(document).ready(function() {
                
        $('a.bgx').click(function(){
            $('html,body').addClass('bg-bea');
            var hello = $('html,body').hasClass('bg-bea') ;
            alert(hello);
            //if('html,body').is('.bg-bea') { alert('YES!'); }
            //$('html,body').css('background','url(images/bg.png) x-repeat');
        });    
        
    });
    
    
</script>                
<%--                
                <div class="crumbs">
                    <div class="float-left">
                        Ruokailu &rAarr; Näytä päivän ruokailut
                    </div>
                    <div class="float-right">
<%
                    User user = MySession.getUser(request);

                    if(user != null) {
%>                        
                        Kirjautuneena: <%= user.getFirstName() %>
<%
                    }
%>
                    </div>
                    
                </div>

--%>