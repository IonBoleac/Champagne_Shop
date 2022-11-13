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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CatalogoManagement {
    private CatalogoManagement(){}

    public static void view(HttpServletRequest request, HttpServletResponse response) {
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;

        String annata = null;
        List<String> annate = null;
        String searchString = null;

        List<Prodotto> prodotti = null;

        Logger logger = LogService.getApplicationLogger();

        try {
            Map sessionFactoryParameters = new HashMap<String, Object>();
            sessionFactoryParameters.put("request", request);
            sessionFactoryParameters.put("response", response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            loggedUser = sessionUserDAO.findLoggedUser();

            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            daoFactory.beginTransaction();

            UtenteDAO userDAO = daoFactory.getUtenteDAO();
            ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();

            annata = (String) request.getParameter("annata");
            String searchFlag = (String) request.getParameter("searchFlag");
            searchString = (String) request.getParameter("searchString");

            if(searchString != null){
                prodotti = prodottoDAO.findBySearchString(searchString);
                if(prodotti.size() == 0){
                    applicationMessage = "Prodotto non trovato";
                    prodotti = prodottoDAO.findAllProductForCatalogo();
                }
            }else{
                if(annata == null || annata.equals("*")){
                    prodotti = prodottoDAO.findAllProductForCatalogo();
                }
                else {
                    prodotti = prodottoDAO.findByAnnata(annata);
                }
            }

            annate = prodottoDAO.findAllAnnate();

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("annate", annate);
            request.setAttribute("prodotti", prodotti);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "catalogoManagement/view");

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

    public static void dettagliProdotto(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;

        List<Prodotto> prodotti;
        Prodotto prodotto;

        Logger logger = LogService.getApplicationLogger();

        try {
            Map sessionFactoryParameters = new HashMap<String, Object>();
            sessionFactoryParameters.put("request", request);
            sessionFactoryParameters.put("response", response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            loggedUser = sessionUserDAO.findLoggedUser();

            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            daoFactory.beginTransaction();

            UtenteDAO userDAO = daoFactory.getUtenteDAO();
            ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();

            String idProdotto = request.getParameter("idProdotto");
            prodotto = prodottoDAO.findById(idProdotto);

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("prodotto", prodotto);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "catalogoManagement/singoloProdotto");

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
}
