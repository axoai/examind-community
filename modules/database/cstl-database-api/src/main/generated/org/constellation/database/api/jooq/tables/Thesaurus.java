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
public class Thesaurus extends org.jooq.impl.TableImpl<org.constellation.database.api.jooq.tables.records.ThesaurusRecord> {

	private static final long serialVersionUID = 995422765;

	/**
	 * The reference instance of <code>admin.thesaurus</code>
	 */
	public static final org.constellation.database.api.jooq.tables.Thesaurus THESAURUS = new org.constellation.database.api.jooq.tables.Thesaurus();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.constellation.database.api.jooq.tables.records.ThesaurusRecord> getRecordType() {
		return org.constellation.database.api.jooq.tables.records.ThesaurusRecord.class;
	}

	/**
	 * The column <code>admin.thesaurus.id</code>.
	 */
	public final org.jooq.TableField<org.constellation.database.api.jooq.tables.records.ThesaurusRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>admin.thesaurus.uri</code>.
	 */
	public final org.jooq.TableField<org.constellation.database.api.jooq.tables.records.ThesaurusRecord, java.lang.String> URI = createField("uri", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false), this, "");

	/**
	 * The column <code>admin.thesaurus.name</code>.
	 */
	public final org.jooq.TableField<org.constellation.database.api.jooq.tables.records.ThesaurusRecord, java.lang.String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false), this, "");

	/**
	 * The column <code>admin.thesaurus.description</code>.
	 */
	public final org.jooq.TableField<org.constellation.database.api.jooq.tables.records.ThesaurusRecord, java.lang.String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(500), this, "");

	/**
	 * The column <code>admin.thesaurus.creation_date</code>.
	 */
	public final org.jooq.TableField<org.constellation.database.api.jooq.tables.records.ThesaurusRecord, java.lang.Long> CREATION_DATE = createField("creation_date", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>admin.thesaurus.state</code>.
	 */
	public final org.jooq.TableField<org.constellation.database.api.jooq.tables.records.ThesaurusRecord, java.lang.Boolean> STATE = createField("state", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>admin.thesaurus.defaultlang</code>.
	 */
	public final org.jooq.TableField<org.constellation.database.api.jooq.tables.records.ThesaurusRecord, java.lang.String> DEFAULTLANG = createField("defaultlang", org.jooq.impl.SQLDataType.VARCHAR.length(3), this, "");

	/**
	 * The column <code>admin.thesaurus.version</code>.
	 */
	public final org.jooq.TableField<org.constellation.database.api.jooq.tables.records.ThesaurusRecord, java.lang.String> VERSION = createField("version", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "");

	/**
	 * The column <code>admin.thesaurus.schemaname</code>.
	 */
	public final org.jooq.TableField<org.constellation.database.api.jooq.tables.records.ThesaurusRecord, java.lang.String> SCHEMANAME = createField("schemaname", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * Create a <code>admin.thesaurus</code> table reference
	 */
	public Thesaurus() {
		this("thesaurus", null);
	}

	/**
	 * Create an aliased <code>admin.thesaurus</code> table reference
	 */
	public Thesaurus(java.lang.String alias) {
		this(alias, org.constellation.database.api.jooq.tables.Thesaurus.THESAURUS);
	}

	private Thesaurus(java.lang.String alias, org.jooq.Table<org.constellation.database.api.jooq.tables.records.ThesaurusRecord> aliased) {
		this(alias, aliased, null);
	}

	private Thesaurus(java.lang.String alias, org.jooq.Table<org.constellation.database.api.jooq.tables.records.ThesaurusRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, org.constellation.database.api.jooq.Admin.ADMIN, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<org.constellation.database.api.jooq.tables.records.ThesaurusRecord, java.lang.Integer> getIdentity() {
		return org.constellation.database.api.jooq.Keys.IDENTITY_THESAURUS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<org.constellation.database.api.jooq.tables.records.ThesaurusRecord> getPrimaryKey() {
		return org.constellation.database.api.jooq.Keys.THESAURUS_PK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<org.constellation.database.api.jooq.tables.records.ThesaurusRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<org.constellation.database.api.jooq.tables.records.ThesaurusRecord>>asList(org.constellation.database.api.jooq.Keys.THESAURUS_PK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.constellation.database.api.jooq.tables.Thesaurus as(java.lang.String alias) {
		return new org.constellation.database.api.jooq.tables.Thesaurus(alias, this);
	}

	/**
	 * Rename this table
	 */
	public org.constellation.database.api.jooq.tables.Thesaurus rename(java.lang.String name) {
		return new org.constellation.database.api.jooq.tables.Thesaurus(name, null);
	}
}
