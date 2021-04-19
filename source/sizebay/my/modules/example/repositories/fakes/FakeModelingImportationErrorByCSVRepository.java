package sizebay.my.modules.modeling.repositories.fakes;


import sizebay.my.modules.modeling.dtos.ModelingImportationErrorByCSVDTO;
import sizebay.my.modules.modeling.infra.database.kikahaJdbi.entities.ModelingImportationErrorByCSV;
import sizebay.my.modules.modeling.repositories.IModelingImportationErrorByCSVRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FakeModelingImportationErrorByCSVRepository implements IModelingImportationErrorByCSVRepository {

	private final List<ModelingImportationErrorByCSV> modelingImportationErrorByCSVRepository = new ArrayList<>();
	private final AtomicLong ids = new AtomicLong(1);

	@Override
	public ModelingImportationErrorByCSV create(ModelingImportationErrorByCSVDTO modelingImportationErrorByCSVDTO) {
		final ModelingImportationErrorByCSV modelingImportationErrorByCSV = ModelingImportationErrorByCSV.create(
			modelingImportationErrorByCSVDTO.getTenantId(),
			modelingImportationErrorByCSVDTO.getMessage()
		);

		modelingImportationErrorByCSV.setId(ids.incrementAndGet());

		this.modelingImportationErrorByCSVRepository.add(modelingImportationErrorByCSV);

		return modelingImportationErrorByCSV;
	}

	@Override
	public ModelingImportationErrorByCSV findByTenantId(Long tenantId) {
		return this.modelingImportationErrorByCSVRepository
			.stream()
			.filter(modelingImportationError ->
				modelingImportationError.getTenantId().equals(tenantId)
			).findFirst().orElse(null);
	}

	@Override
	public void deleteByTenantId(Long tenantId) {
		final ModelingImportationErrorByCSV modelingImportationErrorByCSV =
			this.modelingImportationErrorByCSVRepository
				.stream()
				.filter(modelingImportationError ->
						modelingImportationError.getTenantId().equals(tenantId)
				).findFirst().orElse(null);

		if(modelingImportationErrorByCSV == null) {
			return;
		}

		this.modelingImportationErrorByCSVRepository.remove(
			(int) modelingImportationErrorByCSV.getId()
		);
	}
}