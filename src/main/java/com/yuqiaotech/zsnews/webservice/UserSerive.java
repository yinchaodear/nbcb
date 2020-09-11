package com.yuqiaotech.zsnews.webservice;

import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.zsnews.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"zsapp/user", "ws/user"})
public class UserSerive extends BaseController {

    @Autowired
    private BaseRepository<UserInfo, Long> userinfoRepository;

    @Autowired
    private BaseRepository<NewsFollower, Long> newsFollowerRepository;

    @Autowired
    private BaseRepository<ChannelFollower, Long> channelFollowerRepository;

    //获取用户信息、问答数量、收藏数量
    @GetMapping("getUserInfo")
    public Result getUserInfo(Long userId) {
        Map result = new HashMap<>();
        UserInfo userInfo = userinfoRepository.get(userId, UserInfo.class);
        String sql = "SELECT COUNT(*) FROM `t_news_follower` WHERE f_user_info_id = " + userId;
        List<Map<String, Object>> newsFollower = userinfoRepository.findMapByNativeSql(sql);
        result.put("newsFollower", newsFollower.get(0).values());
        String sql2 = "SELECT COUNT(*) FROM `t_news` WHERE f_type = '问答' AND f_userinfo_id = " + userId;
        List<Map<String, Object>> wenda = userinfoRepository.findMapByNativeSql(sql2, null);
        result.put("wenda", wenda.get(0).values());
        result.put("userInfo", userInfo);
        return success(result);
    }

    //保存、修改用户信息
    @GetMapping("save")
    public Result save(Long userId) {
        UserInfo userInfo = new UserInfo();
        userinfoRepository.save(userInfo);
        return success("success");
    }

    //收藏列表
    public Result newsFollower(Long userId) {
        String sql = "SELECT f_title FROM t_news WHERE f_id IN (SELECT f_news_id FROM `t_news_follower` WHERE f_user_info_id = " + userId + " )";
        List<Map<String, Object>> userFollowerList = userinfoRepository.findMapByNativeSql(sql, null);
        Map result = new HashMap();
        result.put("userFollowerList", userFollowerList);
        return success(userFollowerList);
    }

    //取消收藏
    public Result delNewsFollower(Long userId, Long newsId) {
//        String sql = "SELECT f_id FROM `t_news_follower` WHERE f_user_info_id= "+userId+" AND f_news_id = "+newsId;
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        News news = new News();
        news.setId(newsId);
        NewsFollower newsFollower = new NewsFollower();
        newsFollower.setNews(news);
        newsFollower.setUserInfo(userInfo);
        newsFollowerRepository.delete(newsFollower);
        return success("success");
    }

    //关注列表
    public Result channelFollower(Long userId) {
        String sql = "SELECT f_title,f_remark FROM t_channel WHERE f_id IN (SELECT f_channel_id FROM `t_channel_follower` WHERE f_user_info_id = " + userId + " )";
        List<Map<String, Object>> userFollowerList = userinfoRepository.findMapByNativeSql(sql, null);
        Map result = new HashMap();
        result.put("userFollowerList", userFollowerList);
        return success(userFollowerList);
    }

    //取消关注
    public Result delChannelFollower(Long userId, Long channelId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        Channel channel = new Channel();
        channel.setId(channelId);
        ChannelFollower channelFollower = new ChannelFollower();
        channelFollower.setChannel(channel);
        channelFollower.setUserInfo(userInfo);
        channelFollowerRepository.delete(channelFollower);
        return success("success");
    }


    //投稿列表
    @GetMapping("tougao")
    public Result tougao(Long userId) {
//        String sql = "SELECT f_title FROM `t_news` WHERE f_type='投稿' AND \tf_userinfo_id = " + userId;
        String sql = "SELECT * FROM t_channel";
        List<Map<String, Object>> tougaoList = userinfoRepository.findMapByNativeSql(sql, null);
        Map result = new HashMap();
        result.put("tougaoList", tougaoList);
        return success(result);
    }


    //问答列表
    public Result wenda(Long userId) {
        String sql = "SELECT f_title FROM `t_news` WHERE f_type='投稿' AND \tf_userinfo_id = " + userId;
        List<Map<String, Object>> wendaList = userinfoRepository.findMapByNativeSql(sql, null);
        Map result = new HashMap();
        result.put("wendaList", wendaList);
        return success(result);
    }
}
