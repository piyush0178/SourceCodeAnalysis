/**
 * 
 */
package fr.lille1.iagl.idl.engine;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.bean.UnknowType;
import fr.lille1.iagl.idl.constantes.Constantes;
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
	public static void setUpBeforeClass() throws Exception {
		engine = new CodeSearchEngineDatabaseImpl(
				DatabaseConnection.getConnection(), Constantes.JAVA_XML);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		DatabaseConnection.closeConnection();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
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
	public void testFindTypeWithJavaPrimitiveType1() throws Exception {
		final String typeName = "void";
		final Type type = engine.findType(typeName);
		Assert.assertNotNull(type);
		Assert.assertEquals(typeName, type.getName());
	}

	@Test
	public void testFindTypeWithJavaPrimitiveType2() throws Exception {
		final String typeName = "int";
		final Type type = engine.findType(typeName);
		Assert.assertNotNull(type);
		Assert.assertEquals(typeName, type.getName());
	}

	@Test
	public void testFindTypeWithUnknowType() throws Exception {
		final String typeName = "toto";
		final Type type = engine.findType(typeName);
		Assert.assertNotNull(type);
		Assert.assertTrue(type instanceof UnknowType);
	}

	@Test
	public void testFindSubTypesOf() throws Exception {
		final List<Type> types = engine.findSubTypesOf("RuntimeException");
		Assert.assertNotNull(types);
		Assert.assertEquals(45, types.size());
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
		final List<Method> methods = engine
				.findMethodsTakingAsParameter("ObjectInputStream");
		Assert.assertEquals(26, methods.size());
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
