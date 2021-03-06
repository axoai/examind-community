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
public class InternalMetadataDao extends org.jooq.impl.DAOImpl<org.constellation.database.api.jooq.tables.records.InternalMetadataRecord, org.constellation.database.api.jooq.tables.pojos.InternalMetadata, java.lang.Integer> {

	/**
	 * Create a new InternalMetadataDao without any configuration
	 */
	public InternalMetadataDao() {
		super(org.constellation.database.api.jooq.tables.InternalMetadata.INTERNAL_METADATA, org.constellation.database.api.jooq.tables.pojos.InternalMetadata.class);
	}

	/**
	 * Create a new InternalMetadataDao with an attached configuration
	 */
	public InternalMetadataDao(org.jooq.Configuration configuration) {
		super(org.constellation.database.api.jooq.tables.InternalMetadata.INTERNAL_METADATA, org.constellation.database.api.jooq.tables.pojos.InternalMetadata.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected java.lang.Integer getId(org.constellation.database.api.jooq.tables.pojos.InternalMetadata object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.InternalMetadata> fetchById(java.lang.Integer... values) {
		return fetch(org.constellation.database.api.jooq.tables.InternalMetadata.INTERNAL_METADATA.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public org.constellation.database.api.jooq.tables.pojos.InternalMetadata fetchOneById(java.lang.Integer value) {
		return fetchOne(org.constellation.database.api.jooq.tables.InternalMetadata.INTERNAL_METADATA.ID, value);
	}

	/**
	 * Fetch records that have <code>metadata_id IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.InternalMetadata> fetchByMetadataId(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.InternalMetadata.INTERNAL_METADATA.METADATA_ID, values);
	}

	/**
	 * Fetch records that have <code>metadata_iso IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.InternalMetadata> fetchByMetadataIso(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.InternalMetadata.INTERNAL_METADATA.METADATA_ISO, values);
	}
}
