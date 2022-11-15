package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import services.config.Configuration;
import java.sql.Connection;
import java.util.List;

import model.mo.Utente;
import model.dao.DAOFactory;
import model.dao.ProdottoDAO;
import model.dao.mySQLJDBCImpl.ProdottoDAOMySQLJDBCImpl;
import model.mo.Prodotto;

public class CatalogoManagementTest {
    /* test class per il catalogo */
    @Mock
    static HttpServletRequest requestLogged;
    static HttpServletResponse responseLogged;

    static Utente utenteLoggato;
    static Cookie cookies;
    static Prodotto prodotto;

    @BeforeAll
    public static void setUp(){
        /* utente loggato */
        utenteLoggato = new Utente();
        utenteLoggato.setNome("Ion");
        utenteLoggato.setCognome("Boleac");
        utenteLoggato.setEmail("ion.boleac@gmail.com");
        utenteLoggato.setAdmin(true);

        /* creazione cookie */
        cookies = new Cookie("loggedUser", utenteLoggato.getEmail() + "#" + utenteLoggato.getNome() + "#" + utenteLoggato.getCognome() + "#"+ utenteLoggato.isAdmin());

        requestLogged = mock(HttpServletRequest.class);
        responseLogged = mock(HttpServletResponse.class);

        /* settare i cookies */
        when(requestLogged.getCookies()).thenReturn(new Cookie[]{cookies});

    }

    /* test visualizzo il catalogo */
    @Test
    public void testView() throws Exception {
        when(requestLogged.getParameter("annata")).thenReturn("*");
        CatalogoManagement.view(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
    }

    /* test ricerca per annata */
    @Test
    public void testSearchByAnnata() throws Exception {
        when(requestLogged.getParameter("annata")).thenReturn("2019");
        CatalogoManagement.view(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
    }

    /* ricerca per searchString */
    @Test
    public void testSearchByString() throws Exception {
        when(requestLogged.getParameter("searchString")).thenReturn("Brut Blanc");
        CatalogoManagement.view(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
    }

    /* non trova il prodotto */
    @Test
    public void testSearchByStringNotFound() throws Exception {
        when(requestLogged.getParameter("searchString")).thenReturn("bla bla bla");
        CatalogoManagement.view(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestLogged, atLeast(1)).setAttribute("applicationMessage", "Prodotto non trovato");
    }

    /* test dettagli di un prodotto */
    @Test
    public void testDetails() throws Exception {
        when(requestLogged.getParameter("idProdotto")).thenReturn("1");
        CatalogoManagement.dettagliProdotto(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestLogged, atLeast(1)).setAttribute("applicationMessage", "Visualizzazione prodotto");
    }
}
