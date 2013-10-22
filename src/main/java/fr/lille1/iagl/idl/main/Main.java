package fr.lille1.iagl.idl.main;

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
		long start;
		long end;

		// List<Method> methods = null;
		Type type;

		start = System.currentTimeMillis();

		final CodeSearchEngine engine = new CodeSearchEngineDatabaseImpl(
				DatabaseConnection.getConnection(), Constantes.COMMONS_3_XML);

		// final Type type = engine.findType("File");
		// final Type type = engine.findType("File");
		// final List<Field> types = engine.findFieldsTypedWith("String");
		type = engine.findType("AVLNode");
		// final Type type = engine.findType("void");
		// final Type type = engine.findType("ObjectInputStream");

		// System.out.println("type : " + type);
		DatabaseConnection.closeConnection();

		end = System.currentTimeMillis();

		System.out.println("findMethodsTakingAsParameter :" + (end - start)
				+ " msec");

		System.out.println("methods.size() : " + type);
	}
}
