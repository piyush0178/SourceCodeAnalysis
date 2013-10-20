/**
 * 
 */
package fr.lille1.iagl.idl.main;

import java.util.List;

import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.constantes.Constantes;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;
import fr.lille1.iagl.idl.engine.impl.CodeSearchEngineDatabaseImpl;
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

		long start, end;

		start = System.currentTimeMillis();
		final List<Method> methods = engine
				.findMethodsTakingAsParameter("String");
		// final Type type = engine.findType("void");
		// final Type type = engine.findType("ObjectInputStream");
		end = System.currentTimeMillis();

		// System.out.println("type : " + type);
		int i = 0;
		for (final Method method : methods) {
			System.out.println("method " + i++ + " : " + method);
		}
		System.out.println("findMethodsTakingAsParameter :" + (end - start)
				+ " msec");
		System.out.println("Nbr de query effectué : "
				+ CodeSearchEngineDatabaseImpl.cpt);

		DatabaseConnection.closeConnection();

	}
}
