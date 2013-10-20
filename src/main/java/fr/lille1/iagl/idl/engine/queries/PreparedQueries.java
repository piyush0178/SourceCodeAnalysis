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

	public PreparedQueries(final XQConnection connection, final String filePath) {
		this.connection = connection;
		this.filePath = filePath;
		try {
			prepareQuery(findTypePreparedQuery, Queries.findTypeQuery);
			prepareQuery(findMethodsTakingAsParameterQuery,
					Queries.findTypeQuery);

		} catch (final XQException e) {
			throw new RuntimeException(
					"Erreur pendant la préparation des queries : "
							+ e.getMessage(), e);
		}
	}

	/**
	 * TODO JIV : doc
	 * 
	 * @param queryToBePrepared
	 * @return
	 * @throws XQException
	 */
	private void prepareQuery(XQPreparedExpression queryToBePrepared,
			final String query) throws XQException {
		if (queryToBePrepared == null) {
			queryToBePrepared = connection.prepareExpression(query);
			queryToBePrepared.bindString(new QName("file"), filePath, null);
		}
	}

}
