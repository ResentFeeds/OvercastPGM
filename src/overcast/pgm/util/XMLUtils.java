package overcast.pgm.util;

public class XMLUtils {

	public static boolean parseBoolean(String value) {
		switch (value) {
		case "on":
		case "yes":
		case "enabled":
		case "true":
			return true;

		case "off":
		case "no":
		case "disabled":
		case "false":
			return false;

		default:
			return false;
		}
	}
}