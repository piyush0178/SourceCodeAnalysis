package fr.lille1.iagl.idl.bean;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Method class
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Method extends Member {

	private static final long serialVersionUID = 2533556143370367945L;

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
