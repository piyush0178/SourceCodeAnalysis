package fr.lille1.iagl.idl.engine.methodQueries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;

import lombok.Getter;
import fr.lille1.iagl.idl.bean.Location;
import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.constantes.Constantes;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

/**
 * AbstractMethodObject<T> class.
 */
public abstract class AbstractMethodObject<T> {

	public final static String declareVariables = // "declare variable $file as xs:string external;"
	" declare variable $doc as document-node(element(*,xs:untyped));"
			+ "declare variable $typeName as xs:string external;";

	protected XQConnection connection;
	protected String filePath;
	final CodeSearchEngine searchEngine;

	@Getter
	protected XQPreparedExpression preparedQuery;

	private XMLStreamReader reader;

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
			// //////////////////
			final XMLInputFactory factory = XMLInputFactory.newInstance();
			final FileInputStream doc = new FileInputStream(filePath);
			reader = factory.createXMLStreamReader(doc);

			// //////////////////
			prepareQuery();
		} catch (final XQException | XMLStreamException | FileNotFoundException e) {
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
		if (getQuery() == null) {
			throw new RuntimeException("You have to redefine the query field !");
		}
		preparedQuery = connection.prepareExpression(getQuery());
		preparedQuery.bindDocument(new QName("doc"), reader, null);

	}

	protected abstract String getQuery();

	/**
	 * TODO RAL : commentaire
	 * 
	 * @param xmlReader
	 * @return
	 * @throws XMLStreamException
	 */
	protected List<Method> parseMethods(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		final List<Method> methodList = new ArrayList<Method>();
		Type classe = null;
		Method method = null;
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case Constantes.CLASS:
					method.setDeclaringType(classe);
					break;
				case Constantes.FUNCTION:
					methodList.add(method);
					break;
				case Constantes.FUNCTION_LIST:
					return methodList;
				}
			} else if (eventType == XMLStreamReader.START_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case Constantes.FUNCTION:
					method = new Method();
					break;
				case Constantes.CLASS:
					classe = new Type();
					// method.setDeclaringType(searchEngine.findType(xmlReader
					// .getElementText()));
					break;
				case Constantes.CLASS_NAME:
					classe.setName(xmlReader.getElementText());
					break;
				case Constantes.FILENAME:
					classe.setDeclaration(new Location(xmlReader
							.getElementText()));
					break;
				case Constantes.PACKAGE:
					classe.setFullyQualifiedPackageName(xmlReader
							.getElementText());
					break;
				case Constantes.TYPE_NAME:
					method.setType(searchEngine.findType(xmlReader
							.getElementText()));
					break;
				case Constantes.METHOD_NAME:
					method.setName(xmlReader.getElementText());
					break;
				case Constantes.PARAMETER_LIST:
					method.setParameters(parseFunctionParameterList(xmlReader));
					break;
				}
			}
		}
		throw new RuntimeException("This case will never append");
	}

	/**
	 * parser for parameters
	 * 
	 * @param xmlReader
	 * @throws XMLStreamException
	 */
	protected List<Type> parseFunctionParameterList(
			final XMLStreamReader xmlReader) throws XMLStreamException {
		final List<Type> paramList = new ArrayList<Type>();
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT
					&& Constantes.PARAMETER_LIST.equals(xmlReader
							.getLocalName())) {
				return paramList;
			} else if (eventType == XMLStreamReader.START_ELEMENT
					&& Constantes.TYPE.equals(xmlReader.getLocalName())) {
				paramList
						.add(searchEngine.findType(xmlReader.getElementText()));
			}
		}
		throw new RuntimeException("This case will never append");
	}

}
