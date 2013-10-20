/**
 * 
 */
package fr.lille1.iagl.idl.constantes;

/**
 * Java reserved keywords
 */
public enum JavaKeyword {
	ABSTRACT, ASSERT, BOOLEAN, BREAK, BYTE, CASE, CATCH, CHAR, CLASS, CONST,
	CONTINUE, DEFAULT, DO, DOUBLE, ELSE, ENUM, EXTENDS, FINAL, FINALLY, FLOAT,
	FOR, GOTO, IF, IMPLEMENTS, IMPORT, INSTANCEOF, INT, INTERFACE, LONG,
	NATIVE, NEW, PACKAGE, PRIVATE, PROTECTED, PUBLIC, RETURN, SHORT, STATIC,
	STRICTFP, SUPER, SWITCH, SYNCHRONIZED, THIS, THROW, THROWS, TRANSIENT, TRY,
	VOID, VOLATILE, WHILE, FALSE, NULL, TRUE;

	public boolean isPrimitiveType() {
		return equals(BOOLEAN) || equals(BYTE) || equals(CHAR)
				|| equals(DOUBLE) || equals(FLOAT) || equals(INT)
				|| equals(LONG) || equals(SHORT);
	}

}
