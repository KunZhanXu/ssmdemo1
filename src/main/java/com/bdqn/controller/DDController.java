package com.bdqn.controller;

import com.bdqn.dao.JedisClient;
import com.bdqn.entity.JDDBind;
import com.bdqn.entity.JUser;
import com.bdqn.entity.JsonResult;
import com.bdqn.service.JDDBindService;
import com.bdqn.service.JUserService;
import com.bdqn.util.JWTUtil;
import com.bdqn.util.JsonResultUtil;
import com.bdqn.util.RandomUtil;
import com.bdqn.util.SMSUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DDController {

    private static final String URL = "http://crm.jointpoint.cn/register/login2.aspx";
    private static final String DINGDING_URL = "https://oapi.dingtalk.com";
    private static final String METHOD_GET = "GET";
    private static final String APP_ID = "dingoaap1m7ougydqr6kk9";
    private static final String APP_SECRET = "mSHGOOndyHXlYLmm-_gAcm4adx4pejVo7tO-cKGzSdeyBcfxuqHV_4OrJKu5ieSe";
    private static final String KEY_PREFIX="user:verify:";


    @Autowired
    private JDDBindService jddBindServiceImpl;

    @Autowired
    private JUserService jUserServiceImpl;

    @Autowired
    private JedisClient jedisClientPool;

    @RequestMapping("/DDlogin")
    public void login(HttpServletResponse response) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DINGDING_URL).append("/connect/qrconnect?appid=" + APP_ID + "&")
                .append("response_type=code&scope=snsapi_login&state=")
                .append(System.currentTimeMillis()).append("&redirect_uri=").append(URL);
        response.sendRedirect(stringBuilder.toString());
    }

    /**
     * 判断是不是第一次扫码登录
     * @param code
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/firstLogin")
    @ResponseBody
    public JsonResult firstLogin(@RequestParam("code")String code) throws ApiException {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sns/getuserinfo_bycode");
        OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
        req.setTmpAuthCode(code);
        OapiSnsGetuserinfoBycodeResponse response = client.execute(req,APP_ID,APP_SECRET);
        //把用户信息用JDDBind封装起来
        JDDBind jddBind = new JDDBind();
        jddBind.setNick(response.getUserInfo().getNick());
        jddBind.setUnionid(response.getUserInfo().getUnionid());
        jddBind.setOpenid(response.getUserInfo().getOpenid());

        //查询用户是否在JDDBind表中
        JsonResult jddBIndResult = jddBindServiceImpl.queryJDDBind(jddBind);
        return jddBIndResult;
    }


    @RequestMapping("/loginto")
    @ResponseBody
    public JsonResult loginto(JDDBind jddBind,String code) throws ApiException {
        //取出验证码
        String jcode = jedisClientPool.get(KEY_PREFIX + jddBind.getPhone());
        if(StringUtils.isBlank(code) || !code.equals(jcode)){
            return JsonResultUtil.toJsonString(204,"" + "您输入的验证码有误!");
        }
        JUser queryJUser = jUserServiceImpl.queryJUserByPhone(jddBind.getPhone());
        //验证是否为本系统人员
        if(queryJUser == null){
            return JsonResultUtil.toJsonString(203,"您不是本系统人员!");
        }
        //将查出来的名字放入jddBind中
        jddBind.setRealname(queryJUser.getRealname());
        String sign = JWTUtil.sign(queryJUser,72000);
        Map map = new HashMap();
        map.put("token",sign);
        map.put("openid",jddBind.getOpenid());
        map.put("userId",queryJUser.getId());
        map.put("username",queryJUser.getRealname());
        //将人员记录到表中
        JsonResult jsonResult = jddBindServiceImpl.saveJDDBind(jddBind);
        jsonResult.setData(map);
        return jsonResult;
    }

    @RequestMapping("/code")
    @ResponseBody
    public JsonResult vertifyPhone(String phone) throws Exception {
        //判断手机号是否为空
        if(StringUtils.isBlank(phone)){
            return JsonResultUtil.toJsonString(201,"手机号不能为空");
        }
        //生成验证码
        String code = RandomUtil.getRandom(6);
        //发送短信
        SMSUtil.sendSms(phone,code);
        //缓存验证码
        jedisClientPool.set(KEY_PREFIX+phone,code);
        jedisClientPool.expire(KEY_PREFIX+phone,60);
        return JsonResultUtil.toJsonString(200,"成功");
    }
}
