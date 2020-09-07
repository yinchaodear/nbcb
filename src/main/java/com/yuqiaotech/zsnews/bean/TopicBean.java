package com.yuqiaotech.zsnews.bean;

import com.yuqiaotech.zsnews.model.Topic;

public class TopicBean extends Topic
{
    private Long channelId;
    
    public Long getChannelId()
    {
        return channelId;
    }
    
    public void setChannelId(Long channelId)
    {
        this.channelId = channelId;
    }
    
}
