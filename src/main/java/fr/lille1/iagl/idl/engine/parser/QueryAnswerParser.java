package fr.lille1.iagl.idl.engine.parser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import fr.lille1.iagl.idl.bean.Location;
import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.bean.TypeKind;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

/**
 * QueryAnswerParser
 */
public class QueryAnswerParser {

	private static final String CLASS = "class";
	public static final String PATH = "path";
	public static final String KIND = "kind";
	public static final String TYPE = "type";
	public static final String ERROR = "error";
	public static final String PACKAGE = "package";
	public static final String LOCATION = "location";
	public static final String FUNCTION = "function";
	public static final String TYPE_NAME = "type_name";
	public static final String LINE_NUMBER = "line_number";
	public static final String METHOD_NAME = "method_name";
	public static final String FUNCTION_LIST = "function_list";
	public static final String PARAMETER_LIST = "parameter_list";

	CodeSearchEngine searchEngine;

	/**
	 * @param searchEngine
	 */
	public QueryAnswerParser(final CodeSearchEngine searchEngine) {
		this.searchEngine = searchEngine;
	}

	/**
	 * TODO JIV : documentation
	 * 
	 * @param sequenceAsStream
	 * @return
	 * @throws XMLStreamException
	 */
	public Type parseFindTypeResults(final XMLStreamReader xmlReader,
			final String typeName) throws XMLStreamException {
		final Type typeRes = new Type();
		typeRes.setName(typeName);
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT
					&& TYPE.equals(xmlReader.getLocalName())) {
				return typeRes;
			}
			if (eventType == XMLStreamReader.START_ELEMENT) {
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
	public Location parseLocation(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		final Location location = new Location();
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT
					&& LOCATION.equals(xmlReader.getLocalName())) {
				return location;
			}
			if (eventType == XMLStreamReader.START_ELEMENT) {
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

	/**
	 * TODO JIV : documentation
	 * 
	 * @param xmlReader
	 * @throws XMLStreamException
	 */
	public List<Method> parseFunctionToMethod(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		final List<Method> methodList = new ArrayList<Method>();
		Method method = null;
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case FUNCTION:
					methodList.add(method);
				case FUNCTION_LIST:
					return methodList;
				}
			}
			if (eventType == XMLStreamReader.START_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case FUNCTION:
					method = new Method();
					break;
				case CLASS:
					method.setDeclaringType(searchEngine.findType(xmlReader
							.getElementText()));
					break;
				case TYPE_NAME:
					method.setDeclaringType(searchEngine.findType(xmlReader
							.getElementText()));
					break;
				case METHOD_NAME:
					method.setName(xmlReader.getElementText());
					break;
				case PARAMETER_LIST:
					method.setParameters(parseFunctionParameterList(xmlReader));
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
	 * @throws XMLStreamException
	 */
	public List<Type> parseFunctionParameterList(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		final List<Type> paramList = new ArrayList<Type>();
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT
					&& PARAMETER_LIST.equals(xmlReader.getLocalName())) {
				return paramList;
			}
			if (eventType == XMLStreamReader.START_ELEMENT
					&& TYPE.equals(xmlReader.getLocalName())) {
				paramList
						.add(searchEngine.findType(xmlReader.getElementText()));
			}
		}
		throw new RuntimeException("This case will never append");
	}

}
