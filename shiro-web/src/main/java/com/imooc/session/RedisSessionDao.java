package com.imooc.session;

import com.imooc.util.JedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: linmeng
 * @Description:
 * @Date: Create in 16:04 2018/8/1
 */
public class RedisSessionDao extends AbstractSessionDAO {
    @Resource
    private JedisUtil jedisUtil;
//    指定session的key的前缀
    private final String SHIRO_SESSION_PREFIX="imooc-session";
    private byte[] getkey(String key){
        return (SHIRO_SESSION_PREFIX+key).getBytes();
    }
//    保存session的方法
    private void saveSession(Session session){
        if (session!=null && session.getId()!=null){
            byte[] key = getkey(session.getId().toString());
            byte[] value= SerializationUtils.serialize(session);
            jedisUtil.set(key,value);
            jedisUtil.expire(key,600);
        }
    }
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        saveSession(session);
        return sessionId;
    }
//  通过sessionId获得session
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId==null){
            return null;
        }
        byte[] key = getkey(sessionId.toString());
        byte[] value=jedisUtil.get(key);
        return (Session)SerializationUtils.deserialize(value);
    }
//  session 修改
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }
//  删除session
    public void delete(Session session) {
        if (session ==null || session.getId()==null){
            return;
        }
        byte[] key = getkey(session.getId().toString());
        jedisUtil.delete(key);
    }
//  或得到指定的存活的session
    public Collection<Session> getActiveSessions() {
        Set<byte[]>keys=jedisUtil.getKeys(SHIRO_SESSION_PREFIX);
        Set<Session>sessions=new HashSet<Session>();

        if (CollectionUtils.isEmpty(keys)){
            return sessions;
        }
        for (byte[]key:keys
             ) {
            Session session=(Session)SerializationUtils.deserialize(jedisUtil.get(key));
            sessions.add(session);

        }
        return sessions;
    }
}
