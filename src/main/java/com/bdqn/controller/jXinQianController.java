package com.bdqn.controller;

import com.bdqn.entity.JUser;
import com.bdqn.entity.JXinQian;
import com.bdqn.entity.JsonResult;
import com.bdqn.service.JUserService;
import com.bdqn.service.JXinQianService;
import com.bdqn.util.JWTUtil;
import com.bdqn.util.JsonResultUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cai
 */
@Controller
@RequestMapping("/xinqian")
public class jXinQianController {

    @Resource
    private JXinQianService jXinQianService;
    @Resource
    private JUserService jUserService;

    @ResponseBody
    @RequestMapping(value = "/queryNameByPhone",produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public JsonResult queryNameByPhone(@RequestParam("phone") String phone){
        if (StringUtils.isNotEmpty(phone)){
            String name = jUserService.queryNameByPhone(phone);
            if (name == null){
                return JsonResultUtil.toJsonString(201,"没有该销售人员");
            }
            return JsonResultUtil.toJsonString(200,"成功",name);
        }
        return JsonResultUtil.toJsonString(201,"该手机号不存在");
    }

    /**
     * 新增新签客户
     * @param jXinQian
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insert",produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public JsonResult insertXinQian(JXinQian jXinQian, HttpServletRequest request){

        JsonResult jsonResult = verification(jXinQian);
        if (jsonResult.getCode() == 201){
            return jsonResult;
        }

        //验证未删除新签id
        JXinQian selectjXinQian = new JXinQian();
        selectjXinQian.setXinqianid(jXinQian.getXinqianid());
        selectjXinQian.setIsdel(0);
        List<JXinQian> jXinQians = this.jXinQianService.queryXinQianList(selectjXinQian);
        if (!jXinQians.isEmpty()){
            return JsonResultUtil.toJsonString(201,"新签id不能重复");
        }

        List<JUser> jUserList = this.jUserService.selectUserByCname(jXinQian.getCreatname());
        if (jUserList.isEmpty()){
            return JsonResultUtil.toJsonString(201,"没有该销售人员");
        }

        String token = request.getHeader("token");
        JUser tokenUser = JWTUtil.unsign(token, JUser.class);
        JUser jUser = jUserList.get(0);
        jXinQian.setDepart01(jUser.getDepart01() == null ? 0 : jUser.getDepart01() );
        jXinQian.setDepart02(jUser.getDepart02()== null ? 0 : jUser.getDepart02() );
        jXinQian.setDepart03(jUser.getDepart03()== null ? 0 : jUser.getDepart03() );
        jXinQian.setDepart04(jUser.getDepart04()== null ? 0 : jUser.getDepart04() );
        jXinQian.setCreateid(tokenUser.getId());
        jXinQian.setUpdateid(jUser.getId());
        jXinQian.setCreatname(jXinQian.getCreatname());
        jXinQian.setIsdel(0);
        Integer n =  this.jXinQianService.insertXinQian(jXinQian);
        return JsonResultUtil.returnMessage(n);
    }


    /**
     * 新增新签客户不带token
     * @param jXinQian
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertNottoken",produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public JsonResult insertXinQian1(JXinQian jXinQian, String  insertPhone, HttpServletRequest request){

        JsonResult jsonResult = verification(jXinQian);
        if (jsonResult.getCode() == 201){
            return jsonResult;
        }

        //验证未删除新签id
        JXinQian selectjXinQian = new JXinQian();
        selectjXinQian.setXinqianid(jXinQian.getXinqianid());
        selectjXinQian.setIsdel(0);
        List<JXinQian> jXinQians = this.jXinQianService.queryXinQianList(selectjXinQian);
        if (!jXinQians.isEmpty()){
            return JsonResultUtil.toJsonString(201,"新签id不能重复");
        }
        List<JUser> jUserList = this.jUserService.selectUserByCname(jXinQian.getCreatname());
        if (jUserList.isEmpty()){
            return JsonResultUtil.toJsonString(201,"没有该销售人员");
        }
        JUser jUser = jUserList.get(0);
        jXinQian.setDepart01(jUser.getDepart01() == null ? 0 : jUser.getDepart01() );
        jXinQian.setDepart02(jUser.getDepart02()== null ? 0 : jUser.getDepart02() );
        jXinQian.setDepart03(jUser.getDepart03()== null ? 0 : jUser.getDepart03() );
        jXinQian.setDepart04(jUser.getDepart04()== null ? 0 : jUser.getDepart04() );
        jXinQian.setCreateid(jUser.getId());
        jXinQian.setUpdateid(jUser.getId());
        jXinQian.setCreatname(jXinQian.getCreatname());
        jXinQian.setIsdel(0);
        Integer n =  this.jXinQianService.insertXinQian(jXinQian);
        JsonResult jsonResult1 = JsonResultUtil.returnMessage(n);
        jsonResult1.setData(jUser.getRealname());
        return jsonResult1;
    }


    /**
     * 修改新签客户
     * @param jXinQian
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update",produces = {"application/json;charset=UTF-8"})
    public JsonResult udpateXinQian(JXinQian jXinQian){

        if (jXinQian == null){
            return JsonResultUtil.toJsonString(201, "参数不能为空");
        }

        Integer n = this.jXinQianService.updateXinQian(jXinQian);

        return JsonResultUtil.returnMessage(n);
    }

    /**
     * 查询新签客户  未删除 isdel:0
     * @param jXinQian
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/select",produces = {"application/json;charset=UTF-8"})
    public JsonResult selectXinQian(JXinQian jXinQian,Integer pageIndex,Integer pageSize,String startcreatetimes,String endcreatetimes,String startopentimes,String endopentimes,HttpServletRequest request){

        //用于接收返回的值
        PageInfo<JXinQian> jXinQians = null;

        if(pageIndex == null){
            pageIndex = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }


        //当用户刚登陆的时候所需查询的JXinQian
        if(jXinQian.getDepart01()==null&&jXinQian.getDepart02()==null&&jXinQian.getDepart03()==null&&jXinQian.getDepart04()==null){
            System.out.println("我进来了,我是空的");
            Integer userId = Integer.valueOf(request.getHeader("userId"));
            JUser jUser = jUserService.selectUserById(userId);
            int index =0;
            if(jUser.getDepart01()!=null){
                if(jUser.getDepart01()!=0){
                    index++;
                }
            }
            if(jUser.getDepart02()!=null){
                if(jUser.getDepart02()!=0){
                    index++;
                }
            }
            if(jUser.getDepart03()!=null){
                if(jUser.getDepart03()!=0){
                    index++;
                }
            }
            if(jUser.getDepart04()!=null){
                if(jUser.getDepart04()!=0){
                    index++;
                }
            }
            switch (index){
                case 0:
                    jXinQian.setIsdel(0);
                    jXinQians = this.jXinQianService.queryXinQianListPage(jXinQian,pageIndex,pageSize);
                    break;
                case 1:
                    jXinQian.setDepart01(jUser.getDepart01());
                    jXinQian.setIsdel(0);
                    System.out.println(jXinQian);
                    jXinQians = this.jXinQianService.queryXinQianListPage(jXinQian,pageIndex,pageSize);
                    break;
                case 2:
                    jXinQian.setDepart02(jUser.getDepart02());
                    jXinQian.setIsdel(0);
                    jXinQians = this.jXinQianService.queryXinQianListPage(jXinQian,pageIndex,pageSize);
                    break;
                case 3:
                    jXinQian.setDepart03(jUser.getDepart03());
                    jXinQian.setIsdel(0);
                    jXinQians = this.jXinQianService.queryXinQianListPage(jXinQian,pageIndex,pageSize);
                    break;
                case 4:
                    jXinQian.setDepart04(jUser.getDepart04());
                    jXinQian.setIsdel(0);
                    jXinQians = this.jXinQianService.queryXinQianListPage(jXinQian,pageIndex,pageSize);
                    break;
            }
            return JsonResultUtil.returnMessageDate(jXinQians);
        }



        jXinQian.setIsdel(0);

        jXinQians = this.jXinQianService.queryXinQianListPage(jXinQian,pageIndex,pageSize);

        return JsonResultUtil.returnMessageDate(jXinQians);
    }

    /**
     * 批量删除新签客户(假删除)-isdel:1
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteIsDel")
    public JsonResult deleteXinQian(String ids){
        System.out.println(ids);
        String[] idsArray=ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String s : idsArray) {
            idList.add(Integer.parseInt(s));
        }
        Integer n=this.jXinQianService.updateXinQianIsDel(idList);
        return JsonResultUtil.returnMessage(n);
    }

    /**
     * 批量上传新签客户
     * @param file
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public JsonResult uploadFile(@RequestParam(value = "filename") MultipartFile file,
                             HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("开始上传文件");
        //判断文件是否为空
        if (file == null){
            return JsonResultUtil.toJsonString(201,"文件不能为空");
        }
        //获取文件名
        String name = file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size = file.getSize();
        if (name == null || ("").equals(name) && size == 0) {
            return JsonResultUtil.toJsonString(201,"文件大小不能为0");
        }

        String token = request.getHeader("token");
        JUser tokenUser = JWTUtil.unsign(token, JUser.class);

        //批量导入。参数：文件名，文件。
        Map map = this.jXinQianService.ImportExcel(name, file,tokenUser);
//        boolean b = true;
        return JsonResultUtil.toJsonString(200,"导入成功",map);
    }

    /**
     * 查询报表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/report")
    public JsonResult report(){

        return JsonResultUtil.toJsonString(200,"查询成功");
    }



    private JsonResult verification(JXinQian jXinQian){

        if (jXinQian == null ){
            return JsonResultUtil.toJsonString(201, "参数不能为空");
        }

        if (jXinQian.getCreatname() == null || jXinQian.getCreatname().isEmpty()){
            return JsonResultUtil.toJsonString(201,"销售人员不能为空");
        }
        if (jXinQian.getXinqianid() == null || jXinQian.getXinqianid().isEmpty()){
            return JsonResultUtil.toJsonString(201,"新签id不能为空");
        }
        if (jXinQian.getCreatdate() == null ){
            return JsonResultUtil.toJsonString(201,"录单时间不能为空");
        }
        if (jXinQian.getCanme() == null || jXinQian.getCanme().isEmpty()){
            return JsonResultUtil.toJsonString(201,"公司名称不能为空");
        }
        if (jXinQian.getBianhao_customer() == null || jXinQian.getBianhao_customer().isEmpty()){
            return JsonResultUtil.toJsonString(201,"订单行号不能为空");
        }
        if (jXinQian.getWprice() == null ){
            return JsonResultUtil.toJsonString(201,"金额不能为空");
        }
        /*if (jXinQian.getWxb() == null ){
            return JsonResultUtil.toJsonString(201,"网销宝不能为空");
        }*/
        if (jXinQian.getCtype() == null || jXinQian.getCtype().isEmpty()){
            return JsonResultUtil.toJsonString(201,"行业不能为空");
        }
        if (jXinQian.getTo_order_type() == null || jXinQian.getTo_order_type().isEmpty()){
            return JsonResultUtil.toJsonString(201,"到单方式不能为空");
        }
        if (jXinQian.getContact_person() == null || jXinQian.getContact_person().isEmpty()){
            return JsonResultUtil.toJsonString(201,"联系人不能为空");
        }
        if (jXinQian.getPhone() == null || jXinQian.getPhone().isEmpty()){
            return JsonResultUtil.toJsonString(201,"手机不能为空");
        }
        if (jXinQian.getTo_money_type() == null || jXinQian.getTo_money_type().isEmpty()){
            return JsonResultUtil.toJsonString(201,"到款方式不能为空");
        }
        /*if (jXinQian.getOpentime() == null ){
            return JsonResultUtil.toJsonString(201,"开通日期不能为空");
        }*/
        if (jXinQian.getAddress() == null || jXinQian.getAddress().isEmpty()){
            return JsonResultUtil.toJsonString(201,"实际地址不能为空");
        }
        if (jXinQian.getOrdercount() == null ){
            return JsonResultUtil.toJsonString(201,"单量不能为空");
        }

        /*if (jXinQian.getIsfirstinsurance() == null || jXinQian.getIsfirstinsurance().isEmpty() ){
            return JsonResultUtil.toJsonString(201,"是否赠送首保不能为空");
        }*/
        /*if (jXinQian.getRenewtime() == null ){
            return JsonResultUtil.toJsonString(201,"续费日期不能为空");
        }*/
        /*if (jXinQian.getIsrefund() == null ){
            return JsonResultUtil.toJsonString(201,"是否退款不能为空");
        }*/


        return JsonResultUtil.toJsonString(200,"");
    }




}
