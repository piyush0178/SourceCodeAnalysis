/**
 * 
 */
package fr.lille1.iagl.idl.engine.queries;

/**
 * Queries
 */
public class Queries {

	public static final String findTypeQuery = "declare variable $file as xs:string external;"
			+ " declare variable $typeName as xs:string external;"
			+ "	let $result :="
			+ " 	for $unit in doc($file)//unit[class/name=$typeName]"
			+ " 	return"
			+ "		<type>"
			+ "			<location>"
			+ "				<path>{data($unit/@filename)}</path>"
			+ "				<line_number></line_number>"
			+ "			</location>"
			+ "			<package>"
			+ "			{"
			+ "				(: petite bidouille pr enlever 'package' et ';' de la déclaration du package :)"
			+ "				substring-before(substring-after(data($unit/package),'package'), ';')"
			+ "			}"
			+ "			</package>"
			+ "			<kind>"
			+ "			{ "
			+ "				if($unit/class) then 'class' "
			+ "				else if($unit/enum) then 'enum'"
			+ "				else if($unit/interface) then 'interface'"
			+ "				else ''"
			+ "			}"
			+ "			</kind>"
			+ "		</type>"
			+ "	return"
			+ "		if(count($result) eq 0) then <error>The query returned nothing</error>"
			+ "		else $result";

	public static final String findMethodsTakingAsParameterQuery = "declare variable $file as xs:string external;"
			+ " declare variable $typeName as xs:string external;"
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
			+ "		<function_list>{$functions}</function_list>";

}
