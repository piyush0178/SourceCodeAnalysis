package fr.lille1.iagl.idl.bean;

import lombok.Data;

/**
 * Type class
 */
@Data
public class Type {

	private String name;

	private String fullyQualifiedPackageName;

	private TypeKind kind;

	private Location declaration;

}
