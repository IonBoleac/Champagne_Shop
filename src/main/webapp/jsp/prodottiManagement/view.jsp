<%--
  Created by IntelliJ IDEA.
  User: ionut
  Date: 04/10/2021
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@page session="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="model.mo.Utente"%>
<%@ page import="java.util.List" %>
<%@ page import="model.mo.Prodotto" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "GestioneProdotti";
    boolean flag = true;
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
    </style>
</head>
<body>
    <%@include file="/include/header.inc"%>
    <main>
        <table>
            <caption>
                Gestione Prodotti
            </caption>
            <thead>
                <tr>
                    <td>Nome prodotto</td>
                    <td>Prezzo unitario</td>
                    <td>Url Immagine</td>
                    <td>Quantita'</td>
                    <td>Descrizione</td>
                    <td>Vetrina</td>
                    <td>Bloccato</td>
                    <td>Annata</td>
                    <td>Modifica</td>
                    <td>Eliminare</td>
                </tr>
            </thead>
            <%for (int i = 0; i < prodotti.size(); i++) {%>
                <tr>
                    <td><%=prodotti.get(i).getNomeProdotto()%></td>
                    <td><%=prodotti.get(i).getPrezzoUnitario()%></td>
                    <td><%=prodotti.get(i).getUrlImage()%></td>
                    <td><%=prodotti.get(i).getQuantita()%></td>
                    <td>.....</td>
                    <td><%=prodotti.get(i).isVetrina()%></td>
                    <td>
                        <%if(prodotti.get(i).isBlocked()){%>
                        <form>
                            <input type="hidden" name="controllerAction" value="ProdottiManagement.blockUnblockProduct">
                            <input type="hidden" name="status" value="sbloccare">
                            <input type="hidden" name="idProdotto" value="<%=prodotti.get(i).getIdProdotto()%>">
                            <input type="submit" class="button" value="Sbloccare">
                        </form>
                        <%}else{%>
                        <form>
                            <input type="hidden" name="controllerAction" value="ProdottiManagement.blockUnblockProduct">
                            <input type="hidden" name="status" value="bloccare">
                            <input type="hidden" name="idProdotto" value="<%=prodotti.get(i).getIdProdotto()%>">
                            <input type="submit" class="button" value="Bloccare"/>
                        </form>
                        <%}%>
                    </td>
                    <td><%=prodotti.get(i).getAnnata()%></td>
                    <td><a href="Dispatcher?controllerAction=ProdottiManagement.prodotto&idProduct=<%=prodotti.get(i).getIdProdotto()%>">Modifica prodotto</a></td>
                    <td><a href="Dispatcher?controllerAction=ProdottiManagement.deleteProdotto&idProduct=<%=prodotti.get(i).getIdProdotto()%>"><img id="trashcan" src="images/trashcan.png" width="22" height="22"/></a></td>
                </tr>
            <%}%>
        </table>
        <br><br><br>
        <a href="Dispatcher?controllerAction=ProdottiManagement.prodotto&idProduct=0">Inserimento nuovo prodotto</a>
    </main>
</body>
<%@include file="/include/footer.inc"%>
</html>
