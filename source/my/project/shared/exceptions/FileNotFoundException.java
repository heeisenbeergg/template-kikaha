package my.project.shared.exceptions;

public class FileNotFoundException extends Throwable {
	public FileNotFoundException() {
		super("File not found");
	}
}

