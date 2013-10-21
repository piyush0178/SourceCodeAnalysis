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
	private final String query = declareVariables
			+ " let $fields := "
			+ " 	for $class in doc($file)//class[.//block/decl_stmt/decl/type/name = $typeName]"
			+ " 	 return "
			+ " 		<class>"
			+ "  			 <class_name>{ $class/name/text() }</class_name>"
			+ " 				{"
			+ " 					for $field in $class//block/decl_stmt/decl[type/name=$typeName]"
			+ " 					return"
			+ " 						<name>{ $field/name/text() }</name>"
			+ " 				}"
			+ "			 </class>"
			+ " return "
			+ " <field_list>{$fields}</field_list>"
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
		Field field = null;
		final Type type = searchEngine.findType(typeName);
		Type declaringType = null;
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case Constantes.FIELD_LIST:
					return fields;
				}
			} else if (eventType == XMLStreamReader.START_ELEMENT) {
				final String localName = xmlReader.getLocalName();
				switch (localName) {
				case Constantes.CLASS_NAME:
					declaringType = searchEngine.findType(xmlReader
							.getElementText());
					break;
				case Constantes.NAME:
					field = new Field();
					field.setDeclaringType(type);
					field.setType(declaringType);
					field.setName(xmlReader.getElementText());
					fields.add(field);
					break;
				}

			}
		}
		return null;
	}

}
