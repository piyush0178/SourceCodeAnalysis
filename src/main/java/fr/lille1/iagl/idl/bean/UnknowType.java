package fr.lille1.iagl.idl.bean;

/**
 * UnknowType class. Represent a type used in the document but never declared.
 */
public class UnknowType extends Type {

	private static final long serialVersionUID = -4197324567787520137L;

	/**
	 * empty constructor
	 */
	public UnknowType() {
		// empty constructor
	}

	/**
	 * @param name
	 * @param fullyQualifiedPackageName
	 * @param kind
	 * @param declaration
	 */
	public UnknowType(final String name) {
		super(name, "", null, null);
	}

}
