package com.yuqiaotech.zsnews.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.yuqiaotech.common.tools.UcpaasSms.UcpaasSms;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.domain.response.Result;

/**
 * Created on 2020/9/1 4:27 下午.
 *
 * @Author;fanchuanbin
 */

@Controller
@RequestMapping("/attachment")
public class AttachmentController extends BaseController
{
    @ResponseBody
    @RequestMapping(value = "/showImage", method = RequestMethod.GET)
    public void showImage(String objectId, String objectType, String fileName, HttpServletRequest request,
        HttpServletResponse response)
        throws IOException
    {
        showImg(objectId, objectType, fileName, null, null, request, response);
    }
    
    @ResponseBody
    @RequestMapping("/showImage")
    public void showImage(@RequestParam Map<String, Object> params, HttpServletRequest request,
        HttpServletResponse response)
        throws IOException
    {
        String objectId = (String)params.get("objectId");
        String objectType = (String)params.get("objectType");
        String fileName = (String)params.get("fileName");
        String small = (String)params.get("small");
        String middle = (String)params.get("middle");
        showImg(objectId, objectType, fileName, small, middle, request, response);
    }
    
    private void showImg(String objectId, String objectType, String fileName, String small, String middle,
        HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        JSONObject rtn = new JSONObject();
        if (objectId == null || StringUtils.isEmpty(objectType) || StringUtils.isEmpty(fileName))
        {
            String msg =
                "传递参数不够:objectId=[" + objectId + "],objectType=[" + objectType + "],fileName=[" + fileName + "]";
            rtn.put("exeflag", false);
            rtn.put("errmsg", msg);
            responseWrite(rtn.toString());
            return;
        }
        
        String filePath = attachmentRoot + "/" + objectType + "/" + objectId + "/" + fileName;
        if (small != null && !"".equals(small) && !"false".equals(small))
        {
            filePath = attachmentRoot + "/" + objectType + "/" + objectId + "/" + fileName + ".small";
        }
        if (middle != null && !"".equals(middle) && !"false".equals(middle))
        {
            filePath = attachmentRoot + "/" + objectType + "/" + objectId + "/" + fileName + ".middle";
        }
        File file = new File(filePath);
        if ((file == null || !file.exists()))
        {
            File pf = new File(attachmentRoot + "/" + objectType + "/" + objectId);
            if (pf != null && pf.exists())
            {
                File[] fs = pf.listFiles();
                for (int i = 0; fs != null && i < fs.length; i++)
                {//后续加上优先级判断
                    if (small != null && !"".equals(small) && !"false".equals(small)
                        && fs[i].getName().endsWith("small"))
                    {
                        file = fs[i];
                        break;
                    }
                    else if (middle != null && !"".equals(middle) && !"false".equals(middle)
                        && fs[i].getName().endsWith("middle"))
                    {
                        file = fs[i];
                        break;
                    }
                }
                
                if (file == null || !file.exists())
                {//还是空的
                    for (int i = 0; fs != null && i < fs.length; i++)
                    {
                        if (!fs[i].getName().endsWith("mp4"))
                        {
                            file = fs[i];
                            break;
                        }
                    }
                }
                
            }
        }
        if (file != null && file.isFile())
        {
            showImg(file.getAbsolutePath());
        }
        else
        {
            String useragent = request.getHeader("user-agent");
            if (useragent != null && useragent.contains("Mozilla"))
            {//浏览器访问的话，返回默认图片
                filePath = attachmentRoot + "/blankImg.jpg";
                showImg(filePath);
            }
            else
            {
                rtn.put("exeflag", false);
                rtn.put("fileName", fileName);
                rtn.put("errmsg", "后台没有该文件");
                responseWrite(rtn.toString());
            }
        }
    }
    
    /**2.显示图片*/
    private void showImg(String imgPath)
        throws IOException
    {
        showImg(imgPath, true);
    }
    
    private void showImg(String imgPath, boolean autoBlank)
        throws IOException
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        
        int m = 0;
        File f = new File(imgPath);
        FileInputStream in = null;
        try
        {
            if (f != null && f.exists())
            {
                in = new FileInputStream(f);
            }
        }
        catch (Exception e)
        {
            String useragent = request.getHeader("user-agent");
            //System.out.println("useragent=[\n"+useragent+"\n]");
            if (useragent != null && useragent.contains("Mozilla"))
            {//浏览器访问的话，返回默认图片
                in = new FileInputStream(attachmentRoot + "/blankImg.jpg");
            }
        }
        //System.out.println("in=["+in+"]");
        if (in != null)
        {
            byte[] bytes = new byte[1024];
            
            if (f.getName().endsWith("mp4"))
            {
                response.setContentType("video/mp4");
            }
            else
            {
                response.setContentType("image/jpeg");
            }
            OutputStream outx = response.getOutputStream();
            while ((m = in.read(bytes)) != -1)
            {
                outx.write(bytes, 0, m);
            }
            in.close();
        }
    }
    
    /**
     * 获取手机验证码
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/phoneCode")
    public Result GetCode(@RequestParam Map<String, Object> params)
    {
        Result<Object> result = new Result<>();
        try
        {
            /**
             * 这里还要加上发送短信验证码的代码
             */
            String mobile = (String)params.get("phone");
            String templateId = "560383";
            
            String number = "";
            number = GenerateNumber();
            String coderesult = UcpaasSms.templateSMS(templateId, mobile, number);
            JSONObject smsRes = JSONObject.parseObject(coderesult);
            System.out.println("smsRes:" + smsRes);
            if (smsRes.containsKey("code") && "000000".equals(smsRes.getString("code")))
            {
                result.setSuccess(true);
                result.setData(number);
                result.setMsg("发送成功");
            }
            else
            {
                result.setSuccess(false);
                result.setMsg(" 短信平台返回码异常：" + smsRes.getString("code") + ",msg=" + smsRes.getString("msg"));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            result.setSuccess(false);
            result.setMsg(" 后台出现异常，请联系相关人员检查！");
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping("/showVideo")
    public void getVideo(@RequestParam Map<String, Object> params, HttpServletRequest request,
        HttpServletResponse response)
    {
        String objectId = (String)params.get("objectId");
        String objectType = (String)params.get("objectType");
        String fileName = (String)params.get("fileName");
        String filePath = attachmentRoot + "/" + objectType + "/" + objectId + "/" + fileName;
        
        response.reset();
        //获取从那个字节开始读取文件
        String rangeString = request.getHeader("Range");
        
        try
        {
            //获取响应的输出流
            OutputStream outputStream = response.getOutputStream();
            File file = new File(filePath);
            if (file.exists())
            {
                RandomAccessFile targetFile = new RandomAccessFile(file, "r");
                long fileLength = targetFile.length();
                //播放
                if (rangeString != null)
                {
                    
                    long range =
                        Long.valueOf(rangeString.substring(rangeString.indexOf("=") + 1, rangeString.indexOf("-")));
                    //设置内容类型
                    response.setHeader("Content-Type", "video/mp4");
                    //设置此次相应返回的数据长度
                    response.setHeader("Content-Length", String.valueOf(fileLength - range));
                    //设置此次相应返回的数据范围
                    response.setHeader("Content-Range", "bytes " + range + "-" + (fileLength - 1) + "/" + fileLength);
                    //返回码需要为206，而不是200
                    response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                    //设定文件读取开始位置（以字节为单位）
                    targetFile.seek(range);
                }
                else
                {//下载
                    
                    //设置响应头，把文件名字设置好
                    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                    //设置文件长度
                    response.setHeader("Content-Length", String.valueOf(fileLength));
                    //解决编码问题
                    response.setHeader("Content-Type", "application/octet-stream");
                }
                
                byte[] cache = new byte[1024 * 300];
                int flag;
                while ((flag = targetFile.read(cache)) != -1)
                {
                    outputStream.write(cache, 0, flag);
                }
            }
            else
            {
                String message = "file:" + fileName + " not exists";
                //解决编码问题
                response.setHeader("Content-Type", "application/json");
                outputStream.write(message.getBytes(StandardCharsets.UTF_8));
            }
            
            outputStream.flush();
            outputStream.close();
            
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private String GenerateNumber()
    {
        int x = (int)(1 + Math.random() * (9999 - 1 + 1));
        String param = String.format("%04d", x);// 前面补0
        return param;
    }
}
