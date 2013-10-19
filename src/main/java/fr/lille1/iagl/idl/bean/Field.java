package fr.lille1.iagl.idl.bean;

import lombok.Data;

/**
 * Field class
 */
@Data
public class Field extends Member {
	
	/**
	 * @param declaringType
	 * @param type
	 * @param name
	 */
	public Field(final Type declaringType, final Type type, final String name) {
		super(declaringType, type, name);
	}
	
}
