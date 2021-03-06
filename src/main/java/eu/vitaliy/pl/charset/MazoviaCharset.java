package eu.vitaliy.pl.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

/**
 *
 * @author  Vitaliy Oliynyk
 */
public class MazoviaCharset extends Charset {

     public final static char[][] CHARS_UNICODE_SORT = new char[][] {
           {'\u0104', 143}//Ą
         , {'\u0105', 134}//ą
         , {'\u0106', 149}//Ć
         , {'\u0107', 141}//ć
         , {'\u0118', 144}//Ę
         , {'\u0119', 145}//ę
         , {'\u0141', 156}//Ł
         , {'\u0142', 146}//ł
         , {'\u0143', 165}//Ń
         , {'\u0144', 164}//ń
         , {'\u00D3', 163}//Ó
         , {'\u00F3', 162}//ó
         , {'\u015A', 152}//Ś
         , {'\u015B', 158}//ś
         , {'\u0179', 160}//Ź
         , {'\u017A', 166}//ź
         , {'\u017B', 161}//Ż
         , {'\u017C', 167}//ż
     };
     
     /**
      * Lookup table for charset decoder - maps bytes to chars
      */
     private static final char[] DECODER_LOOKUP_TABLE = new char[256];
     
     /**
      * <p>
      * Prepares lookup table.
      * </p>
      */
     static {
    	 for (int i = 0 ; i < DECODER_LOOKUP_TABLE.length ; i++) {
    		 DECODER_LOOKUP_TABLE[i] = (char) i;
    	 }
    	 for (int i = 0 ; i < CHARS_UNICODE_SORT.length ; i++) {
    		 DECODER_LOOKUP_TABLE[CHARS_UNICODE_SORT[i][1]] = CHARS_UNICODE_SORT[i][0];
    	 }
     }

     public MazoviaCharset(String canonicalName, String[] aliases) {
         super(canonicalName, aliases);
     }
     
     @Override
     public boolean contains(Charset cs) {
         return cs.equals(this);
     }

     @Override
     public CharsetDecoder newDecoder() {
         return new PrivCharsetDecoder(this, 1, 1);
     }

     @Override
     public CharsetEncoder newEncoder() {
         return new PrivCharsetEncoder(this, 1, 1);
     }

     public class PrivCharsetEncoder extends CharsetEncoder {
         public PrivCharsetEncoder(Charset cs, float averageBytesPerChar,
                        float maxBytesPerChar, byte[] replacement)
         {
             super(cs, averageBytesPerChar, maxBytesPerChar, replacement);
         }

         public PrivCharsetEncoder(Charset cs, float averageBytesPerChar,
                                        float maxBytesPerChar)
         {
             super(cs, averageBytesPerChar, maxBytesPerChar);
         }
         
         @Override
         protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out) {
            while (in.hasRemaining()) {
                 if (!out.hasRemaining()) {
                     return CoderResult.OVERFLOW;
                 }
                 char inputChar = in.get();

                 for (int i = 0; i < CHARS_UNICODE_SORT.length; i++) {
                     if (inputChar == CHARS_UNICODE_SORT[i][0]) {
                         inputChar = CHARS_UNICODE_SORT[i][1];
                     }
                 }

                 out.put((byte)(inputChar & 0xFF));
             }
             return CoderResult.UNDERFLOW;
         }
     }


	/**
	 * 
	 */
	public class PrivCharsetDecoder extends CharsetDecoder {
		/**
		 * 
		 * @param cs
		 * @param averageCharsPerByte
		 * @param maxCharsPerByte
		 */
		public PrivCharsetDecoder(Charset cs, float averageCharsPerByte, float maxCharsPerByte) {
			super(cs, averageCharsPerByte, maxCharsPerByte);
		}

		@Override
		protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out) {
			while (in.hasRemaining()) {
				if (!out.hasRemaining()) {
					return CoderResult.OVERFLOW;
				}
				char inputChar = (char) (in.get() & 0x00FF);
				out.put(DECODER_LOOKUP_TABLE[inputChar]);
			}
			return CoderResult.UNDERFLOW;
		}
	}
}
