package com.yuqiaotech.sysadmin.model;
/**
 * 认证对象。
 * 用户登陆后产生本对象来存储用户和授权信息。
 * 不同的登陆方式，产生的认证对象可能不同，比如SSL，证书登录等。
 * 
 */
public interface Authentication {

	/**
	 * 用户的主要信息。
	 * 我不太了解
	 * 
	 * @return
	 */
	public Object getPrincipal();
}
