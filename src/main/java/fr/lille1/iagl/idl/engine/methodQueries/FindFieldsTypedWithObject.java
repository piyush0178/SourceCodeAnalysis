package fr.lille1.iagl.idl.engine.methodQueries;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;

import lombok.Getter;
import lombok.Setter;
import fr.lille1.iagl.idl.bean.Field;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.constantes.Constantes;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

public class FindFieldsTypedWithObject extends
		AbstractMethodObject<List<Field>> {

	@Setter
	private String typeName;

	@Getter
	private final String query2 = findTypeMethod
			+ " let $fields := "
			+ "		<field_type>{ local:findType( doc($file), $typeName ) }</field_type>"
			+ "		{"
			+ " 		for $class in doc($file)//class[.//block/decl_stmt/decl/type/name = $typeName]"
			+ " 		 return "
			+ " 			<class>"
			+ "  			{"
			+ "					("
			+ "					 	local:findType(doc($file), $class/name/text()),"
			+ " 					for $field in $class//block/decl_stmt/decl[type/name=$typeName]"
			+ " 					return"
			+ " 						<field_name>{ $field/name/text() }</field_name>"
			+ " 				)"
			+ "				}"
			+ "				</class>"
			+ "		}"
			+ " return "
			+ " <field_list>{$fields}</field_list>"
			+ "(: Commentaire inutile permettant de garder le formatage du code mm avec ma save action :)";

	@Getter
	private final String query = findTypeMethod
			+ " let $fields :="
			+ " 	for $class in doc($file)//class[.//block/decl_stmt/decl/type/name = $typeName]"
			+ "		return "
			+ "			<class>"
			+ " 		{"
			+ "				if (count($class/name/text()) eq 0) then <type></type>"
			+ "				else local:findType(doc($file), $class/name/text())"
			+ "			}"
			+ "			{"
			+ "				for $field in $class//block/decl_stmt/decl[type/name= $typeName]"
			+ "				return"
			+ " 				<field_name>{ $field/name/text() }</field_name>"
			+ " 		}"
			+ "			</class>"
			+ "		return"
			+ "			<field_list> "
			+ "				<field_type>{ local:findType( doc($file), $typeName) }</field_type>"
			+ "				{$fields}"
			+ "			</field_list>"
			+ "(: Commentaire inutile permettant de garder le formatage du code mm avec ma save action :)";

	/**
	 * Constructor
	 * 
	 * @param connection
	 * @param filePath
	 * @param searchEngine
	 * @param typeName
	 */
	public FindFieldsTypedWithObject(final XQConnection connection,
			final String filePath, final CodeSearchEngine searchEngine) {
		super(connection, filePath, searchEngine);
	}

	@Override
	public List<Field> parse(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		final List<Field> fields = new ArrayList<Field>();
		Type fieldType = null;
		Type declaringType = null;
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.START_ELEMENT) {
				final String localName = xmlReader.getLocalName();
				switch (localName) {
				case "field_type":
					fieldType = parseFindType(xmlReader);
					break;
				case Constantes.CLASS:
					declaringType = parseFindType(xmlReader);
					break;
				case "field_name":
					final Field field = new Field();
					field.setDeclaringType(declaringType);
					field.setType(fieldType);
					field.setName(xmlReader.getElementText());
					fields.add(field);
					break;
				}

			} else if (eventType == XMLStreamReader.END_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case Constantes.FIELD_LIST:
					return fields;
				}
			}
		}
		return null;
	}

}
