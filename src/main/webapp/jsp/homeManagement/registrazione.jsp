<%--
  Created by IntelliJ IDEA.
  User: ionut
  Date: 30/09/2021
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="false"%>
<%@ page import="model.mo.Utente" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Registrazione";
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
    <section id="insModFormSection">
        <form name="registrazione" action="Dispatcher" method="post">
            <div class="field clearfix">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" size="20" maxlength="50" required></br>
            </div>
            <div class="field clearfix">
                <label for="nome">Nome</label>
                <input type="text" id="nome" name="nome" size="20" maxlength="50" required></br>
            </div>
            <div class="field clearfix">
                <label for="cognome">Cognome</label>
                <input type="text" id="cognome" name="cognome" size="20" maxlength="50" required></br>
            </div>
            <div class="field clearfix">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" size="20" maxlength="50" required></br>
            </div>
            <div class="field clearfix">
                <label>Sesso</label>
                <input type="radio" name="sesso" value="M"/> M
                <input type="radio" name="sesso" value="F"/> F
            </div>
            <div class="field clearfix">
                <label for="data">Data Nascita</label>
                <input type="date" id="data" name="data" size="20" maxlength="50" required></br>
            </div>
            <div class="field clearfix">
                <label for="cellulare">Cellulare</label>
                <input type="text" id="cellulare" name="cellulare" size="20" maxlength="50" required max="2021-09-"></br>
            </div>
            <div class="field clearfix">
                <label>&#160;</label>
                <input type="hidden" name="controllerAction" value="HomeManagement.registrazione"/>
                <%if(loggedOn && loggedUser.isAdmin()){%>
                    <input type="hidden" name="isAdmin" value="true">
                <%}else{%>
                    <input type="hidden" name="isAdmin" value="false">
                <%}%>
                <input type="submit" class="button" value="Invia"/>
                <!--input type="button" name="backButton" class="button" value="Annulla"/-->
            </div>
        </form>
    </section>
</main>
</body>
<%@include file="/include/footer.inc"%>
</html>
