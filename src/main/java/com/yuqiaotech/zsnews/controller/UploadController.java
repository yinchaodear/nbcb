package com.yuqiaotech.zsnews.controller;

import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuqiaotech.common.web.base.BaseController;

import net.coobird.thumbnailator.Thumbnails;
import sun.misc.BASE64Decoder;

/**
 *https://blog.csdn.net/qq_23875211/article/details/100533869
 */
@Controller
@RequestMapping("/attachment")
public class UploadController extends BaseController
{
    private final static String[] picTypeStrings = {"bmp", "gif", "jpeg", "jpg", "png"};
    
    private final static int maxWidth = 400;
    
    private final static int maxHeight = 400;
    
    private final static int maxWidthMiddle = 800;
    
    private final static int maxHeightMiddle = 800;
    
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(@RequestParam(value = "file", required = false) MultipartFile file,
        @RequestParam("objectId") String objectId, @RequestParam("objectType") String objectType,
        @RequestParam(value = "imgBase64", required = false) String imgBase64,
        @RequestParam(value = "imgBase64Name", required = false) String imgBase64Name, HttpServletRequest request)
    {
        String filename = System.currentTimeMillis() + "_" + imgBase64Name;
        if (file != null)
            filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String pathString = attachmentRoot + "/" + objectType + "/" + objectId + "/" + filename;
        File files = new File(pathString);
        if (!files.getParentFile().exists())
        {
            files.getParentFile().mkdirs();
        }
        
        try
        {
            if (file != null)
            {
                file.transferTo(files);
            }
            else if (imgBase64 != null)
            {
                int index = imgBase64.indexOf(",");
                imgBase64 = imgBase64.substring(index + 1);
                generateImage(imgBase64, pathString);
            }
            
            justImage(new File(pathString));
            
            if (new File(pathString + ".small.png").exists())
            {
                org.apache.commons.io.FileUtils.moveFile(new File(pathString + ".small.png"),
                    new File(pathString + ".small"));
            }
            if (new File(pathString + ".middle.png").exists())
            {
                org.apache.commons.io.FileUtils.moveFile(new File(pathString + ".middle.png"),
                    new File(pathString + ".middle"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return "{\"code\":0,\"msg\":\"" + filename + "\"}";
    }
    
    public int[] justImage(File file)
        throws IOException
    {
        String fileName = file.getName();
        String postFix = fileName.substring(fileName.lastIndexOf(".") + 1);
        postFix = postFix.toLowerCase();
        if (Arrays.binarySearch(picTypeStrings, postFix) < 0)
            return null;
        // 获取图片的高宽
        Image imgdist = null;
        File outFile = null;
        try
        {
            imgdist = javax.imageio.ImageIO.read(file);
            int width = imgdist.getWidth(null);
            int height = imgdist.getHeight(null);
            //小图尺寸
            float scaleWidth = ((float)maxWidth / width);
            float scaleHeight = ((float)maxHeight / height);
            float scale = Math.min(scaleHeight, scaleWidth);
            if (scale > 1)
                scale = 1;
            int newWidth = (int)(width * scale);
            int newHeight = (int)(height * scale);
            
            outFile = new File(file.getAbsolutePath() + ".small");//以.small文件存放在path下
            
            //压缩
            Thumbnails.of(file).size(newWidth, newHeight).outputQuality(0.5).outputFormat("png").toFile(outFile);
            
            //中图尺寸
            float scaleWidthMiddle = ((float)maxWidthMiddle / width);
            float scaleHeightMiddle = ((float)maxHeightMiddle / height);
            float scaleMiddle = Math.min(scaleHeightMiddle, scaleWidthMiddle);
            if (scaleMiddle > 1)
                scaleMiddle = 1;
            int newWidthMiddle = (int)(width * scaleMiddle);
            int newHeightMiddle = (int)(height * scaleMiddle);
            
            File middleOutFile = new File(file.getAbsolutePath() + ".middle");//以.middle文件存放在path下
            //压缩
            Thumbnails.of(file)
                .size(newWidthMiddle, newHeightMiddle)
                .outputQuality(0.5)
                .outputFormat("png")
                .toFile(middleOutFile);
            
            return new int[] {width, height};
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
    private static void generateImage(String realStr, String filePath)
    {
        BASE64Decoder decoder = new BASE64Decoder();
        BufferedOutputStream bos = null;
        try
        {
            File f = new File(filePath);
            if (!f.getParentFile().exists())
                f.getParentFile().mkdirs();
            byte[] data = decoder.decodeBuffer(realStr);
            bos = new BufferedOutputStream(new FileOutputStream(filePath));
            bos.write(data);
            bos.flush();
            bos.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
        finally
        {
            if (bos != null)
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
    }
    
    @RequestMapping(value = "/deleteFile", method = RequestMethod.GET)
    @ResponseBody
    public String deleteFile(@RequestParam("objectId") String objectId, @RequestParam("objectType") String objectType,
        @RequestParam("fileName") String fileName, HttpServletRequest request)
    {
        String msg = "";
        if (StringUtils.isNotEmpty(objectId) && StringUtils.isNotEmpty(objectType) && StringUtils.isNotEmpty(fileName))
        {
            String pathString = attachmentRoot + "/" + objectType + "/" + objectId + "/" + fileName;
            File file = new File(pathString);
            if (file.exists())
            {
                file.delete();
            }
            else
            {
                msg = "file not exists";
            }
        }
        else
        {
            msg = "param empty";
        }
        return "{\"code\":0,\"msg\":\"" + msg + "\"}";
    }
}
