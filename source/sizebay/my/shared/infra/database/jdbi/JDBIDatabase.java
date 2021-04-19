package sizebay.my.shared.infra.database.jdbi;

import kikaha.config.Config;
import sizebay.my.shared.infra.database.config.DatabaseConfiguration;
import kikaha.db.DataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.vibur.dbcp.ViburDBCPDataSource;
import sizebay.my.shared.infra.database.vibur.ViburConfiguration;
import sizebay.my.shared.infra.environment.EnvironmentVariable;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

/*
* JDBI is instantiated by the kikaha-jdbi3 module.
*
* Vibur is used to manage the pools.
* */

@Slf4j
@Singleton
public class JDBIDatabase implements DataSourceFactory {

	@Inject
	private EnvironmentVariable environmentVariable;

	@Override
	public DataSource newDataSource(String name, Config config) {
		final String dbHost = environmentVariable.get("DB_HOST");
		final String dbName = environmentVariable.get("DB_NAME");
		final String dbUser = environmentVariable.get("DB_USER");
		final String dbPassword = environmentVariable.get("DB_PASSWORD");

		final String jdbcURL = createJDBCUrl(dbHost, dbName);

		final DatabaseConfiguration databaseConfiguration = DatabaseConfiguration.create(
			name, config, jdbcURL, dbUser, dbPassword
		);

		final ViburDBCPDataSource vibur = configureVibur(databaseConfiguration);

		return vibur;
	}

	private ViburDBCPDataSource configureVibur(final DatabaseConfiguration databaseConfiguration) {
		final ViburDBCPDataSource vibur = ViburConfiguration.create(databaseConfiguration);

		vibur.start();

		Runtime.getRuntime().addShutdownHook(new Thread(vibur::terminate));

		return vibur;
	}

	private String createJDBCUrl(String databaseHost, String databaseName) {
		return "jdbc:mysql://" + databaseHost + "/" + databaseName;
	}

}
