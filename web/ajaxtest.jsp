<%-- 
    Document   : ajaxtest
    Created on : 2.11.2013, 8:22:07
    Author     : Kaitsu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>jQuery Autocomplete</title>
        <script src="http://code.jquery.com/jquery-1.7.js" type="text/javascript"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
        <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
        <STYLE TYPE="text/css" media="all"> .ui-autocomplete {
                position: absolute;
                cursor: default;
                height: 200px;
                overflow-y: scroll;
                overflow-x: hidden;
            }
        </STYLE>

            <script type="text/javascript">
    
                $(document).ready(function() {
    
                    $("#auto").autocomplete({
                        source: function(request, response){
                            $.get("ajaxtest.jtml", {data:request.term}, function(data){     
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
                            $("#foodId").val(ui.item.value);

                            return false;
                        }
                    }); 
                });
            </script>
        </head>
        <body>
            <h1>jQuery Autocomplete Example</h1>
            <p>In this example we will have an input box that will provide
                suggestions using autocomplete( url or data, options ) and Ajax
                request.</p>
            <form name="as400samplecode" action="" method="get">
                <input type="hidden" name="foodId" id="foodId">
                <table>

                    <tr>
                        <td><input type="text" id="auto" maxlength="50" />
                    </td>
                    <tr>
                        <td><input type="submit" value="Lähetä">
                    </td>
                </tr>

            </table>

        </form>


    </body>
</html>
