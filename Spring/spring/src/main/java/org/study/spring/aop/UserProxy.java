package org.study.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserProxy {

    //@Before(value = "execution(* org.study.spring.aop.User.function1(..))")
    @Before(value = "execution(* org.study.spring.aop.UserDaoImpl.add(..))")
    public void before(){
        System.out.println("before");
    }
}
