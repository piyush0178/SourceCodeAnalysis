package fr.lille1.iagl.idl.bean;

import lombok.Data;

/**
 * Member class
 */
@Data
public class Member {

	private Type declaringType;

	private Type type;

	private String name;

}
