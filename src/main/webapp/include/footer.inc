<footer class="clearfix"><!-- Defining the footer section of the page -->
  <%if(loggedOn){%>
  <section>
    Benvenuto/a <%=loggedUser.getNome()%>
  </section>
  <%}%>
  <section id="privacy">
    Ion Boleac 2021 <br/>
  </section>
</footer>