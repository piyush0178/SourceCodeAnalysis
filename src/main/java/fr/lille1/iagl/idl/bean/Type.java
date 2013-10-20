package fr.lille1.iagl.idl.bean;

import lombok.Data;

/**
 * Type class
 */
@Data
public class Type {

	private String name;

	/**
	 * Package name
	 */
	private String fullyQualifiedPackageName;

	/**
	 * optional
	 */
	private TypeKind kind;

	private Location declaration;

	/**
	 * empty constructor
	 */
	public Type() {
		// empty constructor
	}

	/**
	 * @param name
	 * @param fullyQualifiedPackageName
	 * @param kind
	 * @param declaration
	 */
	public Type(final String name, final String fullyQualifiedPackageName,
			final TypeKind kind, final Location declaration) {
		this.name = name;
		this.fullyQualifiedPackageName = fullyQualifiedPackageName;
		this.kind = kind;
		this.declaration = declaration;
	}

}
