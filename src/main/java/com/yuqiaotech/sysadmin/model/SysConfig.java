package com.yuqiaotech.sysadmin.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 系统配置。
 * 
 */
@Entity
public class SysConfig implements Serializable
{
    
    /*   not null    pk   */
    
    private Long id;
    
    /* 配置说明     */
    
    private String description;
    
    /* 配置项目  not null     */
    
    private String itemName;
    
    /* 配置值     */
    
    private String itemValue;
    
    private String itemType;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * 描述。
     * @return
     */
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * 属性名。
     * @searchItem
     * displayType="text"
     * 
     * @return
     */
    public String getItemName()
    {
        return itemName;
    }
    
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }
    
    /**
     * 属性值。
     * @searchItem
     * displayType="text"
     * @list editable="true"
     * @return
     */
    public String getItemValue()
    {
        return itemValue;
    }
    
    public void setItemValue(String itemValue)
    {
        this.itemValue = itemValue;
    }
    
    public String getItemType()
    {
        return itemType;
    }
    
    public void setItemType(String itemType)
    {
        this.itemType = itemType;
    }
}
