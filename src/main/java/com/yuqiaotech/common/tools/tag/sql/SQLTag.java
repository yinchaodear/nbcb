package com.yuqiaotech.common.tools.tag.sql;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

@Component
public class SQLTag extends AbstractProcessorDialect
{
    /**
     * 定义方言名称
     */
    private static final String NAME = "系统自定义标签";
    
    /**
     * 定义方言属性
     */
    private static final String PREFIX = "nativeSql";
    
    protected SQLTag()
    {
        super(NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }
    
    @Override
    public Set<IProcessor> getProcessors(final String dialectPrefix)
    {
        final Set<IProcessor> processor = new HashSet<>();
        processor.add(new SqlQueryForlist(PREFIX));
        processor.add(new SqlQueryForUniqueTag(PREFIX));
        return processor;
    }
}
