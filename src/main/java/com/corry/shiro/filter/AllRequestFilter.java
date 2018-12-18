package com.corry.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AllRequestFilter extends AuthenticationFilter {

    Logger log = LoggerFactory.getLogger(getClass());
    private String loginUrl;
    private String unauthorizedUrl;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        System.out.println("---------");

        return false;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request,response);
        subject.isPermitted();
        boolean isAllowed = true;
        /*if(!subject.isAuthenticated()){//判断已经认证
            isAllowed = false;
        }*/
        System.out.println("***************:"+isAllowed);
        return isAllowed;
    }

}
