package sizebay.my.modules.modeling.services;

import sizebay.catalog.client.model.Modeling;
import sizebay.my.modules.modeling.container.providers.CSVManipulationProvider.beans.ModelingCSVBean;
import sizebay.my.shared.container.providers.CSVManipulationProvider.helpers.openCSV.exceptions.CSVBeanToModelSerializerException;
import sizebay.my.modules.modeling.container.providers.CSVManipulationProvider.serializers.ModelingToModelingCSVBeanSerializer;
import sizebay.my.shared.container.providers.CSVManipulationProvider.models.ICSVManipulationProvider;
import sizebay.my.shared.exceptions.CSVManipulationException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Singleton
public class ExportModelingsByCSVService implements IExportModelingsByCSVService {

	private final static String PATH_TEMP_FILE = "tmp/%s.csv";

	private final ICSVManipulationProvider csvManipulationProvider;

	@Inject
	public ExportModelingsByCSVService(
		final ICSVManipulationProvider csvManipulationProvider
	) {
		this.csvManipulationProvider = csvManipulationProvider;
	}

	@Override
	public File execute(final List<Modeling> modelings) throws CSVManipulationException {
		try {
			return exportModelingsByCSV(modelings);
		} catch (CSVManipulationException | CSVBeanToModelSerializerException exception) {

			throw new CSVManipulationException(exception.getMessage());
		}
	}

	private File exportModelingsByCSV(final List<Modeling> modelings) throws CSVBeanToModelSerializerException, CSVManipulationException {
		final List<ModelingCSVBean> modelingCSVBeans = serializeModelingsToModelingCSVBean(
			modelings
		);

		final Path fileNameTMP = generateFilePathTPM();

		return this.csvManipulationProvider.write(fileNameTMP, modelingCSVBeans, ModelingCSVBean.class);
	}
	private List<ModelingCSVBean> serializeModelingsToModelingCSVBean(
		final List<Modeling> modelings
	) throws CSVBeanToModelSerializerException {
		return ModelingToModelingCSVBeanSerializer.serializerList(modelings);
	}

	private Path generateFilePathTPM() {
		final String hashName = generateHashNameTMPFile();
		final String fileNameTMP = String.format(PATH_TEMP_FILE, hashName);

		return Paths.get(fileNameTMP);
	}

	private String generateHashNameTMPFile() {
		return System.currentTimeMillis() + "";
	}

}
