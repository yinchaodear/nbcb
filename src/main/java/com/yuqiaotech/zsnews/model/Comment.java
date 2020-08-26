package com.yuqiaotech.zsnews.model;

import com.yuqiaotech.common.web.base.BaseModel;
import com.yuqiaotech.sysadmin.model.User;

import javax.persistence.*;

/**
 * 评论。
 * 评论，回复等。
 */
@Entity
public class Comment extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="f_app_user_id")
    private AppUser appUser;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="f_news_id")
    private News news;
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
