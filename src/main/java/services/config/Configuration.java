package services.config;

import model.dao.DAOFactory;

import java.util.Calendar;
import java.util.logging.Level;

public class Configuration {
  
  /* Database Configruation */
  public static final String DAO_IMPL=DAOFactory.MYSQLJDBCIMPL;
  public static final String DATABASE_DRIVER="org.postgresql.Driver"; /* com.mysql.cj.jdbc.Driver */
  public static final String SERVER_TIMEZONE=Calendar.getInstance().getTimeZone().getID();
  //public static final String 
  //  DATABASE_URL="jdbc:postgresql://localhost:5432/esame_riguzzi?user=root&password=qwe"; /*"jdbc:mysql://localhost/esame?user=root&password=qweasd&useSSL=false&serverTimezone="+SERVER_TIMEZONE; */
  

  /* per progetto ISA */
  
  public static final String DATABASE_HOST = System.getenv().getOrDefault("DATABASE_HOST", "localhost");
  public static final String DATABASE_USERNAME = System.getenv().getOrDefault("DATABASE_USERNAME", "root");
  public static final String DATABASE_PASSWORD = System.getenv().getOrDefault("DATABASE_PASSWORD", "qwe");
  public static final String DATABASE_NAME = System.getenv().getOrDefault("DATABASE_NAME", "Champagne_Shop");
  public static final String DATABASE_URL = "jdbc:postgresql://" + DATABASE_HOST + "/"+ DATABASE_NAME + "?user=" + DATABASE_USERNAME + "&password=" + DATABASE_PASSWORD + "&useSSL=false&serverTimezone=" + SERVER_TIMEZONE;
  


  /* Session Configuration */
  public static final String COOKIE_IMPL=DAOFactory.COOKIEIMPL;
  
  /* Logger Configuration */
  public static final String GLOBAL_LOGGER_NAME="ChampagneShop"; 
  // public static final String GLOBAL_LOGGER_FILE="F:\\Universita\\Triennale\\SistemiWeb\\Champagne_Shop\\log\\champagneShop_log.%g.%u.txt"; 
  public static final String GLOBAL_LOGGER_FILE= System.getenv().getOrDefault("LOGFILE", "F:\\Universita\\Triennale\\SistemiWeb\\Champagne_Shop\\log\\champagneShop_log.%g.%u.txt");
  //public static final String GLOBAL_LOGGER_FILE= System.getenv().getOrDefault("LOGFILE", "C:\\Users\\ionut\\Desktop\\PD.txt");
  public static final Level GLOBAL_LOGGER_LEVEL=Level.ALL;

}
