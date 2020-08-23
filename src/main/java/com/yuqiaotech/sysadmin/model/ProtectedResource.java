package com.yuqiaotech.sysadmin.model;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

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
    
    private List<ProtectedResourceAuthority> protectedResourceAuthorities;
    
    private Pattern pattern;
    
    private Matcher matcher;
    
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
    
    /**
     * 授权。
     * @return
     */
    @OneToMany
    @JoinColumn(name = "f_protected_resource_id")
    @OrderBy("authority")
    public List<ProtectedResourceAuthority> getProtectedResourceAuthorities()
    {
        return protectedResourceAuthorities;
    }
    
    public void setProtectedResourceAuthorities(List<ProtectedResourceAuthority> protectedResourceAuthorities)
    {
        this.protectedResourceAuthorities = protectedResourceAuthorities;
    }
    
    /**
     * 授权。
     * 
     * 本来这里是和Authority关联的，但有时授权定义并不真实存在而只是按照约定存在的，
     * 所以修改为字符串。比如我们约定，如果用户有source_12的授权，那么该用户就可以访问
     * id为12的ProtectedResource所匹配的那些资源。
     * @return
     */
    @Transient
    public String[] getAuthorities()
    {
        if (protectedResourceAuthorities == null)
            return new String[0];
        
        String[] authorities = new String[protectedResourceAuthorities.size()];
        for (int i = 0; i < authorities.length; i++)
        {
            authorities[i] = protectedResourceAuthorities.get(i).getAuthority();
        }
        return authorities;
    }
    
    @Transient
    public String getAuthoritiesStr()
    {
        String[] authorities = this.getAuthorities();
        String str = "";
        for (int i = 0; i < authorities.length; i++)
        {
            if (i != 0)
                str += ",";
            str += authorities[i];
        }
        return str;
    }
    
    /**
     * 某个角色是否可访问本资源。
     * @param authorityStr
     * @return
     */
    @Transient
    public boolean authorizeTo(String authorityStr)
    {
        if (protectedResourceAuthorities == null)
            return false;
        for (ProtectedResourceAuthority pra : protectedResourceAuthorities)
        {
            if (authorityStr.equals(pra.getAuthority()))
            {
                return true;
            }
        }
        return false;
    }
    
    @Transient
    public boolean match(String str)
    {
        if (pattern == null)
        {
            pattern = Pattern.compile(this.patternStr);
        }
        matcher = pattern.matcher(str);
        return matcher.matches();
    }
    
    /**
     * 顺序号。
     * 对于一票否决，顺序号大的优先，或则说首先应该符合顺序号小的资源要求。
     * @return
     */
    public Integer getCompareOrder()
    {
        return compareOrder;
    }
    
    public void setCompareOrder(Integer compareOrder)
    {
        this.compareOrder = compareOrder;
    }
    
}
