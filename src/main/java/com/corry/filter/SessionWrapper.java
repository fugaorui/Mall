package com.corry.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

public class SessionWrapper extends HttpServletRequestWrapper {

  String sessionId;

  public SessionWrapper(String sessionId, HttpServletRequest request) {
    super(request);
    // TODO Auto-generated constructor stub
    this.sessionId = sessionId;
  }

  @Override
  public HttpSession getSession() {
    // TODO Auto-generated method stub
    return new RedisSessionWrapper(this.sessionId, super.getSession());
  }

  @Override
  public HttpSession getSession(boolean create) {
    // TODO Auto-generated method stub
    return new RedisSessionWrapper(this.sessionId, super.getSession(create));
  }



}
