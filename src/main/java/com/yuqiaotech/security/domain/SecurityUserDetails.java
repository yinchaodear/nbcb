package com.yuqiaotech.security.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.yuqiaotech.sysadmin.model.User;

public class SecurityUserDetails extends User implements UserDetails
{
    private static final long serialVersionUID = 939884680001558121L;
    
    @Override
    public String getPassword()
    {
        return super.getPassword();
    }
    
    @Override
    public String getUsername()
    {
        return super.getUsername();
    }
    
    @Override
    public boolean isAccountNonExpired()
    {
        return false;
    }
    
    @Override
    public boolean isAccountNonLocked()
    {
        return false;
    }
    
    @Override
    public boolean isCredentialsNonExpired()
    {
        return false;
    }
    
    @Override
    public boolean isEnabled()
    {
        return false;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }
    
}
