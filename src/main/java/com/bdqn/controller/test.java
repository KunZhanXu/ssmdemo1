package com.bdqn.controller;

import org.junit.Test;
import sun.text.resources.FormatData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class test {

    public String name;

    public  test(){
        name = "曹操";
        System.out.println(name);
    }

    public void test(){
        name = "曹操";
        System.out.println(name);
    }
    void doSomething(){
        System.out.println("i am father");
    }
    public static void main(String[] args) {
        /*Integer i = null;
        String s = null;
        if (s==""){
            System.out.println("孔子付出n");
        }
        if(s==null){
            System.out.println("空字符串");
        }
        if(0!=i){
            System.out.println("1");
        }
        if (i==null){
            System.out.println("2");
        }*/
        /*Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd");
        String format = sdf.format(date);
        System.out.println(format);*/
        test test = new test();
        test.test();
        test.doSomething();
    }
}
