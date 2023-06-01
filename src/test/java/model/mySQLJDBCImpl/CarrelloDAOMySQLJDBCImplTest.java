package model.mySQLJDBCImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.mo.Carrello;
import model.mo.Prodotto;
import model.mo.Utente;
import model.dao.CarrelloDAO;
import model.dao.DAOFactory;
import model.dao.exception.DuplicatedObjectException;

import services.config.Configuration;

import org.junit.jupiter.api.DisplayName;



public class CarrelloDAOMySQLJDBCImplTest {

    static Utente utente;
    static Prodotto prodotto;

    
    @BeforeAll
    static void init() {
        System.out.println("CarrelloDAOMySQLJDBCImplTest");

        
    }

    @Test
    @DisplayName("Test addProdottoNelCarrello con utente e prodotto esistenti")
    void testAddProdottoNelCarrello() throws DuplicatedObjectException{
        utente = new Utente();
        prodotto = new Prodotto();
        Carrello carrello;
        DAOFactory daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
        assert daoFactory != null;
        daoFactory.beginTransaction();

        CarrelloDAO carrelloDAO = daoFactory.getCarelloDAO();

        utente.setEmail("ion.boleac@gmail.com");
        prodotto.setIdProdotto(Long.valueOf(1));

        int quantita = carrelloDAO.getQuantitaProdottoNelCarrello(utente, prodotto);


        carrelloDAO.addProdottoNelCarrello(utente, prodotto, 2, 200);


        int quantita1 = carrelloDAO.getQuantitaProdottoNelCarrello(utente, prodotto);

        assertEquals(quantita + 2, quantita1);

        

        daoFactory.rollbackTransaction();
        daoFactory.closeTransaction();
    }

    @Test
    @DisplayName("Test addProdottoNelCarrello con utente e prodotto non esistenti")
    void testAddProdottoNelCarrello2() throws DuplicatedObjectException{
        utente = new Utente();
        prodotto = new Prodotto();
        Carrello carrello;
        DAOFactory daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
        assert daoFactory != null;
        daoFactory.beginTransaction();

        CarrelloDAO carrelloDAO = daoFactory.getCarelloDAO();

        utente.setEmail("email@gmail.com");
        prodotto.setIdProdotto(Long.valueOf(100));

        java.lang.RuntimeException thrown = assertThrows(
                java.lang.RuntimeException.class,
                () -> carrelloDAO.addProdottoNelCarrello(utente, prodotto, 2, 200),
                "Expected addProdottoNelCarrello() to throw, but it didn't"
        );

        //carrelloDAO.addProdottoNelCarrello(utente, prodotto, 2, 200),

        daoFactory.rollbackTransaction();
        daoFactory.closeTransaction();
    }

    @Test
    @DisplayName("Test getQuantitaProdottoNelCarrello con utente e prodotto esistenti")
    void testGetQuantitaProdottoNelCarrello() throws DuplicatedObjectException{
        utente = new Utente();
        prodotto = new Prodotto();
        Carrello carrello;
        DAOFactory daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
        assert daoFactory != null;
        daoFactory.beginTransaction();

        CarrelloDAO carrelloDAO = daoFactory.getCarelloDAO();

        utente.setEmail("email@gmail.com");
    }
}
