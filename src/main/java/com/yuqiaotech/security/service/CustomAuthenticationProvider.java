package com.yuqiaotech.security.service;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.yuqiaotech.security.domain.SecurityUserDetailsService;

/**
 * Describe: 自定义 Security 登陆认证实现
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider
{
    
    @Resource
    private SecurityUserDetailsService securityUserDetailsService;
    
    @Resource
    private PasswordEncoder passwordEncoder;
    
    /**
     * Describe: 自定义 Security 登陆逻辑
     * Param: Authentication
     * Return Authentication
     * */
    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException
    {
        
        String username = (String)authentication.getPrincipal();
        String password = (String)authentication.getCredentials();
        
        UserDetails userInfo = securityUserDetailsService.loadUserByUsername(username);
        
        if (!passwordEncoder.matches(password, userInfo.getPassword()))
        {
            throw new BadCredentialsException(" Password Not Found ");
        }
        return new UsernamePasswordAuthenticationToken(userInfo, password, userInfo.getAuthorities());
    }
    
    @Override
    public boolean supports(Class<?> aClass)
    {
        return true;
    }
}
