package sizebay.my.modules.modeling.infra.http.controllers;

import kikaha.urouting.api.*;
import lombok.extern.slf4j.Slf4j;
import sizebay.my.modules.modeling.infra.database.kikahaJdbi.entities.ModelingImportationErrorByCSV;
import sizebay.my.modules.modeling.services.IRetrieveByTenantIdModelingImportationErrorByCSVService;
import sizebay.my.modules.user.dtos.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Slf4j
@Singleton
@Path("modelings/import/errors")
public class ModelingImportationErrorByCSVController {

	private final IRetrieveByTenantIdModelingImportationErrorByCSVService retrieveByTenantIdModelingImportationErrorByCSVService;

	@Inject
	public ModelingImportationErrorByCSVController(final IRetrieveByTenantIdModelingImportationErrorByCSVService retrieveByTenantIdModelingImportationErrorByCSVService) {
		this.retrieveByTenantIdModelingImportationErrorByCSVService = retrieveByTenantIdModelingImportationErrorByCSVService;
	}

	@GET
	public DefaultResponse index(@Context User user) {
		final Long tenantId = user.getCurrentTenant().getId();

		final Optional<ModelingImportationErrorByCSV> modelingImportationErrorByCSV =
			this.retrieveByTenantIdModelingImportationErrorByCSVService.execute(tenantId);

		return modelingImportationErrorByCSV.isPresent() ?
			DefaultResponse.ok(modelingImportationErrorByCSV) : DefaultResponse.notFound();
	}

}
