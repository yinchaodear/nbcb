package com.yuqiaotech.zsnews.bean;

import com.yuqiaotech.zsnews.model.Platform;

public class PlatformBean extends Platform
{
    private String objectId;
    
    private String objectType;
    
    private String picname1;
    
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
