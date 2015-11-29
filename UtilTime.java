package sa.net.ibtikar.octboot.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by mhemdan on 11/29/15.
 */
public class UtilTime {


    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    /**
     * contains mostly all date formates
     */
    private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
        put("^\\d{8}$", "yyyyMMdd");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        put("^\\d{12}$", "yyyyMMddHHmm");
        put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
        put("^\\d{14}$", "yyyyMMddHHmmss");
        put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}t\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd'T'HH:mm:ss");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
    }};

    /**
     * Determine SimpleDateFormat pattern matching with the given date string. Returns null if
     * format is unknown. You can simply extend DateUtil with more formats if needed.
     * @param dateString The date string to determine the SimpleDateFormat pattern for.
     * @return The matching SimpleDateFormat pattern, or null if format is unknown.
     * @see SimpleDateFormat
     */
    public static String determineDateFormat(String dateString) {
        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                return DATE_FORMAT_REGEXPS.get(regexp);
            }
        }
        return null; // Unknown format.
    }

    /**
     * this method to get Date Object from a date string
     * @param dateString : the input for date String
     * @return Date object after the string conversion
     */
    public static Date getDateObjectFromDateString(String dateString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(determineDateFormat(dateString));
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    };

    /**
     * this method to return calendar object from date String and it used the method of getDateObjectFromDateString
     * @param dateString : the date string which will be converted to Calendar object
     * @return calendar object after the string conversion
     */
    public static Calendar getCalendarObjectFromDateString(String dateString){
        Date dateObject = getDateObjectFromDateString(dateString);
        Calendar calendarObject = Calendar.getInstance();
        calendarObject.setTime(dateObject);
        return calendarObject;
    }

    public static int getHours(Object object){
        Calendar calendar = Calendar.getInstance();

        if(object instanceof Date){
            Date dateObject = (Date) object;
            calendar.setTime(dateObject);
        }else if(object instanceof Calendar){
            calendar = (Calendar) object;
        }else if(object instanceof String){
            calendar = getCalendarObjectFromDateString((String) object);
        }
        return calendar.get(Calendar.HOUR);
    }

    public static int getMinutes(Object object){
        Calendar calendar = Calendar.getInstance();

        if(object instanceof Date){
            Date dateObject = (Date) object;
            calendar.setTime(dateObject);
        }else if(object instanceof Calendar){
            calendar = (Calendar) object;
        }else if(object instanceof String){
            calendar = getCalendarObjectFromDateString((String)object);
        }
        return calendar.get(Calendar.MINUTE);
    }


    /**
     * this method to return time ago which is mostly used in notification center
     * @param time : millisecond value of time of the notification
     * @return
     */
    public static long convertToMilli(long time){
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }
        return time;
    }

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }
//        time = convertToLocalTime(time);

        long now = getCurrentTime();
//        if (time > now || time <= 0) {
//            return null;
//        }

        // TODO: localize

        long diff = now - time;
        if(diff<0){
            diff = time-now;
        }
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }
    private static long getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }
    public static Calendar convertTimeToGMT(Calendar calendar){
        DateFormat gmtFormat = new SimpleDateFormat();
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        gmtFormat.setTimeZone(gmtTime);
        try {
            Log.d("time in GMT", gmtFormat.format(calendar.getTime()));
            calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
            calendar.setTime(gmtFormat.parse(gmtFormat.format(calendar.getTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }
}
