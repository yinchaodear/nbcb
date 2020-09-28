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
 * 用户和授权的关联表。
 * **因为不希望User和Authority之间建立必然的关系，所以使用了这么一个定义，
 * **而不是在User和Authority建立纯粹的关联表。
 * 
 * 有时授权和资源定义是一一对应的。这时就拿资源的标志作为授权了。
 * 我们约定这类授权方式的授权字符串authority的值为source_sourceId，
 * sourceId是指资源的ID的值。
 *
 */
@Entity
public class UserAuthority implements Serializable
{
    private static final long serialVersionUID = -1754150892633693726L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_user_id")
    private User user;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_authority_id")
    private Authority authority;
    
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
    
    public Authority getAuthority()
    {
        return authority;
    }
    
    public void setAuthority(Authority authority)
    {
        this.authority = authority;
    }
}
