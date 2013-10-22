package fr.lille1.iagl.idl.engine.methodQueries;

import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;

import lombok.Getter;
import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

/**
 * 
 */
public class FindMethodsTakingAsParameterObject extends
		AbstractMethodObject<List<Method>> {

	@Getter
	private final String query = findTypeMethod
			+ " <function_list> "
			+ "	{"
			+ "		for $class in doc($file)//class[//function/parameter_list/param/decl/type/name = $typeName]"
			+ "		for $function in $class//function[parameter_list/param/decl/type/name = $typeName]"
			+ " 	return"
			+ " 	<function>"
			+ "			<class>"
			+ "			{"
			+ "				if (count($class/name/text()) eq 0) then <type></type>"
			+ "				else local:findType(doc($file), $class/name/text())"
			+ "			}"
			+ "			</class>"
			+ "			<type_name>"
			+ "			{"
			+ "				if (count( $function/type/name[last()]/text() ) eq 0) then <type></type>"
			+ "				else local:findType(doc($file), $function/type/name[last()]/text())"
			+ "			}"
			+ "			</type_name>"
			+ "			<method_name>{$function/name/text()}</method_name>"
			+ " 		<parameter_list>"
			+ "			{"
			+ "				for $param in $function/parameter_list/param"
			+ "				return"
			+ "					<param>"
			+ "					{"
			+ "						if (count( $param/decl/type/name/text() ) eq 0) then <type></type>"
			+ "						else local:findType(doc($file), $param/decl/type/name/text())"
			+ "					}"
			+ "					</param>"
			+ "			}"
			+ "			</parameter_list>"
			+ "		</function>"
			+ "	}"
			+ "	</function_list>"
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
		return parseMethods(xmlReader);
	}

}
