
package com.yuqiaotech.zsnews.controller;

import java.util.Arrays;
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

import com.yuqiaotech.common.logging.annotation.Logging;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.dao.PaginationSupport;
import com.yuqiaotech.common.web.domain.request.PageDomain;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;
import com.yuqiaotech.zsnews.NewsDicConstants;
import com.yuqiaotech.zsnews.model.Category;

@RestController
@RequestMapping("zsnews/category")
public class CategoryController extends BaseController
{
    private static String MODULE_PATH = "zsnews/category/";
    
    @Autowired
    private BaseRepository<Category, Long> categoryRepository;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data(Category category, PageDomain pageDomain)
    {
        DetachedCriteria dc = composeDetachedCriteria(category);
        dc.addOrder(Order.asc("displayOrder"));
        
        PaginationSupport ps = categoryRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    public DetachedCriteria composeDetachedCriteria(Category category)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(Category.class);
        
        if (category != null)
        {
            if (StringUtils.isNotEmpty(category.getTitle()))
            {
                dc.add(Restrictions.or(Restrictions.ilike("title", category.getTitle(), MatchMode.ANYWHERE),
                    Restrictions.ilike("remark", category.getTitle(), MatchMode.ANYWHERE)));
            }
            if (category.getStatus() != null)
            {
                dc.add(Restrictions.eq("status", category.getStatus()));
            }
        }
        
        dc.add(Restrictions.eq("deltag", NewsDicConstants.ICommon.DELETE_NO));
        
        return dc;
    }
    
    @GetMapping("status/{cid}")
    public Result status(@PathVariable Long cid)
    {
        Category category = categoryRepository.findUniqueBy("id", cid, Category.class);
        if (category != null)
        {
            if (category.getStatus() == null || category.getStatus() == NewsDicConstants.ICommon.STATUS_DOWN)
            {
                category.setStatus(NewsDicConstants.ICommon.STATUS_UP);
            }
            else
            {
                category.setStatus(NewsDicConstants.ICommon.STATUS_DOWN);
            }
            
            categoryRepository.update(category);
        }
        return success();
    }
    
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
        modelAndView.addObject("displayOrder", getNextDisplayOrder());
        modelAndView.setViewName(MODULE_PATH + "add");
        return modelAndView;
    }
    
    @PostMapping("save")
    public Result save(@RequestBody Category category)
    {
        category.setStatus(NewsDicConstants.ICommon.STATUS_UP);//默认上架
        category.setDeltag(NewsDicConstants.ICommon.DELETE_NO);//默认未删除
        if (category.getDisplayOrder() == null)
        {
            category.setDisplayOrder(getNextDisplayOrder());
        }
        category.setType(NewsDicConstants.ICategory.Type.QUANJU);
        categoryRepository.save(category);
        return decide(true);
    }
    
    private Integer getNextDisplayOrder()
    { //查当前最大的顺序号
        String sql = "select ifnull(max(f_display_order),0) maxorder from t_category where f_display_order is not null";
        List<Map<String, Object>> rst = categoryRepository.findMapByNativeSql(sql);
        Integer displayOrder = 1;
        if (CollectionUtils.isNotEmpty(rst) && rst.get(0) != null && !rst.get(0).isEmpty())
        {
            displayOrder = com.yuqiaotech.common.tools.common.StringUtils
                .parseInt(com.yuqiaotech.common.tools.common.StringUtils.parseNull(rst.get(0).get("maxorder"))) + 1;
        }
        return displayOrder;
    }
    
    @GetMapping("edit")
    public ModelAndView edit(ModelAndView modelAndView, Long id)
    {
        modelAndView.addObject("category", categoryRepository.get(id, Category.class));
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody Category category)
    {
        Category categorydb = categoryRepository.findUniqueBy("id", category.getId(), Category.class);
        BeanUtils.copyProperties(category, categorydb, getNullPropertyNames(category));
        if (categorydb.getDisplayOrder() == null)
        {
            categorydb.setDisplayOrder(getNextDisplayOrder());
        }
        if (categorydb.getDeltag() == null)
        {
            categorydb.setDeltag(NewsDicConstants.ICommon.DELETE_NO);
        }
        if (categorydb.getStatus() == null)
        {
            categorydb.setStatus(NewsDicConstants.ICommon.STATUS_UP);
        }
        if (StringUtils.isNotEmpty(categorydb.getType()))
        {
            categorydb.setType(NewsDicConstants.ICategory.Type.QUANJU);
        }
        categoryRepository.update(categorydb);
        return decide(true);
    }
    
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除角色")
    public Result remove(@PathVariable Long id)
    {
        //TODO:这里要看，有没有关联，如果有的话，不能删除
        //FIXME:栏目删除为逻辑删除
        Category categorydb = categoryRepository.findUniqueBy("id", id, Category.class);
        categorydb.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
        categoryRepository.update(categorydb);
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
            DetachedCriteria dc = DetachedCriteria.forClass(Category.class);
            dc.add(Restrictions.in("id", cdids));
            List<Category> categoryList = categoryRepository.findByCriteria(dc);
            for (Category categorydb : categoryList)
            {
                categorydb.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
                categoryRepository.update(categorydb);
            }
            return decide(true);
        }
        return decide(false);
    }
}
