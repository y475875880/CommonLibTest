package cn.sighttechnology.armscomponent.commonsdk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
//        return days + " days " + hours + " hours " + minutes + " minutes "
//                + seconds + " seconds ";
        return days + "天" + hours + "小时";
    }

    //输入 2018-06-16 14:02:32 输入 2018/06/16
    public  static String getFormatSlashTimeString(String strTime){
        if (strTime == null || strTime.length() < 10){
            return "";
        }
        String timeOut = "";
        timeOut =   strTime.substring(0, 10);
        timeOut = timeOut.replace("-",    "/");

        return timeOut;
    }

    //输入 2018-06-16 14:02:32 输入 2018/06/16
    public  static String getFormatTimeLong(Long timeLong){
        Date    date    =   new Date(timeLong);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 2000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
