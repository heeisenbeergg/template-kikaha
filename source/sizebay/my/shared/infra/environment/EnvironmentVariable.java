package sizebay.my.shared.infra.environment;

import kikaha.config.Config;
import lombok.Getter;
import lombok.experimental.Accessors;
import sizebay.my.shared.exceptions.NotConfiguredEnvironmentException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import static java.util.Objects.isNull;

/*
*	By default it tries to search for variables in the environment,
* if not, it tries to search the file -> resources/conf/application.yml
* */

/*
	TODO: criar um provider para fornecer as variaveis de ambiente:
		# implementações
			- SystemsManagerAWSProvider
			- KikahaConfigProvider
*/

@Singleton
public class EnvironmentVariable {

	private final static String PREFIX = "environment";
	private final static String PREFIX_VARIABLE = PREFIX + "." + "variables";

	@Inject
	private Config config;

	@Getter
	@Accessors(fluent = true)
	private ENV environment;

//	@Inject
//	public EnvironmentVariable (final Config config) throws NotConfiguredEnvironmentException {
//		try {
//			this.config = config;
//			this.environment = ENV.with(getEnvironment());
//		} catch (Exception e) {
//			throw new NotConfiguredEnvironmentException();
//		}
//	}

	@PostConstruct
	public void load() throws NotConfiguredEnvironmentException {
		try {
			this.environment = ENV.with(getEnvironment());
		} catch (Exception e) {
			throw new NotConfiguredEnvironmentException();
		}
	}

	private String getEnvironment() {
		final String systemVariable = getVariable("environment");

		if(isNull(systemVariable)) {
			final String variable = PREFIX + ".environment";

			return getInApplicationConfig(variable);
		}

		return systemVariable;
	}

	public String get(final String variableName) {
		final String systemVariable = getVariable(variableName);

		if(isNull(systemVariable)) {
			return getVariableInApplicationConfig(variableName);
		}

		return systemVariable;
	}

	private String getVariable(final String variableName){
		final String variable = variableName.toUpperCase();

		return System.getenv(variable);
	}

	private String getVariableInApplicationConfig(final String variableName){
		final String variable = variableName.toLowerCase();
		final String prefix_variable = PREFIX_VARIABLE + "." + variable;

		return getInApplicationConfig(prefix_variable);
	}

	private String getInApplicationConfig(final String variableName){
		return config.getString(variableName);
	}

}
