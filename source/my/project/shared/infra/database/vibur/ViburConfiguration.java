package my.project.shared.infra.database.vibur;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.vibur.dbcp.ViburDBCPDataSource;
import my.project.shared.infra.database.config.DatabaseConfiguration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ViburConfiguration {

	public static ViburDBCPDataSource create(final DatabaseConfiguration dbConf) {
		final ViburDBCPDataSource viburDataSource = new ViburDBCPDataSource();

		viburDataSource.setAcquireRetryAttempts(dbConf.acquireRetryAttempt());
		viburDataSource.setAcquireRetryDelayInMs(dbConf.acquireRetryDelayInMs());
		viburDataSource.setClearSQLWarnings(dbConf.clearSqlWarnings());
		viburDataSource.setConnectionIdleLimitInSeconds(dbConf.connectionIdleLimitInSeconds());
		viburDataSource.setConnectionTimeoutInMs(dbConf.connectionTimeoutInMs());
		viburDataSource.setDefaultAutoCommit(dbConf.defaultAutoCommit());
		viburDataSource.setDefaultReadOnly(dbConf.defaultReadOnly());
		viburDataSource.setInitSQL(dbConf.initSql());
		viburDataSource.setJdbcUrl(dbConf.jdbcUrl());
		viburDataSource.setLogConnectionLongerThanMs(dbConf.logConnectionLongerThanMs());
		viburDataSource.setLoginTimeoutInSeconds(dbConf.loginTimeoutInSeconds());
		viburDataSource.setLogLargeResultSet(dbConf.logLargeResultSet());
		viburDataSource.setLogQueryExecutionLongerThanMs(dbConf.logQueryExecutionLongerThanMs());
		viburDataSource.setLogStackTraceForLargeResultSet(dbConf.logStacktraceForLargeResultSet());
		viburDataSource.setLogStackTraceForLongQueryExecution(dbConf.logStacktraceForLongQueryExecution());
		viburDataSource.setPoolFair(dbConf.poolFair());
		viburDataSource.setPoolInitialSize(dbConf.poolInitialSize());
		viburDataSource.setPoolMaxSize(dbConf.poolMaxSize());
		viburDataSource.setUsername(dbConf.username());
		viburDataSource.setPassword(dbConf.password());

		return viburDataSource;
	}

}
