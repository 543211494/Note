package org.study.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.study.spring.bean.Student;
import org.study.spring.service.UserService;

//@Configuration
//@ComponentScan(basePackages = {"org.study.spring"})
//@PropertySource(value = {"classpath:data.properties"})
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
public class SpringConfig {

//    @Autowired
//    private UserService userService;

    @Autowired
    private Environment environment;

    @Value("${student.name}")
    private String name;

    @Bean(name="student1")
    public Student getStudent(){
        System.out.println(this.name);
        return new Student(Integer.valueOf(environment.getProperty("student.id")),environment.getProperty("student.name"),Integer.valueOf(environment.getProperty("student.age")));
    }
}
