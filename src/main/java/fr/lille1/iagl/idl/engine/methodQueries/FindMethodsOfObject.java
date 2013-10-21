package fr.lille1.iagl.idl.engine.methodQueries;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;

import lombok.Getter;
import lombok.Setter;
import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.constantes.Constantes;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

public class FindMethodsOfObject extends AbstractMethodObject<List<Method>> {

	@Setter
	private String typeName;

	@Getter
	private final String query = declareVariables
			+ " let $methods := "
			+ " for $class in doc($file)//class[block/function/name=$typeName]"
			+ " return"
			+ " <class>"
			+ "  <class_name>{$class/name/text()}</class_name>"
			+ "     <method_list>"
			+ "   {"
			+ "     for $method in $class/block/function[name eq $typeName]"
			+ "     return"
			+ "     <function>"
			+ "     <return>"
			+ "       {$method/type/name[last()]/text()}"
			+ "     </return>"
			+ "     <parameter_list>"
			+ "     {"
			+ "       for $param_list in $method/parameter_list/param"
			+ "       return"
			+ "       <parameter>"
			+ "         <type>{$param_list/decl/type/name/text()}</type>"
			+ "           <name>"
			+ "				{"
			+ "					let $nom := $param_list/decl/name"
			+ "					return"
			+ "  					if (count($nom/text()) = 0)"
			+ "   					then $nom/name/text()"
			+ "    					else $nom/text()"
			+ " 			}"
			+ "			</name>"
			+ "       </parameter>"
			+ "     }"
			+ "     </parameter_list>"
			+ "     </function>"
			+ "   }"
			+ "   </method_list>"
			+ "   </class>"
			+ " return <function_list>{$methods}</function_list>"
			+ "(: Commentaire inutile permettant de garder le formatage du code mm avec ma save action :)";

	public FindMethodsOfObject(final XQConnection connection,
			final String filePath, final CodeSearchEngine searchEngine) {
		super(connection, filePath, searchEngine);
	}

	@Override
	public List<Method> parse(final XMLStreamReader xmlReader)
			throws XMLStreamException {

		final List<Method> methods = new ArrayList<Method>();
		final List<Type> paramList = new ArrayList<Type>();
		Method method = null;
		Type classType = searchEngine.findType(typeName);
		Type param = null;

		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case Constantes.FUNCTION_LIST:
					return methods;
				case Constantes.PARAMETER:
					paramList.add(param);
					break;
				case Constantes.METHOD_LIST:
					method.setParameters(paramList);
				case Constantes.FUNCTION:
					methods.add(method);
					break;
				}
			} else if (eventType == XMLStreamReader.START_ELEMENT) {
				final String localName = xmlReader.getLocalName();
				switch (localName) {
				case Constantes.CLASS_NAME:
					classType = searchEngine.findType(xmlReader
							.getElementText());
					break;
				case Constantes.FUNCTION:
					method = new Method();
					method.setDeclaringType(classType);
					break;
				case Constantes.RETURN:
					method.setName(typeName);
					method.setType(searchEngine.findType(xmlReader
							.getElementText()));
					break;
				case Constantes.PARAMETER_LIST:
					paramList.clear();
					break;
				case Constantes.TYPE:
					param = searchEngine.findType(xmlReader.getElementText());
					break;
				case Constantes.NAME:
					param.setName(xmlReader.getElementText());
					break;

				}

			}
		}
		return null;
	}
}
