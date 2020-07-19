package com.example;

import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroSpringbootApplicationTests {

  @Autowired
  UserService userService;

  @Test
  void contextLoads() {
    System.out.println(userService.queryUserByName("李四"));
  }

}
