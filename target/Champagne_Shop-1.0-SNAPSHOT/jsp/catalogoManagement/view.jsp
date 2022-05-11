<%--
  Created by IntelliJ IDEA.
  User: ionut
  Date: 04/10/2021
  Time: 01:41
  To change this template use File | Settings | File Templates.
--%>
<%@page session="false"%>
<%@ page import="model.mo.Utente" %>
<%@ page import="model.mo.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    List<String> annate = (List<String>) request.getAttribute("annate");
    List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Catalogo";
%>
<html>
<head>
    <%@include file="/include/htmlHead.inc"%>
    <style>
      table {
        border-collapse:collapse;
        text-align-last: center;
      }
      td, th {
        border:1px solid #ddd;
        padding:8px;
      }
      .field {
        margin: 5px 0;
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

      label {
        float: left;
        width: 56px;
        font-size: 0.8em;
        margin-right: 10px;
        padding-top: 3px;
        text-align: left;
      }
    </style>
</head>
<body>
    <%@include file="/include/header.inc"%>
    <main>
        <div>
            <a href="Dispatcher?controllerAction=CatalogoManagement.view&annata=*">* </a>
            <%for(int i = 0; i < annate.size(); i++) {%>
              <a href="Dispatcher?controllerAction=CatalogoManagement.view&annata=<%=annate.get(i)%>&searchFlag=N"><%=annate.get(i)%> </a>
            <%}%>
        </div>
        <section>
            <form name="searchBar" action="Dispatcher" method="post">
                <div class="label clearfix">
                    <label for="search">Ricerca libera</label>
                    <input type="text" id="search" name="searchString">
                </div>
                <input type="hidden" name="searchFlag" value="Y">
                <input type="hidden" name="controllerAction" value="CatalogoManagement.view">
                <input type="submit" class="button" value="Ricerca">
            </form>
        </section>
        <br><br><br>
        <table>
            <caption>Catalogo intero dei prodotti</caption>
            <thead>
                <tr>
                    <th>Prodotto</th>
                    <th>Annata</th>
                    <th>Disponibilita'</th>
                    <th>Prezzo</th>
                </tr>
            </thead>
            <%for (int i = 0; i < prodotti.size(); i++) {%>
                <%if (!prodotti.get(i).isDeleted() && !prodotti.get(i).isBlocked()){%>
                    <tr>
                        <td>
                            <a href="Dispatcher?controllerAction=CatalogoManagement.dettagliProdotto&idProdotto=<%=prodotti.get(i).getIdProdotto()%>"><%=prodotti.get(i).getNomeProdotto()%></a>
                        </td>
                        <td><%=prodotti.get(i).getAnnata()%></td>
                        <td>
                            <%if(prodotti.get(i).getQuantita() > 0){%>
                                <%=prodotti.get(i).getQuantita()%>
                            <%}else{%>
                                Non disponibile
                            <%}%>
                        </td>
                        <td><%=prodotti.get(i).getPrezzoUnitario()%></td>
                    </tr>
                <%}%>
            <%}%>

        </table>
            </br>

    </main>
</body>
    <%@include file="/include/footer.inc"%>
</html>
