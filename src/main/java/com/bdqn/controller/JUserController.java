package com.bdqn.controller;

import com.bdqn.entity.JUser;
import com.bdqn.entity.JsonResult;
import com.bdqn.service.JUserService;
import com.bdqn.util.JWTUtil;
import com.bdqn.util.JsonResultUtil;
import com.bdqn.util.PasswordUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class JUserController {

    @Resource
    private JUserService jUserService;

    @ResponseBody
    @RequestMapping(value = "/login",produces = {"application/json;charset=UTF-8"})
    public JsonResult login(String username, String password){

        if (username == null || username.isEmpty() || password == null || password.isEmpty() ) {
            return JsonResultUtil.toJsonString(201, "账号密码不能为空");
        }

        JUser jUser = new JUser();
        jUser.setUsername(username);
        jUser.setPassword(PasswordUtil.EncoderByMd5(password));
        jUser = this.jUserService.userLogin(jUser);

        if (jUser != null){
            String sign = JWTUtil.sign(jUser,72000);
            Map map = new HashMap();
            map.put("token",sign);
            map.put("userId",jUser.getId());
            map.put("username",jUser.getRealname());
            return JsonResultUtil.toJsonString(200,"查询成功",map);

        }

        return JsonResultUtil.toJsonString(201,"没有该用户");
    }

    public static void main(String[] args) throws UnknownHostException {
//        System.out.println(InetAddress.getLoopbackAddress());
//        System.out.println(InetAddress.getLocalHost().getHostAddress());
//
//        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//
//        Date date = null;
//        String time  = "2019/10/15";
//
//        try {
//            date = format.parse(time);
//            System.out.println(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        String s = null;

        s.isEmpty();


    }





}
