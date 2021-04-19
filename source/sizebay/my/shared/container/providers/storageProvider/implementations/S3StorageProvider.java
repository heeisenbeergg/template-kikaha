package sizebay.my.shared.container.providers.storageProvider.implementations;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sizebay.my.shared.container.providers.storageProvider.models.IStorageProvider;
import sizebay.my.shared.exceptions.MySizebayExceptions;

import javax.inject.Singleton;
import java.io.IOException;

@Slf4j
@Singleton
@RequiredArgsConstructor(staticName = "create")
public class S3StorageProvider implements IStorageProvider {

	private static final Regions DEFAULT_REGION = Regions.US_EAST_1;

	private final AmazonS3 amazonS3;

	public static S3StorageProvider createDefault() {
		final AmazonS3 amazonS3 = AmazonS3ClientBuilder
			.standard()
			.withRegion(DEFAULT_REGION)
			.build();

		return new S3StorageProvider(amazonS3);
	}

	@Override
	public String getFile(final String bucketName, final String fileName) {
		try {
			final S3Object s3Object = amazonS3.getObject(bucketName, fileName);

			return convertS3ObjectToString(s3Object);
		} catch (AmazonS3Exception e) {
			throw new MySizebayExceptions.FileNotFoundOnAWSS3();
		}
	}

	@Override
	public void saveFile(final String bucketName, final String file, final String data) {
		try{
			amazonS3.putObject(bucketName, file, data);
		} catch (Throwable e) {
			log.error("Error uploading file to AWS S3");

			throw new IllegalArgumentException();
		}
	}

	@Override
	public void deleteFile(final String bucketName, final String fileName) {
		final DeleteObjectRequest deleteObject = new DeleteObjectRequest(bucketName, fileName);

		amazonS3.deleteObject(deleteObject);
	}

	private String convertS3ObjectToString(final S3Object s3Object) {
		final S3ObjectInputStream content = s3Object.getObjectContent();

		try {
			return new String(content.readAllBytes());
		} catch (IOException e) {
			return "";
		}
	}

}
