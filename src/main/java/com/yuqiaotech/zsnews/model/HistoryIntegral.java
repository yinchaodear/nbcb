package com.yuqiaotech.zsnews.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 历史积分记录。
 */
@Entity
@Table(name = "historyintegral")
public class HistoryIntegral implements Serializable
{
    private static final long serialVersionUID = 964860731718828296L;
    
    private Long id;
    
    private UserInfo userInfo;
    
    private Long integral;
    
    private String type;//积分类型  1：签到
    
    private Date occurTime;//发生时间
    
    private Integer signDays;//连续签到的天数
    
    private Long originalintegral;//变更前积分
    
    private Long persentintergral;//变更后积分
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    @Column(name = "originalintegral")
    public Long getOriginalintegral()
    {
        return originalintegral;
    }
    
    public void setOriginalintegral(Long originalintegral)
    {
        this.originalintegral = originalintegral;
    }
    
    @Column(name = "persentintergral")
    public Long getPersentintergral()
    {
        return persentintergral;
    }
    
    public void setPersentintergral(Long persentintergral)
    {
        this.persentintergral = persentintergral;
    }
    
    /**
     * 用户名
     * @return
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_user_id")
    public UserInfo getUserInfo()
    {
        return userInfo;
    }
    
    public void setUserInfo(UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }
    
    /**
     * 积分
     * @return
     */
    public Long getIntegral()
    {
        return integral;
    }
    
    public void setIntegral(Long integral)
    {
        this.integral = integral;
    }
    
    /**
     * 类型
     * @return
     */
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    /**
     * 发生时间
     * @return
     */
    public Date getOccurTime()
    {
        return occurTime;
    }
    
    public void setOccurTime(Date occurTime)
    {
        this.occurTime = occurTime;
    }
    
    public Integer getSignDays()
    {
        return signDays;
    }
    
    public void setSignDays(Integer signDays)
    {
        this.signDays = signDays;
    }
    
}