package com.bdqn.dao;

import com.bdqn.entity.JShouBaoDan;

import java.util.List;

public interface JShouBaoDanMapper {

    /**
     * 根据xinqianid查询出首保单
     * @param selectjShouBaoDan
     * @return
     */
    List<JShouBaoDan> queryShouBaoDanListByXinqianid(JShouBaoDan selectjShouBaoDan);

    /**
     * 保存首保单
     * @param jShouBaoDan
     * @return
     */
    Integer insertJShouBaoDan(JShouBaoDan jShouBaoDan);

    /**
     * 分页查询首保单
     * @param jShouBaoDan
     * @return
     */
    List<JShouBaoDan> queryShouBaoDanList(JShouBaoDan jShouBaoDan);

    /**
     * 批量删除首保单(假删除)-isdel:1
     * @param idList
     * @return
     */
    Integer updateJShouBaoDanIsDel(List<Integer> idList);

    /**
     * 修改首保单
     * @param jShouBaoDan
     * @return
     */
    Integer updateJShouBaoDan(JShouBaoDan jShouBaoDan);
}
