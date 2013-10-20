package fr.lille1.iagl.idl.engine.impl;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;

import org.apache.commons.lang3.StringUtils;

import fr.lille1.iagl.idl.bean.Field;
import fr.lille1.iagl.idl.bean.Location;
import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.bean.PrimitiveType;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.constantes.JavaKeyword;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;
import fr.lille1.iagl.idl.engine.parser.QueryAnswerParser;
import fr.lille1.iagl.idl.engine.queries.Queries;
import fr.lille1.iagl.idl.exception.WillNeverBeImplementedMethodException;
import fr.lille1.iagl.idl.utils.PrimitiveTypesCache;

public class CodeSearchEngineDatabaseImpl implements CodeSearchEngine {

	private final XQConnection connection;

	private final String filePath;

	/**
	 * Prepared query of the fyndType method.<br>
	 * We keep it here for performances. This query will be very offen use.
	 */
	private XQPreparedExpression findTypeXQPreparedExpression;

	/**
	 * Object that provides parser for query answers
	 */
	private final QueryAnswerParser parser;

	public CodeSearchEngineDatabaseImpl(final XQConnection connection,
			final String filePath) {
		this.connection = connection;
		this.filePath = filePath;
		findTypeXQPreparedExpression = null;
		parser = new QueryAnswerParser(this);
	}

	@Override
	public Type findType(final String typeName) {
		if (!isTypeNameValid(typeName)) {
			return null;
		}
		if (JavaKeyword.getPrimitiveType(typeName) != null) {
			PrimitiveType primitiveTypeCached = PrimitiveTypesCache.cache
					.get(typeName);
			if (primitiveTypeCached == null) {
				primitiveTypeCached = new PrimitiveType(typeName);
				PrimitiveTypesCache.cache.put(typeName, primitiveTypeCached);
			}
			return primitiveTypeCached;
		}
		try {
			// FIXME : Pour l'instant dans la requète je ne gére que les class,
			// enum, interface et les primitives. Il manque les exceptions et
			// annotations.
			// FIXME : Je ne gére pas encore les numéros de lignes dans Location
			if (findTypeXQPreparedExpression == null) {
				// TODO : Penser à lancer une première requéte avant que le prof
				// prenne la main pr gagner quelques millisecondes.
				// la deuxiéme requête et toutes les suivantes seront plus
				// rapides.
				findTypeXQPreparedExpression = connection
						.prepareExpression(Queries.findTypeQuery);
				// on peut ne le binder que la première fois :)
				findTypeXQPreparedExpression.bindString(new QName("file"),
						filePath, null);
			}

			findTypeXQPreparedExpression.bindString(new QName("typeName"),
					typeName, null);

			if (isResultEmpty(findTypeXQPreparedExpression.executeQuery()
					.getSequenceAsStream())) {
				// FIXME : Trouver quoi faire dans ce cas la. Doit-on le gérer
				// ou doit-on lancer une erreur ? Pour l'instant je throw une
				// RuntimeException, ça nous aidera peut être à trouver les cas
				// à gérer.
				throw new RuntimeException(
						"Ce cas ne devrait pas arriver. Si il arrive c'est que nous devons le gérer. TypeName : "
								+ typeName);
			} else {
				return parser.parseFindTypeResults(findTypeXQPreparedExpression
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
		final String query = "doc('" + filePath + "')//unit/unit/class[name='"
				+ typeName + "']/block/decl_stmt/decl";

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

			final XQPreparedExpression preparedQuery = connection
					.prepareExpression(Queries.findMethodsTakingAsParameterQuery);

			preparedQuery.bindString(new QName("file"), filePath, null);
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
