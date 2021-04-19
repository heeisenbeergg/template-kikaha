package sizebay.my.modules.modeling.services;

import sizebay.catalog.client.model.Modeling;
import sizebay.my.shared.exceptions.CSVManipulationException;

import java.io.File;
import java.util.List;

public interface IExportModelingsByCSVService {

	File execute(List<Modeling> modelings) throws CSVManipulationException;

}
