package com.yuqiaotech.security.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yuqiaotech.common.SysConstants;
import com.yuqiaotech.common.tools.token.JwtUtils;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.security.domain.SecurityUserDetails;
import com.yuqiaotech.security.domain.SecurityUserDetailsService;
import com.yuqiaotech.sysadmin.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 2020/8/27 10:53 上午.
 *
 * @Author;fanchuanbin
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter {

	/**
	 * 用户信息service
	 */
	private SecurityUserDetailsService securityUserDetailsService;

	public SecurityUserDetailsService getSecurityUserDetailsService() {
		return securityUserDetailsService;
	}

	public void setSecurityUserDetailsService(SecurityUserDetailsService securityUserDetailsService) {
		this.securityUserDetailsService = securityUserDetailsService;
	}

	public AuthenticationTokenFilter(SecurityUserDetailsService securityUserDetailsService) {
		this.securityUserDetailsService = securityUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		System.out.println("AuthenticationTokenFilter");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type,Content-Length, Authorization, Accept,X-Requested-With,X-App-Id, X-Token,Origin");
		response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS,HEAD");
		response.setHeader("Access-Control-Max-Age", "3600");

		//app端会带X-Token
		String authToken = request.getHeader("X-Token");
		if (authToken != null) {
			try {
				if (SecurityContextHolder.getContext().getAuthentication() == null) {
					if (request.getRequestURI().equals("/auth/login")) {
						chain.doFilter(request, response);
						return;
					}

					if (StringUtils.isEmpty(authToken)) {
						throw new TokenExpiredException("");
					}

					DecodedJWT tokenInfo = JwtUtils.verify(authToken);
					String username = tokenInfo.getClaim("username").asString();
					String password = tokenInfo.getClaim("password").asString();

					if (username != null) {
						//根据用户名获取用户对象
						SecurityUserDetails userDetails = (SecurityUserDetails) securityUserDetailsService.loadUserByUsername(username);

						if (userDetails != null) {
							UsernamePasswordAuthenticationToken authentication =
									new UsernamePasswordAuthenticationToken(userDetails, null, null);
							authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
							//设置为已登录
							request.getSession().setAttribute(SysConstants.SECURITY_USERTYPE_KEY, userDetails.getType());
							request.getSession().setAttribute(SysConstants.SECURITY_USER_LOGINTYPE_KEY, userDetails.getLoginType());

							request.getSession()
									.setAttribute(SysConstants.SECURITY_CONTEXT_KEY, (SecurityUserDetails) authentication.getPrincipal());
							request.getSession()
									.setAttribute(SysConstants.SECURITY_USERNAME_KEY,
											((SecurityUserDetails) authentication.getPrincipal()).getUsername());
							request.getSession()
									.setAttribute(SysConstants.SECURITY_USERID_KEY,
											((SecurityUserDetails) authentication.getPrincipal()).getId());
							SecurityContextHolder.getContext().setAuthentication(authentication);
						}
					}
				}

				chain.doFilter(request, response);
			} catch (TokenExpiredException | JWTDecodeException e) {
				e.printStackTrace();
				Result resuBean = new Result();
				resuBean.setSuccess(false);
				resuBean.setMsg("token过期或者不存在");
				resuBean.setCode(555);
				response.setHeader("Content-type", "application/json;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(JSON.toJSONString(resuBean));
			}
		} else {
			chain.doFilter(request, response);
		}
	}
}
