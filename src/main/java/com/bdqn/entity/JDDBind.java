package com.bdqn.entity;

/**
 * 这是钉钉登录时候让openid和手机号绑定的
 */
public class JDDBind {
    private Integer id;
    private String nick;
    private String unionid;
    private String dingId;
    private String openid;
    private String phone;
    private String realname;

    @Override
    public String toString() {
        return "JDDBind{" +
                "id=" + id +
                ", nick='" + nick + '\'' +
                ", unionid='" + unionid + '\'' +
                ", dingId='" + dingId + '\'' +
                ", openid='" + openid + '\'' +
                ", phone='" + phone + '\'' +
                ", realname='" + realname + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getDingId() {
        return dingId;
    }

    public void setDingId(String dingId) {
        this.dingId = dingId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
}
