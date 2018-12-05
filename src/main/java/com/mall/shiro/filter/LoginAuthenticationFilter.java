package com.mall.shiro.filter;

import com.mall.base.util.SessionUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * author:32642
 * Date:2018/12/5
 * Time:17:30
 */
public class LoginAuthenticationFilter extends FormAuthenticationFilter {


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

        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {// 验证通过,跳转到主页去
            issueSuccess(request, response);
            return false;
        }
        boolean isLogin =  isLoginRequest(request, response);
        // 登陆地址未匹配成功的情况
        /*if (!(isLoginRequest(request, response))) {

            String reqUrl = WebUtils.getRequestUri(httpReq);
            reqUrl = RequestUtils.getFullUrl(httpReq, reqUrl);
            String message = "request url is '" + reqUrl + "' and the set login url is '" + loginUrl
                    + "', both are match false.";

            logger.error(message);

            issueLogin(request, response);// 跳转到系统指定的登录地址登录

            return false;
        }

        httpReq.setAttribute("username", this.getUsername(request));// 用户名
        // 验证码验证
        boolean validate = captchValidate(httpReq);
        if (!validate) {// 验证失败
            this.setFormFailureAttribute(httpReq, validate, false);
            return true;
        }

        // get请求登录
        if (!isLoginSubmission(request, response)) {// 页面请求参数
            // 清空登录失败次数
            // CaptchaUtils.removeLoginFailNum( WebUtils.toHttp(request) );
            return true;
        }


        // 判断账户锁定状态,如果锁定则返回登陆页面
        ResultDTO resultSysCache = systemCache.getCacheByCodeFromDB(Constants.LOGIN_MAX_FAIL_NUM);
        Map<String, String> valueMapChache=Maps.newHashMap();
        int loginMaxFailNum = Constants.LOGIN_MAX_FAIL_NUM_DEFAULT;
        if(resultSysCache.getState() != -111){
            valueMapChache = (Map)resultSysCache.get("valueMap");
            loginMaxFailNum = Integer.parseInt(valueMapChache.get("default"));
        }



        String loginNameRequest = httpReq.getParameter("username");
        boolean isOverMaxFail = userService.isUserLocked(loginNameRequest);
        if (isOverMaxFail){
            //验证码已经验证过，最大尝试登陆次数验证失败
            userService.updateUserLoginFailNum(loginNameRequest,false,loginMaxFailNum);
            this.setFormFailureAttribute(httpReq,true,true);
            return true;
        }


        // 验证用户名密码信息
        validate = super.onAccessDenied(request, response);// 这里成功返回false//失败返回true

        if (validate) {// 登录失败

            // 数据库登陆失败次数+1
            Long loginFailNum = userService.updateUserLoginFailNum(loginNameRequest,false,loginMaxFailNum);

            //TODO 其他系统密码锁定功能未上线，方法返回空,待处理
            if(loginFailNum == null){
                this.setFormFailureAttribute(httpReq);
            }else{
                if(loginFailNum >= loginMaxFailNum){
                    setFormFailureAttribute(httpReq, true, true);
                }else{
                    // get 请求已经在上面的isLoginSubmission通过，不会进入到此
                    this.setFormFailureAttribute(httpReq);
                }
            }




            return true;// 进入登录页面
        } else {// 登录成功。

            // 清空登录失败次数
            CaptchaUtils.removeLoginFailNum(WebUtils.toHttp(request));
            // 数据库登陆失败次数清零
            userService.updateUserLoginFailNum(loginNameRequest,true,loginMaxFailNum);
            // 设置登录session 用户信息
            String loginName = (String) SecurityUtils.getSubject().getPrincipal();
            IUser caUser = userService.findUserAtLogon(loginName);
            SessionUtil.setLoginUser(httpReq, caUser, null);

            // 更新用户登录信息
            String remoteAddr = RequestUtils.getRemoteAddr(httpReq);
            remoteAddr = StringUtils.isBlank(remoteAddr) ? httpReq.getRemoteHost() : remoteAddr;
            userService.updateUserLoginInfo(remoteAddr, caUser.getId());// 更新用户登录信息

            // 设置默认session
            SessionUtil.setDefaultSession(httpReq, caUser, characterCodingService, officeService);

            // 跳转到首页//这有之前client设置的相关的保存信息
            issueSuccess(request, response);// 原来的方法可以

            return false;// 不往下执行。
        }*/
        return true;
    }


    /**
     * 跳转到主页
     * @param request
     * @param response
     * @throws Exception
     */
    protected void issueSuccess(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
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
        issueSuccessRedirect(request, response);
    }
}
