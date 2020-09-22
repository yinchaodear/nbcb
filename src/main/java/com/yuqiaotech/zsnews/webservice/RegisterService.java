
package com.yuqiaotech.zsnews.webservice;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.yuqiaotech.common.tools.common.ImgBase64Utils;
import com.yuqiaotech.common.tools.token.JwtUtils;
import com.yuqiaotech.zsnews.model.*;
import com.yuqiaotech.zsnews.service.AttachmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yuqiaotech.common.tools.UcpaasSms.UcpaasSms;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.sysadmin.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * app用户注册
 */
@RestController
@RequestMapping(value = {"register"})
@CrossOrigin(origins = "*", maxAge = 3600)
public class RegisterService extends BaseController
{
    private static String MODULE_PATH = "zsnews/channel/";
    
    @Autowired
    private BaseRepository<Channel, Long> channelRepository;
    
    @Autowired
    private BaseRepository<ChannelFollower, Long> channelFollowerRepository;
    
    @Autowired
    private BaseRepository<Category, Long> categoryRepository;
    
    @Autowired
    private BaseRepository<UserAuths, Long> userAuthsRepository;
    
    @Autowired
    private BaseRepository<UserInfo, Long> userInfoRepository;
   
    
    /*
     * 用户注册
     */
    @RequestMapping("register")
    public Result register(@RequestBody UserInfo user) {
       System.out.println("ChannelService.Register()");
       Map result = new HashMap<>();   
       System.out.println(user.getUsername());
       System.out.println(user.getMobile());
       System.out.println(user.getPwd());
       String sql ="SELECT * FROM t_user_info where f_username ='"+user.getUsername()+"'";
       List map =  userInfoRepository.findMapByNativeSql(sql);
       if(!map.isEmpty()){
    	   result.put("msg", 1); //1 用户名重复
    	   return success(result);
       }
       sql ="SELECT * FROM t_user_info where f_mobile ='"+user.getMobile()+"'";
       map =  userInfoRepository.findMapByNativeSql(sql);
       if(!map.isEmpty()){
    	   result.put("msg", 2); //2 电话重复
    	   return success(result);
       }
       
       userInfoRepository.save(user);
       result.put("msg", 0); //0 成功
       return success(result);
    }

    /*
     * 三方登录用户注册
     */
    @RequestMapping("registerThird")
    public Result registerThird(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("======三方登录用户注册=====进入==");
        Map result = new HashMap<>();
        result.put("code", 0); //0 成功
        try {
            System.out.println("======三方登录用户注册=========params=【" + params.toString() + "】==");
            JSONObject params0 = new JSONObject(params);
            String mobile = params0.getString("mobile");
            String password = params0.getString("password");
            String openid = params0.getString("openid");
            String info = params0.getString("info");
            String loginType = params0.getString("loginType");

            //1.处理用户信息
            UserInfo userInfo = userInfoRepository.findUniqueBy("mobile", mobile, UserInfo.class);
            if (userInfo == null) {
                userInfo = new UserInfo();
                userInfo = userInfoRepository.save(userInfo);
            }
            userInfo.setMobile(mobile);
            userInfo.setUsername(mobile);
            userInfo.setPwd(password);
            if ("WX".equals(loginType)) {//微信
                JSONObject thirdJson = JSONObject.parseObject(info);
                System.out.println("======三方登录用户注册=======微信字符串==thirdJson=【" + thirdJson + "】==");
                if (StringUtils.isEmpty(userInfo.getAvatar())) {
                    String profile_image_url = thirdJson.getString("profile_image_url");
                    String objectType = "userInfo";
                    String objectId = userInfo.getId().toString();
                    String filename = "head.jpg";
                    String pathString = attachmentRoot + "/" + objectType + "/" + objectId + "/" + filename;
                    boolean flag = AttachmentService.downloadPicture(profile_image_url, pathString);
                    System.out.println("======三方登录用户注册=======下载图片结果==flag=【" + flag + "】==");
                    String avatar = ImgBase64Utils.getImgStr(profile_image_url);
                    userInfo.setAvatar(avatar);
                }
                if (StringUtils.isEmpty(userInfo.getNickName()))
                    userInfo.setNickName(thirdJson.getString("name"));
            }
            userInfoRepository.update(userInfo);

            //2.处理userauths和userInfo的关联。
            UserAuths userAuths = userAuthsRepository.queryUniqueResult("from UserAuths where thirdType = '" + loginType + "' and thirdKey = '" + openid + "'", null);
            if (userAuths != null) {
                userAuths.setInfo(info);
                userAuths.setUserinfo(userInfo);
                userAuthsRepository.update(userAuths);
            } else {
                userAuths = new UserAuths();
                userAuths.setThirdKey(openid);
                userAuths.setThirdType(loginType);
                userAuths.setInfo(info);
                userAuths = userAuthsRepository.save(userAuths);
            }
            Map<String, String> map = new HashMap<>();
            map.put("username", String.format("%s%s%s%s%s", openid.trim(),
                    "|", "FRONT", "|", loginType));
            map.put("password", password);
            String token = JwtUtils.getToken(map, 60 * 60 * 24 * 30);
            System.out.println("token:" + token);
            result.put("token",token);

            Cookie cookie=new Cookie("token",token);
            response.addCookie(cookie);

            System.out.println("======注册成功===========");
            return success(result);
        }catch (Exception e){
            System.out.println("======注册失败===========");
            e.printStackTrace();
            result.put("code", 1); //1 失败
            result.put("msg", "服务器后台报错，"+e.getMessage());
            return success("服务器后台报错，"+e.getMessage());
        }
    }
    
    /*
     * 用户注册
     */
    @RequestMapping("registercode")
    public Result registerCode(@RequestBody User user) {
       System.out.println("ChannelService.RegisterCode()");
       Map result = new HashMap<>();
		try {
			/**
			 * 这里还要加上发送短信验证码的代码
			 */
			String mobile = user.getMobile();
//			String sql = " select f_item_value from t_sys_config where f_item_name='ucpassTemplateId'";
//			Map ucpassTemplateMap = manager.queryUniqueMapByNativeSql(sql, null);
//			String templateId = "";
//			if (ucpassTemplateMap != null) {
//				templateId = (String) ucpassTemplateMap.get("f_item_value");
//			}
			String templateId ="474082";//这里先用写死的
			String number = "";
			try {
				number = generateNumber();
				System.err.println(number);
				String coderesult = UcpaasSms.templateSMS(templateId, mobile, number);
				result.put("error", coderesult);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				result.put("error", e.getMessage());
			}
			result.put("msg", "success");
			result.put("code", number);			
			return success(result);
		} catch (Exception e) {
			result.put("msg", "error");
			result.put("reason", e.getMessage());
			return success(result);
		}
    }
    
   

	private String generateNumber() {
		int x = (int) (1 + Math.random() * (9999 - 1 + 1));
		String param = String.format("%04d", x);// 前面补0
		return param;
	}
}
