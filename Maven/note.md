# 1.参数说明

- groupId：公司或组织的id
- artifactId：一个项目或是项目中一个模块的id
- version：版本号
- scope：依赖范围,可选值compile/test/provided/system/runtime/import

<table>
	<tr align="center">
		<th></th>
		<th>main目录(空间)</th>
		<th>test目录(空间)</th>
		<th>开发过程(时间)</th>
		<th>部署到服务器(时间)</th>
	</tr>
	<tr align="center">
		<td>compile</td>
		<td>有效</td>
		<td>有效</td>
		<td>有效</td>
		<td>有效</td>
	</tr>
	<tr align="center">
		<td>test</td>
		<td>无效</td>
		<td>有效</td>
		<td>有效</td>
		<td>无效</td>
	</tr>
	<tr align="center">
		<td>provided</td>
		<td>有效</td>
		<td>有效</td>
		<td>有效</td>
		<td>无效</td>
	</tr>
</table>
# 2.依赖的传递性

A 依赖 B，B 依赖 C，在 A 没有配置对 C 的依赖的情况下A直接使用C

- B 依赖 C 时使用 compile 范围：可以传递
- B 依赖 C 时使用 test 或 provided 范围：不能传递，所以需要这样的 jar 包时，就必须在需要的地方明确配置依赖才可以。

# 3.测试依赖的排除

当 A 依赖 B，B 依赖 C 而且 C 可以传递到 A 的时候，A 不想要 C，需要在 A 里面把 C 排除掉。而往往这种情况都是为了避免 jar 包之间的冲突。

```xml
<dependency>
	<groupId>com.atguigu.maven</groupId>
	<artifactId>pro01-maven-java</artifactId>
	<version>1.0-SNAPSHOT</version>
	<scope>compile</scope>
	<!-- 使用excludes标签配置依赖的排除	-->
	<exclusions>
		<!-- 在exclude标签中配置一个具体的排除 -->
		<exclusion>
			<!-- 指定要排除的依赖的坐标（不需要写version） -->
			<groupId>commons-logging</groupId>
			<artifactId>commons-logging</artifactId>
		</exclusion>
	</exclusions>
</dependency>
```

# 4.继承

## 4.1父工程

只有打包方式为 pom 的 Maven 工程能够管理其他 Maven 工程。打包方式为 pom 的 Maven 工程中不写业务代码，它是专门管理其他 Maven 工程的工程。

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.study.maven</groupId>
    <artifactId>maven-parent</artifactId>
    <version>1.0-SNAPSHOT</version>

    <!-- 当前工程作为父工程，它要去管理子工程，所以打包方式必须是 pom -->
    <packaging>pom</packaging>
    <!-- 自定义属性 -->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <!-- 自定义标签，维护Spring版本数据 -->
        <spring.version>5.2.6.RELEASE</spring.version>
    </properties>
    <!-- 在构建这个项目的时候，不需要深入每个module去单独构建 -->
    <!-- 在项目A下的pom.xml构建，就会完成对两个module的构建 -->
    <modules>  
	  <module>module1</module>
	  <module>module2</module>
	  <module>module3</module>
    </modules>
    <!-- 使用dependencyManagement标签配置对依赖的管理 -->
    <!-- 被管理的依赖并没有真正被引入到工程 -->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-core</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-beans</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-context</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-expression</artifactId>
                <version>${spring.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-aop</artifactId>
                <version>${spring.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project> 
```

## 4.2子工程

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <!-- 使用parent标签指定当前工程的父工程 -->
    <parent>
        <!-- 父工程的坐标 -->
        <groupId>com.study.maven</groupId>
        <artifactId>maven-parent</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <!-- 子工程的坐标 -->
    <!-- 如果子工程坐标中的groupId和version与父工程一致，那么可以省略 -->
    <groupId>com.study.maven</groupId>
    <artifactId>module1</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>module1</name>
    <url>http://maven.apache.org</url>
    <!-- 自定义属性 -->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <!-- 子工程引用父工程中的依赖信息时，可以把版本号去掉。	-->
    <!-- 把版本号去掉就表示子工程中这个依赖的版本由父工程决定。 -->
    <!-- 具体来说是由父工程的dependencyManagement来决定。 -->
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-expression</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
        </dependency>
    </dependencies>
</project> 
```
# 5.使用springboot打包插件

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <executable>true</executable>
    </configuration>
</plugin>
```