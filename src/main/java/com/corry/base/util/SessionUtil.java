package com.corry.base.util;

import com.corry.admin.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * author:32642
 * Date:2018/12/5
 * Time:17:36
 */
public class SessionUtil {

    protected static Logger logger = LoggerFactory.getLogger(SessionUtil.class);

    /***
     * 在session中设定key=object
     *
     * @param request
     * @param key
     * @param object
     * @return
     */
    public final static Object set(HttpServletRequest request, String key, Object object) {
        request.getSession().setAttribute(key, object);
        return object;
    }


    /***
     * 从session中获取指定的key值
     *
     * @param request
     * @param key
     * @return
     */
    public final static Object get(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }


    /***
     * 从session中移除指定的key信息
     *
     * @param request
     * @param key
     */
    public final static void remove(HttpServletRequest request, String key) {
        request.getSession().removeAttribute(key);
    }

    /***
     * 判断是否登录
     *
     * @param request
     * @return
     */
    public final static boolean isLogin(HttpServletRequest request) {
        User caUser = getSessionUser(request);
        if (caUser != null) {
            return true;
        }
        return false;
    }

    public final static User getSessionUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
        return user;
    }
}
