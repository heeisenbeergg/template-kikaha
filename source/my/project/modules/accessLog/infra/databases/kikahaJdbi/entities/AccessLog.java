package my.project.modules.accessLog.infra.databases.kikahaJdbi.entities;

import kikaha.jdbi.serializers.Column;
import kikaha.jdbi.serializers.Entity;
import lombok.*;
import lombok.experimental.Accessors;
import my.project.shared.enums.UserPrivilege;

import java.util.Date;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "create")
public class AccessLog {

	@Column("acl_id")
	private long id;

	@Column("acl_user_id")
	private Long userId;

	@Column("acl_email")
	private String email;

	@Column("acl_privilege")
	private UserPrivilege privilege;

	@NonNull
	@Column("acl_method")
	private String method;

	@NonNull
	@Column("acl_path")
	private String path;

	@Column("acl_application_token")
	private String applicationToken;

	@Column("acl_security_token")
	private String securityToken;

	@Column("created_at")
	private Date createdAt;

}
