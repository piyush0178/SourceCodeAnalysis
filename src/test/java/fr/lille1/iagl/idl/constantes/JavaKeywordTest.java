package fr.lille1.iagl.idl.constantes;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class JavaKeywordTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsPrimitiveTypeTrue() throws Exception {
		Assert.assertTrue(JavaKeyword.BOOLEAN.isPrimitiveType());
		Assert.assertTrue(JavaKeyword.BYTE.isPrimitiveType());
		Assert.assertTrue(JavaKeyword.CHAR.isPrimitiveType());
		Assert.assertTrue(JavaKeyword.DOUBLE.isPrimitiveType());
		Assert.assertTrue(JavaKeyword.FLOAT.isPrimitiveType());
		Assert.assertTrue(JavaKeyword.INT.isPrimitiveType());
		Assert.assertTrue(JavaKeyword.LONG.isPrimitiveType());
		Assert.assertTrue(JavaKeyword.SHORT.isPrimitiveType());
		Assert.assertTrue(JavaKeyword.VOID.isPrimitiveType());
	}

	@Test
	public void testIsPrimitiveTypeFalse() throws Exception {
		Assert.assertFalse(JavaKeyword.FALSE.isPrimitiveType());
		Assert.assertFalse(JavaKeyword.FOR.isPrimitiveType());
		Assert.assertFalse(JavaKeyword.ABSTRACT.isPrimitiveType());
	}

}
