/**
 * 
 */
package fr.lille1.iagl.idl.engine.methodQueries;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;

import lombok.Getter;
import fr.lille1.iagl.idl.bean.Location;
import fr.lille1.iagl.idl.constantes.Constantes;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

/**
 * @author Jules
 * 
 */
public class FindNewOfObject extends AbstractMethodObject<List<Location>> {

	@Getter
	private final String query = declareVariables
			+ " let $result :="
			+ "		for $unit in doc($file)/unit//unit[.//expr[call/name/text() = $typeName][text() = 'new']]"
			+ "		return"
			+ "			<path>{data($unit/@filename)}</path>"
			+ "	return"
			+ "		<locations>{$result}</locations>"
			+ "(: Commentaire inutile permettant de garder le formatage du code mm avec ma save action :)";

	/**
	 * Constructor
	 * 
	 * @param connection
	 * @param filePath
	 * @param searchEngine
	 */
	public FindNewOfObject(final XQConnection connection,
			final String filePath, final CodeSearchEngine searchEngine) {
		super(connection, filePath, searchEngine);
	}

	@Override
	public List<Location> parse(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		final List<Location> locations = new ArrayList<Location>();
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.START_ELEMENT
					&& Constantes.PATH.equals(xmlReader.getLocalName())) {
				locations.add(new Location(xmlReader.getElementText()));
			} else if (eventType == XMLStreamReader.END_ELEMENT
					&& Constantes.LOCATIONS.equals(xmlReader.getLocalName())) {
				return locations;
			}
		}
		throw new RuntimeException("This case will never append");
	}

}
