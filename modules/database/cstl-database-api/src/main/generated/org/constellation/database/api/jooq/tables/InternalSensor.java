/**
 * This class is generated by jOOQ
 */
package org.constellation.database.api.jooq.tables;

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
public class InternalSensor extends org.jooq.impl.TableImpl<org.constellation.database.api.jooq.tables.records.InternalSensorRecord> {

	private static final long serialVersionUID = -497699895;

	/**
	 * The reference instance of <code>admin.internal_sensor</code>
	 */
	public static final org.constellation.database.api.jooq.tables.InternalSensor INTERNAL_SENSOR = new org.constellation.database.api.jooq.tables.InternalSensor();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.constellation.database.api.jooq.tables.records.InternalSensorRecord> getRecordType() {
		return org.constellation.database.api.jooq.tables.records.InternalSensorRecord.class;
	}

	/**
	 * The column <code>admin.internal_sensor.id</code>.
	 */
	public final org.jooq.TableField<org.constellation.database.api.jooq.tables.records.InternalSensorRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>admin.internal_sensor.sensor_id</code>.
	 */
	public final org.jooq.TableField<org.constellation.database.api.jooq.tables.records.InternalSensorRecord, java.lang.String> SENSOR_ID = createField("sensor_id", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * The column <code>admin.internal_sensor.metadata</code>.
	 */
	public final org.jooq.TableField<org.constellation.database.api.jooq.tables.records.InternalSensorRecord, java.lang.String> METADATA = createField("metadata", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

	/**
	 * Create a <code>admin.internal_sensor</code> table reference
	 */
	public InternalSensor() {
		this("internal_sensor", null);
	}

	/**
	 * Create an aliased <code>admin.internal_sensor</code> table reference
	 */
	public InternalSensor(java.lang.String alias) {
		this(alias, org.constellation.database.api.jooq.tables.InternalSensor.INTERNAL_SENSOR);
	}

	private InternalSensor(java.lang.String alias, org.jooq.Table<org.constellation.database.api.jooq.tables.records.InternalSensorRecord> aliased) {
		this(alias, aliased, null);
	}

	private InternalSensor(java.lang.String alias, org.jooq.Table<org.constellation.database.api.jooq.tables.records.InternalSensorRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, org.constellation.database.api.jooq.Admin.ADMIN, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<org.constellation.database.api.jooq.tables.records.InternalSensorRecord, java.lang.Integer> getIdentity() {
		return org.constellation.database.api.jooq.Keys.IDENTITY_INTERNAL_SENSOR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<org.constellation.database.api.jooq.tables.records.InternalSensorRecord> getPrimaryKey() {
		return org.constellation.database.api.jooq.Keys.INTERNAL_SENSOR_PK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<org.constellation.database.api.jooq.tables.records.InternalSensorRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<org.constellation.database.api.jooq.tables.records.InternalSensorRecord>>asList(org.constellation.database.api.jooq.Keys.INTERNAL_SENSOR_PK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<org.constellation.database.api.jooq.tables.records.InternalSensorRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<org.constellation.database.api.jooq.tables.records.InternalSensorRecord, ?>>asList(org.constellation.database.api.jooq.Keys.INTERNAL_SENSOR__INTERNAL_SENSOR_ID_FK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.constellation.database.api.jooq.tables.InternalSensor as(java.lang.String alias) {
		return new org.constellation.database.api.jooq.tables.InternalSensor(alias, this);
	}

	/**
	 * Rename this table
	 */
	public org.constellation.database.api.jooq.tables.InternalSensor rename(java.lang.String name) {
		return new org.constellation.database.api.jooq.tables.InternalSensor(name, null);
	}
}