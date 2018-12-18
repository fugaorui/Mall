package com.corry.redis;

import com.corry.Serializers.KryoSerializer;
import com.corry.Serializers.Serializer;
import com.corry.base.util.Constants;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class RedisShiroCache<K, V> implements Cache<K, V> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String keyPrefix = Constants.SHIRO_REDIS_CACHE_KEY_PREFIX;// "shiro_redis_session:";

    private Serializer serializer;

    private JedisUtil jedisUtil;

    public RedisShiroCache(JedisUtil jedisUtil) {
        this.jedisUtil = jedisUtil;
        serializer = new KryoSerializer();
    }

    public RedisShiroCache(JedisUtil jedisUtil, String keyPrefix) {
        this.jedisUtil = jedisUtil;
        serializer = new KryoSerializer();
        this.keyPrefix = keyPrefix;
    }

    @Override
    public V get(K key) throws CacheException {
        // TODO Auto-generated method stub
        logger.debug("根据key从Redis中获取对象 key [" + key + "]");
        try {
            if (key == null) {
                return null;
            } else {
                String sKey = keyPrefix + serializer.obj2str(key);
                String sValue = jedisUtil.get(Constants.SHIRO_SESSION_DB, sKey);
                V value = (V) serializer.str2obj(sValue);
                return value;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    public boolean exists(K key) throws CacheException {
        logger.debug("根据key从Redis中判断对象 key [" + key + "]");
        Boolean isExists = null;
        try {
            if (key != null) {
                String sKey = keyPrefix + serializer.obj2str(key);
                isExists = jedisUtil.exists(Constants.SHIRO_SESSION_DB, sKey);
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
        return BooleanUtils.toBoolean(isExists);
    }

    @Override
    public V put(K key, V value) throws CacheException {
        // TODO Auto-generated method stub
        try {
            String sKey = keyPrefix + serializer.obj2str(key);
            String sValue = serializer.obj2str(value);
            jedisUtil.set(Constants.SHIRO_SESSION_DB, sKey, sValue);
            return value;
        } catch (Exception t) {
            // TODO Auto-generated catch block
            throw new CacheException(t);
        }

    }

    @Override
    public V remove(K key) throws CacheException {
        // TODO Auto-generated method stub

        try {
            V previous = get(key);
            String sKey = keyPrefix + serializer.obj2str(key);
            jedisUtil.del(Constants.SHIRO_SESSION_DB, sKey);
            return previous;
        } catch (Exception t) {
            // TODO Auto-generated catch block
            throw new CacheException(t);
        }

    }

    @Override
    public void clear() throws CacheException {
        // TODO Auto-generated method stub
        jedisUtil.flusDb(Constants.SHIRO_SESSION_DB);
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        Long dbSize = jedisUtil.dbSize(Constants.SHIRO_SESSION_DB);
        return dbSize.intValue();
    }

    @Override
    public Set<K> keys() {
        // TODO Auto-generated method stub
        try {
            Set<String> sKeys = jedisUtil.keys(Constants.SHIRO_SESSION_DB, keyPrefix + "*");
            Set<K> ks = new HashSet();
            for (String sKey : sKeys) {
                K k = (K) serializer.str2obj(StringUtils.removeStart(sKey, keyPrefix));
                ks.add(k);

            }
            return ks;
        } catch (Exception t) {
            // TODO Auto-generated catch block
            throw new CacheException(t);
        }
    }

    @Override
    public Collection<V> values() {
        // TODO Auto-generated method stub
        try {
            Set<String> sKeys = jedisUtil.keys(Constants.SHIRO_SESSION_DB, keyPrefix + "*");
            List<V> vList = new ArrayList();
            if (!CollectionUtils.isEmpty(sKeys)) {
                for (String sKey : sKeys) {
                    V v = (V) serializer.str2obj(jedisUtil.get(Constants.SHIRO_SESSION_DB, sKey));
                    vList.add(v);
                }
                return Collections.unmodifiableList(vList);
            } else {
                return Collections.emptyList();
            }
        } catch (Exception t) {
            // TODO Auto-generated catch block
            throw new CacheException(t);
        }

    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

}
