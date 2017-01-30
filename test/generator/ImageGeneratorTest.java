package generator;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import reader.FileToBytes;
import reader.ImageRGBToBytes;
import reader.ToBytes;
import writer.FileGenerator;
import writer.Generator;
import writer.ImageGenerator;
import writer.Generator.Extension;

public class ImageGeneratorTest {
	private static String FILE_NAME = "test";
	private static String ORIGINE_FILE = "ressources/" + FILE_NAME + ".txt";
	private static String DESTINATION_FOLDER_IMAGE = "target/image/";
	private static String DESTINATION_FOLDER_CODE = "target/code/";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		new File(DESTINATION_FOLDER_CODE).mkdirs();
		new File(DESTINATION_FOLDER_IMAGE).mkdirs();
		
		PrintWriter writer = new PrintWriter(ORIGINE_FILE, "UTF-8");

		for (int i = 0; i < 5; i++) {
			writer.println("Coucou");
		}

		writer.close();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGenerationCanBeReversed() {
		Generator generator = new ImageGenerator(new FileToBytes(ORIGINE_FILE), DESTINATION_FOLDER_IMAGE);
		try {
			generator.generate(Extension.BMP);
		} catch (IOException e) {
			fail();
		}

		File image = new File(DESTINATION_FOLDER_IMAGE + FILE_NAME + Extension.BMP.getExtension());
		assertTrue(image.exists());

		generator = new FileGenerator(
				new ImageRGBToBytes(DESTINATION_FOLDER_IMAGE + FILE_NAME + Extension.BMP.getExtension()),
				DESTINATION_FOLDER_CODE);
		try {
			generator.generate(Extension.TXT);
		} catch (IOException e) {
			fail();
		}

		File codeReversed = new File(DESTINATION_FOLDER_CODE + FILE_NAME + Extension.TXT.getExtension());
		assertTrue(codeReversed.exists());

		try {
			byte[] codeOriginBytes = new FileToBytes(ORIGINE_FILE).getBytes(),
					codeGeneratedBytes = new FileToBytes(codeReversed.getAbsolutePath()).getBytes();

			assertArrayEquals(codeOriginBytes, codeGeneratedBytes);
		} catch (IOException e) {
			fail();
		}
	}

}
