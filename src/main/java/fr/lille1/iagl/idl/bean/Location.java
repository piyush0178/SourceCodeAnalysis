package fr.lille1.iagl.idl.bean;

import lombok.Data;

/**
 * Location class
 */
@Data
public class Location {

	/**
	 * A path of the file containing a code element. E.g. "./foo/Toto.java"
	 */
	private String filePath;

	/**
	 * optional
	 */
	private int lineNumber;

}
