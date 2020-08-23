package com.yuqiaotech.sysadmin.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 组织机构。
 */
@Entity
public class Organisation
{
    
    private Long id;
    
    private String orgName;
    
    private String orgType;
    
    private String orgCode;
    
    private Organisation parentOrg;
    
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
    
    /**
     * 上级机构。
     * @Editor
     * type="sqlSelect"
     * sql="from Organisation order by orgName"
     * listKey="id"
     * listValue="orgName"
     * @searchItem
     * displayType="sqlSelect"
     * sql="from Organisation order by orgName"
     * listKey="id"
     * listValue="orgName"
     * @nameProperty
     * value="orgName"
     * @return
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_parent_org_id")
    public Organisation getParentOrg()
    {
        return parentOrg;
    }
    
    public void setParentOrg(Organisation parentOrg)
    {
        this.parentOrg = parentOrg;
    }
    
}
