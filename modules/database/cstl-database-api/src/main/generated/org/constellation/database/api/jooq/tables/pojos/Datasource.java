/**
 * This class is generated by jOOQ
 */
package org.constellation.database.api.jooq.tables.pojos;

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
public class Datasource implements java.io.Serializable {

	private static final long serialVersionUID = 1105025754;

	private java.lang.Integer id;
	private java.lang.String  type;
	private java.lang.String  url;
	private java.lang.String  username;
	private java.lang.String  pwd;
	private java.lang.String  storeId;
	private java.lang.Boolean readFromRemote;
	private java.lang.Long    dateCreation;
	private java.lang.String  analysisState;
	private java.lang.String  format;
	private java.lang.Boolean permanent;

	public Datasource() {}

	public Datasource(
		java.lang.Integer id,
		java.lang.String  type,
		java.lang.String  url,
		java.lang.String  username,
		java.lang.String  pwd,
		java.lang.String  storeId,
		java.lang.Boolean readFromRemote,
		java.lang.Long    dateCreation,
		java.lang.String  analysisState,
		java.lang.String  format,
		java.lang.Boolean permanent
	) {
		this.id = id;
		this.type = type;
		this.url = url;
		this.username = username;
		this.pwd = pwd;
		this.storeId = storeId;
		this.readFromRemote = readFromRemote;
		this.dateCreation = dateCreation;
		this.analysisState = analysisState;
		this.format = format;
		this.permanent = permanent;
	}

	@javax.validation.constraints.NotNull
	public java.lang.Integer getId() {
		return this.id;
	}

	public Datasource setId(java.lang.Integer id) {
		this.id = id;
		return this;
	}

	@javax.validation.constraints.NotNull
	@javax.validation.constraints.Size(max = 50)
	public java.lang.String getType() {
		return this.type;
	}

	public Datasource setType(java.lang.String type) {
		this.type = type;
		return this;
	}

	@javax.validation.constraints.NotNull
	@javax.validation.constraints.Size(max = 1000)
	public java.lang.String getUrl() {
		return this.url;
	}

	public Datasource setUrl(java.lang.String url) {
		this.url = url;
		return this;
	}

	@javax.validation.constraints.Size(max = 100)
	public java.lang.String getUsername() {
		return this.username;
	}

	public Datasource setUsername(java.lang.String username) {
		this.username = username;
		return this;
	}

	@javax.validation.constraints.Size(max = 500)
	public java.lang.String getPwd() {
		return this.pwd;
	}

	public Datasource setPwd(java.lang.String pwd) {
		this.pwd = pwd;
		return this;
	}

	@javax.validation.constraints.Size(max = 100)
	public java.lang.String getStoreId() {
		return this.storeId;
	}

	public Datasource setStoreId(java.lang.String storeId) {
		this.storeId = storeId;
		return this;
	}

	@javax.validation.constraints.NotNull
	public java.lang.Boolean getReadFromRemote() {
		return this.readFromRemote;
	}

	public Datasource setReadFromRemote(java.lang.Boolean readFromRemote) {
		this.readFromRemote = readFromRemote;
		return this;
	}

	public java.lang.Long getDateCreation() {
		return this.dateCreation;
	}

	public Datasource setDateCreation(java.lang.Long dateCreation) {
		this.dateCreation = dateCreation;
		return this;
	}

	@javax.validation.constraints.Size(max = 50)
	public java.lang.String getAnalysisState() {
		return this.analysisState;
	}

	public Datasource setAnalysisState(java.lang.String analysisState) {
		this.analysisState = analysisState;
		return this;
	}

	public java.lang.String getFormat() {
		return this.format;
	}

	public Datasource setFormat(java.lang.String format) {
		this.format = format;
		return this;
	}

	@javax.validation.constraints.NotNull
	public java.lang.Boolean getPermanent() {
		return this.permanent;
	}

	public Datasource setPermanent(java.lang.Boolean permanent) {
		this.permanent = permanent;
		return this;
	}
}