/*
 * Created on 2004-6-1416:38:16
 *
 * 
 * 
 */
package com.yuqiaotech.common.tools.autocode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author mapengju
 * 
 *  
 */
public class FileUtil
{
    public static long getDirUsedSpace(File f)
    {
        File[] subFiles = f.listFiles();
        int size = 0;
        for (int i = 0; i < subFiles.length; i++)
        {
            File file = subFiles[i];
            if (file.isDirectory())
                size += getDirUsedSpace(file);
            else
                size += file.length();
        }
        return size;
    }
    
    public static void copyFile(File file_in, File file_out)
    {
        
        //instance the File as file_in and file_out
        if (file_out.isDirectory())
        {
            file_out = new File(file_out.getPath() + File.pathSeparator + file_in.getName());
        }
        if (!file_out.getParentFile().exists())
        {
            file_out.getParentFile().mkdirs();
        }
        FileInputStream in1;
        try
        {
            in1 = new FileInputStream(file_in);
            FileOutputStream out1 = new FileOutputStream(file_out);
            byte[] bytes = new byte[1024];
            int c;
            while ((c = in1.read(bytes)) != -1)
                out1.write(bytes, 0, c);
            
            in1.close();
            out1.close();
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
        
    }
    
    public static void copyFile(String file1, String file2)
        throws IOException
    {
        copyFile(new File(file1), new File(file2));
    }
    
    /**
     * �����ļ��С�
     * 
     * @param from
     * @param to
     */
    public static void copyDir(File from, File to)
    {
        try
        {
            DirCopyUtil.copy(from, to);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public static void copySubFiles(File from, File to)
    {
        File files[] = from.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            File f = files[i];
            if (f.isDirectory())
            {
                copyDir(f, new File(to, f.getName()));
            }
            else
            {
                copyFile(f, new File(to, f.getName()));
            }
        }
    }
    
    public static boolean delete(File f)
    {
        if (f != null && f.exists())
        {
            if (f.isDirectory())
            {
                File files[] = f.listFiles();
                for (int i = 0; i < files.length; i++)
                {
                    delete(files[i]);
                }
            }
            return f.delete();
        }
        return true;
    }
    
    public static String readTextFile(String filePath)
        throws IOException
    {
        return readTextFile(filePath, null);
    }
    
    public static String readTextFile(String filePath, String encoding)
        throws IOException
    {
        byte[] b = readFileContent(filePath);
        String fileContent = null;
        if (encoding != null)
            fileContent = new String(b, encoding);
        else
            fileContent = new String(b);
        return fileContent;
    }
    
    public static String readTextFile(File file, String encoding)
        throws IOException
    {
        byte[] b = readFileContent(file);
        String fileContent = new String(b, encoding);
        return fileContent;
    }
    
    public static byte[] readFileContent(String filePath)
        throws IOException
    {
        File file = new File(filePath);
        return readFileContent(file);
    }
    
    public static byte[] readFileContent(File file)
        throws IOException
    {
        DataInputStream fileInputStream = new DataInputStream(new FileInputStream(file));
        long fileLen = file.length();
        byte[] b = new byte[Integer.parseInt(fileLen + "")];
        int lenRead = fileInputStream.read(b);
        fileInputStream.close();
        return b;
    }
    
    public static boolean save(String str, String charset, File file)
    {
        try
        {
            return save(str.getBytes(charset), file, true);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public static boolean save(byte[] content, File file)
    {
        return save(content, file, true);
    }
    
    public static boolean save(byte[] content, File file, boolean createPath)
    {
        if (createPath)
        {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fileOutputStream;
        try
        {
            fileOutputStream = new java.io.FileOutputStream(file);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            dataOutputStream.write(content);
            dataOutputStream.flush();
            dataOutputStream.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
        return true;
    }
    
    public static boolean save(InputStream is, String path)
        throws IOException
    {
        File file = new File(path);
        file.getParentFile().mkdirs();
        FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        byte[] buff = new byte[256];
        int i;
        while ((i = is.read(buff)) != -1)
        {
            dataOutputStream.write(buff, 0, i);
        }
        is.close();
        dataOutputStream.flush();
        dataOutputStream.close();
        return true;
    }
}