package com.corry.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * author:32642
 * Date:2018/12/19
 * Time:21:52
 */
public class RoleFilter extends AccessControlFilter {
    /**
     * 写如何能通过该拦截器的逻辑
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object value) throws Exception {
        //(1)获取认证实体
        Subject subject = getSubject(request, response);
        //(2)进行角色校验（角色为"1"能通过）
        if (subject.hasAllRoles(Arrays.asList("1"))){
            return true;
        }
        //(3)未通过角色校验重定向回登录页面
        ((HttpServletResponse)response).sendRedirect(request.getServletContext().getContextPath()+"/login");
        return false;
    }

    /**
     * isAccessAllowed返回值为fales时经过该方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //(3)未通过角色校验重定向回登录页面
        //((HttpServletResponse)response).sendRedirect(request.getServletContext().getContextPath()+"/login");
        Subject subject = getSubject(request, response);
        if (subject.getPrincipal()==null){
            //未登录重定向登录页面
            saveRequest(request);
            WebUtils.issueRedirect(request,response,((HttpServletRequest)request).getContextPath()+"/login");
        }else {
            //已登录重定向未授权错误页面
            WebUtils.issueRedirect(request,response,((HttpServletRequest)request).getContextPath()+"/error/unauthorizerror");
        }
        return false;
    }
}
