<%@ page import="java.util.List" %>
<%@ page import="net.slimmer.model.Food" %>
<%@ page import="app.commons.util.MyRequest" %>
<%@ page import="app.commons.util.Trace" %>
<%@ page import="app.commons.util.Param" %>

<%
    List<Food> foodList = MyRequest.getFoodList(request);
    String showall = Param.getString(request, "showall", "all");
    Trace t = new Trace();
    t.out("showall = " + showall);
       
%>

<%@include file="header.jsp" %>

<script>
    $(document).ready(function() {
        

        $('body').scrollTop(400);        
        
        
        $("#<%= showall %>").prop('checked', true);
        
        $.ui.autocomplete.prototype._renderItem = function (ul, item) {
            var tmp = item.label;
            var theLink = "food.jtml?act=summary?id=" + item.value;
            tmp = tmp.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $.ui.autocomplete.escapeRegex(this.term) + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<strong>$1</strong>");

            return $("<li></li>")
                    .data("item.autocomplete", item)
                    .append("<a href='" + theLink + "'>" + tmp + "</a>")
                    .appendTo(ul);
        };        

        $("#foodName").autocomplete({

            source: function(request, response){
                $.post("ajax.jtml?act=food", {data:request.term}, function(data){     
                    response($.map(data, function(item) {
                        return {
                            label: item.name,
                            value: item.id // not needed?
                        }
                    })
                )
                }, "json");
            },
            minLength: 1,
            dataType: "json",
            cache: false,
            focus: function(event, ui) {
                return false;
            },
            select: function(event, ui) {
  
                window.location.href = "food.jtml?act=summary&id=" + ui.item.value;

<%-- koitetaan muuttaa niin, että klikkaa suoraan linkkiä food summaryyn 9.3.2016
                this.value = ui.item.label;
                $("#foodId").val(ui.item.value);
--%>

                return false;
            }
        }); 
    });
    
    function sendShow(show) {
        var action = "food.jtml?act=list&showall=" + show;
        $("#showallform").attr("action", action);
        var a = $("#showallform").attr("action");
        //alert("a == " + a);
        $("#showallform").submit();
    }
    
    
    
    
    
    
</script>


<div class="basic-content">

    <h1 id="hh1">Elintarvikkeet</h1>
    
    <form method="post" action="kkk" id="showallform">
        <input type="hidden" name="foodId" id="foodId">
        <table class="show-all-table">
            <tr>
                <td>
                    <input type="radio" name="showall" value="all" id="all" onclick="sendShow('all')">
                    <label for="showall" class="radioLabel">Näytä kaikki</label>
                    
                    <input type="radio" name="showall" value="own" id="own" onclick="sendShow('own')">
                    <label for="own" class="radioLabel">Näytä omat</label>

                     <input type="radio" name="showall" value="others" id="others" onclick="sendShow('others')">
                    <label for="others" class="radioLabel">Näytä muiden</label>
                </td>
                <td>
                    Hae: <input type="text" name="foodName" id="foodName" class="input-text-300px">
                    
                </td>
            </tr>
        </table>
        <table class="list-table-white" cellspacing="0" cellpadding="0">
            <!--
            <tr>
                <th width="230" align="left">Nimi</th> 
                <th></th>
            </tr>
            -->
<%
        String trBg = "";
        int i = 2;
    
        for(Food food : foodList) {
            
            if(i % 2 == 0) { trBg = "trColoured"; } else { trBg = ""; }
%>            
            <!--<tr class="bg-hover">-->
            <%--<a href="food.jtml?act=summary&id=<%= food.getId() %>" class="tr-link">--%>
<tr onclick="document.location = 'food.jtml?act=summary&id=<%= food.getId() %>';" title="<%= food.getName() %>">
                <!--<td class="td-name">-->
                <td>
                    <a class="list-link" href="food.jtml?act=summary&id=<%= food.getId() %>"><%= food.getName() %></a>
                </td>
                <td>
                    <a href="food.jtml?act=delete&id=<%= food.getId() %>" class="button1">Poista</a>
                    <a href="food.jtml?act=edit&id=<%= food.getId() %>" class="button1">Muokkaa</a>
                </td> 
            </tr>
            <%--</a>--%>
<%
            i++;
       }
%>             
        </table>
    </form>
        
    
</div>

<%@include file="footer.jsp" %>
