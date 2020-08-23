package com.yuqiaotech.common.tools.autocode;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * 
 * @author Ma PengJu
 *
 */
public class VelocityTemplate
{
    protected VelocityEngine velocityEngine = new VelocityEngine();
    
    protected Properties properties = new Properties();
    
    /* create a context and add data */
    protected VelocityContext context = new VelocityContext();
    
    public static interface ProduceCallback
    {
        public void preProcess();
        
        public void process(StringWriter writer);
    }
    
    public void produce(String tplFile, ProduceCallback callback)
    {
        StringWriter writer = new StringWriter();
        Template tpl;
        try
        {
            velocityEngine.init(properties);//ÿ�ζ���ʼ�����Ƿ���ʣ���������Ƚϱ��ա�
            callback.preProcess();
            tpl = velocityEngine.getTemplate(tplFile);
            tpl.merge(context, writer);
            callback.process(writer);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage() + " tplFile=" + tplFile, e);
        }
    }
    
    /**
     * �ر�д��һ������ģ���·���ķ�����
     * 
     * @param path
     */
    public void setTemplateRootPath(String path)
    {
        properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, path);
    }
    
    /**
     * ��properties������ԡ�
     * 
     * @param key
     * @param value
     */
    public void setProperty(String key, Object value)
    {
        this.properties.put(key, value);
    }
    
    public void getProperty(String key)
    {
        this.properties.get(key);
    }
    
    /**
     * ��context������ԡ�
     * 
     * @param key
     * @param value
     */
    public void setContextProperty(String key, Object value)
    {
        this.context.put(key, value);
    }
    
    public void getContextProperty(String key)
    {
        this.context.get(key);
    }
    
    public VelocityEngine getVelocityEngine()
    {
        return velocityEngine;
    }
    
    public void setVelocityEngine(VelocityEngine velocityEngine)
    {
        this.velocityEngine = velocityEngine;
    }
    
    public Properties getProperties()
    {
        return properties;
    }
    
    public void setProperties(Properties properties)
    {
        this.properties = properties;
    }
    
    public VelocityContext getContext()
    {
        return context;
    }
    
    public void setContext(VelocityContext context)
    {
        this.context = context;
    }
    
}
