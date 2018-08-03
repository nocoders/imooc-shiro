package com.imooc.util;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;


/**
 * @Author: linmeng
 * @Description:jedis访问工具，通过jedis连接池获取jedis连接
 * @Date: Create in 16:06 2018/8/1
 */
public class JedisUtil {
    @Autowired
    private JedisPool jedisPool;
//  获得redis连接的方法
    private Jedis getResource(){
        return jedisPool.getResource();
    }
//    redis的添加
    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis=getResource();
//        idea添加try catch
        try {
            jedis.set(key,value);
            return value;
        }finally {
            jedis.close();
        }
    }
//  设置指定的key的指定超时时间
    public void expire(byte[] key, int i) {
        Jedis jedis=getResource();
//        idea添加try catch
        try {
            jedis.expire(key,i);
        }finally {
            jedis.close();
        }
    }
//  根据key获取value
    public byte[] get(byte[] key) {
        Jedis jedis=getResource();
//        idea添加try catch
        try {
            return jedis.get(key);
        }finally {
            jedis.close();
        }
    }
//  删除session
    public void delete(byte[] key) {
        Jedis jedis = getResource();
        try {
            jedis.del(key);
        } finally {
            jedis.close();
        }
    }

    public Set<byte[]> getKeys(String shiro_session_prefix) {
        Jedis jedis = getResource();
        try {
            return jedis.keys((shiro_session_prefix+"*").getBytes());
        } finally {
            jedis.close();
        }
    }
}
