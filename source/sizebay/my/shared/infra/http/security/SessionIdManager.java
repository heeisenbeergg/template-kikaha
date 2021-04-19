package sizebay.my.shared.infra.http.security;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.Cookie;
import io.undertow.server.handlers.CookieImpl;
import kikaha.core.modules.security.SessionCookie;
import lombok.extern.slf4j.Slf4j;
import java.util.Calendar;
import java.util.Deque;
import java.util.function.Supplier;

@Slf4j
public class SessionIdManager extends SessionCookie {

	static final String PARAMETER = "mysession";
	static final String COOKIE = "MYSIZESESSIONID";
	static final int COOKIE_LIFE_IN_MONTHS = 12;

	public SessionIdManager() {
		super(COOKIE);

		log.info("MySizebaySessionIdManager initialized...");
	}

	@Override
	public String retrieveSessionIdFrom(HttpServerExchange httpServerExchange, Supplier<String> supplier) {
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
	public void attachSessionId(HttpServerExchange httpServerExchange, String sessionId) {
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

	private boolean isSessionIdEmpty(String sessionId) {
		return sessionId == null || sessionId.isEmpty();
	}

	private Calendar getCookieExpiresDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, COOKIE_LIFE_IN_MONTHS);

		return calendar;
	}

}
