package model.dao.mySQLJDBCImpl;

import model.dao.TestataOrdineDAO;
import model.mo.TestataOrdine;
import model.mo.Utente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestataOrdineDAOMySQLJDBCImpl implements TestataOrdineDAO {

    private final String COUNTER_ID = "idContatore";
    Connection conn;

    public TestataOrdineDAOMySQLJDBCImpl(Connection conn){ this.conn = conn; }


    @Override
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
            boolean annullato) {


        PreparedStatement ps;
        TestataOrdine testataOrdine = new TestataOrdine();
        testataOrdine.setUtente(utente);
        testataOrdine.setNome(nome);
        testataOrdine.setCognome(cognome);
        testataOrdine.setIndirizzoSpedizione(indirizzoSpedizione);
        testataOrdine.setCodicePostale(codicePostale);
        testataOrdine.setCitta(citta);
        testataOrdine.setProvincia(provincia);
        testataOrdine.setRegione(regione);
        testataOrdine.setDataOrdine(dataOrdine);

        try{

            String sql
                    = " UPDATE \"contatore\" "
                    + " SET \"contatore\" = \"contatore\" + 1 "
                    + " WHERE \"idContatore\" = '" + COUNTER_ID + "' ";
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();

            sql
                    = " SELECT \"contatore\" "
                    + " FROM \"contatore\" "
                    + " WHERE \"idContatore\" = '" + COUNTER_ID + "' ";
            ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            testataOrdine.setIdTestataOrdine(resultSet.getLong("contatore"));
            resultSet.close();

            String sql1
                    = " INSERT INTO \"testata_ordine\" "
                    + " ( \"idTestataOrdine\", "
                    + "   \"emailUtente\", "
                    + "   \"nome\", "
                    + "   \"cognome\", "
                    + "   \"indirizzo\", "
                    + "   \"codicePostale\", "
                    + "   \"citta\", "
                    + "   \"provincia\", "
                    + "   \"regione\", "
                    + "   \"dataOrdine\", "
                    + "   \"statoSpedizione\", "
                    + "   \"annullato\" "
                    + " ) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,'e in fase di elaborazione','N')";

            ps = conn.prepareStatement(sql1);
            ps.setLong(1, testataOrdine.getIdTestataOrdine());
            ps.setString(2, testataOrdine.getUtente().getEmail());
            ps.setString(3, testataOrdine.getNome());
            ps.setString(4, testataOrdine.getCognome());
            ps.setString(5, testataOrdine.getIndirizzoSpedizione());
            ps.setString(6, testataOrdine.getCodicePostale());
            ps.setString(7, testataOrdine.getCitta());
            ps.setString(8, testataOrdine.getProvincia());
            ps.setString(9, testataOrdine.getRegione());
            ps.setDate(10, testataOrdine.getDataOrdine());

            ps.executeUpdate();


            ps.close();


        }catch (SQLException e){
            throw new RuntimeException(e);
        }



        return testataOrdine;
    }

    @Override
    public List<TestataOrdine> listAllTestataOrdineByEmail(Utente utente){
        ArrayList<TestataOrdine> listTestataOrdine = new ArrayList<TestataOrdine>();
        TestataOrdine testataOrdine;
        PreparedStatement ps;

        try{
            String sql
                    = " SELECT * "
                    + " FROM \"testata_ordine\" "
                    + " WHERE \"emailUtente\" = ? "
                    + " ORDER BY \"idTestataOrdine\" DESC";
            ps = conn.prepareStatement(sql);
            ps.setString(1, utente.getEmail());
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                testataOrdine = read(resultSet);
                testataOrdine.setUtente(utente);
                listTestataOrdine.add(testataOrdine);
            }
            resultSet.close();
            ps.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return listTestataOrdine;
    }

    @Override
    public TestataOrdine getById(Long idTestataOrdine) {
        TestataOrdine testataOrdine = new TestataOrdine();
        PreparedStatement ps;

        try{
            String sql
                    = " SELECT * "
                    + " FROM \"testata_ordine\" "
                    + " WHERE \"idTestataOrdine\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, idTestataOrdine);

            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                testataOrdine = read(resultSet);
            }
            resultSet.close();
            ps.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }


        return testataOrdine;
    }

    @Override
    public List<TestataOrdine> getAllListForAdmin() {
        ArrayList<TestataOrdine> testataOrdineArrayList = new ArrayList<TestataOrdine>();
        TestataOrdine testataOrdine = new TestataOrdine();
        PreparedStatement ps;

        try{
            String sql
                    = " SELECT * "
                    + " FROM \"testata_ordine\" INNER JOIN \"utente\" ON \"emailUtente\"=\"email\" "
                    + " ORDER BY \"idTestataOrdine\" DESC ";
            ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                testataOrdine = readWithUtente(resultSet);
                testataOrdineArrayList.add(testataOrdine);
            }
            resultSet.close();
            ps.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }


        return testataOrdineArrayList;
    }

    @Override
    public void blockTestataOrdine(TestataOrdine testataOrdine) {
        PreparedStatement ps;

        try{
            String sql
                    = " UPDATE testata_ordine "
                    + " SET \"annullato\" = 'Y' "
                    + " WHERE \"idTestataOrdine\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, testataOrdine.getIdTestataOrdine());
            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unblockTestataOrdine(TestataOrdine testataOrdine) {
        PreparedStatement ps;

        try{
            String sql
                    = " UPDATE \"testata_ordine\" "
                    + " SET \"annullato\" = 'N' "
                    + " WHERE \"idTestataOrdine\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, testataOrdine.getIdTestataOrdine());
            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setConsegnato(TestataOrdine testataOrdine) {
        PreparedStatement ps;

        try{
            String sql
                    = " UPDATE \"testata_ordine\" "
                    + " SET \"consegnato\" = 'Y', "
                    + " \"statoSpedizione\" = 'e stato consegnato' "
                    + " WHERE \"idTestataOrdine\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, testataOrdine.getIdTestataOrdine());
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setStatoSpedizione(TestataOrdine testataOrdine, String statoSpedizione) {
        PreparedStatement ps;

        try{
            String sql
                    = " UPDATE \"testata_ordine\" "
                    + " SET \"statoSpedizione\" = ? "
                    + " WHERE \"idTestataOrdine\" = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, statoSpedizione);
            ps.setLong(2, testataOrdine.getIdTestataOrdine());
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    TestataOrdine read(ResultSet resultSet){
        TestataOrdine testataOrdine = new TestataOrdine();
        Utente utente = new Utente();
        try{
            testataOrdine.setIdTestataOrdine(resultSet.getLong("idTestataOrdine"));
        } catch (SQLException e) {
        }
        try{
            utente.setEmail(resultSet.getString("emailUtente"));
            testataOrdine.setUtente(utente);
        } catch (SQLException e) {
        }
        try{
            testataOrdine.setNome(resultSet.getString("nome"));
        } catch (SQLException e) {
        }
        try{
            testataOrdine.setCognome(resultSet.getString("cognome"));
        } catch (SQLException e) {
        }
        try{
            testataOrdine.setIndirizzoSpedizione(resultSet.getString("indirizzo"));
        }catch(SQLException e){
        }
        try{
            testataOrdine.setCodicePostale(resultSet.getString("codicePostale"));
        }catch(SQLException e){
        }
        try{
            testataOrdine.setCitta(resultSet.getString("citta"));
        }catch(SQLException e){
        }
        try{
            testataOrdine.setProvincia(resultSet.getString("provincia"));
        }catch(SQLException e){
        }
        try{
            testataOrdine.setRegione(resultSet.getString("regione"));
        }catch(SQLException e){
        }
        try{
            testataOrdine.setDataOrdine(resultSet.getDate("dataOrdine"));
        }catch(SQLException e){
        }
        try{
            testataOrdine.setStatoSpedizione(resultSet.getString("statoSpedizione"));
        }catch(SQLException e){
        }
        try{
            testataOrdine.setAnnullato(resultSet.getString("annullato").equals("Y"));
        }catch(SQLException e){
        }
        try{
            testataOrdine.setConsegnato(resultSet.getString("consegnato").equals("Y"));
        }catch(SQLException e){
        }

        return testataOrdine;
    }

    TestataOrdine readWithUtente(ResultSet resultSet){
        TestataOrdine testataOrdine = new TestataOrdine();
        Utente utente = new Utente();
        try{
            testataOrdine.setIdTestataOrdine(resultSet.getLong("idTestataOrdine"));
        } catch (SQLException e) {
        }
        try{
            testataOrdine.setNome(resultSet.getString("nome"));
        } catch (SQLException e) {
        }
        try{
            testataOrdine.setCognome(resultSet.getString("cognome"));
        } catch (SQLException e) {
        }
        try{
            testataOrdine.setIndirizzoSpedizione(resultSet.getString("indirizzo"));
        }catch(SQLException e){
        }
        try{
            testataOrdine.setCodicePostale(resultSet.getString("codicePostale"));
        }catch(SQLException e){
        }
        try{
            testataOrdine.setCitta(resultSet.getString("citta"));
        }catch(SQLException e){
        }
        try{
            testataOrdine.setProvincia(resultSet.getString("provincia"));
        }catch(SQLException e){
        }
        try{
            testataOrdine.setRegione(resultSet.getString("regione"));
        }catch(SQLException e){
        }
        try{
            testataOrdine.setDataOrdine(resultSet.getDate("dataOrdine"));
        }catch(SQLException e){
        }
        try{
            testataOrdine.setStatoSpedizione(resultSet.getString("statoSpedizione"));
        }catch(SQLException e){
        }
        try{
            testataOrdine.setAnnullato(resultSet.getString("annullato").equals("Y"));
        }catch(SQLException e){
        }
        try{
            testataOrdine.setConsegnato(resultSet.getString("consegnato").equals("Y"));
        }catch(SQLException e){
        }
        try{
            utente.setEmail(resultSet.getString("email"));
        }catch(SQLException e){
        }
        try{
            utente.setNome(resultSet.getString("nome"));
        }catch(SQLException e){
        }
        try{
            utente.setCognome(resultSet.getString("cognome"));
        }catch(SQLException e){
        }
        try{
            utente.setNome(resultSet.getString("nome"));
        }catch(SQLException e){
        }
        try{
            utente.setSesso(resultSet.getString("sesso"));
        }catch(SQLException e){
        }
        try{
            utente.setCellulare(resultSet.getString("cellulare"));
        }catch(SQLException e){
        }
        try{
            utente.setNome(resultSet.getString("nome"));
        }catch(SQLException e){
        }
        testataOrdine.setUtente(utente);

        return testataOrdine;
    }
}
