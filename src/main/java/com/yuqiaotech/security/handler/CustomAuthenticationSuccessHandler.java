package com.yuqiaotech.security.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuqiaotech.common.tools.token.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yuqiaotech.common.SysConstants;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.security.domain.SecurityUserDetails;

/**
 * Describe: 自定义 Security 用户未登陆处理类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	public static final Logger log = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
										Authentication authentication)
			throws IOException, ServletException {
		SecurityUserDetails details = (SecurityUserDetails) authentication.getPrincipal();
		String username = details.getUsername();
		String password = details.getPassword();
		String type = details.getType();
		String loginType = details.getLoginType();
		String thirdLoginKey = details.getThirdLoginKey();

		Map<String, String> map = new HashMap<>();
		if (!StringUtils.isEmpty(loginType) && !loginType.equals("COMMON") && !loginType.equals("PHONE")) {
			username = thirdLoginKey;
		}

		if (!StringUtils.isEmpty(loginType) && loginType.equals("PHONE")) {
			username = details.getMobile();
		}

		map.put("username", String.format("%s%s%s%s%s", username.trim(),
				"|", type, "|", loginType));
		map.put("password", password);
		String token = JwtUtils.getToken(map, 60 * 60 * 24 * 30);
		System.out.println("token:" + token);

		Result resuBean = new Result();
		resuBean.setSuccess(true);
		resuBean.setMsg("登录成功");
		resuBean.setCode(200);
		resuBean.setData(token);

		Cookie cookie=new Cookie("token",token);
		httpServletResponse.addCookie(cookie);


		httpServletRequest.getSession()
				.setAttribute(SysConstants.SECURITY_CONTEXT_KEY, (SecurityUserDetails) authentication.getPrincipal());

		httpServletRequest.getSession().setAttribute(SysConstants.SECURITY_USERTYPE_KEY, type);
		httpServletRequest.getSession().setAttribute(SysConstants.SECURITY_USER_LOGINTYPE_KEY, loginType);
		httpServletRequest.getSession()
				.setAttribute(SysConstants.SECURITY_USERNAME_KEY,
						((SecurityUserDetails) authentication.getPrincipal()).getUsername());
		httpServletRequest.getSession()
				.setAttribute(SysConstants.SECURITY_USERID_KEY,
						((SecurityUserDetails) authentication.getPrincipal()).getId());
		httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.getWriter().write(JSON.toJSONString(resuBean));

	}
}
