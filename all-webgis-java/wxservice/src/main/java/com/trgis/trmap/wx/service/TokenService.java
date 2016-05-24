package com.trgis.trmap.wx.service;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.trgis.httpclient.HttpUtil;
import com.trgis.httpclient.service.HttpService;

@Service
@Lazy(false)
public class TokenService {

	private static final String ACCESS_TOKEN_KEY = "access_token";

	public static final String APPID = "wx117410bba940944f";
	public static final String APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";

	public static final String CHECKTOKEN_URL_TEMP = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

	public static String APPTOKEN = "";
	public static final long EXPRIES_IN = 7150 * 1000; // 由于微信token时间为7200秒，系统稍微提前刷新避免token失效

	@Autowired
	HttpService httpService;
	
	/**
	 * 获取TOKEN
	 * 
	 * @return
	 */
	public String getAppToken() {
		return APPTOKEN;
	}

	@Scheduled(fixedRate = EXPRIES_IN)
	private void doGetAppToken() {
		String tokenUrl = getFormatUrl();
		String response = HttpUtil.readLine(httpService.get(tokenUrl, null));
		JSONObject result = JSONObject.parseObject(response);
		String token = result.getString(ACCESS_TOKEN_KEY);
		APPTOKEN = token;
	}

	/**
	 * 获取微信token验证地址
	 * 
	 * @return
	 */
	private String getFormatUrl() {
		return String.format(CHECKTOKEN_URL_TEMP, APPID, APPSECRET);
	}

}
