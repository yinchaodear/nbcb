
package com.yuqiaotech.zsnews.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
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
import com.yuqiaotech.zsnews.model.Channel;

@RestController
@RequestMapping("zsnews/channel")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ChannelController extends BaseController
{
    private static String MODULE_PATH = "zsnews/channel/";
    
    @Autowired
    private BaseRepository<Channel, Long> channelRepository;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data(Channel channel, PageDomain pageDomain)
    {
        DetachedCriteria dc = composeDetachedCriteria(channel);
        PaginationSupport ps = channelRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    public DetachedCriteria composeDetachedCriteria(Channel channel)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(Channel.class);
        return dc;
    }
    
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
        return JumpPage(MODULE_PATH + "add");
    }
    
    @PostMapping("save")
    public Result save(@RequestBody Channel channel)
    {
        channelRepository.save(channel);
        return decide(true);
    }
    
    @GetMapping("edit")
    public ModelAndView edit(ModelAndView modelAndView, Long id)
    {
        modelAndView.addObject("channel", channelRepository.get(id, Channel.class));
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody Channel channel)
    {
        Channel channeldb = channelRepository.findUniqueBy("id", channel.getId(), Channel.class);
        BeanUtils.copyProperties(channel, channeldb, getNullPropertyNames(channel));
        channelRepository.update(channeldb);
        return decide(true);
    }
    
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除角色")
    public Result remove(@PathVariable Long id)
    {
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
            channelList.forEach(channelRepository::delete);
            return decide(true);
        }
        return decide(false);
    }
    
    @GetMapping("appListdata")
    public Result AppChannelData(ModelAndView modelAndView,@RequestParam Long id)
    {
    	System.out.println("ChannelController.AppChannelData()"+id);
    	String sql ="SELECT t.f_title, t.f_id as f_id, t1.f_id as cfid FROM t_channel_follower  "
    			+ "t1 inner join t_channel t on t1.f_channel_id = t.f_id  where f_user_info_id ="+id;
    	List mymenu = channelRepository.findMapByNativeSql(sql);
    	String sqlleft =" select f_title,f_id from t_channel    where f_id not in"
    			+ "   (SELECT  f_channel_id  FROM t_channel_follower where f_user_info_id ="+id+")";   
    	List moremenu = channelRepository.findMapByNativeSql(sqlleft);
    	Map result =new HashMap<>();
    	result.put("mymenu", mymenu);
    	result.put("moremenu", moremenu);
        return success(result);
    }
    
    @GetMapping("deleteselfchannel")
    public Result DeleteSelfChannel(ModelAndView modelAndView,@RequestParam Long cfid)
    {
    	System.out.println("ChannelController.DeleteSelfChannel()"+cfid);
    	String sql ="delete from t_channel_follower where f_id="+cfid;
    	channelRepository.executeUpdateByNativeSql(sql, null);
    	Map result =new HashMap<>();
    	result.put("msg","1");
        return success(result);
    }
    
    @GetMapping("addselfchannel")
    public Result AddSelfChannel(ModelAndView modelAndView,@RequestParam Long id,@RequestParam Long cid)
    {
    	System.err.println(id);
    	System.err.println(cid);
        String sql = "insert into t_channel_follower (f_channel_id,f_user_info_id) values ("+id+","+cid+")";
        channelRepository.executeUpdateByNativeSql(sql, null);
//    	channelRepository.executeUpdateByNativeSql(sql, null);
    	Map result =new HashMap<>();
    	result.put("msg","1");
        return success(result);
    }
}
