package com.yuqiaotech.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yuqiaotech.common.web.domain.response.Result;

/**
 * Describe: 自定义 Security 用户登录失败处理类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler
{
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
        AuthenticationException e)
        throws IOException, ServletException
    {
        httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        Result resuBean = new Result();
        resuBean.setCode(500);
        resuBean.setSuccess(false);
        resuBean.setMsg("登录失败");
        if (e instanceof UsernameNotFoundException)
        {
            if("third_add_mobile".equals(e.getMessage())){
                resuBean.setMsg("三方第一次登录，需要补充手机号。");
                System.out.println("========gws===============三方第一次登录，需要补充手机号。");
                resuBean.setData("gotoRegisterThird");
                resuBean.setCode(200);
            }else {
                resuBean.setMsg("用户名不存在");
            }
            httpServletResponse.getWriter().write(JSON.toJSONString(resuBean));
            return;
        }
        if (e instanceof LockedException)
        {
            resuBean.setMsg("用户冻结");
            httpServletResponse.getWriter().write(JSON.toJSONString(resuBean));
            return;
        }
        if (e instanceof BadCredentialsException)
        {
            resuBean.setMsg("账户密码不正确");
            httpServletResponse.getWriter().write(JSON.toJSONString(resuBean));
            return;
        }
        httpServletResponse.getWriter().write(JSON.toJSONString(resuBean));
    }
}
