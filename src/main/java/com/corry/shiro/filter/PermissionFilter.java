package com.corry.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * author:32642
 * Date:2018/12/19
 * Time:21:53
 */
public class PermissionFilter extends AccessControlFilter {

    /**
     * 写如何能通过该拦截器的逻辑
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        //(1)获取认证实体
        Subject subject = getSubject(request,response);
        //(2)进行权限校验
        //①获取请求url
        HttpServletRequest httpRequest=(HttpServletRequest)request;
        String url = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        if (url!=null && url.startsWith(contextPath)){
            url = url.replaceFirst(contextPath,"");
        }
        //②权限校验
        if (subject.isPermitted(url)){
            return true;
        }
        return false;
    }

    /**
     * isAccessAllowed返回值为fales时经过该方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //(3)未通过权限校验的逻辑
        Subject subject = getSubject(request, response);
        if (subject.getPrincipal()==null){
            //未登录重定向登录页面
            saveRequest(request);
            WebUtils.issueRedirect(request,response,((HttpServletRequest)request).getContextPath()+"/login");
        }else {
            WebUtils.issueRedirect(request,response,"/error");
            //已登录重定向未授权错误页面
            //WebUtil.jsonOut(response,Result .failed("用户未被授权该操作"));
        }
        return false;
    }
}
