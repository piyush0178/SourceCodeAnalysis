/**
 * 
 */
package fr.lille1.iagl.idl.impl;

import java.io.File;
import java.util.List;

/**
 * @author ivanic
 * 
 */
public class CodeSearchEngineFileImpl extends CodeSearchEngineImpl<File> {

	@Override
	public fr.lille1.iagl.idl.CodeSearchEngine.Type findType(
			final String typeName, final File data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Type> findSubTypesOf(
			final String typeName, final File data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Field> findFieldsTypedWith(
			final String typeName, final File data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findAllReadAccessesOf(
			final fr.lille1.iagl.idl.CodeSearchEngine.Field field,
			final File data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findAllWriteAccessesOf(
			final fr.lille1.iagl.idl.CodeSearchEngine.Field field,
			final File data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findMethodsOf(
			final String typeName, final File data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findMethodsReturning(
			final String typeName, final File data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findMethodsTakingAsParameter(
			final String typeName, final File data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findMethodsCalled(
			final String methodName, final File data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findOverridingMethodsOf(
			final fr.lille1.iagl.idl.CodeSearchEngine.Method method,
			final File data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findNewOf(
			final String className, final File data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findCastsTo(
			final String typeName, final File data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findInstanceOf(
			final String typeName, final File data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Method> findMethodsThrowing(
			final String exceptionName, final File data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Location> findCatchOf(
			final String exceptionName, final File data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<fr.lille1.iagl.idl.CodeSearchEngine.Type> findClassesAnnotatedWith(
			final String annotationName, final File data) {
		// TODO Auto-generated method stub
		return null;
	}

}
