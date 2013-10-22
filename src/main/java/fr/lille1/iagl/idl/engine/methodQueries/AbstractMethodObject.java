package fr.lille1.iagl.idl.engine.methodQueries;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;

import lombok.Getter;
import fr.lille1.iagl.idl.bean.Location;
import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.bean.PrimitiveType;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.bean.TypeKind;
import fr.lille1.iagl.idl.bean.UnknowType;
import fr.lille1.iagl.idl.constantes.Constantes;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;

/**
 * AbstractMethodObject<T> class.
 */
public abstract class AbstractMethodObject<T> {

	public final static String declareVariables = "declare variable $file as xs:string external;"
			+ " declare variable $typeName as xs:string external;";

	public final static String findTypeMethod = declareVariables
			+ " declare function local:findType($root, $name as xs:string)"
			+ " {"
			+ " 	if ($name = 'void' or $name = 'char' or $name = 'boolean' "
			+ "			or $name = 'byte' or $name = 'double' or $name = 'float'"
			+ "			or $name = 'int' or $name = 'long' or $name = 'short')"
			+ "		then <primitive>{$name}</primitive>"
			+ "		else"
			+ "		for $unit in $root/unit//unit[class/name = $name]"
			+ " 	return"
			+ "		<type>"
			+ "			<name>{$name}</name>"
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
			+ "	}; "
			+ "(: Commentaire inutile permettant de garder le formatage du code mm avec ma save action :)";

	protected XQConnection connection;
	protected String filePath;
	final CodeSearchEngine searchEngine;

	@Getter
	protected XQPreparedExpression preparedQuery;

	/**
	 * Constructor
	 * 
	 * @param connection
	 * @param filePath
	 * @param searchEngine
	 */
	public AbstractMethodObject(final XQConnection connection,
			final String filePath, final CodeSearchEngine searchEngine) {
		this.connection = connection;
		this.filePath = filePath;
		this.searchEngine = searchEngine;
		try {
			prepareQuery();
		} catch (final XQException e) {
			throw new RuntimeException("ERREUR : " + e.getMessage(), e);
		}
	}

	/**
	 * Parser for the result of the query
	 * 
	 * @return
	 */
	public abstract T parse(final XMLStreamReader xmlReader)
			throws XMLStreamException;

	/**
	 * TODO : doc
	 * 
	 * @return
	 */
	protected abstract String getQuery();

	/**
	 * Initialize the prepared Query.
	 * 
	 * @throws XQException
	 */
	private void prepareQuery() throws XQException {
		if (getQuery() == null) {
			throw new RuntimeException("You have to redefine the query field !");
		}
		preparedQuery = connection.prepareExpression(getQuery());
		preparedQuery.bindString(new QName("file"), filePath, null);
	}

	/**
	 * TODO RAL : commentaire
	 * 
	 * @param xmlReader
	 * @return
	 * @throws XMLStreamException
	 */
	protected List<Method> parseMethods(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		final List<Method> methodList = new ArrayList<Method>();
		Method method = null;
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case Constantes.TYPE_NAME:
					break;
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
					method.setType(parseFindType(xmlReader));
					break;
				case Constantes.TYPE_NAME:
					method.setDeclaringType(parseFindType(xmlReader));
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
	protected List<Type> parseFunctionParameterList(
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
					&& Constantes.PARAM.equals(xmlReader.getLocalName())) {
				paramList.add(parseFindType(xmlReader));
			}
		}
		throw new RuntimeException("This case will never append");
	}

	/**
	 * Parse un type
	 * 
	 * @param xmlReader
	 * @return
	 * @throws XMLStreamException
	 */
	protected Type parseFindType(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		Type typeRes = null;
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT
					&& Constantes.TYPE.equals(xmlReader.getLocalName())) {
				return typeRes;
			} else if (eventType == XMLStreamReader.START_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case "primitive":
					return new PrimitiveType(xmlReader.getElementText());
				case Constantes.ERROR:
					// la base de donnée n'a rien répondu
					return new UnknowType(xmlReader.getElementText());
				case Constantes.TYPE:
					typeRes = new Type();
					break;
				case Constantes.NAME:
					typeRes.setName(xmlReader.getElementText());
					break;
				case Constantes.LOCATION:
					typeRes.setDeclaration(parseLocation(xmlReader));
					break;
				case Constantes.PACKAGE:
					typeRes.setFullyQualifiedPackageName(xmlReader
							.getElementText());
					break;
				case Constantes.KIND:
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
	private Location parseLocation(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		final Location location = new Location();
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT
					&& Constantes.LOCATION.equals(xmlReader.getLocalName())) {
				return location;
			} else if (eventType == XMLStreamReader.START_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case Constantes.PATH:
					location.setFilePath(xmlReader.getElementText());
					break;
				case Constantes.LINE_NUMBER:
					// FIXME : pas encore géré ! (comme dans la requête
					// d'ailleurs)
					break;
				}
			}
		}
		throw new RuntimeException("This case will never append");
	}

}
