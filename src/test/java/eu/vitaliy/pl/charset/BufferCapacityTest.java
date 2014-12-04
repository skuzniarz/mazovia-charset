package eu.vitaliy.pl.charset;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import junitparams.JUnitParamsRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <p>
 * Original (2012-03-15) code doesn't check if the output buffer is full.
 * Default internal buffer capacity is 8192 so it is easy to make it crash.
 * </p>
 * 
 * @author Szczepan Kuzniarz <szczepan.kuzniarz@gmail.com>
 */
@RunWith(JUnitParamsRunner.class)
public class BufferCapacityTest {
	/**
	 * 
	 * @throws IOException
	 */
	@Test
    public void mazoviaCharsetOutputTest() throws IOException {
		Path tmpPath = null;
		try {
			tmpPath = Files.createTempFile(null, null);
			BufferedWriter bw = Files.newBufferedWriter(tmpPath, Charset.forName("mazovia"));
			for (int i = 0 ; i < 8192 ; i++) {
				bw.write("1234");
			}
		} finally {
			if (tmpPath != null) {
				Files.delete(tmpPath);
			}
		}
    }
	
	/**
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Test
    public void mazoviaCharsetInputTest() throws IOException, URISyntaxException {
		URL testUrl = getClass().getResource("/test.txt");
		Path testPath = Paths.get(testUrl.toURI());
		char[] buffer = new char[10000];
		BufferedReader br = Files.newBufferedReader(testPath, Charset.forName("mazovia"));
		br.read(buffer);
    }
}
