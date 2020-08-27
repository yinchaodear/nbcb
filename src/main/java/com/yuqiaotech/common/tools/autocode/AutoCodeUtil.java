package com.yuqiaotech.common.tools.autocode;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.velocity.VelocityContext;

import com.yuqiaotech.common.tools.autocode.VelocityTemplate.ProduceCallback;

/**
 * 自动生成 通用类
 */
public class AutoCodeUtil
{
    public String pojoName = "";
    
    /**项目源代码的目录*/
    public String srcPath = "";
    
    /**生成的代码保持到哪里*/
    public String saveToRootx = "";
    
    /**包名*/
    public String packageName = "";
    
    /**
     * 创建单表
     */
    public void autoCodeOneModel()
    {
        VelocityTemplate velocityTemplate = new VelocityTemplate();
        //设置编码
        velocityTemplate.setProperty("output.encoding", "UTF-8");
        velocityTemplate.setProperty("input.encoding", "UTF-8");//输入的编码   
        
        //构造两个实体类分析对象 
        final EntityParser entityParser = new EntityParser();
        entityParser.parse(srcPath + "/com/yuqiaotech/" + packageName + "/model/" + pojoName + ".java");
        final VelocityContext context = velocityTemplate.getContext();
        context.put("entityParser", entityParser);
        
        String templateBathpath = this.getClass().getResource("/").getPath() + "../../src/main/resources/auto_code/";
        if (templateBathpath.startsWith("/"))
        {
            templateBathpath = templateBathpath.substring(1);
        }
        
        velocityTemplate.setTemplateRootPath(templateBathpath);
        
        velocityTemplate.produce("EntityController.java.vm", new ProduceCallback()
        {
            public void preProcess()
            {
                context.put("controllerPackage", "com.yuqiaotech." + packageName + ".controller");
                context.put("entityFullClassName", "com.yuqiaotech." + packageName + ".model." + pojoName);
                context.put("requestmapping", packageName + "/" + pojoName.toLowerCase());
            }
            
            public void process(StringWriter writer)
            {
                try
                {
                    String filepath = saveToRootx + File.separator + pojoName + "/java/com/yuqiaotech/" + packageName
                        + "/controller/" + pojoName + "Controller.java";
                    File file = new File(filepath);
                    if (!file.getParentFile().exists())
                        file.getParentFile().mkdirs();
                    if (!file.exists())
                        file.createNewFile();
                    FileUtil.save(writer.toString(), "utf-8", new File(filepath));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        velocityTemplate.produce("main.html.vm", new ProduceCallback()
        {
            public void preProcess()
            {
                context.put("requestmapping", "/" + packageName + "/" + pojoName.toLowerCase() + "/");
            }
            
            public void process(StringWriter writer)
            {
                try
                {
                    String filepath = saveToRootx + File.separator + pojoName + "/resources/" + packageName + "/"
                        + pojoName.toLowerCase() + "/main.html";
                    File file = new File(filepath);
                    if (!file.getParentFile().exists())
                        file.getParentFile().mkdirs();
                    if (!file.exists())
                        file.createNewFile();
                    FileUtil.save(writer.toString(), "utf-8", new File(filepath));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        
        velocityTemplate.produce("add.html.vm", new ProduceCallback()
        {
            public void preProcess()
            {
                context.put("requestmapping", "/" + packageName + "/" + pojoName.toLowerCase() + "/");
            }
            
            public void process(StringWriter writer)
            {
                try
                {
                    String filepath = saveToRootx + File.separator + pojoName + "/resources/" + packageName + "/"
                        + pojoName.toLowerCase() + "/add.html";
                    File file = new File(filepath);
                    if (!file.getParentFile().exists())
                        file.getParentFile().mkdirs();
                    if (!file.exists())
                        file.createNewFile();
                    FileUtil.save(writer.toString(), "utf-8", new File(filepath));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        
        velocityTemplate.produce("edit.html.vm", new ProduceCallback()
        {
            public void preProcess()
            {
                context.put("requestmapping", "/" + packageName + "/" + pojoName.toLowerCase() + "/");
            }
            
            public void process(StringWriter writer)
            {
                try
                {
                    String filepath = saveToRootx + File.separator + pojoName + "/resources/" + packageName + "/"
                        + pojoName.toLowerCase() + "/edit.html";
                    File file = new File(filepath);
                    if (!file.getParentFile().exists())
                        file.getParentFile().mkdirs();
                    if (!file.exists())
                        file.createNewFile();
                    FileUtil.save(writer.toString(), "utf-8", new File(filepath));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        
        velocityTemplate.produce("config.txt.vm", new ProduceCallback()
        {
            public void preProcess()
            {
                context.put("requestmapping", "/" + packageName + "/" + pojoName.toLowerCase() + "/");
            }
            
            public void process(StringWriter writer)
            {
                try
                {
                    String filepath = saveToRootx + File.separator + pojoName + "/resources/" + packageName + "/"
                        + pojoName.toLowerCase() + "/config.txt";
                    File file = new File(filepath);
                    if (!file.getParentFile().exists())
                        file.getParentFile().mkdirs();
                    if (!file.exists())
                        file.createNewFile();
                    FileUtil.save(writer.toString(), "utf-8", new File(filepath));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
