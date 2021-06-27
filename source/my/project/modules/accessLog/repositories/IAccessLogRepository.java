package my.project.modules.accessLog.repositories;

import my.project.modules.accessLog.dtos.AccessLogDTO;
import my.project.modules.accessLog.infra.databases.kikahaJdbi.entities.AccessLog;

public interface IAccessLogRepository {

	AccessLog create(AccessLogDTO accessLog);

}
