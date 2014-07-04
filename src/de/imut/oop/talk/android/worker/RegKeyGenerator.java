package de.imut.oop.talk.android.worker;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * @author: Benedikt Buchner 7001697, benebuchner@gmail.com
 * @version: 2.0, 26/04/14
 * @version: 3.0, 29/05/14
 */
public class RegKeyGenerator {

	public RegKeyGenerator() {
	}

	public static synchronized String generate() {
		UUID randomUUID = UUID.randomUUID();
		Date date = new Date();
		Timestamp timeStamp = new Timestamp(date.getTime());
		String timeStampString = timeStamp.toString().replaceAll(" ", "");
		String rawIDString = randomUUID.toString() + timeStampString;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(rawIDString.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] digest = md.digest();
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i < digest.length; i++) {
			sBuilder.append(Integer.toHexString(0xFF & digest[i]));
		}
		return sBuilder.toString();
	}
}
