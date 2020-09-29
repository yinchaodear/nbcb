
package com.yuqiaotech.zsnews.controller;

import java.util.Arrays;
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

import com.yuqiaotech.zsnews.model.NewsForm;

@RestController
@RequestMapping("zsnews/newsform")
public class NewsFormController extends BaseController
{
    private static String MODULE_PATH = "zsnews/newsform/";
    
    @Autowired
    private BaseRepository<NewsForm, Long> newsFormRepository;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data(NewsForm newsForm, PageDomain pageDomain)
    {
        DetachedCriteria dc = composeDetachedCriteria(newsForm);
        PaginationSupport ps = newsFormRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    public DetachedCriteria composeDetachedCriteria(NewsForm newsForm)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(NewsForm.class);
        return dc;
    }
    
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
        return JumpPage(MODULE_PATH + "add");
    }
    
    @PostMapping("save")
    public Result save(@RequestBody NewsForm newsForm)
    {
        newsFormRepository.save(newsForm);
        return decide(true);
    }
    
    @GetMapping("edit")
    public ModelAndView edit(ModelAndView modelAndView, Long id)
    {
        modelAndView.addObject("newsForm", newsFormRepository.get(id, NewsForm.class));
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody NewsForm newsForm)
    {
        NewsForm newsFormdb = newsFormRepository.findUniqueBy("id", newsForm.getId(), NewsForm.class);
        BeanUtils.copyProperties(newsForm, newsFormdb, getNullPropertyNames(newsForm));
        newsFormRepository.update(newsFormdb);
        return decide(true);
    }
    
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除角色")
    public Result remove(@PathVariable Long id)
    {
        newsFormRepository.remove(id, NewsForm.class);
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
            DetachedCriteria dc = DetachedCriteria.forClass(NewsForm.class);
            dc.add(Restrictions.in("id", cdids));
            List<NewsForm> newsFormList = newsFormRepository.findByCriteria(dc);
            newsFormList.forEach(newsFormRepository::delete);
            return decide(true);
        }
        return decide(false);
    }
}
