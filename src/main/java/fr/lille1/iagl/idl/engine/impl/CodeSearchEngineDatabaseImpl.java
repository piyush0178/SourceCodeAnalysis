package fr.lille1.iagl.idl.engine.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import fr.lille1.iagl.idl.bean.Field;
import fr.lille1.iagl.idl.bean.Location;
import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;
import fr.lille1.iagl.idl.exception.WillNeverBeImplementedMethodException;

public class CodeSearchEngineDatabaseImpl implements CodeSearchEngine {

	private final XQConnection connection;

	private final String filePath;

	public CodeSearchEngineDatabaseImpl(final XQConnection connection,
			final String filePath) {
		this.connection = connection;
		this.filePath = filePath;
	}

	@Override
	public Type findType(final String typeName) {
		final Type type = new Type();
		try {

			final String query = "for $x in doc('" + filePath
					+ "')//unit[class/name='" + typeName + "'] return $x";

			final XQResultSequence results = execute(query);

			while (results.next()) {
				final String res = results.getItemAsString(null);
				System.out.println(res);
			}
		} catch (final XQException e) {
			throw new RuntimeException("fyndType(" + typeName + ") FAIL :"
					+ e.getMessage(), e);
		}
		return type;
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
					+ "					<type>{$param/decl/type/name}</type>"
					+ "					<name>{$param/name}</name>"
					+ "				</param>"
					+ "			}"
					+ "			</parameter_list>"
					+ "		</function>"
					+ " return" + "	<functionList>{$functions}</functionList>";

			final XQPreparedExpression preparedQuery = connection
					.prepareExpression(query);

			preparedQuery.bindString(new QName("file"), filePath, null);
			preparedQuery.bindString(new QName("typeName"), typeName, null);

			final XQResultSequence resultSequence = preparedQuery
					.executeQuery();

			System.out.println(resultSequence.getSequenceAsString(null));

			return parseFunctionToMethod(resultSequence.getSequenceAsStream());
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
				case "function":
					// toutes les infos de la fonction été lu et enregistrer
					// dans l'object Method, on peut l'ajouter à la liste
					// résultat.
					methodList.add(method);
				default:
					break;
				}
			}
			if (eventType == XMLStreamReader.START_ELEMENT) {
				switch (xmlReader.getLocalName()) {
				case "function":
					method = new Method();
					break;
				case "specifier":
					method.setDeclaringType(findType(xmlReader.getElementText()));
					break;
				case "type_name":
					method.setDeclaringType(findType(xmlReader.getElementText()));
					break;
				case "method_name":
					method.setName(xmlReader.getElementText());
					break;
				default:
					break;
				}
			}
		}
		return methodList;
	}

	/**
	 * StAX based parser
	 * 
	 * @param xmlReader
	 * @throws XMLStreamException
	 */
	private Method parseMethodsTakingAsParameterResults(
			final XMLStreamReader xmlReader) throws XMLStreamException {
		final Method method = new Method();

		while (xmlReader.hasNext()) {
			xmlReader.next();
			switch (xmlReader.getEventType()) {
			case XMLStreamConstants.START_ELEMENT:
				switch (xmlReader.getLocalName()) {
				case "toto":
					break;

				}

				break;

			default:
				break;
			}
		}
		return method;

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
