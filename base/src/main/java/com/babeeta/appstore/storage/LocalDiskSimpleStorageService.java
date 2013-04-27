package com.babeeta.appstore.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;

public class LocalDiskSimpleStorageService implements SimpleStorageService {

	private static final String STORAGE_HOME = "/var/lib/android-coral-bay/simple-storage";

	@Override
	public void delete(String volume, String id) throws FileNotFoundException,
			IOException {
		File f = getFile(volume, id);
		if (!f.exists()) {
			throw new FileNotFoundException("Delete: no such file!");
		}
		if (!f.delete()) {
			throw new IOException("Delete: deletion failed !");
		}

	}

	@Override
	public InputStream get(String volume, String id)
			throws FileNotFoundException, IOException {
		FileInputStream inputStream = new FileInputStream(getFile(volume, id));
		return inputStream;
	}

	private File getFile(String volume, String id) {
		StringBuffer path = new StringBuffer()
				.append(STORAGE_HOME).append(File.separatorChar)
				.append(volume).append(File.separatorChar)
				.append(id.substring(0, 2)).append(File.separatorChar)
				.append(id.substring(2, 4)).append(File.separatorChar)
				.append(id);
		File file = new File(path.toString());
		return file;
	}

	@Override
	public String put(String volume, InputStream inputStream)
			throws IOException {
		String id = UUID.randomUUID().toString();
		File distFile = getFile(volume, id);
		distFile.getParentFile().mkdirs();
		FileOutputStream outputStream = new FileOutputStream(distFile);
		ByteStreams.copy(inputStream, outputStream);
		Closeables.closeQuietly(outputStream);
		return id;
	}

}
