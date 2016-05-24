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
public class ReceiveMsgController {

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
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String receiveMessage() {
		return null;
		
	}
	

}
