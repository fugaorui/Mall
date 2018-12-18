package com.corry.base.interceptor;

import com.corry.base.token.TokenUtils;
import com.corry.base.token.TokenValid;
import com.corry.base.util.AssistTool;
import com.corry.base.util.Constants;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/** 
* @author 作者:Administrator
* @version 创建时间：2017年10月19日 下午6:41:16 
* 类说明 
*/
public class LoginInterceptor implements HandlerInterceptor {


	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

		TokenValid tokenValid = getTokenValid(o);
		if (tokenValid != null) {
			String tokenName=request.getParameter(Constants.TOKEN_NAME);
			//tokenName=StringUtils.defaultIfBlank( tokenName , Constants.TOKEN );
			String url = tokenValid.url();
			return TokenUtils.processValid(tokenName,request, response, url);
		}
		String url=request.getRequestURI();
		if(url.indexOf("/admin/login.do")>=0){
			return true;
		}
		//app跳转
		if(url.indexOf("/app")>=0){
			return true;
		}
		if(url.indexOf("/conn")>=0){
			return true;
		}
		if(url.indexOf("/admin/loginAgain.do")>=0){

			return true;
		}
		//判断session
		HttpSession session=request.getSession();

		//从session中取出用户份信息
		String sessionUser=(String)session.getAttribute("uname");
		if(AssistTool.isEmpty(sessionUser)){
        	/*HttpSessionID httpSessionID = httpSessionIDMapper.selectByUid(sessionUser);
        	if(AssistTool.isEmpty(httpSessionID)){//首次登陆
        		httpSessionID = new HttpSessionID();
        		httpSessionID.setSessionid(session.getId());
        		httpSessionID.setUname(sessionUser);
        	}else{//二次登录
        		String temp = httpSessionID.getSessionid()+"";
        		if(!session.getId().equals(temp)){
        			request.setAttribute("username", httpSessionID.getUame());
        			request.setAttribute("errorInfo", "此账号已登录");
        			// response.sendRedirect("/index.jsp");
        			request.getRequestDispatcher("/index.jsp").forward(request, response);
        			return false;
        		}
        	}*/
			response.sendRedirect("/index.jsp");
			return false;
		}else{
			//身份存在，放行
			return true;
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
		TokenValid tokenValid = getTokenValid(o);
		if (tokenValid != null) {
			String tokenName=request.getParameter(Constants.TOKEN_NAME);
			if(tokenName==null ||tokenName ==""){
				tokenName =Constants.TOKEN;
			}
			TokenUtils.processReturnObject(tokenName,request, modelAndView != null ? modelAndView.getModel() : null);
		}
	}

	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}

	private TokenValid getTokenValid(Object handler) {
		TokenValid tokenValid = null;
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();

			tokenValid = method.getAnnotation(TokenValid.class);
		}
		return tokenValid;
	}
}
