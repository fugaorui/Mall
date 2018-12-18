package com.corry.base.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * Json处理器<br>
 * 
 * @author XiongChun
 * @since 2009-07-07
 */
public class JsonHelper {

	private static Log log = LogFactory.getLog(JsonHelper.class);

	/**
	 * 将不含日期时间格式的Java对象系列化为Json资料格式
	 * 
	 * @param pObject
	 *            传入的Java对象
	 * @return
	 */
	public static final String encodeObject2Json(Object pObject) {
		String jsonString = "[]";
		if (AssistTool.isEmpty(pObject)) {
			// log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
			return "";
		} else {
			if (pObject instanceof ArrayList || pObject instanceof LinkedList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject);
				jsonString = jsonObject.toString();
			}
		}
		//Page page = (Page) pObject;
		String json = "{\"data\":"+jsonString+"}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + json);
		}
		return json;
	}
	
	/**
	 * datatables分页参数封装
	 * @param pObject
	 * @return
	 */
	
	public static final String data2Json(Object pObject,int sEcho) {
		String jsonString = "[]";
		if (AssistTool.isEmpty(pObject)) {
			// log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
			return "{\"sEcho\":" + 0+ ",\"iTotalRecords\":" + 0 + ",\"iTotalDisplayRecords\":" + 0 + ",\"data\":" + jsonString + "}";
		} else {
			if (pObject instanceof ArrayList || pObject instanceof LinkedList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject);
				jsonString = jsonObject.toString();
			}
		}
		int initEcho = sEcho+1;
		PageInfo page = new PageInfo((List) pObject);
		String json = "{\"sEcho\":" + initEcho + ",\"iTotalRecords\":" + page.getTotal() + ",\"iTotalDisplayRecords\":" + page.getTotal() + ",\"data\":" + jsonString + "}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + json);
		}
		return json;
	}
	public static final String StandencodeObject2Json(Object pObject) {
		String jsonString = "[]";
		if (AssistTool.isEmpty(pObject)) {
			// log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
			return "";
		} else {
			if (pObject instanceof ArrayList || pObject instanceof LinkedList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject);
				jsonString = jsonObject.toString();
			}
		}
		String Msg="调用成功"; 
		Integer Code=1;
	    jsonString="{\"Msg\":\"" + Msg + "\",\"Code\":\"" + Code + "\",\"Row\":"+jsonString+"}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	/**
	 * App数据返回参数
	 * @param pObject
	 * @return
	 */
	public static final String AppObject2Json(String msg,Integer code,Object pObject) {
		String jsonString = "[]";
		if (AssistTool.isEmpty(pObject)) {
			// log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
			jsonString = jsonString;
		} else {
			if (pObject instanceof ArrayList || pObject instanceof LinkedList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject);
				jsonString = jsonObject.toString();
			}
		}
		String Msg=msg; 
		Integer Code=code;
	    jsonString="{\"Msg\":\"" + Msg + "\",\"Code\":\"" + Code + "\",\"Row\":"+jsonString+"}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	public static final String StandencodeList2PageQUI(List list, Integer pageNo, Integer pageSize,Integer TotalCount, String dataFormat) {
		String subJsonString = "";
		if (AssistTool.isEmpty(dataFormat)) {
			subJsonString = encodeObject2Json(list);
		} else {
			subJsonString = encodeObject2Json(list, dataFormat);
		}
		String Msg="调用成功"; 
		Integer Code=1;
		subJsonString = subJsonString == "" ? "[]" : subJsonString;
		String jsonString = "{\"Msg\":\"" + Msg + "\",\"Code\":\"" + Code + "\",\"Result\":{\"Rows\":" +subJsonString + ",\"pageNo\":\"" + pageNo + "\",\"pageSize\":\"" + pageSize + "\",\"TotalCount\":\"" + TotalCount + "\"}}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	/**
	 * 将不含日期时间格式的Java对象系列化为Json资料格式
	 * 
	 * @param pObject
	 *            传入的Java对象
	 * @return
	 */
	public static final String encodeObject2EasyUIJson(Object pObject) {
		String jsonString = "[]";
		if (AssistTool.isEmpty(pObject)) {
			// log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			if (pObject instanceof ArrayList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject);
				jsonString = jsonObject.toString();
			}
			//jsonString="["+jsonString+"]";
		}
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	/**
	 * 将含有日期时间格式的Java对象系列化为Json资料格式<br>
	 * Json-Lib在处理日期时间格式是需要实现其JsonValueProcessor接口,所以在这里提供一个重载的方法对含有<br>
	 * 日期时间格式的Java对象进行序列化
	 * 
	 * @param pObject
	 *            传入的Java对象
	 * @return
	 */
	public static final String encodeObject2Json(Object pObject, String pFormatString) {
		String jsonString = "[]";
		if (AssistTool.isEmpty(pObject)) {
			// log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			JsonConfig cfg = new JsonConfig();
			cfg.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonValueProcessorImpl(pFormatString));
			cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl(pFormatString));
			cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl(pFormatString));
			//cfg.registerJsonValueProcessor(oracle.sql.CLOB.class, new JsonValueClobProcessImpl());
			if (pObject instanceof ArrayList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject, cfg);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject, cfg);
				jsonString = jsonObject.toString();
			}
		}
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	public static final String StandencodeObject2Json(Object pObject, String pFormatString) {
		String jsonString = "[]";
		if (AssistTool.isEmpty(pObject)) {
			// log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			JsonConfig cfg = new JsonConfig();
			cfg.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonValueProcessorImpl(pFormatString));
			cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl(pFormatString));
			cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl(pFormatString));
			//cfg.registerJsonValueProcessor(oracle.sql.CLOB.class, new JsonValueClobProcessImpl());
			if (pObject instanceof ArrayList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject, cfg);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject, cfg);
				jsonString = jsonObject.toString();
			}
		}
		String Msg="调用成功"; 
		Integer Code=1;
	    jsonString="{\"Msg\":\"" + Msg + "\",\"Code\":\"" + Code + "\",\"Row\":"+jsonString+"}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	
	/**
	 * 状态码
	 * @param pObject
	 * @param pFormatString
	 * @return
	 */
	public static final String getBackCode(Object pObject) {
		String jsonString = "";
		String Msg="调用成功"; 
	    jsonString="{\"Msg\":\"" + Msg + "\",\"Code\":\"" + pObject + "\"}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	/**
	 * 状态码
	 * @param pObject
	 * @param pFormatString
	 * @return
	 */
	public static final String getBackMessage(Object pObject) {
		String jsonString = "";
		jsonString="{\"Msg\":\"" + pObject  + "\"}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	/*jquery EasyUI json turn*/
	public static final String encodeObject2EasyUIJson(Object pObject, String pFormatString) {
		String jsonString = "[]";
		if (AssistTool.isEmpty(pObject)) {
			// log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			JsonConfig cfg = new JsonConfig();
			cfg.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonValueProcessorImpl(pFormatString));
			cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl(pFormatString));
			cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl(pFormatString));
			//cfg.registerJsonValueProcessor(oracle.sql.CLOB.class, new JsonValueClobProcessImpl());
			if (pObject instanceof ArrayList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject, cfg);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject, cfg);
				jsonString = jsonObject.toString();
			}
		}
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	/**
	 * 将分页信息压入JSON字符串
	 * 此类内部使用,不对外暴露
	 * @param JSON字符串
	 * @param totalCount
	 * @return 返回合并后的字符串
	 */
	private static String encodeJson2PageJson(String jsonString, Integer totalCount) {
		jsonString = "{TOTALCOUNT:" + totalCount + ", ROOT:" + jsonString + "}";
		if (log.isInfoEnabled()) {
			log.info("合并后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	private static String encodeJson2PageUi(String jsonString, Integer totalCount) {
		jsonString = "{\"total\":\"" + totalCount + "\", \"rows\":" + jsonString + "}";
		if (log.isInfoEnabled()) {
			log.info("合并后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	/**
	 * 直接将List转为分页所需要的Json资料格式
	 * 
	 * @param list
	 *            需要编码的List对象
	 * @param totalCount
	 *            记录总数
	 * @param pDataFormat
	 *            时间日期格式化,传null则表明List不包含日期时间属性
	 */
	public static final String encodeList2PageJson(List list, Integer totalCount, String dataFormat) {
		String subJsonString = "";
		if (AssistTool.isEmpty(dataFormat)) {
			subJsonString = encodeObject2Json(list);
		} else {
			subJsonString = encodeObject2Json(list, dataFormat);
		}
		String jsonString = "{TOTALCOUNT:" + totalCount + ", ROOT:" + subJsonString + "}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	
	public static final String encodeList2PageUi(List list, Integer totalCount, String dataFormat) {
		String subJsonString = "";
		if (AssistTool.isEmpty(dataFormat)) {
			subJsonString = encodeObject2Json(list);
		} else {
			subJsonString = encodeObject2Json(list, dataFormat);
		}
		subJsonString = subJsonString == "" ? "[]" : subJsonString;
		String jsonString = "{\"total\":\"" + totalCount + "\", \"rows\":" + subJsonString + "}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	//QUI
	public static final String encodeList2PageQUI(List list, Integer totalCount, Integer page, String dataFormat) {
		String subJsonString = "";
		if (AssistTool.isEmpty(dataFormat)) {
			subJsonString = encodeObject2Json(list);
		} else {
			subJsonString = encodeObject2Json(list, dataFormat);
		}
		subJsonString = subJsonString == "" ? "[]" : subJsonString;
		String jsonString = "{\"pager.totalRows\":\"" + totalCount + "\", \"pager.pageNo\":\"" + page + "\", \"rows\":" +subJsonString + "}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	/**
	 * 将数据系列化为表单数据填充所需的Json格式
	 * 
	 * @param pObject
	 *            待系列化的对象
	 * @param pFormatString
	 *            日期时间格式化,如果为null则认为没有日期时间型字段
	 * @return
	 */
	public static String encodeDto2FormLoadJson(Dto pDto, String pFormatString) {
		String jsonString = "";
		String sunJsonString = "";
		if (AssistTool.isEmpty(pFormatString)) {
			sunJsonString = encodeObject2Json(pDto);
		} else {
			sunJsonString = encodeObject2Json(pDto, pFormatString);
		}
		jsonString = "{success:"
				+ (AssistTool.isEmpty(pDto.getAsString("success")) ? "true" : pDto.getAsString("success")) + ",data:"
				+ sunJsonString + "}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	/**
	 * 将单一Json对象解析为DTOJava对象
	 * 
	 * @param jsonString
	 *            简单的Json对象
	 * @return dto
	 */
	public static Dto parseSingleJson2Dto(String jsonString) {
		Dto dto = new BaseDto();
		if (AssistTool.isEmpty(jsonString)) {
			return dto;
		}
		JSONObject jb = JSONObject.fromObject(jsonString);
		dto = (BaseDto) JSONObject.toBean(jb, BaseDto.class);
		return dto;
	}
	   @SuppressWarnings({ "unchecked" })
	    public static Dto jsonToMap(String jsonString) {	         
	        JSONObject jsonObject = JSONObject.fromObject(jsonString);
	        Iterator keyIter = jsonObject.keys();
	        String key;
	        Object value;
	        Dto dto = new BaseDto();	 
	        while (keyIter.hasNext()) {	             
	            key = (String) keyIter.next();
	            value = jsonObject.get(key).toString();
	            dto.put(key, value);	             
	        }
	 
	        return dto;
	    }
 
	/**
	 * 将复杂Json资料格式转换为List对象
	 * 
	 * @param jsonString
	 *            复杂Json对象,格式必须符合如下契约<br>
	 *            {"1":{"name":"托尼.贾","age":"27"},
	 *            "2":{"name":"甄子丹","age":"72"}}
	 * @return List
	 */
	public static List parseJson2List(String jsonString) {
		List list = new ArrayList();
		JSONObject jbJsonObject = JSONObject.fromObject(jsonString);
		Iterator iterator = jbJsonObject.keySet().iterator();
		while (iterator.hasNext()) {
			Dto dto = parseSingleJson2Dto(jbJsonObject.getString(iterator.next().toString()));
			list.add(dto);
		}
		return list;
	}
	
	public static List<Dto> parseJson2List2(String jsonString) {
		List list = new ArrayList();
		JSONObject jbJsonObject = JSONObject.fromObject(jsonString);
		Iterator iterator = jbJsonObject.keySet().iterator();
		while (iterator.hasNext()) {
			JSONArray jsonArray = jbJsonObject.getJSONArray(iterator.next().toString());
			list.add(jsonArray.get(0));
		}
		return list;
	}
	
	public static Map parseSingleJson2Map(String jsonString) {
		Map dto = new LinkedHashMap();
		if (AssistTool.isEmpty(jsonString)) {
			return dto;
		}
		JSONObject jb = JSONObject.fromObject(jsonString);
		dto = (LinkedHashMap) JSONObject.toBean(jb, LinkedHashMap.class);
		return dto;
	}
	public static void main(String[] args){
		String s ="{\"aa\":{\"cate_id\":\"1000003\",\"dur_name\":\"lenovo台式机\",\"dp_id\":\"1000000035\",\"tck_amount\":\"4000\",\"dur_no\":\"监察收[20150331]4\",\"cpu\":\"121\",\"blos\":\"12\"},\"cc\":{\"cate_id\":\"1000003\",\"dur_name\":\"lenovo台式机\",\"dp_id\":\"1000000035\",\"tck_amount\":\"4000\",\"dur_no\":\"监察收[20150331]2\",\"cpu\":\"121\",\"blos\":\"1212\"},\"bb\":{\"cate_id\":\"1000003\",\"dur_name\":\"lenovo台式机\",\"dp_id\":\"1000000035\",\"tck_amount\":\"4000\",\"dur_no\":\"监察收[20150331]3\",\"cpu\":\"121\",\"blos\":\"1212\"}}";
		List<Dto> a = JsonHelper.parseJson2List(s);
		System.out.println(a.size());
	}
	
	/**
	 * 获取分页参数(datatables分页封装)
	 * @param httpServletRequest
	 * 获取初始值 
	 */
	public static int getiDisplayStart(HttpServletRequest httpServletRequest) {
		String aoData = httpServletRequest.getParameter("aoData");
		JSONArray alldata = JSONArray.fromObject(aoData);
		String sEcho = "";// 记录操作的次数  每次加1
        int iDisplayStart = 0;// 起始
        int iDisplayLength = 0;// size
        String sSearch = "";// 搜索的关键字
		for (int i = 0; i <alldata.size() ; i++) {
	            JSONObject obj = (JSONObject) alldata.get(i);
	            if (obj.get("name").equals("sEcho"))
	                sEcho = obj.get("value").toString();
	            if (obj.get("name").equals("iDisplayStart"))
	                iDisplayStart = Integer.parseInt(obj.get("value").toString());
	            if (obj.get("name").equals("iDisplayLength"))
	                iDisplayLength = Integer.parseInt(obj.get("value").toString());
	            if (obj.get("name").equals("sSearch"))
	                sSearch = obj.get("value").toString();
	    }
		int start = (iDisplayStart+iDisplayLength)/iDisplayLength;
		return start;
		
	}
	
	
	/**
	 * 获取分页参数(datatables分页封装)
	 * @param httpServletRequest
	 * 获取页码数 
	 */
	public static int getiDisplayLength(HttpServletRequest httpServletRequest) {
		String aoData = httpServletRequest.getParameter("aoData");
		JSONArray alldata = JSONArray.fromObject(aoData);
		String sEcho = "";// 记录操作的次数  每次加1
        String iDisplayStart = "";// 起始
        String iDisplayLength = "";// size
        String sSearch = "";// 搜索的关键字
		for (int i = 0; i <alldata.size() ; i++) {
	            JSONObject obj = (JSONObject) alldata.get(i);
	            if (obj.get("name").equals("sEcho"))
	                sEcho = obj.get("value").toString();
	            if (obj.get("name").equals("iDisplayStart"))
	                iDisplayStart = obj.get("value").toString();
	            if (obj.get("name").equals("iDisplayLength"))
	                iDisplayLength = obj.get("value").toString();
	            if (obj.get("name").equals("sSearch"))
	                sSearch = obj.get("value").toString();
	    }
		return Integer.parseInt(iDisplayLength);
	}
	
	/**
	 * 根据datetables
	 * 获取查询条件
	 */
	public static String getSearchStr(HttpServletRequest httpServletRequest,Object object) {
		String outStr="";
		String aoData = httpServletRequest.getParameter("aoData");
		if(AssistTool.isNotEmpty(object)){
			JSONArray alldata = JSONArray.fromObject(aoData);
			for (int i = 0; i <alldata.size() ; i++) {
	            JSONObject obj = (JSONObject) alldata.get(i);
	            if (obj.get("name").equals(object))
	            	outStr = obj.get("value").toString();
			}
			return outStr;
		}
		return outStr;
	}
	/**
	 * 获取分页参数(datatables分页封装)
	 * @param httpServletRequest
	 * 获取页码数 
	 */
	public static int getsEcho(HttpServletRequest httpServletRequest) {
		String aoData = httpServletRequest.getParameter("aoData");
		JSONArray alldata = JSONArray.fromObject(aoData);
		String sEcho = "";// 记录操作的次数  每次加1
        String iDisplayStart = "";// 起始
        String iDisplayLength = "";// size
        String sSearch = "";// 搜索的关键字
		for (int i = 0; i <alldata.size() ; i++) {
	            JSONObject obj = (JSONObject) alldata.get(i);
	            if (obj.get("name").equals("sEcho"))
	                sEcho = obj.get("value").toString();
	            if (obj.get("name").equals("iDisplayStart"))
	                iDisplayStart = obj.get("value").toString();
	            if (obj.get("name").equals("iDisplayLength"))
	                iDisplayLength = obj.get("value").toString();
	            if (obj.get("name").equals("sSearch"))
	                sSearch = obj.get("value").toString();
	    }
		return Integer.parseInt(sEcho);
	}
	
	/**
	 * zTree树结构组装(菜单授权)
	 */
	public String getZtree(List list){
		
		
		
		return null;
	}
}
