package model.dao.mySQLJDBCImpl;

import model.dao.RigaOrdineDAO;
import model.mo.Prodotto;
import model.mo.RigaOrdine;
import model.mo.TestataOrdine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RigaOrdineDAOMySQLJDBCImpl implements RigaOrdineDAO {
    Connection conn;

    public RigaOrdineDAOMySQLJDBCImpl(Connection conn){
        this.conn = conn;
    }

    @Override
    public RigaOrdine create(
            Long idRigaOrdine,
            int quantita,
            int prezzo,
            boolean deleted,
            TestataOrdine testataOrdine,
            Prodotto prodotto) {
        PreparedStatement ps;
        RigaOrdine rigaOrdine = new RigaOrdine();
        rigaOrdine.setQuantita(quantita);
        rigaOrdine.setPrezzo(prezzo);
        rigaOrdine.setTestataOrdine(testataOrdine);
        rigaOrdine.setProdotto(prodotto);

        try{
            String sql
                    = " INSERT INTO \"riga_ordine\" "
                    + " ( \"quantita\", "
                    + "   \"prezzo\", "
                    + "   \"annullato\", "
                    + "   \"idTestataOrdine\", "
                    + "   \"idProdotto\" "
                    + " ) "
                    + " VALUES (?,?,'N',?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,rigaOrdine.getQuantita());
            ps.setInt(2, rigaOrdine.getPrezzo());
            ps.setLong(3, rigaOrdine.getTestataOrdine().getIdTestataOrdine());
            ps.setLong(4, rigaOrdine.getProdotto().getIdProdotto());
            ps.executeUpdate();
            ps.close();


        }catch (SQLException e){
            throw new RuntimeException(e);
        }


        return rigaOrdine;
    }

    @Override
    public List<RigaOrdine> getRigaOrdineByIdTestataOrdine(TestataOrdine testataOrdine) {
        ArrayList<RigaOrdine> list = new ArrayList<RigaOrdine>();
        RigaOrdine rigaOrdine;
        PreparedStatement ps;

        try{
            String sql
                    = " SELECT * "
                    + " FROM \"riga_ordine\" "
                    + " WHERE \"idTestataOrdine\" = ? AND \"annullato\" = 'N' ";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, testataOrdine.getIdTestataOrdine());

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                rigaOrdine = read(resultSet, testataOrdine);
                list.add(rigaOrdine);
            }
            resultSet.close();
            ps.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }

    RigaOrdine read(ResultSet resultSet, TestataOrdine testataOrdine){
        RigaOrdine rigaOrdine = new RigaOrdine();
        rigaOrdine.setTestataOrdine(testataOrdine);
        Prodotto prodotto = new Prodotto();

        try {
            rigaOrdine.setIdRigaOrdine(resultSet.getLong("idRigaOrdine"));
        } catch (SQLException e) {
        }
        try {
            rigaOrdine.setQuantita(resultSet.getInt("quantita"));
        } catch (SQLException e) {
        }
        try {
            rigaOrdine.setPrezzo(resultSet.getInt("prezzo"));
        } catch (SQLException e) {
        }
        try {
            rigaOrdine.setDeleted(resultSet.getString("annullato").equals("Y"));
        } catch (SQLException e) {
        }
        try {
            rigaOrdine.setArrivato(resultSet.getString("arrivato").equals("Y"));
        } catch (SQLException e) {
        }
        try {
            Long idProdotto = resultSet.getLong("idProdotto");
            prodotto.setIdProdotto(idProdotto);
            rigaOrdine.setProdotto(prodotto);
        } catch (SQLException e) {
        }



        return rigaOrdine;
    }

}
