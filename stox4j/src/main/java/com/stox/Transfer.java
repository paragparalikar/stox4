package com.stox;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.imageio.ImageIO;

public class Transfer {

	public static void main(String[] args) throws Exception {
		final Path path = Paths.get("C:\\Users\\parag\\Downloads\\bicycle.png");
		final BufferedImage image = ImageIO.read(path.toFile());
		final DataBufferByte dataBuffer = (DataBufferByte) image.getRaster().getDataBuffer();
		final byte[] imageData = dataBuffer.getData();
		final ByteBuffer byteBuffer = ByteBuffer.wrap(imageData);
		final int fileSize = byteBuffer.getInt();
		final byte[] fileData = new byte[fileSize];
		byteBuffer.get(fileData);
		Files.write(path.getParent().resolve("bicycle.zip"), fileData, 
				StandardOpenOption.WRITE, StandardOpenOption.CREATE);
		
	}

}
