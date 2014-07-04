package de.imut.oop.talk.android.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Benedikt Buchner, benebuchner@gmail.com, 7001697
 * @version 1.0
 */
public class AndroidCommon {
	
	public static String CONTEXT_FILE = "talk_context";
	public static String NICK_NAME = "nick_name";
	public static String ID = "id";
	public static String REGISTRATION_KEY = "registration_key";
	
	private static String SERVER_NICK_DELIMITER_SUFFIX = "]";
	private static String SERVER_NICK_DELIMITER_PREFIX = "[";
	private static String CLIENT_NICK_DELIMITER = "%talk_android%";
	
	public AndroidCommon() {
		
	}
	
	public static Map<String, String> parseUsernameMessage(String stringToParse) {
		
		Map<String, String> userNameMessage = new HashMap<String, String>();
		if (stringToParse.startsWith(SERVER_NICK_DELIMITER_PREFIX)) {
			String tempString = stringToParse.replaceFirst(SERVER_NICK_DELIMITER_SUFFIX, CLIENT_NICK_DELIMITER);
			String[] splitString = tempString.split(CLIENT_NICK_DELIMITER);
			
			String rawUserName = splitString[0];
			String rawParsedMessage = tempString.replace(rawUserName, "");
			String parsedMessage = rawParsedMessage.replaceAll(CLIENT_NICK_DELIMITER, "");
			
			StringBuilder nickBuilder = new StringBuilder();
			for (int i = 1; i < rawUserName.length(); i++) {
				nickBuilder.append(rawUserName.charAt(i));
			}
			
			userNameMessage.put("nick", nickBuilder.toString());
			userNameMessage.put("message", parsedMessage);
			return userNameMessage;
		}
		userNameMessage.put("nick", "");
		userNameMessage.put("message", stringToParse);
		return userNameMessage;
	}
}
