<%@page import="app.commons.util.Converter"%>
<%@ page import="app.commons.util.MyCalendar"%>
<%@ page import="java.util.List" %>
<%@ page import="app.commons.util.MyRequest"%>
<%@ page import="app.commons.jsp.HelperJSP "%>
<%@ page import="net.slimmer.model.Food" %>
<%@ page import="net.slimmer.model.Serving" %>



<%

    Serving serving = MyRequest.getServing(request);

    if (serving == null) { // case insert
        serving = new Serving();
    }

    List<Food> servingFoodList = serving.getFoodList();

    String h1 = "Tallenna annos";

    if (serving.getId() > 0) {
        h1 = "Muuta annoksen tietoja"; // + serving.getName();
    }

%>

<%@include file="header.jsp" %>

<script>
    $(document).ready(function() {
        
        
        $(this).find('td input:radio').prop('checked', false);
    
<%
    if (serving.getId() == 0) {
%>
        $("#fokus").focus();
<%  
    } 
    else {
%>
        $("#strId").focus();
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
    });
</script>


<div class="basic-content">

    <h1><%= h1%></h1>

    <%@include file="messages.jsp" %>

    <form method="post" action="serving.jtml" >

        <input type="hidden" name="id" value="<%= serving.getId()%>">
        <input type="hidden" name="act" value="save">
        <input type="hidden" name="foodId" id="foodId">


        <%-- jäin tähän --%>
        <table class="form-table">
            <tr>
                <td class="td-60px">*Nimi:
                </td>
                <td>
                    <input type="text" name="name" class="input-text-300px" value="<%= serving.getName()%>" id="fokus">
                </td>
            </tr>
            <tr class="colorTR" id="ftrId">
                <td>
                    Elintarvike:
                </td>
                <td>
                    <input type="text" name="foodName" class="input-text-300px" id="foodName">
                    &nbsp;
                    Grammat: <input type="text" name="foodGrams" 
                                    class="input-text-40px" value="0">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" name="saveAndAdd" value="Lisää ja tallenna" class="large-button" id="submitButton">
                </td> 
            </tr>
            <%
                if (serving.getId() > 0) {
            %>                    
            <tr>
                <td colspan="2">
                    <table class="innerTable">
                        <tr>
                            <td class="td-300px"><b>Elintarvike</b></td> 
                            <td class="td-70px"><b>Paino</b></td>
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
                            for (Food dFood : servingFoodList) {
                                dFoodIndex++;
                        %>                    
                        <tr>
                            <td><a href="food.jtml?act=summary&id=<%= dFood.getId()%>" class="list-link"><%= dFoodIndex%>. <%= dFood.getName()%></a></td>
                            <td><input type="text" class="input-text-four-letters" name="foodGramsId<%= dFood.getId()%>" value="<%= dFood.getGramsString()%>">g</td>
                            <td><input type="text" class="input-text-four-letters" 
                                       name="foodUnitPriceId<%= dFood.getId()%>" value="<%= dFood.getUnitPriceString()%>"></td>
                            <td><%= dFood.getServingPriceString()%>&euro;</td>
                            <td><%= dFood.getEnergyByGrams()%> kcal</td>
                            <td><%= dFood.getProteinByGramsString()%>g </td>                
                            <td><%= dFood.getCarbsByGramsString()%>g </td>                
                            <td><%= dFood.getFatByGramsString()%>g </td>                
                            <td>
                                <a class="button-50px" 
                                   href="serving.jtml?act=deletefood&id=<%= serving.getId()%>&foodId=<%= dFood.getId()%>">Poista</a>
                            </td>

                        </tr>
                        <%
                            } // servingFoodList
                        %>            
                        <tr><td colspan="9"><hr></td></tr>
                        <tr>
                            <td>Yhteensä:</td> 
                            <td><%= serving.getFoodGramsTotalString() %>g</td> 
                            <td></td>
                            <td><%= serving.getPriceString()%>&euro;</td> 
                            <td><%= serving.getEnergy()%> kcal</td>
                            <td><%= serving.getProteinString()%>g</td>
                            <td><%= serving.getCarbsString()%>g</td>
                            <td colspan="2"><%= serving.getFatString()%>g</td>
                        </tr>
                        <tr><td colspan="9">

                                <input type="submit" name="saveChanges" value="Tallenna muutokset" class="large-button" id="submitButton">
                                <input type="button" name="list" value="Näytä yhteenveto" class="large-button"
                                       onclick="redir('serving.jtml?act=summary&id=<%= serving.getId()%>')">


                            </td></tr>

                    </table>
                </td>
            </tr>
            <%
                } // if(serving.getId() > 0)
            %>            
        </table>
    </form>


</div>

<%@include file="footer.jsp" %>
