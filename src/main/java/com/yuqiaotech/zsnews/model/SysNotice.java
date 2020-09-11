package com.yuqiaotech.zsnews.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.yuqiaotech.common.web.base.BaseModel;

/**
 * 系统消息表
 */
@Entity
public class SysNotice extends BaseModel
{
    private static final long serialVersionUID = -969252341954555777L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    private String content;
    
    private Date pubtime;
    
    private Integer type;//消息类型
    
    private Integer way;//推送方式
    
    private Integer status;//状态
    
    private String remark1;
    
    private String remark2;
    
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
     * 标题
     * @return
     */
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    /**
     * 内容
     * @return
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
     * 发布时间
     * @return
     */
    public Date getPubtime()
    {
        return pubtime;
    }
    
    public void setPubtime(Date pubtime)
    {
        this.pubtime = pubtime;
    }
    
    /**
     * 类型
     * @return
     */
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
    
    /**
     * 备用字段1
     * @return
     */
    public String getRemark1()
    {
        return remark1;
    }
    
    public void setRemark1(String remark1)
    {
        this.remark1 = remark1;
    }
    
    /**
     * 备用字段2
     * @return
     */
    public String getRemark2()
    {
        return remark2;
    }
    
    public void setRemark2(String remark2)
    {
        this.remark2 = remark2;
    }
    
    /**
     * 推送形式
     * @return
     */
    public Integer getWay()
    {
        return way;
    }
    
    public void setWay(Integer way)
    {
        this.way = way;
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
