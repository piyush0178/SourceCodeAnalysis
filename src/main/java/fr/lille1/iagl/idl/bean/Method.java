package fr.lille1.iagl.idl.bean;

import java.util.List;

import lombok.Data;

/**
 * Method class
 */
@Data
public class Method extends Member {

	private List<Type> parameters;

}
