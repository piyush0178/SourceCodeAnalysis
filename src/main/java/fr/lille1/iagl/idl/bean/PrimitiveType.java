package fr.lille1.iagl.idl.bean;

import lombok.Data;

/**
 * @author Jules
 * 
 */
@Data
public class PrimitiveType extends Type {

	public PrimitiveType(final String name) {
		super(name, "", TypeKind.PRIMITIVE, null);
	}

}
