package com.trgis.trmap.wx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.trmap.wx.service.SignService;
import com.trgis.trmap.wx.service.TokenService;

@Controller
public class WeiXinSignatureController {

	@Autowired
	private SignService signService;
	
	@Autowired
	private TokenService tokenService;

	/**
	 * 验证微信的 signature
	 * 
	 * @param signature
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String checkSignature(@RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce,
			@RequestParam("echostr") String echostr) {
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (signService.checkSignature(signature, timestamp, nonce)) {
			return echostr;
		}
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public String getToken() {
		return tokenService.getAppToken();
	}

}
