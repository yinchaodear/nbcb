package com.yuqiaotech.zsnews.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.yuqiaotech.common.web.base.BaseModel;

/**
 * APP用户。
 * User是登录后台的用户。
 * app用户表的用户名、简介、头像，需要通过后台审核，用户注册上来以后，有默认的头像、用户名、简介，对应的是avatar,username,remark，如果用户提交了这三个字段的修改，把新的字段存在new开头的字段里，并且把状态位置为审核中。
 * 同时，前台控制下，状态为审核中的不可以再修改
 */
@Entity
public class UserInfo extends BaseModel
{
    private static final long serialVersionUID = 9038438776598900321L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    
    private String newUsername;//新用户名
    
    private String pwd;
    
    private String mobile;
    
    private String gender;
    
    private String avatar;//头像base64
    
    private String newAvatar;//新头像
    
    private String remark;//个人的一些详细说明
    
    private String nickName;//昵称,（微信，qq，微博的，不唯一）
    
    private String newNickName;//新昵称
    
    private String newRemark;//新的用户简介
    
    private Long totalIntegral;//总积分
    
    private Long usedIntegral;//用掉的积分
    
    private Long currentIntegral;//当前剩余积分
    
    private Date birthday;
    
    private String region;//省市区
    
    private Integer status;//状态，用户的头像和用户名需要审核，存在状态位：审核中，通过，不通过，未提交审核
    
    private Integer deltag;//删除标识
    
    private Integer push;//推送
    
    private Integer likes;//点赞数
    
    public Integer getPush()
    {
        return push;
    }
    
    public void setPush(Integer push)
    {
        this.push = push;
    }
    
    public String getRegion()
    {
        return region;
    }
    
    public void setRegion(String region)
    {
        this.region = region;
    }
    
    public Date getBirthday()
    {
        return birthday;
    }
    
    public void setBirthday(Date birthday)
    {
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
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getNewUsername()
    {
        return newUsername;
    }
    
    public void setNewUsername(String newUsername)
    {
        this.newUsername = newUsername;
    }
    
    public String getNewAvatar()
    {
        return newAvatar;
    }
    
    public void setNewAvatar(String newAvatar)
    {
        this.newAvatar = newAvatar;
    }
    
    public String getNewRemark()
    {
        return newRemark;
    }
    
    public void setNewRemark(String newRemark)
    {
        this.newRemark = newRemark;
    }
    
    public Integer getDeltag()
    {
        return deltag;
    }
    
    public void setDeltag(Integer deltag)
    {
        this.deltag = deltag;
    }
    
    public Integer getLikes()
    {
        return likes;
    }
    
    public void setLikes(Integer likes)
    {
        this.likes = likes;
    }
    
    /**
     * 昵称,（微信，qq，微博的，不唯一）
     * @return
     */
    public String getNickName()
    {
        return nickName;
    }
    
    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }
    
    public String getNewNickName()
    {
        return newNickName;
    }
    
    public void setNewNickName(String newNickName)
    {
        this.newNickName = newNickName;
    }
    
}
