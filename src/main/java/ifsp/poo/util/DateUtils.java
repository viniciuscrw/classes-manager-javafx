package ifsp.poo.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String getCurrentPeriod() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int semester = calendar.get(Calendar.MONTH) < 7 ? 1 : 2;
        return String.format("%d/0%d", year, semester);
    }
}
