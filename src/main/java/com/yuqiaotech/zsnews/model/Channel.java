package com.yuqiaotech.zsnews.model;

import com.yuqiaotech.common.web.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 频道。
 * 商户号，企业号，小组等都放这吧。通过类型来区分。
 */
@Entity
public class Channel extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String logo;
    private String title;
    private Column column;
    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
