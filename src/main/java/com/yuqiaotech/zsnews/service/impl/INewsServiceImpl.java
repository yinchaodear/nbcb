package com.yuqiaotech.zsnews.service.impl;

import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.zsnews.model.News;
import com.yuqiaotech.zsnews.service.INewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yuqiaotech.common.tools.servlet.ServletUtil.getCurrentUserId;

/**
 * Created on 2020/9/9 10:28 上午.
 *
 * @Author;fanchuanbin
 */

@Service
public class INewsServiceImpl implements INewsService {
	@Autowired
	private BaseRepository<News, Long> newsRepository;

	@Override
	public List selectNews(String kind, Long userInfoId, Map<String, Object> params) {
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
			String wheresql = "where 1 =1 and t.f_kind ='" + kind + "'";

			String sql = " select ifnull(zanNum, 0) zanNum,ifnull(pinglunNum, 0) pinglunNum, ifnull(shoucangNum, 0) shoucangNum,  \n" +
					" ifnull(uc.userTotalNum, 0) userTotalNum,ui.f_username userName, t.f_title title, t.f_content content\n" +
					"from t_news t\n" +
					"left join t_channel c on t.f_author_channel_id = c.f_id \n" +
					"left join t_user_info ui on c.f_userinfo_id = ui.f_id\n" +
					"left join (\n" +
					"	select f_user_info_id userInfoId, sum(case when f_type = '点赞' then 1 else 0 end) userTotalNum\n" +
					"	from t_comment\n" +
					"	group by f_user_info_id) uc on uc.userInfoId = ui.f_id\n" +
					"left join (\n" +
					"	select f_news_id newsId,sum(case when f_type = '点赞' then 1 else 0 end) zanNum, sum(case when f_type = '评论' then 1 else 0 end) pinglunNum, sum(case when f_type = '收藏' then 1 else 0 end) shoucangNum\n" +
					"	from t_comment\n" +
					"	group by f_news_id) ni on ni.newsId = t.f_id \n" +
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
}
