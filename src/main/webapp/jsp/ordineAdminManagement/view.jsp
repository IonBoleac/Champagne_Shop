<%--
  Created by IntelliJ IDEA.
  User: ionut
  Date: 20/10/2021
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<%@ page import="model.mo.Utente" %>
<%@ page import="java.util.List" %>
<%@ page import="model.mo.TestataOrdine" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    List<TestataOrdine> testataOrdineList = (List<TestataOrdine>) request.getAttribute("testataOrdineList");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "GestioneOrdini";
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
            <caption>Lista ordini di tutti gli utenti</caption>
            <thead>
                <tr>
                    <th>Email</th>
                    <th>Cognome, Nome</th>
                    <th>Indirizzo</th>
                    <th>Codice Postale</th>
                    <th>Citta'</th>
                    <th>Provincia</th>
                    <th>Regione</th>
                    <th>Data ordine</th>
                    <th>Stato spedizione</th>
                    <th>Annullato</th>
                </tr>
            </thead>
            <%for(int i = 0; i < testataOrdineList.size(); i++) {%>
                <tr>
                    <td><%=testataOrdineList.get(i).getUtente().getEmail()%></td>
                    <td><%=testataOrdineList.get(i).getCognome()%>, <%=testataOrdineList.get(i).getNome()%></td>
                    <td><%=testataOrdineList.get(i).getIndirizzoSpedizione()%></td>
                    <td><%=testataOrdineList.get(i).getCodicePostale()%></td>
                    <td><%=testataOrdineList.get(i).getCitta()%></td>
                    <td><%=testataOrdineList.get(i).getProvincia()%></td>
                    <td><%=testataOrdineList.get(i).getRegione()%></td>
                    <td><%=testataOrdineList.get(i).getDataOrdine()%></td>
                    <td>
                        <%if(!testataOrdineList.get(i).getStatoSpedizione().equals("e stato consegnato")){%>
                            <%if (!testataOrdineList.get(i).isAnnullato()){%>
                                <%=testataOrdineList.get(i).getStatoSpedizione()%><br>
                                <a href="Dispatcher?controllerAction=OrdineAdminManagement.viewSetStatoSpedizione&idTestataOrdine=<%=testataOrdineList.get(i).getIdTestataOrdine()%>">Step successivo</a>
                            <%}else{%>
                                Ordine annullato!!!
                            <%}%>
                        <%}else{%>
                            Ordine consegnato!!!
                        <%}%>
                    </td>
                    <td>
                        <%if(!testataOrdineList.get(i).isConsegnato()){%>
                            <%if(testataOrdineList.get(i).isAnnullato()){%>
                                <a href="Dispatcher?controllerAction=OrdineAdminManagement.blockUnblockTestataOrdine&state=unblock&idTestataOrdine=<%=testataOrdineList.get(i).getIdTestataOrdine()%>">Sbloccare</a>
                            <%}else{%>
                                <a href="Dispatcher?controllerAction=OrdineAdminManagement.blockUnblockTestataOrdine&state=block&idTestataOrdine=<%=testataOrdineList.get(i).getIdTestataOrdine()%>">Bloccare</a>
                            <%}%>
                        <%}else{%>
                            Non modificabile!!!
                        <%}%>
                    </td>
                </tr>
            <%}%>
        </table>
    </main>

</body>
<%@include file="/include/footer.inc"%>

</html>
