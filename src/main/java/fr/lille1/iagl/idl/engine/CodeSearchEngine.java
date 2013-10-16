package fr.lille1.iagl.idl.engine;

import java.util.List;

/**
 * CodeQuery specifies a list of methods for querying source code. It is
 * designed with Java in mind but should work with many other languages as well.
 * The metamodel of code abstracts over the language (see in TypeKind for
 * example). The queries are inspired from Eclipse Java Search.
 * 
 * The code query engine can either: - works in one-shot mode (see
 * {@link CodeSearchEngineFile} - loads and pre-computes some data, in this case
 * T may represent your own optimized data structure
 * 
 * @author Martin Monperrus <martin.monperrus@univ-lille1.fr>
 * 
 */
public interface CodeSearchEngine {

	enum TypeKind {
		CLASS, INTERFACE, ENUM, PRIMITIVE, EXCEPTION, ANNOTATION
	}

	interface Location {
		/**
		 * returns a path of the file containing a code element. E.g.
		 * "./foo/Toto.java"
		 */
		String getFilePath();

		int getLineNumber(); // optional
	}

	interface Type {
		String getName();

		String getFullyQualifiedPackageName();

		TypeKind getKind(); // optional

		Location getDeclaration();
	}

	interface Member {
		/** returns the class that defines this method */
		Type getDeclaringType();

		/** returns the class of the field or the returned object */
		Type getType();

		/** returns the name of this member (e.g. the field name) */
		String getName();
	}

	interface Field extends Member {
	}

	interface Method extends Member {
		List<Type> getParamaters();
	}

	/**
	 * returns the type (and its location through getLocation) of type typeName
	 * 
	 * @param typeName
	 * @return
	 */
	Type findType(String typeName);

	/**
	 * returns all subtypes of type typeName
	 * 
	 * @param typeName
	 * @return
	 */
	List<Type> findSubTypesOf(String typeName);

	/**
	 * returns all fields typed with typeName
	 * 
	 * @param typeName
	 * @return
	 */
	List<Field> findFieldsTypedWith(String typeName);

	/**
	 * returns all read accesses of the field given as parameter
	 * 
	 * @param field
	 * @return
	 */
	List<Location> findAllReadAccessesOf(Field field);

	/**
	 * returns all write accesses of the field given as parameter (this.foo =
	 * ... )
	 * 
	 * @param field
	 * @return
	 */
	List<Location> findAllWriteAccessesOf(Field field);

	/**
	 * returns all methods of typeName (does not consider the inherited methods)
	 * 
	 * @param typeName
	 * @return
	 */
	List<Method> findMethodsOf(String typeName);

	/**
	 * returns all methods returning typeName
	 * 
	 * @param typeName
	 * @return
	 */
	List<Method> findMethodsReturning(String typeName);

	/**
	 * returns all methods using typeName as parameter
	 * 
	 * @param typeName
	 * @return
	 */
	List<Method> findMethodsTakingAsParameter(String typeName);

	/**
	 * returns all methods called methodName
	 * 
	 * @param methodName
	 * @return
	 */
	List<Method> findMethodsCalled(String methodName);

	/**
	 * returns all methods overriding the given method
	 * 
	 * @param method
	 * @return
	 */
	List<Method> findOverridingMethodsOf(Method method);

	/**
	 * returns all locations where there is an instance creation of typeName:
	 * new X()
	 * 
	 * @param className
	 * @return
	 */
	List<Location> findNewOf(String className);

	/**
	 * returns all locations where there is a cast to typeName
	 * 
	 * @param typeName
	 * @return
	 */
	List<Location> findCastsTo(String typeName);

	/**
	 * returns all locations where there is an instanceof check to typeName
	 * 
	 * @param typeName
	 * @return
	 */
	List<Location> findInstanceOf(String typeName);

	/**
	 * returns all methods throwing this exception
	 * 
	 * @param exceptionName
	 * @return
	 */
	List<Method> findMethodsThrowing(String exceptionName);

	/**
	 * returns all locations where there is a cast to className
	 * 
	 * @param exceptionName
	 * @return
	 */
	List<Location> findCatchOf(String exceptionName);

	/**
	 * returns all classes annotated with annotationName
	 * 
	 * @param annotationName
	 * @return
	 */
	@Deprecated
	List<Type> findClassesAnnotatedWith(String annotationName);
}
