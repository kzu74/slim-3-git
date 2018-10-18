<%@include file="header.jsp" %>

<div class="basic-content">
    <!-- en tajjuu... -->

    <h1>Tallenna ruokailu</h1>
    <form>
        <table class="box">
            <tr>
                <td>Valitse tuttu ruoka-annos:
                    <select name="some">
                        <option value="Valitse">Valitse</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Tai syötä ruoka-aineet yksitellen:</td>
            </tr>
            <% for (int i = 0; i < 10; i++) {%>
            <tr>
                <td class="td-150px">Ruoka-aine <%= i + 1%>:
                    <select name="dining<%= i + 1%>">
                        <option value="Valitse">Valitse</option>
                    </select>
                    &nbsp;&nbsp;Määrä (g):&nbsp;
                    <input type="text" name="dining<%= i + 1%>gr" class="round-text-box-short">
                </td>
            </tr>
            <% }%>
            <tr>
                <td>Ruokailun ajankohta: (pp.kk.vvvv) 
                    <input type="text" name="date" class="input-date">&nbsp;klo (tt:mm)&nbsp;
                    <input type="text" name="time" class="round-text-box-50px">

                </td>
            </tr>
            <tr>
                <td><input type="submit" class="large-button" value="Tallenna" name="save"></td>
            </tr>
        </table>
    </form>

</div>

<%@include file="footer.jsp" %>
