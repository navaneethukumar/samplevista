package com.spire.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

import com.spire.base.controller.Logging;

public class AssetFileDecode {

	public File decodeBase64File(File inputFile) {

		String tmpPath = System.getProperty("java.io.tmpdir");
		File decodeFile = new File(tmpPath + File.separator + inputFile.getName());
		try {
			int length = (int) inputFile.length();
			BufferedInputStream reader = new BufferedInputStream(new FileInputStream(inputFile));
			byte[] bytes = new byte[length];
			reader.read(bytes, 0, length);
			byte[] decodedBytes = Base64.getDecoder().decode(bytes);

			FileOutputStream flushStream = new FileOutputStream(decodeFile, false);

			BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(decodeFile));
			writer.write(decodedBytes);
			flushStream.close();
			writer.flush();
			writer.close();
			reader.close();

		} catch (Exception e) {
			Logging.log("error in decoding file");
			return null;
		}
		return decodeFile;

	}
}
