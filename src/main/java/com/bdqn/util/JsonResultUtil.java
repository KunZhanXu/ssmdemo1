package com.bdqn.util;

import com.bdqn.entity.JsonResult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonResultUtil {
    public static JsonResult toJsonString(Integer code, String message, Object obj){

        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(code);
        jsonResult.setMessage(message);
        jsonResult.setData(obj);
        return jsonResult;

    }
    public static JsonResult toJsonString(Integer code, String message){

        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(code);
        jsonResult.setMessage(message);
        return jsonResult;

    }

    public static JsonResult returnMessage(Integer n){
        if (n > 0 ){
            return toJsonString(200,"成功");
        }

        return toJsonString(201,"失败");
    }

    public static JsonResult returnMessageDate(Object o){
        if (o != null ){
            return toJsonString(200,"成功",o);
        }

        return toJsonString(201,"失败");
    }

    public static boolean returnNullError(Integer... n){
        int sum = 0;
        for (int i = 0; i <  n.length; i++) {
//            System.out.println(n[i]);
            if (n[i] == null  ){
                return false;
            }
        }
        return true;
    }

    public static boolean returnNullError(String... n){
        int sum = 0;
        for (int i = 0; i <  n.length; i++) {
//            System.out.println(n[i]);
            if (n[i] == null  ){
                return false;
            }
        }
        return true;
    }

    public static String DateToString(Date date){
        return DateFormat.getDateInstance().format(date);
    }

    public static Date stringToDate(String time) {
        if ( time == null || time == ""){
            return null;
        }
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");//日期格式
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
        System.out.println(stringToDate("2010/1/1"));
    }




}
