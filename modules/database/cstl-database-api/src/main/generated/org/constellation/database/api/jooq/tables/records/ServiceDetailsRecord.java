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
public class ServiceDetailsRecord extends org.jooq.impl.UpdatableRecordImpl<org.constellation.database.api.jooq.tables.records.ServiceDetailsRecord> implements org.jooq.Record4<java.lang.Integer, java.lang.String, java.lang.String, java.lang.Boolean> {

	private static final long serialVersionUID = -1845966791;

	/**
	 * Setter for <code>admin.service_details.id</code>.
	 */
	public ServiceDetailsRecord setId(java.lang.Integer value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>admin.service_details.id</code>.
	 */
	@javax.validation.constraints.NotNull
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>admin.service_details.lang</code>.
	 */
	public ServiceDetailsRecord setLang(java.lang.String value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>admin.service_details.lang</code>.
	 */
	@javax.validation.constraints.NotNull
	@javax.validation.constraints.Size(max = 3)
	public java.lang.String getLang() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>admin.service_details.content</code>.
	 */
	public ServiceDetailsRecord setContent(java.lang.String value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>admin.service_details.content</code>.
	 */
	public java.lang.String getContent() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>admin.service_details.default_lang</code>.
	 */
	public ServiceDetailsRecord setDefaultLang(java.lang.Boolean value) {
		setValue(3, value);
		return this;
	}

	/**
	 * Getter for <code>admin.service_details.default_lang</code>.
	 */
	public java.lang.Boolean getDefaultLang() {
		return (java.lang.Boolean) getValue(3);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record2<java.lang.Integer, java.lang.String> key() {
		return (org.jooq.Record2) super.key();
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Integer, java.lang.String, java.lang.String, java.lang.Boolean> fieldsRow() {
		return (org.jooq.Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Integer, java.lang.String, java.lang.String, java.lang.Boolean> valuesRow() {
		return (org.jooq.Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return org.constellation.database.api.jooq.tables.ServiceDetails.SERVICE_DETAILS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return org.constellation.database.api.jooq.tables.ServiceDetails.SERVICE_DETAILS.LANG;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return org.constellation.database.api.jooq.tables.ServiceDetails.SERVICE_DETAILS.CONTENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Boolean> field4() {
		return org.constellation.database.api.jooq.tables.ServiceDetails.SERVICE_DETAILS.DEFAULT_LANG;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getLang();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getContent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Boolean value4() {
		return getDefaultLang();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ServiceDetailsRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ServiceDetailsRecord value2(java.lang.String value) {
		setLang(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ServiceDetailsRecord value3(java.lang.String value) {
		setContent(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ServiceDetailsRecord value4(java.lang.Boolean value) {
		setDefaultLang(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ServiceDetailsRecord values(java.lang.Integer value1, java.lang.String value2, java.lang.String value3, java.lang.Boolean value4) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ServiceDetailsRecord
	 */
	public ServiceDetailsRecord() {
		super(org.constellation.database.api.jooq.tables.ServiceDetails.SERVICE_DETAILS);
	}

	/**
	 * Create a detached, initialised ServiceDetailsRecord
	 */
	public ServiceDetailsRecord(java.lang.Integer id, java.lang.String lang, java.lang.String content, java.lang.Boolean defaultLang) {
		super(org.constellation.database.api.jooq.tables.ServiceDetails.SERVICE_DETAILS);

		setValue(0, id);
		setValue(1, lang);
		setValue(2, content);
		setValue(3, defaultLang);
	}
}