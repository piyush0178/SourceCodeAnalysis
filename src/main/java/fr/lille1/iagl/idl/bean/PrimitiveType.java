package fr.lille1.iagl.idl.bean;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * PrimitiveType class
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PrimitiveType extends Type implements Serializable {

	private static final long serialVersionUID = -1725116127841247726L;

	public PrimitiveType(final String name) {
		super(name, "", TypeKind.PRIMITIVE, null);
	}

}
