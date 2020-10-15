
package com.yuqiaotech.zsnews.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuqiaotech.common.logging.annotation.Logging;
import com.yuqiaotech.common.tools.common.FileUtils;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.dao.PaginationSupport;
import com.yuqiaotech.common.web.domain.request.PageDomain;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;
import com.yuqiaotech.sysadmin.model.User;
import com.yuqiaotech.zsnews.NewsDicConstants;
import com.yuqiaotech.zsnews.bean.NewsBean;
import com.yuqiaotech.zsnews.model.Category;
import com.yuqiaotech.zsnews.model.Channel;
import com.yuqiaotech.zsnews.model.ChannelCategoryMapping;
import com.yuqiaotech.zsnews.model.Column;
import com.yuqiaotech.zsnews.model.News;
import com.yuqiaotech.zsnews.model.NewsCategory;
import com.yuqiaotech.zsnews.model.NewsChannel;
import com.yuqiaotech.zsnews.model.NewsFollower;
import com.yuqiaotech.zsnews.model.NewsTopic;
import com.yuqiaotech.zsnews.model.PicMapping;
import com.yuqiaotech.zsnews.model.Topic;
import com.yuqiaotech.zsnews.service.INewsService;

import sun.misc.BASE64Decoder;

/**
 * 政务资源
 */
@RestController
@RequestMapping(value = {"zsnews/hdnews", "ws/hdnews"})
public class HdNewsController extends BaseController
{
    private static String MODULE_PATH = "zsnews/hdnews/";
    
    @Autowired
    private BaseRepository<News, Long> newsRepository;
    
    @Autowired
    private BaseRepository<NewsChannel, Long> newsChannelRepository;
    
    @Autowired
    private BaseRepository<NewsTopic, Long> newsTopicRepository;
    
    @Autowired
    private BaseRepository<Topic, Long> topicRepository;
    
    @Autowired
    private BaseRepository<User, Long> userRepository;
    
    @Autowired
    private BaseRepository<Channel, Long> channelRepository;
    
    @Autowired
    private BaseRepository<NewsCategory, Long> newsCategoryRepository;
    
    @Autowired
    private BaseRepository<NewsFollower, Long> newsFollowerRepository;
    
    @Autowired
    private BaseRepository<Column, Long> columnRepository;
    
    @Autowired
    private BaseRepository<Category, Long> categoryRepository;
    
    @Autowired
    private BaseRepository<ChannelCategoryMapping, Long> channelCategoryMappingRepository;
    
    @Autowired
    private BaseRepository<PicMapping, Long> picMappingRepository;
    
    @Autowired
    private INewsService iNewsService;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("checkmain")
    public ModelAndView checkmain()
    {
        return JumpPage(MODULE_PATH + "checkmain");
    }
    
    @GetMapping("data")
    public ResultTable data(NewsBean news, PageDomain pageDomain)
    {
        String hql = buildSearchCondition(news);
        PaginationSupport ps =
            newsRepository.paginateByHql(hql, pageDomain.getPage(), pageDomain.getLimit(), new HashMap<>());
        
        List<NewsBean> newsBeanList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ps.getItems()))
        {
            List<News> newsList = ps.getItems();
            List<String> newsidlist = new ArrayList<>();
            for (News newstmp : newsList)
            {
                newsidlist.add(newstmp.getId().toString());
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
            
            for (News newstmp : newsList)
            {
                NewsBean newsBeanTmp = new NewsBean();
                BeanUtils.copyProperties(newstmp, newsBeanTmp);
                //接下来构造news所属栏目，频道，专题
                if (newsChannelMap.containsKey(newstmp.getId()))
                {
                    newsBeanTmp.setColumns(buildColumns(newsChannelMap.get(newstmp.getId())));
                    newsBeanTmp.setChannels(buildChannels(newsChannelMap.get(newstmp.getId())));
                    if (newsChannelMap.get(newstmp.getId()) != null && !newsChannelMap.get(newstmp.getId()).isEmpty())
                    {
                        newsBeanTmp.setChannelid(newsChannelMap.get(newstmp.getId()).get(0).getId().toString());
                    }
                    
                }
                newsBeanList.add(newsBeanTmp);
            }
        }
        return pageTable(newsBeanList, ps.getTotalCount());
    }
    
    /**
     * 根据频道列表构造栏目字符串
     * @param channelList
     * @return
     */
    private String buildColumns(List<Channel> channelList)
    {
        List<String> columnList = new ArrayList<>();
        for (Channel channel : channelList)
        {
            if (channel.getColumn() != null)
            {
                if (!columnList.contains(channel.getColumn().getTitle()))
                {
                    columnList.add(channel.getColumn().getTitle());
                }
            }
        }
        return com.yuqiaotech.common.tools.common.CollectionUtils.join(columnList, ",");
    }
    
    /**
     * 根据频道列表构造频道名称字符串
     * @param channelList
     * @return
     */
    private String buildChannels(List<Channel> channelList)
    {
        List<String> channelNameList = new ArrayList<>();
        for (Channel channel : channelList)
        {
            if (!channelNameList.contains(channel.getTitle()))
            {
                channelNameList.add(channel.getTitle());
            }
        }
        return com.yuqiaotech.common.tools.common.CollectionUtils.join(channelNameList, ",");
    }
    
    @GetMapping("channelTree")
    public ResultTable channelTree(Long cid, Long newsid)
    {
        //如果newsid不空，表示需要进行初始化
        Set<String> selectNodIdSet = new HashSet<>();
        if (newsid != null)
        {
            List<NewsChannel> ncList = newsChannelRepository.findByHql("from NewsChannel where news.id=" + newsid);
            List<NewsTopic> ntList = newsTopicRepository.findByHql("from NewsTopic where news.id=" + newsid);
            List<NewsCategory> ncategoryList =
                newsCategoryRepository.findByHql("from NewsCategory where news.id=" + newsid);
            
            for (NewsChannel nc : ncList)
            {
                selectNodIdSet.add("channel_" + nc.getChannel().getId());
            }
            
            for (NewsTopic nt : ntList)
            {
                selectNodIdSet.add("topic_" + nt.getTopic().getId());
            }
            for (NewsCategory nc : ncategoryList)
            {
                selectNodIdSet.add("category_" + nc.getCategory().getId());
            }
        }
        JSONArray treeArr = new JSONArray();
        if (cid == null)
        {
            //cid为空，构造首页的频道树
            List<Column> columnList = columnRepository.findByHql("from Column where status="
                + NewsDicConstants.ICommon.STATUS_UP + " and deltag=" + NewsDicConstants.ICommon.DELETE_NO);
            for (Column column : columnList)
            {
                JSONObject treeObj = new JSONObject();
                treeObj.put("id", "column_" + column.getId());
                treeObj.put("title", column.getTitle());
                treeObj.put("type", "column");
                treeObj.put("displayOrder", column.getDisplayOrder() == null ? 1 : column.getDisplayOrder());
                
                JSONArray children1 = new JSONArray();
                
                List<Channel> channelList = channelRepository.findByHql("from Channel where column.id=" + column.getId()
                    + " and status=" + NewsDicConstants.ICommon.STATUS_UP + " and deltag="
                    + NewsDicConstants.ICommon.DELETE_NO + " and kind='频道'");
                
                if (CollectionUtils.isNotEmpty(channelList))
                {
                    for (Channel channel : channelList)
                    {
                        JSONArray children2 = new JSONArray();
                        
                        JSONObject channelObj = new JSONObject();
                        channelObj.put("id", "channeljoin_" + channel.getId());
                        channelObj.put("title", channel.getTitle());
                        channelObj.put("type", "cj");
                        channelObj.put("displayOrder",
                            channel.getDisplayOrder() == null ? 1 : channel.getDisplayOrder());
                        
                        JSONObject channelObj1 = new JSONObject();
                        channelObj1.put("id", "channel_" + channel.getId());
                        channelObj1.put("title", channel.getTitle());
                        channelObj1.put("type", "channel");
                        channelObj1.put("displayOrder", 1);
                        channelObj1.put("checked", selectNodIdSet.contains(channelObj1.get("id")));
                        
                        children2.add(channelObj1);
                        
                        List<Topic> topicList = topicRepository.findByHql("from Topic where channel.id="
                            + channel.getId() + " and status=" + NewsDicConstants.ICommon.STATUS_UP + " and deltag="
                            + NewsDicConstants.ICommon.DELETE_NO);
                        
                        if (CollectionUtils.isNotEmpty(topicList))
                        {
                            JSONObject topicObjjoin = new JSONObject();
                            topicObjjoin.put("id", "topicjoin_" + channel.getId());
                            topicObjjoin.put("title", "专题");
                            topicObjjoin.put("type", "zt");
                            topicObjjoin.put("displayOrder", 2);
                            
                            JSONArray children3 = new JSONArray();
                            for (Topic topic : topicList)
                            {
                                JSONObject topicObj = new JSONObject();
                                topicObj.put("id", "topic_" + topic.getId());
                                topicObj.put("title", topic.getTitle());
                                topicObj.put("type", "topic");
                                topicObj.put("displayOrder", 1);
                                topicObj.put("checked", selectNodIdSet.contains(topicObj.get("id")));
                                children3.add(topicObj);
                            }
                            topicObjjoin.put("children", children3);
                            
                            children2.add(topicObjjoin);
                        }
                        
                        channelObj.put("children", children2);
                        children1.add(channelObj);
                    }
                    
                }
                if (!children1.isEmpty())
                {
                    treeObj.put("children", children1);
                }
                treeArr.add(treeObj);
            }
        }
        else
        {
            //构造浙商号的分类树
            Channel channel = channelRepository.findUniqueBy("id", cid, Channel.class);
            
            JSONObject channelObj = new JSONObject();
            channelObj.put("id", "channel_" + channel.getId());
            channelObj.put("title", channel.getTitle());
            channelObj.put("type", "channel");
            channelObj.put("displayOrder", 1);
            
            List<ChannelCategoryMapping> categoryMappingList = channelCategoryMappingRepository
                .findByHql("from ChannelCategoryMapping where channel.id=" + channel.getId() + " and category.status="
                    + NewsDicConstants.ICommon.STATUS_UP + " and category.deltag=" + NewsDicConstants.ICommon.DELETE_NO
                    + " order by category.displayOrder asc");
            
            JSONArray children = new JSONArray();
            for (ChannelCategoryMapping categoryMapping : categoryMappingList)
            {
                JSONObject categoryObj = new JSONObject();
                categoryObj.put("id", "category_" + categoryMapping.getCategory().getId());
                categoryObj.put("title", categoryMapping.getCategory().getTitle());
                categoryObj.put("type", "category");
                categoryObj.put("displayOrder", categoryMapping.getCategory().getDisplayOrder());
                categoryObj.put("checked", selectNodIdSet.contains(categoryObj.get("id")));
                
                children.add(categoryObj);
            }
            
            channelObj.put("children", children);
            treeArr.add(channelObj);
        }
        
        return dataTable(treeArr);
    }
    
    public DetachedCriteria composeDetachedCriteria(NewsBean news)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(News.class);
        //新闻资源管理列表，展示的是除浙商号资源以外的资源，不管状态为何
        //所以，如果authorChannel为空，标识没有关联浙商号
        if (StringUtils.isNotEmpty(news.getTitle()))
        {
            dc.add(Restrictions.or(Restrictions.ilike("title", news.getTitle(), MatchMode.ANYWHERE),
                Restrictions.ilike("content", news.getContent(), MatchMode.ANYWHERE)));
        }
        if (news.getStatus() != null)
        {
            dc.add(Restrictions.eq("status", news.getStatus()));
        }
        if (news.getIstop() != null)
        {
            dc.add(Restrictions.eq("istop", news.getIstop()));
        }
        if (StringUtils.isNotEmpty(news.getChannelid()))
        {
            
        }
        
        dc.add(Restrictions.isNull("authorChannel"));
        dc.addOrder(Order.desc("created"));
        return dc;
    }
    
    public String buildSearchCondition(NewsBean news)
    {
        String hql = "select n from News n";
        String condition = " where 1=1";
        if (StringUtils.isNotEmpty(news.getTitle()))
        {
            condition +=
                " and (n.title like '%" + news.getTitle() + "%' or n.content like '%" + news.getTitle() + "%')";
        }
        if (news.getStatus() != null)
        {
            condition += " and n.status = " + news.getStatus();
        }
        if (StringUtils.isNotEmpty(news.getChannelid()))
        {
            hql += " left join NewsChannel nc on n.id = nc.news.id";
            condition += " and nc.channel.id = " + news.getChannelid();
        }
        if (StringUtils.isNotEmpty(news.getType()))
        {
            condition += " and n.type = '" + news.getType() + "'";
        }
        condition += " and n.isform=" + NewsDicConstants.INews.Form.YES;//活动资源标记
        condition += " and n.deltag=" + NewsDicConstants.ICommon.DELETE_NO + " and n.kind='政务'";
        condition += " order by n.created desc";
        return hql + condition;
    }
    
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
        modelAndView.addObject("news_tid", System.currentTimeMillis());
        modelAndView.setViewName(MODULE_PATH + "add");
        return modelAndView;
    }
    
    @PostMapping("save")
    public Result save(@RequestBody NewsBean newsbean)
    {
        News news = new News();
        BeanUtils.copyProperties(newsbean, news);
        news.setUser(getCurrentUser());
        news.setFrom("平台");
        news.setKind("政务");
        news.setIsform(NewsDicConstants.INews.Form.YES);
        news.setDeltag(NewsDicConstants.ICommon.DELETE_NO);
        news.setStatus(NewsDicConstants.INews.Status.UP);//新增保存默认上架
        
        List<PicMapping> picmappinglist = new ArrayList<>();
        if (StringUtils.isNotEmpty(newsbean.getPicname1()))
        {
            PicMapping pm = new PicMapping();
            pm.setNews(news);
            pm.setDisplayOrder(1);
            pm.setPicpath(newsbean.getPicname1());
            picmappinglist.add(pm);
        }
        
        if (StringUtils.isNotEmpty(newsbean.getPicname2()))
        {
            news.setBgpicPath(newsbean.getPicname2());
        }
        
        news = newsRepository.save(news);
        
        if (StringUtils.isNotEmpty(newsbean.getChannelid()))
        {
            Long channelId = Long.valueOf(newsbean.getChannelid());
            Channel channeltmp = channelRepository.findUniqueBy("id", channelId, Channel.class);
            if (channeltmp != null)
            {
                NewsChannel nc = new NewsChannel();
                nc.setNews(news);
                nc.setChannel(channeltmp);
                newsChannelRepository.save(nc);
            }
        }
        
        String objectId = newsbean.getObjectId();
        String objectType = newsbean.getObjectType();
        
        if (CollectionUtils.isNotEmpty(picmappinglist))
        {
            for (PicMapping pm : picmappinglist)
            {
                picMappingRepository.save(pm);
            }
        }
        
        //把图片移动到material对应的ID下
        File srcFile = new File(attachmentRoot + "/" + objectType + "/" + objectId + "/");
        File dstFile = new File(attachmentRoot + "/" + objectType + "/" + news.getId() + "/");
        if (srcFile.exists())
        {
            try
            {
                org.apache.commons.io.FileUtils.copyDirectory(srcFile, dstFile);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            FileUtils.deleteDir(srcFile);
        }
        
        return decide(true);
    }
    
    private List<String> buildTreeIdList(JSONArray nodeArr)
    {
        List<String> nodeIdList = new ArrayList<>();
        for (int i = 0; i < nodeArr.size(); i++)
        {
            JSONObject nodeObj = nodeArr.getJSONObject(i);
            nodeIdList.add(nodeObj.getString("id"));
            if (nodeObj.containsKey("children"))
            {
                JSONArray childrenArr = nodeObj.getJSONArray("children");
                if (childrenArr != null && !childrenArr.isEmpty())
                {
                    nodeIdList.addAll(buildTreeIdList(childrenArr));
                }
            }
        }
        return nodeIdList;
    }
    
    @GetMapping("check/{nid}")
    public Result check(@PathVariable Long nid, Integer status, String msg)
    {
        News news = newsRepository.findUniqueBy("id", nid, News.class);
        if (news.getStatus() != NewsDicConstants.INews.Status.CHECKING)
        {
            return decide(false, null, "资源状态非审核中，不可审核");
        }
        
        if (status == NewsDicConstants.INews.Status.UP || status == NewsDicConstants.INews.Status.FAIL)
        {
            news.setStatus(status);
            news.setCheckDate(new Date());
            news.setCheckUser(getCurrentUser());
            news.setCheckResult(msg);
            newsRepository.update(news);
            return success();
        }
        else
        {
            return decide(false, null, "异常状态码");
        }
    }
    
    /**
     * 上下架
     * @param nid
     * @param status
     * @param msg
     * @return
     */
    @GetMapping("updown/{nid}")
    public Result updown(@PathVariable Long nid, Integer status)
    {
        News news = newsRepository.findUniqueBy("id", nid, News.class);
        
        if (status == NewsDicConstants.INews.Status.UP)
        {
            if (news.getStatus() != NewsDicConstants.INews.Status.DOWN)
            {
                return decide(false, null, "非下架状态资源不可上架");
            }
            else
            {
                news.setStatus(status);
                newsRepository.update(news);
            }
        }
        else if (status == NewsDicConstants.INews.Status.DOWN)
        {
            if (news.getStatus() != NewsDicConstants.INews.Status.UP)
            {
                return decide(false, null, "非上架资源不可下架");
            }
            else
            {
                news.setStatus(status);
                newsRepository.update(news);
            }
        }
        else
        {
            return decide(false, null, "异常状态码");
        }
        return success();
    }
    
    private String abstractImg(String s, News news)
    {
        String key = "data:image/png;base64,";
        int keyIndex = s.indexOf(key);
        if (keyIndex < 0)
        {
            key = "data:image/jpeg;base64,";
            keyIndex = s.indexOf(key);
        }
        if (keyIndex < 0)
        {
            key = "data:image/gif;base64,";
            keyIndex = s.indexOf(key);
        }
        if (keyIndex < 0)
        {
            key = "data:image/x-icon;base64,";
            keyIndex = s.indexOf(key);
        }
        if (keyIndex < 0)
        {
            return s;
        }
        int base64Start = keyIndex + key.length();
        int srcStart = s.lastIndexOf("\"", base64Start);
        int base64End = s.indexOf("\"", base64Start);
        String base64Str = s.substring(base64Start, base64End);
        Date now = new Date();
        
        String uploadPath = attachmentRoot + "/news/"; //设置保存目录
        if (news != null && news.getId() != null)
        {
            uploadPath += news.getId() + "/";
        }
        
        String fileName = java.util.UUID.randomUUID().toString() + ".jpg"; //采用UUID的方式随机命名
        File dirFile = new File(uploadPath);
        if (!dirFile.exists())
        {
            dirFile.mkdirs();
        }
        String saveTo = uploadPath + fileName;
        generateImage(base64Str, saveTo);
        String imgPath = "/attachment/showImage?objectType=news&objectId=" + news.getId() + "&fileName=" + fileName;
        
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String rtn = s.substring(0, srcStart + 1) + request.getContextPath() + imgPath + s.substring(base64End);
        return abstractImg(rtn, news);
    }
    
    private void generateImage(String realStr, String filePath)
    {
        BASE64Decoder decoder = new BASE64Decoder();
        BufferedOutputStream bos = null;
        try
        {
            File f = new File(filePath);
            if (!f.getParentFile().exists())
            {
                f.getParentFile().mkdirs();
            }
            byte[] data = decoder.decodeBuffer(realStr);
            bos = new BufferedOutputStream(new FileOutputStream(filePath));
            bos.write(data);
            bos.flush();
            bos.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @GetMapping("edit")
    public ModelAndView edit(ModelAndView modelAndView, Long id)
    {
        News news = newsRepository.get(id, News.class);
        NewsBean newsBean = new NewsBean();
        BeanUtils.copyProperties(news, newsBean);
        List<PicMapping> picMappingList =
            picMappingRepository.findByHql("from PicMapping where news.id=" + id + " order by displayOrder asc");
        if (CollectionUtils.isNotEmpty(picMappingList))
        {
            for (int i = 0; i < picMappingList.size(); i++)
            {
                if (i == 0)
                {
                    newsBean.setPicname1(picMappingList.get(i).getPicpath());
                }
                else if (i == 1)
                {
                    newsBean.setPicname2(picMappingList.get(i).getPicpath());
                }
                else if (i == 2)
                {
                    newsBean.setPicname3(picMappingList.get(i).getPicpath());
                }
                else
                {
                    break;
                }
            }
        }
        
        List<NewsChannel> nclist = newsChannelRepository.findByHql("from NewsChannel where news.id =" + id);
        if (CollectionUtils.isNotEmpty(nclist))
        {
            newsBean.setChannelid(nclist.get(0).getChannel().getId().toString());
        }
        
        modelAndView.addObject("news", newsBean);
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    @GetMapping("show")
    public ModelAndView show(ModelAndView modelAndView, Long id)
    {
        ModelAndView mv = edit(modelAndView, id);
        mv.setViewName(MODULE_PATH + "show");
        return mv;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody NewsBean newsbean)
    {
        News news = newsRepository.findUniqueBy("id", newsbean.getId(), News.class);
        BeanUtils.copyProperties(newsbean, news, getNullPropertyNames(newsbean));
        news.setUser(getCurrentUser());
        news.setFrom("平台");
        news.setKind("政务");
        news.setIsform(NewsDicConstants.INews.Form.YES);
        news.setDeltag(NewsDicConstants.ICommon.DELETE_NO);
        news.setStatus(NewsDicConstants.INews.Status.UP);//新增保存默认上架
        
        //先删除picmapping
        picMappingRepository.executeUpdate("delete from PicMapping where news.id=" + newsbean.getId(), new HashMap<>());
        
        List<PicMapping> picmappinglist = new ArrayList<>();
        if (StringUtils.isNotEmpty(newsbean.getPicname1()))
        {
            PicMapping pm = new PicMapping();
            pm.setNews(news);
            pm.setDisplayOrder(1);
            pm.setPicpath(newsbean.getPicname1());
            picmappinglist.add(pm);
        }
        
        news.setBgpicPath(newsbean.getPicname2());
        
        newsChannelRepository.executeUpdate("delete from NewsChannel where news.id=" + news.getId(), new HashMap<>());
        if (StringUtils.isNotEmpty(newsbean.getChannelid()))
        {
            Long channelId = Long.valueOf(newsbean.getChannelid());
            Channel channeltmp = channelRepository.findUniqueBy("id", channelId, Channel.class);
            if (channeltmp != null)
            {
                NewsChannel nc = new NewsChannel();
                nc.setNews(news);
                nc.setChannel(channeltmp);
                newsChannelRepository.save(nc);
            }
        }
        
        newsRepository.update(news);
        
        if (CollectionUtils.isNotEmpty(picmappinglist))
        {
            for (PicMapping pm : picmappinglist)
            {
                picMappingRepository.save(pm);
            }
        }
        
        return decide(true);
    }
    
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除角色")
    public Result remove(@PathVariable Long id)
    {
        News newsdb = newsRepository.findUniqueBy("id", id, News.class);
        if (newsdb.getStatus() == NewsDicConstants.INews.Status.UP)
        {
            return decide(false, null, "上架状态，不可删除");
        }
        newsdb.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
        newsdb.setStatus(NewsDicConstants.INews.Status.DOWN);
        newsRepository.update(newsdb);
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
            DetachedCriteria dc = DetachedCriteria.forClass(News.class);
            dc.add(Restrictions.in("id", cdids));
            List<News> newsList = newsRepository.findByCriteria(dc);
            for (News newsdb : newsList)
            {
                if (newsdb.getStatus() == NewsDicConstants.INews.Status.UP)
                {
                    continue;
                }
                newsdb.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
                newsdb.setStatus(NewsDicConstants.INews.Status.DOWN);
                newsRepository.update(newsdb);
            }
        }
        return decide(false);
    }
    
    /**
     * 社区文章查询
     * @param kind
     * @param params
     * @return
     */
    @RequestMapping("/selectNews")
    public Result selectNews(@RequestParam Map<String, Object> params)
    {
        return success(iNewsService.selectNews(getCurrentUserInfoId(), params));
    }
    
    /**
     * 社区文章详情
     * @param params
     * @return
     */
    @RequestMapping("/community/newsDetail")
    public Result getNewsDetail(@RequestParam Map<String, Object> params)
    {
        return success(iNewsService.getNewsDetail(getCurrentUserInfoId(), params));
    }
    
    /**
     * 社区文章点赞收藏
     * @param params
     * @return
     */
    @RequestMapping("/community/toggleCollectOrAgree")
    public Result toggleCollect(@RequestParam Map<String, Object> params)
    {
        return success(iNewsService.toggleCollectOrAgree(getCurrentUserInfoId(), params));
    }
    
    /**
     * 文章评论|回复评论|对回复对回复
     * @param params
     * @return
     */
    @RequestMapping("/makeComment")
    public Result makeComment(@RequestBody Map<String, Object> params)
    {
        return success(iNewsService.makeComment(getCurrentUserInfoId(), params));
    }
    
    /**
     * 文章评论|回复
     * @param params
     * @return
     */
    @RequestMapping("/comments")
    public Result selectComments(@RequestParam Map<String, Object> params)
    {
        return success(iNewsService.selectComments(getCurrentUserInfoId(), params));
    }
    
    /**
     * 社区文章评论（回复）点赞
     * @param params
     * @return
     */
    @RequestMapping("/toggleCommentAgree")
    public Result toggleCommentAgree(@RequestParam Map<String, Object> params)
    {
        return success(iNewsService.toggleCommentAgree(getCurrentUserInfoId(), params));
    }
}
