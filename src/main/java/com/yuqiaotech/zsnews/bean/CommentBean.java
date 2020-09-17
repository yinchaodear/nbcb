package com.yuqiaotech.zsnews.bean;

import com.yuqiaotech.zsnews.model.Comment;

public class CommentBean extends Comment
{
    private String channelid;//问题对应的小组
    
    private String channeltitle;
    
    private String categorys;//问题对应的小组分类
    
    private String newstitle;//问题标题
    
    private String categoryid;
    
    private Long newsid;
    
    private Long commentid;
    
    public String getChannelid()
    {
        return channelid;
    }
    
    public void setChannelid(String channelid)
    {
        this.channelid = channelid;
    }
    
    public String getChanneltitle()
    {
        return channeltitle;
    }
    
    public void setChanneltitle(String channeltitle)
    {
        this.channeltitle = channeltitle;
    }
    
    public String getCategorys()
    {
        return categorys;
    }
    
    public void setCategorys(String categorys)
    {
        this.categorys = categorys;
    }
    
    public String getNewstitle()
    {
        return newstitle;
    }
    
    public void setNewstitle(String newstitle)
    {
        this.newstitle = newstitle;
    }
    
    public String getCategoryid()
    {
        return categoryid;
    }
    
    public void setCategoryid(String categoryid)
    {
        this.categoryid = categoryid;
    }
    
    public Long getNewsid()
    {
        return newsid;
    }
    
    public void setNewsid(Long newsid)
    {
        this.newsid = newsid;
    }
    
    public Long getCommentid()
    {
        return commentid;
    }
    
    public void setCommentid(Long commentid)
    {
        this.commentid = commentid;
    }
    
}
