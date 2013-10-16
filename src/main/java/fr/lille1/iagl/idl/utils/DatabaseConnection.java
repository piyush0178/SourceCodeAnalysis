package fr.lille1.iagl.idl.utils;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;

import net.xqj.basex.BaseXXQDataSource;

public class DatabaseConnection {

	private final DatabaseConnection dbConnection = new DatabaseConnection();

	private XQConnection connection;

	private DatabaseConnection() {
		final XQDataSource dataSource = new BaseXXQDataSource();
		try {
			dataSource.setProperty("serverName", "localhost");
			dataSource.setProperty("port", "1984");
			dataSource.setProperty("user", "admin");
			dataSource.setProperty("password", "admin");

			connection = dataSource.getConnection();

		} catch (final XQException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Getter du singleton
	 * 
	 * @return
	 */
	public DatabaseConnection getDbConnection() {
		return dbConnection;
	}

	/**
	 * @return the xConnection
	 */
	public XQConnection getConnection() {
		return connection;
	}

}
