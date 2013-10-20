package fr.lille1.iagl.idl.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * PrimitiveType class
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PrimitiveType extends Type {

	public PrimitiveType(final String name) {
		super(name, "", TypeKind.PRIMITIVE, null);
	}

}
