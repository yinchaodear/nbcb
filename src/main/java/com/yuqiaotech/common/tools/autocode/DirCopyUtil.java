//Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
//Decompiler options: packimports(3) fieldsfirst ansi 
//Source File Name:   DirCopyUtil.java

package com.yuqiaotech.common.tools.autocode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DirCopyUtil
{
    private static final Log log = LogFactory.getLog(DirCopyUtil.class);
    
    public static class FileFilter
    {
        public boolean pass(File file)
        {
            return true;
        };
    }
    
    static class FileCopier implements Copyable
    {
        
        File src;
        
        public void copy(File file, FileFilter filter)
        {
            try
            {
                if (!file.exists())
                    file.createNewFile();
                if (!file.isFile())
                    throw new IllegalArgumentException("passed in paremter has to be file" + file);
                FileInputStream fileinputstream = new FileInputStream(src);
                FileOutputStream fileoutputstream = new FileOutputStream(file);
                byte abyte0[] = new byte[4096];
                for (int i = 0; (i = fileinputstream.read(abyte0, 0, abyte0.length)) > 0;)
                    fileoutputstream.write(abyte0, 0, i);
                
                fileinputstream.close();
                fileoutputstream.close();
            }
            catch (IOException ioe)
            {
                log.error(ioe.getMessage(), ioe);
            }
            
        }
        
        public FileCopier(File file)
        {
            if (!file.isFile())
            {
                throw new IllegalArgumentException("passed in paremter has to be file" + file);
            }
            else
            {
                src = file;
                return;
            }
        }
    }
    
    static class DirCopier implements Copyable
    {
        
        File srcDir;
        
        public void copy(File file, FileFilter filter)
            throws IOException
        {
            if (!file.exists() && !file.mkdirs())
                throw new IOException("unable to create dir:" + file);
            if (!file.isDirectory())
                throw new IllegalArgumentException("passed in paremter has to be directory" + file);
            File afile[] = srcDir.listFiles();
            for (int i = 0; i < afile.length; i++)
            {
                File file1 = afile[i];
                String s = file1.getCanonicalPath();
                if (s.endsWith(File.separator))
                    s = s.substring(0, s.length() - File.separator.length());
                File file2 = new File(file, s.substring(s.lastIndexOf(File.separatorChar) + 1));
                DirCopyUtil.getCopyable(afile[i]).copy(file2, filter);
            }
            
        }
        
        public DirCopier(File file)
        {
            if (!file.isDirectory())
            {
                throw new IllegalArgumentException("passed in paremter has to be directory" + file);
            }
            else
            {
                srcDir = file;
                return;
            }
        }
    }
    
    static interface Copyable
    {
        
        public abstract void copy(File file, FileFilter filter)
            throws IOException;
    }
    
    public DirCopyUtil()
    {
    }
    
    public static void copy(File file, File file1, FileFilter filter)
        throws IOException
    {
        getCopyable(file).copy(file1, filter);
    }
    
    public static void copy(File file, File file1)
        throws IOException
    {
        copy(file, file1, null);
    }
    
    public static Copyable getCopyable(File file)
    {
        if (file.isFile())
            return new FileCopier(file);
        else
            return new DirCopier(file);
    }
}
