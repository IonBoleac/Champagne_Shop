package model.dao;

import model.mo.Prodotto;
import model.mo.RigaOrdine;
import model.mo.TestataOrdine;

import java.util.List;

public interface RigaOrdineDAO {

    public RigaOrdine create(
            Long idRigaOrdine,
            int quantita,
            int prezzo,
            boolean deleted,
            TestataOrdine testataOrdine,
            Prodotto prodotto
    );

    public List<RigaOrdine> getRigaOrdineByIdTestataOrdine(TestataOrdine testataOrdine);

}
