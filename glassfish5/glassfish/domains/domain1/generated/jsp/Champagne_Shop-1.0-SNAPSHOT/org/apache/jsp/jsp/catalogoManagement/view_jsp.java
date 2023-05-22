package org.apache.jsp.jsp.catalogoManagement;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import model.mo.Utente;
import model.mo.Prodotto;
import java.util.List;

public final class view_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(3);
    _jspx_dependants.add("/include/htmlHead.inc");
    _jspx_dependants.add("/include/header.inc");
    _jspx_dependants.add("/include/footer.inc");
  }

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      response.setHeader("X-Powered-By", "JSP/2.3");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, false, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    List<String> annate = (List<String>) request.getAttribute("annate");
    List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Catalogo";

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    ");
      out.write("<meta charset=\"utf-8\"/>\r\n");
      out.write("\r\n");
      out.write("<!-- Linking styles -->\r\n");
      out.write("<link rel=\"stylesheet\" href=\"css/addressbook.css\" type=\"text/css\" media=\"screen\">\r\n");
      out.write("<title>ChampagneShop: ");
      out.print(menuActiveLink);
      out.write("</title>\r\n");
      out.write("<script>\r\n");
      out.write("  var applicationMessage;\r\n");
      out.write("  ");
if (applicationMessage != null) {
      out.write("\r\n");
      out.write("    applicationMessage=\"");
      out.print(applicationMessage);
      out.write("\";\r\n");
      out.write("  ");
}
      out.write("\r\n");
      out.write("  function onLoadHandler() {\r\n");
      out.write("    headerOnLoadHandler();\r\n");
      out.write("    try { mainOnLoadHandler(); } catch (e) {}\r\n");
      out.write("    if (applicationMessage!=undefined) alert(applicationMessage);\r\n");
      out.write("  }\r\n");
      out.write("  window.addEventListener(\"load\", onLoadHandler);\r\n");
      out.write("</script> ");
      out.write("\r\n");
      out.write("    <style>\r\n");
      out.write("      table {\r\n");
      out.write("        border-collapse:collapse;\r\n");
      out.write("        text-align-last: center;\r\n");
      out.write("      }\r\n");
      out.write("      td, th {\r\n");
      out.write("        border:1px solid #ddd;\r\n");
      out.write("        padding:8px;\r\n");
      out.write("      }\r\n");
      out.write("      .field {\r\n");
      out.write("        margin: 5px 0;\r\n");
      out.write("      }\r\n");
      out.write("      input[type=\"text\"], input[type=\"tel\"], input[type=\"email\"], input[type=\"password\"], input[type=\"date\"] {\r\n");
      out.write("        border: none;\r\n");
      out.write("        border-radius: 4px;\r\n");
      out.write("        padding: 3px;\r\n");
      out.write("        background-color: #e8eeef;\r\n");
      out.write("        color:#8a97a0;\r\n");
      out.write("        box-shadow: 0 1px 0 rgba(0,0,0,0.03) inset;\r\n");
      out.write("      }\r\n");
      out.write("\r\n");
      out.write("      input[type=\"text\"]:focus, input[type=\"tel\"]:focus, input[type=\"email\"]:focus, input[type=\"password\"]:focus, input[type=\"date\"]:focus {\r\n");
      out.write("        background: #d2d9dd;\r\n");
      out.write("        outline-color: #a3271f;\r\n");
      out.write("      }\r\n");
      out.write("\r\n");
      out.write("      label {\r\n");
      out.write("        float: left;\r\n");
      out.write("        width: 56px;\r\n");
      out.write("        font-size: 0.8em;\r\n");
      out.write("        margin-right: 10px;\r\n");
      out.write("        padding-top: 3px;\r\n");
      out.write("        text-align: left;\r\n");
      out.write("      }\r\n");
      out.write("    </style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("    ");
      out.write("<script>\r\n");
      out.write("  function headerOnLoadHandler() {\r\n");
      out.write("    var usernameTextField = document.querySelector(\"#username\");\r\n");
      out.write("    var usernameTextFieldMsg = \"Lo username \\xE8 obbligatorio.\";\r\n");
      out.write("    var passwordTextField = document.querySelector(\"#password\");\r\n");
      out.write("    var passwordTextFieldMsg = \"La password \\xE8 obbligatoria.\";\r\n");
      out.write("\r\n");
      out.write("    if (usernameTextField != undefined && passwordTextField != undefined ) {\r\n");
      out.write("      usernameTextField.setCustomValidity(usernameTextFieldMsg);\r\n");
      out.write("      usernameTextField.addEventListener(\"change\", function () {\r\n");
      out.write("        this.setCustomValidity(this.validity.valueMissing ? usernameTextFieldMsg : \"\");\r\n");
      out.write("      });\r\n");
      out.write("      passwordTextField.setCustomValidity(passwordTextFieldMsg);\r\n");
      out.write("      passwordTextField.addEventListener(\"change\", function () {\r\n");
      out.write("       this.setCustomValidity(this.validity.valueMissing ? passwordTextFieldMsg : \"\");\r\n");
      out.write("      });\r\n");
      out.write("    }\r\n");
      out.write("  }\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("<header class=\"clearfix\"><!-- Defining the header section of the page -->\r\n");
      out.write("\r\n");
      out.write("  <h1 class=\"logo\"><!-- Defining the logo element -->\r\n");
      out.write("    ChampagneShop\r\n");
      out.write("  </h1>\r\n");
      out.write("\r\n");
      out.write("  <form name=\"logoutForm\" action=\"Dispatcher\" method=\"post\">\r\n");
      out.write("    <input type=\"hidden\" name=\"controllerAction\" value=\"HomeManagement.logout\"/>\r\n");
      out.write("  </form>\r\n");
      out.write("\r\n");
      out.write("  <nav><!-- Defining the navigation menu -->\r\n");
      out.write("    <ul>\r\n");
      out.write("      <li ");
      out.print(menuActiveLink.equals("Home")?"class=\"active\"":"");
      out.write(">\r\n");
      out.write("        <a href=\"Dispatcher?controllerAction=HomeManagement.view\">Home</a>\r\n");
      out.write("      </li>\r\n");
      out.write("      <li ");
      out.print(menuActiveLink.equals("Registrazione")?"class=\"active\"":"");
      out.write(">\r\n");
      out.write("         <a href=\"Dispatcher?controllerAction=HomeManagement.regView\">\r\n");
      out.write("                ");
if(loggedOn && loggedUser.isAdmin()) {
      out.write("Registrazione</br>amministratore\r\n");
      out.write("                ");
}else{
      out.write("Registrazione</br>utente");
}
      out.write("</a>\r\n");
      out.write("          </>\r\n");
      out.write("      <li ");
      out.print(menuActiveLink.equals("Catalogo")?"class=\"active\"":"");
      out.write(">\r\n");
      out.write("                    <a href=\"Dispatcher?controllerAction=CatalogoManagement.view\">Catalogo</a>\r\n");
      out.write("      </li>\r\n");
      out.write("      ");
if (loggedOn) {
      out.write("\r\n");
      out.write("        <li ");
      out.print(menuActiveLink.equals("Carrello")?"class=\"active\"":"");
      out.write(">\r\n");
      out.write("          <a href=\"Dispatcher?controllerAction=CarrelloManagement.view\">Carrello</a>\r\n");
      out.write("        </li>\r\n");
      out.write("        <li ");
      out.print(menuActiveLink.equals("Miei_Ordini")?"class=\"active\"":"");
      out.write(">\r\n");
      out.write("          <a href=\"Dispatcher?controllerAction=OrdineManagement.view\">Miei</br>Ordini</a>\r\n");
      out.write("        </li>\r\n");
      out.write("        ");
if (loggedUser.isAdmin()) {
      out.write("\r\n");
      out.write("           <li ");
      out.print(menuActiveLink.equals("GestioneProdotti")?"class=\"active\"":"");
      out.write(">\r\n");
      out.write("             <a href=\"Dispatcher?controllerAction=ProdottiManagement.view\">Gestione</br>Prodotto</a>\r\n");
      out.write("           </>\r\n");
      out.write("           <li ");
      out.print(menuActiveLink.equals("GestioneUtenti")?"class=\"active\"":"");
      out.write(">\r\n");
      out.write("             <a href=\"Dispatcher?controllerAction=UtenteManagement.view\">Gestione</br>Utenti</a>\r\n");
      out.write("           </>\r\n");
      out.write("           <li ");
      out.print(menuActiveLink.equals("GestioneOrdini")?"class=\"active\"":"");
      out.write(">\r\n");
      out.write("             <a href=\"Dispatcher?controllerAction=OrdineAdminManagement.view\">Gestione</br>Ordini</a>\r\n");
      out.write("           </>\r\n");
      out.write("         ");
}
      out.write("\r\n");
      out.write("        <li ");
      out.print(menuActiveLink.equals("EliminaUtente")?"class=\"active\"":"");
      out.write(">\r\n");
      out.write("          <a href=\"Dispatcher?controllerAction=HomeManagement.deleteUtente\">Elimina</br>Utente</br></a>\r\n");
      out.write("        </li>\r\n");
      out.write("        <li><a href=\"javascript:logoutForm.submit()\">Logout</a></li>\r\n");
      out.write("      ");
}
      out.write("\r\n");
      out.write("    </ul>\r\n");
      out.write("  </nav>\r\n");
      out.write("\r\n");
      out.write("  ");
if (!loggedOn && menuActiveLink.equals("Registrazione")==false) {
      out.write("\r\n");
      out.write("    <section id=\"login\" class=\"clearfix\">\r\n");
      out.write("      <form name=\"logonForm\" action=\"Dispatcher\" method=\"post\">\r\n");
      out.write("        <label for=\"email\">Email</label>\r\n");
      out.write("        <input type=\"text\" id=\"email\"  name=\"email\" maxlength=\"40\" required>\r\n");
      out.write("        <label for=\"password\">Password</label>\r\n");
      out.write("        <input type=\"password\" id=\"password\" name=\"password\" maxlength=\"40\" required>\r\n");
      out.write("        <input type=\"hidden\" name=\"controllerAction\" value=\"HomeManagement.logon\"/>\r\n");
      out.write("        <input type=\"submit\" value=\"Ok\">\r\n");
      out.write("      </form></br>\r\n");
      out.write("    </section>\r\n");
      out.write("  ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("</header>");
      out.write("\r\n");
      out.write("    <main>\r\n");
      out.write("        <div>\r\n");
      out.write("            <a href=\"Dispatcher?controllerAction=CatalogoManagement.view&annata=*\">* </a>\r\n");
      out.write("            ");
for(int i = 0; i < annate.size(); i++) {
      out.write("\r\n");
      out.write("              <a href=\"Dispatcher?controllerAction=CatalogoManagement.view&annata=");
      out.print(annate.get(i));
      out.write("&searchFlag=N\">");
      out.print(annate.get(i));
      out.write(" </a>\r\n");
      out.write("            ");
}
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("        <section>\r\n");
      out.write("            <form name=\"searchBar\" action=\"Dispatcher\" method=\"post\">\r\n");
      out.write("                <div class=\"label clearfix\">\r\n");
      out.write("                    <label for=\"search\">Ricerca libera</label>\r\n");
      out.write("                    <input type=\"text\" id=\"search\" name=\"searchString\">\r\n");
      out.write("                </div>\r\n");
      out.write("                <input type=\"hidden\" name=\"searchFlag\" value=\"Y\">\r\n");
      out.write("                <input type=\"hidden\" name=\"controllerAction\" value=\"CatalogoManagement.view\">\r\n");
      out.write("                <input type=\"submit\" class=\"button\" value=\"Ricerca\">\r\n");
      out.write("            </form>\r\n");
      out.write("        </section>\r\n");
      out.write("        <br><br><br>\r\n");
      out.write("        <table>\r\n");
      out.write("            <caption>Catalogo intero dei prodotti</caption>\r\n");
      out.write("            <thead>\r\n");
      out.write("                <tr>\r\n");
      out.write("                    <th>Prodotto</th>\r\n");
      out.write("                    <th>Annata</th>\r\n");
      out.write("                    <th>Disponibilita'</th>\r\n");
      out.write("                    <th>Prezzo</th>\r\n");
      out.write("                </tr>\r\n");
      out.write("            </thead>\r\n");
      out.write("            ");
for (int i = 0; i < prodotti.size(); i++) {
      out.write("\r\n");
      out.write("                ");
if (!prodotti.get(i).isDeleted() && !prodotti.get(i).isBlocked()){
      out.write("\r\n");
      out.write("                    <tr>\r\n");
      out.write("                        <td>\r\n");
      out.write("                            <a href=\"Dispatcher?controllerAction=CatalogoManagement.dettagliProdotto&idProdotto=");
      out.print(prodotti.get(i).getIdProdotto());
      out.write('"');
      out.write('>');
      out.print(prodotti.get(i).getNomeProdotto());
      out.write("</a>\r\n");
      out.write("                        </td>\r\n");
      out.write("                        <td>");
      out.print(prodotti.get(i).getAnnata());
      out.write("</td>\r\n");
      out.write("                        <td>\r\n");
      out.write("                            ");
if(prodotti.get(i).getQuantita() > 0){
      out.write("\r\n");
      out.write("                                ");
      out.print(prodotti.get(i).getQuantita());
      out.write("\r\n");
      out.write("                            ");
}else{
      out.write("\r\n");
      out.write("                                Non disponibile\r\n");
      out.write("                            ");
}
      out.write("\r\n");
      out.write("                        </td>\r\n");
      out.write("                        <td>");
      out.print(prodotti.get(i).getPrezzoUnitario());
      out.write("</td>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                ");
}
      out.write("\r\n");
      out.write("            ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("        </table>\r\n");
      out.write("            </br>\r\n");
      out.write("\r\n");
      out.write("    </main>\r\n");
      out.write("</body>\r\n");
      out.write("    ");
      out.write("<footer class=\"clearfix\"><!-- Defining the footer section of the page -->\r\n");
      out.write("  ");
if(loggedOn){
      out.write("\r\n");
      out.write("  <section>\r\n");
      out.write("    Benvenuto/a ");
      out.print(loggedUser.getNome());
      out.write("\r\n");
      out.write("  </section>\r\n");
      out.write("  ");
}
      out.write("\r\n");
      out.write("  <section id=\"privacy\">\r\n");
      out.write("    Ion Boleac 2021 <br/>\r\n");
      out.write("  </section>\r\n");
      out.write("</footer>");
      out.write("\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
