package com.yuqiaotech.sysadmin.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 定义什么样的资源需要什么样的授权。
 *
 */
@Entity
public class ProtectedResourceAuthority implements Serializable
{
    private Long id;
    
    private ProtectedResource protectedResource;
    
    private Authority authority;
    
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
     * 受保护的资源。
     * @return
     */
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_protected_resource_id")
    public ProtectedResource getProtectedResource()
    {
        return protectedResource;
    }
    
    public void setProtectedResource(ProtectedResource protectedResource)
    {
        this.protectedResource = protectedResource;
    }
    
    /**
     * 授权字符串。
     * 该字符串可能在Authority里做了定义也可能没做定义。
     * @return
     */
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_authority_id")
    public Authority getAuthority()
    {
        return authority;
    }
    
    public void setAuthority(Authority authority)
    {
        this.authority = authority;
    }
    
}
