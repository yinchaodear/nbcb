package com.yuqiaotech.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yuqiaotech.common.logging.annotation.Logging;
import com.yuqiaotech.common.logging.enums.BusinessType;
import com.yuqiaotech.common.tools.common.CollectionUtils;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.sysadmin.model.Authority;
import com.yuqiaotech.sysadmin.model.ProtectedResource;
import com.yuqiaotech.sysadmin.model.ProtectedResourceAuthority;
import com.yuqiaotech.sysadmin.model.User;
import com.yuqiaotech.zsnews.NewsDicConstants;

@RestController
@RequestMapping
public class EntranceController extends BaseController
{
    @Autowired
    private BaseRepository<User, Long> userRepository;
    
    @Autowired
    private BaseRepository<Authority, Long> authorityRepository;
    
    @Autowired
    private BaseRepository<ProtectedResource, Long> protectedResourceRepository;
    
    @Autowired
    private BaseRepository<ProtectedResourceAuthority, Long> protectedResourceAuthorityRepository;
    
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
        
        User currentUser = getCurrentUser();
        List<String> rlist = new ArrayList<>();
        if (StringUtils.isNotEmpty(currentUser.getRoleids()))
        {
            Authority sysrole =
                authorityRepository.findUniqueBy("id", Long.valueOf(currentUser.getRoleids()), Authority.class);
            if (sysrole != null && sysrole.getDeltag() == NewsDicConstants.ICommon.DELETE_NO)
            {
                List<ProtectedResourceAuthority> pralist = protectedResourceAuthorityRepository
                    .findByHql("from ProtectedResourceAuthority where authority.id=" + sysrole.getId());
                
                for (ProtectedResourceAuthority protectedResourceAuthority : pralist)
                {
                    rlist.add(protectedResourceAuthority.getProtectedResource().getPatternStr());
                }
            }
        }
        getSession().setAttribute("resourceids", CollectionUtils.join(rlist, ","));
        System.out.println("username:" + username);
        System.out.println("resourceids:" + CollectionUtils.join(rlist, ","));
        
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
