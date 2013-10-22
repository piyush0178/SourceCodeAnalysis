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
	private final String query = declareVariables
			+ " <function_list> "
			+ "	{"
			+ "		for $unit in doc($file)/unit//unit[class//function/parameter_list/param/decl/type/name = $typeName]"
			+ "		for $function in $unit/class//function[parameter_list/param/decl/type/name = $typeName]"
			+ " 	return"
			+ " 	<function>"
			+ "				<class> "
			+ "					<class_name>{$unit/class/name/text()}</class_name>"
			+ "					<filename> {data($unit/@filename)}</filename>"
			+ "					<package>{substring-before(substring-after(data($unit/package),'package'), ';')} </package>"
			+ "					</class>"
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
