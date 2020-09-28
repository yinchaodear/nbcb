package com.yuqiaotech.sysadmin.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 受保护资源。
 *
 */
@Entity
public class ProtectedResource implements Serializable
{
    
    private Long id;
    
    private String name;
    
    private String patternStr;
    
    private String type;
    
    private Integer compareOrder;
    
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
     * 用户容易理解的名称。
     * @searchItem
     * displayType="text"
     * @return
     */
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * 资源适配符。
     * 这里虽然定义为patternStr，但未必是按照正则表达式来匹配。
     * 这个和type有关，就看拦截器如何处理了。
     * @searchItem
     * displayType="text"
     * @return
     */
    public String getPatternStr()
    {
        return patternStr;
    }
    
    public void setPatternStr(String patternStr)
    {
        this.patternStr = patternStr;
    }
    
    /**
     * 类型。
     * 不同的资源定义方式，一般的可能对应不同的拦截方式。
     * 所谓不同的拦截方式，如
     * @searchItem
     * displayType="text"
     * 
     * @return
     */
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public Integer getCompareOrder()
    {
        return compareOrder;
    }
    
    public void setCompareOrder(Integer compareOrder)
    {
        this.compareOrder = compareOrder;
    }
    
}
