package my.project.shared.infra.http.security.sessions;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.Cookie;
import io.undertow.server.handlers.CookieImpl;
import kikaha.core.modules.security.SessionCookie;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Deque;
import java.util.function.Supplier;

@Slf4j
public class SessionManager  extends SessionCookie {

	public static final String PARAMETER = "mysession";
	public static final String COOKIE = "MYSESSIONID";
	public static final int COOKIE_LIFE_IN_MONTHS = 12;

	public SessionManager() {
		super(COOKIE);

		log.info("Session manager initialized...");
	}

	@Override
	public String retrieveSessionIdFrom(final HttpServerExchange httpServerExchange, final Supplier<String> supplier) {
		String sessionId = retrieveSessionFromCookie(httpServerExchange);

		if(isSessionIdEmpty(sessionId)) {
			sessionId = retrieveSessionFromQueryParameter(httpServerExchange);
		}

		if(isSessionIdEmpty(sessionId)) {
			sessionId = supplier.get();
		}

		return sessionId;
	}

	@Override
	public void attachSessionId(final HttpServerExchange httpServerExchange, final String sessionId) {
		final Cookie cookie = new CookieImpl(COOKIE, sessionId).setPath("/").setExpires(getCookieExpiresDate().getTime()).setHttpOnly(false);

		httpServerExchange.setResponseCookie(cookie);
	}

	private String retrieveSessionFromQueryParameter(final HttpServerExchange httpServerExchange){
		final Deque<String> foundParams = httpServerExchange.getQueryParameters().get(PARAMETER);

		return foundParams != null ? foundParams.getFirst() : null;
	}

	private String retrieveSessionFromCookie(final HttpServerExchange httpServerExchange){
		final Cookie cookie = httpServerExchange.getRequestCookies().get(COOKIE);

		return cookie != null ? cookie.getValue() : null;
	}

	private boolean isSessionIdEmpty(final String sessionId) {
		return sessionId == null || sessionId.isEmpty();
	}

	private Calendar getCookieExpiresDate(){
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, COOKIE_LIFE_IN_MONTHS);

		return calendar;
	}

}

