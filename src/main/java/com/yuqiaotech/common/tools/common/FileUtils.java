package com.yuqiaotech.common.tools.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件处理工具类
 */
public class FileUtils
{
    public File createFile(String fileName)
    {
        File file = null;
        try
        {
            file = new File(fileName);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists())
            {
                parent.mkdirs();
            }
            file.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return file;
    }
    
    public static void deleteDir(File dir)
    {
        File[] filelist = dir.listFiles();
        for (File file : filelist)
        {
            if (file.isFile())
            {
                file.delete();
            }
            else
            {
                deleteDir(file);
            }
        }
        dir.delete();
    }
    
    public static void copy(File origin, File newfile)
        throws FileNotFoundException, IOException
    {
        if (!newfile.getParentFile().exists())
        {
            newfile.getParentFile().mkdirs();
        }
        FileInputStream fis = new FileInputStream(origin);
        FileOutputStream fos = new FileOutputStream(newfile);
        byte[] buf = new byte[2048];
        int read;
        while ((read = fis.read(buf)) != -1)
        {
            fos.write(buf, 0, read);
        }
        fis.close();
        fos.close();
    }
    
    public static void writeFile(String filename, String contentStr, String charset)
        throws FileNotFoundException, IOException
    {
        byte[] content = contentStr.getBytes(charset);
        FileOutputStream fos = new FileOutputStream(filename);
        fos.write(content);
        fos.close();
    }
    
    public static void writeFile(File file, String contentStr, String charset)
        throws FileNotFoundException, IOException
    {
        byte[] content = contentStr.getBytes(charset);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content);
        fos.close();
    }
    
    public static void writeFileWithParent(String filename, String contentStr, String charset)
        throws FileNotFoundException, IOException
    {
        File file = new File(filename);
        File parent = file.getParentFile();
        if (!parent.exists())
        {
            parent.mkdirs();
        }
        byte[] content = contentStr.getBytes(charset);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content);
        fos.close();
    }
    
    public static void writeFileWithParent(File file, String contentStr, String charset)
        throws FileNotFoundException, IOException
    {
        File parent = file.getParentFile();
        if (!parent.exists())
        {
            parent.mkdirs();
        }
        byte[] content = contentStr.getBytes(charset);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content);
        fos.close();
    }
    
    public static void writeFile(String filename, byte[] content)
        throws FileNotFoundException, IOException
    {
        FileOutputStream fos = new FileOutputStream(filename);
        fos.write(content);
        fos.close();
    }
    
    public static void writeFile(File file, byte[] content)
        throws FileNotFoundException, IOException
    {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content);
        fos.close();
    }
    
    public static void writeFileWithParent(String filename, byte[] content)
        throws FileNotFoundException, IOException
    {
        File file = new File(filename);
        File parent = file.getParentFile();
        if (!parent.exists())
        {
            parent.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content);
        fos.close();
    }
    
    public static void writeFileWithParent(File file, byte[] content)
        throws FileNotFoundException, IOException
    {
        
        File parent = file.getParentFile();
        if (!parent.exists())
        {
            parent.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content);
        fos.close();
    }
    
    public static byte[] readFile(File file)
        throws IOException
    {
        FileInputStream fis = new FileInputStream(file);
        byte[] buf = new byte[2048];
        int read;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((read = fis.read(buf)) != -1)
        {
            bos.write(buf, 0, read);
        }
        
        fis.close();
        return bos.toByteArray();
    }
    
    /**
     * 按行读取内容，自动去重
     * 
     * @param fileName
     * @return
     */
    public static List<String> readFile(String fileName)
    {
        List<String> resultList = new ArrayList<String>();
        File f = new File(fileName);
        BufferedReader reader = null;
        try
        {
            FileReader fr = new FileReader(f);
            reader = new BufferedReader(fr);
            String tempString = null;
            
            while ((tempString = reader.readLine()) != null)
            {
                if (StringUtils.isNotEmpty(tempString) && !resultList.contains(tempString))
                {
                    resultList.add(tempString);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return resultList;
    }
    
    public static List<String> readFile2(File f)
    {
        List<String> resultList = new ArrayList<String>();
        BufferedReader reader = null;
        try
        {
            FileReader fr = new FileReader(f);
            reader = new BufferedReader(fr);
            String tempString = null;
            
            while ((tempString = reader.readLine()) != null)
            {
                if (StringUtils.isNotEmpty(tempString) && !resultList.contains(tempString))
                {
                    resultList.add(tempString);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return resultList;
    }
    
    public static void writeFile(String fileName, String content, boolean append)
    {
        FileWriter writer = null;
        try
        {
            File file = new File(fileName);
            File parent = file.getParentFile();
            if (!parent.exists())
            {
                parent.mkdirs();
            }
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(fileName, true);
            writer.write(content);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (writer != null)
                {
                    writer.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
