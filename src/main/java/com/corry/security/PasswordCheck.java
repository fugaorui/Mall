package com.corry.security;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;



public class PasswordCheck {
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

	private final static String DEFAULT_PROPERTIES = "shiro.properties";
	private final static String CREDENTIALS_MATCHER_CLASS_KEY = "credentials.matcher.class";
	private final static String CREDENTIALS_MATCHER_CLASS ;
	
	static {
		try {
			Properties properties = new Properties();
			ClassPathResource resource = new ClassPathResource(DEFAULT_PROPERTIES);
			PropertiesLoaderUtils.fillProperties(properties, resource);
			String cfgStr=properties.getProperty( CREDENTIALS_MATCHER_CLASS_KEY );
			CREDENTIALS_MATCHER_CLASS= StringUtils.trim( cfgStr );
		} catch (Exception e) {
			System.err.println("shiro.properties load error.please check.");
			throw new Error("shiro.properties load error.", e);
		}
	}

	public static String entryptPassword(String plainPassword) {
		if("com.cj.framework.ext43rdparty.shiro.JmoaCredentialsMatcherV2".equals( CREDENTIALS_MATCHER_CLASS )){
			return entryptPasswordV2(plainPassword);
		}else{
			return entryptPasswordDef( plainPassword );
		}
	}
	
	public static boolean validatePassword(String plainPassword, String password) {
		if("com.cj.framework.ext43rdparty.shiro.JmoaCredentialsMatcherV2".equals( CREDENTIALS_MATCHER_CLASS )){
			return validatePasswordV2( plainPassword,password );
		}else{
			return validatePasswordDef( plainPassword,password );
		}
	}
	
	
	
	/**
	 * 生成安全的密码，生成随机的17位salt并经 sha-1 hash
	 */
	private static String entryptPasswordDef(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}

	/**
	 * 验证密码
	 * 
	 * @param plainPassword
	 *            明文密码
	 * @param password
	 *            密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePasswordDef(String plainPassword, String password) {
		boolean result = false;
		if (StringUtils.length(password) <= 16) {
			if (password != null && password.equals(plainPassword)) {// 明文密码比较
				result = true;
			}
		} else {
			byte[] salt = Encodes.decodeHex(password.substring(0, 16));
			byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
			result = password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
		}
		return result;
	}

	private static String entryptPasswordV2(String plainPassword) {
		String enPassword="";
		String salt= RandomUtils.nextInt(0,9999)+"";
		salt=StringUtils.rightPad( salt , 4 ,"0") ;
		try {
			enPassword = DigestUtils.md5Hex(plainPassword.getBytes("UTF-8"));
			enPassword = enPassword + salt;
			enPassword = DigestUtils.md5Hex(enPassword.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.err.println("PasswordCheck.entryptPasswordV2(String) error");
			e.printStackTrace();
		}
		String password= salt+enPassword;
		
		return password;
	}
	
	public static boolean validatePasswordV2(String plainPassword, String password) {
		/**
		 * $newpassword = md5(md5($password).$ec_salt);//ec_salt = 1-9999
		 * 明文：123456，随机字符串：ec_salt=6087，加密方式：md5(md5(123456)+6087)，
		 * 密文：0bdc6b4a3a6ca6c68f0e93ce6c3e1df1
		 */
		String salt = StringUtils.substring(password, 0, 4);
		//去掉前面的0
		while(StringUtils.startsWith( salt , "0")){
			salt=StringUtils.substring( salt , 1 );
		}
		
		String orgPassword = StringUtils.substring(password, 4);
		
		String enPassword="";
		try {
			enPassword = DigestUtils.md5Hex(plainPassword.getBytes("UTF-8"));
			enPassword = enPassword + salt;
			enPassword = DigestUtils.md5Hex(enPassword.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.err.println("PasswordCheck.validatePasswordV2(String, String) error");
			e.printStackTrace();
		}
		return enPassword.equals( orgPassword );
	}
}
