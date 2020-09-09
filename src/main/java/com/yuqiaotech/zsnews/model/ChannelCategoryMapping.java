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
 * channel与category关联关系表
 * 主要用于浙商号频道于分类的关联关系
 */
@Entity
public class ChannelCategoryMapping extends BaseModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_channel_id")
    private Channel channel;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_category_id")
    private Category category;
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public Channel getChannel()
    {
        return channel;
    }
    
    public void setChannel(Channel channel)
    {
        this.channel = channel;
    }
    
    public Category getCategory()
    {
        return category;
    }
    
    public void setCategory(Category category)
    {
        this.category = category;
    }
}
