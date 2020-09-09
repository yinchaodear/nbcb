
package com.yuqiaotech.zsnews.webservice;

import com.yuqiaotech.common.SysConstants;
import com.yuqiaotech.common.logging.annotation.Logging;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.dao.PaginationSupport;
import com.yuqiaotech.common.web.domain.request.PageDomain;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;
import com.yuqiaotech.sysadmin.model.User;
import com.yuqiaotech.zsnews.model.Channel;
import com.yuqiaotech.zsnews.model.Column;
import com.yuqiaotech.zsnews.model.HistorySearchRecord;
import com.yuqiaotech.zsnews.model.News;
import com.yuqiaotech.zsnews.model.NewsFollower;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = {"zsnews/news", "ws/news"})
public class NewsService extends BaseController
{
    private static String MODULE_PATH = "zsnews/news/";
    
    @Autowired
    private BaseRepository<News, Long> newsRepository;

    @Autowired
    private BaseRepository<User, Long> userRepository;

	@Autowired
	private BaseRepository<Channel, Long> channelRepository;

    @Autowired
    private BaseRepository<NewsFollower, Long> newsFollowerRepository;
    
    @Autowired
    private BaseRepository<Column, Long> columnRepository;
   
    @Autowired
    private BaseRepository<HistorySearchRecord, Long> historySearchRecordRepository;
    /*
     * 这里是首页进来的时候 判断的  推荐的目前就是所有的, 关注的就是自己关注的channel的 现在关注的 都是 authchannel  文章里面是
     */
    @GetMapping("appListdata")
    public Result AppNewsData(ModelAndView modelAndView,@RequestParam Long id,@RequestParam String type,@RequestParam Long cid)
    {
    	System.out.println("NewsService.AppNewsData()"+getCurrentUserId());
    	String wheresql ="where 1 =1 and c.f_kind ='频道'";
        if(type.equals("推荐")){
        	
        }else if(type.equals("关注")){
        	wheresql+= " and f_channel_id  in  ( select f_channel_id from t_channel_follower where f_user_info_id ="+getCurrentUserId()+" )";
        }else{
        	wheresql+= " and f_channel_id ="+id; 
        }
    	String sql =" SELECT  t.* ,d.f_title as channelname ,b.apprisecount as apprisecount FROM t_news t "
    			+" left join  ( SELECT f_news_id  as id2, count(1) "
    			+ " as apprisecount FROM t_comment  where f_type ='评论' or f_type ='回答' group by f_news_id ) b on b.id2 =t.f_id "
    			+ " inner join t_channel  c on  c.f_id = t.f_channel_id  inner join t_channel d on d.f_id =t.f_author_channel_id and"
    			+ " c.f_status = 0  "+wheresql
    			+" order by f_display_order asc ";
    	List news = newsRepository.findMapByNativeSql(sql);	
    	Map result =new HashMap<>();
    	result.put("news", news);
        return success(result);
    }
    
    
    /*
     * 获取新闻的详情等
     * cid:用户id
     * id:newsid
     */
    @GetMapping("newsDetail")
    public Result newsDetail(ModelAndView modelAndView,@RequestParam Long id,@RequestParam Long cid)
    {
    	
    	String sql ="select t.* ,c.f_title as channeltitle , c.f_remark as channelremark,cf.f_id as cfid ,n.f_id as nfid , a.cmid "
    			+ " , case when b.agreecount  >=10000  then  concat(cast(  convert(b.agreecount/10000,decimal(10,1)) as char),'万' )"
    			+ " else cast(b.agreecount   as char)  end as agreecount , "
    			+ " case when g.number  >=10000  then  concat(cast(  convert( g.number/10000,decimal(10,1)) as char),'万' )"
    			+ " else cast( g.number as char)  end as number"
    			+ " FROM t_news  "
    			+ " t left join t_channel c on c.f_id = t.f_author_channel_id  left join t_channel_follower cf "
    			+ " on t.f_author_channel_id = cf.f_channel_id  and cf.f_user_info_id =   "+getCurrentUserId()
    			+ " left join t_news_follower n on n.f_news_id = t.f_id and n.f_user_info_id = "+getCurrentUserId()
    			+ " left join (select  cm.f_id as cmid, cm.f_news_id as cmnewsid from t_comment "
    			+ " cm where   cm.f_type ='点赞'  and cm.f_news_id = "+id+" and cm.f_user_info_id ="+getCurrentUserId()+") a on a.cmnewsid = t.f_id"
    			+ " left join  ( SELECT  cm1.f_news_id  as  cm1newsid, count(1) as agreecount FROM t_comment cm1"
    			+ " where f_type ='点赞'  and cm1.f_news_id = "+id+" group by  cm1.f_news_id ) b on b.cm1newsid =t.f_id"
    			+ " left join (select cf.f_channel_id as channelid ,count(1) as number from t_channel_follower cf  group by cf.f_channel_id ) "
    			+ " g on g.channelid = t.f_author_channel_id"
    			+ " where t.f_id = " +id;
    	System.out.println(sql);
    	List<Map<String, Object>> news = newsRepository.findMapByNativeSql(sql);
    	Map result =new HashMap<>();
    	result.put("news", news);
        return success(result);
    }
    /*
     * 根据类型来 判断是 浙商号页面下的,还是小组,然后根据status 来判断是推荐，还是关注
     * 
     */    
    @GetMapping("querynewsByKindAndType")
    public Result AppNewsData(ModelAndView modelAndView,@RequestParam String kind,@RequestParam String type,@RequestParam String currentstatus,@RequestParam Long cid)
    {
    	
    	System.out.println("NewsController.AppNewsData()"+kind +type+currentstatus+getCurrentUserId());
    	String wheresql ="";
    	if("已关注".equals(currentstatus)){
    	  wheresql = "inner join t_channel_follower cf on cf.f_channel_id = t.f_channel_id and cf.f_user_info_id = "+getCurrentUserId();
    	} 	
    	String sql ="SELECT t.*  ,c.f_kind as channelkind,c.f_type as channeltype, a.apprisecount as apprisecount ,c.f_title as channelname "
    			+ "FROM t_news t left join  ( SELECT  f_news_id  as id1,count(1) as apprisecount FROM t_comment cm1 "
    			+ "where cm1.f_type ='评论' or cm1.f_type='回答' group by f_news_id ) a on a.id1 =t.f_id "
    			+ "inner join t_channel  c on  c.f_id = t.f_channel_id "
    			+  wheresql
    			+" where c.f_kind ='"+kind+"' and c.f_type ='"+type+"'"
    			+ " and c.f_status = 0  order by f_display_order asc ";
    	List news = newsRepository.findMapByNativeSql(sql);	
    	Map result =new HashMap<>();
    	result.put("news", news);
        return success(result);
    }
    
    
    
    /*
     * 根据类型来 判断是 浙商号页面下的,还是小组,然后根据status 来判断是推荐，还是关注
     * id：channel的id
     * category: 文章的category
     */    
    @GetMapping("querynewsByChannelID")
    public Result AppNewsDataChannel(ModelAndView modelAndView,@RequestParam Long id,@RequestParam String category)
    {
    	
    	System.out.println("NewsController.AppNewsDataChannel()"+category+id);
    	String wheresql ="";
    	if(!"综合".equals(category)){
    	  wheresql = " and t.f_category like '%"+category+"%'";
    	} 	
    	String sql ="SELECT  t.* ,d.f_title as channelname ,b.apprisecount as apprisecount FROM t_news t "
    			+" left join  ( SELECT f_news_id  as id2, count(1) "
    			+ "as apprisecount FROM t_comment  where f_type ='评论' or f_type ='回答' group by f_news_id ) b on b.id2 =t.f_id "
    			+ "inner join t_channel  c on  c.f_id = t.f_channel_id  inner join t_channel d on d.f_id =t.f_author_channel_id  "
    			+" where t.f_author_channel_id = "+id
    			+ wheresql
    			+" order by f_display_order asc ";
    	List news = newsRepository.findMapByNativeSql(sql);	
    	Map result =new HashMap<>();
    	result.put("news", news);
        return success(result);
    }
    
    
    
    //政务下面的文章 就固定是文章了，没有channel关联  分别为 政务,政务活动，政务通知
    @GetMapping("querynewsGovernment")
    public Result AppNewsGovernment(ModelAndView modelAndView,@RequestParam String type,@RequestParam String kind)
    {
    	System.out.println("NewsController.AppNewsGovernment()"+type+kind);
    	String sql ="SELECT t.*  ,c.f_kind as channelkind,c.f_type as channeltype, b.apprisecount as  apprisecount ,b1.agreecount,"
    			+ "c.f_title as channelname FROM t_news t "
    			+" left join  ( SELECT f_news_id  as id2, count(1) "
    			+ "as apprisecount FROM t_comment cm where cm.f_type ='回答' or cm.f_type ='评论' group by f_news_id ) b on b.id2 =t.f_id left join "
    			+ "( SELECT f_news_id  as id3, count(1)  as agreecount FROM t_comment cm1 where cm1.f_type ='点赞' group by f_news_id ) "
    			+ "b1 on b1.id3 =t.f_id"
    			+ " left join t_channel  c on  c.f_id = t.f_channel_id  where t.f_type='"+type+"' and t.f_kind='"+kind+"'"
    			+ " order by f_display_order asc ";
    	List news = newsRepository.findMapByNativeSql(sql);	
    	Map result =new HashMap<>();
    	result.put("news", news);
        return success(result);
    }
    
    
    //添加文章的收藏 
    @GetMapping("addselfcollect")
    public Result AddSelfCollect(ModelAndView modelAndView,@RequestParam Long nid,@RequestParam Long cid)
    {
    	String sqlexist ="select  * from t_news_follower where f_news_id ="+nid +" and f_user_info_id ="+getCurrentUserId();
    	List exist =newsFollowerRepository.findMapByNativeSql(sqlexist);
    	if(!exist.isEmpty()){
    		Map result =new HashMap<>();
        	result.put("msg","2");//这里表示已经添加过,正常不会走到这段，怕数据出错
            return success(result);
    	}
        String sql = "insert into t_news_follower (f_news_id,f_user_info_id) values ("+nid+","+getCurrentUserId()+")";
        newsFollowerRepository.executeUpdateByNativeSql(sql, null);
    	Map result =new HashMap<>();
    	result.put("msg","1");
        return success(result);
    }
    
    
    //删除文章的收藏
    @GetMapping("deleteselfcollect")
    public Result DeleteSelfnews(ModelAndView modelAndView,@RequestParam Long nfid)
    {
    	String sql ="delete from t_news_follower where f_id="+nfid;
    	newsFollowerRepository.executeUpdateByNativeSql(sql, null);
    	Map result =new HashMap<>();
    	result.put("msg","1");
        return success(result);
    }
    
    
   /*
    *  添加文章的点赞 
    *  nid:文章id
    *  cid:顾客id
    */
    
    @GetMapping("addselfagree")
    public Result AddSelfAgree(ModelAndView modelAndView,@RequestParam Long nid,@RequestParam Long cid)
    {
    	String sqlexist ="SELECT * FROM t_comment where f_news_id = "+nid+" and f_user_info_id = "+getCurrentUserId()+" and  f_type ='点赞'";
    	List exist =newsFollowerRepository.findMapByNativeSql(sqlexist);
    	if(!exist.isEmpty()){
    		Map result =new HashMap<>();
        	result.put("msg","2");//这里表示已经添加过,正常不会走到这段，怕数据出错
            return success(result);
    	}
        String sql = "insert into t_comment (f_news_id,f_user_info_id,f_type) values ("+nid+","+getCurrentUserId()+",'点赞')";
        newsFollowerRepository.executeUpdateByNativeSql(sql, null);
    	Map result =new HashMap<>();
    	result.put("msg","1");
        return success(result);
    }
    
    
    /*
     *  删除文章的点赞
     *  cmid:comment的id
     *  
     */
     
    @GetMapping("deleteselfagree")
    public Result DeleteSelfAgree(ModelAndView modelAndView,@RequestParam Long cmid)
    {
    	String sql ="delete from t_comment where f_id="+cmid;
    	newsFollowerRepository.executeUpdateByNativeSql(sql, null);
    	Map result =new HashMap<>();
    	result.put("msg","1");
        return success(result);
    }
    
    
    
    @PostMapping("saveapp")
    public Result saveapp(@RequestBody Map<String, Object> params)
    {
        System.out.println("NewsService.save()");
        Long userId = getCurrentUserId();
		String userType = getCurrentUserType();
		Long columnId =((Number) params.get("columnId")).longValue();
		String title =(String) params.get("title");
		String content =(String) params.get("content");
		String type =(String) params.get("type");
		if("文章".equals(type)){
			News news =new News();
			Channel channel = null;
			if (!StringUtils.isEmpty(userType) && userType.equals(SysConstants.SECURITY_USERTYPE_FRONT)) {
				channel =  channelRepository.queryUniqueResult("from Channel where userinfo.id = " + userId, null);
			}else {
				channel = channelRepository.queryUniqueResult("from Channel where user.id = " + userId, null);
			}
	        if(columnId!=null){
	        	Column column  =columnRepository.queryUniqueResult("from Column  where id = " + columnId, null);
	        	news.setColumn(column);
	        }     			
			if(channel != null) {
				news.setAuthorChannel(channel);
			}
	        news.setTitle(title);
	        news = newsRepository.save(news);
	        if (!StringUtils.isEmpty(content)) {
	            if (content.contains("data:image") && content.contains("base64")) {
	                content = abstractImg(content, news);
	            }
	            content = content.replace("&amp;", "&");
	            news.setContent(content);
	        }
	        if(!StringUtils.isEmpty(content)){
	            if(news.getContent().contains("<img>")){
	                news.setContent(news.getContent().replace("<img>", ""));
	            }
	        }
	        newsRepository.save(news);
		}
		
        return decide(true);
    }
  
    
    private String abstractImg(String s, News news) {
        String key = "data:image/png;base64,";
        int keyIndex = s.indexOf(key);
        if(keyIndex<0){
            key = "data:image/jpeg;base64,";
            keyIndex = s.indexOf(key);
        }
        if(keyIndex<0){
            key = "data:image/gif;base64,";
            keyIndex = s.indexOf(key);
        }
        if(keyIndex<0){
            key = "data:image/x-icon;base64,";
            keyIndex = s.indexOf(key);
        }
        if (keyIndex < 0) {
            return s;
        }
        int base64Start = keyIndex + key.length();
        int srcStart = s.lastIndexOf("\"", base64Start);
        int base64End = s.indexOf("\"", base64Start);
        String base64Str = s.substring(base64Start, base64End);
        Date now = new Date();

        String uploadPath = attachmentRoot+"/news/";   //设置保存目录
        if (news != null && news.getId() != null) {
            uploadPath += news.getId() + "/";
        }

        String fileName = java.util.UUID.randomUUID().toString()+ ".jpg";  //采用UUID的方式随机命名
        File dirFile = new File(uploadPath);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        String saveTo = uploadPath + fileName;
        generateImage(base64Str, saveTo);
        String imgPath = "/attachment/showImage?objectType=news&objectId=" + news.getId() + "&fileName="+fileName;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String rtn = s.substring(0, srcStart + 1) + request.getContextPath() + imgPath + s.substring(base64End);
        return abstractImg(rtn, news);
    }
    
    
    
    private void generateImage(String realStr, String filePath) {
        BASE64Decoder decoder = new BASE64Decoder();
        BufferedOutputStream bos = null;
        try {
            File f = new File(filePath);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            byte[] data = decoder.decodeBuffer(realStr);
            bos = new BufferedOutputStream(new FileOutputStream(filePath));
            bos.write(data);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
   /*
    * 搜索页面的内容   
    */
    @GetMapping("querynewsByKewords")
    public Result AppNewsByKewords(@RequestParam String keyword)
    {
    	
    	System.out.println("NewsService.AppNewsByKewords()"+keyword);
    	String sqlquery ="SELECT * FROM t_history_search_record where f_user_info_id =  "+getCurrentUserId()+" and f_content ='"+keyword+"' ";
    	List query = historySearchRecordRepository.findMapByNativeSql(sqlquery);
    	if(query.isEmpty()){
    		String sqlupdate ="insert into  t_history_search_record (f_user_info_id,f_content ) values ("+getCurrentUserId()+",'"+keyword+"')";
    		historySearchRecordRepository.executeUpdateByNativeSql(sqlupdate, null);
    	}
    	String wheresql =" where t.f_title like '%"+keyword+"%'"+" or t.f_content like '%"+keyword+"%'"+"or c.f_title like '%"+keyword+"%'";
    	String sql ="SELECT t.*  ,c.f_kind as channelkind,c.f_type as channeltype, b.apprisecount as  apprisecount ,b1.agreecount,"
    			+ "c.f_title as channelname FROM t_news t "
    			+" left join  ( SELECT f_news_id  as id2, count(1) "
    			+ "as apprisecount FROM t_comment cm where cm.f_type ='回答' or cm.f_type ='评论' group by f_news_id ) b on b.id2 =t.f_id left join "
    			+ "( SELECT f_news_id  as id3, count(1)  as agreecount FROM t_comment cm1 where cm1.f_type ='点赞' group by f_news_id ) "
    			+ "b1 on b1.id3 =t.f_id"
    			+ " left join t_channel  c on  c.f_id = t.f_channel_id "
    			+ wheresql
    			+ " order by f_display_order asc ";
    	System.err.println(sql);
    	List news = newsRepository.findMapByNativeSql(sql);	
    	Map result =new HashMap<>();
    	result.put("news", news);
        return success(result);
    }
    
  
}
