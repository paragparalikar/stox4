package com.stox.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class FileUtil {

	public static Path safeGet(String path) {
		try {
			final File file = new File(path);
			file.getParentFile().mkdirs();
			file.createNewFile();
			return file.toPath();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
