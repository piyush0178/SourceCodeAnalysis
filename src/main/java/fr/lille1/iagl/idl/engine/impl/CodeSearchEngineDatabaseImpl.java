package fr.lille1.iagl.idl.engine.impl;

import java.util.List;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQResultSequence;

import fr.lille1.iagl.idl.exception.WillNeverBeImplementedMethodException;

public class CodeSearchEngineDatabaseImpl extends AbstractCodeSearchEngine {

	private final XQConnection connection;

	public CodeSearchEngineDatabaseImpl(final XQConnection connection) {
		this.connection = connection;
	}

	@Override
	public fr.lille1.iagl.idl.engine.CodeSearchEngine.Type findType(
			final String typeName) {
		XQExpression expression;
		try {
			expression = connection.createExpression();
			@SuppressWarnings("unused")
			final XQResultSequence results = expression.executeQuery("//unit");

		} catch (final XQException e) {
			throw new RuntimeException("Ca suxxxx !");
		}
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.engine.CodeSearchEngine.Type> findSubTypesOf(
			final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.engine.CodeSearchEngine.Field> findFieldsTypedWith(
			final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.engine.CodeSearchEngine.Location> findAllReadAccessesOf(
			final fr.lille1.iagl.idl.engine.CodeSearchEngine.Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.engine.CodeSearchEngine.Location> findAllWriteAccessesOf(
			final fr.lille1.iagl.idl.engine.CodeSearchEngine.Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.engine.CodeSearchEngine.Method> findMethodsOf(
			final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.engine.CodeSearchEngine.Method> findMethodsReturning(
			final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.engine.CodeSearchEngine.Method> findMethodsTakingAsParameter(
			final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.engine.CodeSearchEngine.Method> findMethodsCalled(
			final String methodName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.engine.CodeSearchEngine.Method> findOverridingMethodsOf(
			final fr.lille1.iagl.idl.engine.CodeSearchEngine.Method method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.engine.CodeSearchEngine.Location> findNewOf(
			final String className) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.engine.CodeSearchEngine.Location> findCastsTo(
			final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.engine.CodeSearchEngine.Location> findInstanceOf(
			final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.engine.CodeSearchEngine.Method> findMethodsThrowing(
			final String exceptionName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.engine.CodeSearchEngine.Location> findCatchOf(
			final String exceptionName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public List<fr.lille1.iagl.idl.engine.CodeSearchEngine.Type> findClassesAnnotatedWith(
			final String annotationName) {
		throw new WillNeverBeImplementedMethodException(
				"Méthode impossible à implémenter en utilisant SrcML");
	}
}
