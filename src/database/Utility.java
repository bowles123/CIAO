package database;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * Parses strings into Java calendar objects.
 */

public class Utility {
	
	private static final Pattern SPLITTER = Pattern.compile("[-:T+Z]");
	
	public static Calendar parseDate(String str) {
		Calendar c = new GregorianCalendar();
		String[] split = SPLITTER.split(str);
		int seconds = 0, minutes = 0, hours = 12, day = 1, month = 1, year = 1900;
		switch (split.length) {
			case 8:
				// time zone - minutes
			case 7:
				// time zone - hours
			case 6:
				seconds = Integer.valueOf(split[5]);
			case 5:
				minutes = Integer.valueOf(split[4]);
			case 4:
				hours = Integer.valueOf(split[3]);
			case 3:
				day = Integer.valueOf(split[2]);
			case 2:
				month = Integer.valueOf(split[1]) - 1;
			case 1:
				year = Integer.valueOf(split[0]);
				break;
		}
		c.set(year, month, day, hours, minutes, seconds);
		return c;
	}
}
