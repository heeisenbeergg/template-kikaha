package sizebay.my.shared.utils;

import kikaha.urouting.api.DefaultResponse;
import kikaha.urouting.api.Response;
import lombok.extern.slf4j.Slf4j;
import sizebay.catalog.client.http.ApiException;


@Slf4j
public class Responses {

	public static Response created(Object value) {
		return DefaultResponse.created().entity(value);
	}

	public static Response ok() {
		return DefaultResponse.ok();
	}

	public static Response ok(Object value) {
		return DefaultResponse.ok(value);
	}

	public static Response noContent() {
		return DefaultResponse.noContent();
	}

	public static Response updated() {
		return DefaultResponse.ok();
	}

	public static Response notFound() {
		return DefaultResponse.notFound();
	}

	public static Response notFound(String message) {
		return DefaultResponse.notFound().entity(message);
	}

	public static Response error(ApiException responseAPI) {
		log.info(responseAPI.message);

		if(responseAPI.status == 404) {
			return DefaultResponse.notFound();
		} else if(responseAPI.status == 400) {
			return  DefaultResponse.badRequest();
		}	else {
			return DefaultResponse.serverError(responseAPI.message);
		}

	}

}
