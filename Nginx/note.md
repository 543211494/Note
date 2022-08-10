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

下载地址:<a href="http://nginx.org/en/download.html">http://nginx.org/en/download.html</a>

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

## 2.4windows下安装

下载地址<a herf="http://nginx.org/download/">http://nginx.org/download/</a>

可解决跨域问题方便调试程序

进入解压后文件夹根目录，打开cmd

```shell
#启动nginx
start nginx
#停止nginx
nginx -s stop
#重启服务
nginx -s reload
```

解决session失效问题

```
location /api/ {
	proxy_pass http://127.0.0.1:8080/demo/;
	proxy_cookie_path /demo /api;
}
#语法
#proxy_cookie_path path replacement
```

cookie的path与地址栏的path不一致就会被浏览器丢弃造成session验证失效，加上这个配置在转发的时候会把path替换，path为要替换的路径，replacement为要替换的值

# 3.nginx配置文件

## 3.1基本概念

(1)正向代理

在客户端配置代理服务器，通过代理服务器进行互联网访问(屏蔽了真正的客户端)

(2)反向代理

客户将请求发到代理服务器进行互联网访问，反向代理服务器和目标服务器对外是一个服务器(屏蔽了真正的服务端)

(3)负载均衡

将负载均衡地发配给各个服务器

(4)动静分离

动态资源和静态资源交给不同的服务器

## 3.2nginx.conf

```
#user  nobody;
#工作的进程个数
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;


events {
	#每个worker能创建的连接数
    worker_connections  1024;
}


http {
	#将其它文件引入，mime.types记录了请求头中的文件类型
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

	#数据零拷贝
    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;

	#虚拟主机
    server {
    	#端口号
        listen       80;
        #域名，主机名
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
        	#根目录，可以放静态资源文件
            root   html;
            #默认展示的文件
            index  index.html index.htm;
        }

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }


    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}


    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}

}
```

## 3.3反向代理配置

vi usr/local/nginx/conf/nginx.conf

```
http {
    include       mime.types;
    default_type  application/octet-stream;
    sendfile        on;
    keepalive_timeout  65;
    }
    server {
        listen       80;
        server_name  localhost;
        location / {
        	#所有匹配到的请求都会被转发给http://127.0.0.1:8080/
        	proxy_pass http://127.0.0.1:8080/;
        }
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
    }
```

## 3.4负载均衡

```
http {
    include       mime.types;
    default_type  application/octet-stream;
    upstream myserver {
    	server 127.0.0.1:8080;
    	server 127.0.0.1:80;
    }
    server {
        listen       80;
        server_name  localhost;
        location / {
            root   html;
            index  index.html index.htm;
        }
        location /proxy/ {
            proxy_pass http://myserver/;
        }
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
    }

```

分配策略

(1)轮询

默认，按顺序逐一分配

(2)weight

weight代表权重，默认为1，权重越高被分配的客户端越多

```
upstream myserver {
    server 127.0.0.1:8080 weight=10;
    server 127.0.0.1:80 weight=1;
}
```

(3)ip_hash

每个请求按访问ip的hash结果分配，这样每个访客固定访问一个后端服务器，可以解决session问题

```
upstream myserver {
	ip_hash
    server 127.0.0.1:8080;
    server 127.0.0.1:80;
}
```

(4)fair

按后端服务器的响应时间分配,需要第三方插件
