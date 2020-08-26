package com.yuqiaotech.zsnews.model;

import com.yuqiaotech.common.web.base.BaseModel;

import javax.persistence.Entity;

/**
 * 栏目。
 * 目前是固定的几个，是APP下面的tab。
 */
@Entity
public class Column extends BaseModel
{
    private Long id;
    private String title;

}
