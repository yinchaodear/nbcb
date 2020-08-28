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
 * 新闻。
 * 新闻，投稿，提问。
 */
@Entity
public class News extends BaseModel
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String type;//新闻，投稿，提问
    
    private String mediaType;//图片、文章、链接、视频
    
    private String title;
    
    private String content;
    
    private String link;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_channel_id")
    private Channel channel;
    
    private String attaches;//附件
    
    private Integer displayOrder;//小的数字排在前面
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * 类型。
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
     * 多媒体类型。
     * @return
     */
    public String getMediaType()
    {
        return mediaType;
    }
    
    public void setMediaType(String mediaType)
    {
        this.mediaType = mediaType;
    }
    
    /**
     * 标题。
     * @return
     */
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    /**
     * 内容。
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
     * 链接地址。
     * @return
     */
    public String getLink()
    {
        return link;
    }
    
    public void setLink(String link)
    {
        this.link = link;
    }
    
    /**
     * 频道。
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
    
    /**
     * 附件路径。
     */
    public String getAttaches()
    {
        return attaches;
    }
    
    public void setAttaches(String attaches)
    {
        this.attaches = attaches;
    }
    
    /**
     * 顺序号。
     * @return
     */
    public Integer getDisplayOrder()
    {
        return displayOrder;
    }
    
    public void setDisplayOrder(Integer displayOrder)
    {
        this.displayOrder = displayOrder;
    }
}
