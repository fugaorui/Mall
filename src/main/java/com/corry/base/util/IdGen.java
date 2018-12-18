/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights
 * reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.corry.base.util;
import java.security.SecureRandom;
import java.util.UUID;


public class IdGen {

  private static SecureRandom random = new SecureRandom();

  /**
   * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
   */
  public static String uuid() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  /**
   * 使用SecureRandom随机生成Long.
   */
  public static long randomLong() {
    return Math.abs(random.nextLong());
  }

  public String getNextId() {
    return IdGen.uuid();
  }

  public void test(Boolean b) {
    System.out.print(b);
    b = Boolean.TRUE;
  }
}
