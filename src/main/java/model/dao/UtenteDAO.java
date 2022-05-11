package model.dao;

import model.dao.exception.DuplicatedObjectException;
import model.mo.Prodotto;
import model.mo.Utente;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface UtenteDAO {

    public Utente create(
            String email,
            String nome,
            String cognome,
            String sesso,
            String password,
            Date annoNascita,
            String cellulare,
            boolean isAddmin,
            boolean deleted,
            boolean isBlocked
    ) throws DuplicatedObjectException;

    public Utente findLoggedUser();

    public Utente findByEmail(String email);

    public void update(Utente utente);

    public void delete(Utente user);

    public Utente updatePassword(String email, String password);

    public List<Utente> getAllUtenti();

    public void blockUtenteByEmail(String email);

    public void unblockUtenteByEmail(String email);

    public Utente getCarrelloByUtente(Utente utente);

    public void eliminaProdottoDaCarrello(String email, String idProdotto);

    public void addProdottoNelCarello(String email, Prodotto prodotto, int quantita, int prezzo);

    public int getQuantitaByIdProdotto(Prodotto prodotto);
}
