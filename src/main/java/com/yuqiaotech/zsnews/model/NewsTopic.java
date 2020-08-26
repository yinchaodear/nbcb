package com.yuqiaotech.zsnews.model;

import javax.persistence.Entity;

/**
 * 新闻和专题。
 * 新闻和专题的关联关系。
 */
@Entity
public class NewsTopic {
    private Long id;
    private News news;
    private Topic topic;
    private Integer displayOrder;//小的在前
}
