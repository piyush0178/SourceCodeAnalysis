package fr.lille1.iagl.idl.preliminarywork.test;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQResultSequence;

import net.xqj.basex.BaseXXQDataSource;

public class HelloWorld {
	public static void main(String[] args) throws XQException {
		XQDataSource ds = new BaseXXQDataSource();
		ds.setProperty("serverName", "localhost");
		ds.setProperty("port", "1984");
		ds.setProperty("user", "admin");
		ds.setProperty("password", "admin");

		XQConnection xqc = ds.getConnection();
		XQExpression xqe = xqc.createExpression();
		XQResultSequence rs = xqe.executeQuery("'Hello World'");
		rs.writeSequence(System.out, null);
		xqc.close();
	}
}