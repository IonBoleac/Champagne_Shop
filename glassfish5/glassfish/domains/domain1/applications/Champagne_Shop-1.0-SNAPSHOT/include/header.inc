<script>
  function headerOnLoadHandler() {
    var usernameTextField = document.querySelector("#username");
    var usernameTextFieldMsg = "Lo username \xE8 obbligatorio.";
    var passwordTextField = document.querySelector("#password");
    var passwordTextFieldMsg = "La password \xE8 obbligatoria.";

    if (usernameTextField != undefined && passwordTextField != undefined ) {
      usernameTextField.setCustomValidity(usernameTextFieldMsg);
      usernameTextField.addEventListener("change", function () {
        this.setCustomValidity(this.validity.valueMissing ? usernameTextFieldMsg : "");
      });
      passwordTextField.setCustomValidity(passwordTextFieldMsg);
      passwordTextField.addEventListener("change", function () {
       this.setCustomValidity(this.validity.valueMissing ? passwordTextFieldMsg : "");
      });
    }
  }
</script>

<header class="clearfix"><!-- Defining the header section of the page -->

  <h1 class="logo"><!-- Defining the logo element -->
    ChampagneShop
  </h1>

  <form name="logoutForm" action="Dispatcher" method="post">
    <input type="hidden" name="controllerAction" value="HomeManagement.logout"/>
  </form>

  <nav><!-- Defining the navigation menu -->
    <ul>
      <li <%=menuActiveLink.equals("Home")?"class=\"active\"":""%>>
        <a href="Dispatcher?controllerAction=HomeManagement.view">Home</a>
      </li>
      <li <%=menuActiveLink.equals("Registrazione")?"class=\"active\"":""%>>
         <a href="Dispatcher?controllerAction=HomeManagement.regView">
                <%if(loggedOn && loggedUser.isAdmin()) {%>Registrazione</br>amministratore
                <%}else{%>Registrazione</br>utente<%}%></a>
          </>
      <li <%=menuActiveLink.equals("Catalogo")?"class=\"active\"":""%>>
                    <a href="Dispatcher?controllerAction=CatalogoManagement.view">Catalogo</a>
      </li>
      <%if (loggedOn) {%>
        <li <%=menuActiveLink.equals("Carrello")?"class=\"active\"":""%>>
          <a href="Dispatcher?controllerAction=CarrelloManagement.view">Carrello</a>
        </li>
        <li <%=menuActiveLink.equals("Miei_Ordini")?"class=\"active\"":""%>>
          <a href="Dispatcher?controllerAction=OrdineManagement.view">Miei</br>Ordini</a>
        </li>
        <%if (loggedUser.isAdmin()) {%>
           <li <%=menuActiveLink.equals("GestioneProdotti")?"class=\"active\"":""%>>
             <a href="Dispatcher?controllerAction=ProdottiManagement.view">Gestione</br>Prodotto</a>
           </>
           <li <%=menuActiveLink.equals("GestioneUtenti")?"class=\"active\"":""%>>
             <a href="Dispatcher?controllerAction=UtenteManagement.view">Gestione</br>Utenti</a>
           </>
           <li <%=menuActiveLink.equals("GestioneOrdini")?"class=\"active\"":""%>>
             <a href="Dispatcher?controllerAction=OrdineAdminManagement.view">Gestione</br>Ordini</a>
           </>
         <%}%>
        <li <%=menuActiveLink.equals("EliminaUtente")?"class=\"active\"":""%>>
          <a href="Dispatcher?controllerAction=HomeManagement.deleteUtente">Elimina</br>Utente</br></a>
        </li>
        <li><a href="javascript:logoutForm.submit()">Logout</a></li>
      <%}%>
    </ul>
  </nav>

  <%if (!loggedOn && menuActiveLink.equals("Registrazione")==false) {%>
    <section id="login" class="clearfix">
      <form name="logonForm" action="Dispatcher" method="post">
        <label for="email">Email</label>
        <input type="text" id="email"  name="email" maxlength="40" required>
        <label for="password">Password</label>
        <input type="password" id="password" name="password" maxlength="40" required>
        <input type="hidden" name="controllerAction" value="HomeManagement.logon"/>
        <input type="submit" value="Ok">
      </form></br>
    </section>
  <%}%>

</header>