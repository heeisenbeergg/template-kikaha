package my.project.modules.accessLog.services;

import my.project.modules.accessLog.dtos.AccessLogDTO;
import my.project.modules.accessLog.infra.databases.kikahaJdbi.entities.AccessLog;
import my.project.modules.accessLog.repositories.IAccessLogRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CreateAccessLogService implements ICreateAccessLogService {

	private final IAccessLogRepository accessLogRepository;

	@Inject
	public CreateAccessLogService(final IAccessLogRepository accessLogRepository) {
		this.accessLogRepository = accessLogRepository;
	}

	@Override
	public AccessLog execute(AccessLogDTO accessLogDTO) {
		return accessLogRepository.create(accessLogDTO);
	}

}
