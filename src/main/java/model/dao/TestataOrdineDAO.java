package model.dao;

import model.mo.TestataOrdine;
import model.mo.Utente;

import java.sql.Date;
import java.util.List;

public interface TestataOrdineDAO {

    public TestataOrdine create(
            Long idTestataOrdine,
            Utente utente,
            String nome,
            String cognome,
            String indirizzoSpedizione,
            String codicePostale,
            String citta,
            String provincia,
            String regione,
            Date dataOrdine,
            String statoSpedizione,
            boolean annullato
    );

    public List<TestataOrdine> listAllTestataOrdineByEmail(Utente utente);

    public TestataOrdine getById(Long idTestataOrdine);

    public List<TestataOrdine> getAllListForAdmin();

    public void blockTestataOrdine(TestataOrdine testataOrdine);

    public void unblockTestataOrdine(TestataOrdine testataOrdine);

    public void setConsegnato(TestataOrdine testataOrdine);

    public void setStatoSpedizione(TestataOrdine testataOrdine, String statoSpedizione);
}
