package com.corry.Serializers;

import org.apache.shiro.codec.Base64;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public final class KryoSerializer implements Serializer {
  private static final ThreadLocal<Kryo> _threadLocalKryo = new ThreadLocal<Kryo>();

  private Kryo getKryo() {
    Kryo kryo = _threadLocalKryo.get();
    if (kryo == null) {
      kryo = new Kryo();
      // System.out.println("=====================KryoSerializer=" +
      // RamUsageEstimator.humanSizeOf(kryo));
      _threadLocalKryo.set(kryo);
    }
    return kryo;
  }

  // private KryoSerializer() {
  // }

  public byte[] write(Object obj) {
    return write(obj, -1);
  }

  public byte[] write(Object obj, int maxBufferSize) {

    Output output = new Output(1024, maxBufferSize);
    getKryo().writeClassAndObject(output, obj);
    return output.toBytes();
  }

  public Object read(byte[] bytes) {

    Input input = new Input(bytes);
    return getKryo().readClassAndObject(input);
  }

  @Override
  public String obj2str(Object obj) throws Exception {
    return Base64.encodeToString(write(obj, -1));
  }

  @Override
  public Object str2obj(String str) throws Exception {
    if (str != null) {
      return read(Base64.decode(str));
    }
    return null;
  }

}
