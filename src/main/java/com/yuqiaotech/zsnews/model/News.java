package com.yuqiaotech.zsnews.model;

import javax.persistence.*;

import com.yuqiaotech.common.web.base.BaseModel;
import com.yuqiaotech.sysadmin.model.User;

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

    /**
     * 发布者
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_userinfo_id")
    private UserInfo userinfo;
    private String from; //文章发布方(平台/个人)，如果是平台 取User的信息， 如果是个人app发布 就是取UserInfo的
    
    private String type;//新闻，投稿，提问, 加个政务,(政务活动,政务通告,政务)
    
    private String kind;//属于  政务 /社区/浙商
    private String mediaType;//图片、文章、链接、视频
    
    private String title;

    @Lob
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

	public UserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
    
    
}
