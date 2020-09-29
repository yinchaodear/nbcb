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

/**
 * 活动报名
 */
@Entity
public class NewsForm extends BaseModel
{
    private static final long serialVersionUID = 5953501991420287204L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_userinfo_id")
    private UserInfo userInfo;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_news_id")
    private News news;
    
    private String companyname;//企业名称
    
    private String fullname1;//参会人姓名
    
    private String title;//参会人职务
    
    private String mobile1;//参会人手机号码
    
    private String transportation;//交通工具
    
    private String carno;//车牌号
    
    private String fullname2;
    
    private String mobile2;
    
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
    
    public String getCompanyname()
    {
        return companyname;
    }
    
    public void setCompanyname(String companyname)
    {
        this.companyname = companyname;
    }
    
    public String getFullname1()
    {
        return fullname1;
    }
    
    public void setFullname1(String fullname1)
    {
        this.fullname1 = fullname1;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getMobile1()
    {
        return mobile1;
    }
    
    public void setMobile1(String mobile1)
    {
        this.mobile1 = mobile1;
    }
    
    public String getTransportation()
    {
        return transportation;
    }
    
    public void setTransportation(String transportation)
    {
        this.transportation = transportation;
    }
    
    public String getCarno()
    {
        return carno;
    }
    
    public void setCarno(String carno)
    {
        this.carno = carno;
    }
    
    public String getFullname2()
    {
        return fullname2;
    }
    
    public void setFullname2(String fullname2)
    {
        this.fullname2 = fullname2;
    }
    
    public String getMobile2()
    {
        return mobile2;
    }
    
    public void setMobile2(String mobile2)
    {
        this.mobile2 = mobile2;
    }
}
