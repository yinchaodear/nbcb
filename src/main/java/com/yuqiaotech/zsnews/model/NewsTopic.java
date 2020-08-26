package com.yuqiaotech.zsnews.model;

import javax.persistence.*;

/**
 * 新闻和专题。
 * 新闻和专题的关联关系。
 */
@Entity
public class NewsTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="f_news_id")
    private News news;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="f_topic_id")
    private Topic topic;
    private Integer displayOrder;//小的在前

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
     * 专题。
     * @return
     */
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    /**
     * 顺序号。
     * 置顶。
     * @return
     */
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
