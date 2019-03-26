/**
 * This class is generated by jOOQ
 */
package org.constellation.database.api.jooq.tables.daos;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.3"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DataDao extends org.jooq.impl.DAOImpl<org.constellation.database.api.jooq.tables.records.DataRecord, org.constellation.database.api.jooq.tables.pojos.Data, java.lang.Integer> {

	/**
	 * Create a new DataDao without any configuration
	 */
	public DataDao() {
		super(org.constellation.database.api.jooq.tables.Data.DATA, org.constellation.database.api.jooq.tables.pojos.Data.class);
	}

	/**
	 * Create a new DataDao with an attached configuration
	 */
	public DataDao(org.jooq.Configuration configuration) {
		super(org.constellation.database.api.jooq.tables.Data.DATA, org.constellation.database.api.jooq.tables.pojos.Data.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected java.lang.Integer getId(org.constellation.database.api.jooq.tables.pojos.Data object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchById(java.lang.Integer... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public org.constellation.database.api.jooq.tables.pojos.Data fetchOneById(java.lang.Integer value) {
		return fetchOne(org.constellation.database.api.jooq.tables.Data.DATA.ID, value);
	}

	/**
	 * Fetch records that have <code>name IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchByName(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.NAME, values);
	}

	/**
	 * Fetch records that have <code>namespace IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchByNamespace(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.NAMESPACE, values);
	}

	/**
	 * Fetch records that have <code>provider IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchByProvider(java.lang.Integer... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.PROVIDER, values);
	}

	/**
	 * Fetch records that have <code>type IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchByType(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.TYPE, values);
	}

	/**
	 * Fetch records that have <code>subtype IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchBySubtype(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.SUBTYPE, values);
	}

	/**
	 * Fetch records that have <code>included IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchByIncluded(java.lang.Boolean... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.INCLUDED, values);
	}

	/**
	 * Fetch records that have <code>sensorable IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchBySensorable(java.lang.Boolean... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.SENSORABLE, values);
	}

	/**
	 * Fetch records that have <code>date IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchByDate(java.lang.Long... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.DATE, values);
	}

	/**
	 * Fetch records that have <code>owner IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchByOwner(java.lang.Integer... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.OWNER, values);
	}

	/**
	 * Fetch records that have <code>metadata IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchByMetadata(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.METADATA, values);
	}

	/**
	 * Fetch records that have <code>dataset_id IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchByDatasetId(java.lang.Integer... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.DATASET_ID, values);
	}

	/**
	 * Fetch records that have <code>feature_catalog IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchByFeatureCatalog(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.FEATURE_CATALOG, values);
	}

	/**
	 * Fetch records that have <code>stats_result IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchByStatsResult(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.STATS_RESULT, values);
	}

	/**
	 * Fetch records that have <code>rendered IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchByRendered(java.lang.Boolean... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.RENDERED, values);
	}

	/**
	 * Fetch records that have <code>stats_state IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchByStatsState(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.STATS_STATE, values);
	}

	/**
	 * Fetch records that have <code>hidden IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.Data> fetchByHidden(java.lang.Boolean... values) {
		return fetch(org.constellation.database.api.jooq.tables.Data.DATA.HIDDEN, values);
	}
}