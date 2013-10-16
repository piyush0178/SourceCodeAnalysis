package fr.lille1.iagl.idl.utils;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;

import net.xqj.basex.BaseXXQDataSource;

/**
 * Singleton utilitaire permettant de créer facilement la connection avec la
 * BDD.
 */
public class DatabaseConnection {

	private final static DatabaseConnection dbConnection = new DatabaseConnection();

	private static XQConnection connection;

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
	 * @return the xConnection
	 */
	public static XQConnection getConnection() {
		return connection;
	}

	/**
	 * Ferme la connexion.
	 */
	public static void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (final XQException e) {
			throw new RuntimeException(
					"Probléme lors de la fermeture de la connexion.", e);
		}
	}

}
