package com.example.test.realm;

import com.example.test.entity.Permission;
import com.example.test.entity.Role;
import com.example.test.entity.User;
import com.example.test.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    /*@Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher(credentialsMatcher);
    }*/

    @Autowired
    private UserService userService;

    /**
     * 用户授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //这里获取到的principal就是登录用户的用户名
        Object principal = principalCollection.getPrimaryPrincipal();
        Set<String> roles = new HashSet<>();
        User user = userService.findUserByName(principal.toString());
        List<Role> roleList = user.getRoles();
        List<String> permissions = new ArrayList<>();
        //传入用户拥有的所有角色信息
        System.out.println("拥有的角色：" );
        for (Role role : roleList) {
            roles.add(role.getRole_name());
            List<Permission> permissionList = role.getPermissions();
            for (Permission permission : permissionList) {
                permissions.add(permission.getPermission_name());
            }
            System.out.println(role.getRole_name());
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(roles);
        authorizationInfo.addStringPermissions(permissions);
//        return new SimpleAuthorizationInfo(roles);
        return authorizationInfo;
    }

    /**
     * 用户认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userService.findUserByName(username);
        if ("monster".equals(username)) {
            throw new LockedAccountException("用户被锁定");
        }
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }
        /**
         *  参数：
         *  principals：认证的实体信息，
         *  credentials：密码，
         *  realmName:当前realm对象的name，调用父类的getName()方法即可
         *  credentialsSalt:盐值
         */
        Object principals = username;
        Object credentials = user.getPassword();
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        String realmName = getName();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principals,credentials,credentialsSalt,realmName);
//        return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
        return info;
    }
}
