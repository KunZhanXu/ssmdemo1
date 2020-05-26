package com.bdqn.aop;

import org.aspectj.lang.JoinPoint;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2018/3/2/002.
 */
public class ControllerLogger {
    private static final Logger logger= Logger.getLogger("ControllerLogger");

    public void before(JoinPoint jp){
        logger.info("调用了："+jp.getTarget()+
                "的"+jp.getSignature().getName()+"方法，入参："+
                Arrays.toString(jp.getArgs()));
    }

    public void after(JoinPoint jp,Object result){
        logger.info("调用了："+jp.getTarget()+
                "的"+jp.getSignature().getName()+"方法，返回："+
        result);
    }

}
