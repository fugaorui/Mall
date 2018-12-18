import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {

    public static void main(String[] args) throws UnsupportedEncodingException,NoSuchAlgorithmException {
        //adf5fef7d871f79faa968005773433cb539566f140edf6668d1057f5
        String str ="adf5fef7d871f79f";
        String plainPassword ="123456";
        SimpleByteSource simpleByteSource = new SimpleByteSource(str);
        System.out.println("simpleByteSource:"+simpleByteSource.toString());
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("root","d24128b69f88d96f39a9d64bef9a86416529ab0b",simpleByteSource,"JmoaUserRealm");
        ByteSource byteSource = simpleAuthenticationInfo.getCredentialsSalt();
        String str1 = new String(byteSource.getBytes(),"UTF-8");
        /**/
        byte[] salt = Hex.decode(str);
        //byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        //result = password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
        MessageDigest digest = MessageDigest.getInstance("SHA-1");

        if (salt != null) {
            digest.update(salt);
        }

        byte[] result = digest.digest(plainPassword.getBytes());

        for (int i = 1; i < 1024; i++) {
            digest.reset();
            result = digest.digest(result);
        }
        byte[] hashPassword  =result;
        System.out.println(Hex.encodeToString(salt));
        System.out.println(Hex.encodeToString(salt)+Hex.encodeToString(hashPassword));
    }
}
