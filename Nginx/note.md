# 1.常用版本

Nginx开源版

<a herf="http://nginx.org/">http://nginx.org/</a>

Ngnix plus商业版

<a herf="https://www.nginx.com">https://www.nginx.com</a>

Openresty商业版

<a herf="http://openresty.org">http://openresty.org</a>

Tengine商业版

<a herf="http://tengine.taobao.org/">http://tengine.taobao.org/</a>

# 2.安装Nginx

## 2.1检查环境

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

## 2.2编译安装

下载地址:http://nginx.org/en/download.html

```shell
#进入解压的目录下，编译
make
#安装
make install
```

## 2.3验证

```shell
cd sbin
#启动nginx
./nginx
#快速停止
./nginx -s stop
#优雅关闭，关闭之前完成已经接受的连接请求
./ngnix -s quit
#重新加载配置
./ngnix -s reload
```

