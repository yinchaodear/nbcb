
package com.yuqiaotech.zsnews.controller;

import java.util.Arrays;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yuqiaotech.common.logging.annotation.Logging;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.dao.PaginationSupport;
import com.yuqiaotech.common.web.domain.request.PageDomain;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;
import com.yuqiaotech.zsnews.ChannelBean;
import com.yuqiaotech.zsnews.NewsDicConstants;
import com.yuqiaotech.zsnews.model.Category;
import com.yuqiaotech.zsnews.model.Channel;
import com.yuqiaotech.zsnews.model.ChannelFollower;
import com.yuqiaotech.zsnews.model.Column;

/**
 * 频道管理
 *
 */
@RestController
@RequestMapping(value = {"zsnews/channel", "ws/channel"})
@CrossOrigin(origins = "*", maxAge = 3600)
public class ChannelController extends BaseController
{
    private static String MODULE_PATH = "zsnews/channel/";
    
    @Autowired
    private BaseRepository<Channel, Long> channelRepository;
    
    @Autowired
    private BaseRepository<ChannelFollower, Long> channelFollowerRepository;
    
    @Autowired
    private BaseRepository<Category, Long> categoryRepository;
    
    @Autowired
    private BaseRepository<Column, Long> columnRepository;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data(Channel channel, PageDomain pageDomain)
    {
        DetachedCriteria dc = composeDetachedCriteria(channel);
        dc.addOrder(Order.asc("displayOrder"));
        PaginationSupport ps = channelRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
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
        dc.add(Restrictions.eq("kind", NewsDicConstants.IChannel.KIND.PD));
        return dc;
    }
    
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
        modelAndView.addObject("displayOrder", getNextDisplayOrder());
        modelAndView.setViewName(MODULE_PATH + "add");
        return modelAndView;
    }
    
    @PostMapping("save")
    public Result save(@RequestBody ChannelBean channelBean)
    {
        Channel channel = new Channel();
        BeanUtils.copyProperties(channelBean, channel);
        
        if (channelBean.getColumnid() != null)
        {
            Column column = columnRepository.findUniqueBy("id", channelBean.getColumnid(), Column.class);
            channel.setColumn(column);
        }
        channel.setStatus(NewsDicConstants.ICommon.STATUS_UP);//默认上架
        channel.setDeltag(NewsDicConstants.ICommon.DELETE_NO);//默认未删除
        channel.setKind(NewsDicConstants.IChannel.KIND.PD);
        channel.setUser(getCurrentUser());
        if (channel.getDisplayOrder() == null)
        {
            channel.setDisplayOrder(getNextDisplayOrder());
        }
        channelRepository.save(channel);
        return decide(true);
    }
    
    private Integer getNextDisplayOrder()
    { //查当前最大的顺序号
        String sql = "select ifnull(max(f_display_order),0) maxorder from t_channel where f_display_order is not null";
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
        if (channelBean.getColumnid() != null)
        {
            Column columndb = channeldb.getColumn();
            if (columndb == null || !columndb.getId().equals(channelBean.getColumnid()))
            {
                //如果原来没有。或者新的修改了
                column = columnRepository.findUniqueBy("id", channelBean.getColumnid(), Column.class);
            }
        }
        BeanUtils.copyProperties(channelBean, channeldb, getNullPropertyNames(channelBean));
        if (column != null)
        {
            channeldb.setColumn(column);
        }
        
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
        channeldb.setKind(NewsDicConstants.IChannel.KIND.PD);
        channeldb.setUser(getCurrentUser());
        channelRepository.update(channeldb);
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
    
    /*
     * 对于出现在首页的频道的代码,只取频道
     */
    @GetMapping("appListdata")
    public Result AppChannelData(ModelAndView modelAndView, @RequestParam Long cid)
    {
        String sql = "SELECT t.f_title, t1.f_id as cfid,t.f_id as f_id FROM t_channel_follower  "
            + "t1 inner join t_channel t on t1.f_channel_id = t.f_id  where t.f_kind='频道' and f_user_info_id =" + cid;
        List mymenu = channelRepository.findMapByNativeSql(sql);
        String sqlleft = " select f_title,f_id from t_channel  t  where f_id not in"
            + "   (SELECT  f_channel_id  FROM t_channel_follower where f_user_info_id =" + cid + ") and f_kind='频道' ";
        List moremenu = channelRepository.findMapByNativeSql(sqlleft);
        Map result = new HashMap<>();
        result.put("mymenu", mymenu);
        result.put("moremenu", moremenu);
        return success(result);
    }
    
    @GetMapping("appListchannel")
    public Result ChannelDataByType(ModelAndView modelAndView, @RequestParam String type)
    {
        System.out.println("ChannelController.ChannelDataByType()" + type);
        String sql = "SELECT t.f_title,t.f_id as f_id FROM t_channel t  where f_type =" + type;
        List channel = channelRepository.findMapByNativeSql(sql);
        Map result = new HashMap<>();
        result.put("channel", channel);
        return success(result);
    }
    
    @GetMapping("deleteselfchannel")
    public Result DeleteSelfChannel(ModelAndView modelAndView, @RequestParam Long cfid)
    {
        String sql = "delete from t_channel_follower where f_id=" + cfid;
        channelRepository.executeUpdateByNativeSql(sql, null);
        Map result = new HashMap<>();
        result.put("msg", "1");
        return success(result);
    }
    
    @GetMapping("addselfchannel")
    public Result AddSelfChannel(ModelAndView modelAndView, @RequestParam Long id, @RequestParam Long cid)
    {
        String sqlexist = "select  * from t_channel_follower where f_channel_id =" + id + " and f_user_info_id =" + cid;
        List exist = channelFollowerRepository.findMapByNativeSql(sqlexist);
        if (!exist.isEmpty())
        {
            Map result = new HashMap<>();
            result.put("msg", "2");//这里表示已经添加过,正常不会走到这段，怕数据出错
            return success(result);
        }
        String sql = "insert into t_channel_follower (f_channel_id,f_user_info_id) values (" + id + "," + cid + ")";
        channelRepository.executeUpdateByNativeSql(sql, null);
        Map result = new HashMap<>();
        result.put("msg", "1");
        return success(result);
    }
    
    //社区页面 查询小组等相关信息
    @GetMapping("querynewsCommunityGroup")
    public Result AppChannelGroup(ModelAndView modelAndView, @RequestParam Long cid, @RequestParam String category,
        @RequestParam String kind, @RequestParam String type)
    {
        System.out.println("NewsController.AppChannelGroup()" + category);
        String wheresql = "";
        if (!StringUtils.isEmpty(category) && !"所有".equals(category))
        {
            wheresql = " and t.f_category = '" + category + "'";
        }
        String wherekindandtype = " c.f_type ='" + type + "' and c.f_kind ='" + kind + "'";
        String sql = "SELECT f_category FROM t_channel where f_kind ='" + kind + "' and f_type ='" + type
            + "'  group by f_category";
        List categorygroup = channelRepository.findMapByNativeSql(sql);
        String sqlgroup =
            "SELECT t.* , b.* , case when c.number >=10000  then  concat(cast(  convert(c.number/10000,decimal(10,1)) as char),'万' )"
                + " else cast(c.number  as char)  end as number  FROM  t_channel t  left  join (select cf.f_id as cfid ,cf.f_channel_id as chid "
                + ",cf.f_user_info_id as cid from t_channel_follower cf inner join t_channel c  on c.f_id = "
                + "cf.f_channel_id  where f_user_info_id = " + cid + " and " + wherekindandtype
                + ") b on t.f_id = b.chid "
                + " left join (select c.f_id as channelid ,count(1) as number from t_channel_follower cf inner"
                + " join t_channel c  on c.f_id = cf.f_channel_id  where  " + wherekindandtype
                + " group by  cf.f_channel_id) c on c.channelid  = t.f_id" + " where t.f_type ='" + type
                + "' and t.f_kind ='" + kind + "'" + wheresql;
        List group = channelRepository.findMapByNativeSql(sqlgroup);
        Map result = new HashMap<>();
        result.put("category", categorygroup);
        result.put("groupchannel", group);
        return success(result);
    }
    
    //社区页面 查询小组等相关信息
    /*
     * cid 用户id 
     * id  channel id
     */
    @GetMapping("queryChannelDetail")
    public Result AppChannelGroup(ModelAndView modelAndView, @RequestParam Long cid, @RequestParam Long id)
    {
        
        String sql =
            "SELECT  t.* , b.* ,case when c.number >=10000  then  concat(cast(  convert(c.number/10000,decimal(10,1)) as char),'万' ) else cast(c.number  as char)  end as number "
                + "FROM  t_channel t  left  join (select cf.f_id as cfid ,cf.f_channel_id as chid ,cf.f_user_info_id "
                + "as cid from t_channel_follower cf inner join t_channel c  on c.f_id = cf.f_channel_id "
                + "where f_user_info_id = " + cid + ") b on t.f_id = b.chid "
                + "left join (select c.f_id as channelid ,count(1) as number from t_channel_follower cf inner join t_channel c  on c.f_id = cf.f_channel_id "
                + "where  c.f_id =" + id + " ) c on c.channelid  = t.f_id where t.f_id = " + id;
        List channel = channelRepository.findMapByNativeSql(sql);
        String sqlcategory =
            "SELECT * FROM t_category where f_channel_id = " + id + " and f_type ='局部' and f_show ='显示'";
        List category = categoryRepository.findMapByNativeSql(sqlcategory);
        Map result = new HashMap<>();
        result.put("channel", channel);
        result.put("category", category);
        return success(result);
    }
    
}
