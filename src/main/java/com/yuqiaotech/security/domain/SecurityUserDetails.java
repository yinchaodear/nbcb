package com.yuqiaotech.security.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.yuqiaotech.sysadmin.model.User;

public class SecurityUserDetails extends User implements UserDetails {
	private static final long serialVersionUID = 939884680001558121L;

	//登陆方式 COMMON|PHONE|WX|QQ|WEIBO
	private String loginType;

	private String thirdLoginKey;

	//普通用户：FRONT |管理人员：ADMIN
	private String type;

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getThirdLoginKey() {
		return thirdLoginKey;
	}

	public void setThirdLoginKey(String thirdLoginKey) {
		this.thirdLoginKey = thirdLoginKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

}
