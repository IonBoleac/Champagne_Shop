<%--
  Created by IntelliJ IDEA.
  User: ionut
  Date: 14/10/2021
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="false"%>
<%@ page import="model.mo.Utente" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    Utente utente = (Utente) request.getAttribute("utente");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Carrello";
    int prezzoTot = 0;
%>
<html>
<head>
    <%@include file="/include/htmlHead.inc"%>
    <style>
      table {
        border-collapse:collapse
      }
      td, th {
        border:1px solid #ddd;
        padding:8px;
      }
      .left{
        float: left;
      }
    </style>
</head>
<body>
    <%@include file="/include/header.inc"%>
    <main>
        <table>
            <caption>Prodotti nel carello da acquistare</caption>
            <thead>
                <tr>
                    <th>Nome prodotto</th>
                    <th>Quantita'</th>
                    <th>Prezzo</th>
                    <th>Eliminare</th>
                </tr>
            </thead>
            <%for(int i = 0; i < utente.getCarello().size(); i++){%>
                <tr>
                    <td><%=utente.getCarello().get(i).get("nomeProdotto")%></td>
                    <td><%=utente.getCarello().get(i).get("quantita")%></td>
                    <td><%=utente.getCarello().get(i).get("prezzo")%><%prezzoTot = prezzoTot + Integer.parseInt((String) utente.getCarello().get(i).get("prezzo"));%></td>
                    <td><a href="Dispatcher?controllerAction=CarrelloManagement.deleteProdottoDaCarrello&idProdotto=<%=utente.getCarello().get(i).get("idProdotto")%>&email=<%=utente.getCarello().get(i).get("email")%>"><img id="trashcan" src="images/trashcan.png" width="22" height="22"/></a></td>
                </tr>
            <%}%>
        </table>
        <table>
            <thead>
            <tr>
                <th>Totale ordine</th>
                <th></th>
            </tr>
            </thead>
            <tr>
                <td><%=prezzoTot%></td>
                <th><a href="Dispatcher?controllerAction=OrdineManagement.compilareOrdine">Acquista</a></th>
            </tr>
        </table>
    </main>
</body>
    <%@include file="/include/footer.inc"%>
</html>
