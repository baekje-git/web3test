package ever.support.date;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
	private static final String defaultFormat = "yyyy-MM-dd";

	public static String getBeforeAfter(String chkdate, int cnt) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Integer.parseInt(chkdate.substring(0, 4)), Integer.parseInt(chkdate.substring(4, 6)) - 1,
				Integer.parseInt(chkdate.substring(6, 8)));
		cal.add(5, cnt);
		Date dateTo = cal.getTime();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		return fmt.format(dateTo);
	}

	public static boolean isValidDate(String p_date) {
		String dateStr = p_date;
		if (dateStr == null)
			return false;
		dateStr.indexOf("-");
		dateStr.indexOf("/");
		if (dateStr.length() != 8)
			return false;
		int yyyy = Integer.parseInt(p_date.substring(0, 4));
		int mm = Integer.parseInt(p_date.substring(4, 6));
		if (mm < 1 || mm > 12)
			return false;
		int dd = Integer.parseInt(p_date.substring(6, 8));
		int[] lastDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int lastDay = 0;
		if (mm == 2) {
			if ((yyyy % 4 == 0 && yyyy % 100 != 0) || yyyy % 400 == 0) {
				lastDay = 29;
			} else {
				lastDay = 28;
			}
		} else {
			lastDay = lastDays[mm - 1];
		}
		if (dd < 1 || dd > lastDay)
			return false;
		return true;
	}

	public static boolean isValidTime(String p_time) {
		String timeStr = p_time;
		if (timeStr == null)
			return false;
		timeStr.indexOf(":");
		if (timeStr.length() != 4)
			return false;
		int hh = Integer.parseInt(timeStr.substring(0, 2));
		int mm = Integer.parseInt(timeStr.substring(2, 4));
		if (hh > 23 || hh < 0)
			return false;
		if (mm > 59 || mm < 0)
			return false;
		return true;
	}

	public static boolean chkDateFormat(String yyyymmdd) {
		return isValidDate(yyyymmdd);
	}

	public static Calendar getCalendar() {
		Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+09:00"), Locale.KOREA);
		calendar.setTime(new Date());
		return calendar;
	}

	public static String getDate() {
		return getDate(Calendar.getInstance().getTime(), "yyyy-MM-dd");
	}

	public static String getDate(String p_format) {
		return getDate(Calendar.getInstance().getTime(), p_format);
	}

	public static String getDate(Date p_date, String p_format) {
		p_format = p_format.toLowerCase();
		boolean lb_week = false;
		if (p_format.toUpperCase().indexOf("WEEK") != -1) {
			p_format = p_format.substring(0, p_format.toUpperCase().indexOf("WEEK"));
			lb_week = true;
		}
		int i = p_format.indexOf("hh24");
		if (i != -1)
			p_format = String.valueOf(p_format.substring(0, i)) + "HH" + p_format.substring(i + 4);
		i = p_format.indexOf("mm");
		if (i != -1)
			p_format = String.valueOf(p_format.substring(0, i)) + "MM" + p_format.substring(i + 2);
		i = p_format.indexOf("mi");
		if (i != -1)
			p_format = String.valueOf(p_format.substring(0, i)) + "mm" + p_format.substring(i + 2);
		if (lb_week)
			return (new SimpleDateFormat(p_format)).format(p_date);
		return (new SimpleDateFormat(p_format)).format(p_date);
	}

	public static String getDate(Date p_date, String p_format, int p_week) {
		p_format = p_format.toLowerCase();
		if (p_format.toUpperCase().indexOf("WEEK") != -1)
			p_format = p_format.substring(0, p_format.toUpperCase().indexOf("WEEK"));
		int i = p_format.indexOf("hh24");
		if (i != -1)
			p_format = String.valueOf(p_format.substring(0, i)) + "HH" + p_format.substring(i + 4);
		i = p_format.indexOf("mm");
		if (i != -1)
			p_format = String.valueOf(p_format.substring(0, i)) + "MM" + p_format.substring(i + 2);
		i = p_format.indexOf("mi");
		if (i != -1)
			p_format = String.valueOf(p_format.substring(0, i)) + "mm" + p_format.substring(i + 2);
		String ls_week = null;
		switch (p_week) {
		case 1:
			ls_week = "(일)";
			return String.valueOf((new SimpleDateFormat(p_format)).format(p_date)) + ls_week;
		case 2:
			ls_week = "(월)";
			return String.valueOf((new SimpleDateFormat(p_format)).format(p_date)) + ls_week;
		case 3:
			ls_week = "(화)";
			return String.valueOf((new SimpleDateFormat(p_format)).format(p_date)) + ls_week;
		case 4:
			ls_week = "(수)";
			return String.valueOf((new SimpleDateFormat(p_format)).format(p_date)) + ls_week;
		case 5:
			ls_week = "(목)";
			return String.valueOf((new SimpleDateFormat(p_format)).format(p_date)) + ls_week;
		case 6:
			ls_week = "(금)";
			return String.valueOf((new SimpleDateFormat(p_format)).format(p_date)) + ls_week;
		case 7:
			ls_week = "(토)";
			return String.valueOf((new SimpleDateFormat(p_format)).format(p_date)) + ls_week;
		}
		ls_week = "";
		return String.valueOf((new SimpleDateFormat(p_format)).format(p_date)) + ls_week;
	}

	public static String getDate(String p_date, String p_format) {
		Calendar calendar = Calendar.getInstance();
		if (p_date == null)
			return "";
		if (p_date.length() == 8) {
			calendar.set(Integer.parseInt(p_date.substring(0, 4)), Integer.parseInt(p_date.substring(4, 6)) - 1,
					Integer.parseInt(p_date.substring(6)));
		} else if (p_date.length() == 14) {
			calendar.set(Integer.parseInt(p_date.substring(0, 4)), Integer.parseInt(p_date.substring(4, 6)) - 1,
					Integer.parseInt(p_date.substring(6, 8)), Integer.parseInt(p_date.substring(8, 10)),
					Integer.parseInt(p_date.substring(10, 12)), Integer.parseInt(p_date.substring(12)));
		} else if (p_date.length() == 6) {
			p_date = String.valueOf(getDate("YYYYMMDD")) + p_date;
			calendar.set(Integer.parseInt(p_date.substring(0, 4)), Integer.parseInt(p_date.substring(4, 6)) - 1,
					Integer.parseInt(p_date.substring(6, 8)), Integer.parseInt(p_date.substring(8, 10)),
					Integer.parseInt(p_date.substring(10, 12)), Integer.parseInt(p_date.substring(12)));
		} else {
			return p_date;
		}
		return getDate(calendar.getTime(), p_format);
	}

	public static String getDate(String p_format, char p_type, int p_length) {
		return getDate(getDate(Calendar.getInstance().getTime(), p_format), p_format, p_type, p_length);
	}

	public static String getDate(String p_date, String p_format, char p_type, int p_length) {
		Calendar calendar = Calendar.getInstance();
		if (p_date == null)
			return "";
		if (p_date.length() == 8) {
			calendar.set(Integer.parseInt(p_date.substring(0, 4)), Integer.parseInt(p_date.substring(4, 6)) - 1,
					Integer.parseInt(p_date.substring(6)));
		} else if (p_date.length() == 14) {
			calendar.set(Integer.parseInt(p_date.substring(0, 4)), Integer.parseInt(p_date.substring(4, 6)) - 1,
					Integer.parseInt(p_date.substring(6, 8)), Integer.parseInt(p_date.substring(8, 10)),
					Integer.parseInt(p_date.substring(10, 12)), Integer.parseInt(p_date.substring(12)));
		} else if (p_date.length() == 6) {
			p_date = String.valueOf(getDate("YYYYMMDD")) + p_date;
			calendar.set(Integer.parseInt(p_date.substring(0, 4)), Integer.parseInt(p_date.substring(4, 6)) - 1,
					Integer.parseInt(p_date.substring(6, 8)), Integer.parseInt(p_date.substring(8, 10)),
					Integer.parseInt(p_date.substring(10, 12)), Integer.parseInt(p_date.substring(12)));
		} else {
			return p_date;
		}
		switch (p_type) {
		case 'Y':
			calendar.add(1, p_length);
			break;
		case 'M':
			calendar.add(2, p_length);
			break;
		case 'D':
			calendar.add(5, p_length);
			break;
		case 'W':
			calendar.add(8, p_length);
			break;
		case 'H':
			calendar.add(10, p_length);
			break;
		}
		int li_week = -1;
		if (p_format.toUpperCase().indexOf("WEEK") != -1) {
			p_format = p_format.substring(0, p_format.toUpperCase().indexOf("WEEK"));
			li_week = calendar.get(7);
		}
		return getDate(calendar.getTime(), p_format, li_week);
	}

	public static Date string2Date(String p_date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(p_date, new ParsePosition(0));
	}

	public static int getDayBetween(String p_fromDate, String p_toDate) {
		return getDayBetween(string2Date(p_fromDate), string2Date(p_toDate));
	}

	public static int getDayBetween(Date p_fromDate, Date p_toDate) {
		Calendar fromCal = Calendar.getInstance();
		Calendar toCal = Calendar.getInstance();
		Calendar tmpCal = Calendar.getInstance();
		fromCal.setTime(p_fromDate);
		toCal.setTime(p_toDate);
		int nFromYear = fromCal.get(1);
		int nToYear = toCal.get(1);
		int nFromDate = fromCal.get(6);
		int nToDate = toCal.get(6);
		int nCheckDate = 0;
		for (int i = nFromYear; i < nToYear; i++) {
			tmpCal.set(i, 11, 31);
			nCheckDate += tmpCal.get(6);
		}
		return nCheckDate + nToDate - nFromDate;
	}

	/**
	 * 파라미터로 받은 연월이 몇일까지 있는지를 알아낸다.
	 *
	 * @param p_year  년도
	 * @param p_month 월
	 * @return 해당연월의 총 일수
	 */
	public static int getDayCountOfMonth(int p_year, int p_month) {
		int localDays;
		switch (p_month) {
		case 4:
		case 6:
		case 9:
		case 11:
			localDays = 30;
			break;
		case 2:
			if (isLeapYear(p_year)) {
				localDays = 29;
			} else {
				localDays = 28;
			}
			break;
		default:
			localDays = 31;
		}
		return localDays;
	}

	private static boolean isLeapYear(int p_year) {
		return (p_year % 4 == 0 && p_year != 1900);
	}

	public static String getSvcDate() {
		return (new SimpleDateFormat("yyyyMMddHHmmss")).format(Calendar.getInstance().getTime());
	}

	public static String format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String format(String format) {
		return format(new Date(), format);
	}

	public static Date parse(String s, String format) throws ParseException {
		if (s == null)
			throw new ParseException("date string to check is null", 0);
		if (format == null)
			throw new ParseException("format string to check date is null", 0);
		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
		Date date = null;
		try {
			date = formatter.parse(s);
		} catch (ParseException e) {
			throw new ParseException(" wrong date:\"" + s + "\" with format \"" + format + "\"", 0);
		}
		if (!format(date, format).equals(s))
			throw new ParseException("Out of bound date:\"" + s + "\" with format \"" + format + "\"", 0);
		return date;
	}

	public static boolean isValid(String s, String format) {
		try {
			parse(s, format);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public static int getDay(Date date) {
		return getNumber(date, "dd");
	}

	public static int getDay() {
		return getNumber(new Date(), "dd");
	}

	public static int getYear(Date date) {
		return getNumber(date, "yyyy");
	}

	public static int getYear() {
		return getNumber(new Date(), "yyyy");
	}

	public static int getMonth(Date date) {
		return getNumber(date, "MM");
	}

	public static int getMonth() {
		return getNumber(new Date(), "MM");
	}

	public static int getNumber(Date date, String format) {
		String dateString = format(date, format);
		return Integer.parseInt(dateString);
	}

	public static int getNumber() {
		return Integer.parseInt(format("yyyyMMdd"));
	}

	public static double getLongNumber() {
		return Double.parseDouble(format("yyyyMMddHHmmss"));
	}

	public static String getWeekOfYear() {
		Calendar cal = Calendar.getInstance();
		int week = cal.get(3);
		return Integer.toString(week);
	}

	public static int whichDay(String s, String format) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
		Date date = parse(s, format);
		Calendar calendar = formatter.getCalendar();
		calendar.setTime(date);
		return calendar.get(7);
	}

	public static String getDayOfTheWeek(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("E", Locale.KOREA);
		return sdf.format(date);
	}

	public static String getTodayOfTheWeek() {
		return getDayOfTheWeek(new Date());
	}

	public static String addDays(String s, int day, String format) throws ParseException {
		Date date = parse(s, format);
		int yyyy = Integer.parseInt(format(date, "yyyy"));
		int MM = Integer.parseInt(format(date, "MM"));
		int dd = Integer.parseInt(format(date, "dd"));
		Calendar c = new GregorianCalendar(yyyy, MM - 1, dd + day);
		return format(c.getTime(), format);
	}

	public static String addYears(String s, int year, String format) throws ParseException {
		Date date = parse(s, format);
		int yyyy = Integer.parseInt(format(date, "yyyy"));
		int MM = Integer.parseInt(format(date, "MM"));
		int dd = Integer.parseInt(format(date, "dd"));
		Calendar c = new GregorianCalendar(yyyy + year, MM - 1, dd);
		return format(c.getTime(), format);
	}

	public static long getDifferenceDate(Date from, Date to) {
		return from.getTime() - to.getTime();
	}

	public static int daysBetween(String from, String to, String format) throws ParseException {
		Date d1 = parse(from, format);
		Date d2 = parse(to, format);
		long duration = getDifferenceDate(d1, d2);
		return (int) (duration / 86400000L);
	}

	public static int ageBetween(String from, String to, String format) throws ParseException {
		return daysBetween(from, to, format) / 365;
	}

	public static int monthsBetween(String from, String to, String format) throws ParseException {
		return daysBetween(from, to, format) / 30;
	}

	public static int getFirstDay(String date, String format) throws ParseException {
		Date dt = parse(date, format);
		int yyyy = Integer.parseInt(format(dt, "yyyy"));
		int MM = Integer.parseInt(format(dt, "MM"));
		int dd = Integer.parseInt(format(dt, "dd"));
		Calendar c = new GregorianCalendar(yyyy, MM - 1, dd);
		return c.getActualMinimum(5);
	}

	public static int getLastDay(String date, String format) throws ParseException {
		Date dt = parse(date, format);
		int yyyy = Integer.parseInt(format(dt, "yyyy"));
		int MM = Integer.parseInt(format(dt, "MM"));
		int dd = Integer.parseInt(format(dt, "dd"));
		Calendar c = new GregorianCalendar(yyyy, MM - 1, dd);
		return c.getActualMaximum(5);
	}

	public static String getFirstDay(String date, String format, String reFormat) throws ParseException {
		Date dt = parse(date, format);
		int yyyy = Integer.parseInt(format(dt, "yyyy"));
		int MM = Integer.parseInt(format(dt, "MM"));
		int dd = Integer.parseInt(format(dt, "dd"));
		Calendar c = new GregorianCalendar(yyyy, MM - 1, dd);
		int minday = c.getActualMinimum(5);
		c.set(5, minday);
		return format(c.getTime(), reFormat);
	}

	public static String getLastDay(String date, String format, String reFormat) throws ParseException {
		Date dt = parse(date, format);
		int yyyy = Integer.parseInt(format(dt, "yyyy"));
		int MM = Integer.parseInt(format(dt, "MM"));
		int dd = Integer.parseInt(format(dt, "dd"));
		Calendar c = new GregorianCalendar(yyyy, MM - 1, dd);
		int maxday = c.getActualMaximum(5);
		c.set(5, maxday);
		return format(c.getTime(), reFormat);
	}

	public static String getShortDateString() {
		return format(new Date(), "yyyyMMdd");
	}

	public static String getShortYyyyMMString() {
		return format(new Date(), "yyyyMM");
	}

	public static String getShortTimeString() {
		return format(new Date(), "HHmmss");
	}

	public static Date parseDate(String value, String format) {
		Date date;
		try {
			date = parse(value, format);
		} catch (ParseException e) {
			date = new Date();
		}
		return date;
	}

	public static String getCurrDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sDate = formatter.format(new Date());
		return sDate;
	}

	public static String getCurrDate(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String sDate = formatter.format(new Date());
		return sDate;
	}

	public static Date parseDate(String value) {
		Date date = parseDate(value, "yyyy-MM-dd");
		return date;
	}

	public static String getDateString() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		return formatter.format(new Date());
	}

	public static int getNumberByPattern(String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.KOREA);
		String dateString = formatter.format(new Date());
		return Integer.parseInt(dateString);
	}

	public static String getStringByPattern(String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.KOREA);
		return formatter.format(new Date());
	}

	public static String getCurrentTimeToString() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		return formatter.format(new Date());
	}
}
