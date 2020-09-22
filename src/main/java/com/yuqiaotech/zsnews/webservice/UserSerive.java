package com.yuqiaotech.zsnews.webservice;

import com.yuqiaotech.common.tools.common.StringUtils;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.zsnews.NewsDicConstants;
import com.yuqiaotech.zsnews.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"zsapp/user", "ws/user"})
public class UserSerive extends BaseController implements NewsDicConstants {

    @Autowired
    private BaseRepository<UserInfo, Long> userinfoRepository;

    @Autowired
    private BaseRepository<NewsFollower, Long> newsFollowerRepository;

    @Autowired
    private BaseRepository<Channel, Long> channelRepository;

    @Autowired
    private BaseRepository<ChannelFollower, Long> channelFollowerRepository;

    @Autowired
    private BaseRepository<Feedback, Long> feedbackBaseRepository;

    @Autowired
    private BaseRepository<HistoryIntegral, Long> historyIntegralRepository;

    @Autowired
    private BaseRepository<News, Long> newsRepository;

    @Autowired
    private BaseRepository<Comment, Long> commentRepository;

    @Autowired
    private BaseRepository<SysNotice, Long> sysNoticeRepository;

    @Autowired
    private BaseRepository<FAQ, Long> FAQRepository;


    //获取用户信息、问答数量、收藏数量
    @GetMapping("getUserInfo")
    public Result getUserInfo() {
        Map result = new HashMap<>();
        UserInfo userInfo = userinfoRepository.get(getCurrentUserInfoId(), UserInfo.class);
        String sql = "SELECT COUNT(*) FROM t_news_follower WHERE f_user_info_id = " + getCurrentUserInfoId();
        List<Map<String, Object>> collection = commentRepository.findMapByNativeSql(sql);
        result.put("collection", collection.get(0).values());
        String sql3 = "SELECT COUNT(*) FROM t_news WHERE f_type = '提问' AND f_userinfo_id = " + getCurrentUserInfoId();
        List<Map<String, Object>> question = newsRepository.findMapByNativeSql(sql3);
        result.put("question", Long.valueOf(question.get(0).get("COUNT(*)").toString()));
        result.put("userInfo", userInfo);
        return success(result);
    }

    //保存、修改用户信息
    @PostMapping("save")
    public Result save(@RequestBody Map<String, Object> params) throws Exception {
        String type = params.get("type").toString();
        String value = params.get("value").toString();
        UserInfo userInfo = userinfoRepository.get(getCurrentUserInfoId(), UserInfo.class);
        String errMsg = null;
        if (StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(value)) {
            switch (type) {
                case "gender":
                    userInfo.setGender(value);
                    break;
                case "birthday":
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    userInfo.setBirthday(simpleDateFormat.parse(value));
                    break;
                case "region":
                    userInfo.setRegion(value);
                    break;
                case "nickName":
                    if (userInfo.getStatus() == IUserInfo.Status.CHECKING) {
                        errMsg = "用户信息正在审核";
                    } else {
                        userInfo.setNewNickName(value);
                        userInfo.setStatus(IUserInfo.Status.CHECKING);
                    }
                    break;
                case "remark":
                    if (userInfo.getStatus() == IUserInfo.Status.CHECKING) {
                        errMsg = "用户信息正在审核";
                    } else {
                        userInfo.setNewRemark(value);
                        userInfo.setStatus(IUserInfo.Status.CHECKING);
                    }
                    break;
                case "push":
                    userInfo.setPush(Integer.parseInt(value));
                    break;
                case "pwd":
                    if (StringUtils.isNotEmpty(value)){
                        userInfo.setPwd(value);
                    }else {
                        errMsg="新密码为空";
                    }
                    break;
                case "headImg":
                    if(StringUtils.isNotEmpty(value)){
                        if (userInfo.getStatus() == IUserInfo.Status.CHECKING) {
                            errMsg="用户信息正在审核";
                        }else {
                            userInfo.setNewAvatar(value);
                            userInfo.setStatus(IUserInfo.Status.CHECKING);
                        }
                    }else {
                        errMsg="头像是空的";
                    }
                    break;
            }
        }
        userinfoRepository.update(userInfo);
        Map result = new HashMap();
        result.put("errMsg", errMsg);
        return success(result);
    }

    //获取收藏文章列表
    @GetMapping("getCollection")
    public Result getCollection() {
//        String sql = "SELECT f_id,f_news_id,f_user_info_id FROM t_news_follower WHERE f_user_info_id = " + getCurrentUserInfoId();
        String sql = "SELECT t.* ,  d.f_title as channelname ,pm1.imgs , case when b.apprisecount  >=10000  then  concat(cast(  convert(b.apprisecount/10000,decimal(10,1)) as char),'万' )  else cast(b.apprisecount  as char)  end as apprisecount  FROM t_news t left join\t( SELECT f_news_id  as id2, count(1)  as apprisecount FROM t_comment where f_type ='评论' or f_type ='回答' group by f_news_id )  b on b.id2 =t.f_id left  join  t_channel d on d.f_id =t.f_author_channel_id left join  (select pm.f_news_id as pmid , group_concat(f_picpath) as imgs from t_pic_mapping pm group by pm.f_news_id ) pm1 on pm1.pmid = t.f_id where t.f_id IN (SELECT f_news_id FROM t_news_follower WHERE f_user_info_id = "+getCurrentUserInfoId()+") AND t.f_status = 0 order by f_display_order, f_updated desc";
        List<Map<String, Object>> collectionList = newsRepository.findMapByNativeSql(sql);
        Map result = new HashMap();
        result.put("collectionList", collectionList);
        return success(result);
    }

    //取消收藏
    @GetMapping("delCollection")
    public Result delCollection(Long newsId) {
        Map result = new HashMap();
        if (newsId != null) {
            String sql = "SELECT f_id FROM t_comment WHERE f_news_id = " + newsId + " AND f_type = '收藏' AND f_user_info_id = " + getCurrentUserInfoId();
            List<Map<String, Object>> collection = newsRepository.findMapByNativeSql(sql);
            if (!collection.isEmpty() && collection != null) {
                commentRepository.remove(Long.valueOf(collection.get(0).get("f_id").toString()), Comment.class);
                result.put("msg", "删除成功");
            }
        }
        return success(result);
    }

    //问答列表
    @GetMapping("getQuestion")
    public Result getQuestion() {
//        String sql = "SELECT f_id,f_title,f_content FROM t_news WHERE f_type = '提问' AND f_userinfo_id = " + getCurrentUserInfoId();
        String sql = "select convert(c.f_Logo using utf8) as logo,concat(t.f_id,'') newsId, ifnull(t.f_likes, 0) zanNum,ifnull(t.f_comments, 0) pinglunNum, ifnull(t.f_collects, 0) shoucangNum, ifnull(ui.f_likes, 0) userTotalNum,ui.f_username userName, t.f_title title, t.f_content content \n" +
                "from t_news_channel newsc \n" +
                "left join t_news t on t.f_id=newsc.f_news_id \n" +
                "left join t_channel nc on newsc.f_channel_id = nc.f_id and nc.f_kind = '小组' \n" +
                "left join t_channel c on t.f_author_channel_id = c.f_id \n" +
                "left join t_user_info ui on c.f_userinfo_id = ui.f_id\n" +
                "WHERE newsc.f_news_id IN (SELECT f_id FROM t_news WHERE f_type = '提问' AND f_userinfo_id = " + getCurrentUserInfoId() + " )\n" +
                "order by t.f_id desc LIMIT 0,10";
        List<Map<String, Object>> questionList = newsRepository.findMapByNativeSql(sql);
        Map result = new HashMap();
        result.put("questionList", questionList);
        return success(result);
    }

    //关注列表
    @GetMapping("getChannelFollower")
    public Result getChannelFollower() {
        String sql = "SELECT f_id,f_title,f_remark FROM t_channel WHERE f_id IN (SELECT f_channel_id FROM t_channel_follower WHERE f_user_info_id = " + getCurrentUserInfoId() + " )";
        List<Map<String, Object>> userFollowerList = userinfoRepository.findMapByNativeSql(sql);
        Map result = new HashMap();
        result.put("userFollowerList", userFollowerList);
        return success(result);
    }

    //取消关注
    @GetMapping("delChannelFollower")
    public Result delChannelFollower(Long channelId) {
//        String sql = "DELETE FROM t_channel_follower WHERE f_user_info_id = "+ getCurrentUserInfoId() +" AND f_channel_id = "+ channelId;
        String sql = "SELECT f_id FROM t_channel_follower WHERE f_user_info_id = " + getCurrentUserInfoId() + " AND f_channel_id = " + channelId;
        List<Map<String, Object>> id = channelFollowerRepository.findMapByNativeSql(sql);
        ChannelFollower channelFollower = new ChannelFollower();
        channelFollower.setId(Long.valueOf(id.get(0).get("f_id").toString()));
//        channelFollowerRepository.delete(channelFollower);
        channelFollowerRepository.remove(Long.valueOf(id.get(0).get("f_id").toString()), ChannelFollower.class);
        return success("success");
    }


    //投稿列表
    @GetMapping("tougao")
    public Result tougao() {
        String sql = "SELECT f_id,f_title,f_status,f_content,f_check_result  FROM t_news WHERE f_type='投稿' AND f_userinfo_id = " + getCurrentUserInfoId();
//        String sql = "SELECT * FROM t_channel";
        List<Map<String, Object>> tougaoList = userinfoRepository.findMapByNativeSql(sql, null);
        Map result = new HashMap();
        result.put("tougaoList", tougaoList);
        return success(result);
    }

    //根据ID获取投稿
    @GetMapping("getNews")
    public Result getNews(Long newsId) {
        News news = newsRepository.get(newsId, News.class);
        Map result = new HashMap();
        result.put("news", news);
        return success(result);
    }

    //用户反馈
    @GetMapping("feedback")
    public Result feedback(String feedbackContent) {
        Map result = new HashMap();
        if (StringUtils.isNotEmpty(feedbackContent)) {
            UserInfo userInfo = userinfoRepository.get(getCurrentUserInfoId(), UserInfo.class);
            Feedback feedback = new Feedback();
            feedback.setUserInfo(userInfo);
            feedback.setContent(feedbackContent);
            feedback.setStatus(IFeedback.Status.DOING);
            feedback.setFeedbackTime(new Date());
            feedbackBaseRepository.save(feedback);
        } else {
            result.put("errMsg", "请输入内容");
        }
        return success(result);
    }

    //所有反馈信息
    @GetMapping("getFeedback")
    public Result getFeedback() {
        String sql = "SELECT f_id,f_reply_content,f_content FROM t_feedback WHERE f_userinfo_id = " + getCurrentUserInfoId() + " ORDER BY f_feedback_time DESC";
        List<Map<String, Object>> feedbackList = feedbackBaseRepository.findMapByNativeSql(sql);
        Map result = new HashMap();
        result.put("feedbackList", feedbackList);
        return success(result);
    }

    //签到
    @GetMapping("saveIntegral")
    public Result saveIntegral() {
        String sql = "SELECT f_sign_days,f_persentintergral,f_occur_time FROM historyintegral WHERE f_user_id = " + getCurrentUserInfoId() + " ORDER BY f_occur_time DESC";
        List<Map<String, Object>> historyIntegralList = historyIntegralRepository.findMapByNativeSql(sql);
        Integer signDays = 0;
        Long persentintergral = 0L;
        String occurTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (historyIntegralList != null && !historyIntegralList.isEmpty()) {
            signDays = (Integer) historyIntegralList.get(0).get("f_sign_days");
            persentintergral = Long.valueOf(historyIntegralList.get(0).get("f_persentintergral").toString());
            occurTime = historyIntegralList.get(0).get("f_occur_time").toString();
            if (StringUtils.isNotEmpty(occurTime)) {
                occurTime = occurTime.substring(0, 10);
            }
        }
        String now = sdf.format(new Date());
        now = now.substring(0, 10);
        Map result = new HashMap();
        if (now.equals(occurTime)) {
            result.put("errMsg", "今天已经签过了");
        } else {
            HistoryIntegral historyIntegral = new HistoryIntegral();
            UserInfo userInfo = userinfoRepository.get(getCurrentUserInfoId(), UserInfo.class);
            historyIntegral.setUserInfo(userInfo);
            historyIntegral.setType("1");
            historyIntegral.setSignDays(signDays + 1);
            historyIntegral.setIntegral(todayIntergral(signDays));
            historyIntegral.setOccurTime(new Date());
            historyIntegral.setOriginalintegral(persentintergral);
            historyIntegral.setPersentintergral(persentintergral + todayIntergral(signDays));
            historyIntegralRepository.save(historyIntegral);
            result.put("integral", persentintergral + todayIntergral(signDays));
            result.put("signDays", signDays + 1);
        }
        return success(result);
    }

    //获取签到信息
    @GetMapping("getHistoryIntegral")
    public Result getHistoryIntegral() {
        String sql = "SELECT f_id,f_occur_time,f_sign_days,f_persentintergral FROM historyintegral WHERE f_user_id = " + getCurrentUserInfoId() + " ORDER BY f_occur_time DESC";
        List<Map<String, Object>> historyIntegralList = historyIntegralRepository.findMapByNativeSql(sql);
        Map result = new HashMap();
        if (historyIntegralList != null && !historyIntegralList.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String occurTime = historyIntegralList.get(0).get("f_occur_time").toString().substring(0,10);
            String now = sdf.format(new Date()).substring(0,10);
            if (now.equals(occurTime)) {
                result.put("isOk", "今天已经签过了");
            }
            result.put("historyIntegralList", historyIntegralList);
            result.put("lastIntegral", historyIntegralList.get(0));
        }
        result.put("userName", getCurrentUserInfo().getUsername());
        return success(result);
    }

    //系统消息
    @GetMapping("getNotice")
    public Result getNotice() {
        String sql = "SELECT f_id,f_title,f_content FROM t_sys_notice WHERE f_id IN (SELECT f_sys_notice_id FROM t_sys_notice_result WHERE f_user_info_id = " + getCurrentUserInfoId() + " )  ORDER BY f_pubtime DESC";
        List<Map<String, Object>> noticeList = sysNoticeRepository.findMapByNativeSql(sql);
        Map result = new HashMap();
        result.put("noticeList", noticeList);
        return success(result);
    }

    //常见问题FAQ
    @GetMapping("getFAQList")
    public Result getFAQList() {
        String sql = "SELECT f_id,f_content,f_title FROM t_faq WHERE f_deltag = " + ICommon.DELETE_NO + " ORDER BY f_pub_time DESC";
        List<Map<String, Object>> FAQList = FAQRepository.findMapByNativeSql(sql);
        Map result = new HashMap();
        result.put("FAQList", FAQList);
        return success(result);
    }

    //常见详情
    @GetMapping("getFAQDetail")
    public Result getFAQDetail(Long FAQId) {
        Map result = new HashMap();
        if (FAQId != null) {
            String sql = "SELECT * FROM t_faq WHERE f_id = " + FAQId;
            List<Map<String, Object>> FAQDetail = FAQRepository.findMapByNativeSql(sql);
            result.put("FAQDetail", FAQDetail);
        }
        return success(result);
    }


    //今天的积分
    public Long todayIntergral(Integer signDays) {
        if (signDays >= 6) {
            return 50L;
        } else if (signDays == 5) {
            return 20L;
        } else {
            return Long.valueOf((signDays + 1) * 2);
        }
    }


}
