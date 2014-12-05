package eu.vitaliy.pl.charset;

import static org.fest.assertions.Assertions.assertThat;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */

public class SPITest {

	private String unicodeString;
	private byte[] mazoviaBytes;
	private String asciiString;
	private byte[] asciiBytes;

	/**
	 * 
	 */
	@Before
	public void before() {
		unicodeString = makeUnicodeTestString();
		mazoviaBytes = makeMazoviaTestBytes();
		asciiString = makeASCIITestString();
		asciiBytes = makeASCIITestBytes();
	}

	/**
	 * 
	 */
	@Test
	public void testDecodeFromMazovia() {
		assertThat(new String(mazoviaBytes, Charset.forName("mazovia"))).isEqualTo(unicodeString);
	}

	/**
	 * 
	 */
	@Test
	public void testDecodeFromMazoviaASCII() {
		String mazoviaString = new String(asciiBytes, Charset.forName("mazovia"));
		assertThat(mazoviaString).isEqualTo(asciiString);
	}

	/**
	 * 
	 */
	@Test
	public void testEncodeToMazovia() {
		assertThat(unicodeString.getBytes(Charset.forName("mazovia"))).isEqualTo(mazoviaBytes);
	}

	/**
	 * 
	 */
	@Test(expected = UnsupportedCharsetException.class)
	public void testInvalidCharset() {
		Charset.forName("mazovia-bad-name");
	}

	/**
	 * 
	 * @return
	 */
	private String makeUnicodeTestString() {
		char[] unicodeChars = new char[MazoviaCharset.CHARS_UNICODE_SORT.length];
		for (int i = 0 ; i < unicodeChars.length ; i++) {
			unicodeChars[i] = MazoviaCharset.CHARS_UNICODE_SORT[i][0];
		}
		return new String(unicodeChars);
	}

	/**
	 * 
	 * @return
	 */
	private byte[] makeMazoviaTestBytes() {
		byte[] mazoviaBytes = new byte[MazoviaCharset.CHARS_UNICODE_SORT.length];
		for (int i = 0 ; i < mazoviaBytes.length ; i++) {
			mazoviaBytes[i] = (byte) MazoviaCharset.CHARS_UNICODE_SORT[i][1];
		}
		return mazoviaBytes;
	}
	
	/**
	 * 
	 * @return
	 */
	private String makeASCIITestString() {
		StringBuilder asciiStringBuilder = new StringBuilder();
		for (int i = 0; i < 128; i++) {
			asciiStringBuilder.append((char) i);
		}
		return asciiStringBuilder.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	private byte[] makeASCIITestBytes() {
		byte[] asciiBytes = new byte[128];
		for (int i = 0; i < asciiBytes.length; i++) {
			asciiBytes[i] = (byte) i;
		}
		return asciiBytes;
	}
}
