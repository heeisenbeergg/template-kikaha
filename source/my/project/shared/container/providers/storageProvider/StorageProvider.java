package my.project.shared.container.providers.storageProvider;

import my.project.shared.container.providers.storageProvider.implementations.S3StorageProvider;
import my.project.shared.container.providers.storageProvider.models.IStorageProvider;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
public class StorageProvider {

	@Produces
	public IStorageProvider provide() {
		return S3StorageProvider.createDefault();
	}

}
