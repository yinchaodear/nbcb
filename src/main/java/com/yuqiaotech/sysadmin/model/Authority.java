package com.yuqiaotech.sysadmin.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 授权。
 *
 */
@Entity
public class Authority implements GrantedAuthority, Serializable
{
    
    private Long id;
    
    private String category1;
    
    private String category2;
    
    private String category3;
    
    private String authority;
    
    private String description;
    
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
     * 角色。
     */
    public String getAuthority()
    {
        return authority;
    }
    
    public void setAuthority(String authority)
    {
        this.authority = authority;
    }
    
    /**
     * 描述。
     * @return
     */
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getCategory1()
    {
        return category1;
    }
    
    public void setCategory1(String category1)
    {
        this.category1 = category1;
    }
    
    public String getCategory2()
    {
        return category2;
    }
    
    public void setCategory2(String category2)
    {
        this.category2 = category2;
    }
    
    public String getCategory3()
    {
        return category3;
    }
    
    public void setCategory3(String category3)
    {
        this.category3 = category3;
    }
    
}
