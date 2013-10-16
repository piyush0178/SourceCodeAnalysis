package fr.lille1.iagl.idl.impl;

import java.util.List;

import fr.lille1.iagl.idl.CodeSearchEngine;
import fr.lille1.iagl.idl.utils.DatabaseConnection;

public class CodeSearchEngineDatabaseImpl implements
		CodeSearchEngine<DatabaseConnection> {

	@Override
	public fr.lille1.iagl.idl.CodeSearchEngine.Type findType(
			final String typeName, final DatabaseConnection data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Type> findSubTypesOf(
			final String typeName, final DatabaseConnection data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Field> findFieldsTypedWith(
			final String typeName, final DatabaseConnection data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findAllReadAccessesOf(
			final fr.lille1.iagl.idl.CodeSearchEngine.Field field,
			final DatabaseConnection data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findAllWriteAccessesOf(
			final fr.lille1.iagl.idl.CodeSearchEngine.Field field,
			final DatabaseConnection data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findMethodsOf(
			final String typeName, final DatabaseConnection data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findMethodsReturning(
			final String typeName, final DatabaseConnection data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findMethodsTakingAsParameter(
			final String typeName, final DatabaseConnection data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findMethodsCalled(
			final String methodName, final DatabaseConnection data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findOverridingMethodsOf(
			final fr.lille1.iagl.idl.CodeSearchEngine.Method method,
			final DatabaseConnection data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findNewOf(
			final String className, final DatabaseConnection data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findCastsTo(
			final String typeName, final DatabaseConnection data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findInstanceOf(
			final String typeName, final DatabaseConnection data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findMethodsThrowing(
			final String exceptionName, final DatabaseConnection data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findCatchOf(
			final String exceptionName, final DatabaseConnection data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Type> findClassesAnnotatedWith(
			final String annotationName, final DatabaseConnection data) {
		// TODO Auto-generated method stub
		return null;
	}

}
