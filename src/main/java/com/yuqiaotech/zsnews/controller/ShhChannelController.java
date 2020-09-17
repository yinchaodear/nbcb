
package com.yuqiaotech.zsnews.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.yuqiaotech.common.tools.common.ImgBase64Utils;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.dao.PaginationSupport;
import com.yuqiaotech.common.web.domain.request.PageDomain;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;
import com.yuqiaotech.zsnews.NewsDicConstants;
import com.yuqiaotech.zsnews.bean.ChannelBean;
import com.yuqiaotech.zsnews.model.Category;
import com.yuqiaotech.zsnews.model.Channel;
import com.yuqiaotech.zsnews.model.ChannelCategoryMapping;
import com.yuqiaotech.zsnews.model.ChannelFollower;
import com.yuqiaotech.zsnews.model.Column;

/**
 * 浙商号管理
 *
 */
@RestController
@RequestMapping(value = {"zsnews/shhchannel"})
@CrossOrigin(origins = "*", maxAge = 3600)
public class ShhChannelController extends BaseController
{
    private static String MODULE_PATH = "zsnews/shhchannel/";
    
    @Autowired
    private BaseRepository<Channel, Long> channelRepository;
    
    @Autowired
    private BaseRepository<ChannelFollower, Long> channelFollowerRepository;
    
    @Autowired
    private BaseRepository<Category, Long> categoryRepository;
    
    @Autowired
    private BaseRepository<Column, Long> columnRepository;
    
    @Autowired
    private BaseRepository<ChannelCategoryMapping, Long> channelCategoryMappingRepository;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data(Channel channel, PageDomain pageDomain)
    {
        String querySql =
            "SELECT c1.*,GROUP_CONCAT(c2.f_title) title2 FROM t_channel c1 LEFT JOIN t_channe_catego_mappin mapping ON c1.f_id=mapping.f_channel_id LEFT JOIN t_category c2 ON mapping.f_category_id=c2.f_id WHERE c1.f_deltag=0  AND c1.f_kind='"
                + NewsDicConstants.IChannel.KIND.ZSH + "'";
        if (StringUtils.isNotEmpty(channel.getTitle()))
        {
            querySql += " and (c1.f_title like '%" + channel.getTitle() + "%' or c1.f_remark like '%"
                + channel.getTitle() + "%')";
        }
        if (channel.getStatus() != null)
        {
            querySql += " and c1.f_status =" + channel.getStatus();
        }
        querySql += " and c1.f_type ='商会号'";
        
        querySql += " GROUP BY c1.f_id order by c1.f_display_order asc";
        PaginationSupport ps =
            channelRepository.paginateAsMapByNativeSql(querySql, null, pageDomain.getPage(), pageDomain.getLimit());
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    @GetMapping("status/{cid}")
    public Result status(@PathVariable Long cid)
    {
        Channel channel = channelRepository.findUniqueBy("id", cid, Channel.class);
        if (channel != null)
        {
            if (channel.getStatus() == null || channel.getStatus() == NewsDicConstants.ICommon.STATUS_DOWN)
            {
                channel.setStatus(NewsDicConstants.ICommon.STATUS_UP);
            }
            else
            {
                channel.setStatus(NewsDicConstants.ICommon.STATUS_DOWN);
            }
            
            channelRepository.update(channel);
        }
        return success();
    }
    
    public DetachedCriteria composeDetachedCriteria(Channel channel)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(Channel.class);
        if (channel != null)
        {
            if (StringUtils.isNotEmpty(channel.getTitle()))
            {
                dc.add(Restrictions.or(Restrictions.ilike("title", channel.getTitle(), MatchMode.ANYWHERE),
                    Restrictions.ilike("remark", channel.getTitle(), MatchMode.ANYWHERE)));
            }
            if (channel.getStatus() != null)
            {
                dc.add(Restrictions.eq("status", channel.getStatus()));
            }
        }
        dc.add(Restrictions.eq("deltag", NewsDicConstants.ICommon.DELETE_NO));
        dc.add(Restrictions.eq("kind", NewsDicConstants.IChannel.KIND.ZSH));
        return dc;
    }
    
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
        modelAndView.addObject("displayOrder", getNextDisplayOrder());
        modelAndView.addObject("objectId", System.currentTimeMillis());
        modelAndView.setViewName(MODULE_PATH + "add");
        return modelAndView;
    }
    
    @PostMapping("save")
    public Result save(@RequestBody ChannelBean channelBean)
    {
        Channel channel = new Channel();
        BeanUtils.copyProperties(channelBean, channel);
        
        List<Category> clist = new ArrayList<>();
        if (StringUtils.isNotEmpty(channelBean.getCategoryids()))
        {
            String[] csarr = channelBean.getCategoryids().split(",");
            for (int i = 0; i < csarr.length; i++)
            {
                if (StringUtils.isNotEmpty(csarr[i]))
                {
                    Category c = categoryRepository.findUniqueBy("id", Long.valueOf(csarr[i]), Category.class);
                    if (c != null)
                    {
                        clist.add(c);
                    }
                }
            }
        }
        channel.setStatus(NewsDicConstants.ICommon.STATUS_UP);//默认上架
        channel.setDeltag(NewsDicConstants.ICommon.DELETE_NO);//默认未删除
        channel.setKind(NewsDicConstants.IChannel.KIND.ZSH);
        channel.setUser(getCurrentUser());
        if (channel.getDisplayOrder() == null)
        {
            channel.setDisplayOrder(getNextDisplayOrder());
        }
        
        if (StringUtils.isNotEmpty(channelBean.getPicname1()))
        {
            File srcFile = new File(attachmentRoot + "/" + channelBean.getObjectType() + "/" + channelBean.getObjectId()
                + "/" + channelBean.getPicname1() + ".small");
            if (srcFile != null && srcFile.exists() && srcFile.isFile())
            {
                //保存logo
                channel.setLogo(ImgBase64Utils.getImgStr(srcFile));
            }
        }
        
        channel = channelRepository.save(channel);
        for (Category category : clist)
        {
            ChannelCategoryMapping ccm = new ChannelCategoryMapping();
            ccm.setChannel(channel);
            ccm.setCategory(category);
            
            channelCategoryMappingRepository.save(ccm);
        }
        return decide(true);
    }
    
    private Integer getNextDisplayOrder()
    { //查当前最大的顺序号
        String sql =
            "select ifnull(max(f_display_order),0) maxorder from t_channel where f_kind='浙商号'    and f_display_order is not null";
        List<Map<String, Object>> rst = channelRepository.findMapByNativeSql(sql);
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
        modelAndView.addObject("channel", channelRepository.get(id, Channel.class));
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody ChannelBean channelBean)
    {
        Channel channeldb = channelRepository.findUniqueBy("id", channelBean.getId(), Channel.class);
        Column column = null;
        List<Category> clist = new ArrayList<>();
        if (StringUtils.isNotEmpty(channelBean.getCategoryids()))
        {
            String[] csarr = channelBean.getCategoryids().split(",");
            for (int i = 0; i < csarr.length; i++)
            {
                if (StringUtils.isNotEmpty(csarr[i]))
                {
                    Category c = categoryRepository.findUniqueBy("id", Long.valueOf(csarr[i]), Category.class);
                    if (c != null)
                    {
                        clist.add(c);
                    }
                }
            }
        }
        BeanUtils.copyProperties(channelBean, channeldb, getNullPropertyNames(channelBean));
        
        if (channeldb.getDisplayOrder() == null)
        {
            channeldb.setDisplayOrder(getNextDisplayOrder());
        }
        if (channeldb.getDeltag() == null)
        {
            channeldb.setDeltag(NewsDicConstants.ICommon.DELETE_NO);
        }
        if (channeldb.getStatus() == null)
        {
            channeldb.setStatus(NewsDicConstants.ICommon.STATUS_UP);
        }
        channeldb.setKind(NewsDicConstants.IChannel.KIND.ZSH);
        channeldb.setUser(getCurrentUser());
        
        if (StringUtils.isNotEmpty(channelBean.getPicname1()))
        {
            File srcFile = new File(attachmentRoot + "/" + channelBean.getObjectType() + "/" + channelBean.getObjectId()
                + "/" + channelBean.getPicname1() + ".small");
            if (srcFile != null && srcFile.exists() && srcFile.isFile())
            {
                //保存logo
                channeldb.setLogo(ImgBase64Utils.getImgStr(srcFile));
            }
        }
        else
        {
            channeldb.setLogo("");
        }
        
        channelRepository.update(channeldb);
        
        channelCategoryMappingRepository
            .executeUpdate("delete from ChannelCategoryMapping where channel.id=" + channeldb.getId(), new HashMap<>());
        for (Category category : clist)
        {
            ChannelCategoryMapping ccm = new ChannelCategoryMapping();
            ccm.setChannel(channeldb);
            ccm.setCategory(category);
            
            channelCategoryMappingRepository.save(ccm);
        }
        
        return decide(true);
    }
    
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除角色")
    public Result remove(@PathVariable Long id)
    {
        //TODO:这里要看，有没有关联，如果有的话，不能删除
        //FIXME:栏目删除为逻辑删除
        Channel channeldb = channelRepository.findUniqueBy("id", id, Channel.class);
        channeldb.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
        
        channelRepository.remove(id, Channel.class);
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
            DetachedCriteria dc = DetachedCriteria.forClass(Channel.class);
            dc.add(Restrictions.in("id", cdids));
            List<Channel> channelList = channelRepository.findByCriteria(dc);
            for (Channel channel : channelList)
            {
                channel.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
                channelRepository.update(channel);
            }
            return decide(true);
        }
        return decide(false);
    }
    
}
