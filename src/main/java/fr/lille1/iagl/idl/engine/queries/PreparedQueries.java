/**
 * 
 */
package fr.lille1.iagl.idl.engine.queries;

import javax.xml.namespace.QName;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;

import lombok.Getter;

/**
 * @author Jules
 * 
 */
public class PreparedQueries {

	private final XQConnection connection;

	private final String filePath;

	/**
	 * Prepared query of the fyndType() method.<br>
	 */
	@Getter
	private XQPreparedExpression findTypePreparedQuery;

	/**
	 * Prepared query of the findMethodsTakingAsParameter() method.<br>
	 */
	@Getter
	private XQPreparedExpression findMethodsTakingAsParameterQuery;

	/**
	 * Prepared query of the findFieldsTypedWithQuery() method.<br>
	 */
	@Getter
	private XQPreparedExpression findFieldsTypedWithQuery;

	public PreparedQueries(final XQConnection connection, final String filePath) {
		this.connection = connection;
		this.filePath = filePath;
		try {
			prepareFindTypeQuery(connection, filePath);
			prepareFindMethodsTakingAsParameterQuery(connection, filePath);
			prepareFindFieldsTypedWithQuery(connection, filePath);

		} catch (final XQException e) {
			throw new RuntimeException(
					"Erreur pendant la préparation des queries : "
							+ e.getMessage(), e);
		}
	}

	/**
	 * @param connection
	 * @param filePath
	 * @throws XQException
	 */
	private void prepareFindMethodsTakingAsParameterQuery(
			final XQConnection connection, final String filePath)
			throws XQException {
		findMethodsTakingAsParameterQuery = connection
				.prepareExpression(Queries.findMethodsTakingAsParameterQuery);
		findMethodsTakingAsParameterQuery.bindString(new QName("file"),
				filePath, null);
	}

	/**
	 * @param connection
	 * @param filePath
	 * @throws XQException
	 */
	private void prepareFindTypeQuery(final XQConnection connection,
			final String filePath) throws XQException {
		findTypePreparedQuery = connection
				.prepareExpression(Queries.findTypeQuery);
		findTypePreparedQuery.bindString(new QName("file"), filePath, null);
	}

	/**
	 * @param connection
	 * @param filePath
	 * @throws XQException
	 */
	private void prepareFindFieldsTypedWithQuery(final XQConnection connection,
			final String filePath) throws XQException {
		findTypePreparedQuery = connection
				.prepareExpression(Queries.findFieldsTypedWithQuery);

		findTypePreparedQuery.bindString(new QName("file"), filePath, null);
	}
}
