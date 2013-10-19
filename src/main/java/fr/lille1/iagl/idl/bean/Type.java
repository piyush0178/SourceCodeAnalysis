package fr.lille1.iagl.idl.bean;

import lombok.Data;

/**
 * Type class
 */
@Data
public class Type {

	private String name;

	private String fullyQualifiedPackageName;

	/**
	 * optional
	 */
	private TypeKind kind;

	private Location declaration;

}
