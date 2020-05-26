package com.bdqn.dao;

import com.bdqn.entity.JXinQian;

import java.util.List;

public interface JXinQianMapper {

    /**
     * 添加新签用户
     * @param jXinQian
     * @return
     */
    Integer insertXinQian(JXinQian jXinQian);

    /**
     * 修改新签用户
     * @param jXinQian
     * @return
     */
    Integer updateXinQian(JXinQian jXinQian);

    /**
     * 批量假删除用户
     * @param ids
     * @return
     */
    Integer updateXinQianIsDel(List<Integer> ids);

    /**
     * 该方法暂时不写 TODO
     * @param id
     * @return
     */
    Integer deleteXinQian(int id);

    /**
     * 查询新签用户
     * @param jXinQian
     * @return
     */
    List<JXinQian> queryXinQianList(JXinQian jXinQian);

    /**
     * 根据用户名查询新签用户
     * @param jXinQian
     * @return
     */
    JXinQian queryXinQianBuCname(JXinQian jXinQian);


    /**
     * 上传根据新签id更新数据
     * @param jXinQian
     * @return
     */
    Integer updateXinQianByXinqianId(JXinQian jXinQian);


    /**
     * 通过新签id查询数据
     * @param jXinQian
     * @return
     */
    List<JXinQian> queryXinQianListByXinqianId(JXinQian jXinQian);

}
