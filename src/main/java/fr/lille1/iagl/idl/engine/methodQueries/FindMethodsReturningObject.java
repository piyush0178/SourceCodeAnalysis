package fr.lille1.iagl.idl.engine.methodQueries;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;

import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.constantes.Constantes;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

/**
 * @author Jules
 * 
 */
public class FindMethodsReturningObject extends
		AbstractMethodObject<List<Type>> {

	private final String myQuery = declareVariables;

	public FindMethodsReturningObject(final XQConnection connection,
			final String filePath, final CodeSearchEngine searchEngine) {
		super(connection, filePath, searchEngine);
		query = myQuery;
	}

	@Override
	public List<Type> parse(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		final List<Type> subTypeList = new ArrayList<Type>();
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT
					&& Constantes.EXTENDS.equals(xmlReader.getLocalName())) {
				return subTypeList;
			} else if (eventType == XMLStreamReader.START_ELEMENT
					&& Constantes.CLASS.equals(xmlReader.getLocalName())) {
				subTypeList.add(searchEngine.findType(xmlReader
						.getElementText()));
			}
		}
		throw new RuntimeException("This case will never append");
	}

}
