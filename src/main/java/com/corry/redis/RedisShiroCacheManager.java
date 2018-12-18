package com.corry.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RedisShiroCacheManager implements CacheManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
    private JedisUtil jedisUtil;

    public void setJedisUtil(JedisUtil jedisUtil) {
        this.jedisUtil = jedisUtil;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        // TODO Auto-generated method stub

        logger.debug("获取名称为: " + name + " 的RedisCache实例");

        Cache c = caches.get(name);

        if (c == null) {

            c = new RedisShiroCache<K, V>(jedisUtil);


            // add it to the cache collection
            caches.put(name, c);
        }
        return c;
    }

}
