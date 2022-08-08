package org.study.spring.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component(value = "userDao")
@Transactional(propagation = Propagation.REQUIRED)
public class UserDaoImpl implements UserDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private int num = 1;
    @Override
    public int add(int a, int b) {
        jdbcTemplate.update("INSERT INTO user (id,userName,age)VALUES (3,\"test\",20)");
        if (num==1) {
            throw new RuntimeException("test");
        }
        return a+b;
    }

    @Override
    public String update(String id) {
        return id;
    }
}
