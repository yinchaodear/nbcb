package com.yuqiaotech.zsnews.service.impl;

import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.zsnews.model.*;
import com.yuqiaotech.zsnews.service.INewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2020/9/9 10:28 上午.
 *
 * @Author;fanchuanbin
 */

@Service
public class INewsServiceImpl implements INewsService {
	@Autowired
	private BaseRepository<News, Long> newsRepository;

	@Autowired
	private BaseRepository<UserInfo, Long> userInfoRepository;

	@Autowired
	private BaseRepository<Comment, Long> commentRepository;
	
	@Autowired
	private BaseRepository<NewsFollower, Long> newsFollowerRepository;
	@Override
	public List selectNews(Long userInfoId, Map<String, Object> params) {
		List news = null;
		try {
			Integer pageNo = 0;
			Integer pageSize = 10;
			if (!StringUtils.isEmpty((String) params.get("pageNo"))) {
				pageNo = Integer.parseInt((String) params.get("pageNo"));
			}

			if (!StringUtils.isEmpty((String) params.get("pageSize"))) {
				pageSize = Integer.parseInt((String) params.get("pageSize"));
			}

			String limitStr = "limit " + pageNo * pageSize + "," + pageSize;

			String type = params.get("type") != null ? (String) params.get("type") : "";
			String wheresql = "where t.f_id is not null and nc.f_id is not null ";

			Long teamId = params.get("teamId") != null ? Long.valueOf((String) params.get("teamId")) : null;
			if (teamId != null) {
				wheresql += " and newsc.f_channel_id = " + teamId;
			}

			String sql = " select   convert(c.f_Logo using utf8) as logo,concat(t.f_id,'') newsId, ifnull(t.f_likes, 0) zanNum,ifnull(t.f_comments, 0) pinglunNum, ifnull(t.f_collects, 0) shoucangNum,  \n" +
					" ifnull(ui.f_likes, 0) userTotalNum,ui.f_username userName, t.f_title title, t.f_content content\n" +
					"from t_news_channel newsc \n" +
					"left join t_news t on t.f_id=newsc.f_news_id \n" +
					"left join t_channel nc on newsc.f_channel_id = nc.f_id and nc.f_kind = '小组' \n" +
					"left join t_channel c on t.f_author_channel_id = c.f_id \n" +
					"left join t_user_info ui on c.f_userinfo_id = ui.f_id\n" +
					wheresql + "\n" +
					"order by t.f_id desc \n" +
					limitStr;
			news = newsRepository.findMapByNativeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return news;
		}
	}

	@Override
	public Map getNewsDetail(Long userInfoId, Map<String, Object> params) {
		Map map = null;
		try {
			Long newsId = params.get("newsId") != null ? Long.valueOf((String) params.get("newsId")) : null;
			if (newsId != null) {
				String sql = " select case when (select 1 from t_comment com where com.f_type='点赞' and f_news_id = t.f_id and f_user_info_id = " + userInfoId + ") then 1 else 0 end as agreeFlag,\n" +						
 						"case when (n.f_id is not null ) then 1 else 0 end as collectFlag,\n" +
						"concat(t.f_id,'') newsId, ifnull(t.f_likes, 0) zanNum,ifnull(t.f_comments, 0) pinglunNum, ifnull(t.f_collects, 0) shoucangNum,  \n" +
						" ifnull(ui.f_likes, 0) userTotalNum,c.f_title authorChannelName,ui.f_username userName, t.f_title title, t.f_content content\n" +
						" ,convert(c.f_Logo using utf8) as logo from t_news t\n" +
						"left join t_channel c on t.f_author_channel_id = c.f_id \n" +
						"left join t_user_info ui on c.f_userinfo_id = ui.f_id\n" +
						" left join t_news_follower n on n.f_news_id = t.f_id and n.f_user_info_id = "+
						 userInfoId
						+ " where t.f_id = " + newsId;
				List<Map<String, Object>> news = newsRepository.findMapByNativeSql(sql);
				if (news != null && news.size() > 0) {
					map = news.get(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return map;
		}
	}


	@Transactional
	@Override
	public Map toggleCollectOrAgree(Long userInfoId, Map<String, Object> params) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("flag", true);
		try {
			Long newsId = params.get("newsId") != null ? Long.valueOf((String) params.get("newsId")) : null;
			String type = (String) params.get("type");
            if(!"收藏".equals(type)){
            	if (newsId != null && !StringUtils.isEmpty(type)) {
    				String sql = "select  * from t_comment where f_type = '" + type + "' and f_news_id =" + newsId + " and f_user_info_id ="
    						+ userInfoId;
    				List<Map<String, Object>> cfs = newsRepository.findMapByNativeSql(sql);
    				if (cfs != null && cfs.size() > 0) {
    					newsRepository.executeUpdateByNativeSql("delete from t_comment where  f_type = '" + type + "' and f_news_id =" + newsId + " and f_user_info_id =" + userInfoId, null);
    				} else {
    					UserInfo userInfo = userInfoRepository.get(userInfoId, UserInfo.class);
    					News news = newsRepository.get(newsId, News.class);
    					if (userInfo != null && news != null) {
    						Comment comment = new Comment();
    						comment.setType(type);
    						comment.setUserInfo(userInfo);
    						comment.setNews(news);
    						commentRepository.save(comment);
    					}
    				}
    				updateNewsFollowStat(newsId);
    			} else {
    				throw new RuntimeException("newsId 或者 type 参数缺失");
    			}
            }else{
            	String sqlexist = "SELECT * FROM t_news_follower where f_news_id = "+newsId+" and f_user_info_id =" +userInfoId;
            	List result = newsFollowerRepository.findMapByNativeSql(sqlexist);
            	if(result.isEmpty()){
            		String sql = "insert into t_news_follower (f_news_id,f_user_info_id) values (" + newsId + ","
            				+ userInfoId + ")";
                	newsFollowerRepository.executeUpdateByNativeSql(sql, null);
                	updateNewsFollowStat(newsId);
            	}else{
            		String deletesql ="delete from t_news_follower where f_news_id = "+newsId+" and f_user_info_id = "+userInfoId;
            		newsFollowerRepository.executeUpdateByNativeSql(deletesql, null);
            		updateNewsFollowStat(newsId);
            	}
            	
            
            }
			
		
		} catch (Exception e) {
			e.printStackTrace();
			map.put("flag", false);
		} finally {
			return map;
		}
	}

	/**
	 * 更新新闻的点赞，评论，收藏等数据，更新文章发表者的点赞总数。
	 * @param newsId
	 */
	private void updateNewsFollowStat(long newsId){
		String sql = "select f_type,count(*) cnt from t_comment where f_news_id="+newsId +" group by f_type";
		List<Map<String,Object>> rs = commentRepository.findMapByNativeSql(sql);

		int likes = 0,collects = 0, comments = 0;
		for(int i =0; i < rs.size(); i++){
			Map<String,Object> m = rs.get(i);
			String type = (String)m.get("f_type");
			Number cnt = (Number)m.get("cnt");
			if("点赞".equals(type)){
				likes = cnt.intValue();
			}else
			if("评论".equals(type)){
				comments = cnt.intValue();
			}

//			if("收藏".equals(type)){
//				collects = cnt.intValue();
//			}
		}
		
		sql = "select count(*) cnt from t_news_follower  where f_news_id= "+newsId;
		List<Map<String,Object>> rs1 = newsFollowerRepository.findMapByNativeSql(sql);
		if(!rs1.isEmpty()){
			collects =((Number)rs1.get(0).get("cnt")).intValue();
		}
		
		News n = newsRepository.get(newsId,News.class);
		n.setCollects(collects);
		n.setLikes(likes);
		n.setComments(comments);
		newsRepository.save(n);
		if(n.getUserinfo() == null)return;
		long userId = n.getUserinfo().getId();
		String sqlForUserInfo = "update t_user_info u set " +
				"f_likes =(select count(*) from t_comment t " +
				"	left join t_news n on n.f_id=t.f_news_id " +
				"	where n.f_userinfo_id=u.f_id and t.f_type='点赞') where f_id="+userId;
		commentRepository.executeUpdateByNativeSql(sqlForUserInfo,null);
	}
	@Transactional
	@Override
	public Map makeComment(Long userInfoId, Map<String, Object> params) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("flag", true);
		try {
			//评论|回复
			String type = (String) params.get("type");
			Long newsId = params.get("newsId") != null ? Long.valueOf((String) params.get("newsId")) : null;

			if (newsId == null) {
				throw new RuntimeException("评论 newsId 为空，请确认!");
			}

			if (!StringUtils.isEmpty(type)) {
				Comment comment = new Comment();
				String content = (String) params.get("content");
				if (StringUtils.isEmpty(content)) {
					throw new RuntimeException("评论|回复 的内容为空");
				}

				comment.setContent(content);
				//评论|回复对应的news
				News news = newsRepository.get(newsId, News.class);
				comment.setNews(news);

				//type 评论|回复
				comment.setType(type);
				UserInfo userInfo = userInfoRepository.get(userInfoId, UserInfo.class);
				comment.setUserInfo(userInfo);

				if (type.equals("回复")) {
					//对评论的回复 | 对回复的回复（commentId, answerUserId-要处理的回复或者评论的发表者）
					Long commentId = params.get("commentId") != null ? ((Number) params.get("commentId")).longValue() : null;
					Comment targetComment = commentRepository.get(commentId, Comment.class);
					comment.setComment(targetComment);

					//回复对应的发表者
					Long answerUserId = params.get("answerUserId") != null ? ((Number) params.get("answerUserId")).longValue() : null;
					UserInfo answerUserInfo = userInfoRepository.get(answerUserId, UserInfo.class);
					comment.setAnswerUser(answerUserInfo);
				}

				commentRepository.save(comment);
				updateNewsFollowStat(newsId);
			} else {
				throw new RuntimeException("type 为空，请确认!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("flag", false);
		} finally {
			return map;
		}
	}

	@Override
	public Map selectComments(Long userInfoId, Map<String, Object> params) {
		HashMap<Object, Object> result = new HashMap<>();
		List<Map<String, Object>> comments = null;
		int totalComments = 0;
		try {
			Integer pageNo = 0;
			Integer pageSize = 10;
			if (!StringUtils.isEmpty((String) params.get("pageNo"))) {
				pageNo = Integer.parseInt((String) params.get("pageNo"));
			}

			if (!StringUtils.isEmpty((String) params.get("pageSize"))) {
				pageSize = Integer.parseInt((String) params.get("pageSize"));
			}

			Long newsId = params.get("newsId") != null ? Long.valueOf((String) params.get("newsId")) : null;

			String limitStr = "limit " + pageNo * pageSize + "," + pageSize;
			String wheresql = "where 1 =1 and c.f_type = '评论'";
			if (newsId != null) {
				wheresql += " and c.f_news_id = " + newsId;
				String sql = " select case when (select 1 from t_comment com where com.f_type='点赞' and f_comment_id = c.f_id and f_user_info_id = " + userInfoId + ") then 1 else 0 end as agreeFlag, date_format(c.f_created, '%m-%d') createdDate,c.f_id commentId,ifnull(t.agreeNum, 0) agreeNum, c.f_type commentType,c.f_news_id newsId, c.f_content content, c.f_user_info_id userInfoId, ui.f_username userName\n" +
						",  ifnull(ui.f_avatar, 0) as avatar from t_comment c\n" +
						" left join (\n" +
						"	select f_comment_id commentId, count(*) agreeNum\n" +
						"	from t_comment \n" +
						"	where f_type = '点赞' and f_comment_id is not null \n" +
						"	group by f_comment_id\n" +
						") t on t.commentId = c.f_id \n" +
						"left join t_news n on c.f_news_id = n.f_id\n" +
						"left join t_user_info ui on ui.f_id = c.f_user_info_id\n" +
						"left join t_user_info aui on aui.f_id = c.f_answer_user_id\n" +
						wheresql + "\n" +
						"order by c.f_id desc \n" +
						limitStr;
				comments = commentRepository.findMapByNativeSql(sql);

				List<Map<String, Object>> subComments = null;
				if (comments != null && comments.size() > 0) {
					totalComments += comments.size();
					String subSql = "";
					for (Map comment : comments) {
						Long commentId = ((Number) comment.get("commentId")).longValue();
						subSql = "select date_format(c.f_created, '%m-%d') createdDate,c.f_id commentId,ifnull(t.agreeNum, 0) agreeNum,case when (select 1 from t_comment com where com.f_type='点赞' and f_comment_id = c.f_id and f_user_info_id = " + userInfoId + ") then 1 else 0 end as agreeFlag, c.f_comment_id targetCommentId, c.f_type commentType,c.f_news_id newsId, c.f_content content, c.f_user_info_id userInfoId, ui.f_username userName, c.f_answer_user_id answerUserId, aui.f_username answerUserName\n" +
								", ifnull(ui.f_avatar, 0) as avatar from t_comment c\n" +
								" left join (\n" +
								"	select f_comment_id commentId, count(*) agreeNum\n" +
								"	from t_comment \n" +
								"	where f_type = '点赞' and f_comment_id is not null \n" +
								"	group by f_comment_id\n" +
								") t on t.commentId = c.f_id \n" +
								"left join t_news n on c.f_news_id = n.f_id\n" +
								"left join t_user_info ui on ui.f_id = c.f_user_info_id\n" +
								"left join t_user_info aui on aui.f_id = c.f_answer_user_id \n" +
								"where c.f_type = '回复' and c.f_comment_id = " + commentId;
						subComments = commentRepository.findMapByNativeSql(subSql);
						if (subComments != null && subComments.size() > 0) {
							totalComments += subComments.size();
							comment.put("subComments", subComments);
						}
					}
				}
			} else {
				throw new RuntimeException("newsId 为空，请确认!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			totalComments = 0;
			comments = null;
		} finally {
			result.put("totalComments", totalComments);
			result.put("comments", comments);
			return result;
		}
	}

	@Override
	public Map toggleCommentAgree(Long userInfoId, Map<String, Object> params) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("flag", true);
		try {
			Long commentId = params.get("commentId") != null ? Long.valueOf((String) params.get("commentId")) : null;

			if (commentId != null) {
				String sql = "select  * from t_comment where f_comment_id =" + commentId + " and f_user_info_id ="
						+ userInfoId;
				List<Map<String, Object>> cfs = commentRepository.findMapByNativeSql(sql);
				if (cfs != null && cfs.size() > 0) {
					newsRepository.executeUpdateByNativeSql("delete from t_comment where f_comment_id =" + commentId + " and f_user_info_id =" + userInfoId, null);
				} else {
					UserInfo userInfo = userInfoRepository.get(userInfoId, UserInfo.class);
					Comment agreeComment = commentRepository.get(commentId, Comment.class);
					if (userInfo != null && agreeComment != null) {
						Comment comment = new Comment();
						comment.setType("点赞");
						comment.setUserInfo(userInfo);
						comment.setComment(agreeComment);
						commentRepository.save(comment);
					}
				}
			} else {
				throw new RuntimeException("commentId参数缺失");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("flag", false);
		} finally {
			return map;
		}
	}
}
