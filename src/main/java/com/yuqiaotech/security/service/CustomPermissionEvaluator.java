package com.yuqiaotech.security.service;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.yuqiaotech.security.domain.SecurityUserDetails;

/**
 * Describe: 自定义 Security 权限注解实现
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator
{
    
    /**
     * Describe: 自定义 Security 权限认证 @HasPermission
     * Param: Authentication
     * Return Boolean
     * */
    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1)
    {
        // 根据用户账户查询权限信息
        SecurityUserDetails securityUserDetails = (SecurityUserDetails)authentication.getPrincipal();
        
        // 根据用户账户查询权限信息
        //        List<SysPower> powerList = sysPowerMapper.selectByUsername(securityUserDetails.getUsername());
        //        
        //        Set<String> permissions = new HashSet<>();
        //        
        //        for (SysPower sysPower : powerList)
        //        {
        //            
        //            permissions.add(sysPower.getPowerCode());
        //        }
        //        
        //        if (permissions.contains(o1))
        //        {
        //            return true;
        //        }
        //        else
        //        {
        return false;
        //        }
    }
    
    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o)
    {
        return false;
    }
}
