/**
 *
 */
package com.corry.shiro;


import com.corry.admin.pojo.User;
import com.corry.admin.service.UserMapper;
import com.corry.base.util.BaseDto;
import com.corry.base.util.Constants;
import com.corry.base.util.Dto;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: MyRealm
 * @Description: shiro 认证 + 授权   重写 */
public class MyRealm extends AuthorizingRealm {


    @Autowired
    private UserMapper userMapper;
    Dto dto = new BaseDto();
    public MyRealm() {
        super();
        this.setName(Constants.USER_REALM_NAME);
        this.setAuthorizationCacheName(Constants.USER_REALM_AUTHORIZATION_CACHENAME);
    }
    /* (non-Javadoc)
     * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
     */
    /**
     * 授权Realm
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /*String account = (String)principals.getPrimaryPrincipal();
        User pojo = new User();
        pojo.setAccount(account);
        Long userId = adminUserService.selectOne(pojo).getId();
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        //*根据用户ID查询角色（role），放入到Authorization里.
        info.setRoles(adminUserService.findRoleByUserId(userId));
        //*根据用户ID查询权限（permission），放入到Authorization里.
        info.setStringPermissions(adminUserService.findPermissionByUserId(userId));
        return info;*/
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        return info;
    }

    /* (non-Javadoc)
     * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
     */
    /**
     * 登录认证Realm
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        String username = (String)token.getPrincipal();
        User user = userMapper.findUserBylogin(username);
        if(null==user){
            throw new UnknownAccountException("账号密码错误");
        }
        if(user.getIslock()){
            throw new LockedAccountException("账号锁定了");
        }
       /* User user = adminUserService.login(username, password);

        if(user.getIsDisabled()){
            throw new DisabledAccountException("帐号已经禁止登录！");
        }
        //**密码加盐**交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配*/
        String orgPassword = user.getPassword();
        String salt = StringUtils.substring(orgPassword, 0, 16);
        String password = StringUtils.substring(orgPassword, 16);
        SimpleByteSource byteSource = new SimpleByteSource(salt);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, // 用户名
                password, // 密码
                byteSource, // salt
                getName() // realm name
        );
        return authenticationInfo;
    }


    /**
     * 清空当前用户权限信息
     */
    public  void clearCachedAuthorizationInfo() {
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
    /**
     * 指定principalCollection 清除
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }


}