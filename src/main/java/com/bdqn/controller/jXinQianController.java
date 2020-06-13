package com.bdqn.controller;

import com.bdqn.entity.*;
import com.bdqn.service.JDepartService;
import com.bdqn.service.JShouBaoDanService;
import com.bdqn.service.JUserService;
import com.bdqn.service.JXinQianService;
import com.bdqn.util.ExcelUtil;
import com.bdqn.util.JWTUtil;
import com.bdqn.util.JsonResultUtil;
import com.bdqn.util.ZipUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private JShouBaoDanService jShouBaoDanServiceImpl;
    @Autowired
    private JDepartService jDepartServiceImpl;

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
        //设置是否赠送首保
        JShouBaoDan SelectjShouBaoDan = new JShouBaoDan();
        SelectjShouBaoDan.setXinqianid(jXinQian.getXinqianid());
        SelectjShouBaoDan.setIsdel(0);
        //根据xinqianid查询出是否有相同的xinqianid
        List<JShouBaoDan> jShouBaoDans = this.jShouBaoDanServiceImpl.queryShouBaoDanListByXinqianid(SelectjShouBaoDan);
        jXinQian.setIsfirstinsurance(jShouBaoDans.isEmpty()?"否":"是");

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
            Integer userId = Integer.valueOf(request.getHeader("userId"));
            if(userId == null){
                return JsonResultUtil.toJsonString(201,"userId不能为空");
            }
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
                    jXinQian.setDepart01(jUser.getDepart01());
                    jXinQian.setDepart02(jUser.getDepart02());
                    jXinQian.setIsdel(0);
                    jXinQians = this.jXinQianService.queryXinQianListPage(jXinQian,pageIndex,pageSize);
                    break;
                case 3:
                    jXinQian.setDepart01(jUser.getDepart01());
                    jXinQian.setDepart02(jUser.getDepart02());
                    jXinQian.setDepart03(jUser.getDepart03());
                    jXinQian.setIsdel(0);
                    jXinQians = this.jXinQianService.queryXinQianListPage(jXinQian,pageIndex,pageSize);
                    break;
                case 4:
                    jXinQian.setDepart01(jUser.getDepart01());
                    jXinQian.setDepart02(jUser.getDepart02());
                    jXinQian.setDepart03(jUser.getDepart03());
                    jXinQian.setDepart04(jUser.getDepart04());
                    jXinQian.setCreatname(jUser.getRealname());
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
     * 导出新签表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/export")
    public JsonResult exportXinqianExcel(
            JXinQian jXinQian,HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        //用于接收返回的值
        List<JXinQian> jXinQians = null;
        jXinQian.setIsdel(0);
        jXinQians = this.jXinQianService.queryXinQianList(jXinQian);

        int length = jXinQians.size(); // 数据行数
        if (jXinQians == null || length == 0) {
            response.getWriter().write("导出失败，暂无数据可导出");
            response.setHeader("refresh", "2;url=http://localhost/MySystem/demo/download.html");
        }
        // 导出存放路径
        String exportPath = request.getSession().getServletContext().getRealPath("excel");
        System.out.println(exportPath);
        File file = new File(exportPath);
        if (file.exists()) {
            file.mkdirs(); // 创建文件夹
        }
        // 格式化日期
        SimpleDateFormat sdf1 = new SimpleDateFormat("YYYY-MM-dd");
        String sheetName = "新签下单"; // 底部标题
        String fileName = sheetName + sdf1.format(new Date()); // 文件名称
        System.out.println(fileName);
        String[] title = { "区域","经理组", "销售人员","新签ID", "公司名称","录单时间",  "订单行号", "金额", "网销宝",
                "行业", "到单方式", "联系人", "手机", "到账方式","开通日期","地址","单量","主营产品","续费日期","退款"
                ,"工厂客户","备注" }; // 标题字段
        String[][] values = new String[length][];	//行列值
        for (int i = 0; i < length; i++) {
            values[i] = new String[title.length];	//行值
            JDepart jDepart = jDepartServiceImpl.queryDepartById(jXinQians.get(i).getDepart02());
            values[i][0] = jDepart.getName();
            JDepart jDepart1 = jDepartServiceImpl.queryDepartById(jXinQians.get(i).getDepart03());
            values[i][1] = jDepart1.getName();
            values[i][2] = jXinQians.get(i).getCreatname();
            values[i][3] = jXinQians.get(i).getXinqianid();
            values[i][5] = jXinQians.get(i).getCreatdate()==null?"":sdf1.format(jXinQians.get(i).getCreatdate());
            values[i][4] = jXinQians.get(i).getCanme();
            values[i][6] = jXinQians.get(i).getBianhao_customer()==null?"":jXinQians.get(i).getBianhao_customer();
            values[i][7] = jXinQians.get(i).getWprice().toString();
            values[i][8] = jXinQians.get(i).getWxb()==null?"":jXinQians.get(i).getWxb().toString();
            values[i][9] = jXinQians.get(i).getCtype();
            values[i][10] = jXinQians.get(i).getTo_order_type();
            values[i][11] = jXinQians.get(i).getContact_person();
            values[i][12] = jXinQians.get(i).getPhone();
            values[i][13] = jXinQians.get(i).getTo_money_type();
            values[i][14] = jXinQians.get(i).getOpentime()==null?"":sdf1.format(jXinQians.get(i).getOpentime());
            values[i][15] = jXinQians.get(i).getAddress();
            values[i][16] = jXinQians.get(i).getOrdercount()==null?"":jXinQians.get(i).getOrdercount().toString();
            values[i][17] = jXinQians.get(i).getMain_product();
            values[i][18] = jXinQians.get(i).getRenewtime()==null?"":sdf1.format(jXinQians.get(i).getRenewtime());
            values[i][19] = jXinQians.get(i).getIsrefund()==null?"":(jXinQians.get(i).getIsrefund()==1?"否":"是");
            values[i][20] = jXinQians.get(i).getFactory_customers();
            values[i][21] = jXinQians.get(i).getRemarks();
        }
        // 获取HSSFWorkbook 对象
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, values, null);
        //结合其他的表输出
        String fileName2 = fileName + ".xls";
        boolean flag = ExcelUtil.createExcel(exportPath, fileName2, wb);//导出excel文件
        System.out.println(flag);
        if (flag) {
            // 创建成功则进行压缩下载
            ZipUtil.setZipDownload(fileName,request,response);
            // 压缩
            String[] fileNameArr = {fileName2};
            ZipUtil.downloadZip(exportPath, fileNameArr, response);//这里已经作出了相应，所以不能再重定向或转发，也不能返回信息，否则会报错

            System.out.println("export ok");
            return null;
        } else {
            System.out.println("export fail");
            return null;
        }
    }

    public static void main(String[] args) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String sj = sdf.format(d);
        System.out.println(sj);
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
