package com.yuqiaotech.zsnews.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 地区表
 */
@Entity
@Table(name = "sys_area")
public class SysArea implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//自增id，主键
    
    @Column(name = "name", length = 300)
    private String name;//区域名称
    
    @Column(name = "pid")
    private Long pid;//父区域ID
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Long getPid()
    {
        return pid;
    }
    
    public void setPid(Long pid)
    {
        this.pid = pid;
    }
}
