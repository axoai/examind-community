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
public class DatasourceStoreDao extends org.jooq.impl.DAOImpl<org.constellation.database.api.jooq.tables.records.DatasourceStoreRecord, org.constellation.database.api.jooq.tables.pojos.DatasourceStore, org.jooq.Record3<java.lang.Integer, java.lang.String, java.lang.String>> {

	/**
	 * Create a new DatasourceStoreDao without any configuration
	 */
	public DatasourceStoreDao() {
		super(org.constellation.database.api.jooq.tables.DatasourceStore.DATASOURCE_STORE, org.constellation.database.api.jooq.tables.pojos.DatasourceStore.class);
	}

	/**
	 * Create a new DatasourceStoreDao with an attached configuration
	 */
	public DatasourceStoreDao(org.jooq.Configuration configuration) {
		super(org.constellation.database.api.jooq.tables.DatasourceStore.DATASOURCE_STORE, org.constellation.database.api.jooq.tables.pojos.DatasourceStore.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected org.jooq.Record3<java.lang.Integer, java.lang.String, java.lang.String> getId(org.constellation.database.api.jooq.tables.pojos.DatasourceStore object) {
		return compositeKeyRecord(object.getDatasourceId(), object.getStore(), object.getType());
	}

	/**
	 * Fetch records that have <code>datasource_id IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.DatasourceStore> fetchByDatasourceId(java.lang.Integer... values) {
		return fetch(org.constellation.database.api.jooq.tables.DatasourceStore.DATASOURCE_STORE.DATASOURCE_ID, values);
	}

	/**
	 * Fetch records that have <code>store IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.DatasourceStore> fetchByStore(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.DatasourceStore.DATASOURCE_STORE.STORE, values);
	}

	/**
	 * Fetch records that have <code>type IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.DatasourceStore> fetchByType(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.DatasourceStore.DATASOURCE_STORE.TYPE, values);
	}
}