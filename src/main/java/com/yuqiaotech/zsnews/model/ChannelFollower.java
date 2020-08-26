package com.yuqiaotech.zsnews.model;

import com.yuqiaotech.sysadmin.model.User;

import javax.persistence.Entity;

/**
 * 频道关注。
 * 用户关注新闻频道，加入某个社区小组，关注某个商户号，这个关系都放在这个表里。
 *
 */
@Entity
public class ChannelFollower {

    private Long id;
    private Channel channel;
    private User user;
    private Integer displayOrder;//
}
