package com.bdqn.dao;

import com.bdqn.entity.JCustomer;

import java.util.List;

public interface JCustomerMapper {

    /**
     * 根据公司名称查询是否有该'集团公海'用户
     * 并且没有被删除  isdel = 0
     * @param jCustomer
     * @return
     */
    List<JCustomer> queryCustomer(JCustomer jCustomer);

    /**
     * 添加用户
     * @param jCustomer
     * @return
     */
    Integer insertCustomer(JCustomer jCustomer);

    /**
     *修改用户
     * @param jCustomer
     * @return
     */
    Integer updateCustomer(JCustomer jCustomer);

}
