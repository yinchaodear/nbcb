package com.yuqiaotech.zsnews.bean;

import com.yuqiaotech.zsnews.model.Channel;

public class ChannelBean extends Channel
{
    private Long columnid;
    
    private String categoryids;
    
    private String categorys;
    
    private String objectId;
    
    private String objectType;
    
    private String picname1;
    
    public Long getColumnid()
    {
        return columnid;
    }
    
    public void setColumnid(Long columnid)
    {
        this.columnid = columnid;
    }
    
    public String getCategoryids()
    {
        return categoryids;
    }
    
    public void setCategoryids(String categoryids)
    {
        this.categoryids = categoryids;
    }
    
    public String getCategorys()
    {
        return categorys;
    }
    
    public void setCategorys(String categorys)
    {
        this.categorys = categorys;
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
}
