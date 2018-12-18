package com.corry.base.util;


public class Constants {
	  //原来的nosql下的的定义//start
	  public final static int LANGUAGE_DB = 1;
	  public final static int CONFIG_DB = 2;
	  public final static int LIST_DB = 3;
	  public final static int SESSION_DB = 4;
	  public final static int OPENAPI_SESSION_DB = 5;
	  public final static int SHIRO_SESSION_DB = 6;
	  public final static int USER_ID_CACHE_DB = 7;
	  public final static int USER_CODE_CACHE_DB = 8;
	  public final static int BACKOFFICE_REGISTER_DB = 9;
	  public final static int PRODUCT_INFO_DB = 10;
	  public final static int SESSION_EXPIRE = 600;
	  public final static int HIBERNATE_CACHE_DB=11;
	  public final static int LOCK_DB=12;
	  
	  public final static int OPENAPI_SESSION_EXPIRE = 600;

	  public final static int SHIRO_SESSION_EXPIRE = 1200;

	  public final static String SHIRO_REDIS_SESSION_KEY_PREFIX = "shiro_redis_session:";
	  public final static String SHIRO_REDIS_CACHE_KEY_PREFIX = "shiro_redis_cache:";
	  
	  //原来的nosql下的的定义//end
	  public final static String AUTO_ORDER_TASK_LOCK="auto_order_task_{0}_lock";
	  //注册时控制锁
	  public final static String REGISTER_LOCK="register_{0}_lock";
	  public final static String REGISTER_SIN_TAX_ID_LOCK="register_sin_tax_id_{0}_lock";
	  //支付订单控制锁
	  public static final String ORDERPAY_LOCK="orderpay_{0}_lock";
	  // start//控制层使用到的相关参数
	  public static final String MESSAGE = "message";
	  public static final String MESSAGE_KEY = "messageKey";
	  public static final String MESSAGE_PARAMETER = "messageParameter";
	
	  public static final String STATE = "state";
	  public static final String PAGE = "page";
	  public static final String TOKEN = "mallToken";
	  public static final String TOKEN_NAME = "mallTokenName";
	  public static final String AJAX_RES_TYPE = "ajaxResType";
	  public static final String AJAX_RES_TYPE_JSON = "json";
	  public static final int NO_LOGON_STATE = -10000;// 没有登录状态
	  public static final int NO_RIGHT_STATE = -10001;// 没有权限访问状态
	  public static final int NOT_SAME_LOCAL_SERVER_STATE = -10002;// 不同的本地ip地址访问
	  public static final int DUPLICATE_SUBMISSION_STATE = -10003;// 重复提交
	  public static final int ERROR_INDEX_STATE = -10004;// 错误首页访问状态
	
	  // end //start//控制层使用到的相关参数
	
	
	  public final static String FIRST_MENU_FLAG_KEY = "mall.firstMenuFlag";// 构建菜单使用
	  public final static String FIRST_MENU_ID_KEY = "mall.firstMenuId";// 第一个菜单id//构建菜单使用
	
	  public static final String SESSION_CACHE_BO_MENUS_KEY = "mall.cacheBoMenus";// 缓存backOffice菜单对象key
	  public static final String SESSION_CACHE_BO_MENU_CACHED_KEY = "mall.cacheBoMenuCached";// 缓存backOffice菜单是否缓存key
	  public static final String SESSION_CACHE_BO_MENU_LOCALE_KEY = "mall.cacheBoMenuLocale";// 缓存backOffice菜单语言key
	  public static final String SESSION_CACHE_BO_MENU_LOGIN_NAME_KEY = "mall.cacheBoMenuLoginName";// 缓存backOffice菜单的缓存用户key
	  public static final String SESSION_CACHE_BO_MENU_CONTENT_KEY = "mall.cacheBoMenuContent";// 缓存backOffice菜单内容key
	
	
	  public static final String SESSION_CACHE_PORTAL_TOP_MENUS_KEY = "mall.cachePortalTopMenus";// 缓存portal的顶部菜单对象key
	  public static final String SESSION_CACHE_PORTAL_TOP_MENU_CACHED_KEY =
	      "mall.cachePortalTopMenuCached";// 缓存portal的顶部菜单是否缓存key
	  public static final String SESSION_CACHE_PORTAL_TOP_MENU_LOCALE_KEY =
	      "mall.cachePortalTopMenuLocale";// 缓存portal的顶部菜单语言key
	  public static final String SESSION_CACHE_PORTAL_TOP_LOGIN_NAME_KEY =
	      "mall.cachePortalTopMenuLoginName";// 缓存backOffice顶部菜单缓存用户key
	  public static final String SESSION_CACHE_PORTAL_TOP_MENU_CONTENT_KEY =
	      "mall.cachePortalTopMenuContent";// 缓存portal顶部菜单内容key
	
	  public static final String SESSION_CACHE_PORTAL_LEFT_MENUS_KEY = "mall.cachePortalLeftMenus";// 缓存portal左边菜单对象key
	  public static final String SESSION_CACHE_PORTAL_LEFT_MENU_CACHED_KEY =
	      "mall.cachePortalLeftMenuCached";// 缓存portal左边菜单是否缓存key
	  public static final String SESSION_CACHE_PORTAL_LEFT_MENU_LOCALE_KEY =
	      "mall.cachePortalLeftMenuLocale";// 缓存portal左边菜单语言key
	  public static final String SESSION_CACHE_PORTAL_LEFT_LOGIN_NAME_KEY =
	      "mall.cachePortalLeftMenuLoginName";// 缓存backOffice左边菜单的缓用户key
	  public static final String SESSION_CACHE_PORTAL_LEFT_MENU_CONTENT_KEY =
	      "mall.cachePortalLeftMenuContent";// 缓存portal左边菜单菜单内容key
	
	
	  public static final String SELECT_MENU_ID = "smid";// url使用//当前选择的菜单Id
	  public static final String SESSION_SELECT_MENU_ID_KEY = "mall.selectMenuId";// session中保存的菜单Id
	
	  public static final String LOGIN_SESSION_KEY = "loginSession";// 保存当前session的key
	
	  public static final String SESSION_USER_KEY = "mall.user";// session用户key定义
	  public static final String SESSION_ADDRESS_KEY = "mall.address";// session中地址的定义
	
	  public static final String DEFAULT_COMPANY_CODE = "CN";// 默认机构的参数定义
	  public static final String COMPANY_CODE = "companyCode";// 选择机构的参数定义
	  // public static final String SESSION_COMPANY_CODE_KEY="mall.companyCode";//选择的机构保存到session的key
	  public static final String SESSION_COMPANY_KEY = "mall.company";// 选择的机构保存到session的key这里改为保存为office对象
	
	  public static final String SESSION_CUR_WEEKLY_BATCH = "mall.curWeeklyBatch";// bo中使用的当前工作月
	
	  public static final String LOCALE_EN_US = "en_US";// 美国英语
	  public static final String LOCALE_ZH_CN = "zh_CN";// 中国汉语
	
	  public static final String DEFAULT_LOCALE = Constants.LOCALE_EN_US;// 默认语言的参数定义
	  public static final String LOCALE = "locale";// 选择语言的参数定义
	  public static final String SESSION_LOCALE_KEY = "mall.locale";// 选择的语言保存到session的key
	  public static final String SESSION_SET_UP_LOCALE = "mall.set.up.locale";// 选择的语言保存到session的key
	
	  public static final String DEFAULT_THEME = "default";// 默认主题的参数定义
	  public static final String THEME = "theme";// 选择主题的参数定义
	  public static final String SESSION_THEME_KEY = "mall.theme";// 选择的主题保存到session的key
	
	  public static final String SESSION_TIME_ZONE_KEY = "mall.time.zone";// 时间显示方式
	
	
	  public static final String SESSION_CHARACTER_CODING_LIST_KEY = "mall.character.coding.list";// 字符列表

	
	  // start//验证码相关参数
	  public static final String DEFAULT_LOGIN_URL = "/login";
	  public static final String LOGIN_FAILURE_KEY = "loginFailure";
	  public static final String VALIDATE_CODE_KEY = "validateCode";// 保存验证码key
	  public static final String LOGIN_FAIL_NUM_KEY = "loginFailNum";// 登录失败次数
	  public static final int NORMAL_LOGIN_MAX_NUM = 2;// 普通登录最大次数，超过次数则需要显示验证码
	
	  public static final String IS_VALIDATECODE_LOGIN = "isValidateCodeLogin";// 是否需要验证码登录
	  // end//验证码相关参数
	
	  // start//shiro相关参数
	  public static final String USER_REALM_NAME =
	      "com.cj.framework.ext43rdparty.shiro.mallUserRealm";// mallUserRealm name
	  public static final String USER_REALM_AUTHORIZATION_CACHENAME =
	      "com.cj.framework.ext43rdparty.shiro.mallUserRealm.authorizationCache";// mallUserRealm
	                                                                               // authorizationCache
	
	  public static final String BACK_URL = "backUrl";// shiro没登录验证获取地址参数名称
	  public static final String URL_CONTEXT="_CONTEXT_";//地址中传入请求上下文参数
	  public static final String AUTHC_FALLBACK_URL = "authc.fallbackUrl";// 回退地址session保存的key
	  public static final String RIGHT_INDEX_URL_KEY = "right.index.url.key";// 正确的首页地址key
	  public static final String SILENT_LOGIN_BACK_OFFICE_RIGHT = "silent:login:bo";// 静态登录backoffice权限信息
	  // end//shiro相关参数
	
	  // start//修改权限参数
	  public static final int UPDATE_AUTHORIZATION_JMS_SENDER_TYPE_MENU = 1;// 菜单类型
	  public static final int UPDATE_AUTHORIZATION_JMS_SENDER_TYPE_ROLE = 2;// 菜单类型
	  public static final int UPDATE_AUTHORIZATION_JMS_SENDER_TYPE_USER = 3;// 用户类型
	  public static final int UPDATE_AUTHORIZATION_JMS_SENDER_TYPE_LOGIN_NAME = 4;// 登录名类型
	
	  public static final int CLEAN_AUTHORIZATION_JMS_SENDER_TYPE_ALL = 5;// 清除所有用户权限
	  public static final int CLEAN_AUTHORIZATION_JMS_SENDER_TYPE_MENU = 6;// 清除用户权限根据菜单id
	  public static final int CLEAN_AUTHORIZATION_JMS_SENDER_TYPE_ROLE = 7;// 清除用户权限根据角色id
	  public static final int CLEAN_AUTHORIZATION_JMS_SENDER_TYPE_USER = 8;// 清除用户权限根据用户id
	  public static final int CLEAN_AUTHORIZATION_JMS_SENDER_TYPE_LOGIN_NAME = 9;// 清除用户权限根据用户登录名
	
	
	  public static final int UPDATE_SYSTEM_CACHE_JMS_SENDER_TYPE_ALL = 20;// 更新所有缓存
	  public static final int UPDATE_SYSTEM_CACHE_JMS_SENDER_TYPE_CODE = 21;// 根据编码更新缓存
	  public static final int UPDATE_SYSTEM_CACHE_JMS_SENDER_TYPE_ID = 22;// 根据ID更新缓存
	
	  public static final int CLEAN_SYSTEM_CACHE_JMS_SENDER_TYPE_ALL = 30;// 清除所有系统缓存
	  public static final int CLEAN_SYSTEM_CACHE_JMS_SENDER_TYPE_CODE = 31;// 根据编码清除系统缓存
	  public static final int CLEAN_SYSTEM_CACHE_JMS_SENDER_TYPE_ID = 32;// 根据ID清除系统缓存
	
	  public static final String JMS_SENDER_TYPE_KEY = "type";
	  public static final String JMS_SENDER_VALUE_KEY = "value";
	
	  // end//修改权限参数
	
	  // start//cookie相关参数
	  public static final String SESSION_KEY = "mallSESSIONID";
	  public static final String SHIRO_SESSION_KEY = "mallSHIROSESSIONID";
	  public static final String COOKIE_DOMAIN = "";
	  public static final String COOKIE_PATH = "/";
	  // end//cookie相关参数
	
	  // 单据类型
	  /**
	   * //正常单据
	   */
	  public static final String ORDER_TYPE_O = "O";// 正常单据
	  /**
	   * //换货单据
	   */
	  public static final String ORDER_TYPE_C = "C";// 换货单据
	  /**
	   * //退货单
	   */
	  public static final String ORDER_TYPE_R = "R";// 退货单
	  
	  /**
	   *  订单结算日期
	   */
	  public static final String ORDER_TYPE_D = "D";// 调期单
	
	  // 参数配置
	  public static final String MEMBER_REGISTER_ROLE = "MEMBER.REGISTER.ROLE";// backOffice注册时角色
	  public static final String MEMBER_INITSTORE_ROLE = "MEMBER.INITSTORE.ROLE";// 待审店铺会员角色
	  public static final String MEMBER_STORE_ROLE = "MEMBER.STORE.ROLE";//正式 店铺会员角色
	  public static final String BACKOFFICE_REGISTRATION_AGREEMENT =
	      "BACKOFFICE.REGISTRATION.AGREEMENT";// backOffice注册协议
	  public static final String USE_AUTO_ORDER_COMPANY = "use.auto.order.company";// 使用自动下单的分公司
	  public static final String IDENTITY_CARD_VERIFICATION_COMPANY =
	      "identity.card.verification.company";// 验证身份证的分公司
	  public static final String MOBILE_REQUEST_DOMAIN_URL = "mobile.request.domain.url";// 手机访问系统路径
	
	  /**
	   * 验证使用bv,pv参数(p:验证使用pv;b或其它使用pv )
	   */
	  public static final String VALIDATE_USE_BV_PV="validate.use.bv.pv";
	  /**
	   * 显示使用bv,pv参数(b:显示bv;p:显示:pv;其它:显示bv,pv )
	   */
	  public static final String SHOW_USE_BV_PV="show.use.bv.pv";
	  /***
	   * 超多多少金额 不让下单功能
	   */
	  public static final String ORDER_MONEY_OVER = "ORDER_OVER_MONEY";
	
	  /***
	   * 订单中含直接升钻石产品编码的参数key
	   */
	  public static final String ORDER_STRAIGHT_UP_DIAMOND_PRODUCT =
	      "ORDER.STRAIGHT.UP.DIAMOND.PRODUCT";
	  /**
	   * 应用上下文代码
	   */
	  public static final String WEB_APP_CTX_CODE_BACK_OFFICE = "backOffice";
	  public static final String WEB_APP_CTX_CODE_PORTAL = "portal";
	  public static final String WEB_APP_CTX_CODE_OSS_WALLET = "ossWallet";
	  public static final String WEB_APP_CTX_CODE_OSS_MEMBER = "ossMember";
	  /***
	   * 会员编号生成规则参数
	   * ?代表no,#代表公司编码,+为分隔,X直接为扩展值,;封号分隔随机长度
	   * ?,#,X 按+连接顺序生成编码顺序，
	   * 说明：
	   * 1,'1:X' : X+no
	   * 2,'2:X' : 公司编码+X+no
	   * 3,'3:X' : X+公司编码+no
	   * 4,'4:X' : no+X
	   * 5,'5:X' : no+公司编码+X
	   * 6,'6:X' : no+X+公司编码
	   * 7,'7:(?+#+X)...': ?,#,X 按+连接顺序生成编码顺序，
	   * 8,'8:N;(?+#+X)': N代表长度,默认为7位,生成顺序为按7
	   * 其它 ： no
	   */
	  public static final String GENERATOR_MEMBER_NO_RULE="generator.member.no.rule";
	public static final String LOGIN_MAX_FAIL_NUM = "login.max.fail.num";
	public static final int LOGIN_MAX_FAIL_NUM_DEFAULT = 5;
	
	public static final String JDBC_TYPE_ORACLE="oracle";
	public static final String JDBC_TYPE_POSTGRESQL="postgresql";
	
	
}
