
package com.yuqiaotech.zsnews.controller;

import com.yuqiaotech.common.logging.annotation.Logging;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.dao.PaginationSupport;
import com.yuqiaotech.common.web.domain.request.PageDomain;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;
import com.yuqiaotech.sysadmin.model.User;
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
public class NewsController extends BaseController
{
    private static String MODULE_PATH = "zsnews/news/";
    
    @Autowired
    private BaseRepository<News, Long> newsRepository;

    @Autowired
    private BaseRepository<User, Long> userRepository;

    @Autowired
    private BaseRepository<NewsFollower, Long> newsFollowerRepository;
    
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data(News news, PageDomain pageDomain)
    {
        DetachedCriteria dc = composeDetachedCriteria(news);
        PaginationSupport ps = newsRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    public DetachedCriteria composeDetachedCriteria(News news)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(News.class);
        return dc;
    }
    
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
        return JumpPage(MODULE_PATH + "add");
    }
    
    @PostMapping("save")
    public Result save(@RequestBody News news)
    {
        System.out.println(attachmentRoot);
        Long userId = getCurrentUserId();
        User user = userRepository.get(userId, User.class);
        news.setUser(user);
        news = newsRepository.save(news);
        String content = news.getContent();

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

    @GetMapping("edit")
    public ModelAndView edit(ModelAndView modelAndView, Long id)
    {
        modelAndView.addObject("news", newsRepository.get(id, News.class));
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody News news)
    {
        News newsdb = newsRepository.findUniqueBy("id", news.getId(), News.class);
        BeanUtils.copyProperties(news, newsdb, getNullPropertyNames(news));
        newsRepository.update(newsdb);
        return decide(true);
    }
    
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除角色")
    public Result remove(@PathVariable Long id)
    {
        newsRepository.remove(id, News.class);
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
            newsList.forEach(newsRepository::delete);
            return decide(true);
        }
        return decide(false);
    }
    
    @GetMapping("appListdata")
    public Result AppNewsData(ModelAndView modelAndView,@RequestParam Long id)
    {
    	
    	String sql =" SELECT *  FROM t_news t left join  ( SELECT  f_news_id  as id1,count(1) as number FROM t_comment "
    			+ " where f_type ='评论' group by f_news_id ) a on a.id1 =t.f_id left join  ( SELECT f_news_id  as id2, count(1) "
    			+ "as apprisecount FROM t_comment  where f_type ='回答' group by f_news_id ) b on b.id2 =t.f_id where f_channel_id ="+id 
    			+" order by f_display_order asc ";
    	List news = newsRepository.findMapByNativeSql(sql);	
    	Map result =new HashMap<>();
    	result.put("news", news);
        return success(result);
    }
    
    @GetMapping("newsDetail")
    public Result newsDetail(ModelAndView modelAndView,@RequestParam Long id,@RequestParam Long cid)
    {
    	
    	String sql ="select * FROM t_news where f_id ="+id;
    	List<Map<String, Object>> news = newsRepository.findMapByNativeSql(sql);
    	Map result =new HashMap<>();
    	String sqlfollower = "SELECT * FROM t_news_follower where f_news_id = "+id+" and  f_user_info_id ="+cid;
    	List follower= newsFollowerRepository.findMapByNativeSql(sqlfollower);
    	if(follower.isEmpty()){
    		result.put("exist", false);
    	}else{
    		result.put("exist", true);
    	}
    	result.put("news", news);
        return success(result);
    }
}
