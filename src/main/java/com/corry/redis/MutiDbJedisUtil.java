package com.corry.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;


/**
 * jedis单实例工具类 重写jedis部分常用类，主要增加dbIndex选择。更多操作可以直接使用jedis
 *
 * @author 咖啡煮萝卜 Aidy.Q
 *
 */
public class MutiDbJedisUtil implements JedisUtil {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Map<Integer, JedisPool> jedisPools;

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#getJedisPools()
     */
    @Override
    public Map<Integer, JedisPool> getJedisPools() {
        return jedisPools;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#setJedisPools(java.util.Map)
     */
    @Override
    public void setJedisPools(Map<Integer, JedisPool> jedisPools) {
        this.jedisPools = jedisPools;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#getJedis(int)
     */
    @Override
    public Jedis getJedis(int dbIndex) {
        return jedisPools.get(dbIndex).getResource();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#set(int, java.lang.String, java.lang.String)
     */
    @Override
    public String set(int dbIndex, final String key, String value) {

        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.set(key, value);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error(
                    "Jedis set error [key=" + key + ",value=" + value + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#set(int, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, long)
     */
    @Override
    public String set(int dbIndex, final String key, final String value, final String nxxx,
                      final String expx, final long time) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.set(key, value, nxxx, expx, time);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error(
                    "Jedis set error [key=" + key + ",value=" + value + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return null;

    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#get(int, java.lang.String)
     */
    @Override
    public String get(int dbIndex, final String key) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.get(key);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error("Jedis get error [key=" + key + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#exists(int, java.lang.String)
     */
    @Override
    public Boolean exists(int dbIndex, final String key) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.exists(key);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error("Jedis exists error [key=" + key + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#del(int, java.lang.String)
     */
    @Override
    public Long del(int dbIndex, final String... keys) {

        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.del(keys);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error("Jedis del error [keys=" + keys + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return 0L;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#del(int, java.lang.String)
     */
    @Override
    public Long del(int dbIndex, String key) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.del(key);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error("Jedis del error [key=" + key + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return 0L;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#getSet(int, java.lang.String, java.lang.String)
     */
    @Override
    public String getSet(int dbIndex, final String key, final String value) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.getSet(key, value);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error(
                    "Jedis getSet error [key=" + key + ",value" + value + "] on dbindex [" + dbIndex + "]",
                    e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#mget(int, java.lang.String)
     */
    @Override
    public List<String> mget(int dbIndex, final String... keys) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.mget(keys);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error("Jedis mget error [keys=" + keys + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#setnx(int, java.lang.String, java.lang.String)
     */
    @Override
    public Long setnx(int dbIndex, final String key, final String value) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.setnx(key, value);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error(
                    "Jedis setnx error [key=" + key + ",value" + value + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#setex(int, java.lang.String, int,
     * java.lang.String)
     */
    @Override
    public String setex(int dbIndex, final String key, final int seconds, final String value) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.setex(key, seconds, value);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error(
                    "Jedis setex error [key=" + key + ",value" + value + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#mset(int, java.lang.String)
     */
    @Override
    public String mset(int dbIndex, final String... keysvalues) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.mset(keysvalues);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error("Jedis mset error [keysvalues=" + keysvalues + "] on dbindex [" + dbIndex + "]",
                    e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#msetnx(int, java.lang.String)
     */
    @Override
    public Long msetnx(int dbIndex, final String... keysvalues) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.msetnx(keysvalues);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error(
                    "Jedis msetnx error [keysvalues=" + keysvalues + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#hset(int, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public Long hset(int dbIndex, final String key, final String field, final String value) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.hset(key, field, value);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error("Jedis hset error [key=" + key + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#hset(int, java.lang.String, java.lang.String,
     * java.lang.String, int)
     */
    @Override
    public Long hset(int dbIndex, final String key, final String field, final String value,
                     int expiredSecend) {
        Jedis jedis = getJedis(dbIndex);
        Long retValue = null;
        try {
            // jedis.select(dbIndex);

            retValue = jedis.hset(key, field, value);
            jedis.expire(key, expiredSecend);
            returnResource(jedis, dbIndex);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error("Jedis hset error [key=" + key + "] on dbindex [" + dbIndex + "]", e);
        }
        return retValue;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#hget(int, java.lang.String, java.lang.String)
     */
    @Override
    public String hget(int dbIndex, final String key, final String field) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.hget(key, field);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error("Jedis hget error [key=" + key + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#hsetnx(int, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public Long hsetnx(int dbIndex, final String key, final String field, final String value) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.hsetnx(key, field, value);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error("Jedis hsetnx error [key=" + key + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#hmset(int, java.lang.String, java.util.Map)
     */
    @Override
    public String hmset(int dbIndex, final String key, final Map<String, String> hash) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.hmset(key, hash);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error("Jedis hmset error [key=" + key + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#hmget(int, java.lang.String, java.lang.String)
     */
    @Override
    public List<String> hmget(int dbIndex, final String key, final String... fields) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.hmget(key, fields);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error("Jedis hmset error [key=" + key + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#hdel(int, java.lang.String, java.lang.String)
     */
    @Override
    public Long hdel(int dbIndex, final String key, final String... fields) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.hdel(key, fields);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error("Jedis hdel error [key=" + key + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#hgetAll(int, java.lang.String)
     */
    @Override
    public Map<String, String> hgetAll(int dbIndex, final String key) {
        Jedis jedis = getJedis(dbIndex);

        try {
            // jedis.select(dbIndex);
            return jedis.hgetAll(key);
        } catch (Exception e) {
            ;
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error("Jedis hgetAll error [key=" + key + "] on dbindex [" + dbIndex + "]", e);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#hgetAll(int, java.lang.String, int)
     */
    @Override
    public Map<String, String> hgetAll(int dbIndex, final String key, int expireSecond) {
        Jedis jedis = getJedis(dbIndex);
        Map<String, String> retValue = new HashMap();
        try {
            // jedis.select(dbIndex);
            retValue = jedis.hgetAll(key);
            jedis.expire(key, expireSecond);
            returnResource(jedis, dbIndex);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
            logger.error("Jedis hgetAll error [key=" + key + "] on dbindex [" + dbIndex + "]", e);
        }
        return retValue;
    }

    private void returnBrokenResource(Jedis jedis, int dbIndex) {
        try {
            jedisPools.get(dbIndex).returnBrokenResource(jedis);
        } catch (Exception e) {
            logger.error("returnBrokenResource error.", e);
        }
    }

    private void returnResource(Jedis jedis, int dbIndex) {
        try {
            if (jedis != null) {
                jedisPools.get(dbIndex).returnResource(jedis);
            }

        } catch (Exception e) {
            logger.error("returnResource error.", e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#flusDb(int)
     */
    @Override
    public void flusDb(int dbIndex) {
        Jedis jedis = getJedis(dbIndex);
        try {

            // jedis.select(dbIndex);
            jedis.flushDB();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
        } finally {
            returnResource(jedis, dbIndex);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#expire(int, java.lang.String, int)
     */
    @Override
    public void expire(int dbIndex, String key, int seconds) {
        if (seconds <= 0) {
            return;
        }

        Jedis jedis = getJedis(dbIndex);
        try {
            // jedis.select(dbIndex);
            jedis.expire(key, seconds);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
        } finally {
            returnResource(jedis, dbIndex);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#dbSize(int)
     */
    @Override
    public Long dbSize(int dbIndex) {
        Long dbSize = 0L;
        Jedis jedis = getJedis(dbIndex);
        try {
            // jedis.select(dbIndex);
            dbSize = jedis.dbSize();
            return dbSize;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
        } finally {
            returnResource(jedis, dbIndex);
        }
        return 0L;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cj.framework.common.nosql.JedisUtil1#keys(int, java.lang.String)
     */
    @Override
    public Set<String> keys(int dbIndex, final String pattern) {
        Jedis jedis = getJedis(dbIndex);
        Set<String> retValue = new HashSet();
        try {
            // jedis.select(dbIndex);
            retValue = jedis.keys(pattern);
            returnResource(jedis, dbIndex);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            returnBrokenResource(jedis, dbIndex);
        }
        return retValue;
    }

}
