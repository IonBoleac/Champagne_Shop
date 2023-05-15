<%@ page import="model.mo.Utente" %>
<%@ page import="model.mo.Prodotto" %><%--
  Created by IntelliJ IDEA.
  User: ionut
  Date: 07/10/2021
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@page session="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = (prodotto == null) ? "Creazione prodotto" : "Modifica prodotto";
    boolean flag = menuActiveLink.equals("Creazione prodotto");
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
        <%if(flag){%>Creazione prodotto
        <form method="post" action="Dispatcher" id="insProduct" name="insProduct">
            <div class="field clearfix">
                <label for="nomeProdotto">Nome prodotto: </label>
                <input type="text" id="nomeProdotto" name="nomeProdotto" size="20" maxlength="50" required>
            </div>
            <div class="field clearfix">
                <label for="prezzoUnitario">Prezzo unitario: </label>
                <input type="text" id="prezzoUnitario" name="prezzoUnitario" size="20" maxlength="50" required>
            </div>
            <!--div class="field clearfix">
                <label for="urlImg">Path Immagine: </label>
                <input type="text" id="urlImg" name="urlImg" required>
            </div-->
            <div class="field clearfix">
                <label for="quantita">Quantita' nel magazzino: </label>
                <input type="text" id="quantita" name="quantita" size="20" maxlength="50" required>
            </div>
            <div class="field clearfix">
                <label for="annata">Annata: </label>
                <input type="text" id="annata" name="annata" required>
            </div>
            <div class="field clearfix">
                <label>Descrizione: </label>
                <textarea rows="5" cols="50" name="descrizione" form="insProduct"></textarea>
            </div>
            <div>
                <label>Vetrina: </label>
                <input type="radio" name="vetrina" value="N">SI
                <input type="radio" name="vetrina" value="Y">NO
            </div>
            <input type="hidden" name="controllerAction" value="ProdottiManagement.insNewModProduct">
            <input type="hidden" id="urlImg" name="urlImg" value="noData">
            <input type="hidden" name="idProdotto" value="0">
            <input type="submit" class="button" value="Invia"/>
        </form>

        <%}else{%>Modifica prodotto
        <form id="insProduct" name="insProduct" action="Dispatcher" method="post">
            <div class="field clearfix">
                <label for="nomeProdotto">Nome prodotto: </label>
                <input type="text" id="nomeProdotto" name="nomeProdotto" value="<%=prodotto.getNomeProdotto()%>" required>
            </div>
            <div class="field clearfix">
                <label for="prezzoUnitario">Prezzo unitario: </label>
                <input type="text" id="prezzoUnitario" name="prezzoUnitario" value="<%=prodotto.getPrezzoUnitario()%>" required>
            </div>
            <!--div class="field clearfix">
                <label for="urlImg">Path Immagine: </label>
                <input type="text" id="urlImg" name="urlImg" value="<%=prodotto.getUrlImage()%>" required>
            </div-->
            <div class="field clearfix">
                <label for="quantita">Quantita' nel magazzino: </label>
                <input type="text" id="quantita" name="quantita" value="<%=prodotto.getQuantita()%>" required>
            </div>
            <div class="field clearfix">
                <label for="annata">Annata: </label>
                <input type="text" id="annata" name="annata" value="<%=prodotto.getAnnata()%>" required>
            </div>
            <div class="field clearfix">
                <label>Descrizione: </label>
                <textarea rows="5" cols="50" name="descrizione" form="insProduct"><%=prodotto.getDescrizione()%></textarea>
            </div>
            <div>
                <label>Vetrina: </label>
                <%if(prodotto.isVetrina()){%>
                    <input type="radio" name="vetrina" checked value="Y">SI
                    <input type="radio" name="vetrina" value="N">NO
                <%}else{%>
                    <input type="radio" name="vetrina" value="Y">SI
                    <input type="radio" name="vetrina" checked value="N">NO
                <%}%>
            </div>
            <input type="hidden" name="controllerAction" value="ProdottiManagement.insNewModProduct">
            <input type="hidden" name="idProdotto" value="<%=prodotto.getIdProdotto()%>">
            <input type="hidden" id="urlImg" name="urlImg" value="<%=prodotto.getUrlImage()%>">
            <input type="submit" class="button" value="Invia"/>
        </form>
        <%}%>
    </main>

</body>
    <%@include file="/include/footer.inc"%>
</html>
