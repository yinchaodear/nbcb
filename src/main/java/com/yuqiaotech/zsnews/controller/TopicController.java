
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
import com.yuqiaotech.zsnews.bean.TopicBean;
import com.yuqiaotech.zsnews.model.Channel;
import com.yuqiaotech.zsnews.model.Topic;

@RestController
@RequestMapping("zsnews/topic")
public class TopicController extends BaseController
{
    private static String MODULE_PATH = "zsnews/topic/";
    
    @Autowired
    private BaseRepository<Topic, Long> topicRepository;
    
    @Autowired
    private BaseRepository<Channel, Long> channelRepository;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data(Topic topic, PageDomain pageDomain)
    {
        DetachedCriteria dc = composeDetachedCriteria(topic);
        dc.addOrder(Order.desc("created"));
        PaginationSupport ps = topicRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    public DetachedCriteria composeDetachedCriteria(Topic topic)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(Topic.class);
        if (topic != null)
        {
            if (StringUtils.isNotEmpty(topic.getTitle()))
            {
                dc.add(Restrictions.or(Restrictions.ilike("title", topic.getTitle(), MatchMode.ANYWHERE),
                    Restrictions.ilike("remark", topic.getTitle(), MatchMode.ANYWHERE)));
            }
            if (topic.getStatus() != null)
            {
                dc.add(Restrictions.eq("status", topic.getStatus()));
            }
        }
        dc.add(Restrictions.eq("deltag", NewsDicConstants.ICommon.DELETE_NO));
        return dc;
    }
    
    @GetMapping("status/{cid}")
    public Result status(@PathVariable Long cid)
    {
        Topic topic = topicRepository.findUniqueBy("id", cid, Topic.class);
        if (topic != null)
        {
            if (topic.getStatus() == null || topic.getStatus() == NewsDicConstants.ICommon.STATUS_DOWN)
            {
                topic.setStatus(NewsDicConstants.ICommon.STATUS_UP);
            }
            else
            {
                topic.setStatus(NewsDicConstants.ICommon.STATUS_DOWN);
            }
            
            topicRepository.update(topic);
        }
        return success();
    }
    
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
        return JumpPage(MODULE_PATH + "add");
    }
    
    @PostMapping("save")
    public Result save(@RequestBody TopicBean topicBean)
    {
        Topic topic = new Topic();
        BeanUtils.copyProperties(topicBean, topic);
        
        if (topicBean.getChannelId() != null)
        {
            Channel channel = channelRepository.findUniqueBy("id", topicBean.getChannelId(), Channel.class);
            topic.setChannel(channel);
        }
        topic.setStatus(NewsDicConstants.ICommon.STATUS_UP);//默认上架
        topic.setDeltag(NewsDicConstants.ICommon.DELETE_NO);//默认未删除
        topicRepository.save(topic);
        return decide(true);
    }
    
    @GetMapping("edit")
    public ModelAndView edit(ModelAndView modelAndView, Long id)
    {
        modelAndView.addObject("topic", topicRepository.get(id, Topic.class));
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody TopicBean topicBean)
    {
        Topic topicdb = topicRepository.findUniqueBy("id", topicBean.getId(), Topic.class);
        Channel channel = null;
        if (topicBean.getChannelId() != null)
        {
            Channel channeldb = topicdb.getChannel();
            if (channeldb == null || !channeldb.getId().equals(topicBean.getChannelId()))
            {
                //如果原来没有。或者新的修改了
                channel = channelRepository.findUniqueBy("id", topicBean.getChannelId(), Channel.class);
            }
        }
        BeanUtils.copyProperties(topicBean, topicdb, getNullPropertyNames(topicBean));
        if (channel != null)
        {
            topicdb.setChannel(channel);
        }
        
        if (topicdb.getDeltag() == null)
        {
            topicdb.setDeltag(NewsDicConstants.ICommon.DELETE_NO);
        }
        if (topicdb.getStatus() == null)
        {
            topicdb.setStatus(NewsDicConstants.ICommon.STATUS_UP);
        }
        
        topicRepository.update(topicdb);
        return decide(true);
    }
    
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除角色")
    public Result remove(@PathVariable Long id)
    {
        //TODO:这里要看，有没有关联，如果有的话，不能删除
        //FIXME:栏目删除为逻辑删除
        Topic topicdb = topicRepository.findUniqueBy("id", id, Topic.class);
        topicdb.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
        topicRepository.update(topicdb);
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
            DetachedCriteria dc = DetachedCriteria.forClass(Topic.class);
            dc.add(Restrictions.in("id", cdids));
            List<Topic> topicList = topicRepository.findByCriteria(dc);
            for (Topic topicdb : topicList)
            {
                topicdb.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
                topicRepository.update(topicdb);
            }
            return decide(true);
        }
        return decide(false);
    }
}
