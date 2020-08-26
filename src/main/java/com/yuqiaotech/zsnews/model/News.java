package com.yuqiaotech.zsnews.model;

import com.yuqiaotech.common.web.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 新闻。
 * 新闻，投稿，提问。
 */
@Entity
public class News extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;//新闻，投稿，提问
    private String mediaType;//图片、文章、链接、视频
    private String title;
    private String content;
    private String link;

    private Channel channel;

    private String attaches;//附件

    private Integer displayOrder;//小的数字排在前面



}
