package com.yuqiaotech.common.tools.servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yuqiaotech.common.SysConstants;

/**
 * Describe: Servlet 工具类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */

public class ServletUtil
{
    
    /**
     * Describe: 获取 HttpServletRequest 对象
     * Param null
     * Return HttpServletRequest
     * */
    public static HttpServletRequest getRequest()
    {
        ServletRequestAttributes servletRequestAttributes =
            (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest();
    }
    
    /**
     * Describe: 获取 HttpServletResponse 对象
     * Param null
     * Return HttpServletResponse
     * */
    public static HttpServletResponse getResponse()
    {
        ServletRequestAttributes servletRequestAttributes =
            (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getResponse();
    }
    
    /**
     * Describe: 获取 HttpServletSession 对象
     * Param null
     * Return HttpServletSession
     * */
    public static HttpSession getSession()
    {
        ServletRequestAttributes servletRequestAttributes =
            (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest().getSession();
    }
    
    /**
     * 当前用户名
     * @return
     */
    public static String getCurrentUsername()
    {
        Object username = getSession().getAttribute(SysConstants.SECURITY_USERNAME_KEY);
        if (username != null && !"".equals(username))
        {
            return (String)username;
        }
        return null;
    }
    
    /**
     * 当前用户id
     * @return
     */
    public static Long getCurrentUserId()
    {
        Object userid = getSession().getAttribute(SysConstants.SECURITY_USERID_KEY);
        if (userid != null && !"".equals(userid))
        {
            return (Long)userid;
        }
        return null;
    }
    
    /**
     * Describe: 判断是否为 Ajax 请求
     * Param null
     * Return HttpServletSession
     * */
    public static Boolean isAjax(HttpServletRequest request)
    {
        String requestType = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(requestType))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * 获取查询参数
     * */
    public static String getQueryParam()
    {
        return getRequest().getQueryString();
    }
    
    /**
     * 获取请求地址
     * */
    public static String getRequestURI()
    {
        return getRequest().getRequestURI();
    }
    
    /**
     * 获取客户端地址
     * */
    public static String getRemoteHost()
    {
        return getRequest().getRemoteHost();
    }
    
    /**
     * 获取当前请求方法
     * */
    public static String getMethod()
    {
        return getRequest().getMethod();
    }
    
    /**
     * 获取请求头
     * */
    public static String getHeader(String name)
    {
        return getRequest().getHeader(name);
    }
    
    /**
     * 获取 UserAgent
     * */
    public static String getAgent()
    {
        return getHeader("User-Agent");
    }
    
    /**
     * 获取浏览器类型
     * */
    public static String getBrowser()
    {
        String userAgent = getAgent();
        if (userAgent.contains("Firefox"))
        {
            return "火狐浏览器";
        }
        else if (userAgent.contains("Chrome"))
        {
            return "谷歌浏览器";
        }
        else if (userAgent.contains("Trident"))
        {
            return "IE 浏览器";
        }
        else
        {
            return "你用啥浏览器";
        }
    }
    
    /**
     * 获取浏览器类型
     * */
    public static String getSystem()
    {
        String userAgent = getAgent();
        if (userAgent.toLowerCase().contains("windows"))
        {
            return "Windows";
        }
        else if (userAgent.toLowerCase().contains("mac"))
        {
            return "Mac";
        }
        else if (userAgent.toLowerCase().contains("x11"))
        {
            return "Unix";
        }
        else if (userAgent.toLowerCase().contains("android"))
        {
            return "Android";
        }
        else if (userAgent.toLowerCase().contains("iphone"))
        {
            return "IPhone";
        }
        else
        {
            return "UnKnown, More-Info: " + userAgent;
        }
    }
    
    public static String getBody()
    {
        
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {
            br = getRequest().getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
    
}
