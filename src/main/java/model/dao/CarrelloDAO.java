package model.dao;

import model.mo.Carrello;
import model.mo.Prodotto;
import model.mo.Utente;
import java.util.*;

public interface CarrelloDAO {
    
    public Carrello addProductToCarello(
            String email,
            long idProdotto,
            String nomeProdotto,
            int quantita,
            int prezzo,
            boolean deleted
    );

    public List<Carrello> findCarrelloByUtente(Utente utente);
    public void addProdottoNelCarrello(Utente utente, Prodotto prodotto, int quantita, int prezzo);
    public int getQuantitaProdottoNelCarrello(Utente utente, Prodotto prodotto);
    public void eliminaProdottoDaCarrello(Utente utente, Prodotto prodotto);
}
