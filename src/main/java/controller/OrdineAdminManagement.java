package controller;

import model.dao.DAOFactory;
import model.dao.RigaOrdineDAO;
import model.dao.TestataOrdineDAO;
import model.dao.UtenteDAO;
import model.mo.RigaOrdine;
import model.mo.TestataOrdine;
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

public class OrdineAdminManagement {
    private OrdineAdminManagement(){}

    public static void view(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;
        Logger logger = LogService.getApplicationLogger();


        List<TestataOrdine> testataOrdineList;

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

            TestataOrdineDAO testataOrdineDAO = daoFactory.getTestataOrdineDAO();
            UtenteDAO utenteDAO = daoFactory.getUtenteDAO();


            testataOrdineList = testataOrdineDAO.getAllListForAdmin();
            /*for (int i = 0; i < testataOrdineList.size(); i++) {
                Utente utente = utenteDAO.findByEmail(testataOrdineList.get(i).getUtente().getEmail());
                testataOrdineList.get(i).setUtente(utente);
            }*/

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("testataOrdineList", testataOrdineList);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "ordineAdminManagement/view");


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

    public static void blockUnblockTestataOrdine(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;
        Logger logger = LogService.getApplicationLogger();


        List<TestataOrdine> testataOrdineList;

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

            String state = (String) request.getParameter("state");
            String idTestataOrdine = (String) request.getParameter("idTestataOrdine");

            TestataOrdineDAO testataOrdineDAO = daoFactory.getTestataOrdineDAO();
            UtenteDAO utenteDAO = daoFactory.getUtenteDAO();

            TestataOrdine testataOrdine = testataOrdineDAO.getById(Long.parseLong(idTestataOrdine));

            if(state.equals("unblock")){
                testataOrdineDAO.unblockTestataOrdine(testataOrdine);
            }else{
                testataOrdineDAO.blockTestataOrdine(testataOrdine);
            }

            testataOrdineList = testataOrdineDAO.getAllListForAdmin();
            for (int i = 0; i < testataOrdineList.size(); i++) {
                Utente utente = utenteDAO.findByEmail(testataOrdineList.get(i).getUtente().getEmail());
                testataOrdineList.get(i).setUtente(utente);
            }

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("testataOrdineList", testataOrdineList);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "ordineAdminManagement/view");


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

    public static void setCosegnato(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;
        Logger logger = LogService.getApplicationLogger();


        List<TestataOrdine> testataOrdineList;

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

            String idTestataOrdine = (String) request.getParameter("idTestataOrdine");
            String setConsegnato = (String) request.getParameter("setConsegnato");

            TestataOrdineDAO testataOrdineDAO = daoFactory.getTestataOrdineDAO();
            UtenteDAO utenteDAO = daoFactory.getUtenteDAO();

            TestataOrdine testataOrdine = testataOrdineDAO.getById(Long.parseLong(idTestataOrdine));
            Utente utente = utenteDAO.findByEmail(testataOrdine.getUtente().getEmail());
            testataOrdine.setUtente(utente);

            testataOrdineDAO.setConsegnato(testataOrdine);

            testataOrdineList = testataOrdineDAO.getAllListForAdmin();
            for (int i = 0; i < testataOrdineList.size(); i++) {
                utente = utenteDAO.findByEmail(testataOrdineList.get(i).getUtente().getEmail());
                testataOrdineList.get(i).setUtente(utente);
            }

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("testataOrdineList", testataOrdineList);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "ordineAdminManagement/view");


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

    public static void viewSetStatoSpedizione(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory = null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;
        Logger logger = LogService.getApplicationLogger();

        List<TestataOrdine> testataOrdineList;

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

            String idTestataOrdine = (String) request.getParameter("idTestataOrdine");

            TestataOrdineDAO testataOrdineDAO = daoFactory.getTestataOrdineDAO();
            UtenteDAO utenteDAO = daoFactory.getUtenteDAO();
            RigaOrdineDAO rigaOrdineDAO = daoFactory.getRigaOrdineDAO();

            TestataOrdine testataOrdine = testataOrdineDAO.getById(Long.parseLong(idTestataOrdine));

            testataOrdine.setUtente(utenteDAO.findByEmail(testataOrdine.getUtente().getEmail()));
            //testataOrdine.setRigaOrdine((ArrayList<RigaOrdine>) rigaOrdineDAO.getRigaOrdineByIdTestataOrdine(testataOrdine));

            ArrayList<String> stringheStato = new ArrayList<String>();

            stringheStato.add("e stato preso in carico");
            stringheStato.add("e stato consegnato al corriere");
            stringheStato.add("e in viaggio");
            stringheStato.add("e in consegna");
            stringheStato.add("e stato consegnato");

            if(testataOrdine.getStatoSpedizione().equals("e in fase di elaborazione")){
                testataOrdineDAO.setStatoSpedizione(testataOrdine, stringheStato.get(0));
            }

            if(testataOrdine.getStatoSpedizione().equals("e stato preso in carico")){
                testataOrdineDAO.setStatoSpedizione(testataOrdine, stringheStato.get(1));
            }

            if(testataOrdine.getStatoSpedizione().equals("e stato consegnato al corriere")){
                testataOrdineDAO.setStatoSpedizione(testataOrdine, stringheStato.get(2));
            }
            if(testataOrdine.getStatoSpedizione().equals("e in viaggio")){
                testataOrdineDAO.setStatoSpedizione(testataOrdine, stringheStato.get(3));
            }

            if(testataOrdine.getStatoSpedizione().equals("e in consegna")){
                testataOrdineDAO.setConsegnato(testataOrdine);
            }

            testataOrdineList = testataOrdineDAO.getAllListForAdmin();
            for (int i = 0; i < testataOrdineList.size(); i++) {
                Utente utente = utenteDAO.findByEmail(testataOrdineList.get(i).getUtente().getEmail());
                testataOrdineList.get(i).setUtente(utente);
            }

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("testataOrdine", testataOrdine);
            request.setAttribute("stringheStato", stringheStato);
            request.setAttribute("testataOrdineList", testataOrdineList);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "ordineAdminManagement/view");
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
