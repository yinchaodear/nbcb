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
import com.yuqiaotech.sysadmin.model.User;

/**
 * 频道。
 * 商户号，企业号，小组等都放这吧。通过类型来区分。
 */
@Entity
public class Channel extends BaseModel
{
    private static final long serialVersionUID = -6158563579216142351L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String kind; //频道  浙商号 小组 
    
    private String type; //浙商号里面分为(商会号,企业号,媒体号,个人号,)  小组里面(科技，农业之类的)
    
    private String category;//小组 专用的字段  属于 科技，还是教育
    
    @javax.persistence.Column(columnDefinition = "text")
    private String logo;//如果是浙商号，需要logo，个人也是有logo的
    
    private String title;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_column_id")
    private Column column;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_user_id")
    private User user;//属于哪个运营人员
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_userinfo_id")
    private UserInfo userinfo;//属于哪个app用户
    
    private String remark;//频道的描述
    
    private Integer displayOrder;//
    
    private Integer status;//0：上架状态  1：下架状态
    
    private String h5href;//外部链接地址
    
    private Integer deltag;//删除标识
    
    private Integer showtag;//默认显示（0：显示；1：不显示）
    
    private Integer collects;//关注人数
    private Integer newscount;//文章数量
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
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
     * logo。
     * @return
     */
    public String getLogo()
    {
        return logo;
    }
    
    public void setLogo(String logo)
    {
        this.logo = logo;
    }
    
    /**
     * 栏目。
     * @return
     */
    public Column getColumn()
    {
        return column;
    }
    
    public void setColumn(Column column)
    {
        this.column = column;
    }
    
    public String getKind()
    {
        return kind;
    }
    
    public void setKind(String kind)
    {
        this.kind = kind;
    }
    
    public User getUser()
    {
        return user;
    }
    
    public void setUser(User user)
    {
        this.user = user;
    }
    
    public UserInfo getUserinfo()
    {
        return userinfo;
    }
    
    public void setUserinfo(UserInfo userinfo)
    {
        this.userinfo = userinfo;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    /**
     * 顺序号
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
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getH5href()
    {
        return h5href;
    }
    
    public void setH5href(String h5href)
    {
        this.h5href = h5href;
    }
    
    public Integer getDeltag()
    {
        return deltag;
    }
    
    public void setDeltag(Integer deltag)
    {
        this.deltag = deltag;
    }
    
    public Integer getShowtag()
    {
        return showtag;
    }
    
    public void setShowtag(Integer showtag)
    {
        this.showtag = showtag;
    }

	public Integer getCollects() {
		return collects;
	}

	public void setCollects(Integer collects) {
		this.collects = collects;
	}

	public Integer getNewscount() {
		return newscount;
	}

	public void setNewscount(Integer newscount) {
		this.newscount = newscount;
	}
    
    
    
}
