
package com.yuqiaotech.zsnews.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yuqiaotech.common.tools.UcpaasSms.UcpaasSms;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.sysadmin.model.User;
import com.yuqiaotech.zsnews.model.Category;
import com.yuqiaotech.zsnews.model.Channel;
import com.yuqiaotech.zsnews.model.ChannelFollower;
import com.yuqiaotech.zsnews.model.Column;
import com.yuqiaotech.zsnews.model.News;
import com.yuqiaotech.zsnews.model.UserInfo;

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
    
	@Autowired
	private BaseRepository<News, Long> newsRepository;
    
    @Autowired
    private BaseRepository<UserInfo, Long> userInfoRepository;
    /*
     * 对于出现在首页的频道的代码,只取频道
     */
    @GetMapping("appListdata")
    public Result AppChannelData(ModelAndView modelAndView, @RequestParam Long cid)
    {
        System.out.println("ChannelService.AppChannelData()" + getCurrentUserInfoId());
        String sql = "SELECT t.f_title, t1.f_id as cfid,t.f_id as f_id FROM t_channel_follower  "
            + "t1 inner join t_channel t on t1.f_channel_id = t.f_id  where t.f_kind='频道' and  t.f_title!='推荐' and t.f_title!='热点'  and t.f_status= 0 "
            + " and t.f_deltag =0 and f_user_info_id ="
            + getCurrentUserInfoId();
        List mymenu = channelRepository.findMapByNativeSql(sql);
        String sqlall = " select f_title,f_id,f_showtag from t_channel  t  where "
            + " f_kind='频道' and  t.f_title!='推荐' and t.f_title!='热点'  and t.f_status= 0  and t.f_deltag =0 order by  f_display_order asc";
        List menuall = channelRepository.findMapByNativeSql( sqlall);
        Map result = new HashMap<>();
        result.put("mymenu", mymenu);
        result.put("moremenu",menuall);
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
        checkChannelCollects();
        Map result = new HashMap<>();
        result.put("msg", "1");
        return success(result);
    }
    
    //更新所有的频道的关注人数
    public void checkChannelCollects(){
       String sql ="update t_channel ch  inner join ( "
       		+ " select c.f_id as channelid ,count(1) as number from t_channel_follower cf inner "
       		+ " join t_channel c  on c.f_id = cf.f_channel_id "
       		+ " group by  cf.f_channel_id ) b on b.channelid =ch.f_id "
       		+ " set ch.f_collects =  b.number";
       channelRepository.executeUpdateByNativeSql(sql, null);
    }
    
    @GetMapping("addselfchannel")
    public Result AddSelfChannel(ModelAndView modelAndView, @RequestParam Long id, @RequestParam Long cid)
    {
        System.out.println("ChannelController.AddSelfChannel()" + getCurrentUserInfoId());
        String sqlexist = "select  * from t_channel_follower where f_channel_id =" + id + " and f_user_info_id ="
            + getCurrentUserInfoId();
        List exist = channelFollowerRepository.findMapByNativeSql(sqlexist);
        if (!exist.isEmpty())
        {
            Map result = new HashMap<>();
            result.put("msg", "2");//这里表示已经添加过,正常不会走到这段，怕数据出错
        }
        String sql = "insert into t_channel_follower (f_channel_id,f_user_info_id) values (" + id + ","
            + getCurrentUserInfoId() + ")";
        channelRepository.executeUpdateByNativeSql(sql, null);
        checkChannelCollects();
        Map result = new HashMap<>();
        result.put("msg", "1");
        return success(result);
    }
    
    //浙商号页面 查询浙商号相关信息
    @GetMapping("querynewsCommunityGroup")
    public Result AppChannelGroup(ModelAndView modelAndView, @RequestParam Long cid, @RequestParam String category,
        @RequestParam String kind, @RequestParam String type)
    {
        System.out.println("NewsController.AppChannelGroup()" + category);
        String wheresql = "";
        if (!StringUtils.isEmpty(category) && !"所有".equals(category))
        {
            wheresql = " and cate.f_title = '" + category + "'";
        }
        String wherekindandtype = " c.f_type ='" + type + "' and c.f_kind ='" + kind + "'";
        String sql = "SELECT  c.f_title as f_category  FROM t_channe_catego_mappin m inner join t_channel t "
        		+ " on t.f_id =m.f_channel_id inner join t_category c on c.f_id =m.f_category_id  "
        		+ " where t.f_kind = '"+kind+"' and t.f_type ='"+type+"' group by c.f_title";
        List categorygroup = channelRepository.findMapByNativeSql(sql);
        String sqlgroup = "SELECT  distinct t.f_title,t.f_remark ,t.f_id,  b.* , case when t.f_collects >=10000  then  concat(cast(  convert(t.f_collects/10000,decimal(10,1)) as char),'万' )"
                + " else cast(t.f_collects  as char)  end as number  FROM  t_channel t  left  join (select cf.f_id as cfid ,cf.f_channel_id as chid "
                + ",cf.f_user_info_id as cid from t_channel_follower cf inner join t_channel c  on c.f_id = "
                + "cf.f_channel_id  where f_user_info_id = " + getCurrentUserInfoId() + " and " + wherekindandtype
                + ") b on t.f_id = b.chid "
                + "inner join  t_channe_catego_mappin tcm on t.f_id =tcm.f_channel_id inner join t_category cate "
                + " on  cate.f_id =tcm.f_category_id  "
                + " where t.f_status= 0 and t.f_deltag =0  and  t.f_type ='" + type
                + "' and t.f_kind ='" + kind + "'" + wheresql;
        List group = channelRepository.findMapByNativeSql(sqlgroup);
        Map result = new HashMap<>();
        result.put("category", categorygroup);
        result.put("groupchannel", group);
        return success(result);
    }
    
    
    //社区 查询小组等
    @GetMapping("querynewsCommunityGroupSheQu")
    public Result AppChannelGroupSheQu(ModelAndView modelAndView, @RequestParam Long cid, @RequestParam String category,
        @RequestParam String kind, @RequestParam String type)
    {
        System.out.println("ChannelService.AppChannelGroupSheQu()");
        String wheresql = "";
        if (!StringUtils.isEmpty(category) && !"所有".equals(category))
        {
            wheresql = " and cate.f_title = '" + category + "'";
        }
        String wherekindandtype = " c.f_kind ='" + kind + "'";
        String sql = "SELECT  c.f_title as f_category  FROM t_channe_catego_mappin m inner join t_channel t "
        		+ " on t.f_id =m.f_channel_id inner join t_category c on c.f_id =m.f_category_id  "
        		+ " where t.f_kind = '"+kind+"'  group by c.f_title";
        List categorygroup = channelRepository.findMapByNativeSql(sql);
        String sqlgroup = "SELECT  distinct t.f_title,t.f_remark ,t.f_id,  convert(t.f_Logo using utf8) as logo, b.* , case when t.f_collects >=10000  then  concat(cast(  convert(t.f_collects/10000,decimal(10,1)) as char),'万' )"
                + " else cast(t.f_collects  as char)  end as number,  "
                + " case when chan.questionnumber >=10000  then "
                + "concat(cast(  convert(chan.questionnumber/10000,decimal(10,1)) as char),'万' )"
                + " else cast(chan.questionnumber  as char)  end as questionnumber "         
                + " FROM  t_channel t  left  join (select cf.f_id as cfid ,cf.f_channel_id as chid "
                + ",cf.f_user_info_id as cid from t_channel_follower cf inner join t_channel c  on c.f_id = "
                + "cf.f_channel_id  where f_user_info_id = " + getCurrentUserInfoId() + " and " + wherekindandtype
                + ") b on t.f_id = b.chid "
                + " left join (select c.f_id as channelid ,count(1) as number from t_channel_follower cf inner"
                + " join t_channel c  on c.f_id = cf.f_channel_id  where  " + wherekindandtype
                + " group by  cf.f_channel_id) c on c.channelid  = t.f_id "
                + "inner join  t_channe_catego_mappin tcm on t.f_id =tcm.f_channel_id inner join t_category cate "
                + " on  cate.f_id =tcm.f_category_id  "
                + " left join ( SELECT     ch.f_id as channelid ,  count(1) as questionnumber FROM t_news "
                + " t inner join t_news_channel nc on nc.f_news_id = "
                + " t.f_id  inner join t_channel ch on ch.f_id = nc.f_channel_id and ch.f_kind ='小组' group by ch.f_id) "
                + "chan on chan.channelid = t.f_id " 
                + " where t.f_status= 0 and t.f_deltag =0 "
                + " and t.f_kind ='" + kind + "'" + wheresql;
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
            "SELECT  t.* , convert(t.f_Logo using utf8) as logo, b.* ,case when c.number >=10000  then  concat(cast(  convert(c.number/10000,decimal(10,1)) as char),'万' ) else cast(c.number  as char)  end as number "
                + "FROM  t_channel t  left  join (select cf.f_id as cfid ,cf.f_channel_id as chid ,cf.f_user_info_id "
                + "as cid from t_channel_follower cf inner join t_channel c  on c.f_id = cf.f_channel_id "
                + "where f_user_info_id = " + getCurrentUserInfoId() + ") b on t.f_id = b.chid "
                + "left join (select c.f_id as channelid ,count(1) as number from t_channel_follower cf inner join t_channel c  on c.f_id = cf.f_channel_id "
                + "where  c.f_id =" + id + " ) c on c.channelid  = t.f_id where t.f_id = " + id;
        List channel = channelRepository.findMapByNativeSql(sql);
        String sqlcategory = "SELECT c.* FROM t_channe_catego_mappin cm inner join t_category "
        		+ "c on c.f_id =cm.f_category_id where cm.f_channel_id =  "+id+" and c.f_status = 0";          
        List category = categoryRepository.findMapByNativeSql(sqlcategory);
        Map result = new HashMap<>();
        result.put("channel", channel);
        result.put("category", category);
        return success(result);
    }
    
    
    //主题的详情
    /*
     * cid 用户id 
     * id  channel id
     */
    @GetMapping("queryTopicDetail")
    public Result AppTopicGroup(ModelAndView modelAndView, @RequestParam Long id,@RequestParam Long nid)
    {
        
        String sql ="SELECT * FROM t_topic where f_id ="+id;
        List channel = channelRepository.findMapByNativeSql(sql);
    	sql = "SELECT "
				+ " case when t.f_collects  >=10000  then "
				+ " concat(cast(  convert(t.f_collects/10000,decimal(10,1)) as char),'万' ) "
				+ " else cast(t.f_collects   as char)  end as collects , "
				+ " t.f_video_path, t.f_id, t.f_title ,t.f_media_type,t.f_displaytype,t.f_check_date ,t.f_display_order ,"
				+ "case when t.f_comments >=10000  then  "
				+ " concat(cast(  convert(t.f_comments/10000,decimal(10,1)) as char),'万' ) else cast(t.f_comments as char)  end as apprisecount "
				+ " ,pm1.imgs,top.f_title as toptitle FROM t_news t "
				+ "left join  (select pm.f_news_id as pmid , group_concat(f_picpath) as imgs from t_pic_mapping pm group by pm.f_news_id )"
				+ " pm1 on pm1.pmid = t.f_id "
				+ " inner join t_news_topic nt on t.f_id = nt.f_news_id "
				+ "  inner join t_topic top on top.f_id = nt.f_topic_id   "
				+ " where t.f_status=0 and t.f_id ="+nid;
    	List news =newsRepository.findMapByNativeSql(sql);
				
        Map result = new HashMap<>();
        result.put("channel", channel);
        result.put("news", news);
        return success(result);
    }
    
    
    /*
     * 获取政务的title
     */
    @GetMapping("governmenttitle")
    public Result GovernMentTitle()
    {
        System.out.println("ChannelService.GovernMentTitle()");
        String sql = "SELECT t.f_title,t.f_id as f_id FROM t_channel t  "
            + "  where t.f_kind='政务'  and t.f_status= 0 ";
        List mymenu = channelRepository.findMapByNativeSql(sql);
        Map result = new HashMap<>();
        result.put("mymenu", mymenu);
        return success(result);
    }
    
    /*
     * 获取平台配置的默认背景头像
     */
    @GetMapping("platform")
    public Result Platform()
    {
        System.out.println("ChannelService.Platform()");
        String sql = "SELECT *, convert(f_logo using utf8) as logo FROM  t_platform";
        List paltfrom = channelRepository.findMapByNativeSql(sql);
        Map result = new HashMap<>();
        result.put("platform", paltfrom); 
        return success(result);
    }
    
    

}
