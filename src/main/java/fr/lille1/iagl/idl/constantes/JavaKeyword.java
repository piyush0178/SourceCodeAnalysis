package fr.lille1.iagl.idl.constantes;

import org.apache.commons.lang3.StringUtils;

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

	/**
	 * Return true if the {@link JavaKeyword} is a java primitive data type.
	 * 
	 * @return
	 */
	public boolean isPrimitiveType() {
		return equals(BOOLEAN) || equals(BYTE) || equals(CHAR)
				|| equals(DOUBLE) || equals(FLOAT) || equals(INT)
				|| equals(LONG) || equals(SHORT) || equals(VOID);
	}

	/**
	 * TODO JIV : test & doc
	 * 
	 * @param keyword
	 * @return
	 */
	public static JavaKeyword getPrimitiveType(final String keyword) {
		if (StringUtils.isNotEmpty(keyword)) {
			if (contains(keyword)) {
				final JavaKeyword javaKeyword = JavaKeyword.valueOf(keyword
						.toUpperCase());
				if (javaKeyword.isPrimitiveType()) {
					return javaKeyword;
				}
			}
		}
		return null;
	}

	/**
	 * TODO JIV : test & doc
	 * 
	 * @param keyword
	 * @return
	 */
	public static boolean contains(final String keyword) {
		if (StringUtils.isNotEmpty(keyword)) {
			for (final JavaKeyword javaKeyword : values()) {
				if (String.valueOf(javaKeyword).equalsIgnoreCase(keyword)) {
					return true;
				}
			}
		}
		return false;
	}
}
