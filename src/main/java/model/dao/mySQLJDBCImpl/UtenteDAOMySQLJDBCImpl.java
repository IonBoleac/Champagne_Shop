package model.dao.mySQLJDBCImpl;

import model.dao.UtenteDAO;
import model.dao.exception.DuplicatedObjectException;
import model.mo.Prodotto;
import model.mo.Utente;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtenteDAOMySQLJDBCImpl implements UtenteDAO {
    Connection conn;

    public UtenteDAOMySQLJDBCImpl(Connection conn){
        this.conn = conn;
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
            boolean isAdmin,
            boolean deleted,
            boolean isBlocked) throws DuplicatedObjectException {

        PreparedStatement ps;
        Utente utente = new Utente();
        utente.setEmail(email);
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setSesso(sesso);
        utente.setPassword(password);
        utente.setAnnoNascita(annoNascita);
        utente.setCellulare(cellulare);
        utente.setAdmin(isAdmin);
        utente.setDeleted(deleted);
        utente.setBlocked(isBlocked);

        try{
            String sql
                    = " SELECT * "
                    + " FROM \"utente\" "
                    + " WHERE "
                    + " \"email\" = ? AND \"isBlocked\" = 'N' ";

            ps = conn.prepareStatement(sql);
            ps.setString(1, utente.getEmail());

            ResultSet resultSet = ps.executeQuery();

            boolean exist = false;
            Utente utente1 = new Utente();
            if (resultSet.next()) {
                utente1 = read(resultSet);
                exist = true;
            }
            resultSet.close();

            if(exist && !utente1.isDeleted()){
                throw  new DuplicatedObjectException("UtenteDAOMySQLJDBCImpl.create: Tentativo di creazione di un utente già presenteUtente già");
            }
            if(!exist){
                sql
                        = " INSERT INTO \"utente\" "
                        + " ( \"email\", "
                        + "   \"nome\", "
                        + "   \"cognome\", "
                        + "   \"sesso\","
                        + "   \"password\","
                        + "   \"annoNascita\","
                        + "   \"cellulare\","
                        + "   \"isAdmin\", "
                        + "   \"deleted\","
                        + "   \"isBlocked\" )"
                        + "VALUES (?,?,?,?,?,?,?,?,'N','N')";

                ps = conn.prepareStatement(sql);
                int i = 1;
                ps.setString(i++, utente.getEmail());
                ps.setString(i++, utente.getNome());
                ps.setString(i++, utente.getCognome());
                ps.setString(i++, utente.getSesso());
                ps.setString(i++, utente.getPassword());
                ps.setDate(i++, utente.getAnnoNascita());
                ps.setString(i++, utente.getCellulare());

                if(utente.isAdmin()){
                    ps.setString(i++,"Y");
                }else{
                    ps.setString(i++,"N");
                }

                ps.executeUpdate();
                ps.close();
            }

            if(exist && utente1.isDeleted()){
                sql
                        = " UPDATE \"utente\" "
                        + " SET "
                        + " \"nome\" = ?, "
                        + " \"cognome\" = ?, "
                        + " \"sesso\" = ?, "
                        + " \"password\" = ?, "
                        + " \"annoNascita\" = ?, "
                        + " \"cellulare\" = ?, "
                        + " \"isAdmin\" = ?, "
                        + " \"deleted\" = 'N',"
                        + " \"isBlocked\" = 'N' "
                        + " WHERE \"email\" = ? ";
                ps = conn.prepareStatement(sql);
                int i = 1;
                ps.setString(i++, utente.getNome());
                ps.setString(i++, utente.getCognome());
                ps.setString(i++, utente.getSesso());
                ps.setString(i++, utente.getPassword());
                ps.setDate(i++, utente.getAnnoNascita());
                ps.setString(i++, utente.getCellulare());

                if(utente.isAdmin()){
                    ps.setString(i++,"Y");
                }else{
                    ps.setString(i++,"N");
                }
                ps.setString(i++, utente.getEmail());

                ps.executeUpdate();
                ps.close();
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utente;
    }

    @Override
    public Utente findLoggedUser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Utente findByEmail(String email) {
        PreparedStatement ps;
        Utente utente = null;

        try {
            String sql
                    = " SELECT * "
                    + " FROM \"utente\" "
                    + " WHERE "
                    + " \"email\" = ? AND \"deleted\" = 'N' AND \"isBlocked\" = 'N' ";

            ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                utente = read(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return utente;
    }

    @Override
    public void update(Utente utente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    public Utente getCarrelloByUtente(Utente utente){
        PreparedStatement ps;
        ArrayList<Map> carello = new ArrayList<Map>();

        try{
            String sql
                    = " SELECT * "
                    + " FROM \"carrello\" "
                    + " WHERE "
                    + " \"email\" = ? AND \"deleted\" = 'N' ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, utente.getEmail());
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                Map tupla=new HashMap<String,String>();
                tupla.put("email", ""+resultSet.getString("email"));
                tupla.put("idProdotto", ""+resultSet.getLong("idProdotto"));
                tupla.put("nomeProdotto", ""+resultSet.getString("nomeProdotto"));
                tupla.put("quantita", ""+resultSet.getInt("quantita"));
                tupla.put("prezzo", ""+resultSet.getInt("prezzo"));
                carello.add(tupla);
            }
            resultSet.close();
            ps.close();
            utente.setCarello(carello);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return utente;
    }

    @Override
    public void eliminaProdottoDaCarrello(String email, String idProdotto){
        PreparedStatement ps;

        try{
            String sql
                    = " DELETE "
                    + " FROM \"carrello\" "
                    + " WHERE "
                    + " \"email\" = ? AND \"idProdotto\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,email);
            ps.setLong(2, Long.parseLong(idProdotto));
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addProdottoNelCarello(String email, Prodotto prodotto, int quantita, int prezzo) {
        PreparedStatement ps;

        try{
            String sql
                    = " SELECT * "
                    + " FROM \"carrello\" "
                    + " WHERE \"email\" = ? AND \"idProdotto\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setLong(2, prodotto.getIdProdotto());
            ResultSet resultSet = ps.executeQuery();
            ps.close();
            if(resultSet.next()){ /* prodotto già messo nel carrello. Aggiungere la quantità volut */
                String sql1
                        = " UPDATE \"carrello\" "
                        + " SET \"quantita\" = ?, \"prezzo\" = ? "
                        + " WHERE \"email\" = ? AND \"idProdotto\" = ? ";

                ps = conn.prepareStatement(sql1);
                ps.setInt(1, (quantita + resultSet.getInt("quantita")));
                ps.setInt(2, (prezzo + resultSet.getInt("prezzo")));
                ps.setString(3, email);
                ps.setLong(4, prodotto.getIdProdotto());
                ps.executeUpdate();

            }else{ /* aggiungere da zero nel carrello */
                String sql2
                        = " INSERT INTO \"carrello\" "
                        + " ( \"email\", "
                        + "   \"idProdotto\", "
                        + "   \"nomeProdotto\", "
                        + "   \"quantita\", "
                        + "   \"prezzo\") "
                        + " VALUES (?, ?, ?, ?, ?)";

                ps = conn.prepareStatement(sql2);
                ps.setString(1,email);
                ps.setLong(2, prodotto.getIdProdotto());
                ps.setString(3, prodotto.getNomeProdotto());
                ps.setInt(4,quantita);
                ps.setInt(5,prezzo);
                ps.executeUpdate();
            }

            resultSet.close();
            ps.close();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getQuantitaByIdProdotto(Prodotto prodotto) {
        PreparedStatement ps;
        int quantita = 0;

        try {
            String sql
                    = " SELECT \"quantita\" "
                    + " FROM \"carrello\" "
                    + " WHERE \"idProdotto\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, prodotto.getIdProdotto());
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                quantita = resultSet.getInt("quantita");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return quantita;
    }

    @Override
    public void delete(Utente user) {
        //throw new UnsupportedOperationException("Not supported yet.");
        PreparedStatement ps;
        Utente utente = null;
        try{
            utente = findByEmail(user.getEmail());
            if(utente != null){
                String sql
                        = " UPDATE \"utente\" "
                        + " SET "
                        + " \"deleted\" = 'Y'"
                        + " WHERE "
                        + " \"email\" = ? ";
                ps = conn.prepareStatement(sql);
                ps.setString(1,user.getEmail());
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Utente updatePassword(String email, String password) {
        Utente utente = null;
        PreparedStatement ps;

        try{
            utente = findByEmail(email);
            if(utente != null){
                String sql
                        = " UPDATE \"utente\" "
                        + " SET "
                        + " \"password\" = ? "
                        + " WHERE "
                        + " \"email\" = ? and \"deleted\" = 'N' AND \"isBlocked\" = 'N'";
                ps = conn.prepareStatement(sql);
                int i = 1;
                ps.setString(i++, password);
                ps.setString(i++, utente.getEmail());
                ps.executeUpdate();
                ps.close();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utente;
    }

    @Override
    public List<Utente> getAllUtenti() {
        ArrayList<Utente> utenti = new ArrayList<Utente>();
        Utente utente;
        PreparedStatement ps;

        try{
            String sql
                    = " SELECT * "
                    + " FROM \"utente\" "
                    + " WHERE \"deleted\" = 'N' ";
            ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                utente = read(resultSet);
                utenti.add(utente);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return utenti;
    }

    @Override
    public void blockUtenteByEmail(String email) {
        PreparedStatement ps;
        try{
            String sql
                    = " UPDATE \"utente\" "
                    + " SET \"isBlocked\" = 'Y' "
                    + " WHERE "
                    + " \"email\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unblockUtenteByEmail(String email) {
        PreparedStatement ps;
        try{
            String sql
                    = " UPDATE \"utente\" "
                    + " SET \"isBlocked\" = 'N' "
                    + " WHERE "
                    + " \"email\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    Utente read(ResultSet rs){
        Utente utente = new Utente();
        try{
            utente.setEmail(rs.getString("email"));
        }catch(SQLException sqle){
        }
        try{
            utente.setNome(rs.getString("nome"));
        }catch(SQLException sqle){
        }
        try{
            utente.setCognome(rs.getString("cognome"));
        } catch (SQLException sqle) {
        }
        try{
            utente.setSesso(rs.getString("sesso"));
        } catch (SQLException sqle) {
        }
        try{
            utente.setPassword(rs.getString("password"));
        } catch (SQLException sqle) {
        }
        try{
            utente.setAnnoNascita(rs.getDate("annoNascita"));
        } catch (SQLException sqle) {
        }
        try{
            utente.setCellulare(rs.getString("cellulare"));
        } catch (SQLException sqle) {
        }
        try{
            utente.setAdmin(rs.getString("isAdmin").equals("Y"));
        } catch (SQLException sqle) {
        }
        try{
            utente.setDeleted(rs.getString("deleted").equals("Y"));
        } catch (SQLException sqle) {
        }
        try{
            utente.setBlocked(rs.getString("isBlocked").equals("Y"));
        }catch (SQLException sqle) {
        }
        return utente;
    }

    Prodotto readProdotto(ResultSet rs){
        Prodotto prodotto = new Prodotto();
        try{
            prodotto.setIdProdotto(rs.getLong("idProdotto"));
        }catch(SQLException sqle){
        }
        try{
            prodotto.setNomeProdotto(rs.getString("nomeProdotto"));
        }catch(SQLException sqle){
        }
        try{
            prodotto.setPrezzoUnitario(rs.getInt("prezzoUnitario"));
        }catch(SQLException sqle){
        }
        try{
            prodotto.setUrlImage(rs.getString("urlImage"));
        }catch(SQLException sqle){
        }
        try{
            prodotto.setQuantita(rs.getInt("quantita"));
        }catch(SQLException sqle){
        }
        try{
            prodotto.setDescrizione(rs.getString("descrizione"));
        }catch(SQLException sqle){
        }
        try{
            prodotto.setVetrina(rs.getString("vetrina").equals("Y"));
        }catch(SQLException sqle){
        }
        try{
            prodotto.setBlocked(rs.getString("blocked").equals("Y"));
        }catch(SQLException sqle){
        }
        try{
            prodotto.setAnnata(rs.getString("annata"));
        }catch(SQLException sqle){}
        try{
            prodotto.setDeleted(rs.getString("deleted").equals("Y"));
        }catch(SQLException sqle){}

        return prodotto;
    }
}
