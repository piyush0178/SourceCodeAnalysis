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
		long start;
		long end;

		List<Method> methods = null;

		start = System.currentTimeMillis();

		final CodeSearchEngine engine = new CodeSearchEngineDatabaseImpl(
				DatabaseConnection.getConnection(), Constantes.JAVA_XML);

		// final Type type = engine.findType("File");
		// final Type type = engine.findType("File");
		// final List<Field> types = engine.findFieldsTypedWith("String");
		methods = engine.findMethodsTakingAsParameter("ObjectInputStream");
		// final Type type = engine.findType("void");
		// final Type type = engine.findType("ObjectInputStream");

		// System.out.println("type : " + type);
		DatabaseConnection.closeConnection();

		end = System.currentTimeMillis();

		System.out.println("findMethodsTakingAsParameter :" + (end - start)
				+ " msec");

		System.out.println("methods.size() : " + methods.size());
	}
}
