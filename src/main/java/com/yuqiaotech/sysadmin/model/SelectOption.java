package com.yuqiaotech.sysadmin.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 选项。
 * 
 */
@Entity
public class SelectOption implements Serializable
{
    private static final long serialVersionUID = 3831626162173352411L;
    
    private Long id;
    
    /*null not null */
    private OptionGroup group;
    
    /*null  */
    private java.lang.String optionValue;
    
    /*null  */
    private java.lang.String optionLabel1;
    
    private java.lang.String optionLabel2;
    
    private java.lang.String optionLabel3;
    
    private java.lang.String optionLabel4;
    
    private java.lang.String optionLabel5;
    
    /*null  */
    private Integer displayOrder;
    
    public SelectOption()
    {
    }
    
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
    
    @ManyToOne
    @JoinColumn(name = "f_group_id")
    public OptionGroup getGroup()
    {
        return group;
    }
    
    public void setGroup(OptionGroup group)
    {
        this.group = group;
    }
    
    /**
     * 选项的标志。
     * 一般的在一个选项组中应该是唯一的。
     * 
     * @return
     */
    public String getOptionValue()
    {
        return optionValue;
    }
    
    public void setOptionValue(java.lang.String optionValue)
    {
        this.optionValue = optionValue;
    }
    
    public java.lang.String getOptionLabel1()
    {
        return optionLabel1;
    }
    
    public void setOptionLabel1(java.lang.String optionLabel1)
    {
        this.optionLabel1 = optionLabel1;
    }
    
    public java.lang.String getOptionLabel2()
    {
        return optionLabel2;
    }
    
    public void setOptionLabel2(java.lang.String optionLabel2)
    {
        this.optionLabel2 = optionLabel2;
    }
    
    public java.lang.String getOptionLabel3()
    {
        return optionLabel3;
    }
    
    public void setOptionLabel3(java.lang.String optionLabel3)
    {
        this.optionLabel3 = optionLabel3;
    }
    
    public java.lang.String getOptionLabel4()
    {
        return optionLabel4;
    }
    
    public void setOptionLabel4(java.lang.String optionLabel4)
    {
        this.optionLabel4 = optionLabel4;
    }
    
    public java.lang.String getOptionLabel5()
    {
        return optionLabel5;
    }
    
    public void setOptionLabel5(java.lang.String optionLabel5)
    {
        this.optionLabel5 = optionLabel5;
    }
    
    /**
     * 序号。
     * 
     * @return
     */
    public Integer getDisplayOrder()
    {
        return displayOrder;
    }
    
    public void setDisplayOrder(Integer displayOrder)
    {
        this.displayOrder = displayOrder;
    }
    
    /**
     * 为了兼容原来标签使用的ValueLabel。
     * @return
     */
    @Transient
    public String getLabel()
    {
        return optionLabel1;
    }
    
    /**
     * 为了兼容原来标签使用的ValueLabel。
     * @return
     */
    @Transient
    public String getValue()
    {
        return optionValue;
    }
    
}
