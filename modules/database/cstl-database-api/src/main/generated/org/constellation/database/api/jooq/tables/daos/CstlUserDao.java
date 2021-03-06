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
public class CstlUserDao extends org.jooq.impl.DAOImpl<org.constellation.database.api.jooq.tables.records.CstlUserRecord, org.constellation.database.api.jooq.tables.pojos.CstlUser, java.lang.Integer> {

	/**
	 * Create a new CstlUserDao without any configuration
	 */
	public CstlUserDao() {
		super(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER, org.constellation.database.api.jooq.tables.pojos.CstlUser.class);
	}

	/**
	 * Create a new CstlUserDao with an attached configuration
	 */
	public CstlUserDao(org.jooq.Configuration configuration) {
		super(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER, org.constellation.database.api.jooq.tables.pojos.CstlUser.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected java.lang.Integer getId(org.constellation.database.api.jooq.tables.pojos.CstlUser object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchById(java.lang.Integer... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public org.constellation.database.api.jooq.tables.pojos.CstlUser fetchOneById(java.lang.Integer value) {
		return fetchOne(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.ID, value);
	}

	/**
	 * Fetch records that have <code>login IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByLogin(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.LOGIN, values);
	}

	/**
	 * Fetch a unique record that has <code>login = value</code>
	 */
	public org.constellation.database.api.jooq.tables.pojos.CstlUser fetchOneByLogin(java.lang.String value) {
		return fetchOne(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.LOGIN, value);
	}

	/**
	 * Fetch records that have <code>password IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByPassword(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.PASSWORD, values);
	}

	/**
	 * Fetch records that have <code>firstname IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByFirstname(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.FIRSTNAME, values);
	}

	/**
	 * Fetch records that have <code>lastname IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByLastname(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.LASTNAME, values);
	}

	/**
	 * Fetch records that have <code>email IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByEmail(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.EMAIL, values);
	}

	/**
	 * Fetch a unique record that has <code>email = value</code>
	 */
	public org.constellation.database.api.jooq.tables.pojos.CstlUser fetchOneByEmail(java.lang.String value) {
		return fetchOne(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.EMAIL, value);
	}

	/**
	 * Fetch records that have <code>active IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByActive(java.lang.Boolean... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.ACTIVE, values);
	}

	/**
	 * Fetch records that have <code>avatar IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByAvatar(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.AVATAR, values);
	}

	/**
	 * Fetch records that have <code>zip IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByZip(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.ZIP, values);
	}

	/**
	 * Fetch records that have <code>city IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByCity(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.CITY, values);
	}

	/**
	 * Fetch records that have <code>country IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByCountry(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.COUNTRY, values);
	}

	/**
	 * Fetch records that have <code>phone IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByPhone(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.PHONE, values);
	}

	/**
	 * Fetch records that have <code>forgot_password_uuid IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByForgotPasswordUuid(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.FORGOT_PASSWORD_UUID, values);
	}

	/**
	 * Fetch a unique record that has <code>forgot_password_uuid = value</code>
	 */
	public org.constellation.database.api.jooq.tables.pojos.CstlUser fetchOneByForgotPasswordUuid(java.lang.String value) {
		return fetchOne(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.FORGOT_PASSWORD_UUID, value);
	}

	/**
	 * Fetch records that have <code>address IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByAddress(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.ADDRESS, values);
	}

	/**
	 * Fetch records that have <code>additional_address IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByAdditionalAddress(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.ADDITIONAL_ADDRESS, values);
	}

	/**
	 * Fetch records that have <code>civility IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByCivility(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.CIVILITY, values);
	}

	/**
	 * Fetch records that have <code>title IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByTitle(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.TITLE, values);
	}

	/**
	 * Fetch records that have <code>locale IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.CstlUser> fetchByLocale(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER.LOCALE, values);
	}
}
