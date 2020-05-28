package com.bdqn.controller;

import com.bdqn.entity.*;
import com.bdqn.service.JShouBaoDanService;
import com.bdqn.service.JUserService;
import com.bdqn.util.GeneratID;
import com.bdqn.util.JWTUtil;
import com.bdqn.util.JsonResultUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/sbd")
public class JShouBaoDanController {

    @Autowired
    private JShouBaoDanService jShouBaoDanServiceImpl;

    @Autowired
    private JUserService jUserServiceImpl;

    /**
     * 新增首保单
     * @param jShouBaoDan
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sbd_insert",produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public JsonResult insertJShouBaoDan(JShouBaoDan jShouBaoDan) {
        JsonResult jsonResult = verification(jShouBaoDan);
        if (jsonResult.getCode() == 201){
            return jsonResult;
        }
        //验证未删除新签id
        JShouBaoDan SelectjShouBaoDan = new JShouBaoDan();
        SelectjShouBaoDan.setXinqianid(jShouBaoDan.getXinqianid());
        SelectjShouBaoDan.setIsdel(0);
        //根据xinqianid查询出是否有相同的xinqianid
        List<JShouBaoDan> jShouBaoDans = this.jShouBaoDanServiceImpl.queryShouBaoDanListByXinqianid(SelectjShouBaoDan);
        if (!jShouBaoDans.isEmpty()){
            return JsonResultUtil.toJsonString(201,"新签id不能重复");
        }
        //根据jShoubaodan里面的名字createname判断是否有该销售人员
        List<JUser> jUserList = this.jUserServiceImpl.selectUserByCname(jShouBaoDan.getCreatename());
        if (jUserList.isEmpty()){
            return JsonResultUtil.toJsonString(201,"没有该销售人员");
        }
        //设置录单时间
        Date date = new Date();
        jShouBaoDan.setCreatedate(date);
        //根据查出来的user,将user的部门放入
        JUser jUser = jUserList.get(0);
        jShouBaoDan.setDepart01(jUser.getDepart01() == null ? 0 : jUser.getDepart01() );
        jShouBaoDan.setDepart02(jUser.getDepart02()== null ? 0 : jUser.getDepart02() );
        jShouBaoDan.setDepart03(jUser.getDepart03()== null ? 0 : jUser.getDepart03() );
        jShouBaoDan.setDepart04(jUser.getDepart04()== null ? 0 : jUser.getDepart04() );
        jShouBaoDan.setGd_id("xs"+GeneratID.getGeneratID());
        jShouBaoDan.setStatus("已提交");
        jShouBaoDan.setIsdel(0);
        //将首保单插入
        JsonResult jsonResult1 = this.jShouBaoDanServiceImpl.insertJShouBaoDan(jShouBaoDan);
        jsonResult1.setData(jUser.getRealname());
        return jsonResult1;
    }

    /**
     * 查询首保单
     * @param jShouBaoDan
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sbd_select",produces = {"application/json;charset=UTF-8"})
    public JsonResult selectJShouBaoDan(JShouBaoDan jShouBaoDan,
                                    @RequestParam(value = "pageIndex",required = false,defaultValue = "1") Integer pageIndex,
                                    @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                                    HttpServletRequest request){
        System.out.println(jShouBaoDan);
        //用于接收返回的值
        PageInfo<JShouBaoDan> jShouBaoDans = null;
        jShouBaoDan.setIsdel(0);

        String token = request.getHeader("token");
        JUser tokenUser = JWTUtil.unsign(token, JUser.class);
        String phone = tokenUser.getPhone();
        System.out.println(phone);
        if(phone.equals("18157777426")){
            jShouBaoDan.setArea("温州");
        }else if(phone.equals("13566129623")){
            jShouBaoDan.setArea("苍南");
        }else if(phone.equals("13868793705")){
            jShouBaoDan.setArea("乐清");
        }else if(phone.equals("16899998888")){

        }else{
            jShouBaoDan.setCreatename(tokenUser.getRealname());
        }

        jShouBaoDans = this.jShouBaoDanServiceImpl.queryJShouBaoDanListPage(jShouBaoDan,pageIndex,pageSize);
        return JsonResultUtil.returnMessageDate(jShouBaoDans);
    }
    /**
     * 批量删除首保单(假删除)-isdel:1
     * @return
     */
    @ResponseBody
    @RequestMapping("/sbd_deleteIsDel")
    public JsonResult deleteJShouBaoDan(String ids){
        System.out.println(ids);
        String[] idsArray=ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String s : idsArray) {
            idList.add(Integer.parseInt(s));
        }
        Integer n=this.jShouBaoDanServiceImpl.updateJShouBaoDanIsDel(idList);
        if(n==idsArray.length){
            return JsonResultUtil.returnMessage(n);
        }
        return JsonResultUtil.toJsonString(204, "删除失败");
    }


    /**
     * 批量修改首保单状态
     * @return
     */
    @ResponseBody
    @RequestMapping("/sbd_updateStatus")
    public JsonResult UpdateJShouBaoDanStatus(String ids){
        System.out.println(ids);
        String[] idsArray=ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String s : idsArray) {
            idList.add(Integer.parseInt(s));
        }
        Integer n=this.jShouBaoDanServiceImpl.updateJShouBaoDanStatus(idList);
        if(n==idsArray.length){
            return JsonResultUtil.returnMessage(n);
        }
        return JsonResultUtil.toJsonString(204, "删除失败");
    }

    /**
     * 修改首保单
     * @param jShouBaoDan
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sbd_update",produces = {"application/json;charset=UTF-8"})
    public JsonResult udpateJShouBaoDan(JShouBaoDan jShouBaoDan){

        if (jShouBaoDan == null){
            return JsonResultUtil.toJsonString(201, "参数不能为空");
        }

        Integer n = this.jShouBaoDanServiceImpl.updateXinQian(jShouBaoDan);

        return JsonResultUtil.returnMessage(n);
    }




    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult uploadFile(MultipartFile file) throws IOException {
        //处理图片
        String newFileName = null; //定义 文件名
        String img_url = null;  //保存数据库的路径
        if(!file.isEmpty()){
            //文件放置位置
            String path = "D:\\crm\\images";
            //获取文件原始名
            String originalFileName = file.getOriginalFilename();
            // 新的图片名称
            newFileName = UUID.randomUUID() + originalFileName.substring(originalFileName.lastIndexOf("."));
            // 新的图片
            File newFile = new File(path + newFileName);
            // 将内存中的数据写入磁盘
            file.transferTo(newFile);
        }
        //把图片的相对路径保存至数据库
        img_url = "/images/"+newFileName;
        return JsonResultUtil.toJsonString(200, "上传成功",img_url);
    }





    private JsonResult verification(JShouBaoDan jShouBaoDan){

        if (jShouBaoDan == null ){
            return JsonResultUtil.toJsonString(201, "参数不能为空");
        }

        if (StringUtils.isBlank(jShouBaoDan.getCreatename())){
            return JsonResultUtil.toJsonString(201,"销售人员不能为空");
        }
        if (StringUtils.isBlank(jShouBaoDan.getXinqianid())){
            return JsonResultUtil.toJsonString(201,"新签id不能为空");
        }
        /*if (jShouBaoDan.getCreatedate() == null ){
            return JsonResultUtil.toJsonString(201,"录单时间不能为空");
        }*/
        /*if (jShouBaoDan.getMakedate() == null ){
            return JsonResultUtil.toJsonString(201,"制作时间不能为空");
        }*/
        if (StringUtils.isBlank(jShouBaoDan.getCname())){
            return JsonResultUtil.toJsonString(201,"公司名称不能为空");
        }
        /*if (StringUtils.isBlank(jShouBaoDan.getArea())){
            return JsonResultUtil.toJsonString(201,"区域不能为空");
        }*/
        if (StringUtils.isBlank(jShouBaoDan.getContact_person())){
            return JsonResultUtil.toJsonString(201,"联系人不能为空");
        }
        /*if (StringUtils.isBlank(jShouBaoDan.getCtype())){
            return JsonResultUtil.toJsonString(201,"行业不能为空");
        }*/
        /*if (StringUtils.isBlank(jShouBaoDan.getGd_type())){
            return JsonResultUtil.toJsonString(201,"工单类型不能为空");
        }*/
        /*if (StringUtils.isBlank(jShouBaoDan.getQw())){
            return JsonResultUtil.toJsonString(201,"QQ/微信不能为空");
        }*/
        /*if (StringUtils.isBlank(jShouBaoDan.getSpecial_approval_form())){
            return JsonResultUtil.toJsonString(201,"特殊批注单不能为空");
        }*/
        if (StringUtils.isBlank(jShouBaoDan.getMain_product())){
            return JsonResultUtil.toJsonString(201,"主营产品不能为空");
        }
        if (StringUtils.isBlank(jShouBaoDan.getPassword())){
            return JsonResultUtil.toJsonString(201,"密码不能为空");
        }
        if (StringUtils.isBlank(jShouBaoDan.getPhone())){
            return JsonResultUtil.toJsonString(201,"电话不能为空");
        }
        return JsonResultUtil.toJsonString(200,"");
    }
}
