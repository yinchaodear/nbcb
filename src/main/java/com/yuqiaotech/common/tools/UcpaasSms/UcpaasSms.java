package com.yuqiaotech.common.tools.UcpaasSms;

import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Encoder;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
/***
 * 短信接口v2
 */
public class UcpaasSms {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final String UTF8 = "utf-8";  

	private static final  String accountSid = "a2eea04a6e0cdc00be994e258d4f258e";
	private static final  String authToken = "f0ad640a9363e7116d16ac0d1072e64d";
	private static final  String appId = "0a35ce4e244a4da5b23c441c62409b91";
	/**
	 * 短信单发接口
	 * 返回格式：
	 * rtn={"code":"000000","count":"1","create_date":"2019-08-22 11:47:34","mobile":"18260027172","msg":"OK","smsid":"d44974357463934b15a219965049e6a3","uid":""}
	 * rtn={"code":"105147","count":"0","create_date":"","mobile":"13814170516","msg":"对同个号码发送短信超过限定频率","smsid":"","uid":""}
	 * 确认了一下，respCode=000000不准确,有些发送失败的返回码也是000000。
	 * 目前遇到2种情况：1短信平台的关键字拦截，2手机号是空号。
	 */
	public static String templateSMS(String templateId, String to, String param) throws Exception {

		//指定模板单发
		String action="https://open.ucpaas.com/ol/sms/sendsms";
		JSONObject json = new JSONObject();
		json.put("sid", accountSid);
		json.put("token", authToken);
		json.put("appid", appId);
		json.put("templateid", templateId);
		json.put("param", param);
		json.put("mobile", to);
//		json.put("uid", "");
		String user_define_json = json.toString();

//		String timestamp =  sdf.format(new Date());// 时间戳
//		String signature = getSignature(accountSid, authToken, timestamp);
//		String auth = getBase64Encoder(accountSid, timestamp);
//		String action = "https://api.ucpaas.com/2014-06-30/Accounts/"+accountSid+"/Messages/templateSMS?sig="
//				+ signature;

		URL url = new URL(action);
		HttpURLConnection http = (HttpURLConnection) url.openConnection();

		http.setRequestMethod("POST");
		http.setRequestProperty("Accept", "application/json");
		http.setRequestProperty("Content-Type",
				"application/json;charset=utf-8");
		//http.setRequestProperty("Authorization", auth);
		http.setDoOutput(true);
		http.setDoInput(true);
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
		System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

		http.connect();
		OutputStream os = http.getOutputStream();
		os.write(user_define_json.getBytes("UTF-8"));// 传入参数
		os.flush();
		os.close();

		InputStream is = http.getInputStream();
		int size = is.available();
		byte[] jsonBytes = new byte[size];
		is.read(jsonBytes);
		String message = new String(jsonBytes, "UTF-8");
		return message;
	}

	private static String getSignature(String accountSid, String authToken,
			String timestamp) throws Exception {
		String sig = accountSid + authToken + timestamp;
	       MessageDigest md = MessageDigest.getInstance("MD5");  
	       byte[] b = md.digest(sig.getBytes(UTF8));  
	       String signature = byte2HexStr(b); 
	       
		return signature;
	}

	private static String getBase64Encoder(String accountSid, String timestamp) throws Exception {
		String src = accountSid + ":" + timestamp;
        BASE64Encoder encoder = new BASE64Encoder();  
        String auth = encoder.encode(src.getBytes(UTF8)); 
		return auth;
	}
    /** 
     * 字节数组转化为大写16进制字符串 
     * @param b 
     * @return 
     */  
    private static String byte2HexStr(byte[] b) {  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < b.length; i++) {  
            String s = Integer.toHexString(b[i] & 0xFF);  
            if (s.length() == 1) {  
                sb.append("0");  
            }  
            sb.append(s.toUpperCase());  
        }  
        return sb.toString();  
    } 


	public static void main(String[] args) throws Exception {
		 String templateId = "13216";
		 String to = "18652930955";
		 String param = "1966,f看看";
		 
		 templateId = "392614";//中山达
		 templateId = "476205";//足力健
		 templateId ="494998";//聚爱生活
		 to = "13814170516";
		 //19928197119
		 param = "1233";
		
		String str = templateSMS(templateId, to, param);
		System.out.println("rtn="+str);
		//新接口返回格式，rtn={"code":"000000","count":"1","create_date":"2019-08-22 11:47:34","mobile":"18260027172","msg":"OK","smsid":"d44974357463934b15a219965049e6a3","uid":""}
		//rtn={"code":"105147","count":"0","create_date":"","mobile":"13814170516","msg":"对同个号码发送短信超过限定频率","smsid":"","uid":""}
		//确认了一下，respCode=000000不准确,有些发送失败的返回码也是000000。
		//目前遇到2种情况：1短信平台的关键字拦截，2手机号是空号。
		//老接口格式，废弃，rtn={"resp":{"respCode":"000000","failure":"0","templateSMS":{"createDate":"20190620110641","smsId":"b78b7c5c77cdefc1397729867af485a8"}}}
	}
}
