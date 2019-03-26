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
public class TaskParameterRecord extends org.jooq.impl.UpdatableRecordImpl<org.constellation.database.api.jooq.tables.records.TaskParameterRecord> implements org.jooq.Record10<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String> {

	private static final long serialVersionUID = -1902939075;

	/**
	 * Setter for <code>admin.task_parameter.id</code>.
	 */
	public TaskParameterRecord setId(java.lang.Integer value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>admin.task_parameter.id</code>.
	 */
	@javax.validation.constraints.NotNull
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>admin.task_parameter.owner</code>.
	 */
	public TaskParameterRecord setOwner(java.lang.Integer value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>admin.task_parameter.owner</code>.
	 */
	@javax.validation.constraints.NotNull
	public java.lang.Integer getOwner() {
		return (java.lang.Integer) getValue(1);
	}

	/**
	 * Setter for <code>admin.task_parameter.name</code>.
	 */
	public TaskParameterRecord setName(java.lang.String value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>admin.task_parameter.name</code>.
	 */
	@javax.validation.constraints.NotNull
	@javax.validation.constraints.Size(max = 255)
	public java.lang.String getName() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>admin.task_parameter.date</code>.
	 */
	public TaskParameterRecord setDate(java.lang.Long value) {
		setValue(3, value);
		return this;
	}

	/**
	 * Getter for <code>admin.task_parameter.date</code>.
	 */
	@javax.validation.constraints.NotNull
	public java.lang.Long getDate() {
		return (java.lang.Long) getValue(3);
	}

	/**
	 * Setter for <code>admin.task_parameter.process_authority</code>.
	 */
	public TaskParameterRecord setProcessAuthority(java.lang.String value) {
		setValue(4, value);
		return this;
	}

	/**
	 * Getter for <code>admin.task_parameter.process_authority</code>.
	 */
	@javax.validation.constraints.NotNull
	@javax.validation.constraints.Size(max = 100)
	public java.lang.String getProcessAuthority() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>admin.task_parameter.process_code</code>.
	 */
	public TaskParameterRecord setProcessCode(java.lang.String value) {
		setValue(5, value);
		return this;
	}

	/**
	 * Getter for <code>admin.task_parameter.process_code</code>.
	 */
	@javax.validation.constraints.NotNull
	@javax.validation.constraints.Size(max = 100)
	public java.lang.String getProcessCode() {
		return (java.lang.String) getValue(5);
	}

	/**
	 * Setter for <code>admin.task_parameter.inputs</code>.
	 */
	public TaskParameterRecord setInputs(java.lang.String value) {
		setValue(6, value);
		return this;
	}

	/**
	 * Getter for <code>admin.task_parameter.inputs</code>.
	 */
	@javax.validation.constraints.NotNull
	public java.lang.String getInputs() {
		return (java.lang.String) getValue(6);
	}

	/**
	 * Setter for <code>admin.task_parameter.trigger</code>.
	 */
	public TaskParameterRecord setTrigger(java.lang.String value) {
		setValue(7, value);
		return this;
	}

	/**
	 * Getter for <code>admin.task_parameter.trigger</code>.
	 */
	public java.lang.String getTrigger() {
		return (java.lang.String) getValue(7);
	}

	/**
	 * Setter for <code>admin.task_parameter.trigger_type</code>.
	 */
	public TaskParameterRecord setTriggerType(java.lang.String value) {
		setValue(8, value);
		return this;
	}

	/**
	 * Getter for <code>admin.task_parameter.trigger_type</code>.
	 */
	@javax.validation.constraints.Size(max = 30)
	public java.lang.String getTriggerType() {
		return (java.lang.String) getValue(8);
	}

	/**
	 * Setter for <code>admin.task_parameter.type</code>.
	 */
	public TaskParameterRecord setType(java.lang.String value) {
		setValue(9, value);
		return this;
	}

	/**
	 * Getter for <code>admin.task_parameter.type</code>.
	 */
	public java.lang.String getType() {
		return (java.lang.String) getValue(9);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record10 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row10<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String> fieldsRow() {
		return (org.jooq.Row10) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row10<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String> valuesRow() {
		return (org.jooq.Row10) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return org.constellation.database.api.jooq.tables.TaskParameter.TASK_PARAMETER.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field2() {
		return org.constellation.database.api.jooq.tables.TaskParameter.TASK_PARAMETER.OWNER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return org.constellation.database.api.jooq.tables.TaskParameter.TASK_PARAMETER.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field4() {
		return org.constellation.database.api.jooq.tables.TaskParameter.TASK_PARAMETER.DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return org.constellation.database.api.jooq.tables.TaskParameter.TASK_PARAMETER.PROCESS_AUTHORITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field6() {
		return org.constellation.database.api.jooq.tables.TaskParameter.TASK_PARAMETER.PROCESS_CODE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field7() {
		return org.constellation.database.api.jooq.tables.TaskParameter.TASK_PARAMETER.INPUTS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field8() {
		return org.constellation.database.api.jooq.tables.TaskParameter.TASK_PARAMETER.TRIGGER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field9() {
		return org.constellation.database.api.jooq.tables.TaskParameter.TASK_PARAMETER.TRIGGER_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field10() {
		return org.constellation.database.api.jooq.tables.TaskParameter.TASK_PARAMETER.TYPE;
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
	public java.lang.Integer value2() {
		return getOwner();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value4() {
		return getDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getProcessAuthority();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value6() {
		return getProcessCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value7() {
		return getInputs();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value8() {
		return getTrigger();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value9() {
		return getTriggerType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value10() {
		return getType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskParameterRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskParameterRecord value2(java.lang.Integer value) {
		setOwner(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskParameterRecord value3(java.lang.String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskParameterRecord value4(java.lang.Long value) {
		setDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskParameterRecord value5(java.lang.String value) {
		setProcessAuthority(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskParameterRecord value6(java.lang.String value) {
		setProcessCode(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskParameterRecord value7(java.lang.String value) {
		setInputs(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskParameterRecord value8(java.lang.String value) {
		setTrigger(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskParameterRecord value9(java.lang.String value) {
		setTriggerType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskParameterRecord value10(java.lang.String value) {
		setType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskParameterRecord values(java.lang.Integer value1, java.lang.Integer value2, java.lang.String value3, java.lang.Long value4, java.lang.String value5, java.lang.String value6, java.lang.String value7, java.lang.String value8, java.lang.String value9, java.lang.String value10) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached TaskParameterRecord
	 */
	public TaskParameterRecord() {
		super(org.constellation.database.api.jooq.tables.TaskParameter.TASK_PARAMETER);
	}

	/**
	 * Create a detached, initialised TaskParameterRecord
	 */
	public TaskParameterRecord(java.lang.Integer id, java.lang.Integer owner, java.lang.String name, java.lang.Long date, java.lang.String processAuthority, java.lang.String processCode, java.lang.String inputs, java.lang.String trigger, java.lang.String triggerType, java.lang.String type) {
		super(org.constellation.database.api.jooq.tables.TaskParameter.TASK_PARAMETER);

		setValue(0, id);
		setValue(1, owner);
		setValue(2, name);
		setValue(3, date);
		setValue(4, processAuthority);
		setValue(5, processCode);
		setValue(6, inputs);
		setValue(7, trigger);
		setValue(8, triggerType);
		setValue(9, type);
	}
}