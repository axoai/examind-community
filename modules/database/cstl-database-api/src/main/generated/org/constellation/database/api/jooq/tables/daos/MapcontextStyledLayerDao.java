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
public class MapcontextStyledLayerDao extends org.jooq.impl.DAOImpl<org.constellation.database.api.jooq.tables.records.MapcontextStyledLayerRecord, org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer, java.lang.Integer> {

	/**
	 * Create a new MapcontextStyledLayerDao without any configuration
	 */
	public MapcontextStyledLayerDao() {
		super(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER, org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer.class);
	}

	/**
	 * Create a new MapcontextStyledLayerDao with an attached configuration
	 */
	public MapcontextStyledLayerDao(org.jooq.Configuration configuration) {
		super(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER, org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected java.lang.Integer getId(org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer> fetchById(java.lang.Integer... values) {
		return fetch(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer fetchOneById(java.lang.Integer value) {
		return fetchOne(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER.ID, value);
	}

	/**
	 * Fetch records that have <code>mapcontext_id IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer> fetchByMapcontextId(java.lang.Integer... values) {
		return fetch(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER.MAPCONTEXT_ID, values);
	}

	/**
	 * Fetch records that have <code>layer_id IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer> fetchByLayerId(java.lang.Integer... values) {
		return fetch(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER.LAYER_ID, values);
	}

	/**
	 * Fetch records that have <code>style_id IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer> fetchByStyleId(java.lang.Integer... values) {
		return fetch(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER.STYLE_ID, values);
	}

	/**
	 * Fetch records that have <code>layer_order IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer> fetchByLayerOrder(java.lang.Integer... values) {
		return fetch(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER.LAYER_ORDER, values);
	}

	/**
	 * Fetch records that have <code>layer_opacity IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer> fetchByLayerOpacity(java.lang.Integer... values) {
		return fetch(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER.LAYER_OPACITY, values);
	}

	/**
	 * Fetch records that have <code>layer_visible IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer> fetchByLayerVisible(java.lang.Boolean... values) {
		return fetch(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER.LAYER_VISIBLE, values);
	}

	/**
	 * Fetch records that have <code>external_layer IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer> fetchByExternalLayer(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER.EXTERNAL_LAYER, values);
	}

	/**
	 * Fetch records that have <code>external_layer_extent IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer> fetchByExternalLayerExtent(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER.EXTERNAL_LAYER_EXTENT, values);
	}

	/**
	 * Fetch records that have <code>external_service_url IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer> fetchByExternalServiceUrl(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER.EXTERNAL_SERVICE_URL, values);
	}

	/**
	 * Fetch records that have <code>external_service_version IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer> fetchByExternalServiceVersion(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER.EXTERNAL_SERVICE_VERSION, values);
	}

	/**
	 * Fetch records that have <code>external_style IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer> fetchByExternalStyle(java.lang.String... values) {
		return fetch(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER.EXTERNAL_STYLE, values);
	}

	/**
	 * Fetch records that have <code>iswms IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer> fetchByIswms(java.lang.Boolean... values) {
		return fetch(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER.ISWMS, values);
	}

	/**
	 * Fetch records that have <code>data_id IN (values)</code>
	 */
	public java.util.List<org.constellation.database.api.jooq.tables.pojos.MapcontextStyledLayer> fetchByDataId(java.lang.Integer... values) {
		return fetch(org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER.DATA_ID, values);
	}
}