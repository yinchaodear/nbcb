
package com.yuqiaotech.zsnews.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

import com.yuqiaotech.common.logging.annotation.Logging;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.dao.PaginationSupport;
import com.yuqiaotech.common.web.domain.request.PageDomain;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;
import com.yuqiaotech.zsnews.NewsDicConstants;
import com.yuqiaotech.zsnews.bean.NewsFormBean;
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
    public ResultTable data(NewsFormBean newsFormBean, PageDomain pageDomain)
    {
        //        String hql = buildSearchCondition(newsFormBean);
        DetachedCriteria dc = composeDetachedCriteria(newsFormBean);
        PaginationSupport ps = newsFormRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
        //        PaginationSupport ps =
        //            newsFormRepository.paginateByHql(hql, pageDomain.getPage(), pageDomain.getLimit(), new HashMap<>());
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    public String buildSearchCondition(NewsFormBean newsFormBean)
    {
        String hql = "from NewsForm nf where 1=1 ";
        if (newsFormBean.getNewsid() != null)
        {
            hql += " and news.id=" + newsFormBean.getNewsid();
        }
        if (StringUtils.isNotEmpty(newsFormBean.getTitle()))
        {
            hql += " and (nf.companyname like '%" + newsFormBean.getTitle() + "%' or nf.fullname1 like '%"
                + newsFormBean.getTitle() + "%' or nf.title like '%" + newsFormBean.getTitle()
                + "%' or nf.mobile1 like '%" + newsFormBean.getTitle() + "%' or nf.transportation like '%"
                + newsFormBean.getTitle() + "%' or nf.carno like '%" + newsFormBean.getTitle()
                + "%' or nf.fullname2 like '%" + newsFormBean.getTitle() + "%' or nf.mobile2 like '%"
                + newsFormBean.getTitle() + "%')";
        }
        hql += " order by nf.created desc";
        return hql;
    }
    
    public DetachedCriteria composeDetachedCriteria(NewsFormBean newsFormBean)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(NewsForm.class);
        if (StringUtils.isNotEmpty(newsFormBean.getTitle()))
        {
            dc.add(Restrictions.or(Restrictions.ilike("companyname", newsFormBean.getTitle(), MatchMode.ANYWHERE),
                Restrictions.ilike("fullname1", newsFormBean.getTitle(), MatchMode.ANYWHERE),
                Restrictions.ilike("title", newsFormBean.getTitle(), MatchMode.ANYWHERE),
                Restrictions.ilike("mobile1", newsFormBean.getTitle(), MatchMode.ANYWHERE),
                Restrictions.ilike("transportation", newsFormBean.getTitle(), MatchMode.ANYWHERE),
                Restrictions.ilike("carno", newsFormBean.getTitle(), MatchMode.ANYWHERE),
                Restrictions.ilike("fullname2", newsFormBean.getTitle(), MatchMode.ANYWHERE),
                Restrictions.ilike("mobile2", newsFormBean.getTitle(), MatchMode.ANYWHERE)));
        }
        if (newsFormBean.getNewsid() != null)
        {
            dc.createAlias("news", "news");
            dc.add(Restrictions.eq("news.id", newsFormBean.getNewsid()));
        }
        dc.add(Restrictions.eq("deltag", NewsDicConstants.ICommon.DELETE_NO));
        dc.addOrder(Order.desc("created"));
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
