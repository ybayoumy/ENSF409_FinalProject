package ServerModel;
/**
 * The credentials in order to connect to the database.
 * @author Yassin Bayoumy & Thomas Kahessay
 */
public interface DBCredentials {
	/**
	 * The driver for the JDBC.
	 */
	final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	/**
	 * The location of the database.
	 */
	final String DB_URL = "jdbc:mysql://localhost:3306/______?serverTimezone=MST";
	/**
	 * Username for the database.
	 */
	final String USERNAME = "root";
	/**
	 * Password for the database.
	 */
	final String PASSWORD = "ensfensf";
}
