package model.dao.CookieImpl;

import model.dao.UtenteDAO;
import model.dao.exception.DuplicatedObjectException;
import model.mo.Prodotto;
import model.mo.Utente;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class UtenteDAOCookieImpl implements UtenteDAO {

    HttpServletRequest request;
    HttpServletResponse response;

    public UtenteDAOCookieImpl(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
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
            boolean isBlocked) throws DuplicatedObjectException {

        Utente loggedUtente = new Utente();
        loggedUtente.setEmail(email);
        loggedUtente.setNome(nome);
        loggedUtente.setCognome(cognome);
        loggedUtente.setAdmin(isAddmin);

        Cookie cookie;
        cookie = new Cookie("loggedUser", encode(loggedUtente));
        cookie.setPath("/");
        response.addCookie(cookie);

        return loggedUtente;
    }

    @Override
    public Utente findLoggedUser() {
        Cookie[] cookies = request.getCookies();
        Utente loggedUser = null;

        if (cookies != null) {
            for (int i = 0; i < cookies.length && loggedUser == null; i++) {
                if (cookies[i].getName().equals("loggedUser")) {
                    loggedUser = decode(cookies[i].getValue());
                }
            }
        }

        return loggedUser;
    }

    @Override
    public Utente findByEmail(String email) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void update(Utente loggedUser) {
        Cookie cookie;
        cookie = new Cookie("loggedUser", encode(loggedUser));
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public void delete(Utente loggedUser) {
        Cookie cookie;
        cookie = new Cookie("loggedUser", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public Utente updatePassword(String email, String password) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public List<Utente> getAllUtenti() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void blockUtenteByEmail(String utente) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void unblockUtenteByEmail(String email) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Utente getCarrelloByUtente(Utente utente) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void eliminaProdottoDaCarrello(String email, String idProdotto){
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void addProdottoNelCarello(String email, Prodotto prodotto, int quantita, int prezzo) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public int getQuantitaByIdProdotto(Prodotto prodotto) {
        throw new UnsupportedOperationException("Not supported");
    }


    private String encode(Utente loggedUtente) {
        return loggedUtente.getEmail() + "#" + loggedUtente.getNome() + "#" + loggedUtente.getCognome() + "#"+ loggedUtente.isAdmin();
    }

    private Utente decode(String encodedLoggedUser){
        Utente loggedUser = new Utente();

        String[] values = encodedLoggedUser.split("#");

        loggedUser.setEmail(values[0]);
        loggedUser.setNome(values[1]);
        loggedUser.setCognome(values[2]);
        loggedUser.setAdmin(values[3].equals("true"));

        return loggedUser;
    }
}
