package com.xw.shiro_test;

import com.xw.shiro_test.model.Permission;
import com.xw.shiro_test.model.Role;
import com.xw.shiro_test.model.User;
import com.xw.shiro_test.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * shiro的登录认证和授权类，自定义Realm
 */
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        User dbuser = userService.getUserByUserName(user.getUsername());
        List<String> permissionList = new ArrayList<>();
        Set<Role> roleList = dbuser.getRoles();
        List<String> roleNameList = new LinkedList<>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        if(!CollectionUtils.isEmpty(roleList)){
            for(Role role : roleList){
                roleNameList.add(role.getRoleName());
                Set<Permission> permissions = role.getPermissions();
                if(!CollectionUtils.isEmpty(permissions)){
                    for(Permission permission:permissions){
                        permissionList.add(permission.getpName());
                    }
                }
            }
        }

        info.addRoles(roleNameList);
        info.addStringPermissions(permissionList);
        return info;
    }

    //登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken  usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        User user = userService.getUserByUserName(username);
        if(user==null){
            //此处可能为空，需要处理空指针异常
            System.out.println("用户名不存在");
            throw new AuthenticationException("用户名不存在");
        }
        return new SimpleAuthenticationInfo(user,user.getPassword(),this.getClass().getName());
    }
}
