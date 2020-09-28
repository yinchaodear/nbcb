package com.yuqiaotech.sysadmin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
import com.yuqiaotech.common.web.domain.response.ResuTree;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;
import com.yuqiaotech.sysadmin.bean.ProtectedResourceBean;
import com.yuqiaotech.sysadmin.model.Authority;
import com.yuqiaotech.sysadmin.model.ProtectedResource;
import com.yuqiaotech.sysadmin.model.ProtectedResourceAuthority;
import com.yuqiaotech.zsnews.NewsDicConstants;

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
    
    @Autowired
    private BaseRepository<ProtectedResource, Long> protectedResourceRepository;
    
    @Autowired
    private BaseRepository<ProtectedResourceAuthority, Long> protectedResourceAuthorityRepository;
    
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
    
    @GetMapping("power")
    public ModelAndView power(Model model, Long roleId)
    {
        model.addAttribute("roleId", roleId);
        return JumpPage(MODULE_PATH + "power");
    }
    
    @GetMapping("getRolePower")
    public ResuTree getRolePower(Long roleId)
    {
        List<ProtectedResource> prList = protectedResourceRepository.findByHql("from ProtectedResource");
        List<ProtectedResourceAuthority> pralist = protectedResourceAuthorityRepository
            .findByHql("from ProtectedResourceAuthority where authority.id=" + roleId);
        List<ProtectedResourceBean> prbList = new ArrayList<>();
        Map<String, ProtectedResource> parentMap = new HashMap<>();
        Set<Long> selectAuthorityIdSet = new HashSet<>();
        for (ProtectedResourceAuthority protectedResourceAuthority : pralist)
        {
            selectAuthorityIdSet.add(protectedResourceAuthority.getProtectedResource().getId());
        }
        for (ProtectedResource pr : prList)
        {
            if ("0".equals(pr.getType()))
            {
                parentMap.put(pr.getPatternStr(), pr);
            }
        }
        for (ProtectedResource pr : prList)
        {
            ProtectedResourceBean prb = new ProtectedResourceBean();
            BeanUtils.copyProperties(pr, prb);
            if ("1".equals(pr.getType()))
            {
                String pid = prb.getPatternStr().substring(0, 3);
                if (parentMap.containsKey(pid))
                {
                    prb.setParentId(parentMap.get(pid).getId());
                }
                else
                {
                    prb.setParentId(-1L);
                }
            }
            else
            {
                prb.setParentId(-1L);
            }
            
            if (selectAuthorityIdSet.contains(prb.getId()))
            {
                prb.setCheckArr("1");
            }
            prbList.add(prb);
        }
        
        return dataTree(prbList);
    }
    
    @PutMapping("saveRolePower")
    public Result saveRolePower(String roleId, String powerIds)
    {
        List<ProtectedResource> prlist =
            protectedResourceRepository.findByHql("from ProtectedResource where id in(" + powerIds + ")");
        Authority role = authorityRepository.findUniqueBy("id", Long.valueOf(roleId), Authority.class);
        protectedResourceAuthorityRepository
            .executeUpdate("delete from ProtectedResourceAuthority where authority.id=" + roleId, new HashMap<>());
        if (CollectionUtils.isNotEmpty(prlist))
        {
            for (ProtectedResource protectedResource : prlist)
            {
                ProtectedResourceAuthority pra = new ProtectedResourceAuthority();
                pra.setAuthority(role);
                pra.setProtectedResource(protectedResource);
                protectedResourceAuthorityRepository.save(pra);
            }
        }
        return decide(true);
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
        dc.add(Restrictions.eq("deltag", NewsDicConstants.ICommon.DELETE_NO));
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
        authority.setDeltag(NewsDicConstants.ICommon.DELETE_NO);
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
        Authority authoritydb = authorityRepository.findUniqueBy("id", authority.getId(), Authority.class);
        BeanUtils.copyProperties(authority, authoritydb, getNullPropertyNames(authority));
        authorityRepository.update(authoritydb);
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
        Authority role = authorityRepository.findUniqueBy("id", id, Authority.class);
        if (role != null)
        {
            role.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
            authorityRepository.update(role);
        }
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
            for (Authority authority : authorityList)
            {
                authority.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
                authorityRepository.update(authority);
            }
            return decide(true);
        }
        return decide(false);
    }
}
