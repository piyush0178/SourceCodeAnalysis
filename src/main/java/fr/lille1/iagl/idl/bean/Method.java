package fr.lille1.iagl.idl.bean;

import java.util.List;

import lombok.Data;

/**
 * Method class
 */
@Data
public class Method extends Member {

	private List<Type> parameters;

	/**
	 * @param declaringType
	 * @param type
	 * @param name
	 * @param parameters
	 */
	public Method(final Type declaringType, final Type type, final String name,
			final List<Type> parameters) {
		super(declaringType, type, name);
		this.parameters = parameters;
	}

	/**
	 * Empty constructor
	 */
	public Method() {
		// Empty constructor
	}
}
