/**
 * 
 */
package fr.lille1.iagl.idl.engine.methodQueries;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;

import lombok.Getter;
import fr.lille1.iagl.idl.bean.Location;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

/**
 * @author Jules
 * 
 */
public class FindNewOfObject extends AbstractMethodObject<List<Location>> {

	@Getter
	private final String query = declareVariables;

	public FindNewOfObject(final XQConnection connection,
			final String filePath, final CodeSearchEngine searchEngine) {
		super(connection, filePath, searchEngine);
	}

	@Override
	public List<Location> parse(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		// TODO Auto-generated method stub
		return null;
	}

}
