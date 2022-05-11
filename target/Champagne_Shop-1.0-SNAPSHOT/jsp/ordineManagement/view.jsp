<%--
  Created by IntelliJ IDEA.
  User: ionut
  Date: 15/10/2021
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<%@ page import="model.mo.Utente" %>
<%@ page import="model.mo.TestataOrdine" %>
<%@ page import="java.util.List" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    List<TestataOrdine> listTestataOrdine = (List<TestataOrdine>) request.getAttribute("listTestataOrdine");
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
            <caption>Lista ordini</caption>
            <thead>
                <tr>
                    <th>Data ordine</th>
                    <th>Stato spedizione</th>
                    <th>Annullato</th>
                    <th>Prodotti</th>
                </tr>
            </thead>
            <%for(int i = 0; i < listTestataOrdine.size(); i++) {%>
                <tr>
                    <td><%=listTestataOrdine.get(i).getDataOrdine()%></td>
                    <td><%=listTestataOrdine.get(i).getStatoSpedizione()%></td>
                    <td><%if(listTestataOrdine.get(i).isAnnullato()){%>
                        SI
                        <%}else{%>
                        NO
                        <%}%>
                    </td>
                    <td><a href="Dispatcher?controllerAction=OrdineManagement.listaProdotti&idTestataOrdine=<%=listTestataOrdine.get(i).getIdTestataOrdine()%>">Dettagli</a></td>
                </tr>
            <%}%>
        </table>
    </main>

    </body>
        <%@include file="/include/footer.inc"%>
</html>
