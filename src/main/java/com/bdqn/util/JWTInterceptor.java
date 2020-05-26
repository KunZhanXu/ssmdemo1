package com.bdqn.util;

import com.alibaba.fastjson.JSONArray;
import com.bdqn.controller.JUserController;
import com.bdqn.entity.JUser;
import com.bdqn.entity.ResultVO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Component
public class JWTInterceptor implements HandlerInterceptor{
    public static Logger logger = Logger.getLogger(JUserController.class);


    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    /**
     * Token validates the interceptor
     * @author Stephen
     * @time 2019-10-11 17:00:32
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws IOException {
        System.out.println("JWTInterceptor拦截请求: " + request.getServletPath());
        ResultVO result = new ResultVO();
        PrintWriter out = null ;
        String token = request.getHeader("token");
        String userId = request.getHeader("userId") == null ? "0" : request.getHeader("userId") ;
        /** 您的处理逻辑 */
        JUser JUser = JWTUtil.unsign(token, JUser.class);
        System.out.println("userId>>>>:"+userId);
        if ( JUser != null && Integer.valueOf(userId).equals(JUser.getId())){
            request.setAttribute("userId",userId);
            return true;
        }
        //以下是返回拦截器拦截后返回json格式的方式
        result.setStatus(203);
        result.setMessage("Login verification failed, please login again");
        String jsonStr = JSONArray.toJSONString(result);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

//        return true;
        out = response.getWriter();
        out.append(jsonStr);
        return false;
    }


}
