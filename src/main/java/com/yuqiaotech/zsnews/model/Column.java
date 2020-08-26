package com.yuqiaotech.zsnews.model;

import com.yuqiaotech.common.web.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 栏目。
 * 目前是固定的几个，是APP下面的tab。
 */
@Entity
public class Column extends BaseModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

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
