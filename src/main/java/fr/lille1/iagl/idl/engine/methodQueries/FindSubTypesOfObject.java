package fr.lille1.iagl.idl.engine.methodQueries;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;

import lombok.Getter;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.constantes.Constantes;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

/**
 * @author Jules
 * 
 */
public class FindSubTypesOfObject extends AbstractMethodObject<List<Type>> {

	/**
	 * Query recursive
	 */
	@Getter
	private final String query = declareVariables
			+ " declare function local:findSubtypeOf($root, $typeName as xs:string)"
			+ "	{"
			+ "		for $class in $root[super/extends/name = $typeName]"
			+ "		return"
			+ "			(<class>{$class/name/text()}</class>, local:findSubtypeOf($root, $class/name/text()))"
			+ "	};"
			+ ""
			+ "	<extends>"
			+ "		{ local:findSubtypeOf(doc($file)//class, $typeName) }"
			+ "	</extends>"
			+ "(: Commentaire inutile permettant de garder le formatage du code mm avec ma save action :)";

	/**
	 * Constructor
	 * 
	 * @param connection
	 * @param filePath
	 * @param searchEngine
	 */
	public FindSubTypesOfObject(final XQConnection connection,
			final String filePath, final CodeSearchEngine searchEngine) {
		super(connection, filePath, searchEngine);
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
