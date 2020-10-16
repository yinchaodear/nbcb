package com.yuqiaotech.sysadmin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.yuqiaotech.common.web.base.BaseModel;

/**
 * 组织机构。
 */
@Entity
public class Organisation extends BaseModel
{
    
    private Long id;
    
    private String orgName;
    
    private String orgType;
    
    private String orgCode;
    
    private Integer deltag;//删除标识
    
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
     * 编码。
     * @return
     */
    public String getOrgCode()
    {
        return orgCode;
    }
    
    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }
    
    /**
     * 机构名称。
     * @searchItem
     * displayType="text"
     * @return
     */
    public String getOrgName()
    {
        return orgName;
    }
    
    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
    
    /**
     * 机构类型。
     * @searchItem
     * displayType="text"
     * 如部，局，厅之类的。
     * @return
     */
    public String getOrgType()
    {
        return orgType;
    }
    
    public void setOrgType(String orgType)
    {
        this.orgType = orgType;
    }
    
    public Integer getDeltag()
    {
        return deltag;
    }
    
    public void setDeltag(Integer deltag)
    {
        this.deltag = deltag;
    }
    
}
