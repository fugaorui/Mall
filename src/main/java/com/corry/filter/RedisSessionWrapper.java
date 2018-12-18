package com.corry.filter;

import com.corry.Serializers.KryoSerializer;
import com.corry.Serializers.Serializer;
import com.corry.base.util.Constants;
import com.corry.base.util.ContextUtil;
import com.corry.redis.JedisUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RedisSessionWrapper extends HttpSessionWrapper {

  JedisUtil jedisUtil;

  Serializer serializer;

  private String jsessionid = "";

  private Map<String, String> map = null;

  public RedisSessionWrapper(String jsessionid, HttpSession session) {
    super(session);
    // TODO Auto-generated constructor stub
    jedisUtil = (JedisUtil) ContextUtil.getSpringBeanByName(this.getServletContext(), "jedisUtil");
    serializer = new KryoSerializer();
    this.jsessionid = jsessionid;
    this.map = jedisUtil.hgetAll(Constants.SESSION_DB, jsessionid);
    jedisUtil.expire(Constants.SESSION_DB, jsessionid, Constants.SHIRO_SESSION_EXPIRE);
  }

  @Override
  public Object getAttribute(String name) {
    // TODO Auto-generated method stub
    try {
      if (StringUtils.isNotBlank(map.get(name))) {
        // System.out.println(name + "===========" + map.get(name));
        return serializer.str2obj(map.get(name));
      }
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Enumeration getAttributeNames() {
    // TODO Auto-generated method stub

    Set<String> keyNames = map.keySet();
    final Iterator iterator = keyNames.iterator();
    return new Enumeration() {
      public boolean hasMoreElements() {
        return iterator.hasNext();
      }

      public Object nextElement() {
        return iterator.next();
      }
    };

  }

  @Override
  public void setAttribute(String name, Object value) {
    // TODO Auto-generated method stub
    String sValue;
    try {
      if (value != null) {
        sValue = serializer.obj2str(value);
        this.map.put(name, sValue);
        jedisUtil.hset(Constants.SESSION_DB, jsessionid, name, sValue);
        jedisUtil.expire(Constants.SESSION_DB, jsessionid, Constants.SHIRO_SESSION_EXPIRE);
      }

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // super.setAttribute(name, value);
  }

  @Override
  public void invalidate() {
    // TODO Auto-generated method stub
    this.map.clear();
    jedisUtil.del(Constants.SESSION_DB, jsessionid);
    // super.invalidate();
  }

  @Override
  public void removeAttribute(String name) {
    // TODO Auto-generated method stub
    this.map.remove(name);
    jedisUtil.hdel(Constants.SESSION_DB, jsessionid, name);
  }

}
