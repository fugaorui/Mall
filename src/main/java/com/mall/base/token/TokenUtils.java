package com.mall.base.token;

import com.alibaba.fastjson.JSONObject;
import com.mall.base.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils implements Serializable {

	private static final long serialVersionUID = -4340871985117871349L;

	protected static Logger logger = LoggerFactory.getLogger(TokenUtils.class);

	public static String generateTokenValue(String tokenName, HttpServletRequest request) {
		HttpSession session = request.getSession();

		String encodeRequestUrl = getEncodeFilterRequestUrl(request);
		String sessionTokenKey = getSessionTokenKey(tokenName,encodeRequestUrl);

		String tokenValue = IdGen.uuid() + ";" + encodeRequestUrl;

		session.setAttribute(sessionTokenKey, tokenValue);
		request.setAttribute(tokenName, tokenValue);

		return tokenValue;
	}

	// 验证表单token值和session中的token值是否一致
	public static boolean validToken(HttpServletRequest request) {
		return validToken(Constants.TOKEN,request);
	}
	// 验证表单token值和session中的token值是否一致
	public static boolean validToken(String tokenName, HttpServletRequest request) {
		String inputToken = getInputTokenValue(request,tokenName);
		if (inputToken == null) {
			logger.warn("token is not valid!inputToken is NULL");
			return false;
		}
		int semicolonInd = StringUtils.indexOf(inputToken, ";");
		if (semicolonInd <= 0) {
			logger.warn("token is not valid!inputToken is " + inputToken);
			return false;
		}
		// String paramToken=StringUtils.substring(inputToken, 0,semicolonInd);;
		String encodeUrl = StringUtils.substring(inputToken, semicolonInd + 1);

		HttpSession session = request.getSession();
		String sessionTokenKey = getSessionTokenKey(tokenName,encodeUrl);

		String sessionToken = (String) session.getAttribute(sessionTokenKey);
		String requestToken = (String) request.getAttribute(sessionTokenKey);

		if (!(StringUtils.equals(sessionToken, inputToken) || StringUtils.equals(requestToken, inputToken))) {
			// 先用sessin的值比较，没有就用requestToken比较//这个设计是为了防止一个请求调用多次比对错误
			return false;
		}
		request.setAttribute(sessionTokenKey, inputToken);
		session.removeAttribute(sessionTokenKey);

		return true;
	}

	/***
	 * 处理验证逻辑
	 * 
	 * @param request
	 * @param response
	 * @param url
	 * @return
	 */
	public static boolean processValid(String tokenName, HttpServletRequest request, HttpServletResponse response,
                                       String url) throws Exception {
		if (!validToken(tokenName,request)) {// 验证失败
			//String characterCode = SessionUtil.getLocaleString(request);
			/*String message = localeUtil.getLocalText("please.do.not.duplicate.submissions", "请不要重复提交。", characterCode,
					new Object[] {});*/
			String message ="请不要重复提交";
			String ajaxResType = request.getParameter(Constants.AJAX_RES_TYPE);

			// ajax请求方式返回状态信息。
			if (Constants.AJAX_RES_TYPE_JSON.equalsIgnoreCase(ajaxResType)) {
				ResultDTO resultDTO = new ResultDTO(Constants.DUPLICATE_SUBMISSION_STATE, message);
				resultDTO.put(tokenName, generateTokenValue(tokenName,request));
				resultDTO.put(Constants.TOKEN_NAME, tokenName);
				// 输出json流
				writeJSONString(response, toMap(resultDTO));

			} else if (StringUtils.startsWith(url, "redirect:")) {
				response.sendRedirect(StringUtils.removeStart(url, "redirect:"));

			} else if (StringUtils.isNotBlank(url)) {
				request.getRequestDispatcher(url).forward(request, response);

			} else {
				// 输出字符串
				writeString(response, message);
			} // end if;
			return false;
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object processReturnObject(String tokenName, HttpServletRequest request, Object object) throws Exception {
		if (Constants.AJAX_RES_TYPE_JSON.equalsIgnoreCase(request.getParameter(Constants.AJAX_RES_TYPE))) {
			String token=null;
			if (object instanceof ResultDTO) {
				token=generateTokenValue(tokenName,request);
				ResultDTO resultDTO = (ResultDTO) object;
				resultDTO.put(tokenName, token );
			} else if (object instanceof Map<?, ?>) {
				Map map = (Map) object;
				try {
					token = generateTokenValue(tokenName,request);
					map.put(tokenName, token );
					map.put(Constants.TOKEN_NAME, tokenName);
				} catch (Exception e) {
					logger.error("set token error:" + e);
				}
			}
		}
		return object;
	}

	/***
	 * 获取request地址路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getEncodeFilterRequestUrl(HttpServletRequest request) {
		String requestUrl = getRequestUrl(request);
		requestUrl = filterUrl(requestUrl);
		return getEncodeUrl(requestUrl);
	}

	/***
	 * 编码
	 * 
	 * @param url
	 * @return
	 */
	public static String getEncodeUrl(String url) {
		url = StringUtils.defaultString(url);
		url = new String(Base64.encodeBase64(url.getBytes()));
		return url;
	}

	/**
	 * 获取request Url地址路径
	 * 
	 * @param request
	 * @return
	 */
	private static String getRequestUrl(HttpServletRequest request) {
		String requestUrl = getRequestUri(request);
		return requestUrl;
	}

	/***
	 * 去掉重要路径信息
	 * 
	 * @param url
	 * @return
	 */
	public static String filterUrl(String url) {
		url = StringUtils.replace(url, "/WEB-INF/views/", "/");
		url = StringUtils.removeEnd(url, ".jsp");
		return url;
	}

	/**
	 * 获取session中tokenKey
	 * 
	 * @param encodeRequestUrl
	 * @return
	 */
	private static String getSessionTokenKey(String tokenName,String encodeRequestUrl) {
		String sessionTokenKey = tokenName + ":" + encodeRequestUrl;
		return sessionTokenKey;
	}

	/***
	 * 获取session中token, 注意：requestUrl是生成jsp页面的路径地址,要去掉/WEB-INF/views/和结尾的.jsp,
	 * 生成token或者验证逻辑通过后才能取到值，验证失败是取不到值的或是不正确的。
	 * 
	 * @param request
	 * @return
	 */
	public static String getSessionToken(String tokenName, HttpServletRequest request) {
		String requestUrl = getRequestUrl(request);
		return getSessionToken(tokenName,request, requestUrl);
	}

	public static String getSessionToken(String tokenName, HttpServletRequest request, String path) {
		HttpSession session = request.getSession();

		String encodeRequestUrl = filterUrl(path);
		encodeRequestUrl = getEncodeUrl(encodeRequestUrl);
		String sessionTokenKey = getSessionTokenKey(tokenName,encodeRequestUrl);

		String token = (String) request.getAttribute(tokenName);
		String sessionToken = (String) session.getAttribute(sessionTokenKey);
		String requestToken = (String) request.getAttribute(sessionTokenKey);

		//token = StringUtils.defaultIfBlank(token, requestToken, sessionToken);
		return token;
	}

	/**
	 * 获取表单中token值
	 * 
	 * @param request
	 * @return
	 */
	//@SuppressWarnings("unchecked")
	//public static String getInputToken(HttpServletRequest request) {
		
	//}
	/**
	 * 获取表单中token值
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static String getInputTokenValue(HttpServletRequest request, String tokenName) {
		Map params = request.getParameterMap();
		if (!params.containsKey(tokenName)) {
			logger.warn("Could not find token name in params.");
			return null;
		}
		String[] tokens = (String[]) params.get(tokenName);
		if ((tokens == null) || (tokens.length < 1)) {
			logger.warn("Got a null or empty token name.");
			return null;
		}
		return tokens[0];
	}


	public static String decodeInternal(HttpServletRequest request, String source) {
		String enc = determineEncoding(request);
		try {
			return UriUtils.decode(source, enc);
		} catch (UnsupportedEncodingException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("Could not decode request string [" + source + "] with encoding '" + enc
						+ "': falling back to platform default encoding; exception message: "
						+ ex.getMessage());
			}
			return URLDecoder.decode(source);
		}
	}

	public static String determineEncoding(HttpServletRequest request) {
		String enc = request.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		return enc;
	}

	public static String getRequestUri(HttpServletRequest request) {
		String uri = (String) request.getAttribute(WebUtils.INCLUDE_REQUEST_URI_ATTRIBUTE);
		if (uri == null) {
			uri = request.getRequestURI();
		}
		return decodeAndCleanUriString(request, uri);
	}

	public static String decodeAndCleanUriString(HttpServletRequest request, String uri) {
		uri = removeSemicolonContentInternal(uri);
		uri = decodeInternal(request, uri);
		return uri;
	}

	public static String removeSemicolonContentInternal(String requestUri) {
		int semicolonIndex = requestUri.indexOf(';');
		while (semicolonIndex != -1) {
			int slashIndex = requestUri.indexOf('/', semicolonIndex);
			String start = requestUri.substring(0, semicolonIndex);
			requestUri = (slashIndex != -1) ? start + requestUri.substring(slashIndex) : start;
			semicolonIndex = requestUri.indexOf(';', semicolonIndex);
		}
		return requestUri;
	}

	/***
	 * 输出为json格式流
	 *
	 * @param response
	 * @param dataMap
	 * @throws IOException
	 */
	public static void writeJSONString(ServletResponse response,
									   Map<? extends Object, ? extends Object> dataMap) throws IOException {
		String jsonString = JSONObject.toJSONString(dataMap);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonString);
	}

	/***
	 * 输出字符流
	 *
	 * @param response
	 * @param string
	 * @throws IOException
	 */
	public static void writeString(ServletResponse response, String string) throws IOException {
		writeString(response, string, "UTF-8");
	}

	/***
	 * 输出字符流
	 *
	 * @param response
	 * @param string
	 * @param characterEncoding
	 * @throws IOException
	 */
	public static void writeString(ServletResponse response, String string, String characterEncoding)
			throws IOException {

	if(characterEncoding==null||characterEncoding=="") {
		characterEncoding ="UTF-8";
	}
    response.setContentType("text/html;charset=" + characterEncoding);
    response.setCharacterEncoding(characterEncoding);
    response.getWriter().print(string);
	}

	/**
	 * 将resultDTO的内部信息转换为map对象，返回出来。
	 *
	 * @param result
	 * @return
	 */
	public static Map<String, Object> toMap(ResultDTO resultDTO, String messageKey) {
		resultDTO.setMessageKey(messageKey);
		return toMap(resultDTO);
	}

	/**
	 * 将resultDTO的内部信息转换为map对象，返回出来。
	 *
	 * @param result
	 * @return
	 */
	public static Map<String, Object> toMap(ResultDTO resultDTO) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (resultDTO != null) {
			String message = resultDTO.getMessage();
			map.put(Constants.STATE, resultDTO.getState());
			map.put(Constants.MESSAGE, message);
			map.putAll(resultDTO.getModel());
		}
		return map;
	}
}
