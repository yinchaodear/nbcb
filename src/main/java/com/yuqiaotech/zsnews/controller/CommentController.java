
package com.yuqiaotech.zsnews.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
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
import com.yuqiaotech.zsnews.bean.CommentBean;
import com.yuqiaotech.zsnews.model.Channel;
import com.yuqiaotech.zsnews.model.Comment;
import com.yuqiaotech.zsnews.model.News;
import com.yuqiaotech.zsnews.model.NewsCategory;
import com.yuqiaotech.zsnews.model.NewsChannel;

@RestController
@RequestMapping("zsnews/comment")
public class CommentController extends BaseController
{
    private static String MODULE_PATH = "zsnews/comment/";
    
    @Autowired
    private BaseRepository<Comment, Long> commentRepository;
    
    @Autowired
    private BaseRepository<NewsChannel, Long> newsChannelRepository;
    
    @Autowired
    private BaseRepository<NewsCategory, Long> newsCategoryRepository;
    
    @Autowired
    private BaseRepository<News, Long> newsRepository;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data(Comment comment, PageDomain pageDomain)
    {
        DetachedCriteria dc = composeDetachedCriteria(comment);
        PaginationSupport ps = commentRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
        List<CommentBean> cblist = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ps.getItems()))
        {
            List<Comment> clist = ps.getItems();
            for (Comment comment2 : clist)
            {
                CommentBean cb = new CommentBean();
                BeanUtils.copyProperties(comment2, cb);
                
                cblist.add(cb);
            }
            List<String> newsidlist = new ArrayList<>();
            for (CommentBean comment2 : cblist)
            {
                newsidlist.add(comment2.getNews().getId().toString());
            }
            List<NewsChannel> nclist = newsChannelRepository.findByHql("from NewsChannel where news.id in ("
                + com.yuqiaotech.common.tools.common.CollectionUtils.join(newsidlist, ",") + ")");
            
            Map<Long, List<Channel>> newsChannelMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(nclist))
            {
                for (NewsChannel newsChannel : nclist)
                {
                    if (!newsChannelMap.containsKey(newsChannel.getNews().getId()))
                    {
                        newsChannelMap.put(newsChannel.getNews().getId(), new ArrayList<Channel>());
                    }
                    newsChannelMap.get(newsChannel.getNews().getId()).add(newsChannel.getChannel());
                }
            }
            
            for (CommentBean comment2 : cblist)
            {
                //接下来构造news所属栏目，频道，专题
                if (newsChannelMap.containsKey(comment2.getNews().getId()))
                {
                    if (newsChannelMap.get(comment2.getNews().getId()) != null
                        && !newsChannelMap.get(comment2.getNews().getId()).isEmpty())
                    {
                        comment2.setChanneltitle(newsChannelMap.get(comment2.getNews().getId()).get(0).getTitle());
                    }
                }
            }
            
            //只查状态正常的分类
            List<NewsCategory> ncategoryList = newsCategoryRepository.findByHql("from NewsCategory where news.id in("
                + com.yuqiaotech.common.tools.common.CollectionUtils.join(newsidlist, ",") + ") and category.status="
                + NewsDicConstants.ICommon.STATUS_UP + " and category.deltag=" + NewsDicConstants.ICommon.DELETE_NO);
            Map<String, List<String>> ncategoryMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(ncategoryList))
            {
                for (NewsCategory newsCategory : ncategoryList)
                {
                    if (!ncategoryMap.containsKey(newsCategory.getNews().getId().toString()))
                    {
                        ncategoryMap.put(newsCategory.getNews().getId().toString(), new ArrayList<String>());
                    }
                    ncategoryMap.get(newsCategory.getNews().getId().toString())
                        .add(newsCategory.getCategory().getTitle());
                }
            }
            
            for (CommentBean comment2 : cblist)
            {
                if (ncategoryMap.containsKey(comment2.getNews().getId().toString()))
                {
                    comment2.setCategorys(com.yuqiaotech.common.tools.common.CollectionUtils
                        .join(ncategoryMap.get(comment2.getNews().getId().toString()), ","));
                }
            }
        }
        
        return pageTable(cblist, ps.getTotalCount());
    }
    
    public DetachedCriteria composeDetachedCriteria(Comment comment)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(Comment.class);
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
    public Result save(@RequestBody CommentBean comment)
    {
        if (comment.getNewsid() == null)
        {
            return decide(false, null, "请选择问题");
        }
        News news = newsRepository.findUniqueBy("id", comment.getNewsid(), News.class);
        Comment newcomment = new Comment();
        BeanUtils.copyProperties(comment, newcomment);
        newcomment.setNews(news);
        newcomment.setDeltag(NewsDicConstants.ICommon.DELETE_NO);
        newcomment.setStatus(NewsDicConstants.INews.Status.CHECKING);
        
        commentRepository.save(newcomment);
        return decide(true);
    }
    
    @GetMapping("edit")
    public ModelAndView edit(ModelAndView modelAndView, Long id)
    {
        modelAndView.addObject("comment", commentRepository.get(id, Comment.class));
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody Comment comment)
    {
        Comment commentdb = commentRepository.findUniqueBy("id", comment.getId(), Comment.class);
        BeanUtils.copyProperties(comment, commentdb, getNullPropertyNames(comment));
        commentRepository.update(commentdb);
        return decide(true);
    }
    
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除角色")
    public Result remove(@PathVariable Long id)
    {
        commentRepository.remove(id, Comment.class);
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
            DetachedCriteria dc = DetachedCriteria.forClass(Comment.class);
            dc.add(Restrictions.in("id", cdids));
            List<Comment> commentList = commentRepository.findByCriteria(dc);
            commentList.forEach(commentRepository::delete);
            return decide(true);
        }
        return decide(false);
    }
}
