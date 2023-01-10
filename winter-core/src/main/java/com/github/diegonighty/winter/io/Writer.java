package com.github.diegonighty.winter.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Writer {

	public static void copy(String source, File destination) throws IOException {
		try (InputStream sourceStream = Writer.class.getResourceAsStream(source)) {
			if (sourceStream == null) {
				throw new IOException("Source file not found");
			}

			try (InputStream bufferedStream = new BufferedInputStream(sourceStream)) {
				Files.copy(bufferedStream, destination.toPath());
			}
		}
	}

}
