package com.mockuai.dts.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luliang on 15/7/23.
 */
public class TimeUtil {

    public static String formatTimeStamp(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String dateString = formatter.format(date);
        return dateString;
    }
}
