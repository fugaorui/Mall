/**
 *
 */
package com.mall.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * @ClassName: MyRealm
 * @Description: shiro 认证 + 授权   重写 */
public class MyRealm extends AuthorizingRealm {




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
        String password = new String((char[])token.getCredentials());
       /* User user = adminUserService.login(username, password);
        if(null==user){
            throw new AccountException("帐号或密码不正确！");
        }
        if(user.getIsDisabled()){
            throw new DisabledAccountException("帐号已经禁止登录！");
        }
        //**密码加盐**交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配*/
        return new SimpleAuthenticationInfo("","",ByteSource.Util.bytes("3.14159"), getName());
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