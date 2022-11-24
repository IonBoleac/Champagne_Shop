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

public class OrdineAdminManagementTest {
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
        classUtil.println("---------OrdineAdminManagementTest-------------");

        cont = 1;
        /* utente loggato */
        utenteLoggato = new Utente();
        utenteLoggato.setNome("Ion");
        utenteLoggato.setCognome("Boleac");
        utenteLoggato.setEmail("ion.boleac@gmail.com");

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
        /* utente loggato */
        OrdineAdminManagement.view(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        /*assertThrows(IllegalArgumentException.class, () -> {
            OrdineAdminManagement.view(requestLogged, responseLogged);
        });*/

        /* utente non loggato, non dovrebbe andare ma funziona. Manca il controllo se c'è un utente verificato loggato, chiunque può passare senza problemi */
        OrdineAdminManagement.view(requestNotLogged, responseNotLogged);
        verify(requestNotLogged, atLeast(1)).setAttribute("loggedOn", false);
        /*assertThrows(IllegalArgumentException.class, () -> {
            OrdineAdminManagement.view(requestNotLogged, responseNotLogged);
        });*/
    }

    /* test block/unblock ordine */
    @Test
    public void testBlockUnblockTestataOrdine() throws Exception{
        /* utente loggato */
        when(requestLogged.getParameter("state")).thenReturn("78");
        when(requestLogged.getParameter("idTestataOrdine")).thenReturn("5");
        //OrdineAdminManagement.blockUnblockTestataOrdine(requestLogged, responseLogged);
        //verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        assertThrows(Exception.class, () -> {
            OrdineAdminManagement.blockUnblockTestataOrdine(requestLogged, responseLogged);
        });

        /* utente non loggato, non dovrebbe andare ma funziona. Manca il controllo se c'è un utente verificato loggato, chiunque può passare senza problemi */
        when(requestNotLogged.getParameter("state")).thenReturn("blocked");
        when(requestNotLogged.getParameter("idTestataOrdine")).thenReturn("4");
        OrdineAdminManagement.blockUnblockTestataOrdine(requestNotLogged, responseNotLogged);
        verify(requestNotLogged, atLeast(1)).setAttribute("loggedOn", false);
        /*assertThrows(IllegalArgumentException.class, () -> {
            OrdineAdminManagement.blockUnblockTestataOrdine(requestNotLogged, responseNotLogged);
        });*/
    }

    /* test set consegnato */
    @Test
    public void testSetConsegnato() throws Exception{
        /* utente loggato */
        when(requestLogged.getParameter("idTestataOrdine")).thenReturn("5");
        OrdineAdminManagement.setCosegnato(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        /*assertThrows(IllegalArgumentException.class, () -> {
            OrdineAdminManagement.setConsegnato(requestLogged, responseLogged);
        });*/

        /* utente non loggato, non dovrebbe andare ma funziona. Manca il controllo se c'è un utente verificato loggato, chiunque può passare senza problemi */
        when(requestNotLogged.getParameter("idTestataOrdine")).thenReturn("4");
        OrdineAdminManagement.setCosegnato(requestNotLogged, responseNotLogged);
        verify(requestNotLogged, atLeast(1)).setAttribute("loggedOn", false);
        /*assertThrows(IllegalArgumentException.class, () -> {
            OrdineAdminManagement.setConsegnato(requestNotLogged, responseNotLogged);
        });*/
    }

    /* test set stato spedizione utente loggato */
    @Test
    public void testSetStatoSpedizione() throws Exception{
        /* utente loggato */
        when(requestLogged.getParameter("idTestataOrdine")).thenReturn("5");
        OrdineAdminManagement.viewSetStatoSpedizione(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        /*assertThrows(IllegalArgumentException.class, () -> {
            OrdineAdminManagement.setStatoSpedizione(requestLogged, responseLogged);
        });*/
    }

    /* test set stato spedizione utente non loggato */
    @Test
    public void testSetStatoSpedizioneNotLogged() throws Exception{
        /* utente non loggato, non dovrebbe andare ma funziona. Manca il controllo se c'è un utente verificato loggato, chiunque può passare senza problemi */
        when(requestNotLogged.getParameter("idTestataOrdine")).thenReturn("4");
        OrdineAdminManagement.viewSetStatoSpedizione(requestNotLogged, responseNotLogged);
        verify(requestNotLogged, atLeast(1)).setAttribute("loggedOn", false);
        /*assertThrows(IllegalArgumentException.class, () -> {
            OrdineAdminManagement.setStatoSpedizione(requestNotLogged, responseNotLogged);
        });*/
    }


}
