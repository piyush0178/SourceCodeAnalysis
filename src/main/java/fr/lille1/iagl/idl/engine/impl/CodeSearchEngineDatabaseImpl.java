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

	private static final String FUNCTION_LIST = "function_list";
	private static final String TYPE = "type";
	private static final String PARAM = "param";
	private static final String FUNCTION = "function";
	private static final String SPECIFIER = "specifier";
	private static final String TYPE_NAME = "type_name";
	private static final String METHOD_NAME = "method_name";
	private static final String PARAMETER_LIST = "parameter_list";

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
		// TODO JIV : ecrire Junit
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

			final XQResultSequence resultSequence = preparedQuery
					.executeQuery();

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
