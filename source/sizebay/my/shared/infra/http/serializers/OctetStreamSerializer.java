package sizebay.my.shared.infra.http.serializers;

import io.undertow.server.HttpServerExchange;
import kikaha.core.modules.http.ContentType;
import kikaha.urouting.api.Serializer;

import javax.inject.Singleton;
import java.io.*;

import static io.undertow.util.Headers.CONTENT_DISPOSITION;
import static io.undertow.util.StatusCodes.CREATED;

@Singleton
@ContentType(OctetStreamSerializer.MIME)
public class OctetStreamSerializer implements Serializer {

	private static final String CONTENT_DISPOSITION_VALUE_KEY = "attachment;filename=%s";

	public static final String MIME = "application/octet-stream";

	@Override
	public <T> void serialize(T object, HttpServerExchange exchange, String encoding) throws IOException {
		if (!exchange.isBlocking()) {
			exchange.startBlocking();
		}

		final File content = (File) object;

		setHeader(exchange, content.getName());
		setBody(exchange, content);

		send(exchange);
	}

	private void setHeader(final HttpServerExchange exchange, final String fileName) {
		final String contentDispositionValue = String.format(CONTENT_DISPOSITION_VALUE_KEY, fileName);

		exchange.getResponseHeaders().add(CONTENT_DISPOSITION, contentDispositionValue);
	}

	private void setBody(final HttpServerExchange exchange, final File body) throws IOException {
		final InputStream inputStream = new FileInputStream(body);
		final OutputStream outputStream = exchange.getOutputStream();

		outputStream.write(inputStream.readAllBytes());
		outputStream.flush();

		inputStream.close();

		body.delete();
	}

	private void send(final HttpServerExchange exchange) {
		exchange.setStatusCode(CREATED).endExchange();
	}

}
