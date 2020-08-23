package com.yuqiaotech.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yuqiaotech.common.logging.annotation.Logging;
import com.yuqiaotech.common.logging.enums.BusinessType;
import com.yuqiaotech.common.web.base.BaseController;

@RestController
@RequestMapping
public class EntranceController extends BaseController
{
    
    /**
     * Describe: 获取登录视图
     * Param: ModelAndView
     * Return: 登录视图
     * */
    @GetMapping("login")
    public ModelAndView login()
    {
        return JumpPage("login");
    }
    
    /**
     * Describe: 获取主页视图
     * Param: ModelAndView
     * Return: 登录视图
     * */
    @GetMapping("index")
    @Logging(title = "主页", describe = "返回 Index 主页视图", type = BusinessType.ADD)
    public ModelAndView index(HttpServletRequest request)
    {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails)
        {
            username = ((UserDetails)principal).getUsername();
        }
        else
        {
            username = principal.toString();
        }
        System.out.println("username:" + username);
        return JumpPage("index");
    }
    
    /**
     * Describe: 获取主页视图
     * Param: ModelAndView
     * Return: 主页视图
     * */
    @GetMapping("console")
    public ModelAndView home()
    {
        return JumpPage("console");
    }
    
    /**
     * Describe:无权限页面
     * Return:返回403页面
     * */
    @GetMapping("error/403")
    public ModelAndView noPermission()
    {
        return JumpPage("error/403");
    }
    
    /**
     * Describe:找不带页面
     * Return:返回404页面
     * */
    @GetMapping("error/404")
    public ModelAndView notFound()
    {
        return JumpPage("error/404");
    }
    
    /**
     * Describe:异常处理页
     * Return:返回500界面
     * */
    @GetMapping("error/500")
    public ModelAndView onException()
    {
        return JumpPage("error/500");
    }
}
