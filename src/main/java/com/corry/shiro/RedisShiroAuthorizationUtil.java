package com.corry.shiro;

import com.corry.base.util.Constants;
import com.corry.redis.RedisShiroCache;
import com.corry.redis.RedisShiroCacheManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/***
 * 跟新权限信息util
 * 
 * @author lxw
 *
 */

public class RedisShiroAuthorizationUtil implements Serializable {

  private static final long serialVersionUID = 2554713189405270832L;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  private RedisShiroCacheManager redisShiroCacheManager;

  public void setRedisShiroCacheManager(RedisShiroCacheManager redisShiroCacheManager) {
    this.redisShiroCacheManager = redisShiroCacheManager;
  }

  public RedisShiroAuthorizationUtil() {}

  public boolean isPermitted(String loginName, String permission) {
    AuthorizationInfo info = getAuthorization(loginName);
    return isPermitted(info, permission);
  }

  public boolean isPermitted(AuthorizationInfo authorizationInfo, String permission) {
    if (authorizationInfo == null || StringUtils.isBlank(permission)) {
      return false;
    }
    permission = StringUtils.trim(permission);
    return authorizationInfo.getStringPermissions().contains(permission);
  }

  public AuthorizationInfo getAuthorization(String loginName) {
    AuthorizationInfo info = null;
    if (StringUtils.isNotBlank(loginName)) {
      PrincipalCollection principals = this.getPrincipalCollection(loginName);
      info = getAuthorization(principals);
    }
    return info;
  }


  public AuthorizationInfo getAuthorization(PrincipalCollection principals) {
    AuthorizationInfo info = null;
    if (principals == null) {
      return info;
    }
    Cache<Object, AuthorizationInfo> cache =
        redisShiroCacheManager.getCache(Constants.USER_REALM_AUTHORIZATION_CACHENAME);
    info = cache.get(principals);

    return info;
  }

  public boolean exists(String loginName) throws Exception {
    // RedisShiroCache<Object, AuthorizationInfo>
    // cache=(RedisShiroCache)redisShiroCacheManager.getCache(
    // Constants.USER_REALM_AUTHORIZATION_CACHENAME );
    PrincipalCollection principals = getPrincipalCollection(loginName);
    return exists(principals);
  }

  public boolean exists(PrincipalCollection principals) throws Exception {
    RedisShiroCache<Object, AuthorizationInfo> cache = (RedisShiroCache) redisShiroCacheManager
        .getCache(Constants.USER_REALM_AUTHORIZATION_CACHENAME);
    return cache.exists(principals);
  }

  /***
   * 根据用户名更新
   * 
   * @param loginName
   * @param info
   * @return
   */
  public AuthorizationInfo updateAuthorization(String loginName, AuthorizationInfo info)
      throws Exception {
    if (StringUtils.isBlank(loginName)) {
      logger.info("loginName is null");
      return info;
    }
    PrincipalCollection principals = getPrincipalCollection(loginName);
    return updateAuthorization(principals, info);
  }


  /***
   * jms调用更新AuthorizationInfo信息
   * 
   * @param principals
   * @param info
   * @return
   * @throws Exception
   */
  public AuthorizationInfo updateAuthorization(PrincipalCollection principals,
                                               AuthorizationInfo info) throws Exception {
    if (principals == null) {
      logger.info("updateAuthorization principals is null");
      return info;
    }

    RedisShiroCache<Object, AuthorizationInfo> cache = (RedisShiroCache) redisShiroCacheManager
        .getCache(Constants.USER_REALM_AUTHORIZATION_CACHENAME);
    cache.put(principals, info);

    return info;
  }



  /***
   * 删除缓存信息
   * 
   * @param loginName
   * @return
   * @throws Exception
   */
  public AuthorizationInfo cleanAuthorization(String loginName) throws Exception {
    if (StringUtils.isBlank(loginName)) {
      logger.info("loginName is null");
      return null;
    }
    PrincipalCollection principals = getPrincipalCollection(loginName);
    return cleanAuthorization(principals);
  }


  /***
   * 删除缓存信息
   * 
   * @param principals
   * @return
   * @throws Exception
   */
  public AuthorizationInfo cleanAuthorization(PrincipalCollection principals) throws Exception {
    if (principals == null) {
      return null;
    }
    RedisShiroCache<Object, AuthorizationInfo> cache = (RedisShiroCache) redisShiroCacheManager
        .getCache(Constants.USER_REALM_AUTHORIZATION_CACHENAME);
    return cache.remove(principals);
  }


  public PrincipalCollection getPrincipalCollection(String loginName) {
    if (StringUtils.isBlank(loginName)) {
      return null;
    }
    PrincipalCollection principals =
        new SimplePrincipalCollection(loginName, Constants.USER_REALM_NAME);
    return principals;
  }
}
