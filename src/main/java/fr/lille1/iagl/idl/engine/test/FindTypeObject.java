package fr.lille1.iagl.idl.engine.test;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;

import fr.lille1.iagl.idl.bean.Location;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.bean.TypeKind;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

/**
 * @author Jules
 * 
 */
public class FindTypeObject extends AbstractMethodObject<Type> {

	private final String typeName;

	private final String findTypeQuery = declareVariables
			+ "	let $result :="
			+ " 	for $unit in doc($file)//unit[class/name = $typeName]"
			+ " 	return"
			+ "		<type>"
			+ "			<location>"
			+ "				<path>{data($unit/@filename)}</path>"
			+ "				<line_number></line_number>"
			+ "			</location>"
			+ "			<package>"
			+ "			{"
			+ "				(: petite bidouille pr enlever 'package' et ';' de la déclaration du package :)"
			+ "				substring-before(substring-after(data($unit/package),'package'), ';')"
			+ "			}"
			+ "			</package>"
			+ "			<kind>"
			+ "			{ "
			+ "				if($unit/class) then 'class' "
			+ "				else if($unit/enum) then 'enum'"
			+ "				else if($unit/interface) then 'interface'"
			+ "				else ''"
			+ "			}"
			+ "			</kind>"
			+ "		</type>"
			+ "	return"
			+ "		if(count($result) eq 0) then <error>The query returned nothing</error>"
			+ "		else $result";

	public FindTypeObject(final XQConnection connection, final String filePath,
			final CodeSearchEngine searchEngine, final String typeName) {
		super(connection, filePath, searchEngine);
		this.typeName = typeName;
		query = findTypeQuery;
	}

	@Override
	protected Type parse(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		final Type typeRes = new Type();
		typeRes.setName(typeName);
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT
					&& TYPE.equals(xmlReader.getLocalName())) {
				return typeRes;
			} else if (eventType == XMLStreamReader.START_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case LOCATION:
					typeRes.setDeclaration(parseLocation(xmlReader));
					break;
				case PACKAGE:
					typeRes.setFullyQualifiedPackageName(xmlReader
							.getElementText());
					break;
				case KIND:
					typeRes.setKind(TypeKind.valueOf(xmlReader.getElementText()
							.toUpperCase()));
					break;
				}
			}
		}
		throw new RuntimeException("This case will never append");
	}

	/**
	 * TODO JIV : documentation
	 * 
	 * @param xmlReader
	 * @return
	 * @throws XMLStreamException
	 */
	private Location parseLocation(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		final Location location = new Location();
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT
					&& LOCATION.equals(xmlReader.getLocalName())) {
				return location;
			} else if (eventType == XMLStreamReader.START_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case PATH:
					location.setFilePath(xmlReader.getElementText());
					break;
				case LINE_NUMBER:
					// FIXME : pas encore géré ! (comme dans la requête
					// d'ailleurs)
					break;
				}
			}
		}
		throw new RuntimeException("This case will never append");
	}

}