/**
 * 
 */
package fr.lille1.iagl.idl.main;

import fr.lille1.iagl.idl.engine.CodeSearchEngine;
import fr.lille1.iagl.idl.engine.impl.CodeSearchEngineDatabaseImpl;
import fr.lille1.iagl.idl.utils.Constantes;
import fr.lille1.iagl.idl.utils.DatabaseConnection;

/**
 * @author ivanic
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final CodeSearchEngine engine = new CodeSearchEngineDatabaseImpl(
				DatabaseConnection.getConnection(), Constantes.JAVA_XML);

		engine.findType("RandomAccessFile");

		DatabaseConnection.closeConnection();

	}

}
