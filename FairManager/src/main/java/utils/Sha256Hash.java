package utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Hash {

	private static final MessageDigest DIGEST;
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    static {
        try {
            DIGEST = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new Error(e);
        }
    }

    public static synchronized byte[] hash(byte[] data){
        return DIGEST.digest(data);
    }

    public static synchronized byte[] hash(byte[] data, int offset, int length){
        DIGEST.update(data, offset, length);
        return DIGEST.digest();
    }


	public static byte[] hash(String filepath) throws NoSuchAlgorithmException, IOException {
		int buff = 128;
		// 16384
		RandomAccessFile file = new RandomAccessFile(filepath, "r");
		MessageDigest hashSum = MessageDigest.getInstance("SHA-256");
		byte[] buffer = new byte[buff];
		byte[] partialHash = null;
		long read = 0;
		// calculate the hash of the hole file for the test
		long offset = file.length();
		int unitsize;
		while (read < offset) {
			unitsize = (int) (((offset - read) >= buff) ? buff : (offset - read));
			file.read(buffer, 0, unitsize);
			hashSum.update(buffer, 0, unitsize);
			read += unitsize;
		}
		file.close();
		partialHash = new byte[hashSum.getDigestLength()];
		partialHash = hashSum.digest();
		//return new BigInteger(1, partialHash).toString(16);
		return partialHash;
	}


}
