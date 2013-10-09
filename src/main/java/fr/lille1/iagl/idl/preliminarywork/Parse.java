package fr.lille1.iagl.idl.preliminarywork;

import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import net.xqj.basex.BaseXXQDataSource;

public class Parse {
	public static void main(String[] args) throws XQException, IOException {
		XQDataSource xqs = new BaseXXQDataSource();
		xqs.setProperty("serverName", "localhost");
		xqs.setProperty("user", "USERNAME");
		xqs.setProperty("password", "PASSWORD");

		XQConnection conn = xqs.getConnection();

		XQPreparedExpression xqpe = conn
				.prepareExpression("declare variable $x as xs:string external; $x");

		xqpe.bindString(new QName("x"), "Hello World!", null);

		XQResultSequence rs = xqpe.executeQuery();

		while (rs.next())
			System.out.println(rs.getItemAsString(null));

		conn.close();
		System.out.println("done");
	}
}
