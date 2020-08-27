package com.yuqiaotech.common.tools.tag.sql;

import java.util.ArrayList;
import java.util.List;

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
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressionParser;
import org.thymeleaf.templatemode.TemplateMode;

public class SqlQueryForlist extends AbstractElementTagProcessor {

    // 标签名
    private static final String TAG_NAME = "sqlQueryForList";

    // 优先级
    private static final int PRECEDENCE = 10000;

    public SqlQueryForlist(String dialectPrefix) {
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
        String sql = iProcessableElementTag.getAttributeValue("sql");
        sql = convertExpression(sql);
        sql = (String)executeExpression(sql, context);
        List list = new ArrayList();
        if (StringUtils.isNotEmpty(sql)) {

            EntityManager entityManager = applicationContext.getBean(EntityManager.class);
            Query query = entityManager.createNativeQuery(sql);
            query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            list = query.getResultList();
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

    private String convertExpression(String sqlString) {
        String newsql = sqlString;
        int idx = -1;
        StringBuffer newSql2 = new StringBuffer();
        boolean hasexpression = false;
        while (newsql.indexOf("${") != -1) {
            hasexpression = true;
            newSql2.append(" '");
            String sql1String = newsql.substring(0, newsql.indexOf("${"));
            newSql2.append(sql1String);
            newSql2.append("' + ${");
            String sql2String = newsql.substring(newsql.indexOf("${") + 2, newsql.indexOf("}"));
            newSql2.append(sql2String).append("} +");
            newsql = newsql.substring(newsql.indexOf("}") + 1);
        }
        if (hasexpression) {
            if (StringUtils.isNotEmpty(newsql) && StringUtils.isNotEmpty(newsql.trim())) {
                newSql2.append("'").append(newsql).append("'");
            }
            newsql = newSql2.toString().trim();
            if (newsql.endsWith("+")) {
                newsql = newsql.substring(0, newsql.length() - 1).trim();
            }
        }
        return newsql;
    }
}
