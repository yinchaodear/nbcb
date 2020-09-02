package com.yuqiaotech.zsnews.controller;

import com.alibaba.fastjson.JSONObject;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.domain.response.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2020/9/1 4:27 下午.
 *
 * @Author;fanchuanbin
 */

@Controller
@RequestMapping("/attachment")
public class AttachmentController  extends BaseController {

	@ResponseBody
	@RequestMapping("/showImage")
	public void showImage(@RequestParam Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws IOException {
		String objectId = (String) params.get("objectId");
		String objectType = (String) params.get("objectType");
		String fileName = (String) params.get("fileName");
		String small = (String) params.get("small");
		String middle = (String) params.get("middle");

		JSONObject rtn = new JSONObject();
		if(objectId==null||StringUtils.isEmpty(objectType)||StringUtils.isEmpty(fileName)){
			String msg = "传递参数不够:objectId=["+objectId+"],objectType=["+objectType+"],fileName=["+fileName+"]";
			rtn.put("exeflag", false);
			rtn.put("errmsg", msg);
			responseWrite(rtn.toString());
			return ;
		}

		String filePath= attachmentRoot + "/" + objectType +"/"+objectId+"/"+fileName;
		if(small!=null && !"".equals(small)&& !"false".equals(small)) {
			filePath= attachmentRoot + "/" + objectType +"/"+objectId+"/"+fileName+".small";
		}
		if(middle!=null && !"".equals(middle)&& !"false".equals(middle)) {
			filePath= attachmentRoot + "/" + objectType+"/"+objectId+"/"+fileName+".middle";
		}
		File file = new File(filePath);
		if( (file==null || !file.exists())) {
			File pf = new File(attachmentRoot + "/" + objectType+"/"+objectId);
			if(pf != null && pf.exists()) {
				File[] fs = pf.listFiles();
				for(int i = 0; fs != null && i < fs.length; i++) {//后续加上优先级判断
					if(small!=null && !"".equals(small)&& !"false".equals(small) && fs[i].getName().endsWith("small")) {
						file = fs[i];
						break;
					}else if(middle!=null && !"".equals(middle)&& !"false".equals(middle) && fs[i].getName().endsWith("middle")) {
						file = fs[i];
						break;
					}
				}

				if(file==null || !file.exists()) {//还是空的
					for(int i = 0; fs != null && i < fs.length; i++) {
						if(!fs[i].getName().endsWith("mp4")) {
							file = fs[i];
							break;
						}
					}
				}

			}
		}
		if(file!=null && file.isFile()){
			showImg(file.getAbsolutePath());
		}else{
			String useragent = request.getHeader("user-agent");
			if(useragent!=null && useragent.contains("Mozilla")){//浏览器访问的话，返回默认图片
				filePath = attachmentRoot + "/blankImg.jpg";
				showImg(filePath);
			}else{
				rtn.put("exeflag", false);
				rtn.put("fileName", fileName);
				rtn.put("errmsg", "后台没有该文件");
				responseWrite(rtn.toString());
			}
		}
	}

	/**2.显示图片*/
	private void showImg(String imgPath) throws IOException{
		showImg(imgPath,true);
	}
	private void showImg(String imgPath, boolean autoBlank) throws IOException {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		HttpServletResponse response = attributes.getResponse();

		int m = 0;
		File f = new File(imgPath);
		FileInputStream in = null;
		try{
			if(f!=null && f.exists()) {
				in = new FileInputStream(f);
			}
		}catch(Exception e){
			String useragent = request.getHeader("user-agent");
			//System.out.println("useragent=[\n"+useragent+"\n]");
			if(useragent!=null && useragent.contains("Mozilla")){//浏览器访问的话，返回默认图片
				in = new FileInputStream(attachmentRoot + "/blankImg.jpg");
			}
		}
		//System.out.println("in=["+in+"]");
		if(in!=null){
			byte[] bytes = new byte[1024];

			if(f.getName().endsWith("mp4")) {
				response.setContentType("video/mp4");
			}
			else {
				response.setContentType("image/jpeg");
			}
			OutputStream outx = response.getOutputStream();
			while((m=in.read(bytes))!=-1){
				outx.write(bytes, 0, m);
			}
			in.close();
		}
	}
}