package com.bdqn.service;

import com.bdqn.entity.JUser;
import com.bdqn.entity.JXinQian;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface JXinQianService {

    /**
     * 新增新签
     * @param jXinQian
     * @return
     */
    Integer insertXinQian(JXinQian jXinQian);

    /**
     * 修改新签
     * @param jXinQian
     * @return
     */
    Integer updateXinQian(JXinQian jXinQian);

    /**
     * 新签假删除
     * @param ids 新签id数组
     * @return
     */
    Integer updateXinQianIsDel(List<Integer> ids);

    /**
     * 新签删除,未做
     * @param id
     * @return
     */
    Integer deleteXinQian(int id);

    /**
     * 分页查询新签数据
     * @param jXinQian
     * @param pageNum 页码
     * @param pageSize 页数
     * @return
     */
    PageInfo<JXinQian> queryXinQianListPage(JXinQian jXinQian, Integer pageNum, Integer pageSize);

    /**
     * 根据用户名查询新签用户
     * @param jXinQian
     * @return
     */
    JXinQian queryXinQianBuCname(JXinQian jXinQian);

    /**
     * 批量查询新签用户
     * @param jXinQian
     * @return
     */
    List<JXinQian> queryXinQianList(JXinQian jXinQian);

    /**
     * 导入excel
     * @param name
     * @param file
     * @return
     */
    Map ImportExcel(String name, MultipartFile file, JUser tokenUser);

}
