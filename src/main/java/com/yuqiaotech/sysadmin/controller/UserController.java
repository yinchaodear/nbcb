
package com.yuqiaotech.sysadmin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.dao.PaginationSupport;
import com.yuqiaotech.common.web.domain.request.PageDomain;
import com.yuqiaotech.common.web.domain.response.ResuTree;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;
import com.yuqiaotech.sysadmin.bean.UserBean;
import com.yuqiaotech.sysadmin.model.Authority;
import com.yuqiaotech.sysadmin.model.Organisation;
import com.yuqiaotech.sysadmin.model.User;
import com.yuqiaotech.zsnews.NewsDicConstants;

@RestController
@RequestMapping("system/user")
public class UserController extends BaseController
{
    private static String MODULE_PATH = "system/user/";
    
    @Autowired
    private BaseRepository<User, Long> userRepository;
    
    @Autowired
    private BaseRepository<Organisation, Long> organisationRepository;
    
    @Autowired
    private BaseRepository<Authority, Long> authorityRepository;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data(UserBean userbean, PageDomain pageDomain)
    {
        DetachedCriteria dc = composeDetachedCriteria(userbean);
        PaginationSupport ps = userRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
        if (CollectionUtils.isNotEmpty(ps.getItems()))
        {
            List<Authority> aulist =
                authorityRepository.findByHql("from Authority where deltag=" + NewsDicConstants.ICommon.DELETE_NO);
            Map<String, Authority> auMap = new HashMap<>();
            for (Authority authority : aulist)
            {
                auMap.put(authority.getId().toString(), authority);
            }
            for (Object objtmp : ps.getItems())
            {
                User usertmp = (User)objtmp;
                if (StringUtils.isNotEmpty(usertmp.getRoleids()))
                {
                    String[] roleids = usertmp.getRoleids().split(",");
                    List<String> rolenamelist = new ArrayList<>();
                    for (int i = 0; i < roleids.length; i++)
                    {
                        if (auMap.containsKey(roleids[i]))
                        {
                            rolenamelist.add(auMap.get(roleids[i]).getAuthority());
                        }
                    }
                    usertmp.setAbout(com.yuqiaotech.common.tools.common.CollectionUtils.join(rolenamelist, ","));
                }
            }
        }
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    public DetachedCriteria composeDetachedCriteria(UserBean userbean)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(User.class);
        if (StringUtils.isNotEmpty(userbean.getOrgid()))
        {
            dc.createAlias("organisation", "organisation");
            dc.add(Restrictions.eq("organisation.id", Long.valueOf(userbean.getOrgid())));
        }
        if (StringUtils.isNotEmpty(userbean.getSearchstring()))
        {
            dc.add(Restrictions.or(Restrictions.ilike("username", userbean.getSearchstring(), MatchMode.ANYWHERE),
                Restrictions.ilike("realName", userbean.getSearchstring(), MatchMode.ANYWHERE),
                Restrictions.ilike("mobile", userbean.getSearchstring(), MatchMode.ANYWHERE)));
        }
        dc.add(Restrictions.eq("accountNonExpired", true));
        dc.addOrder(Order.desc("created"));
        return dc;
    }
    
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
        return JumpPage(MODULE_PATH + "add");
    }
    
    @PostMapping("save")
    public Result save(@RequestBody UserBean userbean)
    {
        User user = new User();
        BeanUtils.copyProperties(userbean, user);
        if ("1".equals(userbean.getStatus()))
        {
            //状态正常
            user.setAccountNonLocked(true);
        }
        else
        {
            user.setAccountNonLocked(false);
        }
        if (StringUtils.isNotEmpty(userbean.getOrgid()))
        {
            Organisation org =
                organisationRepository.findUniqueBy("id", Long.valueOf(userbean.getOrgid()), Organisation.class);
            user.setOrganisation(org);
        }
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user = userRepository.save(user);
        return decide(true);
    }
    
    @GetMapping("edit")
    public ModelAndView edit(ModelAndView modelAndView, Long id)
    {
        User user = userRepository.get(id, User.class);
        UserBean userbean = new UserBean();
        BeanUtils.copyProperties(user, userbean);
        if (user.isAccountNonLocked())
        {
            userbean.setStatus("1");
        }
        else
        {
            userbean.setStatus("0");
        }
        if (user.getOrganisation() != null)
        {
            userbean.setOrgid(user.getOrganisation().getId().toString());
        }
        modelAndView.addObject("user", userbean);
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    @GetMapping("edit2")
    public ModelAndView edit2(ModelAndView modelAndView, Long id)
    {
        User user = userRepository.get(id, User.class);
        UserBean userbean = new UserBean();
        BeanUtils.copyProperties(user, userbean);
        if (user.isAccountNonLocked())
        {
            userbean.setStatus("1");
        }
        else
        {
            userbean.setStatus("0");
        }
        if (user.getOrganisation() != null)
        {
            userbean.setOrgid(user.getOrganisation().getId().toString());
        }
        modelAndView.addObject("user", userbean);
        modelAndView.setViewName(MODULE_PATH + "edit2");
        return modelAndView;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody UserBean userbean)
    {
        User user = userRepository.findUniqueBy("id", userbean.getId(), User.class);
        BeanUtils.copyProperties(userbean, user, getNullPropertyNames(userbean));
        if ("1".equals(userbean.getStatus()))
        {
            //状态正常
            user.setAccountNonLocked(true);
        }
        else
        {
            user.setAccountNonLocked(false);
        }
        if (StringUtils.isNotEmpty(userbean.getOrgid()))
        {
            Organisation org =
                organisationRepository.findUniqueBy("id", Long.valueOf(userbean.getOrgid()), Organisation.class);
            user.setOrganisation(org);
        }
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        
        userRepository.update(user);
        return decide(true);
    }
    
    @GetMapping("orgTree")
    public ResuTree orgTree()
    {
        DetachedCriteria orgDc = DetachedCriteria.forClass(Organisation.class);
        orgDc.add(Restrictions.eq("deltag", NewsDicConstants.ICommon.DELETE_NO));
        orgDc.addOrder(Order.desc("created"));
        List<Organisation> orgList = organisationRepository.findByCriteria(orgDc);
        
        DetachedCriteria dc = DetachedCriteria.forClass(User.class);
        dc.add(Restrictions.eq("accountNonExpired", true));
        List<User> userList = userRepository.findByCriteria(dc);
        JSONArray treeArr = new JSONArray();
        JSONObject adminObj = new JSONObject();
        adminObj.put("id", "admin_-99");
        adminObj.put("title", "超级管理员");
        adminObj.put("editable", 0);
        JSONArray adminChildren = new JSONArray();
        
        Map<Long, JSONArray> orgChildrenMap = new HashMap<>();
        
        for (Organisation org : orgList)
        {
            orgChildrenMap.put(org.getId(), new JSONArray());
        }
        
        for (User user : userList)
        {
            JSONObject userObj = new JSONObject();
            userObj.put("id", "user_" + user.getId());
            userObj.put("title", user.getUsername());
            userObj.put("displayOrder", user.getId());
            userObj.put("editable", 0);
            if (user.getOrganisation() == null)
            {
                adminChildren.add(userObj);
            }
            else
            {
                if (orgChildrenMap.containsKey(user.getOrganisation().getId()))
                {
                    orgChildrenMap.get(user.getOrganisation().getId()).add(userObj);
                }
            }
        }
        
        adminObj.put("children", adminChildren);
        treeArr.add(adminObj);
        for (Organisation org : orgList)
        {
            JSONObject orgObj = new JSONObject();
            orgObj.put("id", org.getId());
            orgObj.put("title", org.getOrgName());
            orgObj.put("displayOrder", org.getId());
            adminObj.put("editable", 1);
            orgObj.put("children", orgChildrenMap.get(org.getId()));
            treeArr.add(orgObj);
        }
        
        return dataTree(treeArr);
    }
    
    @GetMapping("status/{id}")
    public Result status(@PathVariable Long id)
    {
        User user = userRepository.findUniqueBy("id", id, User.class);
        if (user.isAccountNonLocked())
        {
            user.setAccountNonLocked(false);
        }
        else
        {
            user.setAccountNonLocked(true);
        }
        
        userRepository.update(user);
        return success();
    }
    
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id)
    {
        User user = userRepository.findUniqueBy("id", id, User.class);
        user.setAccountNonExpired(false);
        userRepository.update(user);
        return decide(true);
    }
    
    @DeleteMapping("batchRemove/{ids}")
    public Result batchRemove(@PathVariable String ids)
    {
        if (StringUtils.isNotEmpty(ids))
        {
            List<Long> cdids =
                Arrays.asList(ids.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            DetachedCriteria dc = DetachedCriteria.forClass(User.class);
            dc.add(Restrictions.in("id", cdids));
            List<User> userList = userRepository.findByCriteria(dc);
            for (User user : userList)
            {
                user.setAccountNonExpired(false);
                userRepository.update(user);
            }
            return decide(true);
        }
        return decide(false);
    }
}
