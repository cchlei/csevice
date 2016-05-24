package com.trgis.trmap.userauth.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

/**
 * JTW token
 * 
 * 生成和解析的工具
 * 
 * @author zhangqian
 *
 */
public class JsonWebTokenService {

	private static final Logger logger = LoggerFactory.getLogger(JsonWebTokenService.class);

	// 一周有效
	private static final long ONE_WEEK = 60 * 1000 * 60 * 7 * 24;

	// 域名
	private static final String TRMAP_DOMAIN = "http://www.trmap.cn";

	// 给用户签名获取jwt钥匙串
	public static String signed(String username) throws NoSuchAlgorithmException, JOSEException {
		if (StringUtils.isEmpty(username)) {
			return "";
		}
		// RSA signatures require a public and private RSA key pair, the public
		// key
		// must be made known to the JWS recipient in order to verify the
		// signatures
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
		keyGenerator.initialize(1024);

		KeyPair kp = keyGenerator.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();

		// Create RSA-signer with the private key
		JWSSigner signer = new RSASSASigner(privateKey);

		// Prepare JWT with claims set
		JWTClaimsSet claimsSet = new JWTClaimsSet();
		claimsSet.setSubject(username);
		claimsSet.setIssuer(TRMAP_DOMAIN);
		claimsSet.setExpirationTime(new Date(new Date().getTime() + ONE_WEEK));

		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);

		// Compute the RSA signature
		signedJWT.sign(signer);

		String s = signedJWT.serialize();
		return s;
	}

	/**
	 * 获取用户名
	 * 
	 * @param s
	 * @return
	 */
	public static String getUsername(String s) {
		String username = "";
		try {
			SignedJWT jwt = parseJWT(s);
			if (!checkDomainAndTime(jwt)) {
				logger.debug("[JWT]失效");
				throw new ParseException("[JWT]失效", 0);
			}
			username = jwt.getJWTClaimsSet().getSubject();
		} catch (ParseException e) {
			logger.debug("[JWT]出错", e);
		}
		return username;
	}

	/**
	 * 检查域名和时间是否有效
	 * 
	 * @param jwt
	 * @return
	 * @throws ParseException
	 */
	public static boolean checkDomainAndTime(SignedJWT jwt) throws ParseException {
		logger.debug("开始检测JWT有效性");
		return doCheckDomainAndTime(jwt);
	}

	/*
	 * 处理检查
	 */
	private static boolean doCheckDomainAndTime(SignedJWT jwt) throws ParseException {
		logger.debug("处理检查");
		return docheckDomain(jwt) && docheckTime(jwt);
	}

	/*
	 * 检验域名是否正常
	 */
	private static boolean docheckDomain(SignedJWT jwt) throws ParseException {
		logger.debug("校验域名是否可用");
		return StringUtils.equals(TRMAP_DOMAIN, jwt.getJWTClaimsSet().getIssuer());
	}

	/*
	 * 检验时间是否过期
	 */
	private static boolean docheckTime(SignedJWT jwt) throws ParseException {
		logger.debug("校验时间是否过期");
		return new Date().before(jwt.getJWTClaimsSet().getExpirationTime());
	}

	/*
	 * 转化jwt格式为对象
	 */
	private static SignedJWT parseJWT(String s) throws ParseException {
		SignedJWT parseJWT = SignedJWT.parse(s);
		return parseJWT;
	}

}
