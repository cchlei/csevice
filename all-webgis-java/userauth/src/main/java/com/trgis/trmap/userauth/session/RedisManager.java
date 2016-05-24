package com.trgis.trmap.userauth.session;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManager {

	private String host = "117.34.70.41";
	
	private int port = 6379;

	private int expire = 1800;

	private int timeout = 180000;

	private String password = "jOvvcTsNG4JAJjCXcCxyEH8lEBy7ZpM8";

	private JedisPool jedisPool = null;

	public RedisManager() {
	}

	/**
	 * 初始化
	 */
	public void init() {
		if (jedisPool == null) {
			jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
		}
	}

	public byte[] get(byte[] key) {
		byte[] value = null;
		Jedis jedis = jedisPool.getResource();
		try {
			value = jedis.get(key);
		} finally {
			jedis.close();
		}
		return value;
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public byte[] set(byte[] key, byte[] value) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, value);
			if (this.expire != 0) {
				jedis.expire(key, this.expire);
			}
		} finally {
			jedis.close();
		}
		return value;
	}

	public byte[] set(byte[] key, byte[] value, int expire) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, value);
			if (expire != 0) {
				jedis.expire(key, expire);
			}
		} finally {
			jedis.close();
		}
		return value;
	}

	public void del(byte[] key) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(key);
		} finally {
			jedis.close();
		}
	}

	public Set<byte[]> keys(String pattern) {
		Set<byte[]> keys = null;
		Jedis jedis = jedisPool.getResource();
		try {
			keys = jedis.keys(pattern.getBytes());
		} finally {
			jedis.close();
		}
		return keys;
	}

	public int getExpire() {
		return this.expire;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}
	
	

}
