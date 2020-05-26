package com.bdqn;


import com.bdqn.dao.JedisClientPool;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class jedisTest {


    @Test
    public void testkeyset(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        BeanFactory factory = (BeanFactory) context;
        JedisClientPool jedisClientPool = (JedisClientPool)context.getBean("jedisClientPool");
        /*Jedis jedis = new Jedis("139.196.105.164", 6379);
        jedis.auth("123456");
        String value = jedis.get("key1");
        System.out.println(value);*/
        /*String s = jedisClientPool.get("key1");
        System.out.println(s);*/
        String set = jedisClientPool.set("hello", "你好");
        System.out.println(set);
    }
}
