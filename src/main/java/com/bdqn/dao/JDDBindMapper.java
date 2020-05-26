package com.bdqn.dao;

import com.bdqn.entity.JDDBind;

import java.util.List;

public interface JDDBindMapper {

    /**
     * 查询数据库中是否有这个JDDBind
     * @param jddBind
     * @return
     */
    List<JDDBind> queryJDDBind(JDDBind jddBind);

    /**
     * 存储JDDBind
     * @param jddBind
     */
    void savaJDDBInd(JDDBind jddBind);
}
