package controller;

import model.dao.CarrelloDAO;
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

public class CarrelloManagement {
    private CarrelloManagement(){}

    public static void view(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;

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

            UtenteDAO utenteDAO = daoFactory.getUtenteDAO();
            CarrelloDAO carelloDAO = daoFactory.getCarelloDAO();
            Utente utente = utenteDAO.findByEmail(loggedUser.getEmail());
            /* utente = utenteDAO.getCarrelloByUtente(utente); */
            utente.setCarello_CarelloList(carelloDAO.findCarrelloByUtente(utente));


            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("utente", utente);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "carrelloManagement/view");

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

    public static void deleteProdottoDaCarrello(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;

        List<Map> carrello;
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

            String email = request.getParameter("email");
            String idProdotto = request.getParameter("idProdotto");

            UtenteDAO utenteDAO = daoFactory.getUtenteDAO();
            ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
            CarrelloDAO carelloDAO = daoFactory.getCarelloDAO();

            Prodotto prodotto = prodottoDAO.findById(idProdotto);

            /* int quantitaCarello = utenteDAO.getQuantitaByIdProdotto(prodotto); */
            int quantitaCarello = carelloDAO.getQuantitaProdottoNelCarrello(loggedUser, prodotto);
            prodottoDAO.modificaQuantita(prodotto, quantitaCarello + prodotto.getQuantita());

            /* elimina prodotto da carrello */
            /* utenteDAO.eliminaProdottoDaCarrello(email, idProdotto); */
            carelloDAO.eliminaProdottoDaCarrello(loggedUser, prodotto);

            
            Utente utente = utenteDAO.findByEmail(loggedUser.getEmail());
            utente.setCarello_CarelloList(carelloDAO.findCarrelloByUtente(utente));


            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("utente", utente);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "carrelloManagement/view");

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

    public static void addProdottoNelCarrelloMethod(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;

        Logger logger = LogService.getApplicationLogger();

        List<Prodotto> prodotti = null;
        List<String> annate = null;

        String viewUrl = null;

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

            String idProdotto = (String) request.getParameter("idProdotto");
            String email = (String) request.getParameter("email");
            int quantita = Integer.parseInt(request.getParameter("quantita"));

            UtenteDAO utenteDAO = daoFactory.getUtenteDAO();
            ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
            CarrelloDAO carelloDAO = daoFactory.getCarelloDAO();

            Prodotto prodotto = prodottoDAO.findById(idProdotto);
            
            annate = prodottoDAO.findAllAnnate();

            if(quantita > prodotto.getQuantita()){
                applicationMessage = "Quantita' richiesta non disponibile nel magazzino";
                viewUrl = "catalogoManagement/singoloProdotto";
            }else{
                int prezzo_tot = quantita * prodotto.getPrezzoUnitario();
                carelloDAO.addProdottoNelCarrello(loggedUser, prodotto, quantita, prezzo_tot);
                /* utenteDAO.addProdottoNelCarello(email, prodotto, quantita, prezzo_tot); */
                int quantitaIniziale = prodottoDAO.getQuantita(prodotto);
                prodottoDAO.modificaQuantita(prodotto, quantitaIniziale - quantita);
                viewUrl = "catalogoManagement/view";
            }
            prodotti = prodottoDAO.findAllProductForCatalogo();

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("prodotto", prodotto);
            request.setAttribute("prodotti", prodotti);
            request.setAttribute("annate", annate);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", viewUrl);

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
