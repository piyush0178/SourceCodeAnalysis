package fr.lille1.iagl.idl.main;

import java.util.List;

import fr.lille1.iagl.idl.bean.Type;
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
		final DatabaseConnection databaseConnection = new DatabaseConnection();
		final CodeSearchEngine engine = new CodeSearchEngineDatabaseImpl(
				databaseConnection.getConnection(), Constantes.JAVA_XML);

		long start, end;

		start = System.currentTimeMillis();

		// final Type type = engine.findType("File");
		final List<Type> types = engine.findSubTypesOf("RuntimeException");
		// final List<Location> locations = engine.findNewOf("IOException");
		// final List<Method> methods = engine
		// .findMethodsTakingAsParameter("ObjectInputStream");
		// final Type type = engine.findType("void");
		// final Type type = engine.findType("ObjectInputStream");
		end = System.currentTimeMillis();

		// System.out.println("type : " + type);
		// int i = 0;
		// for (final Type type : types) {
		// System.out.println("method " + i++ + " : " + type);
		// }

		int i = 1;
		for (final Type type : types) {
			System.out.println("type " + i++ + " : " + type);
		}

		System.out.println("findMethodsTakingAsParameter :" + (end - start)
				+ " msec");
		System.out.println("Nbr de query effectué : "
				+ CodeSearchEngineDatabaseImpl.cpt);
		databaseConnection.closeConnection();

	}
}
