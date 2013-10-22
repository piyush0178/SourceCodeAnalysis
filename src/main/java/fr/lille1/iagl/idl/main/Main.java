package fr.lille1.iagl.idl.main;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

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
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static void main(final String[] args) throws SAXException,
			IOException, ParserConfigurationException {
		final CodeSearchEngine engine = new CodeSearchEngineDatabaseImpl(
				DatabaseConnection.getConnection(), Constantes.JAVA_XML);

		long start, end, start1, end1;

		start = System.currentTimeMillis();
		final Type methods = engine.findType("Exception");
		end = System.currentTimeMillis();

		start1 = System.currentTimeMillis();
		final Type methodes = engine.findType("String");
		end1 = System.currentTimeMillis();

		final int i = 0;
		// for (final Method m : methods) {
		// System.out.println((++i) + " " + m);
		// }
		System.out.println("Nbr de query effectué : "
				+ CodeSearchEngineDatabaseImpl.cpt);
		System.out.println("methode :" + (end - start) + " msec");
		System.out.println("methode :" + (end1 - start1) + " msec");
		DatabaseConnection.closeConnection();

	}
}
