package fr.lille1.iagl.idl.engine.methodQueries;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;

import lombok.Getter;
import lombok.Setter;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

/**
 * @author Jules
 * 
 */
public class FindTypeObject extends AbstractMethodObject<Type> {

	@Setter
	private String typeName;

	@Getter
	private final String query = findTypeMethod
			+ "	let $result := local:findType(doc($file), $typeName)"
			+ "	return $result";

	public FindTypeObject(final XQConnection connection, final String filePath,
			final CodeSearchEngine searchEngine) {
		super(connection, filePath, searchEngine);
	}

	@Override
	public Type parse(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		return parseFindType(xmlReader);
	}

}
