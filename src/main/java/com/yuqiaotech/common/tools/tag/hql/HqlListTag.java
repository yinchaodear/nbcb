package com.yuqiaotech.common.tools.tag.hql;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.WebEngineContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressionParser;
import org.thymeleaf.templatemode.TemplateMode;

public class HqlListTag extends AbstractElementTagProcessor {
    // 标签名
    private static final String TAG_NAME = "queryForList";

    // 优先级
    private static final int PRECEDENCE = 10000;

    public HqlListTag(String dialectPrefix) {
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
        IElementTagStructureHandler structureHandler) {
        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext(context);
        String id = iProcessableElementTag.getAttributeValue("id");
        String hql = iProcessableElementTag.getAttributeValue("hql");
        String thhql = iProcessableElementTag.getAttributeValue("th:hql");
        if (StringUtils.isNotEmpty(thhql)) {
            hql = (String)executeExpression(thhql, context);
        }
        List list = new ArrayList();
        if (StringUtils.isNotEmpty(hql)) {
            EntityManager entityManager = applicationContext.getBean(EntityManager.class);
            list = entityManager.createQuery(hql).getResultList();
        }
        HttpServletRequest request = ((WebEngineContext)context).getRequest();
        request.setAttribute(id, list);
    }

    private Object executeExpression(String value, ITemplateContext context) {
        StandardExpressionParser parser = new StandardExpressionParser();
        Expression parseExpression = parser.parseExpression(context, value);
        Object execute = parseExpression.execute(context);
        return execute;
    }
}
