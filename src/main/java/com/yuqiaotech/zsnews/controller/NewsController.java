
package com.yuqiaotech.zsnews.controller;

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
import com.yuqiaotech.zsnews.model.News;
import com.yuqiaotech.zsnews.model.NewsFollower;
import com.yuqiaotech.zsnews.service.INewsService;
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
	private BaseRepository<Channel, Long> channelRepository;

    @Autowired
    private BaseRepository<NewsFollower, Long> newsFollowerRepository;

    @Autowired
    private INewsService iNewsService;
    
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
		String userType = getCurrentUserType();
		Channel channel = null;
		if (!StringUtils.isEmpty(userType) && userType.equals(SysConstants.SECURITY_USERTYPE_FRONT)) {
			channel =  channelRepository.queryUniqueResult("from Channel where userinfo.id = " + userId, null);

		}else {
			channel = channelRepository.queryUniqueResult("from Channel where user.id = " + userId, null);
		}

		if(channel != null) {
			news.setAuthorChannel(channel);
		}

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


    @RequestMapping("/selectNews/{kind}")
    public Result selectNews(@PathVariable("kind") String kind,@RequestParam Map<String, Object> params) {
        return success(iNewsService.selectNews(kind, getCurrentUserId(), params));
    }
}
