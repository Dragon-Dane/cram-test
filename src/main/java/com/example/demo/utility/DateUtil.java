package com.example.demo.utility;

import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateUtil {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final String DATE_TIME_FORMAT_NEW = "E MMM dd yyyy HH:mm:ss 'GMT'z";

    private final Calendar time;

    public DateUtil(Calendar time){
        this.time = time;
    }
    public static DateFormat getReadableDateForGraph() {
        return new SimpleDateFormat("dd MMM YY");
    }
    public static SimpleDateFormat getReadableDateForView() {
        return new SimpleDateFormat("dd MMM YY h:mm a");
    }
    public static DateFormat getReadableDateTime() {
        return new SimpleDateFormat("MMM dd, yyyy h:m a");
    }


    /**
     * Returns the current time. However, this behaviour can be changed by setting time via calling setCurrentTime() during testing
     * @return
     */
    public Calendar getCurrentTime() {
        if (time == null) {
            return  Calendar.getInstance();
        }
        return (Calendar) time.clone();
    }

    public Calendar getThisMonthStartDate() {
        Calendar date = getCurrentTime();
        date = getStartOfDay(date);
        date.set(Calendar.DAY_OF_MONTH, 1);
        return date;
    }


    public Calendar getThisMonthEndDate() {
        Calendar date = getCurrentTime();
        date.set(Calendar.DAY_OF_MONTH, 1);
        date.add(Calendar.MONTH, 1);
        date.add(Calendar.DAY_OF_YEAR, -1);
        date = getEndOfDay(date);
        return date;
    }

    public static String toDateString(Calendar calendar) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(calendar.getTime());
    }

    public static String toDateString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(date);
    }

    public static String toDateTimeString(Calendar calendar) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);
        return df.format(calendar.getTime());
    }

    public static String toDateTimeString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);
        return df.format(date.getTime());
    }

    public static Date toDateWithLocal(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_NEW, Locale.ENGLISH);
        return sdf.parse(date);
    }


    public static Date toDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        TimeZone timeZone = TimeZone.getDefault();
        timeZone.setRawOffset(-6);
        sdf.setTimeZone(timeZone);
        return sdf.parse(date);
    }

    public static Date toDateWithOutZone(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.parse(date);
    }


    public static Calendar toCalender(String date) throws ParseException {
        Calendar cal= Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        cal.setTime(sdf.parse(date));
        return cal;
    }

    public static Calendar toDateTime(String dateTime) throws ParseException {
        Calendar cal= Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        cal.setTime(sdf.parse(dateTime));
        return cal;
    }

    public static String toString(Calendar calendar, String format) {
        if (format == null)
            format = DATE_TIME_FORMAT;
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(calendar.getTime());
    }

    public static String toString(Date date, String format) {
        if (format == null)
            format = DATE_TIME_FORMAT;
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date.getTime());
    }

    public static boolean isValidDate(String dateString, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            df.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static Calendar getCalender(String dateString, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            Date date = df.parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        } catch (ParseException e) {
            return null;
        }
    }

    public static Calendar getEndOfDay(Calendar date) {
        date.setTime(DateUtils.addMilliseconds(DateUtils.ceiling(date.getTime(), Calendar.DATE), -1));
        return date;
    }

//    public static Date getEndOfDay(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        return getEndOfDay(calendar).getTime();
//    }

    public static Calendar getStartOfDay(Calendar date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }
//    public static Date getStartOfDay(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        return getStartOfDay(calendar).getTime();
//    }

    public static Date getStartDateOfYear(Date asOnDate) {
        return null;
    }

    public static Integer getAgeFromBirthDate(Date birthday) {

        if(birthday == null) return 0;
        Calendar calBirthdaty = Calendar.getInstance();
        calBirthdaty.setTime(birthday);
        Calendar today = Calendar.getInstance();
        return today.get(Calendar.YEAR) - calBirthdaty.get(Calendar.YEAR);
    }

    public static Date getBirthDateFromAge(Integer age) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - age);
        return cal.getTime();
    }
}
