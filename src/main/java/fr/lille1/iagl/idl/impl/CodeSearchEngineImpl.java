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

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.lille1.iagl.idl.CodeSearchEngine#findType(java.lang.String)
	 */
	public Type findType(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.lille1.iagl.idl.CodeSearchEngine#findSubTypesOf(java.lang.String)
	 */
	public List<Type> findSubTypesOf(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.lille1.iagl.idl.CodeSearchEngine#findFieldsTypedWith(java.lang.String)
	 */
	public List<Field> findFieldsTypedWith(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.lille1.iagl.idl.CodeSearchEngine#findAllReadAccessesOf(fr.lille1.iagl
	 * .idl.CodeSearchEngine.Field)
	 */
	public List<Location> findAllReadAccessesOf(Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.lille1.iagl.idl.CodeSearchEngine#findAllWriteAccessesOf(fr.lille1.
	 * iagl.idl.CodeSearchEngine.Field)
	 */
	public List<Location> findAllWriteAccessesOf(Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.lille1.iagl.idl.CodeSearchEngine#findMethodsOf(java.lang.String)
	 */
	public List<Method> findMethodsOf(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.lille1.iagl.idl.CodeSearchEngine#findMethodsReturning(java.lang.String
	 * )
	 */
	public List<Method> findMethodsReturning(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.lille1.iagl.idl.CodeSearchEngine#findMethodsTakingAsParameter(java
	 * .lang.String)
	 */
	public List<Method> findMethodsTakingAsParameter(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.lille1.iagl.idl.CodeSearchEngine#findMethodsCalled(java.lang.String)
	 */
	public List<Method> findMethodsCalled(String methodName) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.lille1.iagl.idl.CodeSearchEngine#findOverridingMethodsOf(fr.lille1
	 * .iagl.idl.CodeSearchEngine.Method)
	 */
	public List<Method> findOverridingMethodsOf(Method method) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.lille1.iagl.idl.CodeSearchEngine#findNewOf(java.lang.String)
	 */
	public List<Location> findNewOf(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.lille1.iagl.idl.CodeSearchEngine#findCastsTo(java.lang.String)
	 */
	public List<Location> findCastsTo(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.lille1.iagl.idl.CodeSearchEngine#findInstanceOf(java.lang.String)
	 */
	public List<Location> findInstanceOf(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.lille1.iagl.idl.CodeSearchEngine#findMethodsThrowing(java.lang.String)
	 */
	public List<Method> findMethodsThrowing(String exceptionName) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.lille1.iagl.idl.CodeSearchEngine#findCatchOf(java.lang.String)
	 */
	public List<Location> findCatchOf(String exceptionName) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.lille1.iagl.idl.CodeSearchEngine#findClassesAnnotatedWith(java.lang
	 * .String)
	 */
	public List<Type> findClassesAnnotatedWith(String annotationName) {
		// TODO Auto-generated method stub
		return null;
	}

}
