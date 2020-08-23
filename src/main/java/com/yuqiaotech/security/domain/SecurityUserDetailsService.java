package com.yuqiaotech.security.domain;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.sysadmin.model.User;

@Component
public class SecurityUserDetailsService implements UserDetailsService
{
    
    @Resource
    private BaseRepository<User, Long> userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String s)
        throws UsernameNotFoundException
    {
        User sysUser = userRepository.findUniqueBy("username", s, User.class);
        
        if (sysUser == null)
        {
            throw new UsernameNotFoundException("Account Not");
        }
        
        SecurityUserDetails securityUserDetails = new SecurityUserDetails();
        securityUserDetails.setId(sysUser.getId());
        securityUserDetails.setUsername(sysUser.getUsername());
        securityUserDetails.setPassword(sysUser.getPassword());
        return securityUserDetails;
    }
}
