package com.yuqiaotech.common.tools.tag.sql;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.WebEngineContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.templatemode.TemplateMode;

public class SqlQueryForUniqueTag extends AbstractElementTagProcessor
{
    
    // 标签名
    private static final String TAG_NAME = "sqlQueryForUnique";
    
    // 优先级
    private static final int PRECEDENCE = 10000;
    
    public SqlQueryForUniqueTag(String dialectPrefix)
    {
        super(
            // 模板类型为HTML
            TemplateMode.HTML,
            // 标签方言前缀
            dialectPrefix,
            // 标签名称
            TAG_NAME,
            // 将标签前缀应用于标签名称
            true,
            // 无属性名称：将通过标签名称匹配
            null,
            // 没有要应用于属性名称的前缀
            false,
            // 优先级
            PRECEDENCE);
    }
    
    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag iProcessableElementTag,
        IElementTagStructureHandler structureHandler)
    {
        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext(context);
        String id = iProcessableElementTag.getAttributeValue("id");
        String sql = iProcessableElementTag.getAttributeValue("sql");
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNoneBlank(sql))
        {
            EntityManager entityManager = applicationContext.getBean(EntityManager.class);
            Query query = entityManager.createNativeQuery(sql);
            query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            map = (Map<String, Object>)query.getSingleResult();
        }
        HttpServletRequest request = ((WebEngineContext)context).getRequest();
        request.setAttribute(id, map);
    }
    
}
