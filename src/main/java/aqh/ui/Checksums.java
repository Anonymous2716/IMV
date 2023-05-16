package aqh.ui;

import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Checksums {
    public static String getSHA1Checksum(Path path) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(Files.readAllBytes(path));
        } catch (NoSuchAlgorithmException nsae) {
            LogIt.getInstance().logWriter("ERROR", "SHA-1 NoSuchAlgorithException " + nsae.getMessage());
            OptionPanes.errorPane("SHA-1 NoSuchAlgorithException " + nsae.getMessage(), "Cryptographic Error");
        } catch (IOException e) {
            LogIt.getInstance().logWriter("ERROR", "IOE At File For Checksum. " + e.getMessage());
            OptionPanes.errorPane("IOE At File For Checksum. " + e.getMessage(), "Checksum File Accessing Error");
        }
        byte[] digest = md.digest();
        return bytesToHex(digest);
    }

    // Convert byte array to hexadecimal string
    private static String bytesToHex(byte[] bytes) {
	StringBuilder sb = new StringBuilder();
	for (byte b : bytes) {
    	    sb.append(String.format("%02x", b));
    	}
    	return sb.toString();
    }
}
