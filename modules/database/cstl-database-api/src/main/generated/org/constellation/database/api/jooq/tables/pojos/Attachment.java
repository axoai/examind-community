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
public class Attachment implements java.io.Serializable {

	private static final long serialVersionUID = 109030944;

	private java.lang.Integer id;
	private byte[]            content;
	private java.lang.String  uri;
	private java.lang.String  filename;

	public Attachment() {}

	public Attachment(
		java.lang.Integer id,
		byte[]            content,
		java.lang.String  uri,
		java.lang.String  filename
	) {
		this.id = id;
		this.content = content;
		this.uri = uri;
		this.filename = filename;
	}

	@javax.validation.constraints.NotNull
	public java.lang.Integer getId() {
		return this.id;
	}

	public Attachment setId(java.lang.Integer id) {
		this.id = id;
		return this;
	}

	public byte[] getContent() {
		return this.content;
	}

	public Attachment setContent(byte[] content) {
		this.content = content;
		return this;
	}

	@javax.validation.constraints.Size(max = 500)
	public java.lang.String getUri() {
		return this.uri;
	}

	public Attachment setUri(java.lang.String uri) {
		this.uri = uri;
		return this;
	}

	@javax.validation.constraints.Size(max = 500)
	public java.lang.String getFilename() {
		return this.filename;
	}

	public Attachment setFilename(java.lang.String filename) {
		this.filename = filename;
		return this;
	}
}