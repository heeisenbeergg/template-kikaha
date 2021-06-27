package my.project.modules.accessLog.infra.databases.kikahaJdbi.repositories;

import my.project.modules.accessLog.dtos.AccessLogDTO;
import my.project.modules.accessLog.infra.databases.kikahaJdbi.entities.AccessLog;
import my.project.modules.accessLog.infra.databases.kikahaJdbi.queries.AccessLogQueries;
import my.project.modules.accessLog.repositories.IAccessLogRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AccessLogRepository implements IAccessLogRepository {

	private AccessLogQueries accessLogQueries;

	@Inject
	public AccessLogRepository(final AccessLogQueries accessLogQueries) {
		this.accessLogQueries = accessLogQueries;
	}

	@Override
	public AccessLog create(AccessLogDTO accessLogDTO) {
		final AccessLog accessLog = AccessLog.create(accessLogDTO.getMethod(), accessLogDTO.getPath())
			.setUserId(accessLogDTO.getUserId())
			.setEmail(accessLogDTO.getEmail())
			.setPrivilege(accessLogDTO.getPrivilege())
			.setApplicationToken(accessLogDTO.getApplicationToken())
			.setSecurityToken(accessLogDTO.getSecurityToken());

		final int id =  accessLogQueries.create(accessLog);

		accessLog.setId(id);

		return accessLog;
	}

}
