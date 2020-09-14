package com.yuqiaotech.zsnews.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * 新闻和频道的关联表
 */
@Entity
public class NewsChannel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_news_id")
    private News news;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_channel_id")
    private Channel channel;
    
    /**
     * id
     * @return
     */
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * 新闻
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
     * 频道
     * @return
     */
    public Channel getChannel()
    {
        return channel;
    }
    
    public void setChannel(Channel channel)
    {
        this.channel = channel;
    }
}
