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

	Map toogleJoinTeam(Long userInfoId, Map<String, Object> params);

}
