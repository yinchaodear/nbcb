package com.yuqiaotech.zsnews.bean;

import com.yuqiaotech.zsnews.model.NewsForm;

public class NewsFormBean extends NewsForm
{
    private Long newsid;
    
    public Long getNewsid()
    {
        return newsid;
    }
    
    public void setNewsid(Long newsid)
    {
        this.newsid = newsid;
    }
    
}
