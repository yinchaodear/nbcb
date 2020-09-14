package com.yuqiaotech.zsnews.bean;

import java.util.List;

import com.yuqiaotech.zsnews.model.Category;

public class ChannelTree
{
    private Long id;
    
    private String title;
    
    private Integer displayOrder;
    
    private String type;//column,channel,topic,category
    
    private List<ChannelTree> channelList;
    
    private List<ChannelTree> topicList;
    
    private List<Category> categoryList;
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public Integer getDisplayOrder()
    {
        return displayOrder;
    }
    
    public void setDisplayOrder(Integer displayOrder)
    {
        this.displayOrder = displayOrder;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public List<ChannelTree> getChannelList()
    {
        return channelList;
    }
    
    public void setChannelList(List<ChannelTree> channelList)
    {
        this.channelList = channelList;
    }
    
    public List<ChannelTree> getTopicList()
    {
        return topicList;
    }
    
    public void setTopicList(List<ChannelTree> topicList)
    {
        this.topicList = topicList;
    }
    
    public List<Category> getCategoryList()
    {
        return categoryList;
    }
    
    public void setCategoryList(List<Category> categoryList)
    {
        this.categoryList = categoryList;
    }
}
