package com.example.config;

import com.example.pojo.User;
import com.example.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

  @Autowired
  UserService userService;

// 身份验证
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
    System.out.println("执行了doGetAuthenticationInfo身份验证");
    Subject subject = SecurityUtils.getSubject();

    UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;

    User user = userService.queryUserByName(usernamePasswordToken.getUsername());

    if (user==null){
      return null;
    }
    return new SimpleAuthenticationInfo(user,user.getPwd(),"");
  }


//  批准授权
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    System.out.println("执行了doGetAuthorizationInfo批准授权");
    Subject subject = SecurityUtils.getSubject();
    User principal = (User) subject.getPrincipal();

    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    info.addStringPermission(principal.getPerms());

//    info.addStringPermission("user:add");

    return info;
  }
}
