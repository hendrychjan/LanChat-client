package com.hendrychjan;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
    public static String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
    }
}
