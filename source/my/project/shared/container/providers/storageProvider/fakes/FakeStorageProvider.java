package my.project.shared.container.providers.storageProvider.fakes;

import my.project.shared.container.providers.storageProvider.models.IStorageProvider;
import my.project.shared.exceptions.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class FakeStorageProvider implements IStorageProvider {

	private Map<String, Map<String, String>> disk = new HashMap<>();

	@Override
	public String getFile(String directory, String fileName) throws FileNotFoundException {
		final Map<String, String> files = disk.get(directory);

		if(files == null) {
			throw new FileNotFoundException();
		}

		final String file = files.get(fileName);

		if(file == null) {
			throw new FileNotFoundException();
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
			return;
		}

		files.remove(fileName);

		disk.put(directory, files);
	}

}
