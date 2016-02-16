package overcast.pgm.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

	static Logger logger = Logger.getLogger("minecraft");

	public static void log(Level level, String message) {
		logger.log(level, message);
	}

	public static void severe(String message) {
		logger.log(Level.SEVERE, message);
	}

	public static void warning(String message) {
		logger.log(Level.WARNING, message);
	}

	public static void info(String message) {
		logger.log(Level.INFO, message);
	}
}
