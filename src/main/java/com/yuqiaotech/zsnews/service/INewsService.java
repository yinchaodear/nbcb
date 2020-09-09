package com.yuqiaotech.zsnews.service;

import com.yuqiaotech.sysadmin.model.SysConfig;

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
}
