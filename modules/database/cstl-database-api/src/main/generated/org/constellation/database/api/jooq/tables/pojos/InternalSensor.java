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
public class InternalSensor implements java.io.Serializable {

	private static final long serialVersionUID = 185225856;

	private java.lang.Integer id;
	private java.lang.String  sensorId;
	private java.lang.String  metadata;

	public InternalSensor() {}

	public InternalSensor(
		java.lang.Integer id,
		java.lang.String  sensorId,
		java.lang.String  metadata
	) {
		this.id = id;
		this.sensorId = sensorId;
		this.metadata = metadata;
	}

	@javax.validation.constraints.NotNull
	public java.lang.Integer getId() {
		return this.id;
	}

	public InternalSensor setId(java.lang.Integer id) {
		this.id = id;
		return this;
	}

	@javax.validation.constraints.NotNull
	@javax.validation.constraints.Size(max = 100)
	public java.lang.String getSensorId() {
		return this.sensorId;
	}

	public InternalSensor setSensorId(java.lang.String sensorId) {
		this.sensorId = sensorId;
		return this;
	}

	@javax.validation.constraints.NotNull
	public java.lang.String getMetadata() {
		return this.metadata;
	}

	public InternalSensor setMetadata(java.lang.String metadata) {
		this.metadata = metadata;
		return this;
	}
}