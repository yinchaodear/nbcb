package com.yuqiaotech.zsnews.model;

import com.yuqiaotech.common.web.base.BaseModel;
import com.yuqiaotech.sysadmin.model.User;

import javax.persistence.Entity;

/**
 * 评论。
 * 评论，回复等。
 */
@Entity
public class Comment extends BaseModel {
    private Long id;
    private User user;
    private News news;
    private String comment;

}
