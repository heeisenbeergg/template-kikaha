package sizebay.my.shared.infra.http.security;

import io.undertow.security.idm.Account;
import io.undertow.security.idm.Credential;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.Cookie;
import kikaha.core.modules.security.*;
import lombok.extern.slf4j.Slf4j;
import sizebay.catalog.client.CatalogAPI;
import sizebay.catalog.client.http.ApiException;
import sizebay.catalog.client.model.UserTenants;
import sizebay.my.modules.tenant.dtos.SimplifiedTenant;
import sizebay.my.modules.user.dtos.User;
import sizebay.my.shared.enums.UserPrivilege;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.Map;
import static java.util.Objects.isNull;


@Slf4j
@Singleton
public class Authentication extends BasicAuthenticationMechanism implements IdentityManager {

	@Inject
	CatalogAPI catalogAPI;

	public Authentication() {
		log.info("MySizebayAuthentication initialized...");
	}

	@Override
	public Account authenticate(HttpServerExchange httpServerExchange, Iterable<IdentityManager> identityManagers, Session session) {
		try {
			Credential credential = this.readCredential(httpServerExchange);
			String sessionId = this.getSession(httpServerExchange);

			User user =  !isNull(credential) ? this.verify(credential) : null;

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
	public User verify(Credential credential) {
		final UsernameAndPasswordCredential validCredentials = (UsernameAndPasswordCredential) credential;

		return this.retrieveAuthenticatedUserTenants(validCredentials.getUsername(), validCredentials.getPassword());
	}

	private User retrieveAuthenticatedUserTenants(String username, String password) {
		try {
			final User user = new User();
			final UserTenants userTenants = catalogAPI.authenticateAndRetrieveUser(username, password);

			user.setId(userTenants.getId());
			user.setName(username);
			user.setTenants(SimplifiedTenant.fromList(userTenants.getTenants()));
			user.setPrivilege(UserPrivilege.valueOf(userTenants.getPrivilege()));
			user.setCurrentTenant(user.getTenants().get(0));

			log.info("Connected " + username);

			return user;
		} catch (ApiException | IOException e) {
			log.error("Authentication failed for credentials: " + username + ", " + password, e);

			return null;
		} catch (Throwable e) {
			log.error("An error occurred at the time of user authentication: " + username + ", " + password, e);

			return null;
		}
	}

	private String getSession(HttpServerExchange httpServerExchange) {
		Map<String, Cookie> responseCookies = httpServerExchange.getResponseCookies();
		Map<String, Cookie> requestCookies = httpServerExchange.getRequestCookies();

		Cookie session = responseCookies.get(SessionIdManager.COOKIE);

		if(isNull(session)) {
			session = requestCookies.get(SessionIdManager.COOKIE);
		}

		return isNull(session) ? null : session.getValue();
	}

}
