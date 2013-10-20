package fr.lille1.iagl.idl.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * Type class
 */
@Data
public class Type implements Serializable {

	private static final long serialVersionUID = 4173121617986948633L;

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
