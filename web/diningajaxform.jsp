<%@page import="app.commons.util.Converter"%>
<%@ page import="app.commons.util.MyCalendar"%>
<%@ page import="java.util.List" %>
<%@ page import="app.commons.util.MyRequest"%>
<%@ page import="app.commons.jsp.HelperJSP "%>
<%@ page import="net.slimmer.model.Dining" %>
<%@ page import="net.slimmer.model.Food" %>
<%@ page import="net.slimmer.model.Serving" %>



<%

    Dining dining = MyRequest.getDining(request);
    String date = "";
    String time = "";
    
    if(dining == null) { // case insert
        dining = new Dining();
        date = MyRequest.getDayString(request); // case add dining from not today page
        if(date == null) {
            date = MyCalendar.getTodayDate();
        }
        time = MyCalendar.getNowTime();
    }
    else {
        date = Converter.toDateString(dining.getDiningTime());
        time = Converter.toTimeString(dining.getDiningTime());
    }
    
    List<Food> diningFoodList = dining.getFoodList();
    
    String h1 = "Tallenna ruokailu";
    
    if(dining.getId() > 0) {
        h1 = "Muuta ruokailun tietoja"; // + dining.getName();
    }
%>

<%@include file="header.jsp" %>

<script>
    $(document).ready(function() {

        $(this).find('td input:radio').prop('checked', false);
    
<% 
        if(dining.getId() == 0) { 
%>
        $("#diningName").focus(); // cursor to text field name when creating new dining
<%      
        }
        else {
%>
        $("#strId").focus(); // ???
        scrollToH1();
<%        
      }
%>
            
        $.ui.autocomplete.prototype._renderItem = function (ul, item) {
            //item.label = item.label.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $.ui.autocomplete.escapeRegex(this.term) + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<strong>$1</strong>");
            var tmp = item.label;
            tmp = tmp.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $.ui.autocomplete.escapeRegex(this.term) + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<strong>$1</strong>");
            
            return $("<li></li>")
                    .data("item.autocomplete", item)
                    .append("<a>" + tmp + "</a>")
                    .appendTo(ul);
        };        
        

        $("#servingGramsName").autocomplete({
            source: function(request, response){
                $.post("ajax.jtml?act=serving", {data:request.term}, function(data){     
                    response($.map(data, function(item) {
                        return {
                            label: item.name,
                            value: item.id 
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

                this.value = ui.item.label;
                document.getElementById("servingGramsId").value = ui.item.value;
                //alert("ui.item.value == " + ui.item.value)
                //$("#servingGramsId").val(ui.item.value);
                //alert("#servingGramsId val() == " + $("#servingGramsId").val());
                //console.log("ui.item.value == " + ui.item.value)
                
                //console.log("#servingGramsId val() == " + $("#servingGramsId").val());
                return false;
            }
        }); 

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

                this.value = ui.item.label;
                $("#foodId").val(ui.item.value);

                return false;
            }
            
        }); 


        $('#strId input:text').focus(function () {
            $("#strId").find('td input:radio').prop('checked', true);
            $('.form-table tr.colorTR').removeClass("trActive");
            $("#strId").addClass("trActive");
        });    
        
        $('#ftrId input:text').focus(function () {
            $("#ftrId").find('td input:radio').prop('checked', true);
            $('.form-table tr.colorTR').removeClass("trActive");
            $("#ftrId").addClass("trActive");
        });    
        
        $('.form-table tr.colorTR').click(function () {
            $(this).find('td input:radio').prop('checked', true);
            $('.form-table tr.colorTR').removeClass("trActive");
            $(this).addClass("trActive");
        });    
        
        $('.form-table tr.colorTR').mouseover(function () {
            $(this).addClass("trHover");
        });

        $('.form-table tr.colorTR').mouseout(function () {
            $(this).removeClass("trHover");
        });        
    });
</script>
<script>
function cRedir(relativeURL, message) {
    var result = confirm(message);
    if (result) {
        window.location.href = relativeURL;
    }
}

</script>


<div class="basic-content" id="basic">

    <h1 id="h1"><%= h1 %></h1>
    
    <%@include file="messages.jsp" %>
        
    <form method="post" action="dining.jtml" >
        
        <input type="hidden" name="id" value="<%= dining.getId() %>">
        <input type="hidden" name="act" value="save">
        <input type="hidden" name="servingGramsId" id="servingGramsId">
        <input type="hidden" name="foodId" id="foodId">
        <input type="hidden" name="buttonAct" id="buttonAct">
        
        <table class="form-table">
            <tr>
                <td class="td-100px">*Nimi:</td> 
                <td class="td-400px">
                    <input type="text" name="name" class="input-text-300px" value="<%= dining.getName() %>" id="diningName">
                </td>
            </tr>
            <!--
            <tr class="colorTR">
                <td>
                    <input type="radio" name="foodOption" value="serving" id="servingRadio" checked>
                    <label for="wServingRadio" class="radioLabel">Punnittu annos:</label>
                </td>
                <td>
                    <input type="text" name="servingName" size="50" id="servingName">
                </td>
            </tr>
            -->
            <tr class="colorTR" id="strId">
                <td>
                    <input type="radio" name="foodOption" value="servingGrams" id="servingRadioGrams">
                    <label for="servingRadioGrams" class="radioLabel">Annos (g):</label>
                </td>
                <td>
                    <input type="text" name="servingGramsName" size="50" id="servingGramsName">
                    &nbsp;
                    Grammat: <input type="text" name="servingGrams" 
                                           class="input-text-40px" value="0">
                </td>
            </tr>
            
            <tr class="colorTR" id="ftrId">
                <td>
                    <input type="radio" name="foodOption" value="food" id="foodRadio">
                    <label for="foodRadio" class="radioLabel">Elintarvike</label>
                    </td> 
                    <td colspan="2">
                    <input type="text" name="foodName" size="50" id="foodName">
                    &nbsp;
                    Grammat: <input type="text" name="foodGrams" 
                                           class="input-text-40px" value="0">
                    
            </tr>
            
            <tr>
                <td>Pvm (pp.kk.vvvv):</td> 
                <td>
                    <input type="text" name="diningDate" class="input-text-80px" value="<%= date %>">&nbsp;
                    Klo (tt:mm):
                    <input type="text" name="diningTime" class="input-text-40px" value="<%= time %>">&nbsp;
                
                </td>
            </tr>
            
            <tr>
                <td colspan="6">
                    <%--<a class="form-button" href="javascript:sendFormAButton('saveAndAdd')">Lis‰‰ ja tallenna</a>--%>
    <input type="submit" name="saveAndAdd" value="Lis‰‰ ja tallenna" class="large-button" id="submitButton">
                
                </td> 
            </tr>
<%
        if(dining.getId() > 0) {
%>                    
            <tr>
                <td colspan="3">
        <table class="innerTable">
            <tr>
                <td class="td-300px"><b>Elintarvike</b></td> 
                <td class="td-70px"><b>Paino(g)</b></td>
                <td class="td-70px"><b>&euro;/kg</b></td>
                <td class="td-70px"><b>Hinta</b></td>
                <td class="td-80px"><b>Kcal</b></td>
                <td class="td-70px"><b>Prot.</b></td>
                <td class="td-70px"><b>Hiil.</b></td>
                <td class="td-50px"><b>Rasva</b></td>
                <td class="td-100px"></td>
            </tr>                        
<%
                int dFoodIndex = 0;
                for(Food dFood : diningFoodList) {
                    dFoodIndex++;
%>                    
            <tr>
                <td><a href="food.jtml?act=summary&id=<%= dFood.getId() %>" class="list-link"><%= dFoodIndex %>. <%= dFood.getName() %></a></td>
                <td><input type="text" class="input-text-60px" name="foodGramsId<%= dFood.getId() %>" value="<%= dFood.getGramsString() %>"></td>
                <td><input type="text" class="input-text-60px" 
                           name="foodUnitPriceId<%= dFood.getId() %>" value="<%= dFood.getUnitPriceString() %>"></td>
                <td><%= dFood.getServingPriceString() %>&euro;</td>
                <td><%= dFood.getEnergyByGrams() %> kcal</td>
                <td><%= dFood.getProteinByGramsString() %>g </td>                
                <td><%= dFood.getCarbsByGramsString() %>g </td>                
                <td><%= dFood.getFatByGramsString() %>g </td>                
                <td>
                    <a class="button-50px" 
                       href="dining.jtml?act=deletefood&id=<%= dining.getId() %>&foodId=<%= dFood.getId() %>">Poista</a>
                </td>
                
            </tr>
<%
               } // diningFoodList
%>            
            <tr><td colspan="9"><hr></td></tr>
            <tr>
                <td>Yhteens‰:</td> 
                <td><%= dining.getFoodGramsTotalString() %>g</td> 
                <td></td>
                <td><%= dining.getPriceString() %>&euro;</td> 
                <td><%= dining.getEnergy() %> kcal</td>
                <td><%= dining.getProteinString() %>g</td>
                <td><%= dining.getCarbsString() %>g</td>
                <td colspan="2"><%= dining.getFatString() %>g</td>
            </tr>
            <tr><td colspan="9">
                    
                    <input type="submit" name="saveChanges" value="Tallenna muutokset" class="large-button" id="submitButton">
                    
                    <input type="button" name="list" value="N‰yt‰ p‰iv‰n ruokailut" class="large-button"
                           onclick="redir('dining.jtml?act=daysummary')">
                    
                    <input type="button" value="Lis‰‰ ruokailu" class="large-button"
                           onclick="redir('dining.jtml?act=form')">

                    <input type="button" value="Poista ruokailu" class="large-button"
                           onclick="cRedir('dining.jtml?act=delete&id=<%= dining.getId() %>', 'Poistetaanko ruokailu?')">
                    
                </td></tr>
            
        </table>
                </td>
            </tr>
<%
       } // if(dining.getId() > 0)
%>            
        </table>
    </form>
        
    
</div>

<%@include file="footer.jsp" %>
