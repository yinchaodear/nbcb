package com.yuqiaotech.zsnews.bean;

import com.yuqiaotech.zsnews.model.Channel;

public class ChannelBean extends Channel
{
    private Long columnid;
    
    private String categoryids;
    
    private String categorys;
    
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
}
