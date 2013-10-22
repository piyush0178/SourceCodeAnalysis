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

	private static XQConnection connection;

	private static XQDataSource dataSource;

	public DatabaseConnection() {
		dataSource = new BaseXXQDataSource();
		try {
			dataSource.setProperty("serverName", "localhost");
			dataSource.setProperty("port", "1984");
		} catch (final XQException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return the xConnection
	 * @throws XQException
	 */
	public XQConnection getConnection() {
		try {
			return dataSource.getConnection("admin", "admin");
		} catch (final XQException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Ferme la connexion.
	 */
	public void closeConnection() {
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
