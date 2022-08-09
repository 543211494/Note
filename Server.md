## 1.安装jdk8

解压文件

```shell
tar -zxvf jdk-8u311-linux-x64.tar.gz -C /usr/local/java/
```

添加环境变量

```shell
vi /etc/profile
```

在其后添加

```shell
export JAVA_HOME=/usr/local/java/jdk1.8.0_311
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH
```

使环境变量生效

```shell
source /etc/profile
```

## 2.安装Tomcat

```shell
#解压tomcat至当前目录
tar -zxvf apache-tomcat-9.0.54.tar.gz -C /usr/local/tomcat/
#进入tomcat的bin文件夹
cd /usr/local/tomcat/apache-tomcat-9.0.54/bin
#启动tomcat
./startup.sh
#关闭tomcat
./shutdown.sh
```

## 3.安装MySQL

```shell
#下载MySQL
wget https://dev.mysql.com/get/mysql57-community-release-el7-9.noarch.rpm
#安装
rpm -ivh mysql57-community-release-el7-9.noarch.rpm
yum -y install mysql-server
#启动MySQL
systemctl start mysqld
#检查是否安装启动成功
systemctl status mysqld
#登录修改密码
mysql -u root -p
ALTER USER 'root'@'localhost' IDENTIFIED BY '123456';
#将root的localhost改为%，谁都可以连接
use mysql
update user set host = '%' where user = 'root'
#刷新生效
FLUSH PRIVILEGES
```

## 4.安装nodejs

```shell
#下载压缩包
wget https://npm.taobao.org/mirrors/node/v12.16.1/node-v12.16.1-linux-x64.tar.gz
#解压
tar -zxvf node-v12.16.1-linux-x64.tar.gz
#设置软链接并验证
ln -s /usr/local/nodejs/node-v12.16.1-linux-x64/bin/node /usr/local/bin/
ln -s /usr/local/nodejs/node-v12.16.1-linux-x64/bin/npm /usr/local/bin/
#npm换源
npm set registry https://registry.npm.taobao.org/
#全局安装forever
npm install forever -g
ln -s /usr/local/nodejs/node-v12.16.1-linux-x64/bin/forever /usr/local/bin/

```

## 5.安装redis

(1)解压文件

```sh
tar -zxvf redis-6.2.7.tar.gz
```

(2)编译

```sh
cd redis-6.2.7
make
```

(3)安装

```sh
make install
```

(4)验证

```
cd /usr/local/bin
ll
```

查看默认安装目录 /usr/local/bin

修改配置

```
vi redis.conf
```

将daemonize no改为daemonize yes

设置密码，requirepass password

## 6.安装nginx

(1)检查环境

```sh
#解压文件
tar zxvf nginx-1.22.0.tar.gz
#检查环境
#先决定好安装路径，如 A 目录
#解压压缩包到 B 目录
#进入 B 目录，指定 A 目录为安装目录。即执行 ./configure --prefix=A目录
#执行 make
#执行 make install
#进入 A 目录，Nginx 已经准备就绪
./configure --prefix=/usr/local/nginx
```

报错信息：

```
./configure: error: the HTTP rewrite module requires the PCRE library.
You can either disable the module by using --without-http_rewrite_module
option, or install the PCRE library into the system, or build the PCRE library
statically from the source with nginx by using --with-pcre=<path> option.
```

解决方案：

```shell
#安装perl库
yum install -y pcre pcre-devel
```

不缺依赖的检查结果

```
Configuration summary
  + using system PCRE library
  + OpenSSL library is not used
  + using system zlib library

  nginx path prefix: "/usr/local/nginx-1.22.0"
  nginx binary file: "/usr/local/nginx-1.22.0/sbin/nginx"
  nginx modules path: "/usr/local/nginx-1.22.0/modules"
  nginx configuration prefix: "/usr/local/nginx-1.22.0/conf"
  nginx configuration file: "/usr/local/nginx-1.22.0/conf/nginx.conf"
  nginx pid file: "/usr/local/nginx-1.22.0/logs/nginx.pid"
  nginx error log file: "/usr/local/nginx-1.22.0/logs/error.log"
  nginx http access log file: "/usr/local/nginx-1.22.0/logs/access.log"
  nginx http client request body temporary files: "client_body_temp"
  nginx http proxy temporary files: "proxy_temp"
  nginx http fastcgi temporary files: "fastcgi_temp"
  nginx http uwsgi temporary files: "uwsgi_temp"
  nginx http scgi temporary files: "scgi_temp"
```

(2)编译安装

下载地址:<a href="http://nginx.org/en/download.html">http://nginx.org/en/download.html</a>

```shell
#进入解压的目录下，编译
make
#安装
make install
```
