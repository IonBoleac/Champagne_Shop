package controller;

import model.dao.DAOFactory;
import model.dao.ProdottoDAO;
import model.dao.UtenteDAO;
import model.mo.Prodotto;
import model.mo.Utente;
import services.config.Configuration;
import services.logservice.LogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdottiManagement {
    private ProdottiManagement(){
    }

    public static void view(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;

        List<Prodotto> prodotti;

        Logger logger = LogService.getApplicationLogger();
        try{
            Map sessionFactoryParameters = new HashMap<String, Object>();
            sessionFactoryParameters.put("request", request);
            sessionFactoryParameters.put("response", response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            loggedUser = sessionUserDAO.findLoggedUser();

            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            daoFactory.beginTransaction();

            ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();

            prodotti = prodottoDAO.getAllProduct();


            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("prodotti", prodotti);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "prodottiManagement/view");
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

    public static void prodotto(HttpServletRequest request, HttpServletResponse response){

        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;
        String idProduct = null;
        Prodotto prodotto = null;

        Logger logger = LogService.getApplicationLogger();

        try{

            Map sessionFactoryParameters = new HashMap<String, Object>();
            sessionFactoryParameters.put("request", request);
            sessionFactoryParameters.put("response", response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            loggedUser = sessionUserDAO.findLoggedUser();

            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            daoFactory.beginTransaction();

            ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
            idProduct = (String) request.getParameter("idProduct");
            if (!idProduct.equals("0")){
                prodotto = prodottoDAO.findById(idProduct);
            }


            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("prodotto", prodotto);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "prodottiManagement/prodotto");

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

    public static void insNewModProduct(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;
        String idProduct = null;
        Prodotto prodotto = null;
        List<Prodotto> prodotti;

        Logger logger = LogService.getApplicationLogger();

        try{
            Map sessionFactoryParameters = new HashMap<String, Object>();
            sessionFactoryParameters.put("request", request);
            sessionFactoryParameters.put("response", response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            loggedUser = sessionUserDAO.findLoggedUser();

            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            daoFactory.beginTransaction();

            ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();

            long idProdotto = Long.parseLong((String) request.getParameter("idProdotto"));
            String nomeProdotto = (String) request.getParameter("nomeProdotto");
            String string = (String) request.getParameter("prezzoUnitario");
            int prezzoUnitario = Integer.parseInt(string);
            String urlImg = (String) request.getParameter("urlImg");
            int quantita = Integer.parseInt((String) request.getParameter("quantita"));
            String descrizione = (String) request.getParameter("descrizione");
            boolean vetrina = (boolean) request.getParameter("vetrina").equals("Y");
            String annata = (String) request.getParameter("annata");

            if(idProdotto == 0){ /* creazione nuovo prodotto */
                urlImg = System.currentTimeMillis() + ".jpg";
                prodottoDAO.create(idProdotto,nomeProdotto,prezzoUnitario,urlImg,quantita,descrizione,vetrina,false,false,annata);
                applicationMessage = "Prodotto creato con successo";
            }else{ /* modifica di un prodotto */
                prodottoDAO.modifica(idProdotto,nomeProdotto,prezzoUnitario,urlImg,quantita,descrizione,vetrina,false,false,annata);
                applicationMessage = "Prodotto modificato con successo";
            }

            prodotti = prodottoDAO.findAllProductForCatalogo();

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("prodotti", prodotti);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "prodottiManagement/view");

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

    public static void deleteProdotto(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;
        Prodotto prodotto = null;
        List<Prodotto> prodotti;

        Logger logger = LogService.getApplicationLogger();

        try{
            Map sessionFactoryParameters = new HashMap<String, Object>();
            sessionFactoryParameters.put("request", request);
            sessionFactoryParameters.put("response", response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            loggedUser = sessionUserDAO.findLoggedUser();

            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            daoFactory.beginTransaction();

            long idProduct = Long.parseLong(request.getParameter("idProduct"));
            ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
            prodottoDAO.delete(idProduct);

            prodotti = prodottoDAO.getAllProduct();


            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("prodotti", prodotti);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "prodottiManagement/view");

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

    public static void blockUnblockProduct(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;

        List<Prodotto> prodotti;

        Logger logger = LogService.getApplicationLogger();

        try{
            Map sessionFactoryParameters = new HashMap<String, Object>();
            sessionFactoryParameters.put("request", request);
            sessionFactoryParameters.put("response", response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            loggedUser = sessionUserDAO.findLoggedUser();

            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            daoFactory.beginTransaction();

            ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();

            long idProduct = Long.parseLong((String) request.getParameter("idProdotto"));
            boolean status = (boolean) request.getParameter("status").equals("bloccare");
            prodottoDAO.blockUnblockProduct(idProduct, status);

            prodotti = prodottoDAO.getAllProduct();

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("prodotti", prodotti);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "prodottiManagement/view");

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
