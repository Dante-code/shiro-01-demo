package com.example.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

  @RequestMapping("/")
  public String toIndex(Model model){
    model.addAttribute("msg","hello,Shiro");
    return "index";
  }

  @RequestMapping("/user/add")
  public String add(){
    return "user/add";
  }

  @RequestMapping("/user/update")
  public String update(){
    return "user/update";
  }

  @RequestMapping("/login")
  public String toLogin(){
    return "login";
  }

  @PostMapping("/user/userlogin")
  public String userlogin(String username, String password, Model model){
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(username, password);

    try {
      subject.login(token);
      return "index";
    } catch (UnknownAccountException e) {
//      用户名不存在
      model.addAttribute("msg","用户名错误");
      e.printStackTrace();
      return "login";
    } catch (IncorrectCredentialsException e){
//      密码错误
      model.addAttribute("msg","密码错误");
      return "login";
    }
  }

  @RequestMapping("/noauth")
  @ResponseBody
  public String unauthorized(){
    return "<h1>未经授权无法访问此页面</h1>";
  }

}
