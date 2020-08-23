package com.yuqiaotech.sysadmin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.yuqiaotech.common.web.base.BaseModel;

/**
 * 用户。
 *
 */
@Entity
@Table(name = "app_user")
public class User extends BaseModel implements UserDetails, Serializable
{
    private static final long serialVersionUID = 4944958627776528683L;
    
    private Long id;
    
    private String username;
    
    private String realName;
    
    private String password;
    
    private String firstName;
    
    private String lastName;
    
    private String address;
    
    private String city;
    
    private String province;
    
    private String country;
    
    private String postalCode;
    
    private boolean enabled;
    
    private boolean accountNonExpired;
    
    private boolean accountNonLocked;
    
    private boolean credentialsNonExpired;
    
    private String email;
    
    private String mobile;
    
    private String tel;
    
    private String nationId;
    
    private String[] authorities;
    
    private String about;
    
    private Integer version;
    
    private Organisation organisation;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * 用户名。
     * 
     * @return
     */
    @Column(nullable = false, unique = true)
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    /**
     * 用户姓名。
     * 很多用户对姓名分开填写很是抓狂。
     * @return
     */
    public String getRealName()
    {
        return realName;
    }
    
    public void setRealName(String realName)
    {
        this.realName = realName;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public boolean isEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    
    public boolean isAccountNonExpired()
    {
        return accountNonExpired;
    }
    
    public void setAccountNonExpired(boolean accountNonExpired)
    {
        this.accountNonExpired = accountNonExpired;
    }
    
    public boolean isAccountNonLocked()
    {
        return accountNonLocked;
    }
    
    public void setAccountNonLocked(boolean accountNonLocked)
    {
        this.accountNonLocked = accountNonLocked;
    }
    
    public boolean isCredentialsNonExpired()
    {
        return credentialsNonExpired;
    }
    
    public void setCredentialsNonExpired(boolean credentialsNonExpired)
    {
        this.credentialsNonExpired = credentialsNonExpired;
    }
    
    public String getFirstName()
    {
        return firstName;
    }
    
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    public String getLastName()
    {
        return lastName;
    }
    
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getMobile()
    {
        return mobile;
    }
    
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
    
    public String getTel()
    {
        return tel;
    }
    
    public void setTel(String tel)
    {
        this.tel = tel;
    }
    
    public String getNationId()
    {
        return nationId;
    }
    
    public void setNationId(String nationId)
    {
        this.nationId = nationId;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_organisation_id")
    public Organisation getOrganisation()
    {
        return organisation;
    }
    
    public void setOrganisation(Organisation organisation)
    {
        this.organisation = organisation;
    }
    
    @Transient
    public String getFullName()
    {
        return (getLastName() == null ? "" : getLastName()) + (getFirstName() == null ? "" : getFirstName());
    }
    
    /**
     * 授权。
     * 这个是个在认证过程中比较重要的内容，一个依据。
     * 
     */
    @Transient
    public String[] getUserAuthorities()
    {
        return authorities;
    }
    
    public void setUserAuthorities(String[] authorities)
    {
        this.authorities = authorities;
    }
    
    /**
     * 是否拥有某个授权。
     * @param authority
     * @return
     */
    @Transient
    public boolean hasAuthority(GrantedAuthority authority)
    {
        if (authorities == null)
            return false;
        return hasAuthority(authority.getAuthority());
    }
    
    @Transient
    public boolean hasAuthority(String authority)
    {
        if (authorities == null)
            return false;
        for (int i = 0; i < authorities.length; i++)
        {
            if (authority.equalsIgnoreCase(authorities[i]))
                return true;
        }
        return false;
    }
    
    @Version
    public Integer getVersion()
    {
        return version;
    }
    
    public void setVersion(Integer version)
    {
        this.version = version;
    }
    
    /**
     * 关于我。
     * @return
     */
    public String getAbout()
    {
        return about;
    }
    
    public void setAbout(String about)
    {
        this.about = about;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    public String getProvince()
    {
        return province;
    }
    
    public void setProvince(String province)
    {
        this.province = province;
    }
    
    public String getCountry()
    {
        return country;
    }
    
    public void setCountry(String country)
    {
        this.country = country;
    }
    
    public String getPostalCode()
    {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }
    
}
