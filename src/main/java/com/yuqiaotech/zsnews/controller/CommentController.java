
package com.yuqiaotech.zsnews.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.FetchMode;
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
    
    @GetMapping("checkmain")
    public ModelAndView checkmain(ModelAndView modelAndView, Long newsid)
    {
        modelAndView.addObject("currentnews", newsRepository.findUniqueBy("id", newsid, News.class));
        modelAndView.setViewName(MODULE_PATH + "checkmain");
        return modelAndView;
    }
    
    @GetMapping("data")
    public ResultTable data(CommentBean comment, PageDomain pageDomain)
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
    
    public DetachedCriteria composeDetachedCriteria(CommentBean comment)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(Comment.class);
        dc.setFetchMode("news", FetchMode.JOIN);
        if (comment.getNewsid() != null)
        {
            dc.add(Restrictions.eq("news.id", comment.getNewsid()));
        }
        if (StringUtils.isNotEmpty(comment.getNewstitle()))
        {
            dc.add(Restrictions.or(Restrictions.ilike("news.title", comment.getNewstitle(), MatchMode.ANYWHERE),
                Restrictions.ilike("news.content", comment.getNewstitle(), MatchMode.ANYWHERE)));
        }
        if (StringUtils.isNotEmpty(comment.getContent()))
        {
            dc.add(Restrictions.ilike("content", comment.getContent(), MatchMode.ANYWHERE));
        }
        if (comment.getStatus() != null)
        {
            dc.add(Restrictions.eq("status", comment.getStatus()));
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
    
    @GetMapping("addcomment")
    public ModelAndView addcomment(ModelAndView modelAndView, Long commentid)
    {
        modelAndView.addObject("currentcomment", commentRepository.findUniqueBy("id", commentid, Comment.class));
        modelAndView.setViewName(MODULE_PATH + "addcomment");
        return modelAndView;
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
    
    @PostMapping("saveComment")
    public Result saveComment(@RequestBody CommentBean comment)
    {
        if (comment.getCommentid() == null)
        {
            return decide(false, null, "请选择追评目标");
        }
        Comment oldComment = commentRepository.findUniqueBy("id", comment.getCommentid(), Comment.class);
        if (oldComment == null || oldComment.getStatus() != NewsDicConstants.INews.Status.UP)
        {
            return decide(false, null, "追评目标未上架");
        }
        Comment newcomment = new Comment();
        newcomment.setComment(oldComment);
        newcomment.setNews(oldComment.getNews());
        newcomment.setDeltag(NewsDicConstants.ICommon.DELETE_NO);
        newcomment.setStatus(NewsDicConstants.INews.Status.CHECKING);
        newcomment.setContent(comment.getContent());
        newcomment.setType("回复");
        
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
    
    @GetMapping("check/{cid}")
    public Result check(@PathVariable Long cid, Integer status, String msg)
    {
        Comment comment = commentRepository.findUniqueBy("id", cid, Comment.class);
        if (comment.getStatus() != NewsDicConstants.INews.Status.CHECKING)
        {
            return decide(false, null, "资源状态非审核中，不可审核");
        }
        
        if (status == NewsDicConstants.INews.Status.UP || status == NewsDicConstants.INews.Status.FAIL)
        {
            comment.setStatus(status);
            comment.setCheckDate(new Date());
            comment.setCheckUser(getCurrentUser());
            comment.setCheckResult(msg);
            commentRepository.update(comment);
            return success();
        }
        else
        {
            return decide(false, null, "异常状态码");
        }
    }
    
    @GetMapping("updown/{cid}")
    public Result updown(@PathVariable Long cid, Integer status)
    {
        Comment commentdb = commentRepository.findUniqueBy("id", cid, Comment.class);
        
        if (status == NewsDicConstants.INews.Status.UP)
        {
            if (commentdb.getStatus() != NewsDicConstants.INews.Status.DOWN)
            {
                return decide(false, null, "非下架状态资源不可上架");
            }
            else
            {
                commentdb.setStatus(status);
                commentRepository.update(commentdb);
            }
        }
        else if (status == NewsDicConstants.INews.Status.DOWN)
        {
            if (commentdb.getStatus() != NewsDicConstants.INews.Status.UP)
            {
                return decide(false, null, "非上架资源不可下架");
            }
            else
            {
                commentdb.setStatus(status);
                commentRepository.update(commentdb);
            }
        }
        else
        {
            return decide(false, null, "异常状态码");
        }
        return success();
    }
    
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除角色")
    public Result remove(@PathVariable Long id)
    {
        Comment commentdb = commentRepository.findUniqueBy("id", id, Comment.class);
        if (commentdb.getStatus() == NewsDicConstants.INews.Status.UP)
        {
            return decide(false, null, "上架状态，不可删除");
        }
        commentdb.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
        commentdb.setStatus(NewsDicConstants.INews.Status.DOWN);
        commentRepository.update(commentdb);
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
            
            for (Comment commentdb : commentList)
            {
                if (commentdb.getStatus() == NewsDicConstants.INews.Status.UP)
                {
                    continue;
                }
                commentdb.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
                commentdb.setStatus(NewsDicConstants.INews.Status.DOWN);
                commentRepository.update(commentdb);
            }
            
            return decide(true);
        }
        return decide(false);
    }
}
