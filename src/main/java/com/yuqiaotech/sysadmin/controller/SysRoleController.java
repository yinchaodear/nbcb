package com.yuqiaotech.sysadmin.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yuqiaotech.common.logging.annotation.Logging;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.dao.PaginationSupport;
import com.yuqiaotech.common.web.domain.request.PageDomain;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;
import com.yuqiaotech.sysadmin.model.Authority;

@RestController
@RequestMapping("system/role")
public class SysRoleController extends BaseController
{
    
    /**
     * Describe: 基础路径
     * */
    private static String MODULE_PATH = "system/role/";
    
    /**
     * Describe: 角色模块服务
     * */
    @Autowired
    private BaseRepository<Authority, Long> authorityRepository;
    
    /**
     * Describe: 获取角色列表视图
     * Param ModelAndView
     * Return 用户列表视图
     * */
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    /**
     * Describe: 获取角色列表数据
     * Param SysRole PageDomain
     * Return 角色列表数据
     * */
    @GetMapping("data")
    //    @PreAuthorize("hasPermission('/system/role/data','sys:role:data')")
    public ResultTable data(Authority authority, PageDomain pageDomain)
    {
        DetachedCriteria dc = composeDetachedCriteria(authority);
        PaginationSupport ps = authorityRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    public DetachedCriteria composeDetachedCriteria(Authority authority)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(Authority.class);
        if (authority != null)
        {
            if (authority.getId() != null)
            {
                dc.add(Restrictions.eq("id", authority.getId()));
            }
            if (StringUtils.isNotEmpty(authority.getAuthority()))
            {
                dc.add(Restrictions.ilike("authority", authority.getAuthority(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotEmpty(authority.getDescription()))
            {
                dc.add(Restrictions.ilike("description", authority.getDescription(), MatchMode.ANYWHERE));
            }
        }
        return dc;
    }
    
    /**
     * Describe: 获取角色新增视图
     * Param ModelAndView
     * Return 角色新增视图
     * */
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
        return JumpPage(MODULE_PATH + "add");
    }
    
    /**
     * Describe: 保存角色信息
     * Param SysRole
     * Return 执行结果
     * */
    @PostMapping("save")
    public Result save(@RequestBody Authority authority)
    {
        authorityRepository.save(authority);
        return decide(true);
    }
    
    /**
     * Describe: 获取角色修改视图
     * Param ModelAndView
     * Return 角色修改视图
     * */
    @GetMapping("edit")
    public ModelAndView edit(ModelAndView modelAndView, Long id)
    {
        modelAndView.addObject("sysRole", authorityRepository.get(id, Authority.class));
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    /**
     * Describe: 修改角色信息
     * Param SysRole
     * Return 执行结果
     * */
    @PutMapping("update")
    public Result update(@RequestBody Authority authority)
    {
        authorityRepository.update(authority);
        return decide(true);
    }
    
    /**
     * Describe: 用户删除接口
     * Param: id
     * Return: ResuBean
     * */
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除角色")
    public Result remove(@PathVariable Long id)
    {
        authorityRepository.remove(id, Authority.class);
        return decide(true);
    }
    
    /**
     * Describe: 用户删除接口
     * Param: id
     * Return: ResuBean
     * */
    @DeleteMapping("batchRemove/{ids}")
    @Logging(title = "批量删除角色")
    public Result batchRemove(@PathVariable String ids)
    {
        if (StringUtils.isNotEmpty(ids))
        {
            List<Long> cdids =
                Arrays.asList(ids.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            DetachedCriteria dc = DetachedCriteria.forClass(Authority.class);
            dc.add(Restrictions.in("id", cdids));
            List<Authority> authorityList = authorityRepository.findByCriteria(dc);
            authorityList.forEach(authorityRepository::delete);
            return decide(true);
        }
        return decide(false);
    }
}
