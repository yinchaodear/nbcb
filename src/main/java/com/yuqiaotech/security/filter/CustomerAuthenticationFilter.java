package com.yuqiaotech.security.filter;

import com.yuqiaotech.common.SysConstants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 2020/9/3 3:33 下午.
 *
 * @Author;fanchuanbin
 */
public class CustomerAuthenticationFilter
		extends AbstractAuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
	private boolean postOnly = true;

	public CustomerAuthenticationFilter() {
		super(new AntPathRequestMatcher("/auth/login", "POST"));
	}

	protected CustomerAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		String usertype = obtainType(request);
		String loginType = obtainLoginType(request);

		if (StringUtils.isEmpty(usertype)) {
			usertype = SysConstants.SECURITY_USERTYPE_ADMIN;
		}

		if (StringUtils.isEmpty(loginType)) {
			loginType = "COMMON";
		}
		
		String usernameAndTypeAndLoginType = String.format("%s%s%s%s%s", username.trim(),
				"|", usertype, "|", loginType);

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(usernameAndTypeAndLoginType, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return super.getAuthenticationManager().authenticate(authRequest);
	}

	private String obtainType(HttpServletRequest request) {
		return request.getParameter(SysConstants.SECURITY_USERTYPE_KEY);
	}

	private String obtainLoginType(HttpServletRequest request) {
		return request.getParameter(SysConstants.SECURITY_USER_LOGINTYPE_KEY);
	}

	@Override
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}

	protected String obtainPassword(HttpServletRequest request) {
		String pwd = request.getParameter(passwordParameter);
		return pwd != null ? pwd.replaceAll(" ", "+") : pwd;
	}

	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}

	protected void setDetails(HttpServletRequest request,
							  UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	public void setUsernameParameter(String usernameParameter) {
		Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
		this.usernameParameter = usernameParameter;
	}

	public void setPasswordParameter(String passwordParameter) {
		Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
		this.passwordParameter = passwordParameter;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getUsernameParameter() {
		return usernameParameter;
	}

	public final String getPasswordParameter() {
		return passwordParameter;
	}
}
