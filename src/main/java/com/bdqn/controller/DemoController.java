package com.bdqn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/3/5/005.
 */
@Controller
@RequestMapping("demo")
public class DemoController {

    @RequestMapping("dataGrideDemo")
    public String toDemo(){
        return "demo";
    }

    @ResponseBody
    @RequestMapping("/test")
    public String toTest(Integer userId,String token,Integer number){
        System.out.println(userId);
        System.out.println(token);
        System.out.println(number);
        return "test";
    }
}
