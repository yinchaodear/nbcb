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
			String sql = " select concat(f_id, '') channelId, f_title title, f_type type, f_kind kind, case when t.f_channel_id is not null then 1 else 0 end joinFlag,ifnull(tf.joinNum, 0) joinNum,ifnull(cn.questionNum,0) questionNum\n" +
					"from t_channel c\n" +
					"left join (\n" +
					"	select f_channel_id\n" +
					"	from t_channel_follower \n" +
					"	where f_user_info_id = " + userInfoId + "\n" +
					"	group by f_channel_id\n" +
					"	) t on t.f_channel_id = c.f_id \n" +
					"left join (\n" +
					"	select f_channel_id, count(*) joinNum\n" +
					"   from t_channel_follower \n" +
					"   group by f_channel_id\n" +
					") tf on c.f_id = tf.f_channel_id\n" +
					"left join (\n" +
					"	select f_channel_id ,count(*) questionNum\n" +
					"   from t_news \n" +
					" 	where f_type = '提问'\n" +
					"   group by f_channel_id\n" +
					") cn on c.f_id = cn.f_channel_id \n" +
					"where c.f_kind = '小组' and c.f_status=0 and c.f_deltag=0 "  +
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
				if (cfs != null && cfs.size() > 0) {
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

	@Override
	public Map getTeamDetail(Long userInfoId, Map<String, Object> params) {
		Map map = null;
		try {
			Long teamId = params.get("teamId") != null ? Long.valueOf((String) params.get("teamId")) : null;
			if (teamId != null) {
				String sql = " select  convert(c.f_Logo using utf8) as logo, ifnull(t.joinNum, 0) joinNum,ifnull(nt.questionNum,0) questionNum,c.f_title title,\n" +
						"case when (select 1 from t_channel_follower cf where cf.f_channel_id = c.f_id and cf.f_user_info_id = " + userInfoId + ") then 1 else 0 end as joinFlag\n" +
						"from t_channel c\n" +
						"left join (\n" +
						"	select f_channel_id channelId, count(*) joinNum\n" +
						"	from t_channel_follower\n" +
						"	group by f_channel_id\n" +
						") t on t.channelId = c.f_id \n" +
						"left join (\n" +
						"	select f_channel_id channelId, count(*) questionNum\n" +
						"	from t_news\n" +
						"	where f_type = '提问'\n" +
						"	group by f_channel_id\n" +
						") nt on nt.channelId = c.f_id \n" +
						"where c.f_id = " + teamId + "\n" +
						"order by f_id desc;";
				List<Map<String, Object>> news = channelRepository.findMapByNativeSql(sql);
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
}
