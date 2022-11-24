package controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ClassUtils;
import org.mockito.Mock;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import model.mo.Utente;
import services.classUtil;

public class OrdineManagementTest {
    @Mock
    /* requeste, response loggato */
    static HttpServletRequest requestLogged;
    static HttpServletResponse responseLogged;

    /* requeste, response non loggato */
    static HttpServletRequest requestNotLogged;
    static HttpServletResponse responseNotLogged;

    static Utente utenteLoggato;
    static Cookie cookies;
    
    static int cont;

    @BeforeAll
    public static void setUp(){
        classUtil.println("---------OrdineManagementTest-------------");

        cont = 1;
        /* utente loggato */
        utenteLoggato = new Utente();
        utenteLoggato.setNome("Ion");
        utenteLoggato.setCognome("Boleac");
        utenteLoggato.setEmail("ion.boleac@gmail.com");
        utenteLoggato.setAdmin(true);

        /* creazione cookie */
        cookies = new Cookie("loggedUser", utenteLoggato.getEmail() + "#" + utenteLoggato.getNome() + "#" + utenteLoggato.getCognome() + "#"+ utenteLoggato.isAdmin());

        /* requeste, response loggato */
        requestLogged = mock(HttpServletRequest.class);
        responseLogged = mock(HttpServletResponse.class);

        /* requeste, response non loggato */
        requestNotLogged = mock(HttpServletRequest.class);
        responseNotLogged = mock(HttpServletResponse.class);

        /* settare i cookies */
        when(requestLogged.getCookies()).thenReturn(new Cookie[]{cookies}); // cookies per utente loggato
        when(requestNotLogged.getCookies()).thenReturn(null); // cookies per utente non loggato
    }

    @BeforeEach
    public void BeforeEach(){
        classUtil.println("Stampo a video " + cont);
        cont += 1;
    }

    /* testo la view */
    @Test
    public void testView() throws Exception{
        OrdineManagement.view(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestLogged, atLeast(1)).setAttribute("forTest", "view tutti gli ordini");
    }

    /* testo la view con utente non loggato, non dovrebbe andare */
    @Test
    public void testViewNotLogged() throws Exception{
        Exception exception = assertThrows(Exception.class, () -> {
            OrdineManagement.view(requestNotLogged, responseNotLogged);
        });
    }

    /* test lista prodotti */
    @Test
    public void testListaProdotti() throws Exception{
        when(requestLogged.getParameter("idTestataOrdine")).thenReturn("1");
        OrdineManagement.listaProdotti(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestLogged, atLeast(1)).setAttribute("forTest", "test lista prodotti");
    }

    /* 
     * test lista prodotti con utente non loggato, non dovrebbe andare 
     * però funziona... 
     * il problema è che non si fa una verica dell'utente se è loggato o meno
    */
    @Test
    public void testListaProdottiNotLogged() throws Exception{
        when(requestNotLogged.getParameter("idTestataOrdine")).thenReturn("1");
        OrdineManagement.listaProdotti(requestNotLogged, responseNotLogged);
        verify(requestNotLogged, atLeast(1)).setAttribute("loggedOn", false);
        verify(requestNotLogged, atLeast(1)).setAttribute("forTest", "test lista prodotti");
    }

    /*  */

    /* test effettua ordine */
    @Test
    public void testEffettuaOrdine() throws Exception{
        when(requestLogged.getParameter("nome")).thenReturn(utenteLoggato.getNome());
        when(requestLogged.getParameter("cognome")).thenReturn(utenteLoggato.getCognome());
        when(requestLogged.getParameter("indirizzoSpedizione")).thenReturn("indirizzo test");
        when(requestLogged.getParameter("citta")).thenReturn("citta test");
        when(requestLogged.getParameter("cap")).thenReturn("12345");
        when(requestLogged.getParameter("provincia")).thenReturn("provincia test");
        when(requestLogged.getParameter("regione")).thenReturn("regione test");

        OrdineManagement.effettuaOrdine(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestLogged, atLeast(1)).setAttribute("forTest", "test effettua ordine");
    }

    /* effettua ordine da non loggato, non effettua l'otdine anche se non è loggato */
    @Test
    public void testEffettuaOrdineNotLogged() throws Exception{
        when(requestNotLogged.getParameter("nome")).thenReturn(utenteLoggato.getNome());
        when(requestNotLogged.getParameter("cognome")).thenReturn(utenteLoggato.getCognome());
        when(requestNotLogged.getParameter("indirizzoSpedizione")).thenReturn("indirizzo test");
        when(requestNotLogged.getParameter("citta")).thenReturn("citta test");
        when(requestNotLogged.getParameter("cap")).thenReturn("12345");
        when(requestNotLogged.getParameter("provincia")).thenReturn("provincia test");
        when(requestNotLogged.getParameter("regione")).thenReturn("regione test");

        Exception exception = assertThrows(Exception.class, () -> {
            OrdineManagement.effettuaOrdine(requestNotLogged, responseNotLogged);
        });
    }

}
