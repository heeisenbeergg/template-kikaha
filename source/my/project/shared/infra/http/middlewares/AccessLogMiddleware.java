package my.project.shared.infra.http.middlewares;

import io.undertow.security.api.SecurityContext;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import kikaha.core.modules.http.HttpHandlerDeploymentModule;
import kikaha.core.modules.http.WebResource;
import lombok.RequiredArgsConstructor;
import my.project.modules.accessLog.dtos.AccessLogDTO;
import my.project.modules.accessLog.services.ICreateAccessLogService;
import my.project.shared.infra.http.security.accounts.UserSession;
import my.project.shared.enums.UserPrivilege;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AccessLogMiddleware implements HttpHandlerDeploymentModule.HttpHandlerDeploymentCustomizer {

	private final ICreateAccessLogService createAccessLogService;

	@Inject
	public AccessLogMiddleware(final ICreateAccessLogService createAccessLogService) {
		this.createAccessLogService = createAccessLogService;
	}

	@Override
	public HttpHandler customize(final HttpHandler httpHandler, final WebResource webResource) {
		return new AccessLogMiddlewareHandler(httpHandler);
	}

	@RequiredArgsConstructor
	private class AccessLogMiddlewareHandler implements HttpHandler {

		final HttpHandler next;

		@Override
		public void handleRequest(final HttpServerExchange httpServerExchange) throws Exception {
			registerAccessLog(httpServerExchange);

			next.handleRequest(httpServerExchange);
		}

		private void registerAccessLog(final HttpServerExchange httpServerExchange) {
			final SecurityContext securityContext = httpServerExchange.getSecurityContext();

			final UserSession user = (UserSession) securityContext.getAuthenticatedAccount();

			final String method = httpServerExchange.getRequestMethod().toString();
			final String path = httpServerExchange.getRequestPath();

			final Long userId = user == null ? null : user.getId();
			final String email = user == null ? null : user.getName();
			final UserPrivilege userPrivilege =  user == null ? null : user.getPrivilege();

			final AccessLogDTO accessLog = AccessLogDTO.create()
				.setUserId(userId)
				.setEmail(email)
				.setPrivilege(userPrivilege)
				.setMethod(method)
				.setPath(path);

			createAccessLogService.execute(accessLog);
		}

	}
}
