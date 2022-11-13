<%--
  Created by IntelliJ IDEA.
  User: ionut
  Date: 20/10/2021
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<%@ page import="model.mo.Utente" %>
<%@ page import="java.util.List" %>
<%@ page import="model.mo.RigaOrdine" %>
<%@ page import="model.mo.TestataOrdine" %>
<%@ page import="model.mo.Prodotto" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    List<RigaOrdine> rigaOrdineArrayList = (List<RigaOrdine>) request.getAttribute("rigaOrdineArrayList");
    TestataOrdine testataOrdine = (TestataOrdine) request.getAttribute("testataOrdine");
    List<Prodotto> prodottoList = (List<Prodotto>) request.getAttribute("prodottoArrayList");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Miei_Ordini";
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
            <caption>Ordine effettuato il <%=testataOrdine.getDataOrdine()%> ed e' <%=testataOrdine.getStatoSpedizione()%></caption>
            <thead>
                <tr>
                    <th>Nome prodotto</th>
                    <th>Quantita acquistata</th>
                    <th>Prezzo pagato</th>
                </tr>
            </thead>
            <%for(int i = 0; i < rigaOrdineArrayList.size(); i++) {%>
                <tr>
                    <td><%=rigaOrdineArrayList.get(i).getProdotto().getNomeProdotto()%></td>
                    <td><%=rigaOrdineArrayList.get(i).getQuantita()%></td>
                    <td><%=rigaOrdineArrayList.get(i).getPrezzo()%></td>
                </tr>
            <%}%>
        </table>
    </main>

</body>
<%@include file="/include/footer.inc"%>
</html>
