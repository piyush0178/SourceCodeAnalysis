package fr.lille1.iagl.idl.engine.impl;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;

import org.apache.commons.lang3.StringUtils;
import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

import fr.lille1.iagl.idl.bean.Field;
import fr.lille1.iagl.idl.bean.Location;
import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.bean.PrimitiveType;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.bean.UnknowType;
import fr.lille1.iagl.idl.constantes.JavaKeyword;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;
import fr.lille1.iagl.idl.engine.parser.QueryAnswerParser;
import fr.lille1.iagl.idl.engine.queries.PreparedQueries;
import fr.lille1.iagl.idl.exception.WillNeverBeImplementedMethodException;

public class CodeSearchEngineDatabaseImpl implements CodeSearchEngine {

	private final XQConnection connection;

	private final String filePath;

	/**
	 * Object that provides parser for query answers
	 */
	private final QueryAnswerParser parser;

	private final PreparedQueries preparedQueries;

	/**
	 * cache de résultat des queries
	 */
	public static JCS cache;

	public static int cpt = 0;

	public CodeSearchEngineDatabaseImpl(final XQConnection connection,
			final String filePath) {
		this.connection = connection;
		this.filePath = filePath;
		parser = new QueryAnswerParser(this);
		preparedQueries = new PreparedQueries(connection, filePath);
		try {
			cache = JCS.getInstance("default");
		} catch (final CacheException e) {
			throw new RuntimeException(
					"Probléme lors l'instanciation du cache : "
							+ e.getMessage(), e);
		}
	}

	@Override
	public Type findType(final String typeName) {
		if (!isTypeNameValid(typeName)) {
			return null;
		}
		final Type typeCached = (Type) cache.get(typeName);
		if (typeCached != null) {
			return typeCached;
		}
		try {
			// Gestion des types primitifs
			if (JavaKeyword.getPrimitiveType(typeName) != null) {
				final PrimitiveType primitiveTypeCached = new PrimitiveType(
						typeName);
				cache.put(typeName, primitiveTypeCached);
				return primitiveTypeCached;
			}

			// FIXME : Pour l'instant dans la requète je ne gére que les class,
			// enum, interface et les primitives. Il manque les exceptions et
			// annotations.
			// FIXME : Je ne gére pas encore les numéros de lignes dans Location

			final XQPreparedExpression findTypePreparedQuery = preparedQueries
					.getFindTypePreparedQuery();
			findTypePreparedQuery.bindString(new QName("typeName"), typeName,
					null);
			final XMLStreamReader xmlReader = findTypePreparedQuery
					.executeQuery().getSequenceAsStream();

			cpt++;

			if (isResultEmpty(xmlReader)) {
				final Type unknowType = new UnknowType(typeName);
				cache.put(typeName, unknowType);
				return unknowType;
			} else {
				final Type typeResult = parser.parseFindTypeResults(xmlReader,
						typeName);
				cache.put(typeName, typeResult);
				return typeResult;
			}

		} catch (final XQException e) {
			throw new RuntimeException("fyndType(" + typeName + ") FAIL : "
					+ e.getMessage(), e);
		} catch (final XMLStreamException e) {
			throw new RuntimeException(
					"Probléme lors du parsing du XML : findType(" + typeName
							+ ") : " + e.getMessage(), e);
		} catch (final CacheException e) {
			throw new RuntimeException(
					"Probléme lors de la mise en cache du type : " + typeName
							+ ".\n Message de l'exception : " + e.getMessage(),
					e);
		}
	}

	/**
	 * Return true if the query result passed in parameter is empty.
	 * 
	 * @param sequenceAsStream
	 * @return
	 * @throws XMLStreamException
	 */
	private boolean isResultEmpty(final XMLStreamReader xmlReader)
			throws XMLStreamException {
		while (xmlReader.hasNext()) {
			xmlReader.next();
			final int eventType = xmlReader.getEventType();
			if (eventType == XMLStreamReader.START_ELEMENT) {
				return QueryAnswerParser.ERROR.equals(xmlReader.getLocalName());
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
	private boolean isTypeNameValid(final String typeName) {
		return StringUtils.isNotEmpty(typeName);
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
			final XQPreparedExpression preparedQuery = preparedQueries
					.getFindFieldsTypedWithQuery();
			preparedQuery.bindString(new QName("typeName"), typeName, null);
			System.out.println(preparedQuery.executeQuery()
					.getSequenceAsString(null));

			return parser.parseFieldsTypedWith(preparedQuery.executeQuery()
					.getSequenceAsStream(), typeName);
		} catch (XMLStreamException | XQException e) {
			throw new RuntimeException(e);
		}

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
			final XQPreparedExpression preparedQuery = preparedQueries
					.getFindMethodsTakingAsParameterQuery();

			preparedQuery.bindString(new QName("typeName"), typeName, null);

			return parser.parseFunctionToMethod(preparedQuery.executeQuery()
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

}
