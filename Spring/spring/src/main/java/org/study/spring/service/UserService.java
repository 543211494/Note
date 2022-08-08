package org.study.spring.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//注解中的value可以不写，相当于bean中的id
//不写value，其id默认为类名将首字母小写
@Component(value = "userService")
public class UserService {
    public void print(){
        System.out.println("hello!");
    }
}
