package fr.lille1.iagl.idl.engine.impl;

import java.io.IOException;
import java.net.ContentHandler;
import java.net.URLConnection;
import java.util.List;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import fr.lille1.iagl.idl.bean.Field;
import fr.lille1.iagl.idl.bean.Location;
import fr.lille1.iagl.idl.bean.Method;
import fr.lille1.iagl.idl.bean.Type;
import fr.lille1.iagl.idl.engine.CodeSearchEngine;
import fr.lille1.iagl.idl.exception.WillNeverBeImplementedMethodException;
import fr.lille1.iagl.idl.preliminarywork.Parse;

public class CodeSearchEngineDatabaseImpl implements CodeSearchEngine {

	private final XQConnection connection;

	private final String filePath;

	public CodeSearchEngineDatabaseImpl(final XQConnection connection,
			final String filePath) {
		this.connection = connection;
		this.filePath = filePath;
	}

	@Override
	public Type findType(final String typeName) {
		final Type type = new Type();
		try {

			final String query = "for $x in doc('" + filePath
					+ "')//unit[class/name='" + typeName + "'] return $x";

			XQResultSequence results = execute(query);

			while (results.next()) {
				final String res = results.getItemAsString(null);
				System.out.println(res);
			}
		} catch (final XQException e) {
			throw new RuntimeException("fyndType(" + typeName + ") FAIL :"
					+ e.getMessage(), e);
		}
		return type;
	}

	@Override
	public List<Type> findSubTypesOf(final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Field> findFieldsTypedWith(final String typeName) {
		// TODO Auto-generated method stub
		try {
			final String query = "doc('" + filePath
					+ "')//unit/unit/class[name='" + typeName
					+ "']/block/decl_stmt/decl";
			XQResultSequence results = execute(query); 
			
			XMLReader saxReader = XMLReaderFactory.createXMLReader();
			saxReader.setContentHandler(new DefaultHandler());
		} catch (XQException | SAXException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	@Override
	public List<Location> findAllReadAccessesOf(final Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findAllWriteAccessesOf(final Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Method> findMethodsOf(final String typeName) {
		// TODO Auto-generated method stub
		// requete xquery :
		// //unit/class/block/function[type/name="typeName"]/name
		return null;
	}

	@Override
	public List<Method> findMethodsReturning(final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Method> findMethodsTakingAsParameter(final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Method> findMethodsCalled(final String methodName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Method> findOverridingMethodsOf(final Method method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findNewOf(final String className) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findCastsTo(final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findInstanceOf(final String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Method> findMethodsThrowing(final String exceptionName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> findCatchOf(final String exceptionName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public List<Type> findClassesAnnotatedWith(final String annotationName) {
		throw new WillNeverBeImplementedMethodException(
				"Méthode impossible à implémenter en utilisant SrcML");
	}

	private XQResultSequence execute(String query) throws XQException {
		XQPreparedExpression xqpe;
		xqpe = connection.prepareExpression(query);
		return xqpe.executeQuery();

	}

}
