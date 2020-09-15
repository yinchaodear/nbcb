package com.yuqiaotech.zsnews.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yuqiaotech.common.web.base.BaseModel;
import com.yuqiaotech.sysadmin.model.User;

/**
 * 新闻。
 * 新闻，投稿，提问。
 * 
 */
@Entity
public class News extends BaseModel
{
    private static final long serialVersionUID = -7738195350953534074L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 发布者
     */
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_user_id")
    private User user;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_userinfo_id")
    private UserInfo userinfo;//前台APP的发布者
    
    private String from; //文章发布方(平台/个人)，如果是平台 取User的信息， 如果是个人app发布 就是取UserInfo的
    
    private String type;//新闻，投稿，提问, 加个政务,(政务活动,政务通告,政务)
    
    private String kind;//属于  政务 /社区/浙商
    
    private String mediaType;//图片、文章(文章1,文章2)、链接、视频
    
    private String title;
    
    @Lob
    private String content;
    
    private String link;//资源链接
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_author_channel_id")
    private Channel authorChannel;//发布在哪个浙商号下，如果为空，默认世界浙商网
    
    private String attaches;//附件
    
    private Integer displayOrder;//小的数字排在前面
    
    private String displaytype;//展现形式，如果是1 就名称加三个图片 如果是2 就是左边是名称 右边一个图  如果是3 就是社区模式的显示  如果是4 就是表示是 图文类型的 ，进的详情页不一样,如果没有图片，存5
    
    private Integer istop;//是否置顶 0：不置顶，1：置顶
    
    private Integer deltag;//删除标识
    
    private Integer status;//
    
    private String checkResult;//不通过意见
    
    private Date checkDate;//审核日期
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_check_user_id")
    private User checkUser;//审核人
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public User getUser()
    {
        return user;
    }
    
    public void setUser(User user)
    {
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
    
    public UserInfo getUserinfo()
    {
        return userinfo;
    }
    
    public void setUserinfo(UserInfo userinfo)
    {
        this.userinfo = userinfo;
    }
    
    public String getFrom()
    {
        return from;
    }
    
    public void setFrom(String from)
    {
        this.from = from;
    }
    
    public String getKind()
    {
        return kind;
    }
    
    public void setKind(String kind)
    {
        this.kind = kind;
    }
    
    /**
     * 新闻作者
     * @return
     */
    public Channel getAuthorChannel()
    {
        return authorChannel;
    }
    
    public void setAuthorChannel(Channel authorChannel)
    {
        this.authorChannel = authorChannel;
    }
    
    public String getDisplaytype()
    {
        return displaytype;
    }
    
    public void setDisplaytype(String displaytype)
    {
        this.displaytype = displaytype;
    }
    
    public Integer getIstop()
    {
        return istop;
    }
    
    public void setIstop(Integer istop)
    {
        this.istop = istop;
    }
    
    public Integer getDeltag()
    {
        return deltag;
    }
    
    public void setDeltag(Integer deltag)
    {
        this.deltag = deltag;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getCheckResult()
    {
        return checkResult;
    }
    
    public void setCheckResult(String checkResult)
    {
        this.checkResult = checkResult;
    }
    
    public Date getCheckDate()
    {
        return checkDate;
    }
    
    public void setCheckDate(Date checkDate)
    {
        this.checkDate = checkDate;
    }
    
    public User getCheckUser()
    {
        return checkUser;
    }
    
    public void setCheckUser(User checkUser)
    {
        this.checkUser = checkUser;
    }
}
