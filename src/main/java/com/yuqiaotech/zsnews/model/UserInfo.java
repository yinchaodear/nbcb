package com.yuqiaotech.zsnews.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.yuqiaotech.common.web.base.BaseModel;

import java.util.Date;

/**
 * APP用户。
 * User是登录后台的用户。
 */
@Entity
public class UserInfo extends BaseModel
{
    private static final long serialVersionUID = 9038438776598900321L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    
    private String pwd;
    
    private String mobile;
    
    private String gender;
    
    private String avatar;//头像base64
    
    private String remark;//个人的一些详细说明
    
    private Long totalIntegral;//总积分
    
    private Long usedIntegral;//用掉的积分
    
    private Long currentIntegral;//当前剩余积分

    private Date birthday;

    private String region;//省市区

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * 用户名。
     * @return
     */
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    /**
     * 密码。
     * @return
     */
    public String getPwd()
    {
        return pwd;
    }
    
    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }
    
    /**
     * 手机。
     * @return
     */
    public String getMobile()
    {
        return mobile;
    }
    
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
    
    /**
     * 性别。
     */
    public String getGender()
    {
        return gender;
    }
    
    public void setGender(String gender)
    {
        this.gender = gender;
    }
    
    /**
     * 头像。
     * @return
     */
    public String getAvatar()
    {
        return avatar;
    }
    
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }
    
    /**
     * 描述
     * @return
     */
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    /**
     * 总积分
     * @return
     */
    public Long getTotalIntegral()
    {
        return totalIntegral;
    }
    
    public void setTotalIntegral(Long totalIntegral)
    {
        this.totalIntegral = totalIntegral;
    }
    
    /**
     * 消耗积分
     * @return
     */
    public Long getUsedIntegral()
    {
        return usedIntegral;
    }
    
    public void setUsedIntegral(Long usedIntegral)
    {
        this.usedIntegral = usedIntegral;
    }
    
    /**
     * 当前剩余积分
     * @return
     */
    public Long getCurrentIntegral()
    {
        return currentIntegral;
    }
    
    public void setCurrentIntegral(Long currentIntegral)
    {
        this.currentIntegral = currentIntegral;
    }
}
