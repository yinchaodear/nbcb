package com.yuqiaotech.zsnews.model;

import com.yuqiaotech.common.web.base.BaseModel;

import javax.persistence.Entity;

/**
 * 新闻关注。
 *
 */
@Entity
public class NewsFollower extends BaseModel {
    private Long id;
    private News news;
    private AppUser appUser;
}
