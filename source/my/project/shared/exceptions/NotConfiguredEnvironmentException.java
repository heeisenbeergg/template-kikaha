package my.project.shared.exceptions;

public class NotConfiguredEnvironmentException extends Throwable {
	public NotConfiguredEnvironmentException() {
		super("Not configured environment");
	}
}
