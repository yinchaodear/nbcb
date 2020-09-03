package com.yuqiaotech.zsnews.model;

import com.yuqiaotech.common.web.base.BaseModel;
import com.yuqiaotech.sysadmin.model.User;

import javax.persistence.*;

/**
 * 频道。
 * 商户号，企业号，小组等都放这吧。通过类型来区分。
 */
@Entity
public class Channel extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kind; //频道  浙商号 小组 
    private String type; //浙商号里面分为(商会号,企业号,媒体号,个人号,)  小组里面(科技，农业之类的)
    private String logo;
    private String title;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="f_column_id")
    private Column column;
    private String description;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="f_user_id")
    private User user;//属于哪个运营人员

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="f_userinfo_id")
    private UserInfo userinfo;//属于哪个app用户
    
    private String remark;//频道的描述
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 标题。
     * @return
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 类型。
     * @return
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * logo。
     * @return
     */
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 栏目。
     * @return
     */
    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    /**
     * 描述。
     * @return
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
    
}
