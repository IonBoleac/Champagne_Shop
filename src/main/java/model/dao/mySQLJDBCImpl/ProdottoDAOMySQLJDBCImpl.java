package model.dao.mySQLJDBCImpl;

import model.dao.ProdottoDAO;
import model.dao.exception.DuplicatedObjectException;
import model.mo.Prodotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAOMySQLJDBCImpl implements ProdottoDAO {
    Connection conn;

    public ProdottoDAOMySQLJDBCImpl(Connection conn){
        this.conn = conn;
    }

    @Override
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
            String annata) throws DuplicatedObjectException {
        PreparedStatement ps;
        Prodotto prodotto = new Prodotto();

        prodotto.setNomeProdotto(nomeProdotto);
        prodotto.setPrezzoUnitario(prezzoUnitario);
        prodotto.setUrlImage(urlImage);
        prodotto.setQuantita(quantita);
        prodotto.setDescrizione(descrizione);
        prodotto.setVetrina(vetrina);
        prodotto.setBlocked(blocked);
        prodotto.setDeleted(deleted);
        prodotto.setAnnata(annata);

        if(findByName(prodotto.getNomeProdotto()) != null){
            throw new DuplicatedObjectException("ProdottoDAOMySQLJDBCImpl: creazione di un prodotto già esistente");
        }

        try{
            String sql
                    = " INSERT INTO \"prodotto\" "
                    + " ( \"nomeProdotto\", "
                    + "   \"prezzoUnitario\", "
                    + "   \"urlImage\", "
                    + "   \"quantita\", "
                    + "   \"descrizione\", "
                    + "   \"vetrina\", "
                    + "   \"blocked\", "
                    + "   \"deleted\","
                     +"   \"annata\") "
                    + " VALUES (?,?,?,?,?,?,?,?,?) ";
            ps = conn.prepareStatement(sql);

            ps.setString(1, prodotto.getNomeProdotto());
            ps.setInt(2, prodotto.getPrezzoUnitario());
            ps.setString(3, prodotto.getUrlImage());
            ps.setInt(4, prodotto.getQuantita());
            ps.setString(5, prodotto.getDescrizione());
            if(prodotto.isVetrina()) {
                ps.setString(6, "Y");
            }
            else{
                ps.setString(6, "N");
            }
            ps.setString(7, "N");
            ps.setString(8, "N");
            ps.setString(9, prodotto.getAnnata());

            int update = ps.executeUpdate();
            ps.close();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return prodotto;
    }

    @Override
    public Prodotto findByName(String nome) {

        PreparedStatement ps;
        Prodotto prodotto = null;

        try{
            String sql
                    = " SELECT * "
                    + " FROM \"prodotto\" "
                    + " WHERE "
                    + " \"nomeProdotto\" = ? AND \"blocked\" = 'N' AND \"deleted\" = 'N' ";

            ps = conn.prepareStatement(sql);
            ps.setString(1, nome);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                prodotto = read(resultSet);
            }
            resultSet.close();
            ps.close();

            return prodotto;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long idProduct) {
        PreparedStatement ps;

        try{
            String sql
                    = " UPDATE \"prodotto\" "
                    + " SET \"deleted\"='Y' "
                    + " WHERE "
                    + " \"idProdotto\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, idProduct);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> findAllProductName() {
        PreparedStatement ps;
        ArrayList<String> productName = new ArrayList<String>();
        String nome;

        try{
            String sql
                    = " SELECT \"nomeProdotto\" "
                    + " FROM \"prodotto\" "
                    + " WHERE "
                    + " \"blocked\" = 'N' AND \"deleted\" = 'N' ";
            ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                nome = resultSet.getString("nomeProdotto");
                productName.add(nome);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return productName;
    }


    /*
    @Override
    public List<Prodotto> findBySearchString(String searchString){
        PreparedStatement ps;
        ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
        Prodotto prodotto;

        try {
            String sql
                    = " SELECT * "
                    + " FROM prodotto "
                    + " WHERE "
                    + " ( blocked = 'N' ) AND ( quantita > 0 ) AND ( INSTR(nome,?)>0 ) ";
            ps = conn.prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();
            ps.close();

            if(resultSet.next()){
                prodotto = read(resultSet);
                prodotti.add(prodotto);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return prodotti;
    }
    */

    @Override
    public List<Prodotto> findAllProductForCatalogo() {
        ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
        Prodotto prodotto;
        PreparedStatement ps;

        try{
            String sql
                    = " SELECT * "
                    + " FROM \"prodotto\" "
                    + " WHERE "
                    + " \"blocked\" = 'N' AND \"deleted\" = 'N' ";
            ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();


            while(resultSet.next()){
                prodotto = read(resultSet);
                prodotti.add(prodotto);
            }
            resultSet.close();
            ps.close();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return prodotti;
    }

    @Override
    public List<Prodotto> getAllProduct() {
        PreparedStatement ps;
        ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
        Prodotto prodotto;

        try{
            String sql
                    = " SELECT * "
                    + " FROM \"prodotto\" "
                    + " WHERE \"deleted\" = 'N' ";

            ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                prodotto = read(resultSet);
                prodotti.add(prodotto);
            }
            ps.close();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return prodotti;
    }

    @Override
    public Prodotto findById(String idProduct) {
        Prodotto prodotto = null;
        PreparedStatement ps;

        try{
            String sql
                    = " SELECT * "
                    + " FROM \"prodotto\" "
                    + " WHERE "
                    + " \"idProdotto\" = ? AND \"blocked\" = 'N' ";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, Long.parseLong(idProduct));
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                prodotto = read(resultSet);
            }
            resultSet.close();
            ps.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return prodotto;
    }

    @Override
    public void modifica(Long idProdotto,
                         String nomeProdotto,
                         int prezzoUnitario,
                         String urlImage,
                         int quantita,
                         String descrizione,
                         boolean vetrina,
                         boolean blocked,
                         boolean deleted,
                         String annata) {
        PreparedStatement ps;

        try{
            String sql
                    = " UPDATE \"prodotto\" "
                    + " SET "
                    + " \"nomeProdotto\" = ?, "
                    + " \"prezzoUnitario\" = ?, "
                    + " \"urlImage\" = ?, "
                    + " \"quantita\" = ?, "
                    + " \"descrizione\" = ?, "
                    + " \"vetrina\" = ?, "
                    + " \"blocked\" = 'N', "
                    + " \"deleted\" = 'N', "
                    + " \"annata\" = ? "
                    + " WHERE "
                    + " \"idProdotto\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, nomeProdotto);
            ps.setInt(2, prezzoUnitario);
            ps.setString(3, urlImage);
            ps.setInt(4, quantita);
            ps.setString(5, descrizione);
            if(vetrina){
                ps.setString(6,"Y");
            }else{
                ps.setString(6, "N");
            }
            ps.setString(7, annata);
            ps.setLong(8,idProdotto);
            ps.executeUpdate();
            ps.close();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public void blockUnblockProduct(Long idProduct, boolean status){
        PreparedStatement ps;

        try{
            String sql
                    = " UPDATE \"prodotto\" "
                    + " SET "
                    + " \"blocked\" = ? "
                    + " WHERE "
                    + " \"idProdotto\" = ? ";
            ps = conn.prepareStatement(sql);
            if(status){
                ps.setString(1,"Y");
            }else{
                ps.setString(1,"N");
            }
            ps.setLong(2,idProduct);
            ps.executeUpdate();
            ps.close();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void modificaQuantita(Prodotto prodotto, int quantita) {
        PreparedStatement ps;

        try{
            String sql
                    = " UPDATE \"prodotto\" "
                    + " SET \"quantita\" = ? "
                    + " WHERE \"idProdotto\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, quantita);
            ps.setLong(2, prodotto.getIdProdotto());
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Prodotto> findByAnnata(String annata) {
        ArrayList<Prodotto> prodottoArrayList = new ArrayList<Prodotto>();
        Prodotto prodotto;
        PreparedStatement ps;

        try{
            String sql
                    = " SELECT * "
                    + " FROM \"prodotto\" "
                    + " WHERE \"annata\" = ? AND \"blocked\" = 'N' AND \"deleted\" = 'N' ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, annata);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                prodotto = read(resultSet);
                prodottoArrayList.add(prodotto);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }


        return prodottoArrayList;
    }

    @Override
    public List<String> findAllAnnate() {
        ArrayList<String> annate = new ArrayList<String>();
        PreparedStatement ps;

        try{
            String sql
                    = " SELECT DISTINCT \"annata\" "
                    + " FROM \"prodotto\" "
                    + " WHERE \"blocked\" = 'N' AND \"deleted\" = 'N' "
                    + " ORDER BY \"annata\" DESC ";
            ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                annate.add(resultSet.getString("annata"));
            }
            resultSet.close();
            ps.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return annate;
    }

    @Override
    public List<Prodotto> findBySearchString(String nome) {
        ArrayList<Prodotto> list = new ArrayList<Prodotto>(0);
        Prodotto prodotto;
        PreparedStatement ps;

        try {
            String sql
                    = " SELECT * "
                    + " FROM \"prodotto\" "
                    + " WHERE (POSITION(? IN \"nomeProdotto\") > 0) ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, nome);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                prodotto = read(resultSet);
                list.add(prodotto);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return  list;
    }

    @Override
    public int getQuantita(Prodotto prodotto) {
        int quantita = 0;
        PreparedStatement ps;

        try{
            String sql
                    = " SELECT \"quantita\" "
                    + " FROM \"prodotto\" "
                    + " WHERE \"idProdotto\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, prodotto.getIdProdotto());
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                quantita = resultSet.getInt("quantita");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return quantita;
    }

    @Override
    public List<Prodotto> getVetrina() {
        PreparedStatement ps;
        ArrayList<Prodotto> vetrina = new ArrayList<Prodotto>();
        Prodotto prodotto;

        try{
            String sql
                    = " SELECT * "
                    + " FROM \"prodotto\" "
                    + " WHERE \"vetrina\" = 'Y' AND \"deleted\" = 'N' ";

            ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                prodotto = read(resultSet);
                vetrina.add(prodotto);
            }

            resultSet.close();
            ps.close();


        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return vetrina;
    }

    Prodotto read(ResultSet rs){
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
