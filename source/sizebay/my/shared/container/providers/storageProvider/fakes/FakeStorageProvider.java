package sizebay.my.shared.container.providers.storageProvider.fakes;

import sizebay.my.shared.container.providers.storageProvider.models.IStorageProvider;
import sizebay.my.shared.exceptions.MySizebayExceptions;

import java.util.HashMap;
import java.util.Map;

public class FakeStorageProvider implements IStorageProvider {

	private Map<String, Map<String, String>> disk = new HashMap<>();

	@Override
	public String getFile(String directory, String fileName) {
		final Map<String, String> files = disk.get(directory);

		if(files == null) {
			throw new MySizebayExceptions.FileNotFoundOnAWSS3();
		}

		final String file = files.get(fileName);

		if(file == null) {
			throw new MySizebayExceptions.FileNotFoundOnAWSS3();
		}

		return file;
	}

	@Override
	public void saveFile(String directory, String fileName, String data) {
		final Map<String, String> file = new HashMap<>(){{ put(fileName, data); }};

		this.disk.put(directory, file);
	}

	@Override
	public void deleteFile(String directory, String fileName) {
		final Map<String, String> files = disk.get(directory);

		if(files == null) {
			throw new MySizebayExceptions.FileNotFoundOnAWSS3();
		}

		files.remove(fileName);

		disk.put(directory, files);
	}

}
