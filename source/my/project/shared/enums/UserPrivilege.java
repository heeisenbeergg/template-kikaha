package my.project.shared.enums;

public enum UserPrivilege {

	USER("USER"),
	ADMIN("ADMIN");

	public final String value;

	UserPrivilege(final String value) {
		this.value = value;
	}

}

