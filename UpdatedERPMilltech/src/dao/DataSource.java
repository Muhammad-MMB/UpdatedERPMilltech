package dao;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import extras.AppConstants;

public class DataSource {

	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;

	static {
		
		/** LOCAL DATABASE SERVER  **/
		//config.setJdbcUrl(AppConstants.DATABSE_URL);
		//config.setUsername(AppConstants.DATABSE_USERNAME);
		//config.setPassword(AppConstants.DATABASE_PASSWORD);
		
		/** REMOTE DATABASE SERVER  **/
		config.setJdbcUrl(AppConstants.REMOTE_DATABSE_URL);
		config.setUsername(AppConstants.REMOTE_DATABSE_USERNAME);
		config.setPassword(AppConstants.REMOTE_DATABASE_PASSWORD);
		
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		config.setMaximumPoolSize(30);
		ds = new HikariDataSource(config);
	}
	
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	public static void closeConnection() throws SQLException {
		ds.close();
	}

	public static boolean checkConnectionStatus() throws SQLException {
		return ds.isClosed();
	}
}