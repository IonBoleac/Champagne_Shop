<%--
  Created by IntelliJ IDEA.
  User: ionut
  Date: 15/10/2021
  Time: 12:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<%@ page import="model.mo.Utente" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Carrello";
%>
<html>
    <head>
        <%@include file="/include/htmlHead.inc"%>
        <style>

          .field {
            margin: 5px 0;
          }

          label {
            float: left;
            width: 56px;
            font-size: 0.8em;
            margin-right: 10px;
            padding-top: 3px;
            text-align: left;
          }

          input[type="text"], input[type="tel"], input[type="email"], input[type="password"], input[type="date"] {
            border: none;
            border-radius: 4px;
            padding: 3px;
            background-color: #e8eeef;
            color:#8a97a0;
            box-shadow: 0 1px 0 rgba(0,0,0,0.03) inset;
          }

          input[type="text"]:focus, input[type="tel"]:focus, input[type="email"]:focus, input[type="password"]:focus, input[type="date"]:focus {
            background: #d2d9dd;
            outline-color: #a3271f;
          }

        </style>
    </head>
    <body>
        <%@include file="/include/header.inc"%>
        <main>
            <form id="formOrdine" name="formOrdine" method="post" action="Dispatcher">
                <div class="field clearfix">
                    <label for="nome">Nome</label>
                    <input type="text" id="nome" name="nome" placeholder="Pluto">
                </div>
                <div class="field clearfix">
                    <label for="cognome">Cognome</label>
                    <input type="text" id="cognome" name="cognome" placeholder="Rossi">
                </div>
                <div class="field clearfix">
                    <label for="indirizzoSpedizione">Indirizzo</label>
                    <input type="text" id="indirizzoSpedizione" name="indirizzoSpedizione" placeholder="Via Don Pippo, 21">
                </div>
                <div class="field clearfix">
                    <label for="cap">Codice postale</label>
                    <input type="text" id="cap" name="cap" placeholder="12345" maxlength="5">
                </div>
                <div class="field clearfix">
                    <label for="citta">Citta'</label>
                    <input type="text" id="citta" name="citta" placeholder="Ferrara">
                </div>
                <div class="field clearfix">
                    <label for="provincia">Provincia</label>
                    <input type="text" id="provincia" name="provincia" placeholder="Narnia">
                </div>
                <div class="field clearfix">
                    <label for="regione">Regione</label>
                    <input type="text" id="regione" name="regione" placeholder="Emilia Romagna">
                </div>
                <input type="hidden" name="controllerAction" value="OrdineManagement.effettuaOrdine">
                <input type="submit" class="button" value="Effettua ordine">
            </form>
        </main>

    </body>
        <%@include file="/include/footer.inc"%>



</html>
