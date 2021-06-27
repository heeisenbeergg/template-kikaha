package my.project.shared.infra.http.security.authentications;

import io.undertow.security.idm.Account;
import io.undertow.security.idm.Credential;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.Cookie;
import kikaha.core.modules.security.*;
import lombok.extern.slf4j.Slf4j;
import my.project.shared.infra.http.security.accounts.UserSession;
import my.project.shared.enums.UserPrivilege;
import my.project.shared.infra.http.security.sessions.SessionManager;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.Map;
import static java.util.Objects.isNull;


@Slf4j
@Singleton
public class BasicAuthentication extends BasicAuthenticationMechanism implements IdentityManager {

	public BasicAuthentication() {
		log.info("Basic authentication initialized...");
	}

	@Override
	public Account authenticate(final HttpServerExchange httpServerExchange, final Iterable<IdentityManager> identityManagers, final Session session) {
		try {
			final Credential credential = this.readCredential(httpServerExchange);
			final String sessionId = this.getSession(httpServerExchange);

			final UserSession user =  !isNull(credential) ? this.verify(credential) : null;

			if(!isNull(user) && !isNull(session)) {
				user.setSessionId(sessionId);

				return user;
			}

			return null;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public UserSession verify(final Credential credential) {
		final UsernameAndPasswordCredential validCredentials = (UsernameAndPasswordCredential) credential;

		return this.retrieveAuthenticatedUserTenants(validCredentials.getUsername(), validCredentials.getPassword());
	}

	private UserSession retrieveAuthenticatedUserTenants(final String username, final String password) {
		try {
			final UserSession user = new UserSession();

			user.setId(1L);
			user.setName(username);
			user.setPrivilege(UserPrivilege.USER);

			log.info("Connected " + username);

			return user;
		} catch (Throwable e) {
			log.error("Authentication failed for credentials: " + username + ", " + password, e);

			return null;
		}
	}

	private String getSession(final HttpServerExchange httpServerExchange) {
		final Map<String, Cookie> responseCookies = httpServerExchange.getResponseCookies();
		final Map<String, Cookie> requestCookies = httpServerExchange.getRequestCookies();

		Cookie session = responseCookies.get(SessionManager.COOKIE);

		if(isNull(session)) {
			session = requestCookies.get(SessionManager.COOKIE);
		}

		return isNull(session) ? null : session.getValue();
	}

}
