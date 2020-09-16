package com.yuqiaotech.zsnews.bean;

import com.yuqiaotech.zsnews.model.News;

public class NewsBean extends News
{
    private String columns;//所属栏目
    
    private String channels;//所属频道
    
    private String topics;//所属专题
    
    private String categoryid;
    
    private String categorys;//展示分类
    
    private String objectId;
    
    private String objectType;
    
    private String picname1;
    
    private String picname2;
    
    private String picname3;
    
    private String channelid;//存政务发布频道
    
    private String channeltitle;
    
    private String topcheck;
    
    public String getColumns()
    {
        return columns;
    }
    
    public void setColumns(String columns)
    {
        this.columns = columns;
    }
    
    public String getChannels()
    {
        return channels;
    }
    
    public void setChannels(String channels)
    {
        this.channels = channels;
    }
    
    public String getTopics()
    {
        return topics;
    }
    
    public void setTopics(String topics)
    {
        this.topics = topics;
    }
    
    public String getObjectId()
    {
        return objectId;
    }
    
    public void setObjectId(String objectId)
    {
        this.objectId = objectId;
    }
    
    public String getObjectType()
    {
        return objectType;
    }
    
    public void setObjectType(String objectType)
    {
        this.objectType = objectType;
    }
    
    public String getPicname1()
    {
        return picname1;
    }
    
    public void setPicname1(String picname1)
    {
        this.picname1 = picname1;
    }
    
    public String getPicname2()
    {
        return picname2;
    }
    
    public void setPicname2(String picname2)
    {
        this.picname2 = picname2;
    }
    
    public String getPicname3()
    {
        return picname3;
    }
    
    public void setPicname3(String picname3)
    {
        this.picname3 = picname3;
    }
    
    public String getChannelid()
    {
        return channelid;
    }
    
    public void setChannelid(String channelid)
    {
        this.channelid = channelid;
    }
    
    public String getTopcheck()
    {
        return topcheck;
    }
    
    public void setTopcheck(String topcheck)
    {
        this.topcheck = topcheck;
    }
    
    public String getCategoryid()
    {
        return categoryid;
    }
    
    public void setCategoryid(String categoryid)
    {
        this.categoryid = categoryid;
    }
    
    public String getCategorys()
    {
        return categorys;
    }
    
    public void setCategorys(String categorys)
    {
        this.categorys = categorys;
    }
    
    public String getChanneltitle()
    {
        return channeltitle;
    }
    
    public void setChanneltitle(String channeltitle)
    {
        this.channeltitle = channeltitle;
    }
}
