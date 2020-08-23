package com.yuqiaotech.sysadmin.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    
    private Long id;
    
    private String username;
    
    private String authority;
    
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
     * 用户。
     * @return
     */
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    /**
     * 授权。
     * @return
     */
    public String getAuthority()
    {
        return authority;
    }
    
    public void setAuthority(String authority)
    {
        this.authority = authority;
    }
    
}
