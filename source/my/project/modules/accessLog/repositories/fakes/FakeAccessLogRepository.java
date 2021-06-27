package my.project.modules.accessLog.repositories.fakes;

import my.project.modules.accessLog.dtos.AccessLogDTO;
import my.project.modules.accessLog.infra.databases.kikahaJdbi.entities.AccessLog;
import my.project.modules.accessLog.repositories.IAccessLogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FakeAccessLogRepository implements IAccessLogRepository {

	private final List<AccessLog> accessLogs = new ArrayList<>();
	private final AtomicLong ids = new AtomicLong(1);

	@Override
	public AccessLog create(AccessLogDTO accessLogDTO) {
		final AccessLog accessLog = AccessLog.create(accessLogDTO.getMethod(), accessLogDTO.getPath())
			.setUserId(accessLogDTO.getUserId())
			.setEmail(accessLogDTO.getEmail())
			.setPrivilege(accessLogDTO.getPrivilege())
			.setApplicationToken(accessLogDTO.getApplicationToken())
			.setSecurityToken(accessLogDTO.getSecurityToken());

		accessLog.setId(ids.incrementAndGet());

		accessLogs.add(accessLog);

		return accessLog;
	}

}
