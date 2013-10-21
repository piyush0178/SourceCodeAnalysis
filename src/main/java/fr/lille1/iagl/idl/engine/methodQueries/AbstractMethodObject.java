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

	protected static final String PATH = "path";
	protected static final String KIND = "kind";
	protected static final String TYPE = "type";
	protected static final String NAME = "name";
	protected static final String CLASS = "class";
	protected static final String FIELD = "field";
	protected static final String ERROR = "error";
	protected static final String EXTENDS = "extends";
	protected static final String PACKAGE = "package";
	protected static final String LOCATION = "location";
	protected static final String FUNCTION = "function";
	protected static final String TYPE_NAME = "type_name";
	protected static final String CLASS_NAME = "class_name";
	protected static final String FIELD_LIST = "field_list";
	protected static final String METHOD_NAME = "method_name";
	protected static final String LINE_NUMBER = "line_number";
	protected static final String FUNCTION_LIST = "function_list";
	protected static final String PARAMETER_LIST = "parameter_list";

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

		if (query == null) {
			throw new RuntimeException("You have to redefine the query field !");
		}

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
	protected abstract T parse(final XMLStreamReader xmlReader)
			throws XMLStreamException;

	/**
	 * Initialize the prepared Query.
	 * 
	 * @throws XQException
	 */
	private void prepareQuery() throws XQException {
		preparedQuery = connection.prepareExpression(query);
		preparedQuery.bindString(new QName("file"), filePath, null);
	}

}
