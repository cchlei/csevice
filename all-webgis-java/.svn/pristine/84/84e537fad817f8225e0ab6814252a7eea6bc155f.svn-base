package com.trgis.trmap.wx.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignService {

	@Autowired
	private TokenService tokenService;

	/**
	 * 检查微信签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public boolean checkSignature(String signature, String timestamp, String nonce) {
		String[] arr = new String[] { tokenService.getAppToken(), timestamp, nonce };
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			String string = arr[i];
			content.append(string);
		}
		MessageDigest msd = null;
		String tmpStr = null;

		try {
			msd = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = msd.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	private String byteToStr(byte[] digest) {
		String strDigest = "";
		for (int i = 0; i < digest.length; i++) {
			byte b = digest[i];
			strDigest += byteToHexStr(b);
		}
		return strDigest;
	}

	private String byteToHexStr(byte b) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(b >>> 4) & 0X0F];
		tempArr[1] = Digit[b & 0X0F];

		String s = new String(tempArr);
		return s;
	}

}
