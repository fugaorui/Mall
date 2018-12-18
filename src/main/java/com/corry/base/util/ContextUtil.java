package com.corry.base.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// import com.joymain.jecs.Constants;

/**
 * 保存Spring Context，以便在非web环境获得Spring的依赖注入服务
 * 
 *
 * <p>
 * Title: is a Class
 * </p>
 *
 * <p>
 * Description: 类
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 *
 * <p>
 * Company: gt
 * </p>
 *
 * @author Aidy.Q
 * @version 1.0
 */

@Component
@Lazy(false)
public class ContextUtil
    implements ServletContextListener, ApplicationContextAware, DisposableBean {
  private static Log log = LogFactory.getLog(ContextUtil.class);
  private static final ThreadLocal loginSession = new ThreadLocal();
  private static ApplicationContext applicationContext = null;
  private static ServletContext servletContext = null;

  public ContextUtil() {}

  /**
   * Bind the given resource for the given key to the current thread.
   * 
   * @param key key to bind the value to
   * @param value value to bind
   * @throws IllegalStateException if there is already a value bound to the thread
   */
  public static void bindResource(Object key, Object value) throws IllegalStateException {
    Map map = (Map) loginSession.get();
    // set ThreadLocal Map if none found
    if (map == null) {
      map = new HashMap();
      loginSession.set(map);
    }
    // if (map.containsKey(key)) {
    // throw new IllegalStateException("Already value [" + map.get(key) + "] for key [" + key +
    // "] bound to thread [" + Thread.currentThread().getName() + "]");
    // }
    map.put(key, value);
    // if (log.isDebugEnabled()) {
    // log.debug("Bound value [" + value + "] for key [" + key + "] to thread [" +
    // Thread.currentThread().getName() + "]");
    // }
  }

  /**
   * Return all resources that are bound to the current thread.
   * <p>
   * Mainly for debugging purposes. Resource managers should always invoke hasResource for a
   * specific resource key that they are interested in.
   * 
   * @return Map with resource keys and resource objects, or empty Map if currently none bound
   * @see #hasResource
   */
  public static Map getResourceMap() {
    Map map = (Map) loginSession.get();
    if (map == null) {
      map = new HashMap();
    }
    return Collections.unmodifiableMap(map);
  }

  /**
   * Retrieve a resource for the given key that is bound to the current thread.
   * 
   * @param key key to check
   * @return a value bound to the current thread, or <code>null</code> if none
   */
  public static Object getResource(Object key) {
    Map map = (Map) loginSession.get();
    if (map == null) {
      return null;
    }
    Object value = map.get(key);
    // if (value != null && log.isDebugEnabled()) {
    // log.debug("Retrieved value [" + value + "] for key [" + key + "] bound to thread [" +
    // Thread.currentThread().getName() + "]");
    // }
    return value;
  }


  public static ApplicationContext getApplicationContext() {
    assertApplicationContext();
    return applicationContext;
  }


  public static ServletContext getServletContext() {
    assertServletContext();
    return servletContext;
  }

  public static void setServletContext(ServletContext servletContext) {
    if (ContextUtil.servletContext != null) {
      log.info("ContextUtil中的servletContext被覆盖, 原有servletContext为:" + ContextUtil.servletContext);
    }
    ContextUtil.servletContext = servletContext;
  }

  /**
   * added by Aidy.Q
   * 
   * @param context
   * @param name
   * @return
   */
  public static Object getSpringBeanByName(ServletContext context, String name) {
    ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
    Object object = ctx.getBean(name);
    return object;

  }


  /***
   * web端获取spring的的实体bean
   * 
   * @param name
   * @return
   */
  @SuppressWarnings("unchecked")
  public static <T> T getBean(String name) {
    assertApplicationContext();
    return (T) applicationContext.getBean(name);
  }

  /*
   * 通过 RequestUtils.getHttpServletRequest() 获取request调用获取bean
   *
   * public static <T> T getBeanByRequest(String name) { HttpServletRequest
   * request=RequestUtils.getHttpServletRequest(); Assert.notNull(request,
   * "local thread request must not be null "); ApplicationContext
   * applicationContext=WebApplicationContextUtils.getRequiredWebApplicationContext(
   * request.getServletContext() ); return (T) applicationContext.getBean(name); }
   */

  /***
   * web端获取spring的的实体bean
   * 
   * @param servletContext
   * @param name
   * @return
   */
  @SuppressWarnings("unchecked")
  public static <T> T getBean(ServletRequest request, String name) {
    Assert.notNull(request, "request must not be null ");
    Assert.notNull(name, "name must not be null ");
    ApplicationContext applicationContext =
        WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
    return (T) applicationContext.getBean(name);
  }

  /***
   * web端获取spring的的实体bean
   * 
   * @param servletContext
   * @param name
   * @return
   */
  @SuppressWarnings("unchecked")
  public static <T> T getBean(ServletContext servletContext, String name) {
    Assert.notNull(servletContext, "servletContext must not be null ");
    Assert.notNull(name, "name must not be null ");
    ApplicationContext applicationContext =
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    return (T) applicationContext.getBean(name);
  }

  /****
   * web端获取spring的实体bean
   * 
   * @param requiredType
   * @return
   */
  public static <T> T getBean(Class<T> requiredType) {
    assertApplicationContext();
    return (T) applicationContext.getBean(requiredType);
  }

  /*
   * 通过 RequestUtils.getHttpServletRequest() 获取request调用获取bean public static <T> T
   * getBeanByRequest(Class<T> requiredType) { HttpServletRequest
   * request=RequestUtils.getHttpServletRequest(); Assert.notNull(request,
   * "local thread request must not be null "); return
   * getBean(request.getServletContext(),requiredType); }
   */

  /***
   * web端获取spring的实体bean
   * 
   * @param servletContext
   * @param requiredType
   * @return
   */
  public static <T> T getBean(ServletContext servletContext, Class<T> requiredType) {
    Assert.notNull(servletContext, "servletContext must not be null ");
    ApplicationContext applicationContext =
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    return applicationContext.getBean(requiredType);
  }

  /***
   * web端获取spring的的实体bean
   * 
   * @param servletContext
   * @param name
   * @return
   */
  @SuppressWarnings("unchecked")
  public static <T> T getBean(ApplicationContext applicationContext, String name) {
    Assert.notNull(applicationContext, "applicationContext must not be null ");
    Assert.notNull(name, "name must not be null ");
    return (T) applicationContext.getBean(name);
  }

  /****
   * 实现监听器实现的方法contextInitialized
   */
  public void contextInitialized(ServletContextEvent event) {
    ServletContext context = event.getServletContext();
    setServletContext(context);
    ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
    setApplicationContext(ctx);
    log.info("set context....");
  }

  /****
   * 实现监听器实现的方法contextDestroyed
   */
  public void contextDestroyed(ServletContextEvent sce) {
    ContextUtil.clear();
  }


  /***
   * 实现spring方法注入方法setApplicationContext 监听器注入调用方法
   */
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    if (ContextUtil.applicationContext != null) {
      log.info("ContextUtil中的ApplicationContext被覆盖, 原有ApplicationContext为:"
          + ContextUtil.applicationContext);
    }
    ContextUtil.applicationContext = applicationContext; // NOSONAR
  }

  /***
   * 实现spring方法注入方法destroy
   */
  public void destroy() throws Exception {
    ContextUtil.clear();
  }



  public static void clear() {
    if (log.isDebugEnabled()) {
      log.debug("清除ContextUtil中的ApplicationContext:" + applicationContext);
      log.debug("清除ContextUtil中的servletContext:" + servletContext);
    }
    applicationContext = null;
    servletContext = null;
  }

  /**
   * 检查ApplicationContext不为空.
   */
  private static void assertApplicationContext() {
    Assert.notNull(applicationContext,
        "applicaitonContext属性未设置值, 请将ContextUtil注册为spring bean 或在web.xml的spring监听器后设置ContextUtil为监听器.");
  }

  /**
   * 检查servletContext不为空.
   */
  private static void assertServletContext() {
    Assert.notNull(servletContext, "servletContext属性未设置值, 请在web.xml的spring监听器后设置ContextUtil为监听器。");
  }
}
