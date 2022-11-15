package controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import model.mo.Prodotto;
import model.mo.Utente;
import services.classUtil;

import static org.mockito.Mockito.*;


public class ProdottiManagementTest {
    @Mock
    static HttpServletRequest requestLogged;
    static HttpServletResponse responseLogged;

    static Utente utenteLoggato;
    static Cookie cookies;
    static Prodotto prodotto;

    static int cont;

    @BeforeAll
    public static void setUp(){
        /* dati del nuovo prodotto da inserire per il testing */
        prodotto = new Prodotto();


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
        String forTest = "view tutti i prodotti";
        ProdottiManagement.view(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestLogged, atLeast(1)).setAttribute("forTest", forTest);
    }

    /* test view singolo prodotto */
    @Test
    public void testViewSingoloProdotto() throws Exception{
        String forTest = "view prodotto singolo";
        when(requestLogged.getParameter("idProduct")).thenReturn("1");
        ProdottiManagement.prodotto(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestLogged, atLeast(1)).setAttribute("forTest", forTest);
    }

}
