package com.bdqn.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于前端获取钉钉用户信息
 */
@RequestMapping("/dingdinguser")
@Controller
public class DDUserController {


    @RequestMapping("/get")
    @ResponseBody
    public Map<String,String> getUserMobileAndName(@RequestParam("accessToken")String accessToken,@RequestParam("code")String code) throws ApiException {
        Map<String, String> result = get(accessToken, code);
        return result;
    }


    /**
     *  获取用户信息
     */
    public Map<String,String> get(String accessToken, String code) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest req = new OapiUserGetRequest();
        String userid = getUserInfo(accessToken, code);
        req.setUserid(userid);
        req.setHttpMethod("GET");
        OapiUserGetResponse rsp = client.execute(req, accessToken);
        Map<String,String> resultMap = new HashMap();
        resultMap.put("name",rsp.getName());
        resultMap.put("mobile",rsp.getMobile());
        return resultMap;
    }


    /**
     *  获取用户的Userid
     */
    public String getUserInfo(String accessToken, String code) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
        OapiUserGetuserinfoRequest req = new OapiUserGetuserinfoRequest();
        req.setHttpMethod("GET");
        req.setCode(code);
        OapiUserGetuserinfoResponse rsp = client.execute(req, accessToken);
        return rsp.getUserid();
    }


    public static void main(String[] args) {
        DDUserController ddUserController = new DDUserController();
        String result=null;
        try {
            result = ddUserController.getUserInfo("c7c59b07308f347c80025ee80fa1b881","adc43b3302d7328fb2f00b4c8c0c1f2e");
        }catch (ApiException e){
            e.printStackTrace();
        }

        System.out.println(result);
    }

}
