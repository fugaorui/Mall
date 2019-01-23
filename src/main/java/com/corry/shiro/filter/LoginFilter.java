package com.corry.shiro.filter;

import com.corry.admin.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * author:32642
 * Date:2018/12/19
 * Time:21:51
 */
public class LoginFilter extends AccessControlFilter {
    /**
     * 写如何能通过该拦截器的逻辑
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        //(1)获取认证信息
        User token = (User) SecurityUtils.getSubject().getPrincipal();
        //(2)登录请求直接放行
        if(null != token || isLoginRequest(request, response)){
            return true;
        }

        //(3)非登录请求,重定向到登录页面
       // ResultVo result = ResultVoUtil.failed("当前用户没有登录！");
       // WebUtils.jsonOut(response,result);

        return false;
    }

    /**
     * isAccessAllowed返回值为fales时经过该方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //保存Request和Response 到登录后的链接
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }
}
