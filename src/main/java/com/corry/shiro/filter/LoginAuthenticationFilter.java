package com.corry.shiro.filter;

import com.corry.admin.pojo.User;
import com.corry.admin.service.UserMapper;
import com.corry.base.util.BaseDto;
import com.corry.base.util.Constants;
import com.corry.base.util.Dto;
import com.corry.base.util.SessionUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * author:32642
 * Date:2018/12/5
 * Time:17:30
 */
public class LoginAuthenticationFilter extends FormAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String loginContextCode;
    Dto dto = new BaseDto();
    @Autowired
    private UserMapper userMapper;
    public String getLoginContextCode() {
        return loginContextCode;
    }

    private String failureKeyAttribute = Constants.LOGIN_FAILURE_KEY; // 验证失败后存储到的属性名

    public String getFailureKeyAttribute() {
        return failureKeyAttribute;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }

    private String successUrl = "/admin/index.jsp";

    public String getSuccessUrl() {
        return this.successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
        super.setSuccessUrl(successUrl);
    }
    public static final String DEFAULT_LOGIN_URL = Constants.DEFAULT_LOGIN_URL;
    private String loginUrl = DEFAULT_LOGIN_URL;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = SecurityUtils.getSubject();
        HttpServletRequest httpRequest = WebUtils.toHttp(request);

        boolean isAllowed = true;
        if (subject.isAuthenticated()) {
            if (SessionUtil.isLogin(httpRequest)) {
                isAllowed = false;// 进入onAccessDenied后跳转到主页
            } else {
                subject.logout();
                isAllowed = true;// 进入到登录页面
            }
        } else if (!isLoginSubmission(request, response) && isLoginRequest(request, response)) {
            isAllowed = true;// 登录请求，并且是get方式请求
        } else {
            isAllowed = super.isAccessAllowed(request, response, mappedValue);
        }
        return isAllowed;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //return super.onAccessDenied(request, response);
        HttpServletRequest httpReq = WebUtils.toHttp(request);
        HttpServletResponse httpRes = WebUtils.toHttp(response);
         HttpSession session =httpReq.getSession();
      /*  // 获取状态值
        Integer authState = (Integer) request.getAttribute(ShiroUtils.AUTHENTICATION_STATE_KEY);
        // 移除状态值
        request.removeAttribute(ShiroUtils.AUTHENTICATION_STATE_KEY);
        String url;
        if (authState != null && authState == Constants.NOT_SAME_LOCAL_SERVER_STATE) {
            url = ShiroUtils.getFullUrl(httpReq);
            url = ShiroUtils.getMatchLocalServerNameUrl(httpReq, this.getLoginServerName(), url);
            url = RequestUtils.addUrlParameter(httpReq, url);

            redirectToUrl(request, response, url);
            return false;
        }*/
        // 登陆地址未匹配成功的情况
        if (!(isLoginRequest(request, response))) {
            redirectToLogin(request, response);
            return false;
        }
        Subject subject = SecurityUtils.getSubject();
        /*if (subject.isAuthenticated()) {// 验证通过,跳转到主页去
            issueSuccess(request, response);
            return false;
        }*/
      //  boolean validate =  isLoginRequest(request, response);
        boolean validate = super.onAccessDenied(request, response);// 这里成功返回false//失败返回true
        if (validate) {// 这里成功返回false//失败返回true
            String string = (String) request.getAttribute("shiroLoginFailure");
            String string1 = (String) request.getAttribute("loginFailure");
            System.out.println("____________"+string);
            System.out.println("_____*___*____"+string1);
            session.setAttribute("loginFailure",string1);
            //this.setFormFailureAttribute(httpReq);
            //httpRes.sendRedirect("/admin/login.jsp");
            setFormFailureAttribute(httpReq, true, true);
            return true;// 进入登录页面
        } else {// 登录成功。

            issueSuccess(request, response);// 原来的方法可以

            return false;// 不往下执行。
        }
    }

    @Override
    protected boolean isLoginRequest(ServletRequest request, ServletResponse response) {

        return pathsMatch("/admin/login.do", request);
    }

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        String loginUrl = "/admin/login.jsp";
        WebUtils.issueRedirect(request, response, loginUrl);
    }


    /**
     * 跳转到主页
     * @param request
     * @param response
     * @throws Exception
     */
    protected void issueSuccess(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        HttpServletResponse httpResponse= WebUtils.toHttp(response);
        if (Constants.AJAX_RES_TYPE_JSON.equalsIgnoreCase(request.getParameter(Constants.AJAX_RES_TYPE))) {
           /* ResultDTO resultDTO = new ResultDTO();
            String loginUrl = getLoginUrl();
            // 获取完整路径
            loginUrl =ShiroUtils.getFullUrlByContextCode(httpRequest, this.getLoginContextCode(), loginUrl);

            resultDTO.put("loginUrl", loginUrl);
            String message = localeUtil.getLocalText("login.success", "登录成功", new Object[] {});

            // 输出json格式流
            ResultUtils.writeJSONString(response, ResultUtils.toMap(resultDTO, message));*/

        } else {
            //issueSuccessRedirect(request, response);
            String username = (String) SecurityUtils.getSubject().getPrincipal();
            User user = userMapper.findUserBylogin(username);
            httpRequest.getSession().setAttribute(Constants.SESSION_USER_KEY, user);
            try {
                WebUtils.issueRedirect(httpRequest, httpResponse, getSuccessUrl(), null, false,true);
            }catch (Exception e){

            }
            //WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
        }
       /* if (Constants.AJAX_RES_TYPE_JSON.equalsIgnoreCase(request.getParameter(Constants.AJAX_RES_TYPE))) {
            ResultDTO resultDTO = new ResultDTO();
            String loginUrl = getLoginUrl();
            // 获取完整路径
            loginUrl =ShiroUtils.getFullUrlByContextCode(httpRequest, this.getLoginContextCode(), loginUrl);

            resultDTO.put("loginUrl", loginUrl);
            String message = localeUtil.getLocalText("login.success", "登录成功", new Object[] {});

            // 输出json格式流
            ResultUtils.writeJSONString(response, ResultUtils.toMap(resultDTO, message));

        } else {

        }*/
        //issueSuccessRedirect(request, response);
        //WebUtils.redirectToSavedRequest(request, response, this.getSuccessUrl());
        // 设置登录session 用户信息

    }

    /***
     * 设置失败相关属性,验证码是成功验证过的。
     *
     * @param request
     */
    private void setFormFailureAttribute(HttpServletRequest request) {
        //验证码与最大尝试登陆次数验证均通过
        setFormFailureAttribute(request, true, false);
    }
    /**
     * 设置失败相关属性
     *
     * @param request
     * @param captchValidate
     */
    private void setFormFailureAttribute(HttpServletRequest request, boolean captchValidate, boolean isOverMaxFail) {
        // 验证码验证
            /*Integer loginFailNum = CaptchaUtils.setLoginFailNum(request);// 登录失败计数，并返回失败次数
            boolean needValidateCodeLogin = CaptchaUtils.isValidateCodeLogin(loginFailNum);// 判断是否需要验证码登录
            request.setAttribute(Constants.LOGIN_FAIL_NUM_KEY, loginFailNum);// 登录失败次数
            request.setAttribute(Constants.IS_VALIDATECODE_LOGIN, needValidateCodeLogin);// 是否需要验证码登录
            String backUrl = request.getParameter(Constants.BACK_URL);// 设置回退地址。
            request.setAttribute(Constants.BACK_URL, StringUtils.trimToEmpty(backUrl));*/
        if (!captchValidate) {// 验证码验证失败
            this.setFailureAttribute(request, new ConcurrentAccessException());

        }else if(isOverMaxFail){// 最大次数验证失败
            this.setFailureAttribute(request, new LockedAccountException());
        }
        return;
    }
}
