package sizebay.my.shared.infra.http.exceptionsHandlers;

import kikaha.urouting.api.DefaultResponse;
import kikaha.urouting.api.ExceptionHandler;
import kikaha.urouting.api.Response;
import sizebay.my.shared.exceptions.CSVManipulationException;

import javax.inject.Singleton;

@Singleton
public class CSVManipulationExceptionHandler implements ExceptionHandler<CSVManipulationException> {

	@Override
	public Response handle(CSVManipulationException exception) {
		return DefaultResponse.badRequest().entity(exception.getMessage());
	}

}
