/**
 * 
 */
package fr.lille1.iagl.idl.impl;

import java.util.List;

import fr.lille1.iagl.idl.CodeSearchEngine;

/**
 * @author ivanic
 * 
 */
public class CodeSearchEngineImpl implements CodeSearchEngine {

	public Type findType(final String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Type> findSubTypesOf(final String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Field> findFieldsTypedWith(final String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Location> findAllReadAccessesOf(final Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Location> findAllWriteAccessesOf(final Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Method> findMethodsOf(final String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Method> findMethodsReturning(final String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Method> findMethodsTakingAsParameter(final String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Method> findMethodsCalled(final String methodName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Method> findOverridingMethodsOf(final Method method) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Location> findNewOf(final String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Location> findCastsTo(final String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Location> findInstanceOf(final String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Method> findMethodsThrowing(final String exceptionName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Location> findCatchOf(final String exceptionName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Type> findClassesAnnotatedWith(final String annotationName) {
		// TODO Auto-generated method stub
		return null;
	}

}
