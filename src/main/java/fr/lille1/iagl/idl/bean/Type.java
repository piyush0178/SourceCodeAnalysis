package fr.lille1.iagl.idl.bean;

import lombok.Data;

/**
 * Type class
 */
@Data
public class Type {

	public String name;

	public String fullyQualifiedPackageName;

	public TypeKind kind;

	public Location declaration;

}
