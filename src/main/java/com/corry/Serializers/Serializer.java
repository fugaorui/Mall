package com.corry.Serializers;

import java.io.IOException;

public interface Serializer {
  public byte[] write(Object obj);

  public Object read(byte[] bytes) throws ClassNotFoundException, IOException;

  public String obj2str(Object obj) throws Exception;

  public Object str2obj(String str) throws Exception;
}
