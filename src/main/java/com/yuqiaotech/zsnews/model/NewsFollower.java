package com.yuqiaotech.zsnews.model;

import com.yuqiaotech.common.web.base.BaseModel;

import javax.persistence.*;

/**
 * 新闻关注。
 *
 */
@Entity
public class NewsFollower extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="f_news_id")
    private News news;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="f_app_user_id")
    private AppUser appUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 新闻。
     * @return
     */
    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    /**
     * 用户。
     * @return
     */
    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
