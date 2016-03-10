package overcast.pgm.util;

public class TimeUtil {

	// parse time
	// 5m = Five minutes (FINAL RESULT == 5:00)
	public static int parseTime(String text) {
		System.out.println(text);
		int length = text.length();
		int start;
		if (length != 0) {
			for (Period period : Period.values()) {
				if (text.endsWith(period.name().toLowerCase())) {
					start = period == Period.mo ? length - 2 : length - 1;
					int end = period == Period.mo ? start + 2 : start + 1;
					String sub = text.substring(start, end);
					String how = text.substring(0, 0 + start);
					System.out.println(how);
					int amount = Integer.parseInt(how);
					int time = Period.getTime(sub);
					return amount * time;
				}
			}
		}

		return 0;
	}

	public static String formatIntoHHMMSS(int secsIn) {

		int hours = secsIn / 3600, remainder = secsIn % 3600, minutes = remainder / 60, seconds = remainder % 60;

		return ((hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":"
				+ (seconds < 10 ? "0" : "") + seconds);

	}
}

enum Period {

	s(), m(), h(), d(), mo(), y();

	Period() {
	}

	public static int getTime(String input) {
		for (Period p : values()) {
			if (input != null) {
				if (input.equalsIgnoreCase(p.name())) {
					int time = time(p);
					return time;
				}
			}
		}
		return 0;
	}

	public static int time(Period p) {
		switch (p) {
		case h:
			return 3600;
		case m:
			return 60;
		case s:
			return 1;
		default:
			return 0;
		}
	}
}
