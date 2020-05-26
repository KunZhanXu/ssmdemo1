package com.bdqn.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成id(根据时间来生成,用于首保单里面的gd_id)
 *
 * @author wjy
 */
public class GeneratID {
    /**
     * 根据传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     *
     * @param sformat yyyyMMddhhmmss
     * @return
     */
    public static String getDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getRandomNum(int num) {
        String numStr = "";
        for (int i = 0; i < num; i++) {
            numStr += (int) (10 * (Math.random()));
        }
        return numStr;
    }

    /**
     * 生成id
     *
     * @return
     */
    public static String getGeneratID() {
        String sformat = "YYMM";
        int num = 3;
        String id = getDate(sformat) + getRandomNum(num);
        return id;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            System.out.println(getGeneratID());
        }
    }

}