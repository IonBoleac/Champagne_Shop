package model.dao.mySQLJDBCImpl;

import model.dao.*;
import services.config.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class MySQLJDBCDAOFactory extends DAOFactory {

  private Map factoryParameters;

  private Connection connection;

  public MySQLJDBCDAOFactory(Map factoryParameters) {
    this.factoryParameters=factoryParameters;
  }

  @Override
  public void beginTransaction() {

    try {
      Class.forName(Configuration.DATABASE_DRIVER);
      this.connection = DriverManager.getConnection(Configuration.DATABASE_URL);
      this.connection.setAutoCommit(false);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void commitTransaction() {
    try {
      this.connection.commit();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void rollbackTransaction() {

    try {
      this.connection.rollback();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void closeTransaction() {
    try {
      this.connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public UtenteDAO getUtenteDAO() {
    return new UtenteDAOMySQLJDBCImpl(connection);
  }

  @Override
  public ProdottoDAO getProdottoDAO() {
    return new ProdottoDAOMySQLJDBCImpl(connection);
  }

  @Override
  public TestataOrdineDAO getTestataOrdineDAO() {
    return new TestataOrdineDAOMySQLJDBCImpl(connection);
  }

  @Override
  public RigaOrdineDAO getRigaOrdineDAO() {
    return new RigaOrdineDAOMySQLJDBCImpl(connection);
  }

  @Override
  public CarrelloDAO getCarelloDAO() {
    return new CarrelloDAOMySQLJDBCImpl(connection);
  }


  /*
  @Override
  public UserDAO getUserDAO() {
    return new UserDAOMySQLJDBCImpl(connection);
  }

  @Override
  public ContactDAO getContactDAO() {
    return new ContactDAOMySQLJDBCImpl(connection);
  }
  */


}