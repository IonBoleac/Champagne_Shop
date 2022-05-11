<%--
  Created by IntelliJ IDEA.
  User: ionut
  Date: 04/10/2021
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@page session="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="model.mo.Utente"%>
<%@ page import="java.util.List" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    List<Utente> utenti = (List<Utente>) request.getAttribute("utenti");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "GestioneUtenti";
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
            Utenti reigstrati
        </caption>
        <thead>
            <tr>
                <th>Email</th>
                <th>Nome</th>
                <th>Cognome</th>
                <th>Amministratore</th>
                <th>Bloccato</th>
                <th>Bloccare?</th>
            </tr>
        </thead>
        <%for (int i = 0; i < utenti.size(); i++) {%>
        <tr>
            <td><%=utenti.get(i).getEmail()%></td>
            <td><%=utenti.get(i).getNome()%></td>
            <td><%=utenti.get(i).getCognome()%></td>
            <td><%if(utenti.get(i).isAdmin()){%>Si<%}else{%>No<%}%></td>
            <td><%if(utenti.get(i).isBlocked()){%>Si<%}else{%>No<%}%></td>
            <td>
                <form action="Dispatcher" method="post">
                    <%if(!loggedUser.getEmail().equals(utenti.get(i).getEmail()) && !utenti.get(i).isAdmin()){%>
                        <%if(!utenti.get(i).isBlocked()){%>
                            <input type="hidden" name="controllerAction" value="UtenteManagement.blockUtente"/>
                            <input type="hidden" name="utenteToBlock" value="<%=utenti.get(i).getEmail()%>">
                            <input type="submit" class="button" value="Bloccare"/>
                        <%} else {%>
                            <input type="hidden" name="controllerAction" value="UtenteManagement.unblockUtente"/>
                            <input type="hidden" name="utenteToUnblock" value="<%=utenti.get(i).getEmail()%>">
                            <input type="submit" class="button" value="Sbloccare"/>
                        <%}%>
                    <%}else{%>Azione non</br>disponibile<%}%>
                </form>
            </td>
        </tr>
        <%}%>
    </table>
</main>
</body>
    <%@include file="/include/footer.inc"%>
</html>
