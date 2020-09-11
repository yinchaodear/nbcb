
package com.yuqiaotech.zsnews.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.dao.PaginationSupport;
import com.yuqiaotech.common.web.domain.request.PageDomain;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;
import com.yuqiaotech.zsnews.NewsDicConstants;
import com.yuqiaotech.zsnews.model.SysNotice;
import com.yuqiaotech.zsnews.model.SysNoticeResult;

@RestController
@RequestMapping("zsnews/sysnotice")
public class SysNoticeController extends BaseController
{
    private static String MODULE_PATH = "zsnews/sysnotice/";
    
    @Autowired
    private BaseRepository<SysNotice, Long> sysNoticeRepository;
    
    @Autowired
    private BaseRepository<SysNoticeResult, Long> sysNoticeResultRepository;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data(SysNotice sysNotice, PageDomain pageDomain)
    {
        String sql =
            "select n.*,count(r.f_id) cnt from t_sys_notice n left join t_sys_notice_result r on n.f_id = r.f_sys_notice_id where 1=1 ";
        if (StringUtils.isNotEmpty(sysNotice.getTitle()))
        {
            sql += " and  (f_title like '%" + sysNotice.getTitle() + "%' or f_content like '%" + sysNotice.getTitle()
                + "%')";
        }
        if (sysNotice.getWay() != null)
        {
            sql += " and f_way=" + sysNotice.getWay();
        }
        if (sysNotice.getStatus() != null)
        {
            sql += " and f_status=" + sysNotice.getStatus();
        }
        sql += " group by n.f_id  order by n.f_created desc";
        PaginationSupport ps = sysNoticeRepository
            .paginateAsMapByNativeSql(sql, new HashMap<>(), pageDomain.getPage(), pageDomain.getLimit());
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    public DetachedCriteria composeDetachedCriteria(SysNotice sysNotice)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(SysNotice.class);
        return dc;
    }
    
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
        return JumpPage(MODULE_PATH + "add");
    }
    
    @PostMapping("save")
    public Result save(@RequestBody SysNotice sysNotice)
    {
        sysNotice.setType(NewsDicConstants.INotice.Type.SYS);
        sysNotice.setPubtime(new Date());
        sysNotice.setStatus(NewsDicConstants.INotice.Status.DOING);
        sysNoticeRepository.save(sysNotice);
        return decide(true);
    }
    
    @GetMapping("edit")
    public ModelAndView edit(ModelAndView modelAndView, Long id)
    {
        modelAndView.addObject("sysNotice", sysNoticeRepository.get(id, SysNotice.class));
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody SysNotice sysNotice)
    {
        SysNotice sysNoticedb = sysNoticeRepository.findUniqueBy("id", sysNotice.getId(), SysNotice.class);
        BeanUtils.copyProperties(sysNotice, sysNoticedb, getNullPropertyNames(sysNotice));
        sysNoticeRepository.update(sysNoticedb);
        return decide(true);
    }
    
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除角色")
    public Result remove(@PathVariable Long id)
    {
        SysNotice sysNotice = sysNoticeRepository.findUniqueBy("id", id, SysNotice.class);
        if (sysNotice != null && sysNotice.getStatus() == NewsDicConstants.INotice.Status.END)
        {
            return decide(false, null, "已完成状态消息不可删除");
        }
        sysNoticeResultRepository.executeUpdate("delete from SysNoticeResult where sysNotice.id=" + id,
            new HashMap<>());
        sysNoticeRepository.remove(id, SysNotice.class);
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
            DetachedCriteria dc = DetachedCriteria.forClass(SysNotice.class);
            dc.add(Restrictions.in("id", cdids));
            List<SysNotice> sysNoticeList = sysNoticeRepository.findByCriteria(dc);
            for (SysNotice sysNotice : sysNoticeList)
            {
                if (sysNotice.getStatus() == NewsDicConstants.INotice.Status.DOING)
                {
                    sysNoticeResultRepository.executeUpdate(
                        "delete from SysNoticeResult where sysNotice.id=" + sysNotice.getId(),
                        new HashMap<>());
                    sysNoticeRepository.delete(sysNotice);
                }
            }
            return decide(true);
        }
        return decide(false);
    }
}
