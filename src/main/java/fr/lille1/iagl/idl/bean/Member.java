package fr.lille1.iagl.idl.bean;

import lombok.Data;

/**
 * Member class
 */
@Data
public class Member {

	/**
	 * The class that defines this method.
	 */
	private Type declaringType;

	/**
	 * The class of the field or the returned object.
	 */
	private Type type;

	/**
	 * The name of this member (e.g. the field name).
	 */
	private String name;

	/**
	 * Empty constructor
	 */
	public Member() {
		// Empty constructor
	}

	/**
	 * @param declaringType
	 * @param type
	 * @param name
	 */
	public Member(final Type declaringType, final Type type, final String name) {
		this.declaringType = declaringType;
		this.type = type;
		this.name = name;
	}
}
