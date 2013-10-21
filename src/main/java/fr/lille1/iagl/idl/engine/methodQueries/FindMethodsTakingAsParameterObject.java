package fr.lille1.iagl.idl.engine.methodQueries;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;

import lombok.Getter;
import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.constantes.Constantes;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

/**
 * 
 */
public class FindMethodsTakingAsParameterObject extends
		AbstractMethodObject<List<Method>> {

	@Getter
	private final String query = declareVariables
			+ " <function_list> "
			+ "	{"
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
			+ "	}"
			+ "	<function_list>"
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
	}

	@Override
	public List<Method> parse(final XMLStreamReader xmlReader)
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
	private List<Type> parseFunctionParameterList(
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
