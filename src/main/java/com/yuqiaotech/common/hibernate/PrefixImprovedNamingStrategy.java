package com.yuqiaotech.common.hibernate;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * 数据库字段映射策略
 * @author Admin
 *
 */
public class PrefixImprovedNamingStrategy implements PhysicalNamingStrategy
{
    private String tablePrefix = "t_";
    
    private String columnPrefix = "f_";
    
    private Integer maxLength = 25;
    
    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment)
    {
        if (name != null)
        {
            String text = name.getText();
            if (text != null && StringUtils.isNotEmpty(text) && Character.isUpperCase(text.charAt(0)))
            {
                return Identifier.toIdentifier(text.toLowerCase());
            }
        }
        return name;
    }
    
    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment)
    {
        if (name != null)
        {
            String text = name.getText();
            if (text != null && StringUtils.isNotEmpty(text) && Character.isUpperCase(text.charAt(0)))
            {
                return Identifier.toIdentifier(text.toLowerCase());
            }
        }
        return name;
    }
    
    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment)
    {
        if (name != null)
        {
            String text = name.getText();
            if (text != null && StringUtils.isNotEmpty(text) && Character.isUpperCase(text.charAt(0)))
            {
                return Identifier.toIdentifier(classToTableName(text));
            }
        }
        return name;
    }
    
    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment)
    {
        if (name != null)
        {
            String text = name.getText();
            if (text != null && StringUtils.isNotEmpty(text) && Character.isUpperCase(text.charAt(0)))
            {
                return Identifier.toIdentifier(text.toLowerCase());
            }
        }
        return name;
    }
    
    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment)
    {
        if (name != null)
        {
            String text = name.getText();
            if (text != null && StringUtils.isNotEmpty(text))
            {
                if (text.startsWith("yq_"))
                {
                    return Identifier.toIdentifier(text.replace("yq_", ""));
                }
                else if (text.startsWith("f_"))
                {
                    return Identifier.toIdentifier(text);
                }
                else
                {
                    return Identifier.toIdentifier(propertyToColumnName(text));
                }
            }
        }
        return name;
    }
    
    private String convert(String name)
    {
        if (StringUtils.isEmpty(name))
        {
            return name;
        }
        
        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";
        String newName = name.replaceAll(regex, replacement).toLowerCase();
        return newName;
    }
    
    public String propertyToColumnName(String propertyName)
    {
        String n = columnPrefix + convert(propertyName);
        if (maxLength != null)
        {
            if (n.length() > maxLength)
                n = shortIt(n, columnPrefix.length());
        }
        return n;
    }
    
    public String classToTableName(String className)
    {
        String n = tablePrefix + convert(className);
        if (maxLength != null)
        {
            if (n.length() > maxLength)
                n = shortIt(n, tablePrefix.length());
        }
        return n;
    }
    
    private String shortIt(String oriName, int prefixLength)
    {
        String[] segments = oriName.split("_");
        int segLength = (maxLength - prefixLength - segments.length + 1) / (segments.length - 1);
        String newStr = segments[0];
        for (int i = 1; i < segments.length; i++)
        {
            if (segments[i].length() > segLength)
            {
                segments[i] = segments[i].substring(0, segLength);
            }
            newStr += "_" + segments[i];
        }
        return newStr;
    }
    
    public String getTablePrefix()
    {
        return tablePrefix;
    }
    
    public void setTablePrefix(String tablePrefix)
    {
        this.tablePrefix = tablePrefix;
    }
    
    public String getColumnPrefix()
    {
        return columnPrefix;
    }
    
    public void setColumnPrefix(String columnPrefix)
    {
        this.columnPrefix = columnPrefix;
    }
    
    public Integer getMaxLength()
    {
        return maxLength;
    }
    
    public void setMaxLength(Integer maxLength)
    {
        this.maxLength = maxLength;
    }
    
    public static void main(String[] args)
    {
        PrefixImprovedNamingStrategy p = new PrefixImprovedNamingStrategy();
        p.setColumnPrefix("f_");
        p.setTablePrefix("t_");
        p.setMaxLength(25);
        String s = "ProtectedResourceAuthorityMappingSetting";
        System.out.println(s + "=" + p.classToTableName(s));
    }
}
