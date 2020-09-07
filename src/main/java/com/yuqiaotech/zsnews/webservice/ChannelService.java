
package com.yuqiaotech.zsnews.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.response.Result;
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
public class ChannelService extends BaseController
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
    
    /*
     * 对于出现在首页的频道的代码,只取频道
     */
    @GetMapping("appListdata")
    public Result AppChannelData(ModelAndView modelAndView, @RequestParam Long cid)
    {
        System.out.println("ChannelService.AppChannelData()" + getCurrentUserId());
        String sql = "SELECT t.f_title, t1.f_id as cfid,t.f_id as f_id FROM t_channel_follower  "
            + "t1 inner join t_channel t on t1.f_channel_id = t.f_id  where t.f_kind='频道' and f_user_info_id ="
            + getCurrentUserId();
        List mymenu = channelRepository.findMapByNativeSql(sql);
        String sqlleft = " select f_title,f_id from t_channel  t  where f_id not in"
            + "   (SELECT  f_channel_id  FROM t_channel_follower where f_user_info_id =" + getCurrentUserId()
            + ") and f_kind='频道' ";
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
        System.out.println("ChannelController.AddSelfChannel()" + getCurrentUserId());
        String sqlexist = "select  * from t_channel_follower where f_channel_id =" + id + " and f_user_info_id ="
            + getCurrentUserId();
        List exist = channelFollowerRepository.findMapByNativeSql(sqlexist);
        if (!exist.isEmpty())
        {
            Map result = new HashMap<>();
            result.put("msg", "2");//这里表示已经添加过,正常不会走到这段，怕数据出错
        }
        String sql = "insert into t_channel_follower (f_channel_id,f_user_info_id) values (" + id + ","
            + getCurrentUserId() + ")";
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
                + "cf.f_channel_id  where f_user_info_id = " + getCurrentUserId() + " and " + wherekindandtype
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
                + "where f_user_info_id = " + getCurrentUserId() + ") b on t.f_id = b.chid "
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
