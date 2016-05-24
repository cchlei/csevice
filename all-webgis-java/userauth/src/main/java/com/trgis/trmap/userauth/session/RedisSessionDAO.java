package com.trgis.trmap.userauth.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

/**
 * 重写shiro sessionDAO 保证session共享
 * 
 * @author zhangqian
 *
 */
public class RedisSessionDAO extends AbstractSessionDAO {

	private final String REDIS_SHIRO_SESSION_KEY = "shiro-session:";

	private RedisManager redisManager;

	public RedisSessionDAO() {
		
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		this.saveSession(session);
	}

	private void saveSession(Session session) {
		if (session == null || session.getId() == null) {
			return;
		}
		byte[] key = getByteKey(session.getId());
		byte[] value = SerializeUtils.serialize(session);
		this.redisManager.set(key, value, redisManager.getExpire());
	}

	/**
	 * 获得byte[]型的key
	 * 
	 * @param key
	 * @return
	 */
	private byte[] getByteKey(Serializable sessionId) {
		String preKey = this.REDIS_SHIRO_SESSION_KEY + sessionId;
		return preKey.getBytes();
	}

	@Override
	public void delete(Session session) {
		if (session == null || session.getId() == null) {
			return;
		}
		this.redisManager.del(this.getByteKey(session.getId()));
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<Session> sessions = new HashSet<Session>();
		Set<byte[]> keys = redisManager.keys(this.REDIS_SHIRO_SESSION_KEY+"*");
		if (keys != null && keys.size() > 0) {
			for (byte[] key : keys) {
				Session s;
				try {
					s = (Session) SerializeUtils.deserialize(redisManager.get(key));
					if(s != null){
						sessions.add(s);
					}
				} catch (Exception e) {
					// ignore exception deserialize
					continue;
				}
			}
		}
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		this.saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if (sessionId == null) {
			return null;
		}
		Session s;
		try {
			s = (Session) SerializeUtils.deserialize(redisManager.get(this.getByteKey(sessionId)));
			return s;
		} catch (Exception e) {
			throw new RuntimeException("Session deserialize failed.",e);
		}
	}

	public RedisManager getRedisManager() {
		return redisManager;
	}

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
		this.redisManager.init();
	}

}
