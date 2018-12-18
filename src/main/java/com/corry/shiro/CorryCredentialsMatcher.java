package com.corry.shiro;

import com.corry.security.PasswordCheck;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.ByteSource;

public class CorryCredentialsMatcher
    extends org.apache.shiro.authc.credential.SimpleCredentialsMatcher {

  @Override
  public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    UsernamePasswordToken uptoken = (UsernamePasswordToken) token;
    /*if (uptoken.isSilentLogin()) {
      // 后台登录直接通过
      return true;
    }*/
    Object tokenCredentials = getCredentials(token);
    Object accountCredentials = getCredentials(info);

    String tokenCreStr = toString(tokenCredentials);
    String accountCreStr = toString(accountCredentials);

    SimpleAuthenticationInfo sinfo = (SimpleAuthenticationInfo) info;
    ByteSource byteSource = sinfo.getCredentialsSalt();
    String salt = toString(byteSource.getBytes());
    
    String saltAndPassword=salt+accountCreStr;

    boolean valRes= PasswordCheck.validatePasswordDef(tokenCreStr,saltAndPassword);
    
    return valRes;
    
    /*
    byte[] saltByte = Encodes.decodeHex(salt);
    byte[] hashPassword =
        Digests.sha1(tokenCreStr.getBytes(), saltByte, PasswordCheck.HASH_INTERATIONS);
    String encodePassword = Encodes.encodeHex(hashPassword);
    return accountCreStr.equals(encodePassword);
     */
    // return PasswordCheck.validatePassword(toString(tokenCredentials),
    // toString(accountCredentials) );
  }

}
