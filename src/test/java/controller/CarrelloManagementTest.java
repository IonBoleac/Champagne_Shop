package controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import model.mo.Utente;
import services.classUtil;

public class CarrelloManagementTest {
    @Mock
    static HttpServletRequest requestLogged;
    static HttpServletResponse responseLogged;

    static Utente utenteLoggato;
    static Cookie cookies;
    
    static int cont;

    @BeforeAll
    public static void setUp(){
        cont = 0;
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

    @BeforeEach
    public void BeforeEach(){
        classUtil.println("Stampo a video " + cont);
        cont += 1;
    }

    /* testo la view */
    @Test
    public void testView() throws Exception{
        CarrelloManagement.view(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestLogged, atLeast(1)).setAttribute("forTest", "tutto il carrello");
    }

    /* aggiungo prodotto nel carrello */
    @Test 
    public void testAddProdottoNelCarrelloMethod() throws Exception{
        when(requestLogged.getParameter("idProdotto")).thenReturn("1");
        when(requestLogged.getParameter("email")).thenReturn(utenteLoggato.getEmail());
        when(requestLogged.getParameter("quantita")).thenReturn("10");
        CarrelloManagement.addProdottoNelCarrelloMethod(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestLogged, atLeast(1)).setAttribute("forTest", "aggiungo il prodotto nel carrello");
    }

    /* test cancello prodotto dal carrello */
    @Test
    public void testEliminaProdotto() throws Exception{
        when(requestLogged.getParameter("idProdotto")).thenReturn("1");
        when(requestLogged.getParameter("email")).thenReturn(utenteLoggato.getEmail());
        CarrelloManagement.deleteProdottoDaCarrello(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestLogged, atLeast(1)).setAttribute("forTest", "cancello il prodotto dal carrello");
    }
}
