package com.corry.base.util;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/***
 * 方法返回传递数据的对象操作类
 * 
 * @author lxw
 *
 */
public class ResultDTO implements Serializable {

  private static final long serialVersionUID = -6381274066247043903L;

  private int state;// 状态值

  private String message;// 提示消息

  private String messageKey;// 消息键值

  private Object[] messageParameter = null;// 消息参数


  private Map<String, Object> model = new HashMap<String, Object>(4);// 方便操作中保存其它数据

  public ResultDTO() {}

  public ResultDTO(String messageKey) {
    this.messageKey = messageKey;
  }

  public ResultDTO(String messageKey, String message) {
    this.messageKey = messageKey;
    this.message = message;
  }

  public ResultDTO(String messageKey, String message, Object[] messageParameter) {
    this.messageKey = messageKey;
    this.message = message;
    this.messageParameter = messageParameter;
  }

  public ResultDTO(int state) {
    this.state = state;
  }


  public ResultDTO(int state, String messageKey) {
    this.state = state;
    this.messageKey = messageKey;
  }

  public ResultDTO(int state, String messageKey, String message) {
    this.message = message;
    this.state = state;
    this.messageKey = messageKey;
  }

  public ResultDTO(int state, String messageKey, String message, Object[] messageParameter) {
    this.state = state;
    this.messageKey = messageKey;
    this.message = message;
    this.messageParameter = messageParameter;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public String getMessage() {
    return message == null ? messageKey : message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessageKey() {
    return messageKey;
  }

  public void setMessageKey(String messageKey) {
    this.messageKey = messageKey;
  }

  public Object[] getMessageParameter() {
    return messageParameter;
  }

  public void setMessageParameter(Object[] messageParameter) {
    this.messageParameter = messageParameter;
  }

  public Map<String, Object> getModel() {
    return model;
  }

  public Object put(String key, Object value) {
    return model.put(key, value);
  }

  public Object get(Object key) {
    return model.get(key);
  }

  public void putAll(Map<? extends String, ? extends Object> map) {
    model.putAll(map);
  }

  public Object remove(Object key) {
    return model.remove(key);
  }
}
