package services.config;

import model.dao.DAOFactory;

import java.util.Calendar;
import java.util.logging.Level;

public class Configuration {
  
  /* Database Configruation */
  public static final String DAO_IMPL=DAOFactory.MYSQLJDBCIMPL;
  public static final String DATABASE_DRIVER="org.postgresql.Driver"; /* com.mysql.cj.jdbc.Driver */
  public static final String SERVER_TIMEZONE=Calendar.getInstance().getTimeZone().getID();
  public static final String 
    DATABASE_URL="jdbc:postgresql://localhost:5432/esame_riguzzi?user=root&password=qwe"; /*"jdbc:mysql://localhost/esame?user=root&password=qweasd&useSSL=false&serverTimezone="+SERVER_TIMEZONE; */
  
  /* Session Configuration */
  public static final String COOKIE_IMPL=DAOFactory.COOKIEIMPL;
  
  /* Logger Configuration */
  public static final String GLOBAL_LOGGER_NAME="champagneShop";
  public static final String GLOBAL_LOGGER_FILE="F:\\Universita\\Triennale\\SistemiWeb\\Champagne_Shop\\log\\champagneShop_log.%g.%u.txt";
  public static final Level GLOBAL_LOGGER_LEVEL=Level.ALL;

}
