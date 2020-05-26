package com.bdqn.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class JXinQian {

    private Integer id;
    private Integer industry;
    private Integer cfrom;
    private Integer chengshudu;
    private Integer status_genjin;
    private Integer status;
    private Integer departjia;
    private Integer departyi;
    private Integer belongid;
    private Integer spstatus;
    private Integer tiaorurenid;
    private Integer lastupdateid;
    private Integer tohighseatimes;
    private Integer chengshudu_last;
    private Integer isdel;
    private Integer createid;
    private Integer updateid;
    private Integer groupid;
    private Integer depart_property;
    private Integer depart01;
    private Integer depart02;
    private Integer depart03;
    private Integer depart04;
    private Integer belongid_last;
    private Integer all_id;
    private Integer hezuo_status;
    private Integer status_trade;
    private Integer multiplestores;
    private Integer ordercount;//单量
    private Integer day90;
    private Integer rechargeallcount;
    private Integer istp;//无用字段
    private Integer wxb;//网销宝
    //续费年限
    private Integer renewyear;
    //是否退款
    private Integer isrefund;


    private String canme;//公司名称
    private String registnumber;
    private String tag;
    private String phone;//手机
    private String faren;
    private String range;
    private String url;
    private String email;
    private String address;//实际地址
    private String belongname;
    private String tiaoruren;
    private String bianhao_customer;//订单行号
    private String createip;
    private String updateip;
    private String depart_property_list;
    private String beizhu;
    private String caccount;
    private String cpwsd;
    private String area;//区域
    private String currentstate;
    private String customersubordinate;
    private String otherplatform;
    private String creatname;//销售
    private String ctype;//行业
    private String xinqianid;//ID
    private String to_order_type;//到单方式
    private String contact_person;//联系人
    private String to_money_type;//到单方式
    private String manager_group;//经理组
    private String startcreatetime;
    private String endcreatetime;
    private String startopentime;
    private String endopentime;
    private String startrenewtime;
    private String endrenewtime;
    //是否赠送首保
    private String isfirstinsurance;
    private String factory_customers; //工厂客户

    private Date registdate;
    private Date tiaorudate;
    private Date lastupdatedate;
    private Date lasttohighseadate;
    private Date creatdate;//录单时间
    private Date upatedate;
    private Date tradespdate;
    private Date opentime;//开通日期
    private Date firstrechargetime;
    private Date renewtime;  //续费日期

    private BigDecimal wprice;//金额
    private BigDecimal rechargeallprice;
    private BigDecimal consumeallprice;

    public Integer getRenewyear() {
        return renewyear;
    }

    public void setRenewyear(Integer renewyear) {
        this.renewyear = renewyear;
    }

    // 后台到前台
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    public Date getRenewtime() {
        return renewtime;
    }

    // 前台到后台   接收参数
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    public void setRenewtime(Date renewtime) {
        this.renewtime = renewtime;
    }

    public String getManager_group() {
        return manager_group;
    }

    public void setManager_group(String manager_group) {
        this.manager_group = manager_group;
    }

    public String getStartopentime() {
        return startopentime;
    }

    public void setStartopentime(String startopentime) {
        this.startopentime = startopentime;
    }

    public String getEndopentime() {
        return endopentime;
    }

    public void setEndopentime(String endopentime) {
        this.endopentime = endopentime;
    }

    public String getStartcreatetime() {
        return startcreatetime;
    }

    public void setStartcreatetime(String startcreatetime) {
        this.startcreatetime = startcreatetime;
    }

    public String getEndcreatetime() {
        return endcreatetime;
    }

    public void setEndcreatetime(String endcreatetime) {
        this.endcreatetime = endcreatetime;
    }

    public String getTo_order_type() {
        return to_order_type;
    }

    public void setTo_order_type(String to_order_type) {
        this.to_order_type = to_order_type;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getTo_money_type() {
        return to_money_type;
    }

    public void setTo_money_type(String to_money_type) {
        this.to_money_type = to_money_type;
    }

    public String getXinqianid() {
        return xinqianid;
    }

    public void setXinqianid(String xinqianid) {
        this.xinqianid = xinqianid;
    }

    public String getCreatname() {
        return creatname;
    }

    public void setCreatname(String creatname) {
        this.creatname = creatname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIndustry() {
        return industry;
    }

    public void setIndustry(Integer industry) {
        this.industry = industry;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public Integer getCfrom() {
        return cfrom;
    }

    public void setCfrom(Integer cfrom) {
        this.cfrom = cfrom;
    }

    public Integer getChengshudu() {
        return chengshudu;
    }

    public void setChengshudu(Integer chengshudu) {
        this.chengshudu = chengshudu;
    }

    public Integer getStatus_genjin() {
        return status_genjin;
    }

    public void setStatus_genjin(Integer status_genjin) {
        this.status_genjin = status_genjin;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDepartjia() {
        return departjia;
    }

    public void setDepartjia(Integer departjia) {
        this.departjia = departjia;
    }

    public Integer getDepartyi() {
        return departyi;
    }

    public void setDepartyi(Integer departyi) {
        this.departyi = departyi;
    }

    public Integer getBelongid() {
        return belongid;
    }

    public void setBelongid(Integer belongid) {
        this.belongid = belongid;
    }

    public Integer getSpstatus() {
        return spstatus;
    }

    public void setSpstatus(Integer spstatus) {
        this.spstatus = spstatus;
    }

    public Integer getTiaorurenid() {
        return tiaorurenid;
    }

    public void setTiaorurenid(Integer tiaorurenid) {
        this.tiaorurenid = tiaorurenid;
    }

    public Integer getLastupdateid() {
        return lastupdateid;
    }

    public void setLastupdateid(Integer lastupdateid) {
        this.lastupdateid = lastupdateid;
    }

    public Integer getTohighseatimes() {
        return tohighseatimes;
    }

    public void setTohighseatimes(Integer tohighseatimes) {
        this.tohighseatimes = tohighseatimes;
    }

    public Integer getChengshudu_last() {
        return chengshudu_last;
    }

    public void setChengshudu_last(Integer chengshudu_last) {
        this.chengshudu_last = chengshudu_last;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public Integer getCreateid() {
        return createid;
    }

    public void setCreateid(Integer createid) {
        this.createid = createid;
    }

    public Integer getUpdateid() {
        return updateid;
    }

    public void setUpdateid(Integer updateid) {
        this.updateid = updateid;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getDepart_property() {
        return depart_property;
    }

    public void setDepart_property(Integer depart_property) {
        this.depart_property = depart_property;
    }

    public Integer getDepart01() {
        return depart01;
    }

    public void setDepart01(Integer depart01) {
        this.depart01 = depart01;
    }

    public Integer getDepart02() {
        return depart02;
    }

    public void setDepart02(Integer depart02) {
        this.depart02 = depart02;
    }

    public Integer getDepart03() {
        return depart03;
    }

    public void setDepart03(Integer depart03) {
        this.depart03 = depart03;
    }

    public Integer getDepart04() {
        return depart04;
    }

    public void setDepart04(Integer depart04) {
        this.depart04 = depart04;
    }

    public Integer getBelongid_last() {
        return belongid_last;
    }

    public void setBelongid_last(Integer belongid_last) {
        this.belongid_last = belongid_last;
    }

    public Integer getAll_id() {
        return all_id;
    }

    public void setAll_id(Integer all_id) {
        this.all_id = all_id;
    }

    public Integer getHezuo_status() {
        return hezuo_status;
    }

    public void setHezuo_status(Integer hezuo_status) {
        this.hezuo_status = hezuo_status;
    }

    public Integer getStatus_trade() {
        return status_trade;
    }

    public void setStatus_trade(Integer status_trade) {
        this.status_trade = status_trade;
    }

    public Integer getMultiplestores() {
        return multiplestores;
    }

    public void setMultiplestores(Integer multiplestores) {
        this.multiplestores = multiplestores;
    }

    public Integer getOrdercount() {
        return ordercount;
    }

    public void setOrdercount(Integer ordercount) {
        this.ordercount = ordercount;
    }

    public Integer getDay90() {
        return day90;
    }

    public void setDay90(Integer day90) {
        this.day90 = day90;
    }

    public Integer getRechargeallcount() {
        return rechargeallcount;
    }

    public void setRechargeallcount(Integer rechargeallcount) {
        this.rechargeallcount = rechargeallcount;
    }

    public Integer getIstp() {
        return istp;
    }

    public void setIstp(Integer istp) {
        this.istp = istp;
    }

    public String getCanme() {
        return canme;
    }

    public void setCanme(String canme) {
        this.canme = canme;
    }

    public String getRegistnumber() {
        return registnumber;
    }

    public void setRegistnumber(String registnumber) {
        this.registnumber = registnumber;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFaren() {
        return faren;
    }

    public void setFaren(String faren) {
        this.faren = faren;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBelongname() {
        return belongname;
    }

    public void setBelongname(String belongname) {
        this.belongname = belongname;
    }

    public String getTiaoruren() {
        return tiaoruren;
    }

    public void setTiaoruren(String tiaoruren) {
        this.tiaoruren = tiaoruren;
    }

    public String getBianhao_customer() {
        return bianhao_customer;
    }

    public void setBianhao_customer(String bianhao_customer) {
        this.bianhao_customer = bianhao_customer;
    }

    public String getCreateip() {
        return createip;
    }

    public void setCreateip(String createip) {
        this.createip = createip;
    }

    public String getUpdateip() {
        return updateip;
    }

    public void setUpdateip(String updateip) {
        this.updateip = updateip;
    }

    public String getDepart_property_list() {
        return depart_property_list;
    }

    public void setDepart_property_list(String depart_property_list) {
        this.depart_property_list = depart_property_list;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getCaccount() {
        return caccount;
    }

    public void setCaccount(String caccount) {
        this.caccount = caccount;
    }

    public String getCpwsd() {
        return cpwsd;
    }

    public void setCpwsd(String cpwsd) {
        this.cpwsd = cpwsd;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getWxb() {
        return wxb;
    }

    public void setWxb(Integer wxb) {
        this.wxb = wxb;
    }

    public String getCurrentstate() {
        return currentstate;
    }

    public void setCurrentstate(String currentstate) {
        this.currentstate = currentstate;
    }

    public String getCustomersubordinate() {
        return customersubordinate;
    }

    public void setCustomersubordinate(String customersubordinate) {
        this.customersubordinate = customersubordinate;
    }

    public String getOtherplatform() {
        return otherplatform;
    }

    public void setOtherplatform(String otherplatform) {
        this.otherplatform = otherplatform;
    }

    public Date getRegistdate() {
        return registdate;
    }

    public void setRegistdate(Date registdate) {
        this.registdate = registdate;
    }

    public Date getTiaorudate() {
        return tiaorudate;
    }

    public void setTiaorudate(Date tiaorudate) {
        this.tiaorudate = tiaorudate;
    }

    public Date getLastupdatedate() {
        return lastupdatedate;
    }

    public void setLastupdatedate(Date lastupdatedate) {
        this.lastupdatedate = lastupdatedate;
    }

    public Date getLasttohighseadate() {
        return lasttohighseadate;
    }

    public void setLasttohighseadate(Date lasttohighseadate) {
        this.lasttohighseadate = lasttohighseadate;
    }

    // 后台到前台
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    public Date getCreatdate() {
        return creatdate;
    }

    // 前台到后台   接收参数
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    public void setCreatdate(Date creatdate) {
        this.creatdate = creatdate;
    }

    public Date getUpatedate() {
        return upatedate;
    }

    public void setUpatedate(Date upatedate) {
        this.upatedate = upatedate;
    }

    public Date getTradespdate() {
        return tradespdate;
    }

    public void setTradespdate(Date tradespdate) {
        this.tradespdate = tradespdate;
    }

    // 后台到前台
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    public Date getOpentime() {
        return opentime;
    }

    // 前台到后台   接收参数
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    public void setOpentime(Date opentime) {
        this.opentime = opentime;
    }

    public Date getFirstrechargetime() {
        return firstrechargetime;
    }

    public void setFirstrechargetime(Date firstrechargetime) {
        this.firstrechargetime = firstrechargetime;
    }

    public BigDecimal getWprice() {
        return wprice;
    }

    public void setWprice(BigDecimal wprice) {
        this.wprice = wprice;
    }

    public BigDecimal getRechargeallprice() {
        return rechargeallprice;
    }

    public void setRechargeallprice(BigDecimal rechargeallprice) {
        this.rechargeallprice = rechargeallprice;
    }

    public BigDecimal getConsumeallprice() {
        return consumeallprice;
    }

    public void setConsumeallprice(BigDecimal consumeallprice) {
        this.consumeallprice = consumeallprice;
    }

    public Integer getIsrefund() {
        return isrefund;
    }

    public void setIsrefund(Integer isrefund) {
        this.isrefund = isrefund;
    }

    public String getStartrenewtime() {
        return startrenewtime;
    }

    public void setStartrenewtime(String startrenewtime) {
        this.startrenewtime = startrenewtime;
    }

    public String getEndrenewtime() {
        return endrenewtime;
    }

    public void setEndrenewtime(String endrenewtime) {
        this.endrenewtime = endrenewtime;
    }

    public String getIsfirstinsurance() {
        return isfirstinsurance;
    }

    public void setIsfirstinsurance(String isfirstinsurance) {
        this.isfirstinsurance = isfirstinsurance;
    }

    public String getFactory_customers() {
        return factory_customers;
    }

    public void setFactory_customers(String factory_customers) {
        this.factory_customers = factory_customers;
    }
}
