package com.yuqiaotech.zsnews.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created on 2020/9/9 9:13 下午.
 *
 * @Author;fanchuanbin
 */
public interface IChannelService {
	/**
	 * 查询社区小组
	 *
	 * @param
	 * @return 参数配置信息
	 */
	Map selectTeamChannels(Long userInfoId, Map<String, Object> params);

	/**
	 * 加入/取消加入 小组
	 * @param userInfoId
	 * @param params
	 * @return
	 */
	Map toogleJoinTeam(Long userInfoId, Map<String, Object> params);


	/**
	 * 查询channel 小组
	 *
	 * @param
	 * @return 参数配置信息
	 */
	Map getTeamDetail(Long userInfoId, Map<String, Object> params);
}
