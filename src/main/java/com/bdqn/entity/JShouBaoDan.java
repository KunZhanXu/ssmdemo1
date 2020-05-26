package com.bdqn.entity;

import java.io.Serializable;
import java.util.Date;

public class JShouBaoDan implements Serializable {
    private Integer id;
    private Date createdate; //录单时间
    private Date makedate; //制作时间
    private Integer depart01;
    private Integer depart02;
    private Integer depart03;
    private Integer depart04;
    private String contact_person;//联系人
    private String xinqianid;//新签ID
    private String cname; //公司名称
    private String ctype; //行业
    private String gd_type;//工单类型
    private String area;  //区域
    private String gd_id; //工单编号
    private Integer isdel; //逻辑删除
    private String createname;//销售人员
    private String status;//状态
    private String password;//密码
    private String qw;//QQ/微信
    private String phone; //手机方式
    private String main_product; //主营产品
    private String special_approval_form; //特殊审批单
    private String remarks; //备注
    private String startcreatedate;  //用于查询createdate
    private String endcreatedate;    //用于查询createdate
    private String startmakedate;  //用于查询createdate
    private String endmakedate;    //用于查询createdate
    private String img_url;        //图片地址

    @Override
    public String toString() {
        return "JShouBaoDan{" +
                "id=" + id +
                ", createdate=" + createdate +
                ", makedate=" + makedate +
                ", depart01=" + depart01 +
                ", depart02=" + depart02 +
                ", depart03=" + depart03 +
                ", depart04=" + depart04 +
                ", contact_person='" + contact_person + '\'' +
                ", xinqianid='" + xinqianid + '\'' +
                ", cname='" + cname + '\'' +
                ", ctype='" + ctype + '\'' +
                ", gd_type='" + gd_type + '\'' +
                ", area='" + area + '\'' +
                ", gd_id='" + gd_id + '\'' +
                ", isdel=" + isdel +
                ", createname='" + createname + '\'' +
                ", status='" + status + '\'' +
                ", password='" + password + '\'' +
                ", qw='" + qw + '\'' +
                ", phone='" + phone + '\'' +
                ", main_product='" + main_product + '\'' +
                ", special_approval_form='" + special_approval_form + '\'' +
                ", remarks='" + remarks + '\'' +
                ", startcreatedate='" + startcreatedate + '\'' +
                ", endcreatedate='" + endcreatedate + '\'' +
                ", startmakedate='" + startmakedate + '\'' +
                ", endmakedate='" + endmakedate + '\'' +
                ", img_url='" + img_url + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getMakedate() {
        return makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
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

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getXinqianid() {
        return xinqianid;
    }

    public void setXinqianid(String xinqianid) {
        this.xinqianid = xinqianid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getGd_type() {
        return gd_type;
    }

    public void setGd_type(String gd_type) {
        this.gd_type = gd_type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getGd_id() {
        return gd_id;
    }

    public void setGd_id(String gd_id) {
        this.gd_id = gd_id;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreatename() {
        return createname;
    }

    public void setCreatename(String createname) {
        this.createname = createname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQw() {
        return qw;
    }

    public void setQw(String qw) {
        this.qw = qw;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMain_product() {
        return main_product;
    }

    public void setMain_product(String main_product) {
        this.main_product = main_product;
    }

    public String getSpecial_approval_form() {
        return special_approval_form;
    }

    public void setSpecial_approval_form(String special_approval_form) {
        this.special_approval_form = special_approval_form;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStartcreatedate() {
        return startcreatedate;
    }

    public void setStartcreatedate(String startcreatedate) {
        this.startcreatedate = startcreatedate;
    }

    public String getEndcreatedate() {
        return endcreatedate;
    }

    public void setEndcreatedate(String endcreatedate) {
        this.endcreatedate = endcreatedate;
    }

    public String getStartmakedate() {
        return startmakedate;
    }

    public void setStartmakedate(String startmakedate) {
        this.startmakedate = startmakedate;
    }

    public String getEndmakedate() {
        return endmakedate;
    }

    public void setEndmakedate(String endmakedate) {
        this.endmakedate = endmakedate;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
