package com.bdqn.service;

import com.bdqn.dao.JDDBindMapper;
import com.bdqn.dao.JUserMapper;
import com.bdqn.entity.JDDBind;
import com.bdqn.entity.JUser;
import com.bdqn.entity.JsonResult;
import com.bdqn.util.JWTUtil;
import com.bdqn.util.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JDDBindServiceImpl implements JDDBindService {

    @Autowired
    private JDDBindMapper jddBindMapper;

    @Autowired
    private JUserMapper jUserMapper;

    @Override
    public JsonResult queryJDDBind(JDDBind jddBind,String where) {
        List<JDDBind> jddBinds = jddBindMapper.queryJDDBind(jddBind);
        JsonResult jsonResult = new JsonResult();
        if(jddBinds.size()>1){
            jsonResult.setCode(201);
            jsonResult.setMessage("查询出错");
            return jsonResult;
        }else if(jddBinds.size()==1){
            //如果不是第一次登陆,那就查询出对应的JUser信息
            JDDBind bind = jddBinds.get(0);
            String jUserPhone = bind.getPhone();
            JUser jUser1 = null;
            if(where.equals("1")){
                jUser1 = jUserMapper.queryJUserByPhone(jUserPhone);
            }else{
                jUser1 = jUserMapper.queryNameByPhone(jUserPhone);
            }
            if(jUser1 == null){
                jsonResult.setMessage("您不是本系统人员!");
                return jsonResult;
            }
            String sign = JWTUtil.sign(jUser1,72000);
            Map map = new HashMap();
            map.put("token",sign);
            map.put("openid",bind.getOpenid());
            map.put("userId",jUser1.getId());
            map.put("username",jUser1.getRealname());
            jsonResult.setCode(200);
            jsonResult.setData(map);
            jsonResult.setMessage("登录成功");
            return jsonResult;
        }else{
            jsonResult.setCode(202);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("isFirstLogin","true");
            map.put("jddBind",jddBind);
            jsonResult.setData(map);
            jsonResult.setMessage("您是第一次使用扫码,请输入手机号验证!");
            return jsonResult;
        }
    }

    @Override
    public JsonResult saveJDDBind(JDDBind jddBind) {
        jddBindMapper.savaJDDBInd(jddBind);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(200);
        jsonResult.setMessage("登录成功");
        return jsonResult;
    }
}
