package org.study.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.study.spring.aop.User;
import org.study.spring.aop.UserDao;
import org.study.spring.aop.UserDaoImpl;
import org.study.spring.bean.Book;
import org.study.spring.bean.Demo;
import org.study.spring.bean.Student;
import org.study.spring.config.SpringConfig;
import org.study.spring.service.UserService;

import java.util.Arrays;

public class Main {

    public static void main(String[] args){
        //ApplicationContext context = new ClassPathXmlApplicationContext("bean6.xml");
//        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
//        UserService userService = context.getBean("userService",UserService.class);
//        userService.print();
//        Student student1 = context.getBean("student1",Student.class);
//        System.out.println(student1.toString());
        ApplicationContext context = new ClassPathXmlApplicationContext("bean7.xml");
        UserDaoImpl user = context.getBean("userDao",UserDaoImpl.class);
        user.add(1,2);
    }
}
