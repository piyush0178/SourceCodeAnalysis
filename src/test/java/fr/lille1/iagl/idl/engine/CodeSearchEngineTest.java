/**
 * 
 */
package fr.lille1.iagl.idl.engine;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.constantes.Constantes;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;
import fr.lille1.iagl.idl.engine.impl.CodeSearchEngineDatabaseImpl;
import fr.lille1.iagl.idl.utils.DatabaseConnection;

/**
 * @author ivanic
 * 
 */
@RunWith(JUnit4.class)
public class CodeSearchEngineTest {

	static CodeSearchEngine engine;

	@BeforeClass
	public static void initializeTests() {
		engine = new CodeSearchEngineDatabaseImpl(
				DatabaseConnection.getConnection(), Constantes.JAVA_XML);
	}

	@AfterClass
	public static void fisnishTests() {
		DatabaseConnection.closeConnection();
	}

	@Test
	public void testFindType() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	/**
	 * Je fait deux appels consécutifs. Le bind de "file" dans la requête n'est
	 * fait qu'une fois et ça ne plante pas.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindTypeBindOnlyOneTimeFilePath() throws Exception {
		engine.findType("ObjectInputStream");
		engine.findType("String");
	}

	@Test
	public void testFindTypeWithJavaKeyword() throws Exception {
		final Type type = engine.findType("void");
		Assert.assertNotNull(type);
		Assert.assertEquals("void", type.getName());
	}

	@Test
	public void testFindSubTypesOf() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testFindFieldsTypedWith() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testFindAllReadAccessesOf() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testFindAllWriteAccessesOf() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testFindMethodsOf() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testFindMethodsReturning() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testFindMethodsTakingAsParameter() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testFindMethodsCalled() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testFindOverridingMethodsOf() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testFindNewOf() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testFindCastsTo() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testFindInstanceOf() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testFindMethodsThrowing() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testFindCatchOf() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testFindClassesAnnotatedWith() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

}
