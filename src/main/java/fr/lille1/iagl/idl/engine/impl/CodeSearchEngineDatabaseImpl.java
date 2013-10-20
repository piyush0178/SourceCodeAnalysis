package fr.lille1.iagl.idl.engine.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import fr.lille1.iagl.idl.bean.Field;
import fr.lille1.iagl.idl.bean.Location;
import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.bean.TypeKind;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;
import fr.lille1.iagl.idl.exception.WillNeverBeImplementedMethodException;

public class CodeSearchEngineDatabaseImpl implements CodeSearchEngine {

	private static final String PATH = "path";
	private static final String KIND = "kind";
	private static final String TYPE = "type";
	private static final String ERROR = "error";
	private static final String PACKAGE = "package";
	private static final String LOCATION = "location";
	private static final String FUNCTION = "function";
	private static final String SPECIFIER = "specifier";
	private static final String TYPE_NAME = "type_name";
	private static final String LINE_NUMBER = "line_number";
	private static final String METHOD_NAME = "method_name";
	private static final String FUNCTION_LIST = "function_list";
	private static final String PARAMETER_LIST = "parameter_list";

	private final XQConnection connection;

	private final String filePath;

	/**
	 * Prepared query of the fyndType method.<br>
	 * We keep it here for performances. This query will be very offen use.
	 */
	private XQPreparedExpression findTypeXQPreparedExpression;

	public CodeSearchEngineDatabaseImpl(final XQConnection connection,
			final String filePath) {
		this.connection = connection;
		this.filePath = filePath;
		findTypeXQPreparedExpression = null;
	}

	@Override
	public Type findType(final String typeName) {
		if (!typeNameIsValid(typeName)) {
			return null;
		}
		try {
			// FIXME : Pour l'instant dans la requète je ne gére que les class,
			// enum et interface. Il manque les primitives, exceptions et
			// annotations.
			// FIXME : Je ne gére pas encore les numéros de lignes dans Location
			final String query = "declare variable $file as xs:string external;"
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
					+ "				let $kind :="
					+ "					if($unit/class) then 'class' "
					+ "					else if($unit/enum) then 'enum'"
					+ "					else if($unit/interface) then 'interface'"
					+ "					else ''"
					+ "				return $kind"
					+ "			}"
					+ "			</kind>"
					+ "		</type>"
					+ "	return"
					+ "		if(count($result) eq 0) then <error>The query returned nothing</error>"
					+ "		else $result";

			// la deuxiéme requête et toutes les suivantes seront plus rapides.
			// TODO : Penser à lancer une première requéte avant que le prof
			// prenne la main pr gagner quelques millisecondes.
			if (findTypeXQPreparedExpression == null) {
				findTypeXQPreparedExpression = connection
						.prepareExpression(query);
				// on peut ne le binder que la première fois :)
				findTypeXQPreparedExpression.bindString(new QName("file"),
						filePath, null);
			}

			findTypeXQPreparedExpression.bindString(new QName("typeName"),
					typeName, null);

			if (resultIsEmpty(findTypeXQPreparedExpression.executeQuery()
					.getSequenceAsStream())) {

				// FIXME (Important) : il faudra ajouter des régles
				// supplémentaires ici. Par exemple, si le mec envoie
				// findType('void'), il faut créer le type void et le mettre en
				// cache ou je ne sais ou. Pour l'instant ça renvoie null;

				return null;
			} else {

				// FIXME : vérifier que cela ne pose jamais de probléme de passé
				// le typeName comme cela. On sait déja qui si on se trouve dans
				// cette branche de ce if c'est que ce typeName existe bien dans
				// le fichier. 9a réduit les risques mais j'ai peut être raté qq
				// chose.

				return parseFindTypeResults(findTypeXQPreparedExpression
						.executeQuery().getSequenceAsStream(), typeName);
			}

		} catch (final XQException e) {
			throw new RuntimeException("fyndType(" + typeName + ") FAIL : "
					+ e.getMessage(), e);
		} catch (final XMLStreamException e) {
			throw new RuntimeException(
					"Probléme lors du parsing du XML : findType(" + typeName
							+ ") : " + e.getMessage(), e);
		}
	}

	/**
	 * Return true if the query result passed in parameter is empty.
	 * 
	 * @param sequenceAsStream
	 * @return
	 * @throws XMLStreamException
	 */
	private boolean resultIsEmpty(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.START_ELEMENT) {
				return ERROR.equals(xmlReader.getLocalName());
			}
		}
		throw new RuntimeException("This case will never append");
	}

	/**
	 * Return true if the typeName is correct according to our rules.
	 * 
	 * @param typeName
	 * @return
	 */
	private boolean typeNameIsValid(final String typeName) {
		return StringUtils.isNotEmpty(typeName);
	}

	/**
	 * TODO JIV : documentation
	 * 
	 * @param sequenceAsStream
	 * @return
	 * @throws XMLStreamException
	 */
	private Type parseFindTypeResults(final XMLStreamReader xmlReader,
			final String typeName) throws XMLStreamException {
		final Type typeRes = new Type();
		typeRes.setName(typeName);
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.END_ELEMENT
					&& TYPE.equals(xmlReader.getLocalName())) {
				return typeRes;
			}
			if (eventType == XMLStreamReader.START_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case LOCATION:
					typeRes.setDeclaration(parseLocation(xmlReader));
					break;
				case PACKAGE:
					typeRes.setFullyQualifiedPackageName(xmlReader
							.getElementText());
					break;
				case KIND:
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
					&& LOCATION.equals(xmlReader.getLocalName())) {
				return location;
			}
			if (eventType == XMLStreamReader.START_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case PATH:
					location.setFilePath(xmlReader.getElementText());
					break;
				case LINE_NUMBER:
					// FIXME : pas encore géré ! (comme dans la requête
					// d'ailleurs)
					break;
				}
			}
		}
		throw new RuntimeException("This case will never append");
	}

	@Override
	public List<Type> findSubTypesOf(final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Field> findFieldsTypedWith(final String typeName) {
		// TODO Auto-generated method stub
		try {
			final String query = "doc('" + filePath
					+ "')//unit/unit/class[name='" + typeName
					+ "']/block/decl_stmt/decl";
			final XQResultSequence results = execute(query);

			final XMLReader saxReader = XMLReaderFactory.createXMLReader();
			saxReader.setContentHandler(new DefaultHandler());
		} catch (XQException | SAXException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	@Override
	public List<Location> findAllReadAccessesOf(final Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findAllWriteAccessesOf(final Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Method> findMethodsOf(final String typeName) {
		// TODO Auto-generated method stub
		// requete xquery :
		// //unit/class/block/function[type/name="typeName"]/name
		return null;
	}

	@Override
	public List<Method> findMethodsReturning(final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	// Jules

	@Override
	public List<Method> findMethodsTakingAsParameter(final String typeName) {
		// TODO JIV : ecrire Junit
		// FIXME JIV : il peut y avoir plusieurs "specifier"
		/*
		 * ex : //unit[enum]
		 * 
		 * <specifier>private</specifier> <specifier>static</specifier>
		 */
		// FIXME JIV : il peut y avoir plusieurs "type_name"
		/*
		 * ex : //unit[enum]
		 * 
		 * <name>native</name> <name>void</name>
		 */
		// FIXME JIV : il y a des déclarations de fonctions dans les interfaces
		// et les classes abstraitres : <function_decl>
		/* ex : //unit[enum] */
		try {
			final String query = "declare variable $file as xs:string external;"
					+ " declare variable $typeName as xs:string external;"
					+ " let $functions := "
					+ "		for $function in doc($file)//unit/unit/function[parameter_list/param/decl/type/name = $typeName]"
					+ " 	return"
					+ " 	<function>"
					+ "			<specifier>{$function/type/specifier/text()}</specifier>"
					+ "			<type_name>{$function/type/name/text()}</type_name>"
					+ "			<method_name>{$function/name/text()}</method_name>"
					+ " 		<parameter_list>"
					+ "			{"
					+ "				for $param in $function/parameter_list/param"
					+ "				return"
					+ "				<param>"
					+ "					<type>{$param/decl/type/name/text()}</type>"
					+ "				</param>"
					+ "			}"
					+ "			</parameter_list>"
					+ "		</function>"
					+ " return"
					+ "	<function_list>{$functions}</function_list>";

			final XQPreparedExpression preparedQuery = connection
					.prepareExpression(query);

			preparedQuery.bindString(new QName("file"), filePath, null);
			preparedQuery.bindString(new QName("typeName"), typeName, null);

			return parseFunctionToMethod(preparedQuery.executeQuery()
					.getSequenceAsStream());

		} catch (final XQException e) {
			throw new RuntimeException(
					"Probléme lors la requête  : findMethodsTakingAsParameter("
							+ typeName + ") : " + e.getMessage(), e);
		} catch (final XMLStreamException e) {
			throw new RuntimeException(
					"Probléme lors du parsing du XML : findMethodsTakingAsParameter("
							+ typeName + ") : " + e.getMessage(), e);
		}
	}

	/**
	 * TODO JIV : documentation
	 * 
	 * @param xmlReader
	 * @throws XMLStreamException
	 */
	private List<Method> parseFunctionToMethod(final XMLStreamReader xmlReader)
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
				case FUNCTION_LIST:
					return methodList;
				}
			}
			if (eventType == XMLStreamReader.START_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case FUNCTION:
					method = new Method();
					break;
				case SPECIFIER:
					method.setDeclaringType(findType(xmlReader.getElementText()));
					break;
				case TYPE_NAME:
					method.setDeclaringType(findType(xmlReader.getElementText()));
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
	 * TODO JIV : documentation
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
			}
			if (eventType == XMLStreamReader.START_ELEMENT
					&& TYPE.equals(xmlReader.getLocalName())) {
				paramList.add(findType(xmlReader.getElementText()));
			}
		}
		throw new RuntimeException("This case will never append");
	}

	@Override
	public List<Method> findMethodsCalled(final String methodName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Method> findOverridingMethodsOf(final Method method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findNewOf(final String className) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findCastsTo(final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findInstanceOf(final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Method> findMethodsThrowing(final String exceptionName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findCatchOf(final String exceptionName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public List<Type> findClassesAnnotatedWith(final String annotationName) {
		throw new WillNeverBeImplementedMethodException(
				"Méthode impossible à implémenter en utilisant SrcML");
	}

	/**
	 * Execute la query Xquery passé en paramétre.
	 * 
	 * @param query
	 * @return
	 * @throws XQException
	 */
	private XQResultSequence execute(final String query) throws XQException {
		XQPreparedExpression xqpe;
		xqpe = connection.prepareExpression(query);
		return xqpe.executeQuery();
	}

}
