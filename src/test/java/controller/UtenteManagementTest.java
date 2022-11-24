package controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import model.mo.Utente;
import services.classUtil;

public class UtenteManagementTest {
    @Mock
    static HttpServletRequest requestLogged;
    static HttpServletResponse responseLogged;

    static Utente utenteLoggato;
    static Cookie cookies;
    
    static int cont;

    @BeforeAll
    public static void setUp(){
        classUtil.println("---------UtenteManagementTest-------------");
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
        UtenteManagement.view(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestLogged, atLeast(1)).setAttribute("applicationMessage", "View tutti gli utenti");
    }

    /* test block utente */
    @Test
    public void testBlockUtente() throws Exception{
        String utenteDaBloccare = "silvia.boleac@gmail.com";
        when(requestLogged.getParameter("utenteToBlock")).thenReturn(utenteDaBloccare);
        UtenteManagement.blockUtente(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestLogged, atLeast(1)).setAttribute("applicationMessage", "Utente " + utenteDaBloccare + " bloccato con successo!!!");
    }

    /* test unblock utente */
    @Test
    public void testUnblockUtente() throws Exception{
        String utenteDaSbloccare = "silvia.boleac@gmail.com";
        when(requestLogged.getParameter("utenteToUnblock")).thenReturn(utenteDaSbloccare);
        UtenteManagement.unblockUtente(requestLogged, responseLogged);
        verify(requestLogged, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestLogged, atLeast(1)).setAttribute("applicationMessage", "Utente " + utenteDaSbloccare + " sbloccato con successo!!!");
    }

}
