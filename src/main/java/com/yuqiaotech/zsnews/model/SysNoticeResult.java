package com.yuqiaotech.zsnews.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yuqiaotech.common.web.base.BaseModel;

/**
 * 消息送达表
 * 定时任务在发送消息的时候需要把消息落入到这个表里
 */
@Entity
public class SysNoticeResult extends BaseModel
{
    private static final long serialVersionUID = -7895546481243115433L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_sys_notice_id")
    private SysNotice sysNotice;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_user_info_id")
    private UserInfo userInfo;
    
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
     * 消息
     * @return
     * 
     */
    public SysNotice getSysNotice()
    {
        return sysNotice;
    }
    
    public void setSysNotice(SysNotice sysNotice)
    {
        this.sysNotice = sysNotice;
    }
    
    /**
     * 接收人
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
}
