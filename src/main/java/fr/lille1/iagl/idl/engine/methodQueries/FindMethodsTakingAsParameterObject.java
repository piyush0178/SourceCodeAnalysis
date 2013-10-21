package fr.lille1.iagl.idl.engine.methodQueries;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;

import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

/**
 * 
 */
public class FindMethodsTakingAsParameterObject extends
		AbstractMethodObject<List<Method>> {

	/**
	 * 
	 */
	private final String findMethodsTakingAsParameterQuery = declareVariables
			+ " let $functions := "
			+ "		for $class in doc($file)//class[//function/parameter_list/param/decl/type/name = $typeName]"
			+ "		for $function in $class//function[parameter_list/param/decl/type/name = $typeName]"
			+ " 	return"
			+ " 	<function>"
			+ "			<class>{$class/name/text()}</class>"
			+ "			<type_name>{$function/type/name[last()]/text()}</type_name>"
			+ "			<method_name>{$function/name/text()}</method_name>"
			+ " 		<parameter_list>"
			+ "			{"
			+ "				for $param in $function/parameter_list/param"
			+ "				return"
			+ "					<type>{$param/decl/type/name/text()}</type>"
			+ "			}"
			+ "			</parameter_list>"
			+ "		</function>"
			+ " return"
			+ "		<function_list>{$functions}</function_list>"
			+ "(: Commentaire inutile permettant de garder le formatage du code mm avec ma save action :)";

	/**
	 * Constructor
	 * 
	 * @param connection
	 * @param filePath
	 * @param searchEngine
	 */
	public FindMethodsTakingAsParameterObject(final XQConnection connection,
			final String filePath, final CodeSearchEngine searchEngine) {
		super(connection, filePath, searchEngine);
		query = findMethodsTakingAsParameterQuery;
	}

	@Override
	protected List<Method> parse(final XMLStreamReader xmlReader)
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
					break;
				case FUNCTION_LIST:
					return methodList;
				}
			} else if (eventType == XMLStreamReader.START_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case FUNCTION:
					method = new Method();
					break;
				case CLASS:
					method.setType(searchEngine.findType(xmlReader
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
	 * parser for parameters
	 * 
	 * @param xmlReader
	 * @throws XMLStreamException
	 */
	private List<Type> parseFunctionParameterList(
			final XMLStreamReader xmlReader) throws XMLStreamException {
		final List<Type> paramList = new ArrayList<Type>();
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT
					&& PARAMETER_LIST.equals(xmlReader.getLocalName())) {
				return paramList;
			} else if (eventType == XMLStreamReader.START_ELEMENT
					&& TYPE.equals(xmlReader.getLocalName())) {
				paramList
						.add(searchEngine.findType(xmlReader.getElementText()));
			}
		}
		throw new RuntimeException("This case will never append");
	}

}
