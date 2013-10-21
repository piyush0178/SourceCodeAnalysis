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
import fr.lille1.iagl.idl.constantes.Constantes;
import fr.lille1.iagl.idl.constantes.JavaKeyword;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;
import fr.lille1.iagl.idl.engine.methodQueries.FindFieldsTypedWithObject;
import fr.lille1.iagl.idl.engine.methodQueries.FindMethodsTakingAsParameterObject;
import fr.lille1.iagl.idl.engine.methodQueries.FindSubTypesOfObject;
import fr.lille1.iagl.idl.engine.methodQueries.FindTypeObject;
import fr.lille1.iagl.idl.exception.WillNeverBeImplementedMethodException;

public class CodeSearchEngineDatabaseImpl implements CodeSearchEngine {

	XQConnection connection;

	String filePath;

	/**
	 * cache de résultat des queries
	 */
	public static JCS cache;

	// TODO JIV : supprimer
	public static int cpt = 0;

	private FindTypeObject findTypeObject;
	private FindSubTypesOfObject findSubTypesOfObject;
	private FindMethodsTakingAsParameterObject findMethodsTakingAsParameterObject;
	private FindFieldsTypedWithObject findFieldsTypedWithObject;

	public CodeSearchEngineDatabaseImpl(final XQConnection connection,
			final String filePath) {
		if (connection == null || StringUtils.isEmpty(filePath)) {
			throw new RuntimeException("Il manque des paramètre !");
		}

		this.connection = connection;
		this.filePath = filePath;

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
			if (findTypeObject == null) {
				findTypeObject = new FindTypeObject(connection, filePath, this,
						typeName);
			}

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

			final XQPreparedExpression findTypePreparedQuery = findTypeObject
					.getPreparedQuery();
			findTypePreparedQuery.bindString(new QName(Constantes.TYPE_NAME),
					typeName, null);
			final XMLStreamReader xmlReader = findTypePreparedQuery
					.executeQuery().getSequenceAsStream();

			cpt++;

			if (isResultEmpty(xmlReader)) {
				final Type unknowType = new UnknowType(typeName);
				cache.put(typeName, unknowType);
				return unknowType;
			} else {
				final Type typeResult = findTypeObject.parse(xmlReader);
				cache.put(typeName, typeResult);
				return typeResult;
			}
		} catch (final XQException | XMLStreamException | CacheException e) {
			throw new RuntimeException("ERREUR : findType( " + typeName
					+ " ) : " + e.getMessage(), e);
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
				return Constantes.ERROR.equals(xmlReader.getLocalName());
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
		try {
			if (findSubTypesOfObject == null) {
				findSubTypesOfObject = new FindSubTypesOfObject(connection,
						typeName, this);
			}

			final XQPreparedExpression preparedQuery = findSubTypesOfObject
					.getPreparedQuery();

			preparedQuery.bindString(new QName(Constantes.TYPE_NAME), typeName,
					null);

			return findSubTypesOfObject.parse(preparedQuery.executeQuery()
					.getSequenceAsStream());

		} catch (final XQException | XMLStreamException e) {
			throw new RuntimeException("ERREUR : findSubTypesOf( " + typeName
					+ " ) : " + e.getMessage(), e);
		}

	}

	@Override
	public List<Field> findFieldsTypedWith(final String typeName) {
		try {
			if (findFieldsTypedWithObject == null) {
				findFieldsTypedWithObject = new FindFieldsTypedWithObject(
						connection, filePath, this, typeName);
			}

			final XQPreparedExpression preparedQuery = findFieldsTypedWithObject
					.getPreparedQuery();

			preparedQuery.bindString(new QName(Constantes.TYPE_NAME), typeName,
					null);

			// TODO RAL : Supprimer
			System.out.println(preparedQuery.executeQuery()
					.getSequenceAsString(null));

			return findFieldsTypedWithObject.parse(preparedQuery.executeQuery()
					.getSequenceAsStream());

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
			if (findMethodsTakingAsParameterObject == null) {
				findMethodsTakingAsParameterObject = new FindMethodsTakingAsParameterObject(
						connection, filePath, this);
			}

			final XQPreparedExpression preparedQuery = findMethodsTakingAsParameterObject
					.getPreparedQuery();

			preparedQuery.bindString(new QName(Constantes.TYPE_NAME), typeName,
					null);

			return findMethodsTakingAsParameterObject.parse(preparedQuery
					.executeQuery().getSequenceAsStream());

		} catch (final XQException | XMLStreamException e) {
			throw new RuntimeException(
					"ERREUR : findMethodsTakingAsParameter( " + typeName
							+ " ) : " + e.getMessage(), e);
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
