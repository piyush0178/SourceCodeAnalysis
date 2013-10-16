package fr.lille1.iagl.idl.impl;

import java.util.List;

import javax.xml.xquery.XQConnection;

import fr.lille1.iagl.idl.utils.DatabaseConnection;

public class CodeSearchEngineDatabaseImpl extends AbstractCodeSearchEngine {

	private final XQConnection connection;

	public CodeSearchEngineDatabaseImpl() {
		connection = DatabaseConnection.getConnection();
	}

	@Override
	public fr.lille1.iagl.idl.CodeSearchEngine.Type findType(
			final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Type> findSubTypesOf(
			final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Field> findFieldsTypedWith(
			final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findAllReadAccessesOf(
			final fr.lille1.iagl.idl.CodeSearchEngine.Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findAllWriteAccessesOf(
			final fr.lille1.iagl.idl.CodeSearchEngine.Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findMethodsOf(
			final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findMethodsReturning(
			final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findMethodsTakingAsParameter(
			final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findMethodsCalled(
			final String methodName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findOverridingMethodsOf(
			final fr.lille1.iagl.idl.CodeSearchEngine.Method method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findNewOf(
			final String className) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findCastsTo(
			final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findInstanceOf(
			final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findMethodsThrowing(
			final String exceptionName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findCatchOf(
			final String exceptionName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Type> findClassesAnnotatedWith(
			final String annotationName) {
		// TODO Auto-generated method stub
		return null;
	}

}
