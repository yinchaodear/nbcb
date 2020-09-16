
package com.yuqiaotech.zsnews.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.yuqiaotech.zsnews.model.Category;
import com.yuqiaotech.zsnews.model.Channel;
import com.yuqiaotech.zsnews.model.ChannelFollower;
import com.yuqiaotech.zsnews.model.Column;
import com.yuqiaotech.zsnews.model.UserInfo;

/**
 * 频道管理
 *
 */
@RestController
@RequestMapping(value = {"register"})
@CrossOrigin(origins = "*", maxAge = 3600)
public class RegisterlService extends BaseController
{
    private static String MODULE_PATH = "zsnews/channel/";
    
    @Autowired
    private BaseRepository<Channel, Long> channelRepository;
    
    @Autowired
    private BaseRepository<ChannelFollower, Long> channelFollowerRepository;
    
    @Autowired
    private BaseRepository<Category, Long> categoryRepository;
    
    @Autowired
    private BaseRepository<Column, Long> columnRepository;
    
    @Autowired
    private BaseRepository<UserInfo, Long> userInfoRepository;
   
    
    /*
     * 用户注册
     */
    @RequestMapping("register")
    public Result Register(@RequestBody UserInfo user) {
       System.out.println("ChannelService.Register()");
       Map result = new HashMap<>();   
       System.err.println(user.getUsername());
       System.err.println(user.getMobile());
       System.err.println(user.getPwd());
       String sql ="SELECT * FROM t_user_info where f_username ='"+user.getUsername()+"'";
       List map =  userInfoRepository.findMapByNativeSql(sql);
       if(!map.isEmpty()){
    	   result.put("msg", 1); //1 用户名重复
    	   return success(result);
       }
       sql ="SELECT * FROM t_user_info where f_mobile ="+user.getMobile();
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
     * 用户注册
     */
    @RequestMapping("registercode")
    public Result RegisterCode(@RequestBody User user) {
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
				number = GenerateNumber();
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
    
   

	private String GenerateNumber() {
		int x = (int) (1 + Math.random() * (9999 - 1 + 1));
		String param = String.format("%04d", x);// 前面补0
		return param;
	}
}
