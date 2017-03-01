package com.mockuai.shopcenter.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luliang on 15/7/27.
 */
public class TimeUtil {

    public static final String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";

    public static String getFormatTime(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

}
