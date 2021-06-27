package my.project.shared.infra.http.security.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.undertow.security.idm.Account;
import lombok.Getter;
import lombok.Setter;
import my.project.shared.enums.UserPrivilege;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

@Getter
@Setter
public class UserSession implements Account {

	private Long id;
	private String name;
	private String sessionId;

	@JsonIgnore
	private transient UserPrivilege privilege;

	@JsonIgnore
	private transient final Principal principal = () -> {
		throw new UnsupportedOperationException();
	};

	@JsonIgnore
	private transient final Set<String> roles = Collections.emptySet();
}
