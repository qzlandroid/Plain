package com.example.jit.plain.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  @author max
 */

public class DateUtils {
    private SimpleDateFormat sf = null;
    private SimpleDateFormat sdf = null;
    private static final long MINUTE_SECONDS = 60; //1分钟多少秒

    private static final long HOUR_SECONDS = MINUTE_SECONDS*60;

    private static final long DAY_SECONDS = HOUR_SECONDS*24;

    private static final long YEAR_SECONDS = DAY_SECONDS*365;



    public String getPassedTime(long nowMilliseconds, long oldMilliseconds) {

        long passed = (nowMilliseconds-oldMilliseconds) /1000;//转为秒

        if (passed > YEAR_SECONDS) {

            return passed/YEAR_SECONDS+"年";

        } else if (passed > DAY_SECONDS) {

            return passed/DAY_SECONDS+"天";

        } else if (passed > HOUR_SECONDS) {

            return passed/HOUR_SECONDS+"小时";

        } else if (passed > MINUTE_SECONDS) {

            return passed/MINUTE_SECONDS+"分钟";

        } else {

            return passed+"秒";

        }

    }
    /*获取系统时间 格式为："yyyy/MM/dd "*/

    public String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /*时间戳转换成字符窜*/
    public String getDateToString(long time) {
        if (time == 0) {
            return null;
        }
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        result = format.format(new Date(time));
        return result;

    }

    /*将字符串转为时间戳*/

    public long getStringToDate(String time) {
        sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
}
