/**
 * 
 */
package fr.lille1.iagl.idl.main;

import fr.lille1.iagl.idl.CodeSearchEngine;
import fr.lille1.iagl.idl.impl.CodeSearchEngineDatabaseImpl;
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
				DatabaseConnection.getConnection());

	}

}
