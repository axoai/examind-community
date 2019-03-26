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
public class InternalSensorDao extends org.jooq.impl.DAOImpl<org.constellation.database.api.jooq.tables.records.InternalSensorRecord, org.constellation.database.api.jooq.tables.pojos.InternalSensor, java.lang.Integer> {

	/**
	 * Create a new InternalSensorDao without any configuration
	 */
	public InternalSensorDao() {
		super(org.constellation.database.api.jooq.tables.InternalSensor.INTERNAL_SENSOR, org.constellation.database.api.jooq.tables.pojos.InternalSensor.class);
	}

	/**
	 * Create a new InternalSensorDao with an attached configuration
	 */
	public InternalSensorDao(org.jooq.Configuration configuration) {
		super(org.constellation.database.api.jooq.tables.InternalSensor.INTERNAL_SENSOR, org.constellation.database.api.jooq.tables.pojos.InternalSensor.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected java.lang.Integer getId(org.constellation.database.api.jooq.tables.pojos.InternalSensor object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.InternalSensor> fetchById(java.lang.Integer... values) {
		return fetch(org.constellation.database.api.jooq.tables.InternalSensor.INTERNAL_SENSOR.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public org.constellation.database.api.jooq.tables.pojos.InternalSensor fetchOneById(java.lang.Integer value) {
		return fetchOne(org.constellation.database.api.jooq.tables.InternalSensor.INTERNAL_SENSOR.ID, value);
	}

	/**
	 * Fetch records that have <code>sensor_id IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.InternalSensor> fetchBySensorId(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.InternalSensor.INTERNAL_SENSOR.SENSOR_ID, values);
	}

	/**
	 * Fetch records that have <code>metadata IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.InternalSensor> fetchByMetadata(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.InternalSensor.INTERNAL_SENSOR.METADATA, values);
	}
}