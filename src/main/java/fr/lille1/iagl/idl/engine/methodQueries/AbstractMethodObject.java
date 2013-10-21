package fr.lille1.iagl.idl.engine.methodQueries;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;

import lombok.Getter;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

/**
 * AbstractMethodObject<T> class.
 */
public abstract class AbstractMethodObject<T> {

	public final static String declareVariables = "declare variable $file as xs:string external;"
			+ " declare variable $typeName as xs:string external;";

	protected XQConnection connection;
	protected String filePath;
	final CodeSearchEngine searchEngine;

	/**
	 * Doit être redéfinit.
	 */
	protected String query;

	@Getter
	protected XQPreparedExpression preparedQuery;

	/**
	 * Constructor
	 * 
	 * @param connection
	 * @param filePath
	 * @param searchEngine
	 */
	public AbstractMethodObject(final XQConnection connection,
			final String filePath, final CodeSearchEngine searchEngine) {
		this.connection = connection;
		this.filePath = filePath;
		this.searchEngine = searchEngine;
		try {
			prepareQuery();
		} catch (final XQException e) {
			throw new RuntimeException("ERREUR : " + e.getMessage(), e);
		}
	}

	/**
	 * Parser for the result of the query
	 * 
	 * @return
	 */
	public abstract T parse(final XMLStreamReader xmlReader)
			throws XMLStreamException;

	/**
	 * Initialize the prepared Query.
	 * 
	 * @throws XQException
	 */
	private void prepareQuery() throws XQException {
		if (query == null) {
			throw new RuntimeException("You have to redefine the query field !");
		}
		preparedQuery = connection.prepareExpression(query);
		preparedQuery.bindString(new QName("file"), filePath, null);
	}

}
