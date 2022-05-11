package controller;

import model.dao.*;
import model.mo.Prodotto;
import model.mo.RigaOrdine;
import model.mo.TestataOrdine;
import model.mo.Utente;
import services.config.Configuration;
import services.logservice.LogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdineManagement {
    private OrdineManagement(){
    }

    public static void compilareOrdine(HttpServletRequest request, HttpServletResponse response){
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


            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "ordineManagement/formOrdine");
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

    public static void effettuaOrdine(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;
        List<Map> carrello;
        List<RigaOrdine> listRigaOrdine;
        List<TestataOrdine> listTestataOrdine;

        List<Prodotto> prodottiVetrina;


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

            UtenteDAO utenteDAO = daoFactory.getUtenteDAO();
            ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
            TestataOrdineDAO testataOrdineDAO = daoFactory.getTestataOrdineDAO();
            RigaOrdineDAO rigaOrdineDAO = daoFactory.getRigaOrdineDAO();

            Utente utente = utenteDAO.findByEmail(loggedUser.getEmail());
            utente = utenteDAO.getCarrelloByUtente(utente);

            String nome = (String) request.getParameter("nome");
            String cognome = (String) request.getParameter("cognome");
            String indirizzoSpedizione = (String) request.getParameter("indirizzoSpedizione");
            String cap = (String) request.getParameter("cap");
            String citta = (String) request.getParameter("citta");
            String provincia = (String) request.getParameter("provincia");
            String regione = (String) request.getParameter("regione");

            long millis=System.currentTimeMillis();
            Date dataOrdine = new java.sql.Date(millis);

            TestataOrdine testataOrdine = testataOrdineDAO.create(null,utente,nome,cognome,indirizzoSpedizione,cap,citta,provincia,regione,dataOrdine,null,false);


            for (int k = 0; k< utente.getCarello().size(); k++){
                String email = (String) utente.getCarello().get(k).get("email");
                int quantita = Integer.parseInt((String) utente.getCarello().get(k).get("quantita"));
                int prezzo = Integer.parseInt((String) utente.getCarello().get(k).get("prezzo"));
                String idProdotto = (String) utente.getCarello().get(k).get("idProdotto");
                Prodotto prodotto = prodottoDAO.findById(idProdotto);
                rigaOrdineDAO.create(null,quantita,prezzo,false,testataOrdine,prodotto);
                utenteDAO.eliminaProdottoDaCarrello(email, idProdotto);
            }

            /*listTestataOrdine = testataOrdineDAO.listAllTestataOrdineByEmail(utente);
            for (int i = 0; i < listTestataOrdine.size(); i++) {
                listRigaOrdine = rigaOrdineDAO.getRigaOrdineByIdTestataOrdine(listTestataOrdine.get(i));
                for (int j = 0; j < listRigaOrdine.size(); j++) {
                    listTestataOrdine.get(i).setRigaOrdine(j, listRigaOrdine.get(j));
                }
            }*/

            prodottiVetrina = prodottoDAO.getVetrina();


            applicationMessage = "Ordine effettuato con successo";

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("prodottiVetrina", prodottiVetrina);
            //request.setAttribute("listTestataOrdine", listTestataOrdine);
            request.setAttribute("applicationMessage", applicationMessage);
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

    public static void view(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser = null;
        String applicationMessage = null;
        List<Map> carrello = null;
        List<RigaOrdine> listRigaOrdine = null;
        List<TestataOrdine> listTestataOrdine = null;
        Prodotto prodotto = null;
        ArrayList<RigaOrdine> ordineArrayList = new ArrayList<RigaOrdine>();

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
            ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
            TestataOrdineDAO testataOrdineDAO = daoFactory.getTestataOrdineDAO();;
            RigaOrdineDAO rigaOrdineDAO = daoFactory.getRigaOrdineDAO();

            Utente utente = utenteDAO.findByEmail(loggedUser.getEmail());
            listTestataOrdine = testataOrdineDAO.listAllTestataOrdineByEmail(utente);

            for (int i = 0; i < listTestataOrdine.size(); i++) {
                listRigaOrdine = rigaOrdineDAO.getRigaOrdineByIdTestataOrdine(listTestataOrdine.get(i));
                for (int j = 0; j < listRigaOrdine.size(); j++) {
                    prodotto = prodottoDAO.findById("" + listRigaOrdine.get(j).getProdotto().getIdProdotto());
                    listRigaOrdine.get(j).setProdotto(prodotto);
                    ordineArrayList.add(j, listRigaOrdine.get(j));
                }
                listTestataOrdine.get(i).setRigaOrdine(ordineArrayList);
            }


            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("listTestataOrdine", listTestataOrdine);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "ordineManagement/view");


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

    public static void listaProdotti(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser = null;
        String applicationMessage = null;

        List<RigaOrdine> rigaOrdineArrayList;
        ArrayList<Prodotto> prodottoArrayList = new ArrayList<Prodotto>();
        Prodotto prodotto;

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

            Long idTestataOrdine = Long.parseLong(request.getParameter("idTestataOrdine"));

            TestataOrdine testataOrdine;

            RigaOrdineDAO rigaOrdineDAO = daoFactory.getRigaOrdineDAO();
            ProdottoDAO prodottoDAO = daoFactory.getProdottoDAO();
            TestataOrdineDAO testataOrdineDAO = daoFactory.getTestataOrdineDAO();

            testataOrdine = testataOrdineDAO.getById(idTestataOrdine);

            rigaOrdineArrayList = rigaOrdineDAO.getRigaOrdineByIdTestataOrdine(testataOrdine);
            for (int i = 0; i < rigaOrdineArrayList.size(); i++) {
                prodotto = prodottoDAO.findById(""+rigaOrdineArrayList.get(i).getProdotto().getIdProdotto());
                rigaOrdineArrayList.get(i).setProdotto(prodotto);
                prodottoArrayList.add(prodotto);
            }



            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("rigaOrdineArrayList", rigaOrdineArrayList);
            request.setAttribute("testataOrdine", testataOrdine);
            request.setAttribute("prodottoArrayList", prodottoArrayList);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "ordineManagement/viewDettagliOrdine");
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
