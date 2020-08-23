package com.yuqiaotech.sysadmin.model;

/**
 * 作为用户必然有各种各样的属性，在本认证系统里本接口定义的方法是必须的。
 * 
 */
public interface UserDetails
{
    /**
     * 用户拥有的授权。
     * @return
     */
    public abstract String[] getUserAuthorities();
    
    public boolean hasAuthority(GrantedAuthority authority);
    
    public boolean hasAuthority(String authority);
    
    public abstract java.lang.String getUsername();
    
    public abstract java.lang.String getPassword();
    
    /**
     * 帐户是否不过期。
     * @return
     */
    public abstract boolean isAccountNonExpired();
    
    /**
     * 是否没被锁定。
     * @return
     */
    public abstract boolean isAccountNonLocked();
    
    /**
     * 凭证是否过期。
     * @return
     */
    public abstract boolean isCredentialsNonExpired();
    
    /**
     * 是否可用。
     * @return
     */
    public abstract boolean isEnabled();
}
