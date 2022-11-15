package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Date;

import model.mo.Utente;




public class HomeManagementTest {
    
    @Mock
    static Utente utenteLoggato, utenteDelete;
    static Cookie cookies;
    static Cookie cookiesDelete;

    /* requeste, response per utente non loggato */
    static HttpServletRequest requestNotLogged;
    static HttpServletResponse responseNotLogged;
    
    /* requeste, response per utente loggato */
    static HttpServletRequest requestLogged;
    static HttpServletResponse responseLogged;

    /* requeste, response per il logon */
    static HttpServletRequest request;
    static HttpServletResponse response;

    /* requeste, response per il test del delete dell'utente */
    static HttpServletRequest requestDelete;
    static HttpServletResponse responseDelete;

    /* requeste, response per registrazione con dati strani */
    static HttpServletRequest requestUtenteConDatiStrani;
    static HttpServletResponse responseUtenteConDatiStrani;

    /* per il testing */
    static Date date;

    @BeforeAll
    public static void setUp(){
        date = new Date();

        /* utente loggato */
        utenteLoggato = new Utente();
        utenteLoggato.setNome("Ion");
        utenteLoggato.setCognome("Boleac");
        utenteLoggato.setEmail("ion.boleac.gmail.com");
        utenteLoggato.setAdmin(true);

        /* utente per l'eliminazione */
        utenteDelete = new Utente();
        utenteDelete.setNome("Silvia");
        utenteDelete.setCognome("Boleac");
        utenteDelete.setEmail("silvia.boleac@gmail.com");
        utenteDelete.setAdmin(true);

        /* requeste, response per registrazione con dati strani */
        requestUtenteConDatiStrani = mock(HttpServletRequest.class);
        responseUtenteConDatiStrani = mock(HttpServletResponse.class);

        /* requeste, response per il test del delete dell'utente */
        requestDelete = mock(HttpServletRequest.class);
        responseDelete = mock(HttpServletResponse.class);

        /* requeste, response per utente non loggato */
        requestNotLogged = mock(HttpServletRequest.class);
        responseNotLogged = mock(HttpServletResponse.class);

        /* requeste, response per utente loggato */
        requestLogged = mock(HttpServletRequest.class);
        responseLogged = mock(HttpServletResponse.class);

        /* requeste, response per il logon */
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        /* creazione cookie */
        cookies = new Cookie("loggedUser", utenteLoggato.getEmail() + "#" + utenteLoggato.getNome() + "#" + utenteLoggato.getCognome() + "#"+ utenteLoggato.isAdmin());
        cookiesDelete = new Cookie("loggedUser", utenteDelete.getEmail() + "#" + utenteDelete.getNome() + "#" + utenteDelete.getCognome() + "#"+ utenteDelete.isAdmin());
        
        /* quando viene chiamato il metodo getCookie() della request, ritorna il cookie creato */
        when(requestLogged.getCookies()).thenReturn(new Cookie[]{cookies}); //utente loggato
        when(requestNotLogged.getCookies()).thenReturn(null); //utente non loggato
        when(request.getCookies()).thenReturn(null); //per il logon
        when(requestDelete.getCookies()).thenReturn(new Cookie[]{cookiesDelete}); //per il delete dell'utente
        when(requestUtenteConDatiStrani.getCookies()).thenReturn(null); //per il test del metodo registrazione con dati strani
    }

    /* test utente non loggato loggato  */
    @Test
    public void testViewHomeManagementLogged() throws Exception{
        HomeManagement.view(requestNotLogged, responseNotLogged);
        verify(requestNotLogged, atLeast(1)).setAttribute("loggedOn", false);
        verify(requestNotLogged, atLeast(1)).setAttribute("viewUrl", "homeManagement/view");
    }


    /* test utente loggato */
    @Test
    public void testViewHomeManagementNotLogged() throws Exception{
        HomeManagement.view(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestLogged, atLeast(1)).setAttribute("viewUrl", "homeManagement/view");
    }

    /* test logon con successo */
    @Test
    public void testLogonSuccess() throws Exception{
        /* parametri per il logon */
        when(request.getParameter("email")).thenReturn("ion.boleac@gmail.com");
        when(request.getParameter("password")).thenReturn("qwe");

        HomeManagement.logon(request, response);
        verify(request, atLeast(1)).setAttribute("loggedOn", true);
        verify(request, atLeast(1)).setAttribute("forTest", "Utente trovato");
        verify(request, atLeast(1)).setAttribute("viewUrl", "homeManagement/view");
    }

    /* test logon con fallimento */
    @Test
    public void testLogonFail() throws Exception{
        /* parametri per il logon con fallimento */
        when(request.getParameter("email")).thenReturn("qwe");
        when(request.getParameter("password")).thenReturn("asd");

        HomeManagement.logon(request, response);
        verify(request, atLeast(1)).setAttribute("loggedOn", false);  
        verify(request, atLeast(1)).setAttribute("forTest", "Utente non trovato");
        verify(request, atLeast(1)).setAttribute("viewUrl", "homeManagement/view");
    }

    /* test logout */
    @Test
    public void testLogout() throws Exception{
        HomeManagement.logout(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", false);
        verify(requestLogged, atLeast(1)).setAttribute("loggedUser", null);
        verify(requestLogged, atLeast(1)).setAttribute("viewUrl", "homeManagement/view");
    }

    /* test registrazione nuovo utente da non loggato */
    @Test
    public void testRegistrazioneNuovoUtenteDaNonLoggato() throws Exception{
        /* parametri per la registrazione del nuovo utente da non loggato */
        when(requestNotLogged.getParameter("email")).thenReturn(date.getTime() + "@gmail.com");
        when(requestNotLogged.getParameter("nome")).thenReturn("NonLoggato");
        when(requestNotLogged.getParameter("cognome")).thenReturn("NonLoggato");
        when(requestNotLogged.getParameter("sesso")).thenReturn("S");
        when(requestNotLogged.getParameter("data")).thenReturn("1999-12-12");
        when(requestNotLogged.getParameter("cellulare")).thenReturn("cellulare");
        when(requestNotLogged.getParameter("password")).thenReturn("password");
        when(requestNotLogged.getParameter("isAdmin")).thenReturn("false");

        HomeManagement.registrazione(requestNotLogged, responseNotLogged);
        verify(requestNotLogged, atLeast(1)).setAttribute("loggedOn", false);
        verify(requestNotLogged, atLeast(1)).setAttribute("applicationMessage", "Registrazione avvenuta con successo");
    }

    /* test registrazione nuovo utente da loggato */
    @Test
    public void testRegistrazioneNuovoUtenteDaLoggato() throws Exception{
        /* parametri pee la registrazione di un nuovo utente da loggato */
        when(requestLogged.getParameter("email")).thenReturn(date.getTime() + "1@gmail.com");
        when(requestLogged.getParameter("nome")).thenReturn("Loggato");
        when(requestLogged.getParameter("cognome")).thenReturn("Loggato");
        when(requestLogged.getParameter("sesso")).thenReturn("S");
        when(requestLogged.getParameter("data")).thenReturn("1999-12-12");
        when(requestLogged.getParameter("cellulare")).thenReturn("cellulare");
        when(requestLogged.getParameter("password")).thenReturn("password");
        when(requestLogged.getParameter("isAdmin")).thenReturn("true");

        HomeManagement.registrazione(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestLogged, atLeast(1)).setAttribute("applicationMessage", "Registrazione avvenuta con successo");
    }

    /* test registrazione utente già registrato da loggato */
    @Test
    public void testRegistrazioneUtenteEsistente() throws Exception{
        /* parametri per la registrazione di un utente già esistente */
        when(requestLogged.getParameter("email")).thenReturn("esistente@gmail.com");
        when(requestLogged.getParameter("nome")).thenReturn("Esistente");
        when(requestLogged.getParameter("cognome")).thenReturn("Esistente");
        when(requestLogged.getParameter("sesso")).thenReturn("S");
        when(requestLogged.getParameter("data")).thenReturn("1999-12-12");
        when(requestLogged.getParameter("cellulare")).thenReturn("cellulare");
        when(requestLogged.getParameter("password")).thenReturn("password");
        when(requestLogged.getParameter("isAdmin")).thenReturn("true");

        HomeManagement.registrazione(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestLogged, atLeast(1)).setAttribute("applicationMessage", "Utente già esistente");
    }

    /* test delete utente */
    @Test
    public void testDeleteUtente() throws Exception{
        HomeManagement.deleteUtente(requestDelete, responseDelete);
        verify(requestDelete, atLeast(1)).setAttribute("loggedOn", false);
        verify(requestDelete, atLeast(1)).setAttribute("loggedUser", null);
        verify(requestDelete, atLeast(1)).setAttribute("applicationMessage", "Utente eliminato con successo");
    }

    /* registrazione con dati strani */
    @Test
    public void testRegistrazioneConDatiStrani() throws Exception{
        /* parametri per la registrazione con dati strani */
        when(requestUtenteConDatiStrani.getParameter("email")).thenReturn("email");
        when(requestUtenteConDatiStrani.getParameter("nome")).thenReturn("Nome");
        when(requestUtenteConDatiStrani.getParameter("cognome")).thenReturn("Cognome");
        when(requestUtenteConDatiStrani.getParameter("sesso")).thenReturn("S");
        when(requestUtenteConDatiStrani.getParameter("data")).thenReturn("1999-12-12");
        when(requestUtenteConDatiStrani.getParameter("cellulare")).thenReturn("cellulare");
        when(requestUtenteConDatiStrani.getParameter("password")).thenReturn(null);
        when(requestUtenteConDatiStrani.getParameter("isAdmin")).thenReturn("true");

        Exception exception = assertThrows(Exception.class, () -> {
            HomeManagement.registrazione(requestUtenteConDatiStrani, responseUtenteConDatiStrani);
        });
    }
}
