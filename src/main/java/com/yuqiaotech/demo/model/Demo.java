package com.yuqiaotech.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.yuqiaotech.common.web.base.BaseModel;

@Entity
public class Demo extends BaseModel
{
    private Long id;
    
    /**
     * 属性1
     * @list(show="true")
     */
    private String attr1;
    
    /**
     * 属性2
     */
    private String attr2;
    
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
    
    public String getAttr1()
    {
        return attr1;
    }
    
    public void setAttr1(String attr1)
    {
        this.attr1 = attr1;
    }
    
    public String getAttr2()
    {
        return attr2;
    }
    
    public void setAttr2(String attr2)
    {
        this.attr2 = attr2;
    }
    
}
