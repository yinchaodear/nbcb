package com.yuqiaotech.sysadmin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 用户配置。
 * 
 */
@Entity
public class UserConfig
{
    
    private Long id;
    
    private String username;
    
    private String configCategory;
    
    private String configKey;
    
    private String configValue;
    
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
     * 用户名。
     * @searchItem
     * displayType="text"
     * @return
     */
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    /**
     * 配置类别。
     * 一般就是模块名。
     * @searchItem
     * displayType="text"
     * @return
     */
    public String getConfigCategory()
    {
        return configCategory;
    }
    
    public void setConfigCategory(String configCategory)
    {
        this.configCategory = configCategory;
    }
    
    /**
     * 配置名称。
     * @searchItem
     * displayType="text"
     * @return
     */
    public String getConfigKey()
    {
        return configKey;
    }
    
    public void setConfigKey(String configKey)
    {
        this.configKey = configKey;
    }
    
    /**
     * 配置的值。
     * @searchItem
     * displayType="text"
     * @return
     */
    public String getConfigValue()
    {
        return configValue;
    }
    
    public void setConfigValue(String configValue)
    {
        this.configValue = configValue;
    }
    
}
