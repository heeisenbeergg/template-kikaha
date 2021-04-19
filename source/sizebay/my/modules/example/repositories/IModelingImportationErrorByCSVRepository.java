package sizebay.my.modules.modeling.repositories;

import sizebay.my.modules.modeling.dtos.ModelingImportationErrorByCSVDTO;
import sizebay.my.modules.modeling.infra.database.kikahaJdbi.entities.ModelingImportationErrorByCSV;

public interface IModelingImportationErrorByCSVRepository {

	ModelingImportationErrorByCSV create(ModelingImportationErrorByCSVDTO modelingImportationErrorByCSV);
	ModelingImportationErrorByCSV findByTenantId(Long tenantId);
	void deleteByTenantId(Long tenantId);

}
