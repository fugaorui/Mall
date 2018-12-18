package com.corry.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JedisUtil {

  public abstract Map<Integer, JedisPool> getJedisPools();

  public abstract void setJedisPools(Map<Integer, JedisPool> jedisPools);

  public abstract Jedis getJedis(int dbIndex);

  /**
   * Set the string value as value of the key. The string can't be longer than 1073741824 bytes (1
   * GB).
   * <p>
   * Time complexity: O(1)
   * 
   * @param key
   * @param value
   * @return Status code reply
   */
  public abstract String set(int dbIndex, String key, String value);

  /**
   * Set the string value as value of the key. The string can't be longer than 1073741824 bytes (1
   * GB).
   * 
   * @param key
   * @param value
   * @param nxxx NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key
   *        if it already exist.
   * @param expx EX|PX, expire time units: EX = seconds; PX = milliseconds
   * @param time expire time in the units of {@param #expx}
   * @return Status code reply
   */
  public abstract String set(int dbIndex, String key, String value, String nxxx, String expx,
                             long time);

  /**
   * Get the value of the specified key. If the key does not exist null is returned. If the value
   * stored at key is not a string an error is returned because GET can only handle string values.
   * <p>
   * Time complexity: O(1)
   * 
   * @param key
   * @return Bulk reply
   */
  public abstract String get(int dbIndex, String key);

  /**
   * Test if the specified key exists. The command returns "1" if the key exists, otherwise "0" is
   * returned. Note that even keys set with an empty string as value will return "1".
   * 
   * Time complexity: O(1)
   * 
   * @param key
   * @return Boolean reply, true if the key exists, otherwise false
   */
  public abstract Boolean exists(int dbIndex, String key);

  /**
   * Remove the specified keys. If a given key does not exist no operation is performed for this
   * key. The command returns the number of keys removed.
   * 
   * Time complexity: O(1)
   * 
   * @param keys
   * @return Integer reply, specifically: an integer greater than 0 if one or more keys were removed
   *         0 if none of the specified key existed
   */
  public abstract Long del(int dbIndex, String... keys);

  public abstract Long del(int dbIndex, String key);

  /**
   * GETSET is an atomic set this value and return the old value command. Set key to the string
   * value and return the old value stored at key. The string can't be longer than 1073741824 bytes
   * (1 GB).
   * <p>
   * Time complexity: O(1)
   * 
   * @param key
   * @param value
   * @return Bulk reply
   */
  public abstract String getSet(int dbIndex, String key, String value);

  /**
   * Get the values of all the specified keys. If one or more keys dont exist or is not of type
   * String, a 'nil' value is returned instead of the value of the specified key, but the operation
   * never fails.
   * <p>
   * Time complexity: O(1) for every key
   * 
   * @param keys
   * @return Multi bulk reply
   */
  public abstract List<String> mget(int dbIndex, String... keys);

  /**
   * SETNX works exactly like {@link #set(String, String) SET} with the only difference that if the
   * key already exists no operation is performed. SETNX actually means "SET if Not eXists".
   * <p>
   * Time complexity: O(1)
   * 
   * @param key
   * @param value
   * @return Integer reply, specifically: 1 if the key was set 0 if the key was not set
   */
  public abstract Long setnx(int dbIndex, String key, String value);

  /**
   * The command is exactly equivalent to the following group of commands:
   * {@link #set(String, String) SET} + {@link #expire(String, int) EXPIRE}. The operation is
   * atomic.
   * <p>
   * Time complexity: O(1)
   * 
   * @param key
   * @param seconds
   * @param value
   * @return Status code reply
   */
  public abstract String setex(int dbIndex, String key, int seconds, String value);

  /**
   * Set the the respective keys to the respective values. MSET will replace old values with new
   * values, while {@link #msetnx(String...) MSETNX} will not perform any operation at all even if
   * just a single key already exists.
   * <p>
   * Because of this semantic MSETNX can be used in order to set different keys representing
   * different fields of an unique logic object in a way that ensures that either all the fields or
   * none at all are set.
   * <p>
   * Both MSET and MSETNX are atomic operations. This means that for instance if the keys A and B
   * are modified, another client talking to Redis can either see the changes to both A and B at
   * once, or no modification at all.
   * 
   * @see #msetnx(String...)
   * 
   * @param keysvalues
   * @return Status code reply Basically +OK as MSET can't fail
   */
  public abstract String mset(int dbIndex, String... keysvalues);

  /**
   * Set the the respective keys to the respective values. {@link #mset(String...) MSET} will
   * replace old values with new values, while MSETNX will not perform any operation at all even if
   * just a single key already exists.
   * <p>
   * Because of this semantic MSETNX can be used in order to set different keys representing
   * different fields of an unique logic object in a way that ensures that either all the fields or
   * none at all are set.
   * <p>
   * Both MSET and MSETNX are atomic operations. This means that for instance if the keys A and B
   * are modified, another client talking to Redis can either see the changes to both A and B at
   * once, or no modification at all.
   * 
   * @see #mset(String...)
   * 
   * @param keysvalues
   * @return Integer reply, specifically: 1 if the all the keys were set 0 if no key was set (at
   *         least one key already existed)
   */
  public abstract Long msetnx(int dbIndex, String... keysvalues);

  /**
   * 
   * Set the specified hash field to the specified value.
   * <p>
   * If key does not exist, a new key holding a hash is created.
   * <p>
   * <b>Time complexity:</b> O(1)
   * 
   * @param key
   * @param field
   * @param value
   * @return If the field already exists, and the HSET just produced an update of the value, 0 is
   *         returned, otherwise if a new field is created 1 is returned.
   */
  public abstract Long hset(int dbIndex, String key, String field, String value);

  public abstract Long hset(int dbIndex, String key, String field, String value, int expiredSecend);

  /**
   * If key holds a hash, retrieve the value associated to the specified field.
   * <p>
   * If the field is not found or the key does not exist, a special 'nil' value is returned.
   * <p>
   * <b>Time complexity:</b> O(1)
   * 
   * @param key
   * @param field
   * @return Bulk reply
   */
  public abstract String hget(int dbIndex, String key, String field);

  /**
   * 
   * Set the specified hash field to the specified value if the field not exists. <b>Time
   * complexity:</b> O(1)
   * 
   * @param key
   * @param field
   * @param value
   * @return If the field already exists, 0 is returned, otherwise if a new field is created 1 is
   *         returned.
   */
  public abstract Long hsetnx(int dbIndex, String key, String field, String value);

  /**
   * Set the respective fields to the respective values. HMSET replaces old values with new values.
   * <p>
   * If key does not exist, a new key holding a hash is created.
   * <p>
   * <b>Time complexity:</b> O(N) (with N being the number of fields)
   * 
   * @param key
   * @param hash
   * @return Return OK or Exception if hash is empty
   */
  public abstract String hmset(int dbIndex, String key, Map<String, String> hash);

  /**
   * Retrieve the values associated to the specified fields.
   * <p>
   * If some of the specified fields do not exist, nil values are returned. Non existing keys are
   * considered like empty hashes.
   * <p>
   * <b>Time complexity:</b> O(N) (with N being the number of fields)
   * 
   * @param key
   * @param fields
   * @return Multi Bulk Reply specifically a list of all the values associated with the specified
   *         fields, in the same order of the request.
   */
  public abstract List<String> hmget(int dbIndex, String key, String... fields);

  /**
   * Remove the specified field from an hash stored at key.
   * <p>
   * <b>Time complexity:</b> O(1)
   * 
   * @param key
   * @param fields
   * @return If the field was present in the hash it is deleted and 1 is returned, otherwise 0 is
   *         returned and no operation is performed.
   */
  public abstract Long hdel(int dbIndex, String key, String... fields);

  /**
   * Return all the fields and associated values in a hash.
   * <p>
   * <b>Time complexity:</b> O(N), where N is the total number of entries
   * 
   * @param key
   * @return All the fields and values contained into a hash.
   */
  public abstract Map<String, String> hgetAll(int dbIndex, String key);

  /**
   * 
   * @param dbIndex
   * @param key
   * @param expireSecond 过期时间
   * @return
   */
  public abstract Map<String, String> hgetAll(int dbIndex, String key, int expireSecond);

  public abstract void flusDb(int dbIndex);

  /**
   * 设置过期时间
   * 
   * @param dbIndex
   * @param key
   * @param seconds
   */
  public abstract void expire(int dbIndex, String key, int seconds);

  public abstract Long dbSize(int dbIndex);

  public abstract Set<String> keys(int dbIndex, String pattern);

}
