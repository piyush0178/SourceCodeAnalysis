package fr.lille1.iagl.idl.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * Location class
 */
@Data
public class Location implements Serializable {

	private static final long serialVersionUID = -484741118500783047L;

	/**
	 * A path of the file containing a code element. E.g. "./foo/Toto.java"
	 */
	private String filePath;

	/**
	 * optional
	 */
	private int lineNumber;

	/**
	 * Constructor
	 * 
	 * @param filePath
	 */
	public Location(final String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Empty constructor
	 */
	public Location() {
		// empty constructor
	}
}
