package com.yuqiaotech.zsnews.model;

import com.yuqiaotech.sysadmin.model.User;

import javax.persistence.*;

/**
 * 频道关注。
 * 用户关注新闻频道，加入某个社区小组，关注某个商户号，这个关系都放在这个表里。
 *
 */
@Entity
public class ChannelFollower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="f_channel_id")
    private Channel channel;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="f_app_user_id")
    private AppUser appUser;
    private Integer displayOrder;//

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
