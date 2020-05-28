package com.bdqn.service;

import com.bdqn.entity.JShouBaoDan;
import com.bdqn.entity.JsonResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface JShouBaoDanService {
    /**
     * 根据xinqianid查询出shoubaodan
     * @param selectjShouBaoDan
     * @return
     */
    List<JShouBaoDan> queryShouBaoDanListByXinqianid(JShouBaoDan selectjShouBaoDan);

    /**
     * 存储首保单
     * @param jShouBaoDan
     * @return
     */
    JsonResult insertJShouBaoDan(JShouBaoDan jShouBaoDan);

    /**
     * 分页查询首保单
     * @param jShouBaoDan
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PageInfo<JShouBaoDan> queryJShouBaoDanListPage(JShouBaoDan jShouBaoDan, Integer pageIndex, Integer pageSize);

    /**
     * 批量删除首保单(假删除)-isdel:1
     * @param idList
     * @return
     */
    Integer updateJShouBaoDanIsDel(List<Integer> idList);

    /**
     * 批量修改首保单的status-status:已完成
     * @param idList
     * @return
     */
    Integer updateJShouBaoDanStatus(List<Integer> idList);

    /**
     * 修改首保单
     * @param jShouBaoDan
     * @return
     */
    Integer updateXinQian(JShouBaoDan jShouBaoDan);
}
