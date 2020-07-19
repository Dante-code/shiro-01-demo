package com.example.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
  //shiroFilterFactoryBean
  @Bean
  public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
    ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
    //设置安全管理器
    bean.setSecurityManager(securityManager);

//    添加shiro的内置过滤器
    /*
    * anon: 无需认证就可以访问
    * authc:必须认证才能访问
    * user: 必须拥有"记住我"功能才能用
    * perms:拥有对某个资源的权限才能访问
    * role: 拥有某个角色权限才能访问
    * */
    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

    filterChainDefinitionMap.put("/user/add","perms[useradd]");

//    filterChainDefinitionMap.put("/user/add","authc");
    filterChainDefinitionMap.put("/user/update","perms[userupdate]");

    bean.setLoginUrl("/login");
    bean.setUnauthorizedUrl("/noauth");

    bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    return bean;
  }


  //step2: DefaultWebSecurityManager
  @Bean(name = "securityManager")
  public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//    关联realm
    securityManager.setRealm(userRealm);
    return securityManager;
  }


  //step1: 创建realm对象，需要自定义类
  @Bean(name = "userRealm")
  public UserRealm userRealm(){
    return new UserRealm();
  }

//  整合shiroDialect
  @Bean
  public ShiroDialect getShiroDialect(){
    return new ShiroDialect();
  }
}
