/**
 * This class is generated by jOOQ
 */
package org.constellation.database.api.jooq;

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
public class Admin extends org.jooq.impl.SchemaImpl {

	private static final long serialVersionUID = 365568578;

	/**
	 * The reference instance of <code>admin</code>
	 */
	public static final Admin ADMIN = new Admin();

	/**
	 * No further instances allowed
	 */
	private Admin() {
		super("admin");
	}

	@Override
	public final java.util.List<org.jooq.Sequence<?>> getSequences() {
		java.util.List result = new java.util.ArrayList();
		result.addAll(getSequences0());
		return result;
	}

	private final java.util.List<org.jooq.Sequence<?>> getSequences0() {
		return java.util.Arrays.<org.jooq.Sequence<?>>asList(
			org.constellation.database.api.jooq.Sequences.ATTACHMENT_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.CHAIN_PROCESS_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.CSTL_USER_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.DATA_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.DATASET_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.DATASOURCE_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.INTERNAL_METADATA_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.INTERNAL_SENSOR_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.LAYER_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.MAPCONTEXT_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.MAPCONTEXT_STYLED_LAYER_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.METADATA_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.PERMISSION_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.PROVIDER_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.SENSOR_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.SERVICE_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.STYLE_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.TASK_PARAMETER_ID_SEQ,
			org.constellation.database.api.jooq.Sequences.THESAURUS_ID_SEQ);
	}

	@Override
	public final java.util.List<org.jooq.Table<?>> getTables() {
		java.util.List result = new java.util.ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final java.util.List<org.jooq.Table<?>> getTables0() {
		return java.util.Arrays.<org.jooq.Table<?>>asList(
			org.constellation.database.api.jooq.tables.Attachment.ATTACHMENT,
			org.constellation.database.api.jooq.tables.ChainProcess.CHAIN_PROCESS,
			org.constellation.database.api.jooq.tables.Crs.CRS,
			org.constellation.database.api.jooq.tables.CstlUser.CSTL_USER,
			org.constellation.database.api.jooq.tables.Data.DATA,
			org.constellation.database.api.jooq.tables.Dataset.DATASET,
			org.constellation.database.api.jooq.tables.Datasource.DATASOURCE,
			org.constellation.database.api.jooq.tables.DatasourcePath.DATASOURCE_PATH,
			org.constellation.database.api.jooq.tables.DatasourcePathStore.DATASOURCE_PATH_STORE,
			org.constellation.database.api.jooq.tables.DatasourceSelectedPath.DATASOURCE_SELECTED_PATH,
			org.constellation.database.api.jooq.tables.DatasourceStore.DATASOURCE_STORE,
			org.constellation.database.api.jooq.tables.DataXData.DATA_X_DATA,
			org.constellation.database.api.jooq.tables.InternalMetadata.INTERNAL_METADATA,
			org.constellation.database.api.jooq.tables.InternalSensor.INTERNAL_SENSOR,
			org.constellation.database.api.jooq.tables.Layer.LAYER,
			org.constellation.database.api.jooq.tables.Mapcontext.MAPCONTEXT,
			org.constellation.database.api.jooq.tables.MapcontextStyledLayer.MAPCONTEXT_STYLED_LAYER,
			org.constellation.database.api.jooq.tables.Metadata.METADATA,
			org.constellation.database.api.jooq.tables.MetadataBbox.METADATA_BBOX,
			org.constellation.database.api.jooq.tables.MetadataXAttachment.METADATA_X_ATTACHMENT,
			org.constellation.database.api.jooq.tables.MetadataXCsw.METADATA_X_CSW,
			org.constellation.database.api.jooq.tables.Permission.PERMISSION,
			org.constellation.database.api.jooq.tables.Property.PROPERTY,
			org.constellation.database.api.jooq.tables.Provider.PROVIDER,
			org.constellation.database.api.jooq.tables.ProviderXCsw.PROVIDER_X_CSW,
			org.constellation.database.api.jooq.tables.ProviderXSos.PROVIDER_X_SOS,
			org.constellation.database.api.jooq.tables.Role.ROLE,
			org.constellation.database.api.jooq.tables.Sensor.SENSOR,
			org.constellation.database.api.jooq.tables.SensoredData.SENSORED_DATA,
			org.constellation.database.api.jooq.tables.SensorXSos.SENSOR_X_SOS,
			org.constellation.database.api.jooq.tables.Service.SERVICE,
			org.constellation.database.api.jooq.tables.ServiceDetails.SERVICE_DETAILS,
			org.constellation.database.api.jooq.tables.ServiceExtraConfig.SERVICE_EXTRA_CONFIG,
			org.constellation.database.api.jooq.tables.Style.STYLE,
			org.constellation.database.api.jooq.tables.StyledData.STYLED_DATA,
			org.constellation.database.api.jooq.tables.StyledLayer.STYLED_LAYER,
			org.constellation.database.api.jooq.tables.Task.TASK,
			org.constellation.database.api.jooq.tables.TaskParameter.TASK_PARAMETER,
			org.constellation.database.api.jooq.tables.Thesaurus.THESAURUS,
			org.constellation.database.api.jooq.tables.ThesaurusLanguage.THESAURUS_LANGUAGE,
			org.constellation.database.api.jooq.tables.ThesaurusXService.THESAURUS_X_SERVICE,
			org.constellation.database.api.jooq.tables.UserXRole.USER_X_ROLE);
	}
}
