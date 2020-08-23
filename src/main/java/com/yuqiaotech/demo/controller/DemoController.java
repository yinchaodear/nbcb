
package com.yuqiaotech.demo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
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
import com.yuqiaotech.demo.model.Demo;

@RestController
@RequestMapping("demo/demo")
public class DemoController extends BaseController
{
    private static String MODULE_PATH = "demo/demo/";
    
    @Autowired
    private BaseRepository<Demo, Long> demoRepository;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data(Demo demo, PageDomain pageDomain)
    {
        DetachedCriteria dc = composeDetachedCriteria(demo);
        PaginationSupport ps = demoRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    public DetachedCriteria composeDetachedCriteria(Demo demo)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(Demo.class);
        return dc;
    }
    
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
        return JumpPage(MODULE_PATH + "add");
    }
    
    @PostMapping("save")
    public Result save(@RequestBody Demo demo)
    {
        demoRepository.save(demo);
        return decide(true);
    }
    
    @GetMapping("edit")
    public ModelAndView edit(ModelAndView modelAndView, Long id)
    {
        modelAndView.addObject("demo", demoRepository.get(id, Demo.class));
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody Demo demo)
    {
        demoRepository.update(demo);
        return decide(true);
    }
    
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除角色")
    public Result remove(@PathVariable Long id)
    {
        demoRepository.remove(id, Demo.class);
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
            DetachedCriteria dc = DetachedCriteria.forClass(Demo.class);
            dc.add(Restrictions.in("id", cdids));
            List<Demo> demoList = demoRepository.findByCriteria(dc);
            demoList.forEach(demoRepository::delete);
            return decide(true);
        }
        return decide(false);
    }
}
