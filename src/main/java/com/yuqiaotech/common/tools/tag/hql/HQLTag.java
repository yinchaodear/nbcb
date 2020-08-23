package com.yuqiaotech.common.tools.tag.hql;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

@Component
public class HQLTag extends AbstractProcessorDialect
{
    /**
     * 定义方言名称
     */
    private static final String NAME = "系统自定义标签";
    
    /**
     * 定义方言属性
     */
    private static final String PREFIX = "hql";
    
    protected HQLTag()
    {
        super(NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }
    
    @Override
    public Set<IProcessor> getProcessors(final String dialectPrefix)
    {
        final Set<IProcessor> processor = new HashSet<>();
        processor.add(new HqlListTag(PREFIX));
        processor.add(new HqlQueryForUniqueTag(PREFIX));
        return processor;
    }
}
