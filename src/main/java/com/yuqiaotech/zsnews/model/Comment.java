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
 * 评论。
 * 评论，回复等。
 */
@Entity
public class Comment extends BaseModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_user_info_id")
    private UserInfo userInfo;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_news_id")
    private News news;
    
    /**
     * type 类型： 点赞|收藏|评论|回复（针对评论，回复）
     *  回复 评论回复的情况下 记录评论以及回复的用户 用answerUserInfo记录
     */
    private String type;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_answer_user_id")
    private UserInfo answerUser;
    
    /**
     * 对评论的回复
     */
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_comment_id")
    private Comment comment;
    
    private String content;
    
    private Integer status;//评论的状态
    
    private Integer deltag;//删除标识
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * 回复人
     * @return
     */
    public UserInfo getUserInfo()
    {
        return userInfo;
    }
    
    public void setUserInfo(UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }
    
    /**
     * 问题标题
     * @return
     */
    public News getNews()
    {
        return news;
    }
    
    public void setNews(News news)
    {
        this.news = news;
    }
    
    /**
     * 回复内容
     * @return
     */
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    /**
     * 追评评论
     * @return
     */
    public Comment getComment()
    {
        return comment;
    }
    
    public void setComment(Comment comment)
    {
        this.comment = comment;
    }
    
    /**
     * 类型
     * @return
     */
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    /**
     * 评论目标用户
     * @return
     */
    public UserInfo getAnswerUser()
    {
        return answerUser;
    }
    
    public void setAnswerUser(UserInfo answerUser)
    {
        this.answerUser = answerUser;
    }
    
    /**
     * 状态
     * @return
     */
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    /**
     * 是否删除
     * @return
     */
    public Integer getDeltag()
    {
        return deltag;
    }
    
    public void setDeltag(Integer deltag)
    {
        this.deltag = deltag;
    }
}
