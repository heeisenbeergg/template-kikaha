package sizebay.my.shared.container.providers.storageProvider.models;

public interface IStorageProvider {

	String getFile(String directory, String fileName);
	void saveFile(String directory, String fileName, String data);
	void deleteFile(String directory, String fileName);

}
