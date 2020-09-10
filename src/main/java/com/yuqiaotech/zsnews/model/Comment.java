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

    /**
     * type 类型： 点赞|收藏|评论|回复（针对评论，回复）
     *  回复 评论回复的情况下 记录评论以及回复的用户 用answerUserInfo记录
     */
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_answer_user_id")
    private UserInfo answerUser;

    /**
     * 对评论的回复
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_comment_id")
    private Comment comment;
    
    private String content;
    
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserInfo getAnswerUser() {
        return answerUser;
    }

    public void setAnswerUser(UserInfo answerUser) {
        this.answerUser = answerUser;
    }
}
