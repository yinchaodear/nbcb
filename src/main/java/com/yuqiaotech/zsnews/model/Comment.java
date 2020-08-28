package com.yuqiaotech.zsnews.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.yuqiaotech.common.web.base.BaseModel;

/**
 * 评论。
 * 评论，回复等。
 */
@Entity
public class Comment extends BaseModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_user_info_id")
    private UserInfo userInfo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_news_id")
    private News news;
    
    private String comment;
    
    private String type;//类型到底是评论还是回答
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public UserInfo getUserInfo()
    {
        return userInfo;
    }
    
    public void setUserInfo(UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }
    
    public News getNews()
    {
        return news;
    }
    
    public void setNews(News news)
    {
        this.news = news;
    }
    
    public String getComment()
    {
        return comment;
    }
    
    public void setComment(String comment)
    {
        this.comment = comment;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
	
		this.type = type;
	}
    
}
