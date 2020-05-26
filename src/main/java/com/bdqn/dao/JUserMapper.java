package com.bdqn.dao;

import com.bdqn.entity.JUser;

import java.util.List;

public interface JUserMapper {

    JUser userLogin(JUser jUser);

    List<JUser> selectLoginAll();

    List<JUser> selectUserByUname(String name);

    /**
     * 根据号码查询真实姓名
     * @param phone
     * @return
     */
    JUser queryNameByPhone(String phone);

    /**
     * 根据用户Id查询用户
     * @param id
     * @return
     */
    JUser selectUserById(Integer id);

}
