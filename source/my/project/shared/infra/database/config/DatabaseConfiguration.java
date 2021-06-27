package my.project.shared.infra.database.config;

import kikaha.config.Config;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DatabaseConfiguration {

	private final String name;
	private final String jdbcUrl;
	private final String username;
	private final String password;
	private final int poolInitialSize;
	private final int poolMaxSize;
	private final boolean poolFair;
	private final boolean poolEnableConnectionTracking;
	private final String driverClassName;
	private final int connectionTimeoutInMs;
	private final int loginTimeoutInSeconds;
	private final int acquireRetryDelayInMs;
	private final int acquireRetryAttempt;
	private final int connectionIdleLimitInSeconds;
	private final int validateTimeoutInSeconds;
	private final String testConnectionQuery;
	private final String initSql;
	private final int logQueryExecutionLongerThanMs;
	private final boolean logStacktraceForLongQueryExecution;
	private final int logLargeResultSet;
	private final boolean logStacktraceForLargeResultSet;
	private final int logConnectionLongerThanMs;
	private final boolean clearSqlWarnings;
	private final boolean resetDefaultsAfterUse;
	private final boolean defaultAutoCommit;
	private final boolean defaultReadOnly;
	private final int statementCacheMaxSize;
	private final String poolReducerClass;
	private final int reducerTimeIntervalInSeconds;
	private final int reducerSamples;


	public static DatabaseConfiguration create(String name, Config config, String jdbcUrl, String username, String password) {
		return new DatabaseConfiguration(
				name,
				jdbcUrl,
				username,
				password,
				config.getInteger("pool-initial-size", 1),
				config.getInteger("pool-max-size", 10),
				config.getBoolean("pool-fair", true),
				config.getBoolean( "pool-enable-connection-tracking", false),
				config.getString("driver-class-name"),
				config.getInteger("connection-timeout-in-ms", 30000),
				config.getInteger("login-timeout-in-seconds", 10),
				config.getInteger("acquire-retry-delay-in-ms", 1000),
				config.getInteger("acquire-retry-attempt", 3),
				config.getInteger("connection-idle-limit-in-seconds", 15),
				config.getInteger("validate-timeout-in-seconds", 3),
				config.getString("test-connection-query", "isValid"),
				config.getString("init-sql"),
				config.getInteger("log-query-execution-longer-than-ms", 50),
				config.getBoolean("log-stacktrace-for-long-query-execution", false),
				config.getInteger("log-large-resultset", 500),
				config.getBoolean("log-stacktrace-for-large-resultset", false),
				config.getInteger("log-connection-longer-than-ms", 3000),
				config.getBoolean("clear-sql-warnings", false),
				config.getBoolean("reset-defaults-after-use", false),
				config.getBoolean("default-auto-commit", true),
				config.getBoolean("default-read-only", false),
				config.getInteger("statement-cache-max-size", 0),
				config.getString("pool-reducer-class", "org.vibur.dbcp.pool.PoolReducer"),
				config.getInteger("reducer-time-interval-in-seconds", 60),
				config.getInteger("reducer-samples", 20)
		);
	}

	@Override
	public String toString() {
		return name + "[" + jdbcUrl + ";" + username + ";" + password + ";" + driverClassName + "]";
	}

}
