package my.project.modules.accessLog.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import my.project.shared.enums.UserPrivilege;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor(staticName = "create")
public class AccessLogDTO {

	private long id;
	private Long userId;
	private String email;
	private UserPrivilege privilege;
	private String method;
	private String path;
	private String applicationToken;
	private String securityToken;
	private Date createdAt;

}
