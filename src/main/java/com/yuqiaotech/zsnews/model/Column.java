package com.yuqiaotech.zsnews.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.yuqiaotech.common.web.base.BaseModel;

/**
 * 栏目。
 * 目前是固定的几个，是APP下面的tab。
 */
@Entity
public class Column extends BaseModel
{
    private static final long serialVersionUID = -4466172520715268788L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    private String remark;
    
    private Integer status;//0：上架状态  1：下架状态
    
    private String h5href;//外部链接地址
    
    private Integer deltag;//删除标识
    
    private Integer displayOrder;//顺序号，顺序越小越靠前
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * 名称
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
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getH5href()
    {
        return h5href;
    }
    
    public void setH5href(String h5href)
    {
        this.h5href = h5href;
    }
    
    public Integer getDeltag()
    {
        return deltag;
    }
    
    public void setDeltag(Integer deltag)
    {
        this.deltag = deltag;
    }
    
    public Integer getDisplayOrder()
    {
        return displayOrder;
    }
    
    public void setDisplayOrder(Integer displayOrder)
    {
        this.displayOrder = displayOrder;
    }
}
