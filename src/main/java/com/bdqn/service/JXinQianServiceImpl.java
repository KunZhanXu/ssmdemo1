package com.bdqn.service;

import com.bdqn.dao.JCustomerMapper;
import com.bdqn.dao.JXinQianMapper;
import com.bdqn.entity.JCustomer;
import com.bdqn.entity.JUser;
import com.bdqn.entity.JXinQian;
import com.bdqn.util.ImportXinQianExcel;
import com.bdqn.util.ReadExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

@Service
public class JXinQianServiceImpl implements JXinQianService {

    @Resource
    private JXinQianMapper jXinQianMapper;

    @Resource
    private JUserService jUserService;

    @Resource
    private JCustomerMapper jCustomerMapper;


    @Override
    public Integer insertXinQian(JXinQian jXinQian) {
        //修改或新增用户
        updateJCustomer(jXinQian);
        //新增计算续费年限
        Date openDate = jXinQian.getOpentime();
        Date renewDate = jXinQian.getRenewtime();
        if(openDate!=null && renewDate!=null){
            Calendar open = Calendar.getInstance();
            open.setTime(openDate);
            Calendar renew = Calendar.getInstance();
            renew.setTime(renewDate);

            int renewyear = 0;
            int openYear = open.get(Calendar.YEAR);
            int renewYear = renew.get(Calendar.YEAR);
            renewyear = renewYear-openYear;
            if(  renewyear == 0 ){
                int openMon =open.get(Calendar.MONTH);
                int renewMon =renew.get(Calendar.MONTH);
                int mon = renewMon-openMon;
                if( mon == 0 ){
                    renewyear = 1;
                }else{
                    int openDay =open.get(Calendar.MONDAY);
                    int renewDay =renew.get(Calendar.MONDAY);
                    int day = renewDay-openDay;
                    if(day > 0){
                        renewyear = 1;
                    }
                }
            }else{
                //不是同一年  判断是否超过一年
                int openMon =open.get(Calendar.MONTH);
                int renewMon =renew.get(Calendar.MONTH);
                int mon = renewMon-openMon;
                //超过月
                if( mon > 0 ){
                    renewyear++;
                }else if(mon == 0){
                    int openDay =open.get(Calendar.MONDAY);
                    int renewDay =renew.get(Calendar.MONDAY);
                    int day = renewDay-openDay;
                    if(day > 0){
                        renewyear++;
                    }
                }
            }
            jXinQian.setRenewyear(renewyear);
        }
        return this.jXinQianMapper.insertXinQian(jXinQian);
    }

    @Override
    public Integer updateXinQian(JXinQian jXinQian) {
        return this.jXinQianMapper.updateXinQian(jXinQian);
    }

    @Override
    public Integer updateXinQianIsDel(List<Integer> ids) {
        return this.jXinQianMapper.updateXinQianIsDel(ids);
    }

    @Override
    public Integer deleteXinQian(int id) {
        return null;
    }

    @Override
    public PageInfo<JXinQian> queryXinQianListPage(JXinQian jXinQian,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        System.out.println("这是JXinQian里面的方法"+jXinQian);
        String toOrderType =jXinQian.getTo_order_type();
        if( toOrderType!=null && !"".equals(toOrderType) && "IC，挑公海、转介绍、陌拜、电话开发、工商资源".indexOf(toOrderType) ==-1){
            jXinQian.setTo_order_type("其他");
        }
        List<JXinQian> list = this.jXinQianMapper.queryXinQianList(jXinQian);
        PageInfo<JXinQian> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public JXinQian queryXinQianBuCname(JXinQian jXinQian) {
        return this.jXinQianMapper.queryXinQianBuCname(jXinQian);
    }

    @Override
    public List<JXinQian> queryXinQianList(JXinQian jXinQian) {

        return this.jXinQianMapper.queryXinQianList(jXinQian);
    }

    @Override
    //文件名,文件//销售人员信息
    public Map ImportExcel(String name, MultipartFile file,JUser tokenUser) {
        System.out.println("进来了>>>>>>>>>>ImportExcel");
        boolean b = false;
        //创建处理EXCEL
        ReadExcel readExcel=new ReadExcel();
        ImportXinQianExcel importXinQianExcel = new ImportXinQianExcel();
        //解析excel，获取客户信息集合。
        List<JXinQian> jXinQianList = importXinQianExcel.getExcelInfo(name ,file);
//        System.out.println(jXinQianList);
        if(jXinQianList != null){
            b = true;
        }
        System.out.println("3>>>>>>>>>>>>>>>>"+jXinQianList);
        List<String> repeatList = new ArrayList<>();
        List<String> notInList = new ArrayList<>();
        List<String> nullList = new ArrayList<>();
        //迭代添加客户信息（注：实际上这里也可以直接将customerList集合作为参数，在Mybatis的相应映射文件中使用foreach标签进行批量添加。）
        for (JXinQian jXinQian : jXinQianList){

//            System.out.println("1>>>>>>>>>>>>>>>>>"+jXinQian);
            if (jXinQian.getCreatname() == null){
                nullList.add("null");
                continue;
            }

            //验证新签id是否重复
//            JXinQian queryXinQian = new JXinQian();
//            queryXinQian.setXinqianid(jXinQian.getXinqianid());
//            queryXinQian.setIsdel(0);
////            System.out.println("2>>>>>>>>>>>>>>>>>"+this.jXinQianMapper.queryXinQianList(queryXinQian));
//            if (!this.jXinQianMapper.queryXinQianList(queryXinQian).isEmpty()){
//                repeatList.add(jXinQian.getXinqianid());
////                System.out.println(repeatList);
////                System.out.println(jXinQian.getXinqianid());
//                continue;
//            }
            //验证销售人员是否存在 ,如果存在,还要判断是不是1688新签部门
            List<JUser> jUsers= this.jUserService.selectUserByCname(jXinQian.getCreatname());
            if (jUsers.isEmpty()){
                notInList.add(jXinQian.getCreatname());
//                System.out.println("creatname"+jXinQian.getCreatname());
                continue;
            }
//            System.out.println(jUsers);
            jXinQian.setDepart01(jUsers.get(0).getDepart01() == null ? 0 : jUsers.get(0).getDepart01() );
            jXinQian.setDepart02(jUsers.get(0).getDepart02()== null ? 0 : jUsers.get(0).getDepart02() );
            jXinQian.setDepart03(jUsers.get(0).getDepart03()== null ? 0 : jUsers.get(0).getDepart03() );
            jXinQian.setDepart04(jUsers.get(0).getDepart04()== null ? 0 : jUsers.get(0).getDepart04() );
            jXinQian.setCreateid(tokenUser.getId());
            jXinQian.setUpdateid(jUsers.get(0).getId());
            jXinQian.setIsdel(0);
            //新增计算续费年限
            Date openDate = jXinQian.getOpentime();
            Date renewDate = jXinQian.getRenewtime();
            if(openDate!=null && renewDate!=null){
                Calendar open = Calendar.getInstance();
                open.setTime(openDate);
                Calendar renew = Calendar.getInstance();
                renew.setTime(renewDate);

                int renewyear = 0;
                int openYear = open.get(Calendar.YEAR);
                int renewYear = renew.get(Calendar.YEAR);
                renewyear = renewYear-openYear;
                if(  renewyear == 0 ){
                    int openMon =open.get(Calendar.MONTH);
                    int renewMon =renew.get(Calendar.MONTH);
                    int mon = renewMon-openMon;
                    if( mon == 0 ){
                        renewyear = 1;
                    }else{
                        int openDay =open.get(Calendar.MONDAY);
                        int renewDay =renew.get(Calendar.MONDAY);
                        int day = renewDay-openDay;
                        if(day > 0){
                            renewyear = 1;
                        }
                    }
                }else{
                    //不是同一年  判断是否超过一年
                    int openMon =open.get(Calendar.MONTH);
                    int renewMon =renew.get(Calendar.MONTH);
                    int mon = renewMon-openMon;
                    //超过月
                    if( mon > 0 ){
                        renewyear++;
                    }else if(mon == 0){
                        int openDay =open.get(Calendar.MONDAY);
                        int renewDay =renew.get(Calendar.MONDAY);
                        int day = renewDay-openDay;
                        if(day > 0){
                            renewyear++;
                        }
                    }
                }
                jXinQian.setRenewyear(renewyear);
            }

            //查询数据是否是新用户
            List<JXinQian> selectXinQian = this.jXinQianMapper.queryXinQianListByXinqianId(jXinQian);
            if( selectXinQian==null || selectXinQian.isEmpty() ){
                this.jXinQianMapper.insertXinQian(jXinQian);
            }else{
                this.jXinQianMapper.updateXinQianByXinqianId(jXinQian);
            }

            //修改或新增用户
            updateJCustomer(jXinQian);

        }
        Map map = new HashMap();
        map.put("repeatList", repeatList);
        map.put("notInList", notInList);
        map.put("num", jXinQianList.size() - repeatList.size() - notInList.size() - nullList.size());
//        System.out.println(jXinQianList.size());
//        System.out.println(repeatList.size());
//        System.out.println(notInList.size());
//        map.put("boolean",b);
//        for(TKnowledge knowledge:knowledgeList){
////            knowledgeMapper.insert(knowledge);
//        }
        return map;
    }

    private void updateJCustomer(JXinQian jXinQian){
        //查询主表是否有改用户
        JCustomer queryjCustomer = new JCustomer();
        queryjCustomer.setCanme(jXinQian.getCanme());
        List<JCustomer> jCustomerList = this.jCustomerMapper.queryCustomer(queryjCustomer);
//        System.out.println(jCustomerList);

        if (jCustomerList.isEmpty()){

            JCustomer insertJCustomer = new JCustomer();
            insertJCustomer.setCreatdate(jXinQian.getCreatdate());
            insertJCustomer.setCanme(jXinQian.getCanme());
            insertJCustomer.setBianhao_customer(jXinQian.getBianhao_customer());
            insertJCustomer.setWprice(jXinQian.getWprice());
            insertJCustomer.setPhone(jXinQian.getPhone());
            insertJCustomer.setOpentime(jXinQian.getOpentime());
            insertJCustomer.setAddress(jXinQian.getAddress());
            insertJCustomer.setOrdercount(jXinQian.getOrdercount());
            insertJCustomer.setCreatname(jXinQian.getCreatname());
            insertJCustomer.setXinqianid(jXinQian.getXinqianid());
            insertJCustomer.setTo_order_type(jXinQian.getTo_order_type());
            insertJCustomer.setContact_person(jXinQian.getContact_person());
            insertJCustomer.setTo_money_type(jXinQian.getTo_money_type());

            insertJCustomer.setCreateid(jXinQian.getCreateid());
            insertJCustomer.setUpdateid(jXinQian.getUpdateid());
            insertJCustomer.setIsdel(0);

            insertJCustomer.setBelongname("集团公海");

//            System.out.println(insertJCustomer);

            this.jCustomerMapper.insertCustomer(insertJCustomer);

        }else {

            JCustomer updateJCustomer = new JCustomer();
            updateJCustomer.setId(jCustomerList.get(0).getId());
            updateJCustomer.setCreatname(jXinQian.getCreatname());
            updateJCustomer.setXinqianid(jXinQian.getXinqianid());
            updateJCustomer.setTo_order_type(jXinQian.getTo_order_type());
            updateJCustomer.setContact_person(jXinQian.getContact_person());
            updateJCustomer.setTo_money_type(jXinQian.getTo_money_type());

            updateJCustomer.setIsdel(0);

//            updateJCustomer.setBelongname("集团公海");

//            System.out.println(updateJCustomer);

            this.jCustomerMapper.updateCustomer(updateJCustomer);

        }
    }


}
