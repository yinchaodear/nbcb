package com.yuqiaotech.zsnews.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yuqiaotech.sysadmin.model.User;

/**
 * 用户信息审核历史表
 */
@Entity
public class UserInfoCheck
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userinfoId;//是哪个用户
    
    private String username;//提交审核的用户名
    
    private String nickName;//提交审核的昵称
    
    private String avatar;//提交审核的头像base64
    
    private String remark;//提交审核的个人的一些详细说明
    
    private Integer checkResult;//0:通过、1:不通过
    
    private String checkMsg;//不通过的原因
    
    private Date checkTime;//审核时间
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_user_id")
    private User checker;//审核人
    
    public Integer getCheckResult()
    {
        return checkResult;
    }
    
    public void setCheckResult(Integer checkResult)
    {
        this.checkResult = checkResult;
    }
    
    public String getCheckMsg()
    {
        return checkMsg;
    }
    
    public void setCheckMsg(String checkMsg)
    {
        this.checkMsg = checkMsg;
    }
    
    public Date getCheckTime()
    {
        return checkTime;
    }
    
    public void setCheckTime(Date checkTime)
    {
        this.checkTime = checkTime;
    }
    
    public User getChecker()
    {
        return checker;
    }
    
    public void setChecker(User checker)
    {
        this.checker = checker;
    }
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public Long getUserinfoId()
    {
        return userinfoId;
    }
    
    public void setUserinfoId(Long userinfoId)
    {
        this.userinfoId = userinfoId;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getAvatar()
    {
        return avatar;
    }
    
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getNickName()
    {
        return nickName;
    }
    
    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }
}
