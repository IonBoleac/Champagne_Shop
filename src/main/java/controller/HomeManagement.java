package controller;

import model.dao.DAOFactory;
import model.dao.ProdottoDAO;
import model.dao.UtenteDAO;
import model.dao.exception.DuplicatedObjectException;
import model.mo.Prodotto;
import model.mo.Utente;
import services.config.Configuration;
import services.logservice.LogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeManagement {

  private HomeManagement() {
  }

  public static void view(HttpServletRequest request, HttpServletResponse response) {

    DAOFactory sessionDAOFactory= null;
    DAOFactory daoFactory = null;
    Utente loggedUser;
    String applicationMessage=null;

    Logger logger = LogService.getApplicationLogger();

    List<Prodotto> prodottiVetrina;
    
    try {

      Map<String,Object> sessionFactoryParameters=new HashMap<String,Object>();
      sessionFactoryParameters.put("request",request);
      sessionFactoryParameters.put("response",response);
      sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
      sessionDAOFactory.beginTransaction();

      daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
      daoFactory.beginTransaction();

      UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
      loggedUser = sessionUserDAO.findLoggedUser();

      ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
      prodottiVetrina = prodottoDAO.getVetrina();

      sessionDAOFactory.commitTransaction();
      daoFactory.commitTransaction();


      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("prodottiVetrina", prodottiVetrina);
      request.setAttribute("applicationMessage", applicationMessage);
      request.setAttribute("viewUrl", "homeManagement/view");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
      try {
        if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
      } catch (Throwable t) {
      }
      throw new RuntimeException(e);

    } finally {
      try {
        if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
      } catch (Throwable t) {
      }
    }

  }

  public static void logon(HttpServletRequest request, HttpServletResponse response) {

    DAOFactory sessionDAOFactory= null;
    DAOFactory daoFactory = null;
    Utente loggedUser;
    String applicationMessage = null;

    Logger logger = LogService.getApplicationLogger();
    List<Prodotto> prodottiVetrina;
    
    try {

      Map<String,Object> sessionFactoryParameters=new HashMap<String,Object>();
      sessionFactoryParameters.put("request",request);
      sessionFactoryParameters.put("response",response);
      sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
      sessionDAOFactory.beginTransaction();

      UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
      loggedUser = sessionUserDAO.findLoggedUser();

      daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
      daoFactory.beginTransaction();

      ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
      prodottiVetrina = prodottoDAO.getVetrina();

      String email = request.getParameter("email");
      String password = request.getParameter("password");

      UtenteDAO userDAO = daoFactory.getUtenteDAO();
      Utente user = userDAO.findByEmail(email);
      String forTest = null;

      if (user == null || !user.getPassword().equals(password)) {
        sessionUserDAO.delete(null);
        applicationMessage = "Username e password errati!";
        loggedUser=null;
        forTest = "Utente non trovato";
      } else {
        loggedUser = sessionUserDAO.create(user.getEmail(), user.getNome(), user.getCognome(), null, null, null, null, user.isAdmin(), false, false);
        forTest = "Utente trovato";
      }

      daoFactory.commitTransaction();
      sessionDAOFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("prodottiVetrina", prodottiVetrina);
      request.setAttribute("applicationMessage", applicationMessage);
      request.setAttribute("forTest", forTest);
      request.setAttribute("viewUrl", "homeManagement/view");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
      try {
        if (daoFactory != null) daoFactory.rollbackTransaction();
        if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
      } catch (Throwable t) {
      }
      throw new RuntimeException(e);

    } finally {
      try {
        if (daoFactory != null) daoFactory.closeTransaction();
        if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
      } catch (Throwable t) {
      }
    }

  }

  public static void logout(HttpServletRequest request, HttpServletResponse response) {

    DAOFactory sessionDAOFactory= null;
    
    Logger logger = LogService.getApplicationLogger();

    List<Prodotto> prodottiVetrina;
    DAOFactory daoFactory = null;

    try {

      Map<String,Object> sessionFactoryParameters=new HashMap<String,Object>();
      sessionFactoryParameters.put("request",request);
      sessionFactoryParameters.put("response",response);
      sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
      sessionDAOFactory.beginTransaction();

      daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
      daoFactory.beginTransaction();

      ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
      prodottiVetrina = prodottoDAO.getVetrina();

      UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
      sessionUserDAO.delete(null);

      sessionDAOFactory.commitTransaction();

      request.setAttribute("loggedOn",false);
      request.setAttribute("loggedUser", null);
      request.setAttribute("prodottiVetrina", prodottiVetrina);
      request.setAttribute("viewUrl", "homeManagement/view");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
      try {
        if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
      } catch (Throwable t) {
      }
      throw new RuntimeException(e);

    } finally {
      try {
        if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
      } catch (Throwable t) {
      }
    }
  }

  public static void regView(HttpServletRequest request, HttpServletResponse response){
    DAOFactory sessionDAOFactory= null;
    DAOFactory daoFactory = null;
    Utente loggedUser;
    String applicationMessage = null;

    Logger logger = LogService.getApplicationLogger();

    try{
      Map<String,Object> sessionFactoryParameters=new HashMap<String,Object>();
      sessionFactoryParameters.put("request",request);
      sessionFactoryParameters.put("response",response);
      sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
      sessionDAOFactory.beginTransaction();

      UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
      loggedUser = sessionUserDAO.findLoggedUser();

      sessionDAOFactory.commitTransaction();

      if(loggedUser != null){
        request.setAttribute("loggedOn",loggedUser!=null);
        request.setAttribute("loggedUser", loggedUser);
      }
      else{
        request.setAttribute("loggedOn",false);
        request.setAttribute("loggedUser", null);
      }


      request.setAttribute("applicationMessage", applicationMessage);
      request.setAttribute("viewUrl", "homeManagement/registrazione");

    }catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
      try {
        if (daoFactory != null) daoFactory.rollbackTransaction();
        if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
      } catch (Throwable t) {
      }
      throw new RuntimeException(e);

    } finally {
      try {
        if (daoFactory != null) daoFactory.closeTransaction();
        if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
      } catch (Throwable t) {
      }
    }
  }

  public static void registrazione(HttpServletRequest request, HttpServletResponse response){
    DAOFactory sessionDAOFactory= null;
    DAOFactory daoFactory = null;
    Utente loggedUser;
    String applicationMessage = null;

    List<Prodotto> prodottiVetrina;


    Logger logger = LogService.getApplicationLogger();

    try {

      Map<String,Object> sessionFactoryParameters=new HashMap<String,Object>();
      sessionFactoryParameters.put("request",request);
      sessionFactoryParameters.put("response",response);
      sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
      sessionDAOFactory.beginTransaction();

      UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
      loggedUser = sessionUserDAO.findLoggedUser();

      daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
      daoFactory.beginTransaction();

      String email = (String) request.getParameter("email");
      String nome = (String) request.getParameter("nome");
      String cognome = (String) request.getParameter("cognome");
      String sesso = (String) request.getParameter("sesso");
      Date data = Date.valueOf(request.getParameter("data"));
      String cellulare = (String) request.getParameter("cellulare");
      String password =(String) request.getParameter("password");
      boolean isAdmin = (boolean) request.getParameter("isAdmin").equals("true");

      UtenteDAO userDAO = daoFactory.getUtenteDAO();

      try{
        userDAO.create(email, nome, cognome, sesso, password, data, cellulare, isAdmin, false, false);
        applicationMessage = "Registrazione avvenuta con successo";
      }catch(DuplicatedObjectException e){
        applicationMessage = "Utente già esistente";
        logger.log(Level.INFO, "Tentativo di inserimento di utente già esistente");
      }

      ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
      prodottiVetrina = prodottoDAO.getVetrina();



      daoFactory.commitTransaction();
      sessionDAOFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("applicationMessage", applicationMessage);
      request.setAttribute("prodottiVetrina", prodottiVetrina);
      request.setAttribute("viewUrl", "homeManagement/view");

    }catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
      try {
        if (daoFactory != null) daoFactory.rollbackTransaction();
        if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
      } catch (Throwable t) {
      }
      throw new RuntimeException(e);

    } finally {
      try {
        if (daoFactory != null) daoFactory.closeTransaction();
        if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
      } catch (Throwable t) {
      }
    }

  }

  public static void deleteUtente(HttpServletRequest request, HttpServletResponse response){
    DAOFactory sessionDAOFactory= null;
    DAOFactory daoFactory = null;
    Utente loggedUser;
    String applicationMessage = null;

    List<Prodotto> prodottiVetrina;


    Logger logger = LogService.getApplicationLogger();
    try{
      Map<String,Object> sessionFactoryParameters=new HashMap<String,Object>();
      sessionFactoryParameters.put("request",request);
      sessionFactoryParameters.put("response",response);
      sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
      sessionDAOFactory.beginTransaction();

      UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
      loggedUser = sessionUserDAO.findLoggedUser();
      sessionUserDAO.delete(null);

      daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
      daoFactory.beginTransaction();

      UtenteDAO userDAO = daoFactory.getUtenteDAO();

      userDAO.delete(loggedUser);

      ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
      prodottiVetrina = prodottoDAO.getVetrina();

      applicationMessage = "Utente eliminato con successo";

      daoFactory.commitTransaction();
      sessionDAOFactory.commitTransaction();

      request.setAttribute("loggedOn",false);
      request.setAttribute("loggedUser", null);
      request.setAttribute("applicationMessage", applicationMessage);
      request.setAttribute("prodottiVetrina", prodottiVetrina);
      request.setAttribute("viewUrl", "homeManagement/view");
    }catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
      try {
        if (daoFactory != null) daoFactory.rollbackTransaction();
        if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
      } catch (Throwable t) {
      }
      throw new RuntimeException(e);

    } finally {
      try {
        if (daoFactory != null) daoFactory.closeTransaction();
        if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
      } catch (Throwable t) {
      }
    }
  }

}
