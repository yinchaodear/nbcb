
package com.yuqiaotech.zsnews.controller;

import java.util.Arrays;
import java.util.Date;
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
import com.yuqiaotech.zsnews.model.Feedback;

@RestController
@RequestMapping("zsnews/feedback")
public class FeedbackController extends BaseController
{
    private static String MODULE_PATH = "zsnews/feedback/";
    
    @Autowired
    private BaseRepository<Feedback, Long> feedbackRepository;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data(Feedback feedback, PageDomain pageDomain)
    {
        DetachedCriteria dc = composeDetachedCriteria(feedback);
        PaginationSupport ps = feedbackRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    public DetachedCriteria composeDetachedCriteria(Feedback feedback)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(Feedback.class);
        if (StringUtils.isNotEmpty(feedback.getContent()))
        {
            dc.add(Restrictions.or(Restrictions.ilike("content", feedback.getContent(), MatchMode.ANYWHERE),
                Restrictions.ilike("replyContent", feedback.getContent(), MatchMode.ANYWHERE)));
        }
        if (feedback.getStatus() != null)
        {
            dc.add(Restrictions.eq("status", feedback.getStatus()));
        }
        dc.add(Restrictions.eq("deltag", NewsDicConstants.ICommon.DELETE_NO));
        dc.addOrder(Order.desc("feedbackTime"));
        return dc;
    }
    
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
        return JumpPage(MODULE_PATH + "add");
    }
    
    @PostMapping("save")
    public Result save(@RequestBody Feedback feedback)
    {
        feedbackRepository.save(feedback);
        return decide(true);
    }
    
    @GetMapping("edit")
    public ModelAndView edit(ModelAndView modelAndView, Long id)
    {
        modelAndView.addObject("feedback", feedbackRepository.get(id, Feedback.class));
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody Feedback feedback)
    {
        Feedback feedbackdb = feedbackRepository.findUniqueBy("id", feedback.getId(), Feedback.class);
        BeanUtils.copyProperties(feedback, feedbackdb, getNullPropertyNames(feedback));
        feedbackdb.setStatus(NewsDicConstants.IFeedback.Status.END);
        feedbackdb.setReplyTime(new Date());
        feedbackdb.setUser(getCurrentUser());
        
        feedbackRepository.update(feedbackdb);
        return decide(true);
    }
    
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除角色")
    public Result remove(@PathVariable Long id)
    {
        Feedback feedback = feedbackRepository.findUniqueBy("id", id, Feedback.class);
        if (feedback == null)
        {
            return decide(false);
        }
        if (feedback.getStatus() == NewsDicConstants.IFeedback.Status.END)
        {
            return decide(false, null, "已完成的反馈不可删除");
        }
        feedback.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
        feedbackRepository.update(feedback);
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
            DetachedCriteria dc = DetachedCriteria.forClass(Feedback.class);
            dc.add(Restrictions.in("id", cdids));
            List<Feedback> feedbackList = feedbackRepository.findByCriteria(dc);
            for (Feedback feedback : feedbackList)
            {
                if (feedback.getStatus() == NewsDicConstants.IFeedback.Status.DOING)
                {
                    feedback.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
                    feedbackRepository.update(feedback);
                }
            }
            return decide(true);
        }
        return decide(false);
    }
}
