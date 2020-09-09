package com.yuqiaotech.zsnews.service.impl;

import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.zsnews.model.Channel;
import com.yuqiaotech.zsnews.model.ChannelFollower;
import com.yuqiaotech.zsnews.model.UserInfo;
import com.yuqiaotech.zsnews.service.IChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2020/9/9 9:14 下午.
 *
 * @Author;fanchuanbin
 */

@Service
public class IChannelServiceImpl implements IChannelService {
	@Autowired
	private BaseRepository<Channel, Long> channelRepository;

	@Autowired
	private BaseRepository<UserInfo, Long> userInfoRepository;

	@Autowired
	private BaseRepository<ChannelFollower, Long> channelFollowerRepository;

	@Override
	public Map selectTeamChannels(Long userInfoId, Map<String, Object> params) {
		List<Map<String, Object>> channels = null;
		HashMap<String, Object> map = new HashMap<>();
		try {
			String sql = " select concat(f_id, '') channelId, f_title title, f_type type, f_kind kind, case when t.f_channel_id is null then 1 else 0 end joinFlag\n" +
					"from t_channel c\n" +
					"left join (\n" +
					"	select f_channel_id\n" +
					"	from t_channel_follower \n" +
					"	where f_user_info_id = " + userInfoId + "\n" +
					"	group by f_channel_id\n" +
					"	) t on t.f_channel_id = c.f_id \n" +
					"where c.f_kind = '小组'\n" +
					"order by f_type desc";
			channels = channelRepository.findMapByNativeSql(sql);

//			map.put("全部", channels);
			for (Map channel : channels) {
				String type = (String) channel.get("type");
				ArrayList<Map> typeChannels = null;

				if (map.containsKey(type)) {
					typeChannels = (ArrayList<Map>) map.get(type);
				} else {
					typeChannels = new ArrayList<>();
				}

				typeChannels.add(channel);
				map.put(type, typeChannels);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return map;
		}
	}

	@Override
	public Map toogleJoinTeam(Long userInfoId, Map<String, Object> params) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("flag", true);
		try {
			Long channelId = Long.valueOf((String) params.get("channelId"));

			if (channelId != null) {
				String sql = "select  * from t_channel_follower where f_channel_id =" + channelId + " and f_user_info_id ="
						+ userInfoId;
				List<Map<String, Object>> cfs = channelRepository.findMapByNativeSql(sql);
				if (cfs != null) {
					channelFollowerRepository.executeUpdateByNativeSql("delete from t_channel_follower where f_channel_id =" + channelId + " and f_user_info_id =" + userInfoId, null);
				} else {
					UserInfo userInfo = userInfoRepository.get(userInfoId, UserInfo.class);
					Channel channel = channelRepository.get(channelId, Channel.class);
					if (userInfo != null && channel != null) {
						ChannelFollower cf = new ChannelFollower();
						cf.setChannel(channel);
						cf.setUserInfo(userInfo);
						channelFollowerRepository.save(cf);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("flag", false);
		} finally {
			return map;
		}
	}
}
