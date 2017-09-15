package biz.diginov.isitravel.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.DAY_OF_MONTH;

/**
 * Created by Forntoh on 15-Sep-17.
 */

public class DateUtils {
    public static String getDayOfWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }

    public static String getDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(DAY_OF_MONTH));
    }

    public static String getMonthOfYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }

    public static String getYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }
}
