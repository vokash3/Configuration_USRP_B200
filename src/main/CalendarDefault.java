package main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarDefault {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    public static SimpleDateFormat dateFormatWithoutMins = new SimpleDateFormat("yyyy.MM.dd");
    public static Calendar calendar = new GregorianCalendar();
}
