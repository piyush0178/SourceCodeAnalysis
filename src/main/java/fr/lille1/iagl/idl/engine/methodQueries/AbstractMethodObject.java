package fr.lille1.iagl.idl.engine.methodQueries;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;

import lombok.Getter;
import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.constantes.Constantes;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

/**
 * AbstractMethodObject<T> class.
 */
public abstract class AbstractMethodObject<T> {

	public final static String declareVariables = "declare variable $file as xs:string external;"
			+ " declare variable $typeName as xs:string external;";

	protected final XQConnection connection;
	protected final String filePath;
	protected final CodeSearchEngine searchEngine;

	private final ExecutorService executor = Executors.newFixedThreadPool(100);

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
		prepareQuery();
	}

	/**
	 * Parser for the result of the query
	 * 
	 * @return
	 */
	public abstract T parse(final XMLStreamReader xmlReader)
			throws XMLStreamException;

	protected List<Type> callThreaded(final List<String> strings) {
		// final List<Type> types = Collections
		// .synchronizedList(new ArrayList<Type>());
		final List<Type> types = new ArrayList<Type>();
		final List<Future<Type>> futures = new ArrayList<Future<Type>>();
		for (final String string : strings) {
			futures.add(executor.submit(new Callable<Type>() {
				@Override
				public Type call() throws Exception {
					return searchEngine.findType(string);
				}
			}));
		}
		for (final Future<Type> future : futures) {
			try {
				types.add(future.get());
			} catch (InterruptedException | ExecutionException e) {
				throw new RuntimeException(e);
			}
		}
		return types;
	}

	/**
	 * Initialize the prepared Query.
	 * 
	 * @throws XQException
	 */
	private void prepareQuery() {
		if (getQuery() == null) {
			throw new RuntimeException("You have to redefine the query field !");
		}

		try {
			preparedQuery = connection.prepareExpression(getQuery());
			preparedQuery.bindString(new QName("file"), filePath, null);
		} catch (final XQException e) {
			throw new RuntimeException(e);
		}
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
		Method method = null;
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT) {
				switch (xmlReader.getLocalName()) {
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
					method.setType(searchEngine.findType(xmlReader
							.getElementText()));
					break;
				case Constantes.TYPE_NAME:
					method.setDeclaringType(searchEngine.findType(xmlReader
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
