package com.foursquare.takehome;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by admin on 12/15/16.
 */

public class Util {
    /**
     * Convert Unit time in Sec to h:mm a
     */
    public static String getFormattedTime(long unixSec) {
        Date date = new Date(unixSec * 1000L); // convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String formattedTime = sdf.format(date);
        return formattedTime;
    }
}
