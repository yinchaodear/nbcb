package com.yuqiaotech.zsnews.service;

import java.util.List;
import java.util.Map;

/**
 * Created on 2020/9/9 10:26 上午.
 * newsService 接口
 * @Author;fanchuanbin
 */
public interface INewsService {
	/**
	 * 查询社区提问，发稿
	 *
	 * @param
	 * kind: news-kind
	 * @return 参数配置信息
	 */
	List selectNews(String kind, Long userInfoId,Map<String, Object> params);

	/**
	 * 查询社区发稿，提问详情
	 *
	 * @param
	 * @return 参数配置信息
	 */
	Map getNewsDetail(Long userInfoId, Map<String, Object> params);


	/**
	 * 点赞/取消点赞  收藏|取消收藏
	 * @param userInfoId
	 * @param params
	 * @return
	 */
	Map toggleCollectOrAgree(Long userInfoId, Map<String, Object> params);


	/**
	 * 文章评论|回复评论|对回复对回复
	 * @param params
	 * @return
	 */
	Map makeComment(Long userInfoId, Map<String, Object> params);


	/**
	 * 查询文章评论|回复
	 *
	 * @param
	 * @return 参数配置信息
	 */
	Map selectComments(Long userInfoId, Map<String, Object> params);

	/**
	 * 点赞|取消点赞 回复|评论
	 * @param userInfoId
	 * @param params
	 * @return
	 */
	Map toggleCommentAgree(Long userInfoId, Map<String, Object> params);
}
