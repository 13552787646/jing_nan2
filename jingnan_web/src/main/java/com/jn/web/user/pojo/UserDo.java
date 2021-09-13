package com.jn.web.user.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/*
 * @Author yaxiongliu
 **/
@Table(name="tb_user")
public class UserDo implements Serializable {

    @Id
    private String username;//用户名
    private String phone;//注册手机号
    private String email;//注册邮箱
//    private String sourceType;//会员来源：1:PC，2：H5，3：Android，4：IOS
//    private String nickName;//昵称
//    private String name;//真实姓名
//    private String headPic;//头像地址
//    private String qq;//QQ号码
//    private String sex;//性别，1男，0女
//    private Integer userLevel;//会员等级
//    private Integer points;//积分
//    private Integer experienceValue;//经验值
//    private java.util.Date birthday;//出生年月日


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
