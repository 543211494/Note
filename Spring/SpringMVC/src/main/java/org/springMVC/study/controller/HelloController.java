package org.springMVC.study.controller;

import org.springMVC.study.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @ResponseBody
    public String hello(){
        return "hello!";
    }

//    @Bean
//    public User getUser(){
//        System.out.println("JZN");
//        return new User(1,"test");
//    }
}
