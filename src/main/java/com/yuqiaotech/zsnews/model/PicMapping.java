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
 * 这个是图片关联表
 */
@Entity
public class PicMapping extends BaseModel
{
    private static final long serialVersionUID = -2325380076030874383L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_material_id")
    private Material material;//素材和图片的关联关系
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_news_id")
    private News news;
    
    private Integer displayOrder;//顺序号，由小到大
    
    private String picpath;//图片地址
    
    private String h5href;//图片对应链接
    
    private String pictext;//图片描述
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public Material getMaterial()
    {
        return material;
    }
    
    public void setMaterial(Material material)
    {
        this.material = material;
    }
    
    public News getNews()
    {
        return news;
    }
    
    public void setNews(News news)
    {
        this.news = news;
    }
    
    public Integer getDisplayOrder()
    {
        return displayOrder;
    }
    
    public void setDisplayOrder(Integer displayOrder)
    {
        this.displayOrder = displayOrder;
    }
    
    public String getPicpath()
    {
        return picpath;
    }
    
    public void setPicpath(String picpath)
    {
        this.picpath = picpath;
    }
    
    public String getH5href()
    {
        return h5href;
    }
    
    public void setH5href(String h5href)
    {
        this.h5href = h5href;
    }
    
    public String getPictext()
    {
        return pictext;
    }
    
    public void setPictext(String pictext)
    {
        this.pictext = pictext;
    }
}
