package sizebay.my.modules.modeling.helpers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sizebay.catalog.client.model.Brand;
import sizebay.catalog.client.model.Modeling;
import sizebay.catalog.client.model.filters.BrandFilter;
import sizebay.my.modules.modeling.dtos.ModelingImportationErrorByCSVDTO;
import sizebay.my.modules.modeling.repositories.IModelingImportationErrorByCSVRepository;
import sizebay.my.modules.user.dtos.User;
import sizebay.my.shared.exceptions.CSVManipulationException;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelingByCSVHelper {

	private final static String MESSAGE_TO_BRAND_NOT_FOUND = "Marca %s não encontrada para tabela %s";

	public static void populateWithBrand(
		final User user,
		final IModelingImportationErrorByCSVRepository modelingImportationErrorByCSVRepository,
		final Modeling modeling
	) throws CSVManipulationException {
		final List<Brand> brands = user.getCatalogAPI().getBrands(
				new BrandFilter().setName(modeling.getGMerchantBrandName())
		);

		if(brands.isEmpty()) {
			final Long tenantId = user.getCurrentTenant().getId();
			final String message = String.format(MESSAGE_TO_BRAND_NOT_FOUND, modeling.getGMerchantBrandName(), modeling.getName());

			final ModelingImportationErrorByCSVDTO importationErrorByCSVDTO = ModelingImportationErrorByCSVDTO.create(
					tenantId,
					message
			);

			modelingImportationErrorByCSVRepository.create(importationErrorByCSVDTO);

			throw new CSVManipulationException(message);
		} else {
			final Long brandId = brands.get(0).getId();

			modeling.setBrandId(brandId);
		}
	}

	public static void populateWithBrand(
		final User user,
		final IModelingImportationErrorByCSVRepository modelingImportationErrorByCSVRepository,
		final List<Modeling> modelings
	) throws CSVManipulationException {
		for(final Modeling modeling : modelings) {
			populateWithBrand(user, modelingImportationErrorByCSVRepository, modeling);
		}
	}

}
