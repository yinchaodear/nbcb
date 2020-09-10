package com.yuqiaotech.zsnews.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuqiaotech.common.web.base.BaseController;

/**
 *https://blog.csdn.net/qq_23875211/article/details/100533869
 */
@Controller
@RequestMapping("/attachment")
public class UploadController extends BaseController
{
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("objectId") String objectId,
        @RequestParam("objectType") String objectType, HttpServletRequest request)
    {
        String pathString = null;
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        if (file != null)
        {
            pathString = attachmentRoot + "/" + objectType + "/" + objectId + "/" + filename;
        }
        
        try
        {
            File files = new File(pathString);
            if (!files.getParentFile().exists())
            {
                files.getParentFile().mkdirs();
            }
            file.transferTo(files);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return "{\"code\":0,\"msg\":\"" + filename + "\"}";
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
