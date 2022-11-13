package model.dao.mySQLJDBCImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.CarrelloDAO;
import model.mo.Carrello;
import model.mo.Prodotto;
import model.mo.Utente;

public class CarrelloDAOMySQLJDBCImpl implements CarrelloDAO{

    Connection conn;

    CarrelloDAOMySQLJDBCImpl(Connection conn){
        this.conn = conn;
    }

    @Override
    public Carrello addProductToCarello(
            String email, 
            long idProdotto, 
            String nomeProdotto, 
            int quantita, 
            int prezzo,
            boolean deleted
        ) 
        {
        
        Carrello carello = new Carrello();
        carello.setEmail(email);
        carello.setIdProdotto(idProdotto);
        carello.setNomeProdotto(nomeProdotto);
        carello.setQuantita(quantita);
        carello.setPrezzo(prezzo);
        carello.setDeleted(deleted);

        try {
            String sql 
                    = "INSERT INTO \"carrello\" "
                    + "(\"email\", "
                    + "\"idProdotto\", "
                    + "\"nomeProdotto\", "
                    + "\"quantita\", "
                    + "\"prezzo\", "
                    + "\"deleted\") "
                    + " VALUES (?,?,?,?,?,?) ";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, carello.getEmail());
            ps.setLong(2, carello.getIdProdotto());
            ps.setString(3, carello.getNomeProdotto());
            ps.setInt(4, carello.getQuantita());
            ps.setInt(5, carello.getPrezzo());
            ps.setBoolean(6, carello.isDeleted());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return carello;
    }

    @Override
    public List<Carrello> findCarrelloByUtente(Utente utente) {
        PreparedStatement ps;
        List<Carrello> carello = new ArrayList<>();

        try {
            String sql 
                    = "SELECT * "
                    + "FROM \"carrello\" "
                    + "WHERE \"email\" = ? ";

            ps = conn.prepareStatement(sql);
            ps.setString(1, utente.getEmail());
            ResultSet resultSet = ps.executeQuery();

            /* leggi il resultSet */
            while (resultSet.next()) {
                Carrello c = new Carrello();
                c.setEmail(resultSet.getString("email"));
                c.setIdProdotto(resultSet.getLong("idProdotto"));
                c.setNomeProdotto(resultSet.getString("nomeProdotto"));
                c.setQuantita(resultSet.getInt("quantita"));
                c.setPrezzo(resultSet.getInt("prezzo"));
                c.setDeleted(resultSet.getBoolean("deleted"));
                carello.add(c);
            }
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return carello;
    }
    
    @Override
    public void addProdottoNelCarrello(Utente utente, Prodotto prodotto, int quantita, int prezzo) {
        PreparedStatement ps;
        String email = utente.getEmail();

        try{
            String sql
                    = " SELECT * "
                    + " FROM \"carrello\" "
                    + " WHERE \"email\" = ? AND \"idProdotto\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setLong(2, prodotto.getIdProdotto());
            ResultSet resultSet = ps.executeQuery();
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
    public int getQuantitaProdottoNelCarrello(Utente utente, Prodotto prodotto) {
        PreparedStatement ps;
        int quantita = 0;

        try {
            String sql
                    = " SELECT \"quantita\" "
                    + " FROM \"carrello\" "
                    + " WHERE \"idProdotto\" = ? "
                    + " AND \"email\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, prodotto.getIdProdotto());
            ps.setString(2, utente.getEmail());
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
    public void eliminaProdottoDaCarrello(Utente utente, Prodotto prodotto) {
        PreparedStatement ps;

        try{
            String sql
                    = " DELETE "
                    + " FROM \"carrello\" "
                    + " WHERE "
                    + " \"email\" = ? AND \"idProdotto\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,utente.getEmail());
            ps.setLong(2, prodotto.getIdProdotto());
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        
    }
}
