package my.project.shared.container.providers.storageProvider.models;

import my.project.shared.exceptions.FileNotFoundException;

public interface IStorageProvider {

	String getFile(String directory, String fileName) throws FileNotFoundException;
	void saveFile(String directory, String fileName, String data);
	void deleteFile(String directory, String fileName);

}
