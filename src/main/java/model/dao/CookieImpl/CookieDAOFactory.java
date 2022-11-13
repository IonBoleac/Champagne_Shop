package model.dao.CookieImpl;

import model.dao.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class CookieDAOFactory extends DAOFactory {

  /**
   *
   */
  private Map factoryParameters;


  

  private HttpServletRequest request;
  private HttpServletResponse response;

  public CookieDAOFactory(Map factoryParameters) {
      this.factoryParameters=factoryParameters;
  }

  @Override
  public void beginTransaction() {

    try {
      this.request=(HttpServletRequest) factoryParameters.get("request");
      this.response=(HttpServletResponse) factoryParameters.get("response");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void commitTransaction() {}

  @Override
  public void rollbackTransaction() {}

  @Override
  public void closeTransaction() {}

  /*@Override
  public UserDAO getUserDAO() {
    return new UserDAOCookieImpl(request,response);
  }

  @Override
  public ContactDAO getContactDAO() {
    throw new UnsupportedOperationException("Not supported yet.");
  }*/

  @Override
  public UtenteDAO getUtenteDAO() {
    return new UtenteDAOCookieImpl(request,response);
  }

  @Override
  public ProdottoDAO getProdottoDAO() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public TestataOrdineDAO getTestataOrdineDAO() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public RigaOrdineDAO getRigaOrdineDAO() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public CarrelloDAO getCarelloDAO() {
    throw new UnsupportedOperationException("Not supported yet.");
  }


}