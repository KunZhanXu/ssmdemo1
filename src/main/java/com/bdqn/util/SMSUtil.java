package com.bdqn.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/*
 *
 *
 * 
 * 
 * username  ?????
 * password_md5   ????
 * mobile  ?????
 * apikey  apikey???
 * content  ????????
 * startTime  UNIX????????§??????????http://tool.chinaz.com/Tools/unixtime.aspx ??UNIX?????????
 * 
 * success:msgid  ???????
error:msgid  ?????  
error:Missing username  ????????
error:Missing password  ???????
error:Missing apikey  APIKEY???
error:Missing recipient  ??????????
error:Missing message content  ???????????
error:Account is blocked  ????????
error:Unrecognized encoding  ???????????
error:APIKEY or password_md5 error  APIKEY?????????
error:Unauthorized IP address  ????? IP ???
error:Account balance is insufficient  ?????
 * */

/**
 * 用于发送短信的工具类
 */

public class SMSUtil {
	public static void sendSms(String phone,String code) throws Exception {
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
		System.setProperty("sun.net.client.defaultReadTimeout", "30000");
		StringBuffer buffer = new StringBuffer();
		String encode = "UTF-8";
		String username = "zjjudian";
		System.out.println(MD5Util.md5Encode("asdf1234"));
		String password_md5 = MD5Util.md5Encode("asdf1234");
		String mobile = phone;
		String apikey = "c9c8430bbed8f79ee5033bb2df6f30cd";
		String content = "您的短信验证码是:"+code;
		try {
			String contentUrlEncode = URLEncoder.encode(content,encode);
			buffer.append("http://m.5c.com.cn/api/send/index.php?username="+username+"&password_md5="+password_md5+"&mobile="+mobile+"&apikey="+apikey+"&content="+contentUrlEncode+"&encode="+encode);
			URL url = new URL(buffer.toString());
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String result = reader.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}