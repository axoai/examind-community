/**
 * This class is generated by jOOQ
 */
package org.constellation.database.api.jooq.tables.records;

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
public class RoleRecord extends org.jooq.impl.UpdatableRecordImpl<org.constellation.database.api.jooq.tables.records.RoleRecord> implements org.jooq.Record1<java.lang.String> {

	private static final long serialVersionUID = 572426856;

	/**
	 * Setter for <code>admin.role.name</code>.
	 */
	public RoleRecord setName(java.lang.String value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>admin.role.name</code>.
	 */
	@javax.validation.constraints.NotNull
	@javax.validation.constraints.Size(max = 32)
	public java.lang.String getName() {
		return (java.lang.String) getValue(0);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.String> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record1 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row1<java.lang.String> fieldsRow() {
		return (org.jooq.Row1) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row1<java.lang.String> valuesRow() {
		return (org.jooq.Row1) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field1() {
		return org.constellation.database.api.jooq.tables.Role.ROLE.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value1() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RoleRecord value1(java.lang.String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RoleRecord values(java.lang.String value1) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached RoleRecord
	 */
	public RoleRecord() {
		super(org.constellation.database.api.jooq.tables.Role.ROLE);
	}

	/**
	 * Create a detached, initialised RoleRecord
	 */
	public RoleRecord(java.lang.String name) {
		super(org.constellation.database.api.jooq.tables.Role.ROLE);

		setValue(0, name);
	}
}
