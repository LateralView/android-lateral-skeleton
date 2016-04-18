package co.lateralview.androidskeleton.ui.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtils
{
	public enum DateOrderType
	{
		BEFORE,
		AFTER,
		EQUALS
	}

	public final static long SECOND = 1;
	public final static long MINUTE = SECOND * 60;
	public final static long HOUR = MINUTE * 60;
	public final static long DAY = HOUR * 24;
	public final static long MONTH = DAY * 30;
	public final static long YEAR = DAY * 365;
	public final static long DEFAULT_NOW_CRITERIA = MINUTE * 5;

	public final static String YYYY_MM_DD = "yyyy-MM-dd";
	public final static String YYYY_MM_DD_SLASH = "yyyy/MM/dd";
	public final static String MM_DD_YY_SLASH = "MM/dd/yy";
	public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";

	public static final String MONTH_FORMAT_FULL = "MMMM";
	public static final String DAY_FORMAT_FULL = "EEEE";

	public static Date getDateFromDateFormat(String dateString, String dateFormat)
	{
		DateFormat formatter = new SimpleDateFormat(dateFormat);
		Date date = null;
		try
		{
			formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			date = formatter.parse(dateString);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}

		return date;
	}

	public static Date getTimeFromTimeFormat(String timeString)
	{
		DateFormat formatter = new SimpleDateFormat("mm:ss");
		Date date = null;
		try
		{
			formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			date = formatter.parse(timeString);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}

		return date;
	}

	public static Date getCurrentDate()
	{
		return new Date();
	}

	public static long getUnixTimestampFromTimeFormat(String timeString)
	{
		return getTimeFromTimeFormat(timeString).getTime() / 1000L;
	}

	public static long getUnixTimestampFromDateFormat(String dateString, String dateFormat)
	{
		return getDateFromDateFormat(dateString, dateFormat).getTime() / 1000L;
	}

	public static long getUnixTimestampFromDateFormat(String dateString)
	{
		return getUnixTimestampFromDateFormat(dateString, YYYY_MM_DD_HH_MM_SS);
	}

	public static long nowUnixTimestamp()
	{
		return (int) (System.currentTimeMillis() / 1000L);
	}

	public static String aproxTimeStringSince(Date since)
	{
		long time = Math.abs(nowUnixTimestamp() - (since.getTime() / 1000L));

		if (time < MINUTE)
		{
			return String.valueOf(time) + " seg"; //TODO: PASAR A STRING VALUES
		} else if (time < HOUR)
		{
			return String.valueOf(time / MINUTE) + " min";
		} else if (time < DAY)
		{
			return String.valueOf(time / HOUR) + " h";
		} else if (time < MONTH)
		{
			return String.valueOf(time / DAY) + " días";
		} else if (time < YEAR)
		{
			return String.valueOf(time / MONTH) + " meses";
		} else
		{
			return String.valueOf(time / YEAR) + " años";
		}
	}

	public static DateOrderType getDateOrderType(String dateFormat1, String dateFormat2)
	{
		long unixTimestamp1 = getUnixTimestampFromDateFormat(dateFormat1);
		long unixTimestamp2 = getUnixTimestampFromDateFormat(dateFormat2);

		if (unixTimestamp1 > unixTimestamp2)
		{
			return DateOrderType.AFTER;
		}

		if (unixTimestamp1 < unixTimestamp2)
		{
			return DateOrderType.BEFORE;
		}

		return DateOrderType.EQUALS;
	}

	public static long getRemainingHoursFromMillis(long millis)
	{
		return TimeUnit.MILLISECONDS.toHours(millis);
	}

	public static long getRemainingMinutesFromMillis(long millis)
	{
		return TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1);
	}

	public static long getRemainingSecondsFromMillis(long millis)
	{
		return TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1);
	}

	public static String getRemainingHoursStringFromMillis(long millis)
	{
		return formatTimeString(String.valueOf(getRemainingHoursFromMillis(millis)));
	}

	public static String getRemainingMinutesStringFromMillis(long millis)
	{
		return formatTimeString(String.valueOf(getRemainingMinutesFromMillis(millis)));
	}

	public static String getRemainingSecondsStringFromMillis(long millis)
	{
		return formatTimeString(String.valueOf(getRemainingSecondsFromMillis(millis)));
	}

	public static String formatTimeString(String time)
	{
		if (time.length() < 2)
		{
			time = "0" + time;
		}

		return time;
	}

	public static boolean isDateValid(String dateToValidate, String dateFromat)
	{

		if (dateToValidate == null)
		{
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);

		try
		{

			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);

		} catch (ParseException e)
		{

			e.printStackTrace();
			return false;
		}

		return true;
	}


	public static int getDiffYears(Date first, Date last)
	{
		Calendar a = getCalendar(first);
		Calendar b = getCalendar(last);
		int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
		if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
				(a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE)))
		{
			diff--;
		}

		return diff;
	}

	public static Calendar getCalendar(Date date)
	{
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		return cal;
	}

	public static String formatUnixtimestamp(int unixtimestamp, String format)
	{
		Date date = new Date(unixtimestamp * 1000L); // *1000 is to convert seconds to milliseconds

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

		return sdf.format(date);
	}

	public static String getWeekOfYear(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
	}

	public static String getMonthName(Date date)
	{
		return new SimpleDateFormat(MONTH_FORMAT_FULL, Locale.ENGLISH).format(date);
	}

	public static String getDay(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return String.valueOf(calendar.get(Calendar.DATE));
	}

	public static String getDayOfWeek(Date date)
	{
		return new SimpleDateFormat(DAY_FORMAT_FULL, Locale.ENGLISH).format(date);
	}

	public static String getYear(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return String.valueOf(calendar.get(Calendar.YEAR));
	}

	public static String getDayOfMonthSuffix(final int n)
	{
		if (n >= 11 && n <= 13)
		{
			return "th";
		}
		switch (n % 10)
		{
			case 1:
				return "st";
			case 2:
				return "nd";
			case 3:
				return "rd";
			default:
				return "th";
		}
	}
}
