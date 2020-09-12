package com.yuqiaotech.zsnews.model;

import java.io.Serializable;
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
import com.yuqiaotech.zsnews.NewsDicConstants;

/**
 * 用户反馈
 */
@Entity
public class Feedback implements Serializable
{
    private static final long serialVersionUID = 4645234334017964945L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String content;//反馈内容
    
    private String replyContent;//回复反馈的内容
    
    private Date feedbackTime;//反馈时间
    
    private Date replyTime;//回复时间
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_userinfo_id")
    private UserInfo userInfo;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_user_id")
    private User user;
    
    private Integer deltag = NewsDicConstants.ICommon.DELETE_NO;//删除标识
    
    private Integer status = NewsDicConstants.IFeedback.Status.DOING;
    
    /**
     * id
     * @return
     */
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * 反馈内容
     * @return
     * 
     */
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    /**
     * 回复内容
     * @return
     */
    public String getReplyContent()
    {
        return replyContent;
    }
    
    public void setReplyContent(String replyContent)
    {
        this.replyContent = replyContent;
    }
    
    /**
     * 反馈时间
     * @return
     */
    public Date getFeedbackTime()
    {
        return feedbackTime;
    }
    
    public void setFeedbackTime(Date feedbackTime)
    {
        this.feedbackTime = feedbackTime;
    }
    
    /**
     * 回复时间
     * @return
     */
    public Date getReplyTime()
    {
        return replyTime;
    }
    
    public void setReplyTime(Date replyTime)
    {
        this.replyTime = replyTime;
    }
    
    /**
     * 反馈人
     * @return
     */
    public UserInfo getUserInfo()
    {
        return userInfo;
    }
    
    public void setUserInfo(UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }
    
    /**
     * 回复人
     * @return
     */
    public User getUser()
    {
        return user;
    }
    
    public void setUser(User user)
    {
        this.user = user;
    }
    
    /**
     * 删除标识
     * @return
     */
    public Integer getDeltag()
    {
        return deltag;
    }
    
    public void setDeltag(Integer deltag)
    {
        this.deltag = deltag;
    }
    
    /**
     * 状态
     * @return
     */
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
}
