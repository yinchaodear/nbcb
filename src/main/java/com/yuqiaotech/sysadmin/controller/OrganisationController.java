
package com.yuqiaotech.sysadmin.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
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

import com.yuqiaotech.common.logging.annotation.Logging;
import com.yuqiaotech.common.tools.common.CollectionUtils;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.dao.PaginationSupport;
import com.yuqiaotech.common.web.domain.request.PageDomain;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;
import com.yuqiaotech.sysadmin.model.Organisation;
import com.yuqiaotech.sysadmin.model.User;
import com.yuqiaotech.zsnews.NewsDicConstants;

@RestController
@RequestMapping("system/organisation")
public class OrganisationController extends BaseController
{
    private static String MODULE_PATH = "system/organisation/";
    
    @Autowired
    private BaseRepository<Organisation, Long> organisationRepository;
    
    @Autowired
    private BaseRepository<User, Long> userRepository;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data(Organisation organisation, PageDomain pageDomain)
    {
        DetachedCriteria dc = composeDetachedCriteria(organisation);
        PaginationSupport ps =
            organisationRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    public DetachedCriteria composeDetachedCriteria(Organisation organisation)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(Organisation.class);
        return dc;
    }
    
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
        return JumpPage(MODULE_PATH + "add");
    }
    
    @PostMapping("save")
    public Result save(@RequestBody Organisation organisation)
    {
        organisation.setDeltag(NewsDicConstants.ICommon.DELETE_NO);
        organisationRepository.save(organisation);
        return decide(true);
    }
    
    @GetMapping("edit")
    public ModelAndView edit(ModelAndView modelAndView, Long id)
    {
        modelAndView.addObject("organisation", organisationRepository.get(id, Organisation.class));
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody Organisation organisation)
    {
        Organisation organisationdb =
            organisationRepository.findUniqueBy("id", organisation.getId(), Organisation.class);
        BeanUtils.copyProperties(organisation, organisationdb, getNullPropertyNames(organisation));
        organisationdb.setDeltag(NewsDicConstants.ICommon.DELETE_NO);
        organisationRepository.update(organisationdb);
        return decide(true);
    }
    
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除角色")
    public Result remove(@PathVariable Long id)
    {
        List<User> userlist =
            userRepository.findByHql("from User where organisation.id=" + id + " and accountNonExpired=true");
        if (CollectionUtils.isNotEmpty(userlist))
        {
            return decide(false, null, "组织下有人员，不可删除");
        }
        Organisation organisationdb = organisationRepository.findUniqueBy("id", id, Organisation.class);
        organisationdb.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
        organisationRepository.update(organisationdb);
        return decide(true);
    }
    
    @DeleteMapping("batchRemove/{ids}")
    @Logging(title = "批量删除角色")
    public Result batchRemove(@PathVariable String ids)
    {
        if (StringUtils.isNotEmpty(ids))
        {
            List<Long> cdids =
                Arrays.asList(ids.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            DetachedCriteria dc = DetachedCriteria.forClass(Organisation.class);
            dc.add(Restrictions.in("id", cdids));
            List<Organisation> organisationList = organisationRepository.findByCriteria(dc);
            for (Organisation organisation : organisationList)
            {
                organisation.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
                organisationRepository.update(organisation);
            }
            return decide(true);
        }
        return decide(false);
    }
}
