package fr.lille1.iagl.idl.engine;

import java.util.List;

import fr.lille1.iagl.idl.bean.Field;
import fr.lille1.iagl.idl.bean.Location;
import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.bean.Type;

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

	/**
	 * returns the type (and its location through getLocation) of type typeName
	 * 
	 * @param typeName
	 * @return
	 */
	Type findType(final String typeName);

	/**
	 * returns all subtypes of type typeName
	 * 
	 * @param typeName
	 * @return
	 */
	List<Type> findSubTypesOf(final String typeName);

	/**
	 * returns all fields typed with typeName
	 * 
	 * @param typeName
	 * @return
	 */
	List<Field> findFieldsTypedWith(final String typeName);

	/**
	 * returns all read accesses of the field given as parameter
	 * 
	 * @param field
	 * @return
	 */
	List<Location> findAllReadAccessesOf(final Field field);

	/**
	 * returns all write accesses of the field given as parameter (this.foo =
	 * ... )
	 * 
	 * @param field
	 * @return
	 */
	List<Location> findAllWriteAccessesOf(final Field field);

	/**
	 * returns all methods of typeName (does not consider the inherited methods)
	 * 
	 * @param typeName
	 * @return
	 */
	List<Method> findMethodsOf(final String typeName);

	/**
	 * returns all methods returning typeName
	 * 
	 * @param typeName
	 * @return
	 */
	List<Method> findMethodsReturning(final String typeName);

	// Jules

	/**
	 * returns all methods using typeName as parameter
	 * 
	 * @param typeName
	 * @return
	 */
	List<Method> findMethodsTakingAsParameter(final String typeName);

	/**
	 * returns all methods called methodName
	 * 
	 * @param methodName
	 * @return
	 */
	List<Method> findMethodsCalled(final String methodName);

	/**
	 * returns all methods overriding the given method
	 * 
	 * @param method
	 * @return
	 */
	List<Method> findOverridingMethodsOf(final Method method);

	/**
	 * returns all locations where there is an instance creation of typeName:
	 * new X()
	 * 
	 * @param className
	 * @return
	 */
	List<Location> findNewOf(final String className);

	/**
	 * returns all locations where there is a cast to typeName
	 * 
	 * @param typeName
	 * @return
	 */
	List<Location> findCastsTo(final String typeName);

	/**
	 * returns all locations where there is an instanceof check to typeName
	 * 
	 * @param typeName
	 * @return
	 */
	List<Location> findInstanceOf(final String typeName);

	/**
	 * returns all methods throwing this exception
	 * 
	 * @param exceptionName
	 * @return
	 */
	List<Method> findMethodsThrowing(final String exceptionName);

	/**
	 * returns all locations where there is a cast to className
	 * 
	 * @param exceptionName
	 * @return
	 */
	List<Location> findCatchOf(final String exceptionName);

	/**
	 * returns all classes annotated with annotationName
	 * 
	 * @param annotationName
	 * @return
	 */
	@Deprecated
	List<Type> findClassesAnnotatedWith(final String annotationName);
}
