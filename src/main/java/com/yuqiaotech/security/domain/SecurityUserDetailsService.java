package com.yuqiaotech.security.domain;

import javax.annotation.Resource;

import com.yuqiaotech.common.SysConstants;
import com.yuqiaotech.zsnews.model.UserAuths;
import com.yuqiaotech.zsnews.model.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.sysadmin.model.User;

import java.util.List;

@Component
public class SecurityUserDetailsService implements UserDetailsService {

	@Resource
	private BaseRepository<User, Long> userRepository;

	@Resource
	private BaseRepository<UserInfo, Long> userInfoRepository;

	@Resource
	private BaseRepository<UserAuths, Long> userAuthsRepository;

	@Override
	public UserDetails loadUserByUsername(String s)
			throws UsernameNotFoundException {
		String[] usernameAndType = StringUtils.split(s, "|");
		if (usernameAndType == null || usernameAndType.length != 3) {
			throw new UsernameNotFoundException("Username and type and loginType must be provided");
		}

		String username = usernameAndType[0];
		String type = usernameAndType[1];
		String loginType = usernameAndType[2];

		SecurityUserDetails securityUserDetails = new SecurityUserDetails();
		securityUserDetails.setType(type);
		securityUserDetails.setLoginType(loginType);
		if (!StringUtils.isEmpty(type) && SysConstants.SECURITY_USERTYPE_ADMIN.equals(type)) {
			User sysUser = userRepository.findUniqueBy("username", username, User.class);

			if (sysUser == null) {
				throw new UsernameNotFoundException("Account Not");
			}

			securityUserDetails.setId(sysUser.getId());
			securityUserDetails.setUsername(sysUser.getUsername());
			securityUserDetails.setPassword(sysUser.getPassword());
		} else {
			//分离前端front
			UserInfo userInfo = null;

			if (loginType.equals("COMMON")) {
				//用户名密码方式登陆
				userInfo = userInfoRepository.findUniqueBy("username", username, UserInfo.class);
			} else if (loginType.equals("PHONE")) {
				userInfo = userInfoRepository.findUniqueBy("mobile", username, UserInfo.class);
				//手机验证码登陆
				securityUserDetails.setMobile(username);
			} else {
				//三方登录方式
				UserAuths userAuths = userAuthsRepository.queryUniqueResult("from UserAuths where thirdType = '" + loginType + "' and thirdKey = '" + username + "'", null);
				if (userAuths != null) {
					if (userAuths.getUserinfo() != null) {
						userInfo = userInfoRepository.get(userAuths.getUserinfo().getId(), UserInfo.class);
					}
				}

				//三方登陆时这里username 记录的thirdKey
				securityUserDetails.setThirdLoginKey(username);
			}

			if (userInfo == null) {
				throw new UsernameNotFoundException("Account Not");
			}

			securityUserDetails.setId(userInfo.getId());
			securityUserDetails.setUsername(userInfo.getUsername());
			securityUserDetails.setPassword(userInfo.getPwd());
		}

		return securityUserDetails;
	}
}
