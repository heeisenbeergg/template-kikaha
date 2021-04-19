package sizebay.my.shared.infra.environment;


public enum ENV {

	DEVELOP, STAGING, PRODUCTION;

	public static ENV with(String environment) {
		if(environment == null || environment.isEmpty()) {
			return ENV.DEVELOP;
		}

		return ENV.valueOf(environment.toUpperCase());
	}
}
