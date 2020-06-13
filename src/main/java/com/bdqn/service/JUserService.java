package com.bdqn.service;

import com.bdqn.entity.JUser;

import java.util.List;

public interface JUserService {

    /**
     * 新签部登录接口
     * @param jUser
     * @return
     */
    JUser userLogin(JUser jUser);

    /**
     * 总部查询接口
     * @param jUser
     * @return
     */
    JUser zbUserLogin(JUser jUser);

    List<JUser> selectLoginAll();

    List<JUser> selectUserByCname(String name);

    /**
     * 根据手机号查询用户真实姓名
     * @param phone
     * @return
     */
    String queryNameByPhone(String phone);

    /**
      * 根据手机号查询用户
     * @param phone
     * @return
             */
    JUser queryNameByPhone1(String phone);

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    JUser selectUserById(int id);

    /**
     * 根据扫码人填入的手机判断是否有着这个人
     * @param juserPhone
     * @return
     */
    JUser queryJUserByPhone(String juserPhone);
}
