package model.mySQLJDBCImpl;

import static org.junit.jupiter.api.Assertions.*;
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

        utente = new Utente();
        prodotto = new Prodotto();
    }

    @Test
    @DisplayName("Test addProdottoNelCarrello con utente e prodotto esistenti")
    void testAddProdottoNelCarrello() throws DuplicatedObjectException{
        Carrello carrello;
        DAOFactory daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
        assert daoFactory != null;
        daoFactory.beginTransaction();

        CarrelloDAO carrelloDAO = daoFactory.getCarelloDAO();

        utente.setEmail("ion.boleac@gmail.com");
        prodotto.setIdProdotto(Long.valueOf(1));


        carrelloDAO.addProdottoNelCarrello(utente, prodotto, 2, 200);

        daoFactory.rollbackTransaction();
        daoFactory.closeTransaction();
    }

    @Test
    @DisplayName("Test addProdottoNelCarrello con utente e prodotto non esistenti")
    void testAddProdottoNelCarrello2() throws DuplicatedObjectException{
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

        daoFactory.rollbackTransaction();
        daoFactory.closeTransaction();
    }
}
