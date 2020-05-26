package com.bdqn.service;

import com.bdqn.entity.JDDBind;
import com.bdqn.entity.JsonResult;

public interface JDDBindService {
    /**
     * 根据扫码获得的JDDBind,查询出JDDBInd
     * @param jddBind
     * @return
     */
    JsonResult queryJDDBind(JDDBind jddBind);

    /**
     * 存储JDDBind
     * @param jddBind
     * @return
     */
    JsonResult saveJDDBind(JDDBind jddBind);
}
