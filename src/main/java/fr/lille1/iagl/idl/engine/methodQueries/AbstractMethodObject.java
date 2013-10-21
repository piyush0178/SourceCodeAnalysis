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

	public static final String PATH = "path";
	public static final String KIND = "kind";
	public static final String TYPE = "type";
	public static final String NAME = "name";
	public static final String CLASS = "class";
	public static final String FIELD = "field";
	public static final String ERROR = "error";
	public static final String EXTENDS = "extends";
	public static final String PACKAGE = "package";
	public static final String LOCATION = "location";
	public static final String FUNCTION = "function";
	public static final String TYPE_NAME = "type_name";
	public static final String CLASS_NAME = "class_name";
	public static final String FIELD_LIST = "field_list";
	public static final String METHOD_NAME = "method_name";
	public static final String LINE_NUMBER = "line_number";
	public static final String FUNCTION_LIST = "function_list";
	public static final String PARAMETER_LIST = "parameter_list";

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
