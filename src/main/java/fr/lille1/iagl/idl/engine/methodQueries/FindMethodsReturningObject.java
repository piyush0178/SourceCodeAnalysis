package fr.lille1.iagl.idl.engine.methodQueries;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;

import lombok.Getter;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

/**
 * @author Jules
 * 
 */
public class FindMethodsReturningObject extends
		AbstractMethodObject<List<Type>> {

	@Getter
	private final String query = declareVariables;

	/**
	 * Constructor
	 * 
	 * @param connection
	 * @param filePath
	 * @param searchEngine
	 */
	public FindMethodsReturningObject(final XQConnection connection,
			final String filePath, final CodeSearchEngine searchEngine) {
		super(connection, filePath, searchEngine);
	}

	@Override
	public List<Type> parse(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		return null;
	}

}
