package com.yuqiaotech.zsnews.model;

import javax.persistence.*;

/**
 * Created on 2020/9/3 8:06 下午.
 *	三方登陆信息关联表
 * @Author;fanchuanbin
 */

@Entity
public class UserAuths {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 关联userInfo
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_userinfo_id")
	private UserInfo userinfo;

	/**
	 * 三方登陆类型（wx:微信，qq:QQ登陆，wb:微博）
	 */
	private String thirdType;

	/**
	 * 三方登陆唯一标识
	 */
	private String thirdKey;

	/**
	 * 扩展三方登陆账号相关信息
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}

	public String getThirdType() {
		return thirdType;
	}

	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}

	public String getThirdKey() {
		return thirdKey;
	}

	public void setThirdKey(String thirdKey) {
		this.thirdKey = thirdKey;
	}
}
