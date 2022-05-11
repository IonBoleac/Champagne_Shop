<%--
  Created by IntelliJ IDEA.
  User: ionut
  Date: 09/10/2021
  Time: 00:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.mo.Utente" %>
<%@ page session="false" %>
<%@ page import="model.mo.Prodotto" %>
<%@ page import="java.util.List" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Catalogo";
%>
<html>
<head>
    <%@include file="/include/htmlHead.inc"%>
    <style>
        body{
          text-align: center;
        }
    </style>
</head>
<body>
    <%@include file="/include/header.inc"%>
    <main>
        <img src="images/<%=prodotto.getUrlImage()%>"><br>
        <div class="clearfix"><%=prodotto.getDescrizione()%></div>
        <%if(loggedOn){%>
            <form name="messaNelCarello" action="Dispatcher" method="post">
                <div class="field clearfix">
                    <label for="quantita">Quantita' da acquistare: </label>
                    <input type="number" id="quantita" name="quantita" min="1" max="<%=prodotto.getQuantita()%>">
                </div>
                <input type="hidden" name="controllerAction" value="CarrelloManagement.addProdottoNelCarrelloMethod"/>
                <input type="hidden" name="idProdotto" value="<%=prodotto.getIdProdotto()%>">
                <input type="hidden" name="email" value="<%=loggedUser.getEmail()%>">
                <input type="submit" class="button" value="Aggiungi al carrello">
            </form>
        <%}%>
    </main>
</body>
    <%@include file="/include/footer.inc"%>
</html>
