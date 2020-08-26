package com.yuqiaotech.zsnews.model;

import com.yuqiaotech.common.web.base.BaseModel;

import javax.persistence.Entity;

/**
 * APP用户。
 * User是登录后台的用户。
 */
@Entity
public class AppUser extends BaseModel {
    private Long id;
    private String username;
    private String pwd;
    private String mobile;
    private String gender;
    private String avatar;

}
