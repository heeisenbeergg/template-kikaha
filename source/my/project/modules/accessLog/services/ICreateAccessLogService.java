package my.project.modules.accessLog.services;

import my.project.modules.accessLog.dtos.AccessLogDTO;
import my.project.modules.accessLog.infra.databases.kikahaJdbi.entities.AccessLog;

public interface ICreateAccessLogService {

	AccessLog execute(AccessLogDTO accessLogDTO);

}
