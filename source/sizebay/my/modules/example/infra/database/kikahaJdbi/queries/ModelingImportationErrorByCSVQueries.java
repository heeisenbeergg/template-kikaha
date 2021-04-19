package sizebay.my.modules.modeling.infra.database.kikahaJdbi.queries;

import kikaha.jdbi.JDBI;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import sizebay.my.modules.modeling.infra.database.kikahaJdbi.entities.ModelingImportationErrorByCSV;

@JDBI
public interface ModelingImportationErrorByCSVQueries {

	@GetGeneratedKeys
	@SqlUpdate("	INSERT INTO "
					 + "		szb_modeling_importation_errors_by_csv "
					 + "			(mic_tenant_id, mic_message, created_at) "
					 + "	VALUES "
					 + "		(:tenantId, :message, NOW()) ")
	int create(@BindBean ModelingImportationErrorByCSV modelingImportationErrorByCSV);

	@SqlQuery("	SELECT * "
					+ " FROM szb_modeling_importation_errors_by_csv "
					+ "	WHERE mic_tenant_id = :tenantId")
	ModelingImportationErrorByCSV findByTenantId(@Bind("tenantId") Long tenantId);

	@SqlUpdate("  DELETE FROM "
					 + " 		szb_modeling_importation_errors_by_csv "
					 + "  WHERE "
					 + " 		mic_tenant_id = :tenantId ")
	int deleteByTenantId(@Bind("tenantId") Long tenantId);

}
