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
	 * Prepared query of the findFieldsTypedWithQuery() method.<br>
	 */
	@Getter
	private XQPreparedExpression findFieldsTypedWithPreparedQuery;

	/**
	 * Prepared query of the findMethodsTakingAsParameter() method.<br>
	 */
	@Getter
	private XQPreparedExpression findMethodsTakingAsParameterPreparedQuery;

	public PreparedQueries(final XQConnection connection, final String filePath) {
		this.connection = connection;
		this.filePath = filePath;
		try {
			prepareFindTypeQuery();
			prepareFindMethodsTakingAsParameterQuery();
			prepareFindFieldsTypedWithQuery();

		} catch (final XQException e) {
			throw new RuntimeException(
					"Erreur pendant la préparation des queries : "
							+ e.getMessage(), e);
		}
	}

	/**
	 * Prepare FindType() Query.
	 * 
	 * @throws XQException
	 */
	private void prepareFindTypeQuery() throws XQException {
		findTypePreparedQuery = connection
				.prepareExpression(Queries.findTypeQuery);
		findFilePath(findTypePreparedQuery);
	}

	/**
	 * Prepare FindFieldsTypedWith() Query.
	 * 
	 * @throws XQException
	 */
	private void prepareFindFieldsTypedWithQuery() throws XQException {
		findFieldsTypedWithPreparedQuery = connection
				.prepareExpression(Queries.findFieldsTypedWithQuery);
		findFilePath(findFieldsTypedWithPreparedQuery);
	}

	/**
	 * Prepare FindMethodsTakingAsParameter() Query
	 * 
	 * @throws XQException
	 */
	private void prepareFindMethodsTakingAsParameterQuery() throws XQException {
		findMethodsTakingAsParameterPreparedQuery = connection
				.prepareExpression(Queries.findMethodsTakingAsParameterQuery);
		findFilePath(findMethodsTakingAsParameterPreparedQuery);
	}

	/**
	 * Factorisation du bind.
	 * 
	 * @throws XQException
	 */
	private void findFilePath(final XQPreparedExpression preparedQuery)
			throws XQException {
		preparedQuery.bindString(new QName("file"), filePath, null);
	}
}
