package com.yuqiaotech.common.web.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseModel implements Serializable
{
    
    private Date created;//创建时间
    
    private Long createdBy;//创建人ID
    
    private String createdUser;//创建人姓名
    
    private Date updated;//最后更新时间
    
    private Long updatedBy;//最后更新人ID
    
    private String updatedUser;//最后更新人姓名
    
    private String clientOS;
    
    private String clientMachine;
    
    /**
     * 创建时间
     * @return
     */
    @Column(name = "f_created")
    @CreatedDate
    public Date getCreated()
    {
        return created;
    }
    
    public void setCreated(Date created)
    {
        this.created = created;
    }
    
    /**
     * 创建人ID
     * @return
     */
    @Column(name = "f_created_by")
    @CreatedBy
    public Long getCreatedBy()
    {
        return createdBy;
    }
    
    public void setCreatedBy(Long createdBy)
    {
        this.createdBy = createdBy;
    }
    
    /**
     * 创建人姓名
     * @return
     */
    @Column(name = "f_created_user")
    public String getCreatedUser()
    {
        return createdUser;
    }
    
    public void setCreatedUser(String createdUser)
    {
        this.createdUser = createdUser;
    }
    
    /**
     * 最后更新时间
     * @return
     */
    @Column(name = "f_updated")
    @LastModifiedDate
    public Date getUpdated()
    {
        return updated;
    }
    
    public void setUpdated(Date updated)
    {
        this.updated = updated;
    }
    
    /**
     * 最后更新人ID
     * @return
     */
    @Column(name = "f_updated_by")
    @LastModifiedBy
    public Long getUpdatedBy()
    {
        return updatedBy;
    }
    
    public void setUpdatedBy(Long updatedBy)
    {
        this.updatedBy = updatedBy;
    }
    
    /**
     * 最后更新人姓名
     * @return
     */
    @Column(name = "f_updated_user")
    public String getUpdatedUser()
    {
        return updatedUser;
    }
    
    public void setUpdatedUser(String updatedUser)
    {
        this.updatedUser = updatedUser;
    }
    
    /**
     * 客户端操作系统。
     * @return
     */
    public String getClientOS()
    {
        return clientOS;
    }
    
    public void setClientOS(String clientOS)
    {
        this.clientOS = clientOS;
    }
    
    /**
     * 客户端机型。
     * @return
     */
    public String getClientMachine()
    {
        return clientMachine;
    }
    
    public void setClientMachine(String clientMachine)
    {
        this.clientMachine = clientMachine;
    }
    
}
