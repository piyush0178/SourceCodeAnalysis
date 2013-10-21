package fr.lille1.iagl.idl.engine.methodQueries;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;

import lombok.Getter;
import lombok.Setter;
import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

/**
 * @author Jules
 * 
 */
public class FindMethodsReturningObject extends
		AbstractMethodObject<List<Method>> {

	@Getter
	private final String query = declareVariables
			+ " let $methods :="
			+ " <function_list>"
			+ " {"
			+ " for $class in doc($file)//class[block/function/type/name[last()]=$typeName]"
			+ " for $function in $class/block/function[type/name[last()] = $typeName]"
			+ " return"
			+ " <function>"
			+ "     <class>{$class/name/text()}</class>"
			+ "     <type_name>{$function/type/name[last()]/text()}</type_name>"
			+ "     <method_name>{$function/name/text()}</method_name>"
			+ "     <parameter_list>"
			+ "     {"
			+ "        for $param in $function/parameter_list/param"
			+ "        return"
			+ "          <type>{$param/decl/type/name/text()}</type>"
			+ "     }"
			+ "     </parameter_list>"
			+ "   </function>"
			+ " }"
			+ " </function_list>"
			+ " return $methods "
			+ "(: Commentaire inutile permettant de garder le formatage du code mm avec ma save action :)";

	@Setter
	private String typeName;

	/**
	 * Constructor
	 * 
	 * @param connection
	 * @param filePath
	 * @param searchEngine
	 */
	public FindMethodsReturningObject(final XQConnection connection,
			final String filePath, final CodeSearchEngine searchEngine) {
		super(connection, filePath, searchEngine);
	}

	@Override
	public List<Method> parse(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		return parseMethods(xmlReader);
	}

}
