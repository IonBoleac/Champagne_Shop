package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import model.mo.Utente;

import static org.mockito.Mockito.*;

public class HomeManagementTest {
    
    @Mock
    static HttpServletRequest request;
    static HttpServletRequest requestNotLogged;
    static HttpServletResponse response;
    static HttpSession sessionMock;
    static HttpSession sessionMockNotLogged;

    static Utente utente;


    @BeforeAll
    public static void setUp(){
        utente = new Utente();
        utente.setNome("nome");
        utente.setCognome("cognome");
        utente.setEmail("ion.boleac@gmail.com");
        utente.setPassword("qwe");
        utente.setAdmin(true);

        response = mock(HttpServletResponse.class);

        sessionMock = mock(HttpSession.class);
        sessionMockNotLogged = mock(HttpSession.class);

        request = mock(HttpServletRequest.class);
        requestNotLogged = mock(HttpServletRequest.class);

        when(request.getSession(true)).thenReturn(sessionMock);
        when(request.getParameter("email")).thenReturn("ion.boleac@gmail.com");
        when(request.getParameter("password")).thenReturn("qwe");
        when(request.getAttribute("loggedOn")).thenReturn(true);

        when(requestNotLogged.getSession(true)).thenReturn(sessionMockNotLogged);

        when(sessionMock.getAttribute("loggedUser")).thenReturn(utente);
        
        when(sessionMockNotLogged.getAttribute("loggedUser")).thenReturn(null);
    }

    @Test
    public void testViewHomeManagement() throws Exception {
        HomeManagement.view(request, response);
        verify(request, atLeast(1)).setAttribute("loggedOn", true);
        verify(requestNotLogged, atLeast(1)).setAttribute("viewUrl", "homeManagement/view");
    }

    @Test
    public void testViewHomeManagementNotLogged() throws Exception {
        HomeManagement.view(requestNotLogged, response);
        verify(requestNotLogged, atLeast(1)).setAttribute("loggedOn", false);
        verify(requestNotLogged, atLeast(1)).setAttribute("viewUrl", "homeManagement/view");
    }

    @Test 
    public void testLogon() throws Exception {
        HomeManagement.logon(request, response);
        verify(request, atLeast(1)).setAttribute("loggedOn", true);
        verify(request, atLeast(1)).setAttribute("viewUrl", "homeManagement/view");
    }

}
