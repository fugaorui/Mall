package com.mall.base.token;

import com.mall.base.util.Constants;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component("tokenValidInterceptor")
public class TokenValidInterceptor implements HandlerInterceptor {

	//@Resource(name = "sysLocaleUtil")
	//protected LocaleUtil sysLocaleUtil;

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
                                Exception exception) throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
		TokenValid tokenValid = getTokenValid(handler);
		if (tokenValid != null) {
			String tokenName=request.getParameter(Constants.TOKEN_NAME);
			if(tokenName==null ||tokenName ==""){
				tokenName =Constants.TOKEN;
			}
			TokenUtils.processReturnObject(tokenName,request, modelAndView != null ? modelAndView.getModel() : null);
		}
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		TokenValid tokenValid = getTokenValid(handler);
		if (tokenValid != null) {
			String tokenName=request.getParameter(Constants.TOKEN_NAME);
			//tokenName=StringUtils.defaultIfBlank( tokenName , Constants.TOKEN );
			String url = tokenValid.url();
			return TokenUtils.processValid(tokenName,request, response, url);
		}
		return true;
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
