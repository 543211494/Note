# 1.IDEA控制台输出乱码

在bin文件夹下的idea64.exe.vmoptions文件后追加-Dfile.encoding=UTF-8

# 2.IOC

## 2.1底层原理

xml解析,工厂模式，反射机制

### 2.1.1bean作用域

bean默认为单实例对象，可通过scope进行设置，scope有以下几个值

(1)singleton，表示是单实例对象，加载xml文件时创建对象实例

(2)prototype，表示是多实例对象，获取时创建对象实例

(3)request

(4)session

### 2.1.2bean生命周期

(1)创建bean实例

(2)为bean的属性设置值和对其它bean的引用

(3)调用bean的初始化方法(需要进行配置初始化的方法)

(4)bean可以使用了

(5)当容器关闭时，调用bean的销毁方法(需要进行配置销毁的方法)

## 2.2xml

pom.xml

```xml
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.9</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-core</artifactId>
      <version>4.0.7.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-beans</artifactId>
      <version>4.0.7.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>4.0.7.RELEASE</version>
    </dependency>
  </dependencies>
```

### 2.2.1基本属性的注入

beans

```java
package org.study.spring.bean;

public class Student {
    private int id;
    private String name;
    private int age;
    /*getter,setter,constructor,toString略*/
}
```

xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--配置User对象创建-->
    <bean id="student1" class="org.study.spring.bean.Student"></bean>
    <!--set方法注入属性-->
    <bean id="student2" class="org.study.spring.bean.Student">
        <!--使用Property来进行属性注入-->
        <property name="id" value="2"/>
        <property name="name" value="Micah"/>
        <property name="age" value="21"></property>
    </bean>
    <!--有参构造方法注入属性-->
    <bean id="student3" class="org.study.spring.bean.Student">
        <constructor-arg index="0" value="3"/>
        <constructor-arg index="1" value="lichang"/>
        <constructor-arg index="2" value="20"></constructor-arg>
    </bean>
    <!--  p名称空间注入  -->
    <bean id="student4" class="org.study.spring.bean.Student" p:id="4" p:name="cmf"/>
</beans>
```

Main

```java
public static void main(String[] args){
    ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
    Student studen1 = context.getBean("student1",Student.class);
    Student studen2 = context.getBean("student2",Student.class);
    Student studen3 = context.getBean("student3",Student.class);
    Student studen4 = context.getBean("student4",Student.class);
    System.out.println(studen1.toString());
    System.out.println(studen2.toString());
    System.out.println(studen3.toString());
    System.out.println(studen4.toString());
}
```

### 2.2.2对象属性注入

beans

```java
package org.study.spring.bean;

public class Book {

    private String IBSN;
    private double price;
    private User author;
}
```

```java
package org.study.spring.bean;

public class User {
    private int id;
    private String name;
    private int age;
}
```

xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="book1" class="org.study.spring.bean.Book">
        <!--   普通属性     -->
        <property name="IBSN" value="001"></property>
        <property name="price" value="18.8"></property>
        <!--
            注入对象属性
            ref为要注入对象的id
        -->
        <property name="author" ref="user1"></property>
    </bean>

    <bean id="book2" class="org.study.spring.bean.Book">
        <!--   普通属性     -->
        <property name="IBSN" value="002"></property>
        <property name="price" value="18.8"></property>
        <!--   对象属性     -->
        <property name="author">
            <bean id="user2" class="org.study.spring.bean.User">
                <property name="age" value="20"></property>
                <property name="id" value="002"></property>
                <property name="name" value="user002"></property>
            </bean>
        </property>
    </bean>

    <bean id="user1" class="org.study.spring.bean.User">
        <property name="age" value="20"></property>
        <property name="id" value="001"></property>
        <property name="name" value="user001"></property>
    </bean>
</beans>
```

Main

```java
public static void main(String[] args){
    ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
    Book book1 = context.getBean("book1", Book.class);
    Book book2 = context.getBean("book2", Book.class);
    System.out.println(book1.toString());
    System.out.println(book2.toString());
}
```

### 2.2.3集合类型注入

beans

```java
package org.study.spring.bean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Demo {
    private String[] array;
    private List<String> list;
    private Map<String,String> map;
    private Set<String> set;
}
```

xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--  集合类型注入  -->
    <bean id="demo1" class="org.study.spring.bean.Demo">
        <!--    数组类型注入    -->
        <property name="array">
            <array>
                <!--
                     若要注入对象，格式如下:
                     <ref bean=“id”></ref>
                 -->
                <value>abc</value>
                <value>123</value>
                <value>efg</value>
            </array>
        </property>
        <!--   list类型注入    -->
        <property name="list">
            <list>
                <value>awf</value>
                <value>jzn</value>
                <value>135</value>
            </list>
        </property>
        <!--   map类型注入     -->
        <property name="map">
            <map>
                <!--
                    若要注入对象，格式如下:
                    <entry key-ref="id" value-ref="id"></entry>
                -->
                <entry key="a" value="12"></entry>
                <entry key="b" value="34"></entry>
                <entry key="c" value="56"></entry>
            </map>
        </property>
        <!--   set类型注入     -->
        <property name="set">
            <set>
                <value>123</value>
                <value>456</value>
                <value>abc</value>
            </set>
        </property>
    </bean>
</beans>
```

Main

```java
public static void main(String[] args){
    ApplicationContext context = new ClassPathXmlApplicationContext("bean3.xml");
    Demo demo1 = context.getBean("demo1", Demo.class);
    System.out.println(demo1.toString());
}
```

### 2.2.4自动装配

beans

```java
package org.study.spring.bean;

public class Book {

    private String IBSN;
    private double price;
    private User author;
}
```

```java
package org.study.spring.bean;

public class User {
    private int id;
    private String name;
    private int age;
}
```

xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="book1" class="org.study.spring.bean.Book" autowire="byName">
        <!--   普通属性     -->
        <property name="IBSN" value="001"></property>
        <property name="price" value="18.8"></property>
        <!--
            byName方法注入要求注入bean的id与被注入的变量名相同
        -->
    </bean>
    <bean id="book2" class="org.study.spring.bean.Book" autowire="byType">
        <!--   普通属性     -->
        <property name="IBSN" value="001"></property>
        <property name="price" value="18.8"></property>
        <!--
            byType方法注入要求注入bean类型的实例只有一个，否则会保存
        -->
    </bean>
    <bean id="author" class="org.study.spring.bean.User">
        <property name="age" value="20"></property>
        <property name="id" value="001"></property>
        <property name="name" value="user001"></property>
    </bean>
</beans>
```

Main

```java
public static void main(String[] args){
    ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
    Book book1 = context.getBean("book1", Book.class);
    Book book2 = context.getBean("book2", Book.class);
    System.out.println(book1.toString());
    System.out.println(book2.toString());
}
```

### 2.2.5引入外部文件

beans

```java
package org.study.spring.bean;

public class Student {
    private int id;
    private String name;
    private int age;
}
```

xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <!--  引入外部属性文件  -->
    <context:property-placeholder location="classpath:data.properties"></context:property-placeholder>
    <bean id="student1" class="org.study.spring.bean.Student">
        <property name="id" value="${student.id}"/>
        <property name="name" value="${student.name}"/>
        <property name="age" value="${student.age}"></property>
    </bean>
</beans>
```

Main

```java
public static void main(String[] args){
    ApplicationContext context = new ClassPathXmlApplicationContext("bean5.xml");
    Student student1 = context.getBean("student1",Student.class);
    System.out.println(student1.toString());
}
```

data.properties

```properties
student.id=2
student.age=18
student.name=Micah
```

## 2.3注解

pom.xml

```xml
<dependencies>
<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>4.9</version>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-core</artifactId>
  <version>4.0.7.RELEASE</version>
</dependency>
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-beans</artifactId>
  <version>4.0.7.RELEASE</version>
</dependency>
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context</artifactId>
  <version>4.0.7.RELEASE</version>
</dependency>
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-aop</artifactId>
  <version>4.0.7.RELEASE</version>
</dependency>
</dependencies>
```

### 2.3.1创建对象的注解

(1)@Component

(2)@Service

(3)@Controller

(4)@Repository

*这四个注解的功能是一样的，都可以用来创建bean实例*

bean

```java
package org.study.spring.service;

import org.springframework.stereotype.Component;

//注解中的value可以不写，相当于bean中的id
//不写value，其id默认为类名将首字母小写
@Component(value = "userService")
public class UserService {

    public void print(){
        System.out.println("hello!");
    }
}
```

xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <!--  开启组件扫描,多个包可用逗号隔开  -->
    <context:component-scan base-package="org.study.spring">
</beans>
```

包扫描的细化

```xml
<!--  设置扫描的内容  -->
<context:component-scan base-package="org.study.spring" use-default-filters="false">
    <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
</context:component-scan>
<!--  设置不扫描的内容  -->
<context:component-scan base-package="org.study.spring">
    <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Component"/>
</context:component-scan></context:component-scan>
```

Main

```java
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean6.xml");
        UserService userService = context.getBean("userService",UserService.class);
        userService.print();
    }
```

### 2.3.2注入属性的注解

(1)@Autowired 根据属性类型进行自动装配(不需要set方法)

(2)@Qualifier 根据属性名称进行注入

和@Autowire搭配使用

```java
@Autowired
@Qualifier(value = "id")
```

(3)@Resource 可以根据类型注入，也可以根据名称注入

```java
@Resource //根据类型注入
@Resource( name = "id") //根据名称注入
```

(4)@Value 注入普通类型

```java
@Value( value = "abc")
```

### 2.3.3完全注解开发

增加配置类，代替xml

SpringConfig

```java
package org.study.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.study.spring.bean.Student;

@Configuration
@ComponentScan(basePackages = {"org.study.spring"})
public class SpringConfig {
	/**
     * 注解类中引用其它bean可直接在函数参数中写相应类型的变量
     * 多个同类型的变量使用@Qualifier选择注入
     */
    @Bean(name="student1")
    public Student getStudent(){
        return new Student(1,"student1",20);
    }
}
```

Main

```java
public static void main(String[] args){
    ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    UserService userService = context.getBean("userService",UserService.class);
    userService.print();
    Student student1 = context.getBean("student1",Student.class);
    System.out.println(student1.toString());
}
```

### 2.3.4使用注解获取properties文件的值

可以使用Environment或@Value获取，但版本过低的spring中用@Value获取会失败，这里使用的spring版本号为5.2.6.RELEASE

```java
@Configuration
@ComponentScan(basePackages = {"org.study.spring"})
@PropertySource(value = {"classpath:data.properties"})
public class SpringConfig {

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
```



# 3.AOP

## 3.1JDK动态代理

接口

```java
public interface UserDao {

    public int add(int a,int b);

    public String update(String id);
}

```

接口实现

```java
public class UserDaoImpl implements UserDao{
    @Override
    public int add(int a, int b) {
        return a+b;
    }

    @Override
    public String update(String id) {
        return id;
    }
}
```

代理

```java
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
```

## 3.2AOP术语

(1)连接点

类里可以被增强的方法

(2)切入点

实际被真正增强的方法

(3)增强(通知)

实际增强的逻辑部分，前置通知，后置通知，环绕通知，异常通知，最终通知

(4)切面

把通知应用到切入点的过程

## 3.3AspectJ

Spring框架一般是基于AspectJ实现AOP

切入点表达式:execution( [权限修饰符] [返回类型] [全类名] [方法名称] [参数列表])

如：execution(* org.study.spring.aop.UserDao.add(...))，对add方法进行增强

pom.xml

```xml
<dependency>
  <groupId>org.aspectj</groupId>
  <artifactId>aspectjrt</artifactId>
  <version>1.9.1</version>
</dependency>
<dependency>
  <groupId>org.aspectj</groupId>
  <artifactId>aspectjweaver</artifactId>
  <version>1.9.1</version>
</dependency>
```

代理类

```java
@Component
@Aspect
public class UserProxy {

    @Before(value = "execution(* org.study.spring.aop.User.function1(..))")
    public void before(){
        System.out.println("before");
    }
}
```

被增强的类

```java
@Component
public class User {
    public void function1() {
        System.out.println("fuction1...");
    }
}
```

xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                           http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
    <!--  开启组件扫描,多个包可用逗号隔开  -->
    <context:component-scan base-package="org.study.spring.aop"></context:component-scan>
    <!--  开启AspectJ生成代理对象  -->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
    <!-- 
        若要增加的类实现了接口，则需加上属性proxy-target-class="true"
        <aop:aspectj-autoproxy proxy-target-class="true"></aop:aspectj-autoproxy>
    -->
</beans>
```

Main

```java
public static void main(String[] args){
    ApplicationContext context = new ClassPathXmlApplicationContext("bean7.xml");
    User user = context.getBean("user",User.class);
    user.function1();
}
```

# 4.Spring事务

xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                           http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
                           http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">
    <!--  开启组件扫描,多个包可用逗号隔开  -->
    <context:component-scan base-package="org.study.spring.aop"></context:component-scan>
    <!--  开启AspectJ生成代理对象  -->
    <aop:aspectj-autoproxy proxy-target-class="true"></aop:aspectj-autoproxy>
    <!--
        若要增加的类实现了接口，则需加上属性proxy-target-class="true"
        <aop:aspectj-autoproxy proxy-target-class="true"></aop:aspectj-autoproxy>
    -->

    <!--  创建数据源  -->
    <bean id="datasource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
        <property name="url" value="jdbc:mysql://127.0.0.1:3306/study?serverTimezone=Asia/Shanghai"></property>
        <property name="username" value="root"></property>
        <property name="password" value="123456"></property>
    </bean>
    <!--  创建jdbcTemplate  -->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="datasource"></property>
    </bean>
    <!--  创建事务管理器  -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="datasource"></property>
    </bean>
    <!--  开启事务注解,等同于@EnableTransactionManagement  -->
    <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
</beans>
```

@Transactional：使用在方法或者类的上面。

事务传播行为,参数为propagation

(1)REQUIRED：如果有事务在运行，当前的方法就在这个事务内运行，否则开启新事物并在自己的事务内运行

(2)REQUIRED_NEW：当前的方法必须启动新事务，并在自己的事务内运行，如果有事务正在运行，应该将它挂起

(3)SUPPORTS：如果有事务在运行，当前的方法就在这个事务内运行，否则它不可以运行在事务中

隔离等级，参数为isolation

(1)TransactionDefnion.ISOLATION_DEFAULT

这是默认值，表示使用底层数据库的默认隔离级别。对大部分数据库而言，通常这值就是TransactionDefinition.ISOLATION_READ_COMMITTED。

(2)Transactionboehnlion. ISOLATION_READ_UNCOMMITTED

该隔离级别表示一个事务可以读取另一个事务修改但还没有提交的数据，该级别不能防止脏读，不可重复读和幻读，因此很少使用该隔离级别。比如PostgreSQL实际上并没有此级别。

(3)TransactionDefinition.ISOLATION_READ_COMMITTED

该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下的推荐值。

(4)TransactionDefinition.ISOLATION_REPEATABLE_READ

该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同。该级别可以防止脏读和不可重复读。

(5)TransactionDefinition.ISOLATION_SERIALIZABLE

所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰。也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别。

# 5.SpringMVC

## 5.1概念

(1)M，即model，指工程中的javaBean

(2)V，即view，指工程中的html或jsp

(3)C，即Controller，指工程中的servlet，作用是接收请求和响应浏览器

IDEA的maven项目中可以右键项目，选择open module settings(F4)进行配置，

## 5.2web.xml配置

pom.xml

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>3.1.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <!--post乱码过滤器-->
    <!-- 配置springMVC编码过滤器 -->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <!-- 设置过滤器中的属性值 -->
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <!-- 启动过滤器 -->
        <init-param>
            <param-name>forceEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <!-- 过滤所有请求 -->
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <!--  配置SpringMVC前端控制器，对浏览器发出的请求进行统一处理  -->
    <servlet>
        <!--   servlet-name要与servlet-mapping保持一致     -->
        <servlet-name>SpringMVC</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
<!--    使用java类代替xml配置    -->
<!--        <init-param>-->
<!--            <param-name>contextClass</param-name>-->
<!--            <param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>-->
<!--        </init-param>-->
<!--        <init-param>-->
<!--            <param-name>contextConfigLocation</param-name>-->
<!--            <param-value>org.springMVC.study.config.WebConfig</param-value>-->
<!--        </init-param>-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <!--  多个值以;隔开   -->
            <param-value>classpath:spring/spring-mvc.xml</param-value>
        </init-param>
        <!--    将DispatcherServlet初始化时间提前到服务器启动时    -->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>SpringMVC</servlet-name>
        <!--
            /匹配的请求可以是/login,.html,.css方式的请求路径，
            但不能是.jsp
        -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

spring-mvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                           http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- spring mvc 的注解驱动-->
    <mvc:annotation-driven></mvc:annotation-driven>
    <!--  扫描组件  -->
    <context:component-scan base-package="org.springMVC.study"></context:component-scan>
    <!-- 配置视图解析器 进行jsp解析-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--配置前缀和后缀，也可以不指定 -->
        <property name="prefix" value="/WEB-INF/view/" />
        <property name="suffix" value=".jsp" />
    </bean>
    <!-- 静态资源映射 -->
    <mvc:resources mapping="/css/**" location="/WEB-INF/resource/css/"></mvc:resources>
    <mvc:resources mapping="/js/**" location="/WEB-INF/resource/js/"></mvc:resources>
</beans>
```

# 6.jdk生成自签证书

命令：keytool -genkey -alias testhttps -keyalg RSA -keysize 2048 -validity 36500 -keystore  "D:/tmp/ssl/testhttps.keystore"

命令解释:
• -genkey 表示要创建一个新的密钥。 

• -alias 表示 keystore 的别名。 

• -keyalg 表示使用的加密算法是 RSA。

• -keysize 表示密钥的长度。

• -keystore 表示生成的密钥存放位直。 

• -validity 表示密钥的有效时间，单位为天。

```properties
server:
  servlet:
    context-path: /test
  ssl:
    key-store: classpath:testhttps.keystore
    key-password: abcdefg
    key-store-password: abcdefg
    key-store-type: JKS
    key-alias: testhttps
    enabled: true
```

