package com.yuqiaotech.zsnews.model;

import com.yuqiaotech.common.web.base.BaseModel;

import javax.persistence.Entity;

/**
 * 专题。
 * 新闻的特定组合方式。
 */
@Entity
public class Topic extends BaseModel {
    private Long id;
    private String title;

}
