package fr.lille1.iagl.idl.engine.impl;

import java.util.List;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

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

			type.setName(typeName);

			final String query = "for $x in doc('" + filePath
					+ "')//unit[class/name='" + typeName + "'] return $x";

			final XQPreparedExpression xqpe = connection
					.prepareExpression(query);

			final Long start = System.currentTimeMillis();
			final XQResultSequence results = xqpe.executeQuery();
			final Long end = System.currentTimeMillis();

			while (results.next()) {
				final String res = results.getItemAsString(null);
				System.out.println(res);
			}

			System.out.println((end - start));
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
		return null;
	}

	@Override
	public List<Method> findMethodsReturning(final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Method> findMethodsTakingAsParameter(final String typeName) {
		// TODO Auto-generated method stub
		return null;
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
