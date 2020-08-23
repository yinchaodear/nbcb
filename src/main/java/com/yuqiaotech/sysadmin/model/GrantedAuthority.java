package com.yuqiaotech.sysadmin.model;
/**
 * 授权。
 * 在一般的基于角色的系统里这个类似角色。
 * 
 */
public interface GrantedAuthority {
	public String getAuthority();
}
