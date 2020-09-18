
package com.yuqiaotech.zsnews.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
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
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.dao.PaginationSupport;
import com.yuqiaotech.common.web.domain.request.PageDomain;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;
import com.yuqiaotech.zsnews.NewsDicConstants;
import com.yuqiaotech.zsnews.model.FAQ;

@RestController
@RequestMapping("zsnews/faq")
public class FAQController extends BaseController
{
    private static String MODULE_PATH = "zsnews/faq/";
    
    @Autowired
    private BaseRepository<FAQ, Long> fAQRepository;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data(FAQ fAQ, PageDomain pageDomain)
    {
        DetachedCriteria dc = composeDetachedCriteria(fAQ);
        PaginationSupport ps = fAQRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    public DetachedCriteria composeDetachedCriteria(FAQ fAQ)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(FAQ.class);
        if (StringUtils.isNotEmpty(fAQ.getTitle()))
        {
            dc.add(Restrictions.or(Restrictions.ilike("title", fAQ.getTitle(), MatchMode.ANYWHERE),
                Restrictions.ilike("content", fAQ.getTitle(), MatchMode.ANYWHERE)));
        }
        dc.add(Restrictions.eq("deltag", NewsDicConstants.ICommon.DELETE_NO));
        return dc;
    }
    
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
        return JumpPage(MODULE_PATH + "add");
    }
    
    @PostMapping("save")
    public Result save(@RequestBody FAQ fAQ)
    {
        fAQ.setPubTime(new Date());
        fAQ.setUser(getCurrentUser());
        fAQ.setDeltag(NewsDicConstants.ICommon.DELETE_NO);
        fAQRepository.save(fAQ);
        return decide(true);
    }
    
    @GetMapping("edit")
    public ModelAndView edit(ModelAndView modelAndView, Long id)
    {
        modelAndView.addObject("fAQ", fAQRepository.get(id, FAQ.class));
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody FAQ fAQ)
    {
        FAQ fAQdb = fAQRepository.findUniqueBy("id", fAQ.getId(), FAQ.class);
        BeanUtils.copyProperties(fAQ, fAQdb, getNullPropertyNames(fAQ));
        fAQRepository.update(fAQdb);
        return decide(true);
    }
    
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除角色")
    public Result remove(@PathVariable Long id)
    {
        FAQ faq = fAQRepository.findUniqueBy("id", id, FAQ.class);
        if (faq != null)
        {
            faq.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
            fAQRepository.update(faq);
        }
        
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
            DetachedCriteria dc = DetachedCriteria.forClass(FAQ.class);
            dc.add(Restrictions.in("id", cdids));
            List<FAQ> fAQList = fAQRepository.findByCriteria(dc);
            for (FAQ faq : fAQList)
            {
                faq.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
                fAQRepository.update(faq);
            }
            return decide(true);
        }
        return decide(false);
    }
}
