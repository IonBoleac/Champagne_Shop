<%@page session="false"%>
<%@page import="model.mo.Utente"%>
<%@ page import="java.util.List" %>
<%@ page import="model.mo.Prodotto" %>

<%
  boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
  Utente loggedUser = (Utente) request.getAttribute("loggedUser");
  List<Prodotto> prodottiVetrina = (List<Prodotto>) request.getAttribute("prodottiVetrina");
  String applicationMessage = (String) request.getAttribute("applicationMessage");
  String menuActiveLink = "Home";
%>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="/include/htmlHead.inc"%>
    <style>
      img{
        width: 300px;
        height: 400px;
      }
    </style>
  </head>
  <body>
    <%@include file="/include/header.inc"%>
    <main>
      <%if (loggedOn) {%>
      Benvenuto <%if(loggedUser.isAdmin()){%> amministratore<%}%> <%=loggedUser.getNome()%> <%=loggedUser.getCognome()%>!<br/>
      <%} else {%>
      Benvenuto.
      Fai il logon per gestire la tua pagina.
      <%}%>
      <table>
        <caption>Elementi in promozione</caption>
        <%for(int i = 0; i < prodottiVetrina.size(); i++) {%>
          <tr>
            <td><img src="images/<%=prodottiVetrina.get(i).getUrlImage()%>" alt="Immagine prodotto"></td>
            <td style="width: 80%"><%=prodottiVetrina.get(i).getDescrizione()%></td>
            <td><%=prodottiVetrina.get(i).getPrezzoUnitario()%> euro</td>
          </tr>
        <%}%>
      </table>
    </main>
  </body>
    <%@include file="/include/footer.inc"%>
</html>
