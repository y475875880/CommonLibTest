package cn.sighttechnology.armscomponent.commonres.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 * Data：2018/6/8-17:54
 * Author: yangjichao
 */
public class HouseUtils {

    //根据 5-3-1 返回 5室3厅1室
    public static String getRoomType(String roomType) {
        StringBuffer roomBuffer = new StringBuffer();
        if (roomType != null && roomType.length() > 0) {
            String[] type = roomType.split("-");
            for (int i = 0; i < type.length; i++) {
                if (i == 0) {
                    roomBuffer.append(type[i]).append("室");
                } else if (i == 1) {
                    roomBuffer.append(type[i]).append("厅");
                } else if (i == 2) {
                    roomBuffer.append(type[i]).append("卫");
                }
            }
        }
        return roomBuffer.toString();
    }

    public static String getFormatTime(String time) {
        if (time != null && time.length() > 0) {
            Date date = new Date(Long.valueOf(time));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String strTime = sdf.format(date);
            return strTime;
        } else {
            return "";
        }
    }

    public static String getFormatTimeYMD(String time) {
        time = time.substring(0, 10);
        time = time.replace("T", " ");
        return time;
    }

    public static String getSquarePrice(Double square, Double price) {
        double fPriceSquare = price / square * 10000;
//        DecimalFormat format = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String priceSquare = String.valueOf(Math.round(fPriceSquare));//format 返回的是字符串
        return priceSquare;
    }

    public static LatLng getFormatLatLng(String strPostion) {
        double lat = 0.0;
        double lng = 0.0;
        if (strPostion != null && strPostion.length() > 0) {
            String[] strs = strPostion.split(",");
            lat = Double.valueOf(strs[1]);
            lng = Double.valueOf(strs[0]);
            return new LatLng(lat, lng);
        } else {
            return null;
        }
    }

}
