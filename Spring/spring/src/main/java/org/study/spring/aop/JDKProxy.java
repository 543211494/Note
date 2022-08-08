package org.study.spring.aop;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class JDKProxy {

    public static void main(String[] args){
        Class[] interfaces = {UserDao.class};
        UserDaoImpl userDao = new UserDaoImpl();
        UserDao dao = (UserDao) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("方法执行之前..."+method.getName()+",传递的参数:"+ Arrays.toString(args));
                //被增强方法的执行
                Object res = method.invoke(userDao,args);
                System.out.println("方法执行之前..."+userDao);
                return res;
            }
        });
        dao.add(1,2);
    }
}
