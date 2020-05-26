package com.bdqn.service;

import com.bdqn.dao.JShouBaoDanMapper;
import com.bdqn.entity.JShouBaoDan;
import com.bdqn.entity.JXinQian;
import com.bdqn.entity.JsonResult;
import com.bdqn.util.JsonResultUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JShouBaoDanServiceImpl implements JShouBaoDanService {

    @Autowired
    private JShouBaoDanMapper jShouBaoDanMapper;

    /**
     * 根据xinqianid查询首保单
     * @param selectjShouBaoDan
     * @return
     */
    @Override
    public List<JShouBaoDan> queryShouBaoDanListByXinqianid(JShouBaoDan selectjShouBaoDan) {
        return jShouBaoDanMapper.queryShouBaoDanListByXinqianid(selectjShouBaoDan);
    }

    /**
     * 保存首保单
     * @param jShouBaoDan
     * @return
     */
    @Override
    public JsonResult insertJShouBaoDan(JShouBaoDan jShouBaoDan) {
        Integer n = jShouBaoDanMapper.insertJShouBaoDan(jShouBaoDan);
        if(n==1){
            return JsonResultUtil.toJsonString(200,"保存首保单成功");
        }
        return JsonResultUtil.toJsonString(203,"保存首保单失败");
    }

    /**
     * 分页查询首保单
     * @param jShouBaoDan
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<JShouBaoDan> queryJShouBaoDanListPage(JShouBaoDan jShouBaoDan, Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex,pageSize);
        System.out.println("这是jshoubaodan里面的方法"+jShouBaoDan);

        List<JShouBaoDan> list = this.jShouBaoDanMapper.queryShouBaoDanList(jShouBaoDan);
        PageInfo<JShouBaoDan> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 批量删除首保单(假删除)-isdel:1
     * @param idList
     * @return
     */
    @Override
    public Integer updateJShouBaoDanIsDel(List<Integer> idList) {
        return jShouBaoDanMapper.updateJShouBaoDanIsDel(idList);
    }

    /**
     * 修改首保单
     * @param jShouBaoDan
     * @return
     */
    @Override
    public Integer updateXinQian(JShouBaoDan jShouBaoDan) {
        return jShouBaoDanMapper.updateJShouBaoDan(jShouBaoDan);
    }
}
