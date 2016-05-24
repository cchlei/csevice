package com.trgis.trmap.userauth.util;

import java.security.Key;
import java.security.SecureRandom;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 用户密码加密工具
 * 
 * @author zhangqian
 *
 */
public class UserEncrypt {

	private static final String PUBKEY_SALT = "qtmap&trgis_2015$$$";

	private static final String AES_KEY = "RT@$JZ%^#@ZJR@_T";

	private static final String ALGORITHM = "AES";

	/**
	 * 生成私盐
	 * 
	 * @return
	 */
	public static String generateSalt() {
		String str = UUID.randomUUID().toString();
		String base64 = Base64.encodeToString(str.getBytes());
		String md5 = Md5Hash.fromBase64String(base64).toBase64();
		return md5;
	}

	/**
	 * md5 加密 默认19次循环
	 * 
	 * @param password
	 * @param salt
	 * @return
	 */
	public static String md5hash(String password, Object salt) {
		/*
		 * 盐采用公盐+私盐的形式.私盐存在数据库中,公盐硬编码,防止密码反算.
		 */
		String md5 = new Md5Hash(password, PUBKEY_SALT + salt, 19).toHex();
		return md5;
	}

	/**
	 * md5 加密 默认19次循环
	 * 
	 * @param password
	 * @param salt
	 * @return
	 */
	public static String emailCode(String email, Object salt) {
		/*
		 * 盐采用公盐+私盐的形式.私盐存在数据库中,公盐硬编码,防止密码反算.
		 */
		String md5 = new Md5Hash(email, PUBKEY_SALT + salt, 19).toHex();
		return md5;
	}

	private static Key generateKey(String key) throws Exception {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
			return keySpec;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	protected static byte[] AES_Encrypt(String keyStr, String plainText) throws Exception {
		byte[] encrypt = null;
		Key key = generateKey(keyStr);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		encrypt = cipher.doFinal(plainText.getBytes("UTF-8"));
		return encrypt;
	}

	protected static byte[] AES_Decrypt(String keyStr, String encryptData) throws Exception {
		byte[] decrypt = null;
		Key key = generateKey(keyStr);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		decrypt = cipher.doFinal(encryptData.getBytes("UTF-8"));
		return decrypt;
	}

	/**
	 * base 64 encode
	 * 
	 * @param bytes
	 *            待编码的byte[]
	 * @return 编码后的base 64 code
	 */
	public static String base64Encode(byte[] bytes) {
		return Base64.encodeToString(bytes);
	}

	/**
	 * base 64 decode
	 * 
	 * @param base64Code
	 *            待解码的base 64 code
	 * @return 解码后的byte[]
	 * @throws Exception
	 */
	public static byte[] base64Decode(String base64Code) throws Exception {
		return StringUtils.isEmpty(base64Code) ? null : Base64.decode(base64Code);
	}

	/**
	 * AES加密
	 * 
	 * @param content
	 *            加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * 
	 * @return 加密后的byte[]
	 * 
	 * @throws Exception
	 */
	public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(encryptKey.getBytes());
		kgen.init(128, random);

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));

		return cipher.doFinal(content.getBytes("utf-8"));
	}

	/**
	 * AES加密为base 64 code
	 * 
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * 
	 * @return 加密后的base 64 code
	 * @throws Exception
	 */
	public static String aesEncrypt(String content, String encryptKey) throws Exception {
		return base64Encode(aesEncryptToBytes(content, encryptKey));
	}

	/**
	 * AES解密
	 * 
	 * @param encryptBytes
	 *            待解密的byte[]
	 * @param decryptKey
	 *            解密密钥
	 * @return 解密后的String
	 * @throws Exception
	 */
	public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(decryptKey.getBytes());
		kgen.init(128, random);

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encryptBytes);

		return new String(decryptBytes);
	}

	/**
	 * 将base 64 code AES解密
	 * 
	 * @param encryptStr
	 *            待解密的base 64 code
	 * @param decryptKey
	 *            解密密钥
	 * @return 解密后的string
	 * @throws Exception
	 */
	public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
		return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
	}

	/**
	 * Convert byte[] to hex
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * 
	 * @param src
	 *            byte[] data
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 加密方法
	 * 
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public static String QTAES_Encrypt(String plainText) throws Exception {
		return bytesToHexString(aesEncryptToBytes(plainText, AES_KEY));
	}

	/**
	 * 解密方法
	 * 
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public static String QTAES_Decrypt(String plainText) throws Exception {
		return aesDecryptByBytes(hexStringToBytes(plainText), AES_KEY);
	}

	public static void main(String[] args) throws Exception {

		// String password = "Tirain86:)";
		// String salt = "YWUzZjFhNzgtNDg5NC00OWZiLTg4NDMtZjA1YzYyNjMzODhm";
		// String encrypt = md5hash(password, salt);
		// System.out.println(encrypt);

		String en = QTAES_Encrypt("1");
		System.out.println(en);

		String de = QTAES_Decrypt(en);
		System.out.println(de);
	}
}
