package sizebay.my.modules.modeling.infra.database.kikahaJdbi.repositories;

import sizebay.my.modules.modeling.dtos.ModelingImportationErrorByCSVDTO;
import sizebay.my.modules.modeling.infra.database.kikahaJdbi.entities.ModelingImportationErrorByCSV;
import sizebay.my.modules.modeling.infra.database.kikahaJdbi.queries.ModelingImportationErrorByCSVQueries;
import sizebay.my.modules.modeling.repositories.IModelingImportationErrorByCSVRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ModelingImportationErrorByCSVRepository implements IModelingImportationErrorByCSVRepository {

	private ModelingImportationErrorByCSVQueries modelingImportationErrorByCSVQueries;

	@Inject
	public ModelingImportationErrorByCSVRepository(final ModelingImportationErrorByCSVQueries queries) {
		this.modelingImportationErrorByCSVQueries = queries;
	}

	@Override
	public ModelingImportationErrorByCSV create(ModelingImportationErrorByCSVDTO modelingImportationErrorByCSVDTO) {
		final ModelingImportationErrorByCSV modelingImportationErrorByCSV = ModelingImportationErrorByCSV.create(
			modelingImportationErrorByCSVDTO.getTenantId(),
			modelingImportationErrorByCSVDTO.getMessage()
		);

		final int id = modelingImportationErrorByCSVQueries.create(modelingImportationErrorByCSV);

		modelingImportationErrorByCSV.setId(id);

		return modelingImportationErrorByCSV;
	}

	@Override
	public ModelingImportationErrorByCSV findByTenantId(Long tenantId) {
		return modelingImportationErrorByCSVQueries.findByTenantId(tenantId);
	}

	@Override
	public void deleteByTenantId(Long tenantId) {
		modelingImportationErrorByCSVQueries.deleteByTenantId(tenantId);
	}

}
