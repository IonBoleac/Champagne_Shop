package model.dao;

import model.dao.exception.DuplicatedObjectException;
import model.mo.Prodotto;

import java.util.List;

public interface ProdottoDAO {

    public Prodotto create(
            Long idProdotto,
            String nomeProdotto,
            int prezzoUnitario,
            String urlImage,
            int quantita,
            String descrizione,
            boolean vetrina,
            boolean blocked,
            boolean deleted,
            String annata
    ) throws DuplicatedObjectException;

    public Prodotto findByName(String nome);

    public void delete(Long idProduct);

    public List<String> findAllProductName();

    /* public List<Prodotto> findBySearchString(String searchString); */

    public List<Prodotto> findAllProductForCatalogo();

    public List<Prodotto> getAllProduct();

    public Prodotto findById(String idProduct);

    public void modifica(Long idProdotto,
                         String nomeProdotto,
                         int prezzoUnitario,
                         String urlImage,
                         int quantita,
                         String descrizione,
                         boolean vetrina,
                         boolean blocked,
                         boolean deleted,
                         String annata);

    public void blockUnblockProduct(Long idProduct, boolean status);

    public void modificaQuantita(Prodotto prodotto, int quantita);

    public List<Prodotto> findByAnnata(String annata);

    public List<String> findAllAnnate();

    public List<Prodotto> findBySearchString(String nome);

    public int getQuantita(Prodotto prodotto);

    public List<Prodotto> getVetrina();
}
