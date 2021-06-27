package my.project.modules.accessLog.infra.databases.kikahaJdbi.queries;

import kikaha.jdbi.JDBI;
import my.project.modules.accessLog.infra.databases.kikahaJdbi.entities.AccessLog;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@JDBI
public interface AccessLogQueries {

	@GetGeneratedKeys
	@SqlUpdate("	INSERT INTO "
			   	 + "		szb_access_logs "
					 + "			(acl_user_id, acl_email, acl_privilege, acl_method, acl_path, acl_application_token, "
					 + "			acl_security_token, created_at) "
					 + "	VALUES "
					 + "		(:userId, :email, :privilege, :method, :path, :applicationToken, :securityToken, NOW()) ")
	int create(@BindBean AccessLog accessLog);

}
