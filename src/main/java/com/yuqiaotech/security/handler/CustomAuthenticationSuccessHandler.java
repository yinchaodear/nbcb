package com.yuqiaotech.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yuqiaotech.common.SysConstants;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.security.domain.SecurityUserDetails;

/**
 * Describe: 自定义 Security 用户未登陆处理类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
    
    public static final Logger log = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
        Authentication authentication)
        throws IOException, ServletException
    {
        Result resuBean = new Result();
        resuBean.setSuccess(true);
        resuBean.setMsg("登陆成功");
        resuBean.setCode(200);
        httpServletRequest.getSession()
            .setAttribute(SysConstants.SECURITY_CONTEXT_KEY, (SecurityUserDetails)authentication.getPrincipal());
        httpServletRequest.getSession()
            .setAttribute(SysConstants.SECURITY_USERNAME_KEY,
                ((SecurityUserDetails)authentication.getPrincipal()).getUsername());
        httpServletRequest.getSession()
            .setAttribute(SysConstants.SECURITY_USERID_KEY,
                ((SecurityUserDetails)authentication.getPrincipal()).getId());
        httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(resuBean));
        
    }
}
